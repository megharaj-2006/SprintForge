package org.SprintForge.modules.workspace.customfield.service;

import org.SprintForge.common.exception.BusinessRuleException;
import org.SprintForge.common.exception.ConflictException;
import org.SprintForge.common.exception.ForbiddenException;
import org.SprintForge.common.exception.ResourceNotFoundException;
import org.SprintForge.modules.workspace.customfield.dto.request.CreateCustomFieldRequest;
import org.SprintForge.modules.workspace.customfield.dto.request.UpdateCustomFieldRequest;
import org.SprintForge.modules.workspace.customfield.dto.response.CustomFieldResponse;
import org.SprintForge.modules.workspace.customfield.entity.CustomField;
import org.SprintForge.modules.workspace.customfield.entity.CustomFieldValue;
import org.SprintForge.modules.workspace.customfield.entity.enums.CustomFieldType;
import org.SprintForge.modules.workspace.customfield.event.CustomFieldCreatedEvent;
import org.SprintForge.modules.workspace.customfield.event.CustomFieldDeletedEvent;
import org.SprintForge.modules.workspace.customfield.event.CustomFieldUpdatedEvent;
import org.SprintForge.modules.workspace.customfield.mapper.CustomFieldMapper;
import org.SprintForge.modules.workspace.customfield.repository.CustomFieldRepository;
import org.SprintForge.modules.workspace.customfield.repository.CustomFieldValueRepository;
import org.SprintForge.modules.workspace.project.entity.Project;
import org.SprintForge.modules.workspace.project.entity.ProjectSettings;
import org.SprintForge.modules.workspace.project.repository.ProjectMemberRepository;
import org.SprintForge.modules.workspace.project.repository.ProjectRepository;
import org.SprintForge.modules.workspace.project.repository.ProjectRoleRepository;
import org.SprintForge.modules.workspace.project.repository.ProjectSettingsRepository;
import org.SprintForge.modules.workspace.project.service.member.ProjectPermissionService;
import org.SprintForge.modules.workspace.workspace.repository.WorkspaceMemberRepository;
import org.SprintForge.modules.workspace.workspace.service.WorkspacePermissionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomFieldManagementServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private ProjectSettingsRepository projectSettingsRepository;

    @Mock
    private ProjectMemberRepository projectMemberRepository;

    @Mock
    private ProjectRoleRepository projectRoleRepository;

    @Mock
    private WorkspaceMemberRepository workspaceMemberRepository;

    @Mock
    private CustomFieldRepository customFieldRepository;

    @Mock
    private CustomFieldValueRepository customFieldValueRepository;

    @Mock
    private CustomFieldMapper customFieldMapper;

    @Mock
    private WorkspacePermissionService workspacePermissionService;

    @Mock
    private ProjectPermissionService projectPermissionService;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @InjectMocks
    private CustomFieldManagementServiceImpl customFieldManagementService;

    private Project project;
    private CreateCustomFieldRequest createRequest;
    private CustomField customField;
    private CustomFieldResponse responseDto;

    @BeforeEach
    void setUp() {
        project = new Project();
        project.setId(10L);
        project.setWorkspaceId(20L);
        project.setOwnerId(1L);

        createRequest = CreateCustomFieldRequest.builder()
                .name("Story Points")
                .fieldType(CustomFieldType.NUMBER)
                .required(false)
                .defaultValue("1")
                .build();

        customField = new CustomField();
        customField.setId(100L);
        customField.setName("Story Points");
        customField.setProject(project);
        customField.setFieldType(CustomFieldType.NUMBER);
        customField.setRequired(false);

        responseDto = new CustomFieldResponse();
        responseDto.setId(100L);
        responseDto.setProjectId(10L);
        responseDto.setName("Story Points");
        responseDto.setFieldType(CustomFieldType.NUMBER);
    }

    @Test
    void createField_Success() {
        when(projectRepository.findById(10L)).thenReturn(Optional.of(project));
        when(customFieldRepository.existsByProjectIdAndNameAndIsDeletedFalse(10L, "Story Points")).thenReturn(false);
        when(customFieldMapper.toEntity(createRequest)).thenReturn(customField);
        when(customFieldRepository.save(any(CustomField.class))).thenReturn(customField);
        when(customFieldMapper.toResponse(customField)).thenReturn(responseDto);

        CustomFieldResponse result = customFieldManagementService.createField(10L, createRequest, 1L);

        assertNotNull(result);
        assertEquals(100L, result.getId());
        verify(eventPublisher, times(1)).publishEvent(any(CustomFieldCreatedEvent.class));
    }

    @Test
    void createField_Conflict_DuplicateName() {
        when(projectRepository.findById(10L)).thenReturn(Optional.of(project));
        when(customFieldRepository.existsByProjectIdAndNameAndIsDeletedFalse(10L, "Story Points")).thenReturn(true);

        assertThrows(ConflictException.class, () -> 
                customFieldManagementService.createField(10L, createRequest, 1L));
    }

    @Test
    void createField_DisabledSettings() {
        ProjectSettings settings = new ProjectSettings();
        settings.setAllowCustomFields(false);

        when(projectRepository.findById(10L)).thenReturn(Optional.of(project));
        when(projectSettingsRepository.findByProjectIdAndIsDeletedFalse(10L)).thenReturn(Optional.of(settings));

        assertThrows(BusinessRuleException.class, () -> 
                customFieldManagementService.createField(10L, createRequest, 1L));
    }

    @Test
    void deleteField_Success() {
        customField.setRequired(false);
        when(customFieldRepository.findById(100L)).thenReturn(Optional.of(customField));

        customFieldManagementService.deleteField(100L, 1L);

        assertTrue(customField.isDeleted());
        verify(customFieldValueRepository, times(1)).deleteByCustomFieldId(100L, "1");
        verify(eventPublisher, times(1)).publishEvent(any(CustomFieldDeletedEvent.class));
    }

    @Test
    void deleteField_Required_InUse() {
        customField.setRequired(true);
        when(customFieldRepository.findById(100L)).thenReturn(Optional.of(customField));
        when(customFieldValueRepository.findByCustomFieldIdAndIsDeletedFalse(100L))
                .thenReturn(List.of(new CustomFieldValue()));

        assertThrows(BusinessRuleException.class, () -> 
                customFieldManagementService.deleteField(100L, 1L));
    }
}
