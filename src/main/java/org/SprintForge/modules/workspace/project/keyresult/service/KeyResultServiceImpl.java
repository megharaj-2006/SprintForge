package org.SprintForge.modules.workspace.project.keyresult.service;

import lombok.RequiredArgsConstructor;
import org.SprintForge.common.exception.ResourceNotFoundException;
import org.SprintForge.modules.workspace.project.keyresult.dto.request.CreateKeyResultRequest;
import org.SprintForge.modules.workspace.project.keyresult.dto.request.UpdateKeyResultProgressRequest;
import org.SprintForge.modules.workspace.project.keyresult.dto.request.UpdateKeyResultRequest;
import org.SprintForge.modules.workspace.project.keyresult.dto.response.KeyResultForecastResponse;
import org.SprintForge.modules.workspace.project.keyresult.dto.response.KeyResultResponse;
import org.SprintForge.modules.workspace.project.keyresult.entity.KeyResult;
import org.SprintForge.modules.workspace.project.keyresult.entity.enums.KeyResultMetricType;
import org.SprintForge.modules.workspace.project.keyresult.repository.KeyResultRepository;
import org.SprintForge.modules.workspace.project.objective.entity.Objective;
import org.SprintForge.modules.workspace.project.objective.repository.ObjectiveRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class KeyResultServiceImpl implements KeyResultService {

    private final KeyResultRepository keyResultRepository;
    private final ObjectiveRepository objectiveRepository;

    @Override
    @Transactional
    public KeyResultResponse createKeyResult(Long objectiveId, CreateKeyResultRequest request, Long actorId) {
        Objective objective = objectiveRepository.findById(objectiveId)
                .filter(o -> !o.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Objective not found with ID: " + objectiveId));

        KeyResult kr = new KeyResult();
        kr.setObjectiveId(objectiveId);
        kr.setTitle(request.getTitle());
        kr.setDescription(request.getDescription());
        kr.setMetricType(request.getMetricType() != null ? request.getMetricType() : KeyResultMetricType.PERCENTAGE);
        kr.setTargetValue(request.getTargetValue() != null ? request.getTargetValue() : 100.0);
        kr.setCurrentValue(request.getCurrentValue() != null ? request.getCurrentValue() : 0.0);
        kr.setUnit(request.getUnit() != null ? request.getUnit() : "%");
        kr.setWeight(request.getWeight() != null ? request.getWeight() : 1.0);
        kr.setStatus("IN_PROGRESS");

        KeyResult saved = keyResultRepository.save(kr);
        return toResponse(saved);
    }

    @Override
    @Transactional
    public KeyResultResponse updateKeyResult(Long keyResultId, UpdateKeyResultRequest request, Long actorId) {
        KeyResult kr = keyResultRepository.findById(keyResultId)
                .filter(k -> !k.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Key result not found with ID: " + keyResultId));

        if (request.getTitle() != null) kr.setTitle(request.getTitle());
        if (request.getDescription() != null) kr.setDescription(request.getDescription());
        if (request.getMetricType() != null) kr.setMetricType(request.getMetricType());
        if (request.getTargetValue() != null) kr.setTargetValue(request.getTargetValue());
        if (request.getCurrentValue() != null) kr.setCurrentValue(request.getCurrentValue());
        if (request.getUnit() != null) kr.setUnit(request.getUnit());
        if (request.getWeight() != null) kr.setWeight(request.getWeight());
        if (request.getStatus() != null) kr.setStatus(request.getStatus());

        KeyResult saved = keyResultRepository.save(kr);
        return toResponse(saved);
    }

    @Override
    @Transactional
    public KeyResultResponse updateProgress(Long keyResultId, UpdateKeyResultProgressRequest request, Long actorId) {
        KeyResult kr = keyResultRepository.findById(keyResultId)
                .filter(k -> !k.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Key result not found with ID: " + keyResultId));

        kr.setCurrentValue(request.getCurrentValue());
        if (kr.getTargetValue() > 0 && kr.getCurrentValue() >= kr.getTargetValue()) {
            kr.setStatus("COMPLETED");
        }

        KeyResult saved = keyResultRepository.save(kr);
        return toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<KeyResultResponse> getKeyResults(Long objectiveId) {
        return keyResultRepository.findByObjectiveIdAndIsDeletedFalse(objectiveId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public KeyResultResponse getKeyResult(Long keyResultId) {
        KeyResult kr = keyResultRepository.findById(keyResultId)
                .filter(k -> !k.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Key result not found with ID: " + keyResultId));
        return toResponse(kr);
    }

    @Override
    @Transactional
    public void deleteKeyResult(Long keyResultId, Long actorId) {
        KeyResult kr = keyResultRepository.findById(keyResultId)
                .filter(k -> !k.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Key result not found with ID: " + keyResultId));

        kr.markDeleted(actorId != null ? actorId.toString() : "SYSTEM");
        keyResultRepository.save(kr);
    }

    @Override
    @Transactional(readOnly = true)
    public KeyResultForecastResponse getForecast(Long keyResultId) {
        KeyResult kr = keyResultRepository.findById(keyResultId)
                .filter(k -> !k.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Key result not found with ID: " + keyResultId));

        double progress = kr.getTargetValue() > 0 ? Math.min(100.0, (kr.getCurrentValue() / kr.getTargetValue()) * 100.0) : 0.0;
        String status = progress >= 100.0 ? "COMPLETED" : (progress >= 50.0 ? "ON_TRACK" : "AT_RISK");

        return KeyResultForecastResponse.builder()
                .keyResultId(kr.getId())
                .title(kr.getTitle())
                .currentValue(kr.getCurrentValue())
                .targetValue(kr.getTargetValue())
                .progressPercentage(progress)
                .runRatePerDay(1.5)
                .estimatedCompletionDate(LocalDate.now().plusDays((long) Math.max(1, (kr.getTargetValue() - kr.getCurrentValue()))))
                .forecastStatus(status)
                .build();
    }

    private KeyResultResponse toResponse(KeyResult kr) {
        double progress = kr.getTargetValue() > 0 ? Math.min(100.0, (kr.getCurrentValue() / kr.getTargetValue()) * 100.0) : 0.0;
        Long creatorId = null;
        if (kr.getCreatedBy() != null) {
            try {
                creatorId = Long.parseLong(kr.getCreatedBy());
            } catch (NumberFormatException ignored) {}
        }

        return KeyResultResponse.builder()
                .id(kr.getId())
                .objectiveId(kr.getObjectiveId())
                .title(kr.getTitle())
                .description(kr.getDescription())
                .metricType(kr.getMetricType())
                .targetValue(kr.getTargetValue())
                .currentValue(kr.getCurrentValue())
                .unit(kr.getUnit())
                .weight(kr.getWeight())
                .progressPercentage(progress)
                .status(kr.getStatus())
                .createdBy(creatorId)
                .createdAt(kr.getCreatedAt())
                .updatedAt(kr.getUpdatedAt())
                .build();
    }
}
