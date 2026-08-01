package org.SprintForge.modules.workspace.task.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskTemplateDetailResponse {

    private TaskTemplateResponse template;
    private Integer checklistCount;
    private Integer labelCount;
    private Integer attachmentCount;
    private Integer customFieldCount;
}
