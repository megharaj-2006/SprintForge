package org.SprintForge.modules.workspace.comment.service;

import org.SprintForge.modules.workspace.comment.dto.request.CreateCommentRequest;
import org.SprintForge.modules.workspace.comment.dto.request.ReplyCommentRequest;
import org.SprintForge.modules.workspace.comment.dto.request.UpdateCommentRequest;
import org.SprintForge.modules.workspace.comment.dto.response.CommentResponse;

import java.util.List;

public interface CommentService {

    CommentResponse createComment(Long taskId, CreateCommentRequest request, Long actorId);

    CommentResponse updateComment(Long commentId, UpdateCommentRequest request, Long actorId);

    void deleteComment(Long commentId, Long actorId);

    CommentResponse reply(Long commentId, ReplyCommentRequest request, Long actorId);

    CommentResponse edit(Long commentId, UpdateCommentRequest request, Long actorId);

    List<CommentResponse> getTaskComments(Long taskId, Long actorId);

    List<CommentResponse> getReplies(Long commentId, Long actorId);
}