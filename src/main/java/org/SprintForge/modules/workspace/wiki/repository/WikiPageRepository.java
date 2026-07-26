package org.SprintForge.modules.workspace.wiki.repository;

import org.SprintForge.modules.workspace.wiki.entity.WikiPage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface WikiPageRepository extends JpaRepository<WikiPage, Long>, JpaSpecificationExecutor<WikiPage> {
}