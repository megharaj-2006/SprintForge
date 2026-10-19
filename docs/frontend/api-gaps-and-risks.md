# SprintForge — API Gaps, Inconsistencies & Integration Risks

This document records all architectural gaps, discrepancies, and integration risks identified during the Phase 0 backend audit.

---

## 1. Critical Finding: The `DEVELOPER` Role vs `@PreAuthorize` Security Guard Mismatch

> [!CAUTION]
> **Severity: CRITICAL**  
> In `AuthServiceImpl.java`, when a new user registers via `POST /api/v1/auth/signup`:
> ```java
> Role defaultRole = roleRepository.findByName("DEVELOPER")
>         .orElseGet(() -> roleRepository.save(
>                 Role.builder().name("DEVELOPER").description("Default Developer Role").build()
>         ));
> ```
> In `UserPrincipal.java`, user authorities are created as `ROLE_` + `role.getName().toUpperCase()`, yielding `ROLE_DEVELOPER`.
> However, virtually all REST controllers (TaskController, ProjectController, etc.) are annotated with:
> ```java
> @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
> ```
> **Impact:** A newly registered user with role `DEVELOPER` will be rejected with HTTP 403 Forbidden across all primary endpoints!
> **Required Resolution:** This requires an explicit decision. Either the backend seed / default signup role must be `USER`, or `@PreAuthorize` must accept `DEVELOPER`, or the user needs an initial admin assignment.

## 2. Empty Placeholder Controllers in `modules/`

> [!WARNING]
> **Severity: HIGH**  
> The audit discovered 8 controller classes that are completely empty stubs (zero endpoints):
> 1. `org.SprintForge.modules.file.controller.FileController` (`/api/v1/files`)
> 2. `org.SprintForge.modules.notification.controller.NotificationController` (`/api/v1/notifications`)
> 3. `org.SprintForge.modules.activity.controller.ActivityController` (`/api/v1/activitys`)
> 4. `org.SprintForge.modules.workspace.issue.controller.IssueController` (`/api/v1/issues`)
> 5. `org.SprintForge.modules.workspace.kanban.controller.BoardController` (`/api/v1/boards`)
> 6. `org.SprintForge.modules.admin.controller.AdminController` (`/api/v1/admins`)
> 7. `org.SprintForge.modules.search.controller.SearchController` (`/api/v1/searchs`)
> 8. `org.SprintForge.modules.dashboard.controller.DashboardController` (`/api/v1/dashboards`)
> 
> **Important Context:** These modules have actual implementations in other packages! For example:
> - File storage is implemented in `org.SprintForge.platform.storage.controller.FileStorageController`.
> - Notifications are implemented in `org.SprintForge.modules.workspace.notification.controller.NotificationController` and `PlatformNotificationController`.
> - Activity feeds are in `org.SprintForge.platform.communication.activity.ActivityFeedController`.
> - Administration is in `PlatformManagementController` and `TaskAdminController`.
> - Search is in `AdvancedSearchController` and `GlobalSearchController`.
> - Kanban boards & issues are handled directly via `TaskController`, `SprintController`, and `ProjectController`.

## 3. Duplicate Controller Class Names Across Packages

> [!NOTE]
> The project has several controllers sharing identical simple class names in different packages:
> - `NotificationController` (`workspace.notification` vs `platform.communication.notification`)
> - `GlobalSearchController` (`workspace.task` vs `platform.search`)
> - `RoadmapController` (`workspace.project.controller` vs `workspace.project.roadmap.controller`)
> - `AnalyticsController` (`workspace.project.insights.analytics` vs `platform.analytics`)
> - `ReportController` (`workspace.project.insights.reporting` vs `platform.reporting`)
> The routes are distinct, but Spring assigns explicit bean names (e.g. `@RestController("platformNotificationController")`) to avoid bean collision.

## 4. Real-Time Communication & Polling Strategy

> [!IMPORTANT]
> There is **no WebSocket, SSE (Server-Sent Events), or STOMP broker** implemented in the backend.
> All live updates (notifications, activity feeds, task board changes, AI streaming, background job execution progress) must use **smart client-side polling with exponential backoff** (via TanStack Query `refetchInterval`).

## 5. Header-Based Actor Injection (`X-User-Id` / `X-Actor-ID`)

> [!NOTE]
> Many controller methods declare `@RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId`.
> The backend `UserHeaderFilter` automatically unwraps the authenticated JWT and injects `X-User-Id` into the request.
> However, the frontend API client should also pass `X-Workspace-ID` header whenever workspace context is active.

