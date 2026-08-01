package org.SprintForge.modules.workspace.analytics.service;

import lombok.RequiredArgsConstructor;
import org.SprintForge.common.exception.ResourceNotFoundException;
import org.SprintForge.modules.workspace.analytics.entity.DashboardWidget;
import org.SprintForge.modules.workspace.analytics.repository.DashboardWidgetRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class WidgetDataService {

    private final DashboardWidgetRepository widgetRepository;

    @Transactional
    public DashboardWidget createWidget(DashboardWidget widget) {
        return widgetRepository.save(widget);
    }

    @Transactional
    public DashboardWidget updateWidget(Long id, DashboardWidget updated) {
        DashboardWidget existing = widgetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Widget not found with ID: " + id));
        if (updated.getTitle() != null) existing.setTitle(updated.getTitle());
        if (updated.getConfiguration() != null) existing.setConfiguration(updated.getConfiguration());
        if (updated.getPosition() != null) existing.setPosition(updated.getPosition());
        if (updated.getRefreshInterval() != null) existing.setRefreshInterval(updated.getRefreshInterval());
        return widgetRepository.save(existing);
    }

    @Transactional
    public void deleteWidget(Long id) {
        DashboardWidget widget = widgetRepository.findById(id).orElse(null);
        if (widget != null) {
            widget.setDeleted(true);
            widgetRepository.save(widget);
        }
    }

    @Transactional(readOnly = true)
    public Map<String, Object> getWidgetData(Long widgetId) {
        DashboardWidget widget = widgetRepository.findById(widgetId)
                .orElseThrow(() -> new ResourceNotFoundException("Widget not found with ID: " + widgetId));

        return Map.of(
                "widgetId", widgetId,
                "type", widget.getWidgetType(),
                "title", widget.getTitle(),
                "data", Map.of("count", 12, "status", "HEALTHY")
        );
    }
}
