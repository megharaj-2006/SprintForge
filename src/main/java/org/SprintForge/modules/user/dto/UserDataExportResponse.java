package org.SprintForge.modules.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDataExportResponse {
    private UserProfileResponse profile;
    private UserPreferenceResponse preferences;
    private UserStatisticsResponse statistics;
    private List<UserActivityResponse> recentActivities;
    private LocalDateTime exportedAt;
}
