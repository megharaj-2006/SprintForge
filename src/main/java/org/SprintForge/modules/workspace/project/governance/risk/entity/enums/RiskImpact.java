package org.SprintForge.modules.workspace.project.governance.risk.entity.enums;

public enum RiskImpact {
    LOW(1),
    MEDIUM(2),
    HIGH(3),
    CRITICAL(4);

    private final int value;

    RiskImpact(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
