package org.SprintForge.modules.workspace.workspace.team.repository;

import org.SprintForge.modules.workspace.workspace.team.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long>, JpaSpecificationExecutor<Team> {
    List<Team> findByWorkspaceIdAndIsDeletedFalse(Long workspaceId);
    List<Team> findByDepartmentIdAndIsDeletedFalse(Long departmentId);
    List<Team> findByLeadMemberIdAndIsDeletedFalse(Long leadMemberId);
    List<Team> findByWorkspaceIdAndIsArchivedAndIsDeletedFalse(Long workspaceId, boolean isArchived);
    boolean existsByWorkspaceIdAndNameAndIsDeletedFalse(Long workspaceId, String name);
    long countByDepartmentIdAndIsDeletedFalse(Long departmentId);
    
    @Query("SELECT t FROM Team t WHERE t.workspaceId = :workspaceId AND t.name LIKE %:query% AND t.isDeleted = false")
    List<Team> searchByName(@Param("workspaceId") Long workspaceId, @Param("query") String query);
}
