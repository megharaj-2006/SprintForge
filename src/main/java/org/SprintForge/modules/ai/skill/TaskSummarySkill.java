package org.SprintForge.modules.ai.skill;

import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.ai.service.SummarizationService;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class TaskSummarySkill implements AISkill {

    private final SummarizationService summarizationService;

    @Override
    public String getSkillKey() {
        return "skill.taskSummary";
    }

    @Override
    public String executeSkill(Map<String, Object> inputs, String policyKey) {
        String description = inputs.getOrDefault("description", "Task description").toString();
        return summarizationService.summarize("TASK", description, policyKey);
    }
}
