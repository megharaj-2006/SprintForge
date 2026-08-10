package org.SprintForge.platform.communication.ports;

public interface NotificationDeliveryPort {
    void deliverInApp(String recipientId, String title, String message, String actionUrl);
}
