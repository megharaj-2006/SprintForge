package org.SprintForge.modules.workspace.project.governance.risk.entity.enums;

public enum RiskProbability {
    LOW(1),
    MEDIUM(2),
    HIGH(3);

    private final int value;

    RiskProbability(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
