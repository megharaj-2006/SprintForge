package org.SprintForge.modules.workspace.project.service.category;

import lombok.RequiredArgsConstructor;
import org.SprintForge.common.exception.ConflictException;
import org.SprintForge.common.exception.ResourceNotFoundException;
import org.SprintForge.modules.workspace.project.entity.ProjectCategory;
import org.SprintForge.modules.workspace.project.repository.ProjectCategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectCategoryServiceImpl implements ProjectCategoryService {

    private final ProjectCategoryRepository categoryRepository;

    @Override
    @Transactional
    public ProjectCategory createCategory(Long workspaceId, String name, String description, String color, Long actorId) {
        if (categoryRepository.existsByWorkspaceIdAndNameAndIsDeletedFalse(workspaceId, name)) {
            throw new ConflictException("Category with name '" + name + "' already exists in this workspace.");
        }
        ProjectCategory category = new ProjectCategory();
        category.setWorkspaceId(workspaceId);
        category.setName(name);
        category.setDescription(description);
        category.setColor(color != null ? color : "#4B5563");
        return categoryRepository.save(category);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectCategory> getCategories(Long workspaceId) {
        return categoryRepository.findByWorkspaceIdAndIsDeletedFalse(workspaceId);
    }

    @Override
    @Transactional(readOnly = true)
    public ProjectCategory getCategory(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .filter(c -> !c.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: " + categoryId));
    }

    @Override
    @Transactional
    public void deleteCategory(Long categoryId, Long actorId) {
        ProjectCategory category = getCategory(categoryId);
        category.markDeleted(actorId != null ? actorId.toString() : "SYSTEM");
        categoryRepository.save(category);
    }
}
