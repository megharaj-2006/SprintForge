package org.SprintForge.modules.workflow.dto;

import lombok.Data;

import java.util.Map;

@Data
public class ExecuteWorkflowRequest {
    private Long versionId;
    private String triggeredBy;
    private String triggerSource;
    private Map<String, Object> inputVariables;
}
