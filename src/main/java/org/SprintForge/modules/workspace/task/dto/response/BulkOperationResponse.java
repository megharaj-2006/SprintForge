package org.SprintForge.modules.workspace.task.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BulkOperationResponse {

    private String operationType;
    private int totalRequested;
    private int successCount;
    private int failureCount;
    private List<Long> successfulTaskIds;
    private List<BulkFailure> failures;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BulkFailure {
        private Long taskId;
        private String reason;
    }
}
