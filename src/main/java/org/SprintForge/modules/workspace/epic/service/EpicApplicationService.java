package org.SprintForge.modules.workspace.epic.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.SprintForge.common.exception.BusinessRuleException;
import org.SprintForge.common.exception.InvalidOperationException;
import org.SprintForge.common.exception.ResourceNotFoundException;
import org.SprintForge.modules.workspace.epic.dto.request.EpicMergeRequest;
import org.SprintForge.modules.workspace.epic.dto.request.EpicSplitRequest;
import org.SprintForge.modules.workspace.epic.entity.Epic;
import org.SprintForge.modules.workspace.epic.repository.EpicRepository;
import org.SprintForge.modules.workspace.task.entity.Task;
import org.SprintForge.modules.workspace.task.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EpicApplicationService {

    private final EpicRepository epicRepository;
    private final TaskRepository taskRepository;

    @Transactional
    public void mergeEpics(Long sourceEpicId, EpicMergeRequest request, Long actorId) {
        log.info("Merging epic {} into epic {} by user {}", sourceEpicId, request.getTargetEpicId(), actorId);

        Epic source = findEpicOrThrow(sourceEpicId);
        Epic target = findEpicOrThrow(request.getTargetEpicId());

        if (!source.getProjectId().equals(target.getProjectId())) {
            throw new BusinessRuleException("Cannot merge epics from different projects");
        }
        if (Boolean.TRUE.equals(source.getIsArchived()) || Boolean.TRUE.equals(target.getIsArchived())) {
            throw new InvalidOperationException("Archived epics cannot be merged");
        }

        source.setDeleted(true);
        epicRepository.save(source);
    }

    @Transactional
    public Epic splitEpic(Long sourceEpicId, EpicSplitRequest request, Long actorId) {
        log.info("Splitting epic {} by user {}", sourceEpicId, actorId);

        Epic source = findEpicOrThrow(sourceEpicId);
        if (Boolean.TRUE.equals(source.getIsArchived())) {
            throw new InvalidOperationException("Archived epic cannot be split");
        }

        Epic newEpic = new Epic();
        newEpic.setProjectId(source.getProjectId());
        newEpic.setName(request.getNewEpicName());
        newEpic.setDescription("Split from epic: " + source.getName());
        newEpic.setStatus("PLANNED");
        newEpic.setOwnerId(actorId);

        return epicRepository.save(newEpic);
    }

    private Epic findEpicOrThrow(Long id) {
        Epic epic = epicRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Epic not found with ID: " + id));
        if (epic.isDeleted()) {
            throw new ResourceNotFoundException("Epic not found with ID: " + id);
        }
        return epic;
    }
}
