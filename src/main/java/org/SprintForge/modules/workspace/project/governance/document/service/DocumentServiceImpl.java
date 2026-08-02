package org.SprintForge.modules.workspace.project.governance.document.service;

import lombok.RequiredArgsConstructor;
import org.SprintForge.common.exception.ResourceNotFoundException;
import org.SprintForge.modules.workspace.project.entity.Project;
import org.SprintForge.modules.workspace.project.governance.document.dto.request.CreateDocumentRequest;
import org.SprintForge.modules.workspace.project.governance.document.dto.request.CreateFolderRequest;
import org.SprintForge.modules.workspace.project.governance.document.dto.request.UpdateDocumentRequest;
import org.SprintForge.modules.workspace.project.governance.document.dto.response.DocumentResponse;
import org.SprintForge.modules.workspace.project.governance.document.dto.response.DocumentVersionResponse;
import org.SprintForge.modules.workspace.project.governance.document.entity.DocumentFolder;
import org.SprintForge.modules.workspace.project.governance.document.entity.DocumentVersion;
import org.SprintForge.modules.workspace.project.governance.document.entity.GovernanceDocument;
import org.SprintForge.modules.workspace.project.governance.document.entity.enums.DocumentFormat;
import org.SprintForge.modules.workspace.project.governance.document.repository.DocumentFolderRepository;
import org.SprintForge.modules.workspace.project.governance.document.repository.DocumentVersionRepository;
import org.SprintForge.modules.workspace.project.governance.document.repository.GovernanceDocumentRepository;
import org.SprintForge.modules.workspace.project.repository.ProjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {

    private final GovernanceDocumentRepository documentRepository;
    private final DocumentFolderRepository folderRepository;
    private final DocumentVersionRepository versionRepository;
    private final ProjectRepository projectRepository;

    @Override
    @Transactional
    public DocumentResponse createDocument(Long projectId, CreateDocumentRequest request, Long actorId) {
        Project project = projectRepository.findById(projectId)
                .filter(p -> !p.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with ID: " + projectId));

        GovernanceDocument document = new GovernanceDocument();
        document.setProjectId(projectId);
        document.setFolderId(request.getFolderId());
        document.setTitle(request.getTitle());
        document.setSlug(generateSlug(request.getTitle()));
        document.setContent(request.getContent() != null ? request.getContent() : "");
        document.setFormat(request.getFormat() != null ? request.getFormat() : DocumentFormat.MARKDOWN);
        document.setVersionNumber(1);
        document.setAuthorId(actorId);
        document.setLastEditorId(actorId);
        document.setIsPinned(false);
        document.setIsFavorite(false);
        document.setIsArchived(false);

        GovernanceDocument saved = documentRepository.save(document);

        DocumentVersion version = new DocumentVersion();
        version.setDocumentId(saved.getId());
        version.setVersionNumber(1);
        version.setContent(saved.getContent());
        version.setChangeSummary("Initial document creation");
        versionRepository.save(version);

        return toResponse(saved);
    }

    @Override
    @Transactional
    public DocumentResponse updateDocument(Long documentId, UpdateDocumentRequest request, Long actorId) {
        GovernanceDocument document = documentRepository.findById(documentId)
                .filter(d -> !d.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Document not found with ID: " + documentId));

        boolean contentChanged = false;
        if (request.getContent() != null && !request.getContent().equals(document.getContent())) {
            contentChanged = true;
            document.setContent(request.getContent());
        }

        if (request.getTitle() != null) {
            document.setTitle(request.getTitle());
            document.setSlug(generateSlug(request.getTitle()));
        }
        if (request.getFolderId() != null) document.setFolderId(request.getFolderId());
        if (request.getFormat() != null) document.setFormat(request.getFormat());
        if (request.getIsPinned() != null) document.setIsPinned(request.getIsPinned());
        if (request.getIsFavorite() != null) document.setIsFavorite(request.getIsFavorite());
        if (request.getIsArchived() != null) document.setIsArchived(request.getIsArchived());
        document.setLastEditorId(actorId);

        if (contentChanged) {
            int newVersionNum = document.getVersionNumber() + 1;
            document.setVersionNumber(newVersionNum);

            DocumentVersion version = new DocumentVersion();
            version.setDocumentId(document.getId());
            version.setVersionNumber(newVersionNum);
            version.setContent(document.getContent());
            version.setChangeSummary(request.getChangeSummary() != null ? request.getChangeSummary() : "Updated content");
            versionRepository.save(version);
        }

        GovernanceDocument saved = documentRepository.save(document);
        return toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DocumentResponse> getDocuments(Long projectId, Long folderId) {
        List<GovernanceDocument> docs = (folderId != null)
                ? documentRepository.findByProjectIdAndFolderIdAndIsDeletedFalse(projectId, folderId)
                : documentRepository.findByProjectIdAndIsDeletedFalse(projectId);

        return docs.stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public DocumentResponse getDocument(Long documentId) {
        GovernanceDocument document = documentRepository.findById(documentId)
                .filter(d -> !d.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Document not found with ID: " + documentId));
        return toResponse(document);
    }

    @Override
    @Transactional
    public void deleteDocument(Long documentId, Long actorId) {
        GovernanceDocument document = documentRepository.findById(documentId)
                .filter(d -> !d.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Document not found with ID: " + documentId));

        document.markDeleted(actorId != null ? actorId.toString() : "SYSTEM");
        documentRepository.save(document);
    }

    @Override
    @Transactional
    public DocumentResponse restoreVersion(Long documentId, Integer versionNumber, Long actorId) {
        GovernanceDocument document = documentRepository.findById(documentId)
                .filter(d -> !d.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Document not found with ID: " + documentId));

        DocumentVersion version = versionRepository.findByDocumentIdAndVersionNumberAndIsDeletedFalse(documentId, versionNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Document version " + versionNumber + " not found"));

        document.setContent(version.getContent());
        int newVersionNum = document.getVersionNumber() + 1;
        document.setVersionNumber(newVersionNum);
        document.setLastEditorId(actorId);

        DocumentVersion restoredVersion = new DocumentVersion();
        restoredVersion.setDocumentId(document.getId());
        restoredVersion.setVersionNumber(newVersionNum);
        restoredVersion.setContent(version.getContent());
        restoredVersion.setChangeSummary("Restored from version v" + versionNumber);
        versionRepository.save(restoredVersion);

        GovernanceDocument saved = documentRepository.save(document);
        return toResponse(saved);
    }

    @Override
    @Transactional
    public DocumentResponse toggleFavorite(Long documentId, Long actorId) {
        GovernanceDocument document = documentRepository.findById(documentId)
                .filter(d -> !d.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Document not found with ID: " + documentId));

        document.setIsFavorite(!Boolean.TRUE.equals(document.getIsFavorite()));
        GovernanceDocument saved = documentRepository.save(document);
        return toResponse(saved);
    }

    @Override
    @Transactional
    public DocumentResponse togglePin(Long documentId, Long actorId) {
        GovernanceDocument document = documentRepository.findById(documentId)
                .filter(d -> !d.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Document not found with ID: " + documentId));

        document.setIsPinned(!Boolean.TRUE.equals(document.getIsPinned()));
        GovernanceDocument saved = documentRepository.save(document);
        return toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DocumentVersionResponse> getDocumentVersions(Long documentId) {
        return versionRepository.findByDocumentIdAndIsDeletedFalseOrderByVersionNumberDesc(documentId)
                .stream()
                .map(v -> {
                    Long creatorId = null;
                    if (v.getCreatedBy() != null) {
                        try {
                            creatorId = Long.parseLong(v.getCreatedBy());
                        } catch (NumberFormatException ignored) {}
                    }
                    return DocumentVersionResponse.builder()
                            .id(v.getId())
                            .documentId(v.getDocumentId())
                            .versionNumber(v.getVersionNumber())
                            .content(v.getContent())
                            .changeSummary(v.getChangeSummary())
                            .createdBy(creatorId)
                            .createdAt(v.getCreatedAt())
                            .build();
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public DocumentFolder createFolder(Long projectId, CreateFolderRequest request, Long actorId) {
        DocumentFolder folder = new DocumentFolder();
        folder.setProjectId(projectId);
        folder.setName(request.getName());
        folder.setParentId(request.getParentId());
        return folderRepository.save(folder);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DocumentFolder> getFolders(Long projectId) {
        return folderRepository.findByProjectIdAndIsDeletedFalse(projectId);
    }

    private String generateSlug(String title) {
        if (title == null) return "";
        return title.toLowerCase().replaceAll("[^a-z0-9]+", "-").replaceAll("^-|-$", "");
    }

    private DocumentResponse toResponse(GovernanceDocument doc) {
        Long creatorId = null;
        if (doc.getCreatedBy() != null) {
            try {
                creatorId = Long.parseLong(doc.getCreatedBy());
            } catch (NumberFormatException ignored) {}
        }

        return DocumentResponse.builder()
                .id(doc.getId())
                .projectId(doc.getProjectId())
                .folderId(doc.getFolderId())
                .title(doc.getTitle())
                .slug(doc.getSlug())
                .content(doc.getContent())
                .format(doc.getFormat())
                .versionNumber(doc.getVersionNumber())
                .authorId(doc.getAuthorId())
                .lastEditorId(doc.getLastEditorId())
                .isPinned(doc.getIsPinned())
                .isFavorite(doc.getIsFavorite())
                .isArchived(doc.getIsArchived())
                .createdBy(creatorId)
                .createdAt(doc.getCreatedAt())
                .updatedAt(doc.getUpdatedAt())
                .build();
    }
}
