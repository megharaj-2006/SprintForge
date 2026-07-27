package org.SprintForge.modules.workspace.task.mapper;

import org.SprintForge.modules.workspace.task.dto.request.CreateLabelRequest;
import org.SprintForge.modules.workspace.task.dto.request.UpdateLabelRequest;
import org.SprintForge.modules.workspace.task.dto.response.LabelResponse;
import org.SprintForge.modules.workspace.task.dto.response.LabelSummaryResponse;
import org.SprintForge.modules.workspace.task.dto.response.TaskLabelResponse;
import org.SprintForge.modules.workspace.task.entity.TaskLabel;

import org.springframework.stereotype.Component;

@Component
public class LabelMapper {

    public TaskLabel toEntity(CreateLabelRequest request) {
        if (request == null) {
            return null;
        }
        TaskLabel label = new TaskLabel();
        label.setName(request.getName());
        label.setDescription(request.getDescription());
        label.setColor(request.getColor());
        // project will be set in service
        return label;
    }

    public void updateEntityFromRequest(UpdateLabelRequest request, TaskLabel label) {
        if (request == null || label == null) {
            return;
        }
        label.setName(request.getName());
        label.setDescription(request.getDescription());
        label.setColor(request.getColor());
        // id and project should not be changed via update
    }

    public LabelResponse toLabelResponse(TaskLabel label) {
        if (label == null) {
            return null;
        }
        LabelResponse response = new LabelResponse();
        response.setId(label.getId());
        response.setProjectId(label.getProject().getId());
        response.setName(label.getName());
        response.setDescription(label.getDescription());
        response.setColor(label.getColor());
        response.setArchived(label.isArchived());
        response.setCreatedBy(label.getCreatedBy());
        response.setCreatedAt(label.getCreatedAt());
        response.setUpdatedBy(label.getUpdatedBy());
        response.setUpdatedAt(label.getUpdatedAt());
        return response;
    }

    public LabelSummaryResponse toLabelSummaryResponse(TaskLabel label) {
        if (label == null) {
            return null;
        }
        return new LabelSummaryResponse(
                label.getId(),
                label.getName(),
                label.getColor()
        );
    }

    public TaskLabelResponse toTaskLabelResponse(TaskLabel label) {
        if (label == null) {
            return null;
        }
        return new TaskLabelResponse(
                label.getId(),
                label.getName(),
                label.getColor()
        );
    }
}