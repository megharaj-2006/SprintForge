package org.SprintForge.modules.workspace.notification.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.SprintForge.common.entity.SoftDeleteEntity;

import java.time.LocalDateTime;

@Entity
@Table(name = "notification_preferences")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationPreference extends SoftDeleteEntity {

    @Column(name = "user_id", nullable = false, unique = true)
    private Long userId;

    @Column(name = "enable_in_app")
    private Boolean enableInApp = true;

    @Column(name = "enable_email")
    private Boolean enableEmail = true;

    @Column(name = "enable_mentions")
    private Boolean enableMentions = true;

    @Column(name = "enable_comments")
    private Boolean enableComments = true;

    @Column(name = "enable_assignments")
    private Boolean enableAssignments = true;

    @Column(name = "enable_watchers")
    private Boolean enableWatchers = true;

    @Column(name = "do_not_disturb")
    private Boolean doNotDisturb = false;

    @Column(name = "mute_until")
    private LocalDateTime muteUntil;

    @Column(name = "digest_frequency")
    private String digestFrequency = "INSTANT"; // INSTANT, DAILY, WEEKLY
}
