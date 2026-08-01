package org.SprintForge.modules.workspace.task.service;

import org.SprintForge.modules.workspace.task.dto.request.*;
import org.SprintForge.modules.workspace.task.dto.response.BulkOperationResponse;

import java.util.List;

public interface BulkTaskService {

    BulkOperationResponse bulkAssign(BulkAssignRequest request, Long actorId);

    BulkOperationResponse bulkStatus(BulkStatusRequest request, Long actorId);

    BulkOperationResponse bulkPriority(BulkPriorityRequest request, Long actorId);

    BulkOperationResponse bulkArchive(BulkArchiveRequest request, Long actorId);

    BulkOperationResponse bulkDelete(BulkDeleteRequest request, Long actorId);

    BulkOperationResponse bulkMoveSprint(BulkMoveSprintRequest request, Long actorId);

    BulkOperationResponse bulkMoveMilestone(BulkMoveMilestoneRequest request, Long actorId);

    BulkOperationResponse bulkLabels(BulkLabelRequest request, Long actorId);

    BulkOperationResponse bulkCustomField(BulkCustomFieldRequest request, Long actorId);

    BulkOperationResponse bulkRestore(List<Long> taskIds, Long actorId);
}
