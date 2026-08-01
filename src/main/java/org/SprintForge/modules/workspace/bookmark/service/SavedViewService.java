package org.SprintForge.modules.workspace.bookmark.service;

import org.SprintForge.modules.workspace.bookmark.dto.request.*;
import org.SprintForge.modules.workspace.bookmark.dto.response.SavedViewResponse;
import org.SprintForge.modules.workspace.bookmark.dto.response.SavedViewSummaryResponse;

import java.util.List;

public interface SavedViewService {

    SavedViewResponse createView(Long projectId, CreateSavedViewRequest request, Long actorId);

    SavedViewResponse updateView(Long viewId, UpdateSavedViewRequest request, Long actorId);

    void deleteView(Long viewId, Long actorId);

    SavedViewResponse favoriteView(Long viewId, Long actorId);

    SavedViewResponse shareView(Long viewId, ShareSavedViewRequest request, Long actorId);

    SavedViewResponse applyView(Long viewId, ApplySavedViewRequest request, Long actorId);

    SavedViewResponse duplicateView(Long viewId, String newName, Long actorId);

    SavedViewResponse setDefaultView(Long viewId, Long actorId);

    List<SavedViewSummaryResponse> getProjectViews(Long projectId, Long actorId);

    SavedViewResponse getViewById(Long viewId, Long actorId);

    List<SavedViewSummaryResponse> getUserFavoriteViews(Long actorId);
}
