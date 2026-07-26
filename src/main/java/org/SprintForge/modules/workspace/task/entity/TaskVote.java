package org.SprintForge.modules.workspace.task.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.SprintForge.common.entity.SoftDeleteEntity;
import org.SprintForge.modules.workspace.task.entity.enums.TaskVoteType;

@Entity
@Table(name = "task_votes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskVote extends SoftDeleteEntity {

    @Column(name = "task_id", nullable = false)
    private Long taskId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "vote_type")
    private TaskVoteType voteType = TaskVoteType.UPVOTE;
}

