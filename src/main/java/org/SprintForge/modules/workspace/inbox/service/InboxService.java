package org.SprintForge.modules.workspace.inbox.service;

import lombok.RequiredArgsConstructor;
import org.SprintForge.common.exception.ResourceNotFoundException;
import org.SprintForge.modules.workspace.inbox.entity.InboxItem;
import org.SprintForge.modules.workspace.inbox.repository.InboxItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InboxService {

    private final InboxItemRepository inboxItemRepository;

    @Transactional
    public InboxItem createInboxItem(Long userId, Long taskId, String type, String title, String summary) {
        InboxItem item = new InboxItem();
        item.setUserId(userId);
        item.setTaskId(taskId);
        item.setType(type);
        item.setTitle(title);
        item.setSummary(summary);
        item.setIsRead(false);
        item.setIsArchived(false);
        return inboxItemRepository.save(item);
    }

    @Transactional(readOnly = true)
    public List<InboxItem> getUserInbox(Long userId) {
        LocalDateTime now = LocalDateTime.now();
        return inboxItemRepository.findByUserIdAndIsArchivedFalseAndIsDeletedFalseOrderByCreatedAtDesc(userId).stream()
                .filter(item -> item.getSnoozedUntil() == null || item.getSnoozedUntil().isBefore(now))
                .collect(Collectors.toList());
    }

    @Transactional
    public InboxItem markAsRead(Long id, Long userId) {
        InboxItem item = inboxItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inbox item not found with ID: " + id));
        item.setIsRead(true);
        return inboxItemRepository.save(item);
    }

    @Transactional
    public InboxItem archiveItem(Long id, Long userId) {
        InboxItem item = inboxItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inbox item not found with ID: " + id));
        item.setIsArchived(true);
        return inboxItemRepository.save(item);
    }

    @Transactional
    public InboxItem snoozeItem(Long id, int minutes, Long userId) {
        InboxItem item = inboxItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inbox item not found with ID: " + id));
        item.setSnoozedUntil(LocalDateTime.now().plusMinutes(minutes));
        return inboxItemRepository.save(item);
    }
}
