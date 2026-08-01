package org.SprintForge.modules.workspace.task.repository;

import org.SprintForge.modules.workspace.task.entity.TaskTemplateFavorite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskTemplateFavoriteRepository extends JpaRepository<TaskTemplateFavorite, Long> {

    List<TaskTemplateFavorite> findByUserIdAndIsDeletedFalse(Long userId);

    Optional<TaskTemplateFavorite> findByUserIdAndTaskTemplateIdAndIsDeletedFalse(Long userId, Long taskTemplateId);

    boolean existsByUserIdAndTaskTemplateIdAndIsDeletedFalse(Long userId, Long taskTemplateId);
}
