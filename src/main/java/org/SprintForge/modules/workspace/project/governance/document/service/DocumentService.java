package org.SprintForge.modules.workspace.project.governance.document.service;

import org.SprintForge.modules.workspace.project.governance.document.dto.request.CreateDocumentRequest;
import org.SprintForge.modules.workspace.project.governance.document.dto.request.CreateFolderRequest;
import org.SprintForge.modules.workspace.project.governance.document.dto.request.UpdateDocumentRequest;
import org.SprintForge.modules.workspace.project.governance.document.dto.response.DocumentResponse;
import org.SprintForge.modules.workspace.project.governance.document.dto.response.DocumentVersionResponse;
import org.SprintForge.modules.workspace.project.governance.document.entity.DocumentFolder;

import java.util.List;

public interface DocumentService {
    DocumentResponse createDocument(Long projectId, CreateDocumentRequest request, Long actorId);
    DocumentResponse updateDocument(Long documentId, UpdateDocumentRequest request, Long actorId);
    List<DocumentResponse> getDocuments(Long projectId, Long folderId);
    DocumentResponse getDocument(Long documentId);
    void deleteDocument(Long documentId, Long actorId);
    DocumentResponse restoreVersion(Long documentId, Integer versionNumber, Long actorId);
    DocumentResponse toggleFavorite(Long documentId, Long actorId);
    DocumentResponse togglePin(Long documentId, Long actorId);
    List<DocumentVersionResponse> getDocumentVersions(Long documentId);
    DocumentFolder createFolder(Long projectId, CreateFolderRequest request, Long actorId);
    List<DocumentFolder> getFolders(Long projectId);
}
