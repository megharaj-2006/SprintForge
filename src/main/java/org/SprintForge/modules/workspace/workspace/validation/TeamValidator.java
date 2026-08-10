package org.SprintForge.modules.workspace.workspace.validation;

import org.SprintForge.common.exception.BusinessRuleException;
import org.SprintForge.modules.workspace.workspace.team.entity.Team;
import org.SprintForge.modules.workspace.workspace.team.repository.TeamRepository;
import org.springframework.stereotype.Component;

@Component
public class TeamValidator {
    private final TeamRepository teamRepository;

    public TeamValidator(TeamRepository teamRepository) { 
        this.teamRepository = teamRepository; 
    }

    public void validateUniqueName(Long workspaceId, String name) {
        if (teamRepository.existsByWorkspaceIdAndNameAndIsDeletedFalse(workspaceId, name)) {
            throw new BusinessRuleException("Team name already exists in this workspace: " + name);
        }
    }

    public void validateNotArchived(Team team) {
        if (team.isArchived()) {
            throw new BusinessRuleException("Cannot modify an archived team.");
        }
    }
}
