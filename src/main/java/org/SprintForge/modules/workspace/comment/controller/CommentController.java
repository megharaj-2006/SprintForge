package org.SprintForge.modules.workspace.comment.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.workspace.comment.dto.request.CreateCommentRequest;
import org.SprintForge.modules.workspace.comment.dto.request.ReactCommentRequest;
import org.SprintForge.modules.workspace.comment.dto.request.ReplyCommentRequest;
import org.SprintForge.modules.workspace.comment.dto.request.UpdateCommentRequest;
import org.SprintForge.modules.workspace.comment.dto.response.CommentResponse;
import org.SprintForge.modules.workspace.comment.dto.response.ReactionResponse;
import org.SprintForge.modules.workspace.comment.service.CommentService;
import org.SprintForge.modules.workspace.comment.service.ReactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Validated
@Tag(name = "Comment Controller", description = "REST endpoints for managing task comments, replies, and reactions")
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class CommentController {

    private final CommentService commentService;
    private final ReactionService reactionService;

    @Operation(summary = "Create a comment on a task")
    @PostMapping("/tasks/{taskId}/comments")
    public ResponseEntity<CommentResponse> createComment(
            @PathVariable Long taskId,
            @Valid @RequestBody CreateCommentRequest request,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.ok(commentService.createComment(taskId, request, actorId));
    }

    @Operation(summary = "Get all comments for a task")
    @GetMapping("/tasks/{taskId}/comments")
    public ResponseEntity<List<CommentResponse>> getTaskComments(
            @PathVariable Long taskId,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.ok(commentService.getTaskComments(taskId, actorId));
    }

    @Operation(summary = "Update a comment")
    @PatchMapping("/comments/{commentId}")
    public ResponseEntity<CommentResponse> updateComment(
            @PathVariable Long commentId,
            @Valid @RequestBody UpdateCommentRequest request,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.ok(commentService.updateComment(commentId, request, actorId));
    }

    @Operation(summary = "Delete a comment")
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable Long commentId,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        commentService.deleteComment(commentId, actorId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Reply to a comment")
    @PostMapping("/comments/{commentId}/reply")
    public ResponseEntity<CommentResponse> reply(
            @PathVariable Long commentId,
            @Valid @RequestBody ReplyCommentRequest request,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.ok(commentService.reply(commentId, request, actorId));
    }

    @Operation(summary = "Add a reaction to a comment")
    @PostMapping("/comments/{commentId}/reactions")
    public ResponseEntity<ReactionResponse> addReaction(
            @PathVariable Long commentId,
            @Valid @RequestBody ReactCommentRequest request,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.ok(reactionService.addReaction(commentId, request.getEmoji(), actorId));
    }

    @Operation(summary = "Remove a reaction from a comment")
    @DeleteMapping("/comments/{commentId}/reactions/{reaction}")
    public ResponseEntity<Void> removeReaction(
            @PathVariable Long commentId,
            @PathVariable String reaction,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        reactionService.removeReaction(commentId, reaction, actorId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get replies to a comment")
    @GetMapping("/comments/{commentId}/replies")
    public ResponseEntity<List<CommentResponse>> getReplies(
            @PathVariable Long commentId,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.ok(commentService.getReplies(commentId, actorId));
    }
}