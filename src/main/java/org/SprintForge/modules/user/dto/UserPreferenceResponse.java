package org.SprintForge.modules.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserPreferenceResponse {

    @NotBlank(message = "Theme cannot be blank")
    private String theme;

    @NotBlank(message = "Language cannot be blank")
    private String language;

    @NotBlank(message = "Timezone cannot be blank")
    private String timezone;

    private String dateFormat;

    private boolean emailNotifications;
    private boolean pushNotifications;
    private boolean inAppNotifications;
    private boolean taskReminderEnabled;
}
