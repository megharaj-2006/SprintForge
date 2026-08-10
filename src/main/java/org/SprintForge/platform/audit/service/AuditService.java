package org.SprintForge.platform.audit.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.SprintForge.platform.audit.domain.AuditRecord;
import org.SprintForge.platform.audit.dto.AuditRecordResponse;
import org.SprintForge.platform.audit.dto.CreateAuditRequest;
import org.SprintForge.platform.audit.repository.PlatformAuditRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service("platformAuditService")
public class AuditService {

    private static final Logger log = LoggerFactory.getLogger(AuditService.class);
    private final PlatformAuditRecordRepository auditRecordRepository;

    @Autowired
    public AuditService(PlatformAuditRecordRepository auditRecordRepository) {
        this.auditRecordRepository = auditRecordRepository;
    }

    @Transactional
    public AuditRecordResponse recordAudit(CreateAuditRequest request) {
        AuditRecord record = AuditRecord.builder()
                .workspaceId(request.getWorkspaceId())
                .projectId(request.getProjectId())
                .entityType(request.getEntityType())
                .entityId(request.getEntityId())
                .action(request.getAction())
                .actorId(request.getActorId())
                .oldValue(request.getOldValue())
                .newValue(request.getNewValue())
                .ipAddress(request.getIpAddress())
                .userAgent(request.getUserAgent())
                .correlationId(request.getCorrelationId())
                .build();

        AuditRecord saved = auditRecordRepository.save(record);
        log.debug("Recorded audit entry id={} action={} entityType={} entityId={}", saved.getId(), saved.getAction(), saved.getEntityType(), saved.getEntityId());
        return toAuditRecordResponse(saved);
    }

    public List<AuditRecordResponse> getAuditByEntity(String entityType, String entityId, int page, int size) {
        return auditRecordRepository.findByEntityTypeAndEntityId(entityType, entityId, PageRequest.of(page, size)).stream()
                .map(this::toAuditRecordResponse)
                .collect(Collectors.toList());
    }

    public List<AuditRecordResponse> getAuditByUser(String userId, int page, int size) {
        return auditRecordRepository.findByActorId(userId, PageRequest.of(page, size)).stream()
                .map(this::toAuditRecordResponse)
                .collect(Collectors.toList());
    }

    public List<AuditRecordResponse> getAuditByWorkspace(String workspaceId, int page, int size) {
        return auditRecordRepository.findByWorkspaceId(workspaceId, PageRequest.of(page, size)).stream()
                .map(this::toAuditRecordResponse)
                .collect(Collectors.toList());
    }

    public List<AuditRecordResponse> getAllAudits(int page, int size) {
        return auditRecordRepository.findAll(PageRequest.of(page, size)).getContent().stream()
                .map(this::toAuditRecordResponse)
                .collect(Collectors.toList());
    }

    public AuditRecordResponse toAuditRecordResponse(AuditRecord record) {
        return AuditRecordResponse.builder()
                .id(record.getId())
                .workspaceId(record.getWorkspaceId())
                .projectId(record.getProjectId())
                .entityType(record.getEntityType())
                .entityId(record.getEntityId())
                .action(record.getAction())
                .actorId(record.getActorId())
                .oldValue(record.getOldValue())
                .newValue(record.getNewValue())
                .ipAddress(record.getIpAddress())
                .userAgent(record.getUserAgent())
                .correlationId(record.getCorrelationId())
                .createdAt(record.getCreatedAt())
                .build();
    }
}
