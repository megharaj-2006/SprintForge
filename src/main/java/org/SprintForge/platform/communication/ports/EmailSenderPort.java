package org.SprintForge.platform.communication.ports;

public interface EmailSenderPort {
    boolean sendEmail(String recipient, String subject, String body);
}
