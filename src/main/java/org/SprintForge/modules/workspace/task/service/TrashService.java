package org.SprintForge.modules.workspace.task.service;

import lombok.RequiredArgsConstructor;
import org.SprintForge.common.exception.ResourceNotFoundException;
import org.SprintForge.modules.workspace.task.entity.Task;
import org.SprintForge.modules.workspace.task.entity.TrashRecord;
import org.SprintForge.modules.workspace.task.repository.TaskRepository;
import org.SprintForge.modules.workspace.task.repository.TrashRecordRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TrashService {

    private final TrashRecordRepository trashRecordRepository;
    private final TaskRepository taskRepository;

    @Transactional
    public TrashRecord moveToTrash(String entityType, Long entityId, String reason, Long actorId) {
        if ("TASK".equalsIgnoreCase(entityType)) {
            Task task = taskRepository.findById(entityId).orElse(null);
            if (task != null) {
                task.setDeleted(true);
                taskRepository.save(task);
            }
        }

        TrashRecord record = new TrashRecord();
        record.setEntityType(entityType.toUpperCase());
        record.setEntityId(entityId);
        record.setDeletedByUserId(actorId);
        record.setDeletedAt(LocalDateTime.now());
        record.setScheduledPurgeAt(LocalDateTime.now().plusDays(30));
        record.setReason(reason);

        return trashRecordRepository.save(record);
    }

    @Transactional
    public void restoreFromTrash(Long trashRecordId, Long actorId) {
        TrashRecord record = trashRecordRepository.findById(trashRecordId)
                .orElseThrow(() -> new ResourceNotFoundException("Trash record not found with ID: " + trashRecordId));

        if ("TASK".equalsIgnoreCase(record.getEntityType())) {
            Task task = taskRepository.findById(record.getEntityId()).orElse(null);
            if (task != null) {
                task.setDeleted(false);
                taskRepository.save(task);
            }
        }

        record.setDeleted(true);
        trashRecordRepository.save(record);
    }

    @Transactional(readOnly = true)
    public List<TrashRecord> getTrashItems() {
        return trashRecordRepository.findByIsDeletedFalseOrderByDeletedAtDesc();
    }
}
