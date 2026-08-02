package org.SprintForge.modules.workspace.project.insights.reporting.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.SprintForge.common.entity.SoftDeleteEntity;
import org.SprintForge.modules.workspace.project.insights.reporting.entity.enums.ReportFormat;
import org.SprintForge.modules.workspace.project.insights.reporting.entity.enums.ReportType;

@Entity(name = "InsightsSavedReport")
@Table(name = "saved_reports")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SavedReport extends SoftDeleteEntity {

    @Column(name = "project_id", nullable = false)
    private Long projectId;

    @Column(name = "name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "report_type", nullable = false)
    private ReportType reportType = ReportType.EXECUTIVE_SUMMARY;

    @Enumerated(EnumType.STRING)
    @Column(name = "format", nullable = false)
    private ReportFormat format = ReportFormat.PDF;

    @Column(name = "parameters", columnDefinition = "TEXT")
    private String parameters;
}
