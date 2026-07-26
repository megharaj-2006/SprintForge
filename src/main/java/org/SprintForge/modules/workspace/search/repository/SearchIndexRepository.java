package org.SprintForge.modules.workspace.search.repository;

import org.SprintForge.modules.workspace.search.entity.SearchIndex;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SearchIndexRepository extends JpaRepository<SearchIndex, Long>, JpaSpecificationExecutor<SearchIndex> {
}