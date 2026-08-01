package org.SprintForge.modules.user.service;

import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.user.entity.UserCapacity;
import org.SprintForge.modules.user.repository.UserCapacityRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CapacityService {

    private final UserCapacityRepository capacityRepository;

    @Transactional(readOnly = true)
    public UserCapacity getUserCapacity(Long userId) {
        return capacityRepository.findByUserIdAndIsDeletedFalse(userId)
                .orElseGet(() -> {
                    UserCapacity c = new UserCapacity();
                    c.setUserId(userId);
                    c.setDailyHoursCapacity(8.0);
                    c.setWeeklyHoursCapacity(40.0);
                    c.setIsOnVacation(false);
                    return c;
                });
    }
}
