package org.SprintForge.modules.workspace.project.governance.document.repository;

import org.SprintForge.modules.workspace.project.governance.document.entity.DocumentFolder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("governanceDocumentFolderRepository")
public interface DocumentFolderRepository extends JpaRepository<DocumentFolder, Long>, JpaSpecificationExecutor<DocumentFolder> {

    List<DocumentFolder> findByProjectIdAndIsDeletedFalse(Long projectId);
}
