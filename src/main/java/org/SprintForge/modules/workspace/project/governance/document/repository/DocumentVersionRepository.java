package org.SprintForge.modules.workspace.project.governance.document.repository;

import org.SprintForge.modules.workspace.project.governance.document.entity.DocumentVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("governanceDocumentVersionRepository")
public interface DocumentVersionRepository extends JpaRepository<DocumentVersion, Long>, JpaSpecificationExecutor<DocumentVersion> {

    List<DocumentVersion> findByDocumentIdAndIsDeletedFalseOrderByVersionNumberDesc(Long documentId);

    Optional<DocumentVersion> findByDocumentIdAndVersionNumberAndIsDeletedFalse(Long documentId, Integer versionNumber);
}
