package org.SprintForge.modules.activity.controller;

import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/activitys")
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class ActivityController {
}