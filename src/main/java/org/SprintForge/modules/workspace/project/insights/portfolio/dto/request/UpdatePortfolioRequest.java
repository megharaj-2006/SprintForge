package org.SprintForge.modules.workspace.project.insights.portfolio.dto.request;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePortfolioRequest {

    @Size(min = 2, max = 150, message = "Portfolio name must be between 2 and 150 characters")
    private String name;

    private String description;
    private String status;
    private Long ownerId;
    private List<Long> projectIds;
}
