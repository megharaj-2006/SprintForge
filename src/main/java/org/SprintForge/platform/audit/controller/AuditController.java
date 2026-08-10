package org.SprintForge.platform.audit.controller;

import org.SprintForge.platform.audit.dto.AuditRecordResponse;
import org.SprintForge.platform.audit.dto.CreateAuditRequest;
import org.SprintForge.platform.audit.service.AuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("platformAuditController")
@RequestMapping("/audit")
public class AuditController {

    private final AuditService auditService;

    @Autowired
    public AuditController(AuditService auditService) {
        this.auditService = auditService;
    }

    @PostMapping
    public ResponseEntity<AuditRecordResponse> createAuditRecord(@RequestBody CreateAuditRequest request) {
        return ResponseEntity.ok(auditService.recordAudit(request));
    }

    @GetMapping
    public ResponseEntity<List<AuditRecordResponse>> getAllAudits(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size) {
        return ResponseEntity.ok(auditService.getAllAudits(page, size));
    }

    @GetMapping("/entity/{type}/{id}")
    public ResponseEntity<List<AuditRecordResponse>> getAuditByEntity(
            @PathVariable("type") String type,
            @PathVariable("id") String id,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size) {
        return ResponseEntity.ok(auditService.getAuditByEntity(type, id, page, size));
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<AuditRecordResponse>> getAuditByUser(
            @PathVariable("id") String id,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size) {
        return ResponseEntity.ok(auditService.getAuditByUser(id, page, size));
    }

    @GetMapping("/workspace/{id}")
    public ResponseEntity<List<AuditRecordResponse>> getAuditByWorkspace(
            @PathVariable("id") String id,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size) {
        return ResponseEntity.ok(auditService.getAuditByWorkspace(id, page, size));
    }
}
