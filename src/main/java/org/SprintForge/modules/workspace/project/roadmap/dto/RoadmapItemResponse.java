package org.SprintForge.modules.workspace.project.roadmap.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoadmapItemResponse {

    private Long id;
    private String itemType; // GOAL, OBJECTIVE, RELEASE, MILESTONE
    private String title;
    private String description;
    private String status;
    private Double progressPercentage;
    private LocalDate startDate;
    private LocalDate endDate;
    private String color;
    private String timeBucket; // e.g. Q1 2026, Q2 2026, 2026-H1
}
