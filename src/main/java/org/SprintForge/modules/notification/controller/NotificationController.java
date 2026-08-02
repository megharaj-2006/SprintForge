package org.SprintForge.modules.notification.controller;

import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("globalNotificationController")
@RequestMapping("/api/v1/notifications")
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class NotificationController {
}