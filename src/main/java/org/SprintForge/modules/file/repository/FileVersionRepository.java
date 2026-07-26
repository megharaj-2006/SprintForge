package org.SprintForge.modules.file.repository;

import org.SprintForge.modules.file.entity.FileVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface FileVersionRepository extends JpaRepository<FileVersion, Long>, JpaSpecificationExecutor<FileVersion> {
}