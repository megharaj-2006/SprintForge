package org.SprintForge.modules.workspace.task.service;

import org.SprintForge.modules.workspace.task.dto.request.CreateChecklistRequest;
import org.SprintForge.modules.workspace.task.dto.request.UpdateChecklistRequest;
import org.SprintForge.modules.workspace.task.dto.request.CreateChecklistItemRequest;
import org.SprintForge.modules.workspace.task.dto.request.UpdateChecklistItemRequest;
import org.SprintForge.modules.workspace.task.dto.request.MoveChecklistItemRequest;
import org.SprintForge.modules.workspace.task.dto.response.ChecklistResponse;
import org.SprintForge.modules.workspace.task.dto.response.ChecklistItemResponse;

import java.util.List;

public interface ChecklistService {

    ChecklistResponse createChecklist(Long taskId, CreateChecklistRequest request, Long actorId);

    ChecklistResponse updateChecklist(Long id, UpdateChecklistRequest request, Long actorId);

    void deleteChecklist(Long id, Long actorId);

    ChecklistItemResponse addItem(Long checklistId, CreateChecklistItemRequest request, Long actorId);

    ChecklistItemResponse updateItem(Long itemId, UpdateChecklistItemRequest request, Long actorId);

    ChecklistItemResponse completeItem(Long itemId, Boolean completed, Long actorId);

    void reorderItems(List<MoveChecklistItemRequest> requests, Long actorId);

    void deleteItem(Long itemId, Long actorId);

    List<ChecklistResponse> getTaskChecklists(Long taskId, Long actorId);
}
