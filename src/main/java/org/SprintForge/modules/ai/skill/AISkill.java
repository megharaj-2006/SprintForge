package org.SprintForge.modules.ai.skill;

import java.util.Map;

public interface AISkill {
    String getSkillKey();
    String executeSkill(Map<String, Object> inputs, String policyKey);
}
