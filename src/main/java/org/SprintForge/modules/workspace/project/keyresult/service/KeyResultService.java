package org.SprintForge.modules.workspace.project.keyresult.service;

import org.SprintForge.modules.workspace.project.keyresult.dto.request.CreateKeyResultRequest;
import org.SprintForge.modules.workspace.project.keyresult.dto.request.UpdateKeyResultProgressRequest;
import org.SprintForge.modules.workspace.project.keyresult.dto.request.UpdateKeyResultRequest;
import org.SprintForge.modules.workspace.project.keyresult.dto.response.KeyResultForecastResponse;
import org.SprintForge.modules.workspace.project.keyresult.dto.response.KeyResultResponse;

import java.util.List;

public interface KeyResultService {
    KeyResultResponse createKeyResult(Long objectiveId, CreateKeyResultRequest request, Long actorId);
    KeyResultResponse updateKeyResult(Long keyResultId, UpdateKeyResultRequest request, Long actorId);
    KeyResultResponse updateProgress(Long keyResultId, UpdateKeyResultProgressRequest request, Long actorId);
    List<KeyResultResponse> getKeyResults(Long objectiveId);
    KeyResultResponse getKeyResult(Long keyResultId);
    void deleteKeyResult(Long keyResultId, Long actorId);
    KeyResultForecastResponse getForecast(Long keyResultId);
}
