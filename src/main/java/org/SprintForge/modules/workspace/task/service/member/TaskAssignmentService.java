package org.SprintForge.modules.workspace.task.service.member;

import org.SprintForge.modules.workspace.task.dto.response.TaskAssignmentResponse;
import org.SprintForge.modules.workspace.task.dto.response.TaskAssigneeResponse;
import org.SprintForge.modules.workspace.task.dto.response.TaskResponse;

import java.util.List;

public interface TaskAssignmentService {

    TaskAssignmentResponse assignMember(Long taskId, Long projectMemberId, Long actorId);

    List<TaskAssignmentResponse> assignMembers(Long taskId, List<Long> projectMemberIds, Long actorId);

    void unassignMember(Long taskId, Long projectMemberId, Long actorId);

    void unassignAllMembers(Long taskId, Long actorId);

    List<TaskAssignmentResponse> reassignTask(Long taskId, List<Long> projectMemberIds, Long actorId);

    List<TaskAssigneeResponse> getAssignees(Long taskId, Long actorId);

    List<TaskResponse> getAssignedTasks(Long projectMemberId, Long actorId);

    List<TaskResponse> getUnassignedTasks(Long projectId, Long actorId);

    long countAssignments(Long taskId, Long actorId);

    boolean isAssigned(Long taskId, Long projectMemberId);

    boolean isTaskAssigned(Long taskId);
}
