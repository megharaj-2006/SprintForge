package org.SprintForge.modules.workspace.workspace.department.service;

import lombok.RequiredArgsConstructor;
import org.SprintForge.common.exception.BusinessRuleException;
import org.SprintForge.common.exception.ResourceNotFoundException;
import org.SprintForge.modules.workspace.workspace.department.dto.request.CreateDepartmentRequest;
import org.SprintForge.modules.workspace.workspace.department.dto.request.UpdateDepartmentRequest;
import org.SprintForge.modules.workspace.workspace.department.dto.response.DepartmentResponse;
import org.SprintForge.modules.workspace.workspace.department.dto.response.DepartmentSummaryResponse;
import org.SprintForge.modules.workspace.workspace.department.entity.Department;
import org.SprintForge.modules.workspace.workspace.department.event.DepartmentCreatedEvent;
import org.SprintForge.modules.workspace.workspace.department.mapper.DepartmentMapper;
import org.SprintForge.modules.workspace.workspace.department.repository.DepartmentRepository;
import org.SprintForge.modules.workspace.workspace.team.entity.Team;
import org.SprintForge.modules.workspace.workspace.team.repository.TeamRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final TeamRepository teamRepository;
    private final DepartmentMapper departmentMapper;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional
    public DepartmentResponse createDepartment(CreateDepartmentRequest request, Long actorId) {
        if (departmentRepository.existsByWorkspaceIdAndNameAndIsDeletedFalse(request.getWorkspaceId(), request.getName())) {
            throw new BusinessRuleException("Department name already exists in this workspace: " + request.getName());
        }

        Department department = departmentMapper.toEntity(request);
        department = departmentRepository.save(department);

        eventPublisher.publishEvent(new DepartmentCreatedEvent(department.getId(), department.getWorkspaceId(), actorId, LocalDateTime.now()));

        return mapToResponse(department);
    }

    @Override
    @Transactional
    public DepartmentResponse updateDepartment(Long deptId, UpdateDepartmentRequest request, Long actorId) {
        Department department = getActiveDepartment(deptId);

        if (department.isArchived()) {
            throw new BusinessRuleException("Cannot modify an archived department.");
        }

        if (request.getName() != null && !request.getName().equals(department.getName())) {
            if (departmentRepository.existsByWorkspaceIdAndNameAndIsDeletedFalse(department.getWorkspaceId(), request.getName())) {
                throw new BusinessRuleException("Department name already exists in this workspace: " + request.getName());
            }
        }

        departmentMapper.updateEntity(request, department);
        department = departmentRepository.save(department);

        return mapToResponse(department);
    }

    @Override
    @Transactional
    public void archiveDepartment(Long deptId, Long actorId) {
        Department department = getActiveDepartment(deptId);
        department.setArchived(true);
        departmentRepository.save(department);
    }

    @Override
    @Transactional
    public void restoreDepartment(Long deptId, Long actorId) {
        Department department = departmentRepository.findById(deptId)
                .filter(d -> !d.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Department not found: " + deptId));

        if (!department.isArchived()) {
            throw new BusinessRuleException("Department is not archived.");
        }

        department.setArchived(false);
        departmentRepository.save(department);
    }

    @Override
    @Transactional
    public DepartmentResponse assignHead(Long deptId, Long headMemberId, Long actorId) {
        Department department = getActiveDepartment(deptId);
        if (department.isArchived()) {
            throw new BusinessRuleException("Cannot modify an archived department.");
        }

        department.setHeadMemberId(headMemberId);
        department = departmentRepository.save(department);

        return mapToResponse(department);
    }

    @Override
    @Transactional
    public void moveTeamToDepartment(Long teamId, Long deptId, Long actorId) {
        Department department = getActiveDepartment(deptId);
        if (department.isArchived()) {
            throw new BusinessRuleException("Cannot modify an archived department.");
        }

        Team team = teamRepository.findById(teamId)
                .filter(t -> !t.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Team not found: " + teamId));
                
        team.setDepartmentId(deptId);
        teamRepository.save(team);
    }

    @Override
    @Transactional(readOnly = true)
    public DepartmentResponse getDepartment(Long deptId) {
        return mapToResponse(getActiveDepartment(deptId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<DepartmentResponse> listByWorkspace(Long workspaceId) {
        return departmentRepository.findByWorkspaceIdAndIsDeletedFalse(workspaceId).stream()
                .filter(d -> !d.isArchived())
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<DepartmentSummaryResponse> listSummaries(Long workspaceId) {
        return departmentRepository.findByWorkspaceIdAndIsDeletedFalse(workspaceId).stream()
                .filter(d -> !d.isArchived())
                .map(department -> {
                    DepartmentSummaryResponse response = departmentMapper.toSummaryResponse(department);
                    response.setTeamCount(teamRepository.countByDepartmentIdAndIsDeletedFalse(department.getId()));
                    return response;
                })
                .collect(Collectors.toList());
    }

    private Department getActiveDepartment(Long deptId) {
        return departmentRepository.findById(deptId)
                .filter(d -> !d.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Department not found: " + deptId));
    }

    private DepartmentResponse mapToResponse(Department department) {
        DepartmentResponse response = departmentMapper.toResponse(department);
        response.setTeamCount(teamRepository.countByDepartmentIdAndIsDeletedFalse(department.getId()));
        // Note: member count needs aggregation, omitted here for brevity or set to 0.
        return response;
    }
}
