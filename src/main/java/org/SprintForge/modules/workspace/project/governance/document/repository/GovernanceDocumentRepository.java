package org.SprintForge.modules.workspace.project.governance.document.repository;

import org.SprintForge.modules.workspace.project.governance.document.entity.GovernanceDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("governanceDocumentRepository")
public interface GovernanceDocumentRepository extends JpaRepository<GovernanceDocument, Long>, JpaSpecificationExecutor<GovernanceDocument> {

    List<GovernanceDocument> findByProjectIdAndIsDeletedFalse(Long projectId);

    List<GovernanceDocument> findByProjectIdAndFolderIdAndIsDeletedFalse(Long projectId, Long folderId);

    List<GovernanceDocument> findByProjectIdAndIsPinnedTrueAndIsDeletedFalse(Long projectId);

    List<GovernanceDocument> findByProjectIdAndIsFavoriteTrueAndIsDeletedFalse(Long projectId);

    long countByProjectIdAndIsDeletedFalse(Long projectId);
}
