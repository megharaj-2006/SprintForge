package org.SprintForge.modules.workspace.form.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FormSummaryResponse {

    private Long id;
    private String title;
    private Boolean isPublic;
    private Integer submissionCount;
}
