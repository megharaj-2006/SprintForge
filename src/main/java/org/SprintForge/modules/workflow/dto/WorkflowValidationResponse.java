package org.SprintForge.modules.workflow.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkflowValidationResponse {
    private boolean valid;
    private List<ValidationError> errors = new ArrayList<>();
    private List<ValidationWarning> warnings = new ArrayList<>();

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ValidationError {
        private String code;
        private String message;
        private String targetId;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ValidationWarning {
        private String code;
        private String message;
        private String targetId;
    }

    public void addError(String code, String message, String targetId) {
        this.valid = false;
        this.errors.add(new ValidationError(code, message, targetId));
    }

    public void addWarning(String code, String message, String targetId) {
        this.warnings.add(new ValidationWarning(code, message, targetId));
    }
}
