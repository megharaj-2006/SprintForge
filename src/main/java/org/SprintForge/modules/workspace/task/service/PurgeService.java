package org.SprintForge.modules.workspace.task.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.SprintForge.modules.workspace.task.entity.TrashRecord;
import org.SprintForge.modules.workspace.task.repository.TaskRepository;
import org.SprintForge.modules.workspace.task.repository.TrashRecordRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PurgeService {

    private final TrashRecordRepository trashRecordRepository;
    private final TaskRepository taskRepository;

    @Transactional
    public int purgeExpiredTrash() {
        LocalDateTime now = LocalDateTime.now();
        List<TrashRecord> expired = trashRecordRepository.findByScheduledPurgeAtBeforeAndIsDeletedFalse(now);
        log.info("Purging {} expired trash records", expired.size());

        for (TrashRecord r : expired) {
            if ("TASK".equalsIgnoreCase(r.getEntityType())) {
                taskRepository.deleteById(r.getEntityId());
            }
            r.setDeleted(true);
        }
        trashRecordRepository.saveAll(expired);
        return expired.size();
    }
}
