package org.SprintForge.modules.workspace.analytics.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.SprintForge.common.entity.SoftDeleteEntity;

@Entity
@Table(name = "dashboard_widgets")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DashboardWidget extends SoftDeleteEntity {

    @Column(name = "dashboard_id", nullable = false)
    private Long dashboardId;

    @Column(name = "widget_type", nullable = false)
    private String widgetType;

    @Column(name = "title")
    private String title;

    @Column(name = "configuration", columnDefinition = "TEXT")
    private String configuration;

    @Column(name = "position")
    private Integer position;

    @Column(name = "width")
    private Integer width;

    @Column(name = "height")
    private Integer height;
}

