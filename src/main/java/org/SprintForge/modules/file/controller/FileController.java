package org.SprintForge.modules.file.controller;

import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/files")
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class FileController {
}