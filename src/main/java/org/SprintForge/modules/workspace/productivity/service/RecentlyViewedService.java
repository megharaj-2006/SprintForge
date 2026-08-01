package org.SprintForge.modules.workspace.productivity.service;

import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.workspace.productivity.entity.RecentlyViewed;
import org.SprintForge.modules.workspace.productivity.repository.RecentlyViewedRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecentlyViewedService {

    private final RecentlyViewedRepository recentlyViewedRepository;

    @Transactional
    public RecentlyViewed recordView(Long userId, String entityType, Long entityId, String title) {
        Optional<RecentlyViewed> existing = recentlyViewedRepository.findByUserIdAndEntityTypeAndEntityIdAndIsDeletedFalse(userId, entityType, entityId);
        RecentlyViewed item;
        if (existing.isPresent()) {
            item = existing.get();
            item.setLastViewedAt(LocalDateTime.now());
            if (title != null) item.setTitle(title);
        } else {
            item = new RecentlyViewed();
            item.setUserId(userId);
            item.setEntityType(entityType);
            item.setEntityId(entityId);
            item.setTitle(title);
            item.setLastViewedAt(LocalDateTime.now());
        }
        return recentlyViewedRepository.save(item);
    }

    @Transactional(readOnly = true)
    public List<RecentlyViewed> getRecentlyViewed(Long userId) {
        return recentlyViewedRepository.findByUserIdAndIsDeletedFalseOrderByLastViewedAtDesc(userId);
    }
}
