package org.SprintForge.modules.workspace.inbox.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.workspace.inbox.entity.InboxItem;
import org.SprintForge.modules.workspace.inbox.service.InboxService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/me/inbox")
@RequiredArgsConstructor
@Validated
@Tag(name = "Inbox Controller", description = "REST endpoints for Linear-inspired notification & task update inbox")
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class InboxController {

    private final InboxService inboxService;

    @Operation(summary = "Get active user inbox items")
    @GetMapping
    public ResponseEntity<List<InboxItem>> getInbox(
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.ok(inboxService.getUserInbox(actorId));
    }

    @Operation(summary = "Mark inbox item as read")
    @PatchMapping("/{id}/read")
    public ResponseEntity<InboxItem> markAsRead(
            @PathVariable Long id,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.ok(inboxService.markAsRead(id, actorId));
    }

    @Operation(summary = "Archive inbox item")
    @PatchMapping("/{id}/archive")
    public ResponseEntity<InboxItem> archiveItem(
            @PathVariable Long id,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.ok(inboxService.archiveItem(id, actorId));
    }

    @Operation(summary = "Snooze inbox item for specified minutes")
    @PatchMapping("/{id}/snooze")
    public ResponseEntity<InboxItem> snoozeItem(
            @PathVariable Long id,
            @RequestParam(value = "minutes", defaultValue = "60") int minutes,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.ok(inboxService.snoozeItem(id, minutes, actorId));
    }
}
