package org.SprintForge.modules.workspace.customfield.service;

import org.SprintForge.common.exception.BusinessRuleException;
import org.SprintForge.common.exception.ForbiddenException;
import org.SprintForge.common.exception.ResourceNotFoundException;
import org.SprintForge.common.exception.ValidationException;
import org.SprintForge.modules.user.repository.UserRepository;
import org.SprintForge.modules.workspace.customfield.dto.request.AssignCustomFieldRequest;
import org.SprintForge.modules.workspace.customfield.dto.response.TaskCustomFieldResponse;
import org.SprintForge.modules.workspace.customfield.entity.CustomField;
import org.SprintForge.modules.workspace.customfield.entity.CustomFieldValue;
import org.SprintForge.modules.workspace.customfield.entity.enums.CustomFieldType;
import org.SprintForge.modules.workspace.customfield.event.CustomFieldValueAssignedEvent;
import org.SprintForge.modules.workspace.customfield.repository.CustomFieldRepository;
import org.SprintForge.modules.workspace.customfield.repository.CustomFieldValueRepository;
import org.SprintForge.modules.workspace.project.entity.Project;
import org.SprintForge.modules.workspace.project.service.member.ProjectPermissionService;
import org.SprintForge.modules.workspace.task.entity.Task;
import org.SprintForge.modules.workspace.task.repository.TaskRepository;
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
class TaskCustomFieldServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private CustomFieldRepository customFieldRepository;

    @Mock
    private CustomFieldValueRepository customFieldValueRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProjectPermissionService projectPermissionService;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @InjectMocks
    private TaskCustomFieldServiceImpl taskCustomFieldService;

    private Task task;
    private Project project;
    private CustomField customField;
    private AssignCustomFieldRequest assignRequest;

    @BeforeEach
    void setUp() {
        project = new Project();
        project.setId(10L);

        task = new Task();
        task.setId(500L);
        task.setProject(project);

        customField = new CustomField();
        customField.setId(100L);
        customField.setName("Estimation Cost");
        customField.setFieldType(CustomFieldType.NUMBER);
        customField.setRequired(false);
        customField.setProject(project);

        assignRequest = AssignCustomFieldRequest.builder()
                .customFieldId(100L)
                .value("150.50")
                .build();
    }

    @Test
    void assignValue_Success() {
        when(taskRepository.findById(500L)).thenReturn(Optional.of(task));
        when(projectPermissionService.canManageTasks(10L, 1L)).thenReturn(true);
        when(customFieldRepository.findById(100L)).thenReturn(Optional.of(customField));
        when(customFieldValueRepository.findByTaskIdAndCustomFieldIdAndIsDeletedFalse(500L, 100L))
                .thenReturn(Optional.empty());
        when(customFieldValueRepository.save(any(CustomFieldValue.class))).thenAnswer(invocation -> invocation.getArgument(0));

        TaskCustomFieldResponse result = taskCustomFieldService.assignValue(500L, assignRequest, 1L);

        assertNotNull(result);
        assertEquals("150.50", result.getValue());
        verify(eventPublisher, times(1)).publishEvent(any(CustomFieldValueAssignedEvent.class));
    }

    @Test
    void assignValue_ValidationFailure_InvalidNumber() {
        assignRequest.setValue("abc");

        when(taskRepository.findById(500L)).thenReturn(Optional.of(task));
        when(projectPermissionService.canManageTasks(10L, 1L)).thenReturn(true);
        when(customFieldRepository.findById(100L)).thenReturn(Optional.of(customField));

        assertThrows(ValidationException.class, () -> 
                taskCustomFieldService.assignValue(500L, assignRequest, 1L));
    }

    @Test
    void assignValue_ArchivedField() {
        customField.setArchived(true);

        when(taskRepository.findById(500L)).thenReturn(Optional.of(task));
        when(projectPermissionService.canManageTasks(10L, 1L)).thenReturn(true);
        when(customFieldRepository.findById(100L)).thenReturn(Optional.of(customField));

        assertThrows(BusinessRuleException.class, () -> 
                taskCustomFieldService.assignValue(500L, assignRequest, 1L));
    }

    @Test
    void assignValue_ValidationEmail_Success() {
        customField.setFieldType(CustomFieldType.EMAIL);
        assignRequest.setValue("test@example.com");

        when(taskRepository.findById(500L)).thenReturn(Optional.of(task));
        when(projectPermissionService.canManageTasks(10L, 1L)).thenReturn(true);
        when(customFieldRepository.findById(100L)).thenReturn(Optional.of(customField));
        when(customFieldValueRepository.findByTaskIdAndCustomFieldIdAndIsDeletedFalse(500L, 100L))
                .thenReturn(Optional.empty());
        when(customFieldValueRepository.save(any(CustomFieldValue.class))).thenAnswer(invocation -> invocation.getArgument(0));

        TaskCustomFieldResponse result = taskCustomFieldService.assignValue(500L, assignRequest, 1L);
        assertNotNull(result);
        assertEquals("test@example.com", result.getValue());
    }

    @Test
    void assignValue_ValidationEmail_Failure() {
        customField.setFieldType(CustomFieldType.EMAIL);
        assignRequest.setValue("invalid-email");

        when(taskRepository.findById(500L)).thenReturn(Optional.of(task));
        when(projectPermissionService.canManageTasks(10L, 1L)).thenReturn(true);
        when(customFieldRepository.findById(100L)).thenReturn(Optional.of(customField));

        assertThrows(ValidationException.class, () -> 
                taskCustomFieldService.assignValue(500L, assignRequest, 1L));
    }
}
