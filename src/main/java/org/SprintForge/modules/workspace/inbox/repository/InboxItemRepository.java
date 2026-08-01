package org.SprintForge.modules.workspace.inbox.repository;

import org.SprintForge.modules.workspace.inbox.entity.InboxItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InboxItemRepository extends JpaRepository<InboxItem, Long> {

    List<InboxItem> findByUserIdAndIsArchivedFalseAndIsDeletedFalseOrderByCreatedAtDesc(Long userId);
}
