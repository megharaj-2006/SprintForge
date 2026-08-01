package org.SprintForge.modules.workspace.bookmark.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.SprintForge.common.exception.DuplicateResourceException;
import org.SprintForge.common.exception.ForbiddenException;
import org.SprintForge.common.exception.ResourceNotFoundException;
import org.SprintForge.modules.workspace.bookmark.dto.request.*;
import org.SprintForge.modules.workspace.bookmark.dto.response.SavedViewResponse;
import org.SprintForge.modules.workspace.bookmark.dto.response.SavedViewSummaryResponse;
import org.SprintForge.modules.workspace.bookmark.entity.SavedView;
import org.SprintForge.modules.workspace.bookmark.repository.SavedViewRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SavedViewServiceImpl implements SavedViewService {

    private final SavedViewRepository savedViewRepository;

    @Override
    @Transactional
    public SavedViewResponse createView(Long projectId, CreateSavedViewRequest request, Long actorId) {
        log.info("Creating saved view '{}' for project {} by user {}", request.getName(), projectId, actorId);

        if (savedViewRepository.existsByNameAndProjectIdAndUserIdAndIsDeletedFalse(request.getName(), projectId, actorId)) {
            throw new DuplicateResourceException("A view with name '" + request.getName() + "' already exists for this project");
        }

        if (Boolean.TRUE.equals(request.getIsDefault())) {
            clearDefaultView(actorId, projectId);
        }

        SavedView view = new SavedView();
        view.setProjectId(projectId);
        view.setWorkspaceId(request.getWorkspaceId() != null ? request.getWorkspaceId() : 1L);
        view.setUserId(actorId);
        view.setName(request.getName());
        view.setDescription(request.getDescription());
        view.setViewType(request.getViewType() != null ? request.getViewType() : "BOARD");
        view.setFilters(request.getFilters());
        view.setSorting(request.getSorting());
        view.setGrouping(request.getGrouping());
        view.setColumns(request.getColumns());
        view.setLayout(request.getLayout());
        view.setVisibility(request.getVisibility() != null ? request.getVisibility() : "PRIVATE");
        view.setIsDefault(Boolean.TRUE.equals(request.getIsDefault()));
        view.setIsShared(Boolean.TRUE.equals(request.getIsShared()));
        view.setIsFavorite(false);

        SavedView saved = savedViewRepository.save(view);
        return mapToResponse(saved);
    }

    @Override
    @Transactional
    public SavedViewResponse updateView(Long viewId, UpdateSavedViewRequest request, Long actorId) {
        SavedView view = findViewOrThrow(viewId);
        validateViewOwner(view, actorId);

        if (request.getName() != null && !request.getName().equals(view.getName())) {
            if (savedViewRepository.existsByNameAndProjectIdAndUserIdAndIsDeletedFalse(request.getName(), view.getProjectId(), actorId)) {
                throw new DuplicateResourceException("A view with name '" + request.getName() + "' already exists for this project");
            }
            view.setName(request.getName());
        }

        if (request.getDescription() != null) view.setDescription(request.getDescription());
        if (request.getViewType() != null) view.setViewType(request.getViewType());
        if (request.getFilters() != null) view.setFilters(request.getFilters());
        if (request.getSorting() != null) view.setSorting(request.getSorting());
        if (request.getGrouping() != null) view.setGrouping(request.getGrouping());
        if (request.getColumns() != null) view.setColumns(request.getColumns());
        if (request.getLayout() != null) view.setLayout(request.getLayout());
        if (request.getVisibility() != null) view.setVisibility(request.getVisibility());

        if (Boolean.TRUE.equals(request.getIsDefault()) && !Boolean.TRUE.equals(view.getIsDefault())) {
            clearDefaultView(actorId, view.getProjectId());
            view.setIsDefault(true);
        } else if (request.getIsDefault() != null) {
            view.setIsDefault(request.getIsDefault());
        }

        if (request.getIsShared() != null) view.setIsShared(request.getIsShared());

        SavedView updated = savedViewRepository.save(view);
        return mapToResponse(updated);
    }

    @Override
    @Transactional
    public void deleteView(Long viewId, Long actorId) {
        SavedView view = findViewOrThrow(viewId);
        validateViewOwner(view, actorId);

        view.setDeleted(true);
        savedViewRepository.save(view);
    }

    @Override
    @Transactional
    public SavedViewResponse favoriteView(Long viewId, Long actorId) {
        SavedView view = findViewOrThrow(viewId);
        view.setIsFavorite(!Boolean.TRUE.equals(view.getIsFavorite()));
        SavedView saved = savedViewRepository.save(view);
        return mapToResponse(saved);
    }

    @Override
    @Transactional
    public SavedViewResponse shareView(Long viewId, ShareSavedViewRequest request, Long actorId) {
        SavedView view = findViewOrThrow(viewId);
        validateViewOwner(view, actorId);

        view.setIsShared(request.getIsShared());
        if (request.getVisibility() != null) {
            view.setVisibility(request.getVisibility());
        }
        SavedView saved = savedViewRepository.save(view);
        return mapToResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public SavedViewResponse applyView(Long viewId, ApplySavedViewRequest request, Long actorId) {
        SavedView view = findViewOrThrow(viewId);
        return mapToResponse(view);
    }

    @Override
    @Transactional
    public SavedViewResponse duplicateView(Long viewId, String newName, Long actorId) {
        SavedView source = findViewOrThrow(viewId);
        String name = (newName != null && !newName.isBlank()) ? newName : source.getName() + " (Copy)";

        if (savedViewRepository.existsByNameAndProjectIdAndUserIdAndIsDeletedFalse(name, source.getProjectId(), actorId)) {
            throw new DuplicateResourceException("A view with name '" + name + "' already exists");
        }

        SavedView copy = new SavedView();
        copy.setWorkspaceId(source.getWorkspaceId());
        copy.setProjectId(source.getProjectId());
        copy.setUserId(actorId);
        copy.setName(name);
        copy.setDescription(source.getDescription());
        copy.setViewType(source.getViewType());
        copy.setFilters(source.getFilters());
        copy.setSorting(source.getSorting());
        copy.setGrouping(source.getGrouping());
        copy.setColumns(source.getColumns());
        copy.setLayout(source.getLayout());
        copy.setVisibility("PRIVATE");
        copy.setIsDefault(false);
        copy.setIsShared(false);
        copy.setIsFavorite(false);

        SavedView savedCopy = savedViewRepository.save(copy);
        return mapToResponse(savedCopy);
    }

    @Override
    @Transactional
    public SavedViewResponse setDefaultView(Long viewId, Long actorId) {
        SavedView view = findViewOrThrow(viewId);
        validateViewOwner(view, actorId);

        clearDefaultView(actorId, view.getProjectId());
        view.setIsDefault(true);

        SavedView saved = savedViewRepository.save(view);
        return mapToResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SavedViewSummaryResponse> getProjectViews(Long projectId, Long actorId) {
        List<SavedView> views = savedViewRepository.findAccessibleViewsForProject(projectId, actorId);
        return views.stream().map(this::mapToSummary).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public SavedViewResponse getViewById(Long viewId, Long actorId) {
        SavedView view = findViewOrThrow(viewId);
        return mapToResponse(view);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SavedViewSummaryResponse> getUserFavoriteViews(Long actorId) {
        List<SavedView> views = savedViewRepository.findByUserIdAndIsFavoriteTrueAndIsDeletedFalse(actorId);
        return views.stream().map(this::mapToSummary).collect(Collectors.toList());
    }

    private void clearDefaultView(Long userId, Long projectId) {
        Optional<SavedView> existingDefault = savedViewRepository.findByUserIdAndProjectIdAndIsDefaultTrueAndIsDeletedFalse(userId, projectId);
        existingDefault.ifPresent(v -> {
            v.setIsDefault(false);
            savedViewRepository.save(v);
        });
    }

    private SavedView findViewOrThrow(Long viewId) {
        SavedView view = savedViewRepository.findById(viewId)
                .orElseThrow(() -> new ResourceNotFoundException("Saved view not found with ID: " + viewId));
        if (view.isDeleted()) {
            throw new ResourceNotFoundException("Saved view not found with ID: " + viewId);
        }
        return view;
    }

    private void validateViewOwner(SavedView view, Long actorId) {
        if (actorId != null && !actorId.equals(view.getUserId())) {
            throw new ForbiddenException("Only the owner of this view can modify or delete it");
        }
    }

    private SavedViewResponse mapToResponse(SavedView view) {
        return SavedViewResponse.builder()
                .id(view.getId())
                .workspaceId(view.getWorkspaceId())
                .projectId(view.getProjectId())
                .userId(view.getUserId())
                .name(view.getName())
                .description(view.getDescription())
                .viewType(view.getViewType())
                .filters(view.getFilters())
                .sorting(view.getSorting())
                .grouping(view.getGrouping())
                .columns(view.getColumns())
                .layout(view.getLayout())
                .visibility(view.getVisibility())
                .isDefault(view.getIsDefault())
                .isShared(view.getIsShared())
                .isFavorite(view.getIsFavorite())
                .createdAt(view.getCreatedAt())
                .updatedAt(view.getUpdatedAt())
                .build();
    }

    private SavedViewSummaryResponse mapToSummary(SavedView view) {
        return SavedViewSummaryResponse.builder()
                .id(view.getId())
                .projectId(view.getProjectId())
                .name(view.getName())
                .viewType(view.getViewType())
                .isDefault(view.getIsDefault())
                .isShared(view.getIsShared())
                .isFavorite(view.getIsFavorite())
                .build();
    }
}
