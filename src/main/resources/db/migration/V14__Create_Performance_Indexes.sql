-- ============================================================================
-- SprintForge PostgreSQL Migration V14: Production Performance Indexes
-- Adds composite indexes on high-cardinality foreign keys paired with is_deleted.
-- ============================================================================

-- Workspaces & Workspace Members
CREATE INDEX IF NOT EXISTS idx_workspace_members_ws_deleted ON workspace_members(workspace_id, is_deleted);
CREATE INDEX IF NOT EXISTS idx_workspace_members_user_deleted ON workspace_members(user_id, is_deleted);

-- Projects
CREATE INDEX IF NOT EXISTS idx_projects_ws_deleted ON projects(workspace_id, is_deleted);
CREATE INDEX IF NOT EXISTS idx_project_members_proj_deleted ON project_members(project_id, is_deleted);
CREATE INDEX IF NOT EXISTS idx_project_members_user_deleted ON project_members(user_id, is_deleted);

-- Sprints & Epics
CREATE INDEX IF NOT EXISTS idx_sprints_proj_deleted ON sprints(project_id, is_deleted);
CREATE INDEX IF NOT EXISTS idx_epics_proj_deleted ON epics(project_id, is_deleted);

-- Tasks
CREATE INDEX IF NOT EXISTS idx_tasks_proj_deleted ON tasks(project_id, is_deleted);
CREATE INDEX IF NOT EXISTS idx_tasks_sprint_deleted ON tasks(sprint_id, is_deleted);
CREATE INDEX IF NOT EXISTS idx_tasks_epic_deleted ON tasks(epic_id, is_deleted);
CREATE INDEX IF NOT EXISTS idx_task_assignments_task_user ON task_assignments(task_id, user_id);

-- Workflows & AI Executions
CREATE INDEX IF NOT EXISTS idx_workflows_ws_deleted ON workflows(workspace_id, is_deleted);
CREATE INDEX IF NOT EXISTS idx_workflow_executions_wf_status ON workflow_executions(workflow_id, status);
CREATE INDEX IF NOT EXISTS idx_ai_executions_prov_model ON ai_executions(provider_key, model_key);

-- Management & Integration
CREATE INDEX IF NOT EXISTS idx_mgmt_feature_flags_key ON mgmt_feature_flags(flag_key);
CREATE INDEX IF NOT EXISTS idx_integration_conn_ws_status ON integration_connections(workspace_id, status);
