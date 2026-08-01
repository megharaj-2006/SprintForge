package org.SprintForge.modules.workspace.attachment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.SprintForge.common.exception.BusinessRuleException;
import org.SprintForge.common.exception.ForbiddenException;
import org.SprintForge.common.exception.ResourceNotFoundException;
import org.SprintForge.modules.workspace.attachment.dto.request.RenameAttachmentRequest;
import org.SprintForge.modules.workspace.attachment.dto.request.UploadAttachmentRequest;
import org.SprintForge.modules.workspace.attachment.dto.response.AttachmentResponse;
import org.SprintForge.modules.workspace.attachment.entity.Attachment;
import org.SprintForge.modules.workspace.attachment.event.*;
import org.SprintForge.modules.workspace.attachment.mapper.AttachmentMapper;
import org.SprintForge.modules.workspace.attachment.repository.AttachmentRepository;
import org.SprintForge.modules.workspace.task.entity.Task;
import org.SprintForge.modules.workspace.task.repository.TaskRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AttachmentServiceImpl implements AttachmentService {

    private static final long MAX_FILE_SIZE = 10 * 1024 * 1024; // 10MB
    private static final Set<String> ALLOWED_MIME_TYPES = Set.of(
            "image/jpeg", "image/png", "image/gif", "application/pdf",
            "text/plain", "text/csv", "application/zip", "application/msword",
            "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
            "application/vnd.ms-excel",
            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
    );

    private final Path rootLocation = Paths.get("uploads/attachments");

    private final TaskRepository taskRepository;
    private final AttachmentRepository attachmentRepository;
    private final AttachmentMapper attachmentMapper;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional
    public AttachmentResponse uploadAttachment(Long taskId, UploadAttachmentRequest request, MultipartFile file, Long actorId) {
        if (file == null || file.isEmpty()) {
            throw new BusinessRuleException("Uploaded file cannot be empty.");
        }

        Task task = getTaskOrThrow(taskId);
        validateTaskNotArchived(task);

        // 1. Validate File Size
        if (file.getSize() > MAX_FILE_SIZE) {
            throw new BusinessRuleException("File size exceeds the limit of 10MB.");
        }

        // 2. Validate MIME Type
        String contentType = file.getContentType();
        if (contentType == null || !ALLOWED_MIME_TYPES.contains(contentType.toLowerCase())) {
            throw new BusinessRuleException("File type '" + contentType + "' is not allowed.");
        }

        byte[] fileBytes;
        try {
            fileBytes = file.getBytes();
        } catch (IOException e) {
            throw new BusinessRuleException("Failed to read file contents.", e);
        }

        // 3. Duplicate Upload Checksum Validation
        String checksum = calculateChecksum(fileBytes);
        List<Attachment> taskAttachments = attachmentRepository.findByTaskIdAndIsDeletedFalseAndArchivedFalse(taskId);
        for (Attachment existing : taskAttachments) {
            if (checksum.equals(existing.getChecksum())) {
                throw new BusinessRuleException("This file has already been uploaded for this task.");
            }
        }

        // 4. Save to Disk
        String uniqueFileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path destinationFile = rootLocation.resolve(uniqueFileName);
        try {
            Files.createDirectories(rootLocation);
            Files.write(destinationFile, fileBytes);
        } catch (IOException e) {
            throw new BusinessRuleException("Failed to store file on disk.", e);
        }

        // 5. Save Metadata to DB
        Attachment attachment = new Attachment();
        attachment.setWorkspaceId(task.getProject().getWorkspaceId());
        attachment.setTaskId(taskId);
        attachment.setUploadedBy(actorId);
        attachment.setFileName(file.getOriginalFilename());
        attachment.setOriginalFileName(file.getOriginalFilename());
        attachment.setContentType(contentType);
        attachment.setSize(file.getSize());
        attachment.setStorageKey(uniqueFileName);
        attachment.setDownloadUrl("/api/v1/attachments/" + uniqueFileName + "/download"); // Simulated URL
        attachment.setChecksum(checksum);
        attachment.setArchived(false);
        attachment.setCreatedBy(actorId.toString());

        Attachment saved = attachmentRepository.save(attachment);
        eventPublisher.publishEvent(new AttachmentUploadedEvent(saved.getId(), actorId, LocalDateTime.now()));

        return attachmentMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public AttachmentResponse renameAttachment(Long attachmentId, RenameAttachmentRequest request, Long actorId) {
        Attachment attachment = getAttachmentOrThrow(attachmentId);
        Task task = getTaskOrThrow(attachment.getTaskId());
        validateTaskNotArchived(task);

        String oldName = attachment.getFileName();
        attachment.setFileName(request.getFileName());
        attachment.setUpdatedBy(actorId.toString());

        Attachment saved = attachmentRepository.save(attachment);
        eventPublisher.publishEvent(new AttachmentRenamedEvent(
                saved.getId(),
                oldName,
                request.getFileName(),
                actorId,
                LocalDateTime.now()
        ));

        return attachmentMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public void deleteAttachment(Long attachmentId, Long actorId) {
        Attachment attachment = getAttachmentOrThrow(attachmentId);
        Task task = getTaskOrThrow(attachment.getTaskId());
        validateTaskNotArchived(task);

        // 1. Only uploader (or admin) can delete.
        // For simplicity, we enforce that the actorId matches the uploaderId.
        if (!attachment.getUploadedBy().equals(actorId)) {
            throw new ForbiddenException("Only the uploader can delete this attachment.");
        }

        attachment.markDeleted(actorId.toString());
        attachmentRepository.save(attachment);

        // Delete from Disk
        Path fileLocation = rootLocation.resolve(attachment.getStorageKey());
        try {
            Files.deleteIfExists(fileLocation);
        } catch (IOException e) {
            log.error("Failed to delete physical file from disk: {}", fileLocation, e);
        }

        eventPublisher.publishEvent(new AttachmentDeletedEvent(attachmentId, actorId, LocalDateTime.now()));
    }

    @Override
    @Transactional
    public AttachmentResponse archiveAttachment(Long attachmentId, Long actorId) {
        Attachment attachment = getAttachmentOrThrow(attachmentId);
        Task task = getTaskOrThrow(attachment.getTaskId());
        validateTaskNotArchived(task);

        attachment.setArchived(true);
        attachment.setUpdatedBy(actorId.toString());

        Attachment saved = attachmentRepository.save(attachment);
        eventPublisher.publishEvent(new AttachmentArchivedEvent(attachmentId, actorId, LocalDateTime.now()));

        return attachmentMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public AttachmentResponse restoreAttachment(Long attachmentId, Long actorId) {
        Attachment attachment = getAttachmentOrThrow(attachmentId);
        Task task = getTaskOrThrow(attachment.getTaskId());
        validateTaskNotArchived(task);

        attachment.setArchived(false);
        attachment.setUpdatedBy(actorId.toString());

        Attachment saved = attachmentRepository.save(attachment);
        eventPublisher.publishEvent(new AttachmentRestoredEvent(attachmentId, actorId, LocalDateTime.now()));

        return attachmentMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AttachmentResponse> getTaskAttachments(Long taskId, Long actorId) {
        getTaskOrThrow(taskId);
        List<Attachment> list = attachmentRepository.findByTaskIdAndIsDeletedFalseAndArchivedFalse(taskId);
        return attachmentMapper.toResponseList(list);
    }

    @Override
    @Transactional(readOnly = true)
    public AttachmentResponse getAttachment(Long id, Long actorId) {
        Attachment attachment = getAttachmentOrThrow(id);
        return attachmentMapper.toResponse(attachment);
    }

    @Override
    @Transactional(readOnly = true)
    public Resource downloadAttachment(Long attachmentId, Long actorId) {
        Attachment attachment = getAttachmentOrThrow(attachmentId);
        Path fileLocation = rootLocation.resolve(attachment.getStorageKey());
        try {
            Resource resource = new UrlResource(fileLocation.toUri());
            if (resource.exists() && resource.isReadable()) {
                return resource;
            } else {
                throw new ResourceNotFoundException("File is not readable or does not exist on disk.");
            }
        } catch (IOException e) {
            throw new ResourceNotFoundException("Failed to access file: " + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public long countTaskAttachments(Long taskId, Long actorId) {
        getTaskOrThrow(taskId);
        return attachmentRepository.countByTaskIdAndIsDeletedFalseAndArchivedFalse(taskId);
    }

    private Task getTaskOrThrow(Long taskId) {
        return taskRepository.findById(taskId)
                .filter(t -> !t.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with ID: " + taskId));
    }

    private Attachment getAttachmentOrThrow(Long id) {
        return attachmentRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Attachment not found with ID: " + id));
    }

    private void validateTaskNotArchived(Task task) {
        if (Boolean.TRUE.equals(task.getArchived())) {
            throw new BusinessRuleException("Archived tasks cannot be modified.");
        }
    }

    private String calculateChecksum(byte[] bytes) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(bytes);
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            throw new BusinessRuleException("Could not calculate file checksum", e);
        }
    }
}
