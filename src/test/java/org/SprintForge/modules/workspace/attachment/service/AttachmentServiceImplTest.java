package org.SprintForge.modules.workspace.attachment.service;

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
import org.SprintForge.modules.workspace.project.entity.Project;
import org.SprintForge.modules.workspace.task.entity.Task;
import org.SprintForge.modules.workspace.task.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AttachmentServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private AttachmentRepository attachmentRepository;

    @Mock
    private AttachmentMapper attachmentMapper;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    private AttachmentService attachmentService;

    private Task mockTask;
    private Attachment mockAttachment;

    @TempDir
    Path tempDir;

    @BeforeEach
    void setUp() {
        attachmentService = new AttachmentServiceImpl(
                taskRepository,
                attachmentRepository,
                attachmentMapper,
                eventPublisher
        );

        // Inject temp directory to avoid writing to real uploads folder
        ReflectionTestUtils.setField(attachmentService, "rootLocation", tempDir);

        Project mockProject = new Project();
        mockProject.setId(100L);
        mockProject.setWorkspaceId(200L);

        mockTask = new Task();
        mockTask.setId(10L);
        mockTask.setProject(mockProject);
        mockTask.setArchived(false);
        mockTask.setDeleted(false);

        mockAttachment = new Attachment();
        mockAttachment.setId(30L);
        mockAttachment.setTaskId(10L);
        mockAttachment.setWorkspaceId(200L);
        mockAttachment.setUploadedBy(1L);
        mockAttachment.setFileName("test.pdf");
        mockAttachment.setOriginalFileName("test.pdf");
        mockAttachment.setContentType("application/pdf");
        mockAttachment.setSize(100L);
        mockAttachment.setStorageKey("30L_test.pdf");
        mockAttachment.setChecksum("abc123checksum");
        mockAttachment.setArchived(false);
        mockAttachment.setDeleted(false);
    }

    @Test
    void uploadAttachment_Success() throws IOException {
        MockMultipartFile file = new MockMultipartFile(
                "file", "test.pdf", "application/pdf", "Hello PDF Content".getBytes()
        );
        UploadAttachmentRequest request = new UploadAttachmentRequest("Desc", null);

        when(taskRepository.findById(10L)).thenReturn(Optional.of(mockTask));
        when(attachmentRepository.findByTaskIdAndIsDeletedFalseAndArchivedFalse(10L)).thenReturn(new ArrayList<>());
        when(attachmentRepository.save(any(Attachment.class))).thenAnswer(inv -> {
            Attachment a = inv.getArgument(0);
            a.setId(30L);
            return a;
        });

        AttachmentResponse expectedResponse = new AttachmentResponse();
        expectedResponse.setId(30L);
        expectedResponse.setFileName("test.pdf");
        when(attachmentMapper.toResponse(any(Attachment.class))).thenReturn(expectedResponse);

        AttachmentResponse response = attachmentService.uploadAttachment(10L, request, file, 1L);

        assertNotNull(response);
        assertEquals(30L, response.getId());
        assertEquals("test.pdf", response.getFileName());

        // Verify file is actually saved in tempDir
        long fileCount = Files.list(tempDir).count();
        assertEquals(1, fileCount);

        verify(attachmentRepository).save(any(Attachment.class));
        verify(eventPublisher).publishEvent(any(AttachmentUploadedEvent.class));
    }

    @Test
    void uploadAttachment_FileTooLarge() {
        byte[] largeBytes = new byte[11 * 1024 * 1024]; // 11MB
        MockMultipartFile file = new MockMultipartFile(
                "file", "large.pdf", "application/pdf", largeBytes
        );
        UploadAttachmentRequest request = new UploadAttachmentRequest();

        when(taskRepository.findById(10L)).thenReturn(Optional.of(mockTask));

        assertThrows(BusinessRuleException.class, () -> attachmentService.uploadAttachment(10L, request, file, 1L));
        verify(attachmentRepository, never()).save(any());
    }

    @Test
    void uploadAttachment_InvalidMimeType() {
        MockMultipartFile file = new MockMultipartFile(
                "file", "test.exe", "application/octet-stream", "EXE Content".getBytes()
        );
        UploadAttachmentRequest request = new UploadAttachmentRequest();

        when(taskRepository.findById(10L)).thenReturn(Optional.of(mockTask));

        assertThrows(BusinessRuleException.class, () -> attachmentService.uploadAttachment(10L, request, file, 1L));
    }

    @Test
    void uploadAttachment_DuplicateUpload() {
        byte[] bytes = "duplicate content".getBytes();
        MockMultipartFile file = new MockMultipartFile(
                "file", "dup.pdf", "application/pdf", bytes
        );
        UploadAttachmentRequest request = new UploadAttachmentRequest();

        String expectedChecksum = "";
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(bytes);
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            expectedChecksum = sb.toString();
        } catch (Exception e) {
            fail(e);
        }

        Attachment existing = new Attachment();
        existing.setChecksum(expectedChecksum);

        when(taskRepository.findById(10L)).thenReturn(Optional.of(mockTask));
        when(attachmentRepository.findByTaskIdAndIsDeletedFalseAndArchivedFalse(10L)).thenReturn(List.of(existing));

        assertThrows(BusinessRuleException.class, () -> attachmentService.uploadAttachment(10L, request, file, 1L));
    }

    @Test
    void renameAttachment_Success() {
        RenameAttachmentRequest request = new RenameAttachmentRequest("new_name.pdf");
        when(attachmentRepository.findByIdAndIsDeletedFalse(30L)).thenReturn(Optional.of(mockAttachment));
        when(taskRepository.findById(10L)).thenReturn(Optional.of(mockTask));
        when(attachmentRepository.save(any(Attachment.class))).thenReturn(mockAttachment);

        AttachmentResponse expectedResponse = new AttachmentResponse();
        expectedResponse.setId(30L);
        expectedResponse.setFileName("new_name.pdf");
        when(attachmentMapper.toResponse(any(Attachment.class))).thenReturn(expectedResponse);

        AttachmentResponse response = attachmentService.renameAttachment(30L, request, 1L);

        assertNotNull(response);
        assertEquals("new_name.pdf", response.getFileName());
        verify(eventPublisher).publishEvent(any(AttachmentRenamedEvent.class));
    }

    @Test
    void deleteAttachment_Success() throws IOException {
        // Create simulated physical file on temp disk
        Files.write(tempDir.resolve("30L_test.pdf"), "content".getBytes());

        when(attachmentRepository.findByIdAndIsDeletedFalse(30L)).thenReturn(Optional.of(mockAttachment));
        when(taskRepository.findById(10L)).thenReturn(Optional.of(mockTask));

        attachmentService.deleteAttachment(30L, 1L);

        assertTrue(mockAttachment.isDeleted());
        assertFalse(Files.exists(tempDir.resolve("30L_test.pdf")));
        verify(attachmentRepository).save(mockAttachment);
        verify(eventPublisher).publishEvent(any(AttachmentDeletedEvent.class));
    }

    @Test
    void deleteAttachment_Forbidden() {
        when(attachmentRepository.findByIdAndIsDeletedFalse(30L)).thenReturn(Optional.of(mockAttachment));
        when(taskRepository.findById(10L)).thenReturn(Optional.of(mockTask));

        // Actor 2 is not the uploader (mockAttachment uploaded by 1)
        assertThrows(ForbiddenException.class, () -> attachmentService.deleteAttachment(30L, 2L));
        verify(attachmentRepository, never()).save(any());
    }

    @Test
    void archiveAttachment_Success() {
        when(attachmentRepository.findByIdAndIsDeletedFalse(30L)).thenReturn(Optional.of(mockAttachment));
        when(taskRepository.findById(10L)).thenReturn(Optional.of(mockTask));
        when(attachmentRepository.save(any(Attachment.class))).thenReturn(mockAttachment);

        attachmentService.archiveAttachment(30L, 1L);

        assertTrue(mockAttachment.getArchived());
        verify(eventPublisher).publishEvent(any(AttachmentArchivedEvent.class));
    }

    @Test
    void restoreAttachment_Success() {
        mockAttachment.setArchived(true);
        when(attachmentRepository.findByIdAndIsDeletedFalse(30L)).thenReturn(Optional.of(mockAttachment));
        when(taskRepository.findById(10L)).thenReturn(Optional.of(mockTask));
        when(attachmentRepository.save(any(Attachment.class))).thenReturn(mockAttachment);

        attachmentService.restoreAttachment(30L, 1L);

        assertFalse(mockAttachment.getArchived());
        verify(eventPublisher).publishEvent(any(AttachmentRestoredEvent.class));
    }

    @Test
    void downloadAttachment_Success() throws IOException {
        Files.write(tempDir.resolve("30L_test.pdf"), "hello".getBytes());
        when(attachmentRepository.findByIdAndIsDeletedFalse(30L)).thenReturn(Optional.of(mockAttachment));

        Resource resource = attachmentService.downloadAttachment(30L, 1L);

        assertNotNull(resource);
        assertTrue(resource.exists());
        assertEquals("30L_test.pdf", resource.getFilename());
    }
}
