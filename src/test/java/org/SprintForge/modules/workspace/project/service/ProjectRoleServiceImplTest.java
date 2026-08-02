package org.SprintForge.modules.workspace.project.service;

import org.SprintForge.common.exception.ConflictException;
import org.SprintForge.modules.workspace.project.dto.request.CreateProjectRoleRequest;
import org.SprintForge.modules.workspace.project.dto.response.ProjectRoleResponse;
import org.SprintForge.modules.workspace.project.entity.ProjectRole;
import org.SprintForge.modules.workspace.project.repository.ProjectMemberRepository;
import org.SprintForge.modules.workspace.project.repository.ProjectRoleRepository;
import org.SprintForge.modules.workspace.project.service.role.ProjectRoleServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProjectRoleServiceImplTest {

    @Mock
    private ProjectRoleRepository roleRepository;

    @Mock
    private ProjectMemberRepository memberRepository;

    @InjectMocks
    private ProjectRoleServiceImpl roleService;

    private ProjectRole role;

    @BeforeEach
    void setUp() {
        role = new ProjectRole();
        role.setProjectId(1L);
        role.setName("QA Lead");
        role.setDescription("Quality Assurance Lead");
        role.setColor("#33CC33");
        role.setPermissions("TASK_MANAGE,PROJECT_VIEW");
    }

    @Test
    void createRole_Success() {
        CreateProjectRoleRequest request = CreateProjectRoleRequest.builder()
                .name("QA Lead")
                .description("Quality Assurance Lead")
                .color("#33CC33")
                .permissions(Set.of("TASK_MANAGE", "PROJECT_VIEW"))
                .build();

        when(roleRepository.existsByProjectIdAndNameAndIsDeletedFalse(1L, "QA Lead")).thenReturn(false);
        when(roleRepository.save(any(ProjectRole.class))).thenReturn(role);

        ProjectRoleResponse response = roleService.createRole(1L, request, 10L);

        assertNotNull(response);
        assertEquals("QA Lead", response.getName());
    }

    @Test
    void createRole_Conflict_ThrowsException() {
        CreateProjectRoleRequest request = CreateProjectRoleRequest.builder()
                .name("QA Lead")
                .build();

        when(roleRepository.existsByProjectIdAndNameAndIsDeletedFalse(1L, "QA Lead")).thenReturn(true);

        assertThrows(ConflictException.class, () -> roleService.createRole(1L, request, 10L));
    }

    @Test
    void cloneRole_Success() {
        when(roleRepository.findById(100L)).thenReturn(Optional.of(role));

        ProjectRole clonedRole = new ProjectRole();
        clonedRole.setProjectId(1L);
        clonedRole.setName("Copy of QA Lead");
        clonedRole.setPermissions("TASK_MANAGE,PROJECT_VIEW");

        when(roleRepository.existsByProjectIdAndNameAndIsDeletedFalse(1L, "Copy of QA Lead")).thenReturn(false);
        when(roleRepository.save(any(ProjectRole.class))).thenReturn(clonedRole);

        ProjectRoleResponse response = roleService.cloneRole(100L, null, 10L);

        assertNotNull(response);
        assertEquals("Copy of QA Lead", response.getName());
    }
}
