package org.SprintForge.modules.workspace.attachment.service;

import org.SprintForge.modules.workspace.attachment.dto.request.RenameAttachmentRequest;
import org.SprintForge.modules.workspace.attachment.dto.request.UploadAttachmentRequest;
import org.SprintForge.modules.workspace.attachment.dto.response.AttachmentResponse;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AttachmentService {

    AttachmentResponse uploadAttachment(Long taskId, UploadAttachmentRequest request, MultipartFile file, Long actorId);

    AttachmentResponse renameAttachment(Long attachmentId, RenameAttachmentRequest request, Long actorId);

    void deleteAttachment(Long attachmentId, Long actorId);

    AttachmentResponse archiveAttachment(Long attachmentId, Long actorId);

    AttachmentResponse restoreAttachment(Long attachmentId, Long actorId);

    List<AttachmentResponse> getTaskAttachments(Long taskId, Long actorId);

    AttachmentResponse getAttachment(Long id, Long actorId);

    Resource downloadAttachment(Long attachmentId, Long actorId);

    long countTaskAttachments(Long taskId, Long actorId);
}
