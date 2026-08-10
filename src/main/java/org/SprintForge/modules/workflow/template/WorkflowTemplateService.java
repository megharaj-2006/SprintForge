package org.SprintForge.modules.workflow.template;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WorkflowTemplateService {

    public List<WorkflowTemplate> getStarterTemplates() {
        List<WorkflowTemplate> templates = new ArrayList<>();

        // 1. Manual Workflow Template
        templates.add(WorkflowTemplate.builder()
                .id("manual-starter")
                .name("Manual Workflow")
                .description("Starter template triggered manually from UI")
                .category("STARTER")
                .nodes(List.of(
                        new WorkflowTemplate.TemplateNode("manual.trigger", "Start", 100.0, 100.0),
                        new WorkflowTemplate.TemplateNode("log.utility", "Log Action", 300.0, 100.0),
                        new WorkflowTemplate.TemplateNode("stop.logic", "End", 500.0, 100.0)
                ))
                .connections(List.of(
                        new WorkflowTemplate.TemplateConnection("Start", "Log Action", "SUCCESS"),
                        new WorkflowTemplate.TemplateConnection("Log Action", "End", "SUCCESS")
                ))
                .build());

        // 2. Approval Workflow Template
        templates.add(WorkflowTemplate.builder()
                .id("approval-starter")
                .name("Approval Workflow")
                .description("Starter template requiring human manager approval")
                .category("HUMAN")
                .nodes(List.of(
                        new WorkflowTemplate.TemplateNode("manual.trigger", "Start", 100.0, 100.0),
                        new WorkflowTemplate.TemplateNode("approval.human", "Manager Approval", 300.0, 100.0),
                        new WorkflowTemplate.TemplateNode("log.utility", "Log Approved", 500.0, 50.0),
                        new WorkflowTemplate.TemplateNode("stop.logic", "End", 700.0, 100.0)
                ))
                .connections(List.of(
                        new WorkflowTemplate.TemplateConnection("Start", "Manager Approval", "SUCCESS"),
                        new WorkflowTemplate.TemplateConnection("Manager Approval", "Log Approved", "APPROVED"),
                        new WorkflowTemplate.TemplateConnection("Log Approved", "End", "SUCCESS")
                ))
                .build());

        // 3. Parallel Workflow Template
        templates.add(WorkflowTemplate.builder()
                .id("parallel-starter")
                .name("Parallel Workflow")
                .description("Starter template executing parallel branches concurrently")
                .category("ADVANCED")
                .nodes(List.of(
                        new WorkflowTemplate.TemplateNode("manual.trigger", "Start", 100.0, 100.0),
                        new WorkflowTemplate.TemplateNode("parallel.logic", "Fork Parallel", 300.0, 100.0),
                        new WorkflowTemplate.TemplateNode("uuid.utility", "Branch A UUID", 500.0, 50.0),
                        new WorkflowTemplate.TemplateNode("timestamp.utility", "Branch B Timestamp", 500.0, 150.0),
                        new WorkflowTemplate.TemplateNode("merge.logic", "Join Branches", 700.0, 100.0)
                ))
                .connections(List.of(
                        new WorkflowTemplate.TemplateConnection("Start", "Fork Parallel", "SUCCESS"),
                        new WorkflowTemplate.TemplateConnection("Fork Parallel", "Branch A UUID", "DEFAULT"),
                        new WorkflowTemplate.TemplateConnection("Fork Parallel", "Branch B Timestamp", "DEFAULT"),
                        new WorkflowTemplate.TemplateConnection("Branch A UUID", "Join Branches", "SUCCESS"),
                        new WorkflowTemplate.TemplateConnection("Branch B Timestamp", "Join Branches", "SUCCESS")
                ))
                .build());

        return templates;
    }
}
