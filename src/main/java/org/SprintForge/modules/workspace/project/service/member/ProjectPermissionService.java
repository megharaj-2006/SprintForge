package org.SprintForge.modules.workspace.project.service.member;

public interface ProjectPermissionService {

    String PROJECT_MEMBER_MANAGE = "PROJECT_MEMBER_MANAGE";
    String SPRINT_CREATE = "SPRINT_CREATE";
    String PROJECT_DELETE = "PROJECT_DELETE";
    String PROJECT_ARCHIVE = "PROJECT_ARCHIVE";
    String TASK_MANAGE = "TASK_MANAGE";
    String TASK_ASSIGN = "TASK_ASSIGN";
    String PROJECT_VIEW = "PROJECT_VIEW";

    boolean hasPermission(Long projectId, Long userId, String permission);

    boolean canManageMembers(Long projectId, Long userId);

    boolean canCreateSprint(Long projectId, Long userId);

    boolean canDeleteProject(Long projectId, Long userId);

    boolean canArchiveProject(Long projectId, Long userId);

    boolean canManageTasks(Long projectId, Long userId);

    boolean canAssignTasks(Long projectId, Long userId);

    boolean canViewProject(Long projectId, Long userId);
}
