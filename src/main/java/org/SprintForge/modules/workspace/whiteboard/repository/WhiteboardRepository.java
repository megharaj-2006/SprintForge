package org.SprintForge.modules.workspace.whiteboard.repository;

import org.SprintForge.modules.workspace.whiteboard.entity.Whiteboard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface WhiteboardRepository extends JpaRepository<Whiteboard, Long>, JpaSpecificationExecutor<Whiteboard> {
}