package org.SprintForge.modules.workspace.kanban.controller;

import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/boards")
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class BoardController {
}