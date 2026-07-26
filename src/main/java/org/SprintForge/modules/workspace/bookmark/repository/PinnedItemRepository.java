package org.SprintForge.modules.workspace.bookmark.repository;

import org.SprintForge.modules.workspace.bookmark.entity.PinnedItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PinnedItemRepository extends JpaRepository<PinnedItem, Long>, JpaSpecificationExecutor<PinnedItem> {
}