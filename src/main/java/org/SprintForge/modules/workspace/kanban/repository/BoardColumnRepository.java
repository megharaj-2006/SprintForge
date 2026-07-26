package org.SprintForge.modules.workspace.kanban.repository;

import org.SprintForge.modules.workspace.kanban.entity.BoardColumn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardColumnRepository extends JpaRepository<BoardColumn, Long>, JpaSpecificationExecutor<BoardColumn> {
}