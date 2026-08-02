package org.SprintForge.modules.workspace.project.service.category;

import org.SprintForge.modules.workspace.project.entity.ProjectCategory;

import java.util.List;

public interface ProjectCategoryService {
    ProjectCategory createCategory(Long workspaceId, String name, String description, String color, Long actorId);
    List<ProjectCategory> getCategories(Long workspaceId);
    ProjectCategory getCategory(Long categoryId);
    void deleteCategory(Long categoryId, Long actorId);
}
