package org.SprintForge.modules.ai.skill;

import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.ai.service.ExtractionService;
import org.SprintForge.modules.ai.service.SummarizationService;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class ReleaseNotesSkill implements AISkill {

    private final ExtractionService extractionService;
    private final SummarizationService summarizationService;

    @Override
    public String getSkillKey() {
        return "skill.releaseNotes";
    }

    @Override
    public String executeSkill(Map<String, Object> inputs, String policyKey) {
        String rawCommits = inputs.getOrDefault("commits", "Initial release features").toString();

        // Multi-step AI Skill Pipeline: Extract -> Summarize -> Format Release Notes
        String extractedWork = extractionService.extract("COMPLETED_FEATURES", rawCommits, policyKey);
        return summarizationService.summarize("RELEASE_NOTES", extractedWork, policyKey);
    }
}
