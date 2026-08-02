package org.SprintForge.modules.workspace.project.insights.reporting.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.SprintForge.common.entity.SoftDeleteEntity;

import java.time.LocalDateTime;

@Entity(name = "InsightsReportExecution")
@Table(name = "report_executions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReportExecution extends SoftDeleteEntity {

    @Column(name = "report_id", nullable = false)
    private Long reportId;

    @Column(name = "executed_at")
    private LocalDateTime executedAt = LocalDateTime.now();

    @Column(name = "status")
    private String status = "COMPLETED";

    @Column(name = "download_url")
    private String downloadUrl;
}
