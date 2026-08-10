package org.SprintForge.platform.communication.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommunicationDashboardResponse {
    private long totalNotificationsSent;
    private long totalUnreadNotifications;
    private long totalEmailsSent;
    private double webhookSuccessRate;
    private double averageDeliveryTimeMs;
    private long totalFailures;
    private String mostActiveUser;
    private String mostActiveProject;
}
