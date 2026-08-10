package org.SprintForge.modules.ai;

import org.SprintForge.modules.ai.service.ExtractionService;
import org.SprintForge.modules.ai.service.SummarizationService;
import org.SprintForge.modules.ai.skill.ReleaseNotesSkill;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class AISkillsTest {

    @Test
    void testReleaseNotesSkillOrchestration() {
        ExtractionService extractionService = Mockito.mock(ExtractionService.class);
        SummarizationService summarizationService = Mockito.mock(SummarizationService.class);

        when(extractionService.extract(anyString(), anyString(), anyString())).thenReturn("Extracted Feature: User Auth");
        when(summarizationService.summarize(anyString(), anyString(), anyString())).thenReturn("## Release Notes v1.0\n- User Auth feature added");

        ReleaseNotesSkill skill = new ReleaseNotesSkill(extractionService, summarizationService);
        assertEquals("skill.releaseNotes", skill.getSkillKey());

        String result = skill.executeSkill(Map.of("commits", "feat: add user authentication"), "BALANCED");

        assertNotNull(result);
        assertTrue(result.contains("Release Notes v1.0"));
    }
}
