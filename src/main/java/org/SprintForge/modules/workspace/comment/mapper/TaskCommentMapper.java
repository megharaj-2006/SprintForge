package org.SprintForge.modules.workspace.comment.mapper;

import org.SprintForge.common.config.GlobalMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.SprintForge.modules.workspace.comment.dto.response.CommentResponse;
import org.SprintForge.modules.workspace.comment.dto.response.CommentSummaryResponse;
import org.SprintForge.modules.workspace.comment.dto.response.ReactionResponse;
import org.SprintForge.modules.workspace.comment.entity.TaskComment;
import org.SprintForge.modules.workspace.comment.entity.CommentReaction;

import java.util.List;

@Mapper(config = GlobalMapperConfig.class)
public interface TaskCommentMapper {

    @Mapping(target = "username", source = "username")
    @Mapping(target = "replies", source = "replies")
    @Mapping(target = "reactions", source = "reactions")
    CommentResponse toResponse(TaskComment entity, String username, List<CommentResponse> replies, List<ReactionResponse> reactions);

    @Mapping(target = "username", source = "username")
    CommentSummaryResponse toSummaryResponse(TaskComment entity, String username);

    @Mapping(target = "username", source = "username")
    ReactionResponse toResponse(CommentReaction entity, String username);
}
