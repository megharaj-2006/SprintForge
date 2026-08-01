package org.SprintForge.modules.workspace.customfield.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.SprintForge.common.exception.BusinessRuleException;
import org.SprintForge.common.exception.ForbiddenException;
import org.SprintForge.common.exception.ResourceNotFoundException;
import org.SprintForge.common.exception.ValidationException;
import org.SprintForge.modules.user.repository.UserRepository;
import org.SprintForge.modules.workspace.customfield.dto.request.AssignCustomFieldRequest;
import org.SprintForge.modules.workspace.customfield.dto.request.UpdateCustomFieldValueRequest;
import org.SprintForge.modules.workspace.customfield.dto.response.TaskCustomFieldResponse;
import org.SprintForge.modules.workspace.customfield.entity.CustomField;
import org.SprintForge.modules.workspace.customfield.entity.CustomFieldValue;
import org.SprintForge.modules.workspace.customfield.event.CustomFieldValueAssignedEvent;
import org.SprintForge.modules.workspace.customfield.event.CustomFieldValueUpdatedEvent;
import org.SprintForge.modules.workspace.customfield.repository.CustomFieldRepository;
import org.SprintForge.modules.workspace.customfield.repository.CustomFieldValueRepository;
import org.SprintForge.modules.workspace.project.service.member.ProjectPermissionService;
import org.SprintForge.modules.workspace.task.entity.Task;
import org.SprintForge.modules.workspace.task.repository.TaskRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskCustomFieldServiceImpl implements TaskCustomFieldService {

    private final TaskRepository taskRepository;
    private final CustomFieldRepository customFieldRepository;
    private final CustomFieldValueRepository customFieldValueRepository;
    private final UserRepository userRepository;
    
    private final ProjectPermissionService projectPermissionService;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional
    public TaskCustomFieldResponse assignValue(Long taskId, AssignCustomFieldRequest request, Long actorId) {
        log.info("Assigning custom field value: taskId: {} fieldId: {} by actor: {}", taskId, request.getCustomFieldId(), actorId);
        
        Task task = taskRepository.findById(taskId)
                .filter(t -> !t.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Task not found."));

        if (!projectPermissionService.canManageTasks(task.getProject().getId(), actorId)) {
            throw new ForbiddenException("Access Denied: You do not have permission to manage tasks in this project.");
        }

        CustomField field = customFieldRepository.findById(request.getCustomFieldId())
                .filter(f -> !f.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Custom field not found."));

        if (Boolean.TRUE.equals(field.getArchived())) {
            throw new BusinessRuleException("Archived fields cannot receive new values.");
        }

        validateValue(field, request.getValue());

        Optional<CustomFieldValue> existingVal = customFieldValueRepository
                .findByTaskIdAndCustomFieldIdAndIsDeletedFalse(taskId, field.getId());

        CustomFieldValue valRecord;
        boolean isNew = existingVal.isEmpty();
        if (isNew) {
            valRecord = new CustomFieldValue();
            valRecord.setTask(task);
            valRecord.setCustomField(field);
            valRecord.setCreatedBy(actorId.toString());
        } else {
            valRecord = existingVal.get();
            valRecord.setUpdatedBy(actorId.toString());
        }

        valRecord.setValue(request.getValue());
        CustomFieldValue saved = customFieldValueRepository.save(valRecord);

        if (isNew) {
            eventPublisher.publishEvent(new CustomFieldValueAssignedEvent(saved.getId(), taskId, field.getId(), actorId, LocalDateTime.now()));
        } else {
            eventPublisher.publishEvent(new CustomFieldValueUpdatedEvent(saved.getId(), taskId, field.getId(), actorId, LocalDateTime.now()));
        }

        return mapToResponse(field, saved);
    }

    @Override
    @Transactional
    public TaskCustomFieldResponse updateValue(Long taskId, Long fieldId, UpdateCustomFieldValueRequest request, Long actorId) {
        log.info("Updating custom field value: taskId: {} fieldId: {} by actor: {}", taskId, fieldId, actorId);
        
        Task task = taskRepository.findById(taskId)
                .filter(t -> !t.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Task not found."));

        if (!projectPermissionService.canManageTasks(task.getProject().getId(), actorId)) {
            throw new ForbiddenException("Access Denied: You do not have permission to manage tasks in this project.");
        }

        CustomField field = customFieldRepository.findById(fieldId)
                .filter(f -> !f.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Custom field not found."));

        if (Boolean.TRUE.equals(field.getArchived())) {
            throw new BusinessRuleException("Archived fields cannot receive new values.");
        }

        validateValue(field, request.getValue());

        CustomFieldValue valRecord = customFieldValueRepository
                .findByTaskIdAndCustomFieldIdAndIsDeletedFalse(taskId, fieldId)
                .orElseThrow(() -> new ResourceNotFoundException("No assigned custom field value found."));

        valRecord.setValue(request.getValue());
        valRecord.setUpdatedBy(actorId.toString());
        CustomFieldValue saved = customFieldValueRepository.save(valRecord);

        eventPublisher.publishEvent(new CustomFieldValueUpdatedEvent(saved.getId(), taskId, fieldId, actorId, LocalDateTime.now()));

        return mapToResponse(field, saved);
    }

    @Override
    @Transactional
    public void removeValue(Long taskId, Long fieldId, Long actorId) {
        log.info("Removing custom field value: taskId: {} fieldId: {} by actor: {}", taskId, fieldId, actorId);
        
        Task task = taskRepository.findById(taskId)
                .filter(t -> !t.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Task not found."));

        if (!projectPermissionService.canManageTasks(task.getProject().getId(), actorId)) {
            throw new ForbiddenException("Access Denied: You do not have permission to manage tasks.");
        }

        CustomFieldValue valRecord = customFieldValueRepository
                .findByTaskIdAndCustomFieldIdAndIsDeletedFalse(taskId, fieldId)
                .orElseThrow(() -> new ResourceNotFoundException("Assigned value not found."));

        valRecord.markDeleted(actorId.toString());
        customFieldValueRepository.save(valRecord);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskCustomFieldResponse> getTaskFields(Long taskId, Long actorId) {
        log.info("Retrieving custom fields and values for task: {} by actor: {}", taskId, actorId);
        
        Task task = taskRepository.findById(taskId)
                .filter(t -> !t.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Task not found."));

        if (!projectPermissionService.canViewProject(task.getProject().getId(), actorId)) {
            throw new ForbiddenException("Access Denied: You do not have permission to view this project.");
        }

        List<CustomField> fields = customFieldRepository.findByProjectIdAndIsDeletedFalse(task.getProject().getId());
        List<CustomFieldValue> values = customFieldValueRepository.findByTaskIdAndIsDeletedFalse(taskId);

        return fields.stream()
                .map(field -> {
                    CustomFieldValue val = values.stream()
                            .filter(v -> v.getCustomField().getId().equals(field.getId()))
                            .findFirst()
                            .orElse(null);
                    return TaskCustomFieldResponse.builder()
                            .fieldId(field.getId())
                            .fieldName(field.getName())
                            .fieldType(field.getFieldType())
                            .required(field.getRequired())
                            .defaultValue(field.getDefaultValue())
                            .options(field.getOptions())
                            .validationRules(field.getValidationRules())
                            .valueId(val != null ? val.getId() : null)
                            .value(val != null ? val.getValue() : null)
                            .build();
                })
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public void validateValues(Long taskId, List<AssignCustomFieldRequest> requests) {
        log.info("Validating custom field values for task: {}", taskId);
        
        Task task = taskRepository.findById(taskId)
                .filter(t -> !t.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Task not found."));

        List<CustomField> fields = customFieldRepository.findByProjectIdAndIsDeletedFalse(task.getProject().getId());
        List<CustomFieldValue> existingValues = customFieldValueRepository.findByTaskIdAndIsDeletedFalse(taskId);

        for (CustomField field : fields) {
            AssignCustomFieldRequest req = requests.stream()
                    .filter(r -> r.getCustomFieldId().equals(field.getId()))
                    .findFirst()
                    .orElse(null);

            String newValue = null;
            boolean hasNewValue = false;
            if (req != null) {
                newValue = req.getValue();
                hasNewValue = true;
            }

            if (!hasNewValue) {
                CustomFieldValue existing = existingValues.stream()
                        .filter(v -> v.getCustomField().getId().equals(field.getId()))
                        .findFirst()
                        .orElse(null);
                if (existing != null) {
                    newValue = existing.getValue();
                }
            }

            validateValue(field, newValue);
        }
    }

    private void validateValue(CustomField field, String value) {
        if (Boolean.TRUE.equals(field.getRequired()) && (value == null || value.trim().isEmpty())) {
            throw new ValidationException("Required field '" + field.getName() + "' must be populated.");
        }

        if (value == null || value.trim().isEmpty()) {
            return;
        }

        String trimmed = value.trim();

        switch (field.getFieldType()) {
            case TEXT:
                if (trimmed.length() > 255) {
                    throw new ValidationException("Field '" + field.getName() + "' value must not exceed 255 characters.");
                }
                break;
            case TEXTAREA:
                break;
            case NUMBER:
                try {
                    Double.parseDouble(trimmed);
                } catch (NumberFormatException e) {
                    throw new ValidationException("Field '" + field.getName() + "' must be a valid number.");
                }
                break;
            case BOOLEAN:
                if (!"true".equalsIgnoreCase(trimmed) && !"false".equalsIgnoreCase(trimmed)) {
                    throw new ValidationException("Field '" + field.getName() + "' must be a boolean ('true' or 'false').");
                }
                break;
            case DATE:
                try {
                    LocalDate.parse(trimmed);
                } catch (Exception e) {
                    throw new ValidationException("Field '" + field.getName() + "' must be a valid date in YYYY-MM-DD format.");
                }
                break;
            case DATETIME:
                try {
                    LocalDateTime.parse(trimmed);
                } catch (Exception e) {
                    try {
                        LocalDate.parse(trimmed);
                    } catch (Exception ex) {
                        throw new ValidationException("Field '" + field.getName() + "' must be a valid date-time in ISO format.");
                    }
                }
                break;
            case EMAIL:
                if (!trimmed.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
                    throw new ValidationException("Field '" + field.getName() + "' must be a valid email address.");
                }
                break;
            case URL:
                try {
                    new URI(trimmed).toURL();
                } catch (Exception e) {
                    throw new ValidationException("Field '" + field.getName() + "' must be a valid URL.");
                }
                break;
            case SELECT:
                if (field.getOptions() != null && !field.getOptions().isBlank()) {
                    List<String> allowedOptions = Arrays.stream(field.getOptions().split(","))
                            .map(String::trim)
                            .toList();
                    if (!allowedOptions.contains(trimmed)) {
                        throw new ValidationException("Field '" + field.getName() + "' must be one of the configured options: " + field.getOptions());
                    }
                }
                break;
            case MULTI_SELECT:
                if (field.getOptions() != null && !field.getOptions().isBlank()) {
                    List<String> allowedOptions = Arrays.stream(field.getOptions().split(","))
                            .map(String::trim)
                            .toList();
                    List<String> inputOptions = Arrays.stream(trimmed.split(","))
                            .map(String::trim)
                            .toList();
                    for (String opt : inputOptions) {
                        if (!allowedOptions.contains(opt)) {
                            throw new ValidationException("Field '" + field.getName() + "' value '" + opt + "' is not a configured option.");
                        }
                    }
                }
                break;
            case USER:
                try {
                    Long userId = Long.parseLong(trimmed);
                    if (!userRepository.existsById(userId)) {
                        throw new ValidationException("User with ID " + userId + " does not exist.");
                    }
                } catch (NumberFormatException e) {
                    throw new ValidationException("Field '" + field.getName() + "' must be a valid numeric user ID.");
                }
                break;
        }
    }

    private TaskCustomFieldResponse mapToResponse(CustomField field, CustomFieldValue val) {
        return TaskCustomFieldResponse.builder()
                .fieldId(field.getId())
                .fieldName(field.getName())
                .fieldType(field.getFieldType())
                .required(field.getRequired())
                .defaultValue(field.getDefaultValue())
                .options(field.getOptions())
                .validationRules(field.getValidationRules())
                .valueId(val != null ? val.getId() : null)
                .value(val != null ? val.getValue() : null)
                .build();
    }
}
