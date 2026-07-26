package org.SprintForge.modules.workspace.kanban.repository;

import org.SprintForge.modules.workspace.kanban.entity.BoardCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardCardRepository extends JpaRepository<BoardCard, Long>, JpaSpecificationExecutor<BoardCard> {
}