package org.SprintForge.modules.workspace.workspace.validation;

import org.SprintForge.common.exception.BusinessRuleException;
import org.SprintForge.modules.workspace.workspace.department.entity.Department;
import org.SprintForge.modules.workspace.workspace.department.repository.DepartmentRepository;
import org.springframework.stereotype.Component;

@Component
public class DepartmentValidator {
    private final DepartmentRepository departmentRepository;

    public DepartmentValidator(DepartmentRepository departmentRepository) { 
        this.departmentRepository = departmentRepository; 
    }

    public void validateUniqueName(Long workspaceId, String name) {
        if (departmentRepository.existsByWorkspaceIdAndNameAndIsDeletedFalse(workspaceId, name)) {
            throw new BusinessRuleException("Department name already exists in this workspace: " + name);
        }
    }

    public void validateNotArchived(Department department) {
        if (department.isArchived()) {
            throw new BusinessRuleException("Cannot modify an archived department.");
        }
    }
}
