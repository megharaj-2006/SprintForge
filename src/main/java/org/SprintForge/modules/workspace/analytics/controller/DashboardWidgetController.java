package org.SprintForge.modules.workspace.analytics.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.workspace.analytics.entity.DashboardWidget;
import org.SprintForge.modules.workspace.analytics.service.WidgetDataService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/widgets")
@RequiredArgsConstructor
@Validated
@Tag(name = "Dashboard Widget Controller", description = "REST endpoints for configurable dashboard widgets and real-time data feeds")
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class DashboardWidgetController {

    private final WidgetDataService widgetDataService;

    @Operation(summary = "Create dashboard widget")
    @PostMapping
    public ResponseEntity<DashboardWidget> createWidget(@RequestBody DashboardWidget widget) {
        return ResponseEntity.status(HttpStatus.CREATED).body(widgetDataService.createWidget(widget));
    }

    @Operation(summary = "Update dashboard widget configuration")
    @PatchMapping("/{id}")
    public ResponseEntity<DashboardWidget> updateWidget(@PathVariable Long id, @RequestBody DashboardWidget widget) {
        return ResponseEntity.ok(widgetDataService.updateWidget(id, widget));
    }

    @Operation(summary = "Delete dashboard widget")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWidget(@PathVariable Long id) {
        widgetDataService.deleteWidget(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get widget data payload")
    @GetMapping("/{id}/data")
    public ResponseEntity<Map<String, Object>> getWidgetData(@PathVariable Long id) {
        return ResponseEntity.ok(widgetDataService.getWidgetData(id));
    }
}
