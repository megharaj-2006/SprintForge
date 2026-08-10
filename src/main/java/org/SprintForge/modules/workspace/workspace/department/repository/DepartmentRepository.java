package org.SprintForge.modules.workspace.workspace.department.repository;

import org.SprintForge.modules.workspace.workspace.department.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long>, JpaSpecificationExecutor<Department> {
    List<Department> findByWorkspaceIdAndIsDeletedFalse(Long workspaceId);
    List<Department> findByHeadMemberIdAndIsDeletedFalse(Long headMemberId);
    List<Department> findByWorkspaceIdAndIsArchivedAndIsDeletedFalse(Long workspaceId, boolean isArchived);
    boolean existsByWorkspaceIdAndNameAndIsDeletedFalse(Long workspaceId, String name);
    
    @Query("SELECT d FROM Department d WHERE d.workspaceId = :workspaceId AND d.name LIKE %:query% AND d.isDeleted = false")
    List<Department> searchByName(@Param("workspaceId") Long workspaceId, @Param("query") String query);
}
