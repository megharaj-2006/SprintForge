package org.SprintForge.platform.communication.activity;

import lombok.RequiredArgsConstructor;
import org.SprintForge.common.dto.ApiResponse;
import org.SprintForge.platform.communication.dto.ActivityFeedResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("platformActivityFeedController")
@RequestMapping
@RequiredArgsConstructor
public class ActivityFeedController {

    private final ActivityQueryService activityQueryService;

    @GetMapping("/activity")
    public ResponseEntity<ApiResponse<List<ActivityFeedResponse>>> getGlobalActivity(
            @RequestParam(required = false) String workspaceId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        List<ActivityFeedResponse> list = (workspaceId != null && !workspaceId.isBlank())
                ? activityQueryService.getRecentWorkspaceActivity(workspaceId, page, size)
                : activityQueryService.getAllGlobalActivity(page, size);
        return ResponseEntity.ok(ApiResponse.success(list));
    }

    @GetMapping("/projects/{id}/activity")
    public ResponseEntity<ApiResponse<List<ActivityFeedResponse>>> getProjectActivity(
            @PathVariable("id") String projectId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        List<ActivityFeedResponse> list = activityQueryService.getRecentProjectActivity(projectId, page, size);
        return ResponseEntity.ok(ApiResponse.success(list));
    }

    @GetMapping("/tasks/{id}/activity")
    public ResponseEntity<ApiResponse<List<ActivityFeedResponse>>> getTaskActivity(
            @PathVariable("id") String taskId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        List<ActivityFeedResponse> list = activityQueryService.getEntityActivity("TASK", taskId, page, size);
        return ResponseEntity.ok(ApiResponse.success(list));
    }

    @GetMapping("/users/{id}/activity")
    public ResponseEntity<ApiResponse<List<ActivityFeedResponse>>> getUserActivity(
            @PathVariable("id") String userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        List<ActivityFeedResponse> list = activityQueryService.getUserActivity(userId, page, size);
        return ResponseEntity.ok(ApiResponse.success(list));
    }
}
