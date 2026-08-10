package org.SprintForge.modules.workspace.workspace.department.service;

import org.SprintForge.modules.workspace.workspace.department.dto.request.CreateDepartmentRequest;
import org.SprintForge.modules.workspace.workspace.department.dto.request.UpdateDepartmentRequest;
import org.SprintForge.modules.workspace.workspace.department.dto.response.DepartmentResponse;
import org.SprintForge.modules.workspace.workspace.department.dto.response.DepartmentSummaryResponse;

import java.util.List;

public interface DepartmentService {
    DepartmentResponse createDepartment(CreateDepartmentRequest request, Long actorId);
    DepartmentResponse updateDepartment(Long deptId, UpdateDepartmentRequest request, Long actorId);
    void archiveDepartment(Long deptId, Long actorId);
    void restoreDepartment(Long deptId, Long actorId);
    DepartmentResponse assignHead(Long deptId, Long headMemberId, Long actorId);
    void moveTeamToDepartment(Long teamId, Long deptId, Long actorId);
    DepartmentResponse getDepartment(Long deptId);
    List<DepartmentResponse> listByWorkspace(Long workspaceId);
    List<DepartmentSummaryResponse> listSummaries(Long workspaceId);
}
