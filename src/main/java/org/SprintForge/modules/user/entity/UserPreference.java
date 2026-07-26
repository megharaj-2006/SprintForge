package org.SprintForge.modules.user.entity;

import jakarta.persistence.*;
import lombok.*;
import org.SprintForge.common.entity.SoftDeleteEntity;

@Entity
@Table(name = "user_preferences", indexes = {
        @Index(name = "idx_user_pref_user_id", columnList = "user_id")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPreference extends SoftDeleteEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Builder.Default
    @Column(nullable = false)
    private String theme = "LIGHT"; // LIGHT, DARK, SYSTEM

    @Builder.Default
    @Column(nullable = false)
    private String language = "en"; // en, es, fr, etc.

    @Builder.Default
    @Column(nullable = false)
    private String timezone = "UTC";

    @Builder.Default
    @Column(name = "date_format", nullable = false)
    private String dateFormat = "yyyy-MM-dd";

    @Builder.Default
    @Column(name = "email_notifications", nullable = false)
    private boolean emailNotifications = true;

    @Builder.Default
    @Column(name = "push_notifications", nullable = false)
    private boolean pushNotifications = true;

    @Builder.Default
    @Column(name = "in_app_notifications", nullable = false)
    private boolean inAppNotifications = true;

    @Builder.Default
    @Column(name = "task_reminder_enabled", nullable = false)
    private boolean taskReminderEnabled = true;
}

