package org.SprintForge.modules.workspace.comment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.SprintForge.common.exception.BusinessRuleException;
import org.SprintForge.modules.user.entity.User;
import org.SprintForge.modules.user.repository.UserRepository;
import org.SprintForge.modules.workspace.comment.entity.Mention;
import org.SprintForge.modules.workspace.comment.event.UserMentionedEvent;
import org.SprintForge.modules.workspace.comment.repository.MentionRepository;
import org.SprintForge.modules.workspace.project.service.member.ProjectMemberService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
@RequiredArgsConstructor
public class MentionServiceImpl implements MentionService {

    private static final Pattern MENTION_PATTERN = Pattern.compile("@([a-zA-Z0-9_.-]+)");

    private final UserRepository userRepository;
    private final ProjectMemberService projectMemberService;
    private final MentionRepository mentionRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional
    public void extractAndProcessMentions(Long commentId, String content, Long projectId, Long actorId) {
        if (content == null || content.isBlank()) {
            return;
        }

        // Soft-delete existing mentions for this comment first in case of update
        List<Mention> existingMentions = mentionRepository.findByCommentIdAndIsDeletedFalse(commentId);
        for (Mention m : existingMentions) {
            m.markDeleted(actorId.toString());
            mentionRepository.save(m);
        }

        Matcher matcher = MENTION_PATTERN.matcher(content);
        Set<Long> processedUserIds = new HashSet<>();

        while (matcher.find()) {
            String username = matcher.group(1);
            User user = userRepository.findByUsername(username).orElse(null);

            if (user == null) {
                log.debug("Mentioned user @{} does not exist, skipping.", username);
                continue;
            }

            if (!projectMemberService.isProjectMember(projectId, user.getId())) {
                throw new BusinessRuleException("Mentioned user @" + username + " is not a member of this project.");
            }

            if (processedUserIds.add(user.getId())) {
                Mention mention = new Mention();
                mention.setCommentId(commentId);
                mention.setMentionedUserId(user.getId());
                mention.setCreatedBy(actorId.toString());

                Mention saved = mentionRepository.save(mention);
                eventPublisher.publishEvent(new UserMentionedEvent(
                        commentId,
                        saved.getId(),
                        user.getId(),
                        actorId,
                        LocalDateTime.now()
                ));
            }
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Mention> getMentionsForComment(Long commentId) {
        return mentionRepository.findByCommentIdAndIsDeletedFalse(commentId);
    }
}
