package org.SprintForge.modules.workspace.task.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.SprintForge.modules.user.entity.User;
import org.SprintForge.modules.user.repository.UserRepository;
import org.SprintForge.modules.workspace.attachment.entity.Attachment;
import org.SprintForge.modules.workspace.attachment.event.AttachmentDeletedEvent;
import org.SprintForge.modules.workspace.attachment.event.AttachmentUploadedEvent;
import org.SprintForge.modules.workspace.attachment.repository.AttachmentRepository;
import org.SprintForge.modules.workspace.comment.entity.TaskComment;
import org.SprintForge.modules.workspace.comment.event.CommentCreatedEvent;
import org.SprintForge.modules.workspace.comment.event.CommentDeletedEvent;
import org.SprintForge.modules.workspace.comment.event.CommentUpdatedEvent;
import org.SprintForge.modules.workspace.comment.repository.TaskCommentRepository;
import org.SprintForge.modules.workspace.customfield.entity.CustomField;
import org.SprintForge.modules.workspace.customfield.event.CustomFieldValueAssignedEvent;
import org.SprintForge.modules.workspace.customfield.event.CustomFieldValueUpdatedEvent;
import org.SprintForge.modules.workspace.customfield.repository.CustomFieldRepository;
import org.SprintForge.modules.workspace.project.entity.ProjectMember;
import org.SprintForge.modules.workspace.workspace.entity.WorkspaceMember;
import org.SprintForge.modules.workspace.project.repository.ProjectMemberRepository;
import org.SprintForge.modules.workspace.workspace.repository.WorkspaceMemberRepository;
import org.SprintForge.modules.workspace.task.entity.Checklist;
import org.SprintForge.modules.workspace.task.entity.ChecklistItem;
import org.SprintForge.modules.workspace.task.entity.Label;
import org.SprintForge.modules.workspace.task.entity.enums.TaskHistoryActionType;
import org.SprintForge.modules.workspace.task.event.*;
import org.SprintForge.modules.workspace.task.repository.ChecklistItemRepository;
import org.SprintForge.modules.workspace.task.repository.ChecklistRepository;
import org.SprintForge.modules.workspace.task.repository.LabelRepository;
import org.SprintForge.modules.workspace.task.service.TaskHistoryService;
import org.SprintForge.modules.workspace.timelog.event.TimeEntryCreatedEvent;
import org.SprintForge.modules.workspace.timelog.event.TimeEntryDeletedEvent;
import org.SprintForge.modules.workspace.timelog.event.TimeEntryUpdatedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TaskHistoryListener {

    private final TaskHistoryService taskHistoryService;
    private final TaskCommentRepository taskCommentRepository;
    private final AttachmentRepository attachmentRepository;
    private final LabelRepository labelRepository;
    private final ProjectMemberRepository projectMemberRepository;
    private final WorkspaceMemberRepository workspaceMemberRepository;
    private final UserRepository userRepository;
    private final ChecklistRepository checklistRepository;
    private final ChecklistItemRepository checklistItemRepository;
    private final CustomFieldRepository customFieldRepository;

    @EventListener
    public void handleTaskCreated(TaskCreatedEvent event) {
        try {
            taskHistoryService.recordHistory(
                    event.taskId(),
                    event.actorId(),
                    TaskHistoryActionType.TASK_CREATED,
                    null, null, null,
                    "Task was created."
            );
        } catch (Exception e) {
            log.warn("Failed to log TaskCreatedEvent for task ID: {}", event.taskId(), e);
        }
    }

    @EventListener
    public void handleTaskUpdated(TaskUpdatedEvent event) {
        try {
            taskHistoryService.recordHistory(
                    event.taskId(),
                    event.actorId(),
                    TaskHistoryActionType.TASK_UPDATED,
                    null, null, null,
                    "Task details were updated."
            );
        } catch (Exception e) {
            log.warn("Failed to log TaskUpdatedEvent for task ID: {}", event.taskId(), e);
        }
    }

    @EventListener
    public void handleTaskDeleted(TaskDeletedEvent event) {
        try {
            taskHistoryService.recordHistory(
                    event.taskId(),
                    event.actorId(),
                    TaskHistoryActionType.TASK_UPDATED,
                    "isDeleted", "false", "true",
                    "Task was deleted."
            );
        } catch (Exception e) {
            log.warn("Failed to log TaskDeletedEvent for task ID: {}", event.taskId(), e);
        }
    }

    @EventListener
    public void handleTaskArchived(TaskArchivedEvent event) {
        try {
            taskHistoryService.recordHistory(
                    event.taskId(),
                    event.actorId(),
                    TaskHistoryActionType.ARCHIVED,
                    "archived", "false", "true",
                    "Task was archived."
            );
        } catch (Exception e) {
            log.warn("Failed to log TaskArchivedEvent for task ID: {}", event.taskId(), e);
        }
    }

    @EventListener
    public void handleTaskRestored(TaskRestoredEvent event) {
        try {
            taskHistoryService.recordHistory(
                    event.taskId(),
                    event.actorId(),
                    TaskHistoryActionType.RESTORED,
                    "archived", "true", "false",
                    "Task was restored from archive."
            );
        } catch (Exception e) {
            log.warn("Failed to log TaskRestoredEvent for task ID: {}", event.taskId(), e);
        }
    }

    @EventListener
    public void handleTaskStatusChanged(TaskStatusChangedEvent event) {
        try {
            taskHistoryService.recordHistory(
                    event.taskId(),
                    event.actorId(),
                    TaskHistoryActionType.STATUS_CHANGED,
                    "status",
                    event.oldStatus() != null ? event.oldStatus().name() : null,
                    event.newStatus() != null ? event.newStatus().name() : null,
                    "Status changed from " + event.oldStatus() + " to " + event.newStatus() + "."
            );
        } catch (Exception e) {
            log.warn("Failed to log TaskStatusChangedEvent for task ID: {}", event.taskId(), e);
        }
    }

    @EventListener
    public void handleTaskPriorityChanged(TaskPriorityChangedEvent event) {
        try {
            taskHistoryService.recordHistory(
                    event.taskId(),
                    event.actorId(),
                    TaskHistoryActionType.PRIORITY_CHANGED,
                    "priority",
                    event.oldPriority() != null ? event.oldPriority().name() : null,
                    event.newPriority() != null ? event.newPriority().name() : null,
                    "Priority changed from " + event.oldPriority() + " to " + event.newPriority() + "."
            );
        } catch (Exception e) {
            log.warn("Failed to log TaskPriorityChangedEvent for task ID: {}", event.taskId(), e);
        }
    }

    @EventListener
    public void handleTaskAssigned(TaskAssignedEvent event) {
        try {
            String name = getMemberName(event.projectMemberId());
            taskHistoryService.recordHistory(
                    event.taskId(),
                    event.actorId(),
                    TaskHistoryActionType.ASSIGNEE_CHANGED,
                    "assignee",
                    null,
                    event.projectMemberId() != null ? event.projectMemberId().toString() : null,
                    "Task was assigned to " + name + "."
            );
        } catch (Exception e) {
            log.warn("Failed to log TaskAssignedEvent for task ID: {}", event.taskId(), e);
        }
    }

    @EventListener
    public void handleTaskUnassigned(TaskUnassignedEvent event) {
        try {
            String name = getMemberName(event.projectMemberId());
            taskHistoryService.recordHistory(
                    event.taskId(),
                    event.actorId(),
                    TaskHistoryActionType.ASSIGNEE_CHANGED,
                    "assignee",
                    event.projectMemberId() != null ? event.projectMemberId().toString() : null,
                    null,
                    "Task was unassigned from " + name + "."
            );
        } catch (Exception e) {
            log.warn("Failed to log TaskUnassignedEvent for task ID: {}", event.taskId(), e);
        }
    }

    @EventListener
    public void handleLabelAssigned(LabelAssignedEvent event) {
        try {
            String labelName = getLabelName(event.labelId());
            taskHistoryService.recordHistory(
                    event.taskId(),
                    event.actorId(),
                    TaskHistoryActionType.LABEL_ADDED,
                    "label",
                    null,
                    event.labelId() != null ? event.labelId().toString() : null,
                    "Label '" + labelName + "' was added to the task."
            );
        } catch (Exception e) {
            log.warn("Failed to log LabelAssignedEvent for task ID: {}", event.taskId(), e);
        }
    }

    @EventListener
    public void handleLabelRemoved(LabelRemovedEvent event) {
        try {
            String labelName = getLabelName(event.labelId());
            taskHistoryService.recordHistory(
                    event.taskId(),
                    event.actorId(),
                    TaskHistoryActionType.LABEL_REMOVED,
                    "label",
                    event.labelId() != null ? event.labelId().toString() : null,
                    null,
                    "Label '" + labelName + "' was removed from the task."
            );
        } catch (Exception e) {
            log.warn("Failed to log LabelRemovedEvent for task ID: {}", event.taskId(), e);
        }
    }

    @EventListener
    public void handleCommentCreated(CommentCreatedEvent event) {
        try {
            TaskComment comment = taskCommentRepository.findById(event.commentId()).orElse(null);
            if (comment != null) {
                taskHistoryService.recordHistory(
                        comment.getTaskId(),
                        event.actorId(),
                        TaskHistoryActionType.COMMENT_CREATED,
                        "comment",
                        null,
                        comment.getContent(),
                        "A new comment was added."
                );
            }
        } catch (Exception e) {
            log.warn("Failed to log CommentCreatedEvent for comment ID: {}", event.commentId(), e);
        }
    }

    @EventListener
    public void handleCommentUpdated(CommentUpdatedEvent event) {
        try {
            TaskComment comment = taskCommentRepository.findById(event.commentId()).orElse(null);
            if (comment != null) {
                taskHistoryService.recordHistory(
                        comment.getTaskId(),
                        event.actorId(),
                        TaskHistoryActionType.COMMENT_UPDATED,
                        "comment",
                        null,
                        comment.getContent(),
                        "A comment was updated."
                );
            }
        } catch (Exception e) {
            log.warn("Failed to log CommentUpdatedEvent for comment ID: {}", event.commentId(), e);
        }
    }

    @EventListener
    public void handleCommentDeleted(CommentDeletedEvent event) {
        try {
            TaskComment comment = taskCommentRepository.findById(event.commentId()).orElse(null);
            if (comment != null) {
                taskHistoryService.recordHistory(
                        comment.getTaskId(),
                        event.actorId(),
                        TaskHistoryActionType.COMMENT_UPDATED,
                        "comment",
                        comment.getContent(),
                        null,
                        "A comment was deleted."
                );
            }
        } catch (Exception e) {
            log.warn("Failed to log CommentDeletedEvent for comment ID: {}", event.commentId(), e);
        }
    }

    @EventListener
    public void handleAttachmentUploaded(AttachmentUploadedEvent event) {
        try {
            Attachment attachment = attachmentRepository.findById(event.attachmentId()).orElse(null);
            if (attachment != null) {
                taskHistoryService.recordHistory(
                        attachment.getTaskId(),
                        event.actorId(),
                        TaskHistoryActionType.ATTACHMENT_UPLOADED,
                        "attachment",
                        null,
                        attachment.getOriginalFileName(),
                        "Attachment '" + attachment.getOriginalFileName() + "' was uploaded."
                );
            }
        } catch (Exception e) {
            log.warn("Failed to log AttachmentUploadedEvent for attachment ID: {}", event.attachmentId(), e);
        }
    }

    @EventListener
    public void handleAttachmentDeleted(AttachmentDeletedEvent event) {
        try {
            Attachment attachment = attachmentRepository.findById(event.attachmentId()).orElse(null);
            if (attachment != null) {
                taskHistoryService.recordHistory(
                        attachment.getTaskId(),
                        event.actorId(),
                        TaskHistoryActionType.ATTACHMENT_REMOVED,
                        "attachment",
                        attachment.getOriginalFileName(),
                        null,
                        "Attachment '" + attachment.getOriginalFileName() + "' was deleted."
                );
            }
        } catch (Exception e) {
            log.warn("Failed to log AttachmentDeletedEvent for attachment ID: {}", event.attachmentId(), e);
        }
    }

    @EventListener
    public void handleTimeEntryCreated(TimeEntryCreatedEvent event) {
        try {
            taskHistoryService.recordHistory(
                    event.taskId(),
                    event.userId(),
                    TaskHistoryActionType.TIME_LOGGED,
                    "timeLog",
                    null,
                    event.durationMinutes() != null ? event.durationMinutes().toString() : "0",
                    "Logged " + event.durationMinutes() + " minutes of work."
            );
        } catch (Exception e) {
            log.warn("Failed to log TimeEntryCreatedEvent for task ID: {}", event.taskId(), e);
        }
    }

    @EventListener
    public void handleTimeEntryUpdated(TimeEntryUpdatedEvent event) {
        try {
            taskHistoryService.recordHistory(
                    event.taskId(),
                    event.userId(),
                    TaskHistoryActionType.TIME_LOGGED,
                    "timeLog",
                    null,
                    event.durationMinutes() != null ? event.durationMinutes().toString() : "0",
                    "Updated time entry to " + event.durationMinutes() + " minutes."
            );
        } catch (Exception e) {
            log.warn("Failed to log TimeEntryUpdatedEvent for task ID: {}", event.taskId(), e);
        }
    }

    @EventListener
    public void handleTimeEntryDeleted(TimeEntryDeletedEvent event) {
        try {
            taskHistoryService.recordHistory(
                    event.taskId(),
                    event.userId(),
                    TaskHistoryActionType.TIME_LOGGED,
                    "timeLog",
                    null, null,
                    "Deleted time entry."
            );
        } catch (Exception e) {
            log.warn("Failed to log TimeEntryDeletedEvent for task ID: {}", event.taskId(), e);
        }
    }

    @EventListener
    public void handleCustomFieldValueAssigned(CustomFieldValueAssignedEvent event) {
        try {
            CustomField field = customFieldRepository.findById(event.customFieldId()).orElse(null);
            String fieldName = field != null ? field.getName() : "Custom Field";
            taskHistoryService.recordHistory(
                    event.taskId(),
                    event.actorId(),
                    TaskHistoryActionType.CUSTOM_FIELD_UPDATED,
                    fieldName,
                    null, null,
                    "Custom field '" + fieldName + "' was set."
            );
        } catch (Exception e) {
            log.warn("Failed to log CustomFieldValueAssignedEvent for task ID: {}", event.taskId(), e);
        }
    }

    @EventListener
    public void handleCustomFieldValueUpdated(CustomFieldValueUpdatedEvent event) {
        try {
            CustomField field = customFieldRepository.findById(event.customFieldId()).orElse(null);
            String fieldName = field != null ? field.getName() : "Custom Field";
            taskHistoryService.recordHistory(
                    event.taskId(),
                    event.actorId(),
                    TaskHistoryActionType.CUSTOM_FIELD_UPDATED,
                    fieldName,
                    null, null,
                    "Custom field '" + fieldName + "' value was updated."
            );
        } catch (Exception e) {
            log.warn("Failed to log CustomFieldValueUpdatedEvent for task ID: {}", event.taskId(), e);
        }
    }

    @EventListener
    public void handleTaskWatcherAdded(TaskWatcherAddedEvent event) {
        try {
            User user = userRepository.findById(event.userId()).orElse(null);
            String userName = user != null ? (user.getFullName() != null ? user.getFullName() : user.getUsername()) : "User #" + event.userId();
            taskHistoryService.recordHistory(
                    event.taskId(),
                    event.actorId(),
                    TaskHistoryActionType.WATCHER_ADDED,
                    "watcher",
                    null,
                    event.userId().toString(),
                    userName + " started watching this task."
            );
        } catch (Exception e) {
            log.warn("Failed to log TaskWatcherAddedEvent for task ID: {}", event.taskId(), e);
        }
    }

    @EventListener
    public void handleTaskWatcherRemoved(TaskWatcherRemovedEvent event) {
        try {
            User user = userRepository.findById(event.userId()).orElse(null);
            String userName = user != null ? (user.getFullName() != null ? user.getFullName() : user.getUsername()) : "User #" + event.userId();
            taskHistoryService.recordHistory(
                    event.taskId(),
                    event.actorId(),
                    TaskHistoryActionType.WATCHER_REMOVED,
                    "watcher",
                    event.userId().toString(),
                    null,
                    userName + " stopped watching this task."
            );
        } catch (Exception e) {
            log.warn("Failed to log TaskWatcherRemovedEvent for task ID: {}", event.taskId(), e);
        }
    }

    @EventListener
    public void handleChecklistCreated(ChecklistCreatedEvent event) {
        try {
            Checklist checklist = checklistRepository.findById(event.checklistId()).orElse(null);
            if (checklist != null) {
                taskHistoryService.recordHistory(
                        checklist.getTaskId(),
                        event.actorId(),
                        TaskHistoryActionType.TASK_UPDATED,
                        "checklist",
                        null,
                        checklist.getTitle(),
                        "Checklist '" + checklist.getTitle() + "' was added."
                );
            }
        } catch (Exception e) {
            log.warn("Failed to log ChecklistCreatedEvent for checklist ID: {}", event.checklistId(), e);
        }
    }

    @EventListener
    public void handleChecklistItemCompleted(ChecklistItemCompletedEvent event) {
        try {
            ChecklistItem item = checklistItemRepository.findById(event.checklistItemId()).orElse(null);
            if (item != null) {
                Checklist checklist = checklistRepository.findById(item.getChecklistId()).orElse(null);
                if (checklist != null) {
                    String actionText = Boolean.TRUE.equals(event.completed()) ? "completed" : "uncompleted";
                    taskHistoryService.recordHistory(
                            checklist.getTaskId(),
                            event.actorId(),
                            TaskHistoryActionType.CHECKLIST_COMPLETED,
                            "checklistItem",
                            null,
                            item.getTitle(),
                            "Checklist item '" + item.getTitle() + "' was marked " + actionText + "."
                    );
                }
            }
        } catch (Exception e) {
            log.warn("Failed to log ChecklistItemCompletedEvent for item ID: {}", event.checklistItemId(), e);
        }
    }

    private String getMemberName(Long projectMemberId) {
        if (projectMemberId == null) return "Unknown Assignee";
        return projectMemberRepository.findById(projectMemberId)
                .flatMap(pm -> workspaceMemberRepository.findById(pm.getWorkspaceMemberId()))
                .flatMap(wm -> userRepository.findById(wm.getUserId()))
                .map(u -> u.getFullName() != null ? u.getFullName() : u.getUsername())
                .orElse("Member #" + projectMemberId);
    }

    private String getLabelName(Long labelId) {
        if (labelId == null) return "Label";
        return labelRepository.findById(labelId)
                .map(Label::getName)
                .orElse("Label #" + labelId);
    }
}
