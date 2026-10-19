# SprintForge — Page Inventory & Layout Specifications

Total Defined Application Pages: **21 primary functional views**.
Every single one of the 642 audited backend endpoints is consumed by one or more of these pages or supporting global workflows.

---

## 1. Login & Authentication

- **Route Path:** `/login`
- **Purpose:** User authentication, JWT token issuance, session initiation with Three.js ambient background.
- **Key Components:** `LoginForm`, `ThreeAmbientMesh`, `SocialAuthRow`, `PasswordInput`
- **Consumed API Endpoints:** API-002 (POST /api/v1/auth/login), API-003 (POST /api/v1/auth/refresh)
- **Authorized Roles:** `Public`
- **State Handling & Feedback:** Loading spinner on submit button; inline error toast on invalid credentials.

---

## 2. Registration & Signup

- **Route Path:** `/signup`
- **Purpose:** Account creation, username availability verification, password confirmation.
- **Key Components:** `SignupForm`, `UsernameAsyncValidator`, `ThreeAmbientMesh`, `PasswordStrengthMeter`
- **Consumed API Endpoints:** API-001 (POST /api/v1/auth/signup), API-619 (GET /api/v1/users/check-username)
- **Authorized Roles:** `Public`
- **State Handling & Feedback:** Debounced async validation checkmark/error for username; submission spinner.

---

## 3. Password Recovery & Reset

- **Route Path:** `/forgot-password, /reset-password`
- **Purpose:** Self-service password recovery flow with token validation.
- **Key Components:** `ForgotPasswordForm`, `ResetPasswordForm`, `TokenExpiryBanner`
- **Consumed API Endpoints:** API-006 (POST /api/v1/auth/forgot-password), API-007 (POST /api/v1/auth/reset-password)
- **Authorized Roles:** `Public`
- **State Handling & Feedback:** Success state shows 'Reset email sent'; error shows invalid/expired reset token.

---

## 4. User Profile & Security Settings

- **Route Path:** `/settings/profile, /settings/security`
- **Purpose:** Profile details, avatar upload/crop, password change, theme and language preferences.
- **Key Components:** `UserProfileCard`, `AvatarDropzone`, `ChangePasswordForm`, `PreferenceThemeSelector`
- **Consumed API Endpoints:** API-618 (GET /api/v1/users/me), API-620 (PUT /api/v1/users/profile), API-615 (POST /api/v1/users/avatar), API-616 (DELETE /api/v1/users/avatar), API-008 (POST /api/v1/auth/change-password), API-609 to API-614 (Preferences)
- **Authorized Roles:** `USER, ADMIN, DEVELOPER`
- **State Handling & Feedback:** Optimistic avatar preview; toast on password update; theme switches instantly.

---

## 5. Workspaces Hub & Settings

- **Route Path:** `/workspaces, /workspace/settings`
- **Purpose:** Workspace switcher, organization management, invitations, member list, and custom roles.
- **Key Components:** `WorkspaceGrid`, `WorkspaceCreateModal`, `MemberTable`, `RolePermissionsMatrix`, `InviteModal`
- **Consumed API Endpoints:** API-567 to API-592 (WorkspaceController), API-535 to API-545 (WorkspaceMemberController), API-546 to API-555 (WorkspaceRoleController), API-529 to API-534 (WorkspaceInvitationController)
- **Authorized Roles:** `ADMIN, USER`
- **State Handling & Feedback:** Skeleton table while fetching members; role change triggers confirmation dialog.

---

## 6. Teams & Organization Directory

- **Route Path:** `/workspace/directory, /workspace/teams`
- **Purpose:** Departments tree, team structures, user directory, business calendars.
- **Key Components:** `DepartmentTree`, `TeamCardGrid`, `DirectorySearchTable`, `CalendarEditor`
- **Consumed API Endpoints:** API-556 to API-563 (DepartmentController), API-593 to API-602 (TeamController), API-603 to API-605 (DirectoryController), API-524 to API-528 (BusinessCalendarController)
- **Authorized Roles:** `USER, ADMIN`
- **State Handling & Feedback:** Empty state illustration if no teams defined; inline editing of team attributes.

---

## 7. Projects Hub & Overview

- **Route Path:** `/projects`
- **Purpose:** Browse active, archived, and starred projects; view status, leads, and category tags.
- **Key Components:** `ProjectCardGrid`, `ProjectDataTable`, `CreateProjectModal`, `ProjectFilterBar`
- **Consumed API Endpoints:** API-163 to API-208 (ProjectController)
- **Authorized Roles:** `USER, ADMIN`
- **State Handling & Feedback:** Shimmer loading cards; empty state prompts 'Create your first project (`c`)'.

---

## 8. Project Workspace & Kanban Board

- **Route Path:** `/projects/:projectId/board`
- **Purpose:** Interactive Kanban board with Drag-and-Drop task status columns (`TODO` → `IN_PROGRESS` → `DONE`).
- **Key Components:** `KanbanBoard`, `KanbanColumn`, `TaskCard`, `ViewSwitcher`, `FilterToolbar`, `TaskDetailDrawer`
- **Consumed API Endpoints:** API-321 (GET /api/v1/projects/{projectId}/tasks), API-345 (POST /api/v1/tasks/{taskId}/move), API-341 (PATCH /api/v1/tasks/{taskId}/status), API-342 (GET allowed-transitions)
- **Authorized Roles:** `USER, ADMIN`
- **State Handling & Feedback:** Optimistic drag animation; automatic rollback with error toast if transition disallowed.

---

## 9. Project Backlog & List View

- **Route Path:** `/projects/:projectId/list`
- **Purpose:** Ultra-dense data table of all tasks with inline quick-pickers for status, priority, and assignees.
- **Key Components:** `TaskDataTable`, `InlineAssigneePicker`, `InlinePriorityBadge`, `BulkActionBar`
- **Consumed API Endpoints:** API-321 (GET tasks), API-323 (PATCH /api/v1/tasks/{taskId}), API-334 to API-340 (BulkTaskController)
- **Authorized Roles:** `USER, ADMIN`
- **State Handling & Feedback:** `j`/`k` keyboard selection ring; sticky multi-select bulk action bar at bottom.

---

## 10. Project Roadmap & Timeline

- **Route Path:** `/projects/:projectId/roadmap`
- **Purpose:** Interactive Gantt timeline showing sprints, epics, milestones, and release schedules.
- **Key Components:** `GanttTimeline`, `MilestoneBar`, `EpicRow`, `DependencyConnectorLine`
- **Consumed API Endpoints:** API-209 to API-211 (RoadmapController), API-230 to API-232 (strategicRoadmapController), API-138 to API-140 (EpicController)
- **Authorized Roles:** `USER, ADMIN`
- **State Handling & Feedback:** Smooth horizontal pan/zoom; drag milestone handles to reschedule.

---

## 11. Active Sprint & Sprint Planning

- **Route Path:** `/projects/:projectId/sprints`
- **Purpose:** Backlog grooming, sprint creation, start/complete sprint workflows, sprint burndown chart.
- **Key Components:** `SprintPlanningList`, `SprintBurndownChart`, `StartSprintModal`, `CompleteSprintDialog`
- **Consumed API Endpoints:** API-308 to API-320 (SprintController)
- **Authorized Roles:** `USER, ADMIN`
- **State Handling & Feedback:** Burndown chart re-renders smoothly with Recharts; modal confirmation to close sprint.

---

## 12. Team Workload & Capacity Grid

- **Route Path:** `/projects/:projectId/workload`
- **Purpose:** Capacity planning and assigned hours heatmap per engineer across sprints.
- **Key Components:** `WorkloadHeatmap`, `CapacityBar`, `ResourceHourTable`
- **Consumed API Endpoints:** API-212 to API-214 (WorkloadController), API-224 to API-226 (ResourceAllocationController)
- **Authorized Roles:** `USER, ADMIN`
- **State Handling & Feedback:** Color-coded capacity warnings (green < 80%, yellow 80-100%, red > 100% capacity).

---

## 13. Project Governance & Risk Register

- **Route Path:** `/projects/:projectId/governance/risks`
- **Purpose:** 5x5 Risk Matrix, risk severity assessment, mitigation tracking, and audit log.
- **Key Components:** `RiskMatrix5x5`, `RiskTable`, `CreateRiskModal`, `MitigationProgress`
- **Consumed API Endpoints:** API-155 to API-162 (RiskController)
- **Authorized Roles:** `USER, ADMIN`
- **State Handling & Feedback:** Interactive risk scatter plot; filtering by severity level.

---

## 14. Decisions & Change Requests

- **Route Path:** `/projects/:projectId/governance/decisions, /projects/:projectId/governance/changes`
- **Purpose:** Architectural Decision Records (ADRs) log and formal project change approval pipeline.
- **Key Components:** `DecisionLogTable`, `CreateDecisionModal`, `ChangeRequestPipeline`, `ApprovalReviewDrawer`
- **Consumed API Endpoints:** API-145 to API-151 (DecisionController), API-140 to API-144 (ChangeController), API-131 to API-137 (ApprovalController)
- **Authorized Roles:** `USER, ADMIN`
- **State Handling & Feedback:** Change status pills (Pending, Approved, Rejected); revision request modal.

---

## 15. Governance Documentation & Dashboard

- **Route Path:** `/projects/:projectId/governance/docs, /projects/:projectId/governance/dashboard`
- **Purpose:** Project document repository, version control, and composite governance health score.
- **Key Components:** `DocumentExplorer`, `DocumentUploadZone`, `GovernanceHealthScorecard`, `ComplianceGauges`
- **Consumed API Endpoints:** API-152 to API-154 (DocumentController), API-141 to API-142 (GovernanceDashboardController)
- **Authorized Roles:** `USER, ADMIN`
- **State Handling & Feedback:** Document version preview; multi-version download links; health scorecard radar chart.

---

## 16. Strategic Goals & OKR Hub

- **Route Path:** `/goals`
- **Purpose:** Company & project objectives, key result progress sliders, alignment tree.
- **Key Components:** `ObjectiveTree`, `KeyResultProgressBar`, `CreateGoalModal`, `ProgressUpdateDialog`
- **Consumed API Endpoints:** API-124 to API-130 (GoalController), API-227 to API-232 (ObjectiveController), API-117 to API-123 (KeyResultController)
- **Authorized Roles:** `USER, ADMIN`
- **State Handling & Feedback:** Percentage sliders update key results optimistically with instant progress rollups.

---

## 17. Analytics & Executive Cockpit

- **Route Path:** `/analytics`
- **Purpose:** Velocity trends, cycle time distribution, portfolio health, forecasting, productivity.
- **Key Components:** `ExecutiveDashboardCockpit`, `VelocityChart`, `ForecastConfidenceBand`, `ProductivityHeatmap`
- **Consumed API Endpoints:** API-215 to API-223 (AnalyticsController, MetricsController, ForecastController, PortfolioController, ExecutiveDashboardController)
- **Authorized Roles:** `USER, ADMIN`
- **State Handling & Feedback:** Time-range picker (7d, 30d, 90d, custom); interactive tooltip crosshairs on Recharts.

---

## 18. SprintForge AI Automation Studio

- **Route Path:** `/ai/studio`
- **Purpose:** Prompt template library, AI execution policies, autonomous agent skill runner, context synthesis.
- **Key Components:** `PromptEditor`, `ModelSelector`, `SkillExecutionRunner`, `ExecutionHistoryLog`
- **Consumed API Endpoints:** API-009 to API-023 (AIPlatformController, AIExecutionController)
- **Authorized Roles:** `USER, ADMIN`
- **State Handling & Feedback:** Real-time streaming text reveal for generated responses; execution policy toggle.

---

## 19. Visual Workflow Studio

- **Route Path:** `/workflows, /workflows/:workflowId`
- **Purpose:** Interactive node canvas with Three.js ambient background to design, simulate, publish, and monitor workflows.
- **Key Components:** `WorkflowCanvas`, `NodeCatalogPalette`, `SimulationRunner`, `ExecutionTraceDrawer`
- **Consumed API Endpoints:** API-621 to API-642 (WorkflowController, NodeCatalogController, SimulationController, ExecutionController, MarketplaceController)
- **Authorized Roles:** `USER, ADMIN`
- **State Handling & Feedback:** Connecting nodes with SVG bezier curves; step-by-step debugger highlight during simulation.

---

## 20. App Integrations Marketplace

- **Route Path:** `/settings/integrations`
- **Purpose:** Connect third-party developer tools (GitHub, Jira, Slack), configure webhooks, inspect logs.
- **Key Components:** `ConnectorGrid`, `OAuthConnectionModal`, `WebhookManager`, `DeliveryLogDrawer`
- **Consumed API Endpoints:** API-041 to API-052 (ConnectorPackageController, IntegrationController), API-564 to API-566 (WorkspaceWebhookController)
- **Authorized Roles:** `ADMIN, USER`
- **State Handling & Feedback:** Status badges (Connected, Degraded, Disconnected); 'Test ping' webhook action button.

---

## 21. Platform Administration & System Console

- **Route Path:** `/admin/platform, /admin/scheduler, /admin/events`
- **Purpose:** Platform licenses, feature flag rollouts, tenant policies, background job queue monitoring, event outbox logs.
- **Key Components:** `LicenseTierCard`, `FeatureFlagToggleTable`, `JobSchedulerTable`, `EventOutboxStream`
- **Consumed API Endpoints:** API-053 to API-065 (PlatformManagementController), API-473 to API-484 (SchedulerController, JobControllers), API-453 to API-457 (EventLogController)
- **Authorized Roles:** `ADMIN`
- **State Handling & Feedback:** Rollout percentage sliders (0-100%); 'Trigger Job Now' and 'Retry Dead Job' admin controls.

---

