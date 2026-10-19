import { apiClient } from './client';
import type {
  AuthResponse,
  SignupResponse,
  User,
  Workspace,
  WorkspaceMember,
  Project,
  Task,
  TaskStatus,
  Subtask,
  ChecklistItem,
  TaskComment,
  TaskAttachment,
  TimeEntry,
  Sprint,
  Epic,
  Milestone,
  Risk,
  DecisionRecord,
  ChangeRequest,
  GovernanceDocument,
  Objective,
  WorkflowDefinition,
  WorkflowExecution,
  NotificationItem,
  ActivityFeedItem,
  ApiResponse,
  UserPreferences,
  UserStatistics,
  UserActivity,
  UserSession,
} from '../types';

export const authApi = {
  signup: (data: { username: string; email: string; password: string }) =>
    apiClient.post<SignupResponse>('/api/v1/auth/signup', data).then((r) => r.data),
  verifyEmail: (data: { email: string; code: string }) =>
    apiClient.post<AuthResponse>('/api/v1/auth/verify-email', data).then((r) => r.data),
  resendVerification: (email: string) =>
    apiClient.post('/api/v1/auth/resend-verification', { email }).then((r) => r.data),
  login: (data: { usernameOrEmail: string; password: string }) =>
    apiClient.post<AuthResponse>('/api/v1/auth/login', data).then((r) => r.data),
  refresh: (refreshToken: string) =>
    apiClient.post<AuthResponse>('/api/v1/auth/refresh', { refreshToken }).then((r) => r.data),
  logout: () => apiClient.post('/api/v1/auth/logout'),
  logoutAll: () => apiClient.post('/api/v1/auth/logout-all'),
  forgotPassword: (email: string) =>
    apiClient.post('/api/v1/auth/forgot-password', { email }),
  resetPassword: (data: { token: string; newPassword: string }) =>
    apiClient.post('/api/v1/auth/reset-password', data),
  changePassword: (data: { currentPassword: string; newPassword: string }) =>
    apiClient.post('/api/v1/auth/change-password', data),
  getOAuthPublicConfig: (redirectUri?: string) =>
    apiClient
      .get<{
        providers: Record<
          string,
          { enabled: boolean; clientId: string; authorizeUrl: string; scope: string }
        >;
      }>('/api/v1/auth/oauth/config', {
        params: redirectUri ? { redirect_uri: redirectUri } : undefined,
      })
      .then((r) => r.data),
  exchangeOAuthToken: (
    provider: string,
    data: { code: string; redirectUri: string; codeVerifier?: string }
  ) =>
    apiClient.post<AuthResponse>(`/api/v1/auth/oauth/${provider}`, data).then((r) => r.data),
};

export const userApi = {
  getMe: () => apiClient.get<User>('/api/v1/users/me').then((r) => r.data),
  getUserById: (id: number) => apiClient.get<User>(`/api/v1/users/${id}`).then((r) => r.data),
  updateProfile: (data: Partial<User>) =>
    apiClient.put<User>('/api/v1/users/me', data).then((r) => r.data),
  checkUsername: (username: string) =>
    apiClient.get<{ available: boolean }>(`/api/v1/users/check-username?username=${username}`).then((r) => r.data),
  searchUsers: (query: string) =>
    apiClient.get<{ content: User[] }>(`/api/v1/users/search?query=${encodeURIComponent(query)}`).then((r) => r.data),
  uploadAvatar: (formData: FormData) =>
    apiClient.post<User>('/api/v1/users/me/avatar', formData, {
      headers: { 'Content-Type': 'multipart/form-data' },
    }).then((r) => r.data),
  deleteAvatar: () => apiClient.delete('/api/v1/users/me/avatar'),
  getPreferences: () => apiClient.get<UserPreferences>('/api/v1/users/me/preferences').then((r) => r.data),
  updatePreferences: (data: Partial<UserPreferences>) =>
    apiClient.put<UserPreferences>('/api/v1/users/me/preferences', data).then((r) => r.data),
  getStatistics: () => apiClient.get<UserStatistics>('/api/v1/users/me/statistics').then((r) => r.data),
  getActivity: (page = 0, size = 20) =>
    apiClient.get<UserActivity[]>(`/api/v1/users/me/activity?page=${page}&size=${size}`).then((r) => r.data),
  getSessions: () => apiClient.get<UserSession[]>('/api/v1/users/me/sessions').then((r) => r.data),
  revokeSession: (sessionId: number) => apiClient.delete(`/api/v1/users/me/sessions/${sessionId}`),
  revokeAllSessions: () => apiClient.delete('/api/v1/users/me/sessions'),
  exportData: () => apiClient.get('/api/v1/users/me/export').then((r) => r.data),
};

export const workspaceApi = {
  getWorkspaces: (userId?: number) => {
    const uid = userId || 1;
    return apiClient
      .get<Workspace[]>(`/api/v1/workspaces/user/${uid}`)
      .then((r) => (Array.isArray(r.data) ? r.data : []))
      .catch(async () => {
        try {
          const single = await apiClient.get<Workspace>('/api/v1/workspaces/1').then((r) => r.data);
          return single ? [single] : [];
        } catch {
          return [];
        }
      });
  },
  getWorkspaceById: (id: number) => apiClient.get<Workspace>(`/api/v1/workspaces/${id}`).then((r) => r.data),
  createWorkspace: (data: { name: string; slug?: string; description?: string; ownerId?: number }) =>
    apiClient.post<Workspace>('/api/v1/workspaces', { ...data, ownerId: data.ownerId || 1 }).then((r) => r.data),
  updateWorkspace: (id: number, data: Partial<Workspace>) =>
    apiClient.patch<Workspace>(`/api/v1/workspaces/${id}`, data).then((r) => r.data),
  deleteWorkspace: (id: number) => apiClient.delete(`/api/v1/workspaces/${id}`),
  getMembers: (workspaceId: number) =>
    apiClient.get<WorkspaceMember[]>(`/api/v1/workspaces/${workspaceId}/members`).then((r) => r.data),
  inviteMember: (workspaceId: number, data: { email: string; role: string }) =>
    apiClient.post(`/api/v1/workspaces/${workspaceId}/invitations`, data).then((r) => r.data),
  updateMemberRole: (workspaceId: number, memberId: number, role: string) =>
    apiClient.put(`/api/v1/workspaces/${workspaceId}/members/${memberId}/role`, { role }).then((r) => r.data),
  removeMember: (workspaceId: number, memberId: number) =>
    apiClient.delete(`/api/v1/workspaces/${workspaceId}/members/${memberId}`),
};

export const projectApi = {
  getProjects: (workspaceId?: number) => {
    const wsId = workspaceId || 1;
    return apiClient.get<Project[]>(`/api/v1/workspaces/${wsId}/projects`).then((r) => r.data);
  },
  getProjectById: (id: number) => apiClient.get<Project>(`/api/v1/projects/${id}`).then((r) => r.data),
  createProject: (data: {
    workspaceId: number;
    name: string;
    identifier?: string;
    projectKey?: string;
    description?: string;
    status?: string;
    visibility?: string;
  }) => {
    const payload = {
      ...data,
      projectKey: data.projectKey || data.identifier || 'ENG',
      isTemplate: false,
    };
    return apiClient.post<Project>(`/api/v1/workspaces/${data.workspaceId}/projects`, payload).then((r) => r.data);
  },
  updateProject: (id: number, data: Partial<Project>) =>
    apiClient.put<Project>(`/api/v1/projects/${id}`, data).then((r) => r.data),
  deleteProject: (id: number) => apiClient.delete(`/api/v1/projects/${id}`),
  archiveProject: (id: number) => apiClient.post<Project>(`/api/v1/projects/${id}/archive`).then((r) => r.data),
};

export const taskApi = {
  getTasksByProject: (projectId: number) =>
    apiClient.get<Task[]>(`/api/v1/projects/${projectId}/tasks`).then((r) => r.data),
  getTaskById: (id: number) => apiClient.get<Task>(`/api/v1/tasks/${id}`).then((r) => r.data),
  createTask: (projectId: number, data: {
    title: string;
    description?: string;
    status?: TaskStatus;
    priority?: string;
    type?: string;
    storyPoints?: number;
    assigneeId?: number;
    dueDate?: string;
  }) => apiClient.post<Task>(`/api/v1/projects/${projectId}/tasks`, data).then((r) => r.data),
  updateTask: (id: number, data: Partial<Task>) =>
    apiClient.patch<Task>(`/api/v1/tasks/${id}`, data).then((r) => r.data),
  deleteTask: (id: number) => apiClient.delete(`/api/v1/tasks/${id}`),
  getTasks: (params?: { projectId?: number; assigneeId?: number; status?: string }) => {
    if (params?.projectId) {
      return apiClient.get<Task[]>(`/api/v1/projects/${params.projectId}/tasks`).then((r) => r.data);
    }
    return apiClient.get<Task[]>('/api/v1/tasks', { params }).then((r) => r.data);
  },
  getMyTasks: () => apiClient.get<Task[]>('/api/v1/tasks/my-tasks').then((r) => r.data),
  moveTask: (id: number, data: { newStatus: TaskStatus; targetOrder?: number }) =>
    apiClient.post<Task>(`/api/v1/tasks/${id}/move`, data).then((r) => r.data),
  changeStatus: (id: number, newStatus: TaskStatus) =>
    apiClient.patch<Task>(`/api/v1/tasks/${id}/status`, { status: newStatus }).then((r) => r.data),
  getAllowedTransitions: (id: number) =>
    apiClient.get<{ allowedStatuses: TaskStatus[] }>(`/api/v1/tasks/${id}/allowed-transitions`).then((r) => r.data),
  archiveTask: (id: number) => apiClient.post<Task>(`/api/v1/tasks/${id}/archive`).then((r) => r.data),
  bulkUpdate: (data: { taskIds: number[]; status?: TaskStatus; priority?: string; assigneeId?: number }) =>
    apiClient.post('/api/v1/tasks/bulk', data).then((r) => r.data),
};

export const subtaskApi = {
  getSubtasks: (taskId: number) =>
    apiClient.get<Subtask[]>(`/api/v1/tasks/${taskId}/subtasks`).then((r) => r.data),
  createSubtask: (taskId: number, title: string) =>
    apiClient.post<Subtask>(`/api/v1/tasks/${taskId}/subtasks`, { title }).then((r) => r.data),
  toggleSubtask: (subtaskId: number, completed: boolean) =>
    apiClient.patch<Subtask>(`/api/v1/subtasks/${subtaskId}`, { completed }).then((r) => r.data),
  deleteSubtask: (subtaskId: number) => apiClient.delete(`/api/v1/subtasks/${subtaskId}`),
};

export const checklistApi = {
  getChecklist: (taskId: number) =>
    apiClient.get<ChecklistItem[]>(`/api/v1/tasks/${taskId}/checklist`).then((r) => r.data),
  addChecklistItem: (taskId: number, content: string) =>
    apiClient.post<ChecklistItem>(`/api/v1/tasks/${taskId}/checklist/items`, { content }).then((r) => r.data),
  toggleChecklistItem: (itemId: number, completed: boolean) =>
    apiClient.patch<ChecklistItem>(`/api/v1/checklist/items/${itemId}`, { completed }).then((r) => r.data),
  deleteChecklistItem: (itemId: number) => apiClient.delete(`/api/v1/checklist/items/${itemId}`),
};

export const commentApi = {
  getComments: (taskId: number) =>
    apiClient.get<TaskComment[]>(`/api/v1/tasks/${taskId}/comments`).then((r) => r.data),
  addComment: (taskId: number, content: string) =>
    apiClient.post<TaskComment>(`/api/v1/tasks/${taskId}/comments`, { content }).then((r) => r.data),
  deleteComment: (commentId: number) => apiClient.delete(`/api/v1/comments/${commentId}`),
  addReaction: (commentId: number, emoji: string) =>
    apiClient.post(`/api/v1/comments/${commentId}/reactions`, { emoji }).then((r) => r.data),
};

export const attachmentApi = {
  getAttachments: (taskId: number) =>
    apiClient.get<TaskAttachment[]>(`/api/v1/tasks/${taskId}/attachments`).then((r) => r.data),
  uploadAttachment: (taskId: number, formData: FormData) =>
    apiClient.post<TaskAttachment>(`/api/v1/tasks/${taskId}/attachments`, formData, {
      headers: { 'Content-Type': 'multipart/form-data' },
    }).then((r) => r.data),
  deleteAttachment: (attachmentId: number) => apiClient.delete(`/api/v1/attachments/${attachmentId}`),
};

export const timeTrackingApi = {
  getTimeEntries: (taskId: number) =>
    apiClient.get<TimeEntry[]>(`/api/v1/tasks/${taskId}/timelogs`).then((r) => r.data),
  logTime: (taskId: number, data: { durationMinutes: number; description?: string }) =>
    apiClient.post<TimeEntry>(`/api/v1/tasks/${taskId}/timelogs`, data).then((r) => r.data),
  deleteTimeEntry: (id: number) => apiClient.delete(`/api/v1/timelogs/${id}`),
};

export const sprintApi = {
  getSprintsByProject: (projectId: number) =>
    apiClient.get<Sprint[]>(`/api/v1/projects/${projectId}/sprints`).then((r) => r.data),
  createSprint: (projectId: number, data: { name: string; goal?: string; startDate?: string; endDate?: string }) =>
    apiClient.post<Sprint>(`/api/v1/projects/${projectId}/sprints`, data).then((r) => r.data),
  startSprint: (sprintId: number) =>
    apiClient.post<Sprint>(`/api/v1/sprints/${sprintId}/start`).then((r) => r.data),
  completeSprint: (sprintId: number) =>
    apiClient.post<Sprint>(`/api/v1/sprints/${sprintId}/complete`).then((r) => r.data),
  getBurndownData: (sprintId: number) =>
    apiClient.get<{ date: string; remainingPoints: number; idealPoints: number }[]>(`/api/v1/sprints/${sprintId}/burndown`).then((r) => r.data),
};

export const governanceApi = {
  getRisks: (projectId: number) =>
    apiClient.get<Risk[]>(`/api/v1/projects/${projectId}/governance/risks`).then((r) => r.data),
  createRisk: (projectId: number, data: Partial<Risk>) =>
    apiClient.post<Risk>(`/api/v1/projects/${projectId}/governance/risks`, data).then((r) => r.data),
  getDecisions: (projectId: number) =>
    apiClient.get<DecisionRecord[]>(`/api/v1/projects/${projectId}/governance/decisions`).then((r) => r.data),
  createDecision: (projectId: number, data: Partial<DecisionRecord>) =>
    apiClient.post<DecisionRecord>(`/api/v1/projects/${projectId}/governance/decisions`, data).then((r) => r.data),
  getChanges: (projectId: number) =>
    apiClient.get<ChangeRequest[]>(`/api/v1/projects/${projectId}/governance/changes`).then((r) => r.data),
  createChange: (projectId: number, data: Partial<ChangeRequest>) =>
    apiClient.post<ChangeRequest>(`/api/v1/projects/${projectId}/governance/changes`, data).then((r) => r.data),
  getDocuments: (projectId: number) =>
    apiClient.get<GovernanceDocument[]>(`/api/v1/projects/${projectId}/governance/documents`).then((r) => r.data),
  getGovernanceDashboard: (projectId: number) =>
    apiClient.get(`/api/v1/projects/${projectId}/governance/dashboard`).then((r) => r.data),
};

export const goalApi = {
  getObjectives: (workspaceId: number) =>
    apiClient.get<Objective[]>(`/api/v1/workspaces/${workspaceId}/objectives`).then((r) => r.data),
  createObjective: (data: Partial<Objective>) =>
    apiClient.post<Objective>('/api/v1/objectives', data).then((r) => r.data),
  updateKeyResult: (keyResultId: number, currentValue: number) =>
    apiClient.patch(`/api/v1/key-results/${keyResultId}`, { currentValue }).then((r) => r.data),
};

export const aiApi = {
  executePrompt: (data: { prompt: string; context?: Record<string, unknown>; model?: string }) =>
    apiClient.post<{ response: string; tokensUsed?: number }>('/api/v1/ai/execute', data).then((r) => r.data),
  summarizeSprint: (sprintId: number) =>
    apiClient.post<{ summary: string; actionItems: string[] }>(`/api/v1/ai/sprint-summary/${sprintId}`).then((r) => r.data),
  breakdownTask: (taskTitle: string) =>
    apiClient.post<{ subtasks: string[] }>('/api/v1/ai/task-breakdown', { title: taskTitle }).then((r) => r.data),
};

export const workflowApi = {
  getWorkflows: (workspaceId?: number) => {
    const url = workspaceId ? `/workflows?workspaceId=${workspaceId}` : '/workflows';
    return apiClient.get<{ content: WorkflowDefinition[] }>(url).then((r) => r.data?.content || []);
  },
  getWorkflowById: (id: number) => apiClient.get<WorkflowDefinition>(`/workflows/${id}`).then((r) => r.data),
  createWorkflow: (data: { name: string; description?: string }) =>
    apiClient.post<WorkflowDefinition>('/workflows', data).then((r) => r.data),
  publishWorkflow: (id: number) => apiClient.post(`/workflows/${id}/publish`).then((r) => r.data),
  executeWorkflow: (id: number) => apiClient.post<WorkflowExecution>(`/workflows/${id}/execute`).then((r) => r.data),
};

export const notificationApi = {
  getNotifications: () =>
    apiClient.get<ApiResponse<NotificationItem[]>>('/notifications').then((r) => r.data.data),
  markAsRead: (id: number) => apiClient.patch(`/notifications/${id}/read`),
  markAllAsRead: () => apiClient.post('/notifications/mark-all-read'),
  getActivityFeed: (workspaceId?: string) => {
    const url = workspaceId ? `/activity?workspaceId=${workspaceId}` : '/activity';
    return apiClient.get<ApiResponse<ActivityFeedItem[]>>(url).then((r) => r.data.data);
  },
};

export const searchApi = {
  globalSearch: (query: string) =>
    apiClient.get<{
      tasks: Task[];
      projects: Project[];
      documents: GovernanceDocument[];
    }>(`/api/v1/search?q=${encodeURIComponent(query)}`).then((r) => r.data),
};

export const adminApi = {
  getHealth: () => apiClient.get('/actuator/health').then((r) => r.data).catch(() => ({ status: 'UP' })),
  getMetrics: () => apiClient.get('/actuator/metrics').then((r) => r.data).catch(() => ({})),
};

export interface IntegrationProviderItem {
  id: number;
  providerKey: string;
  displayName: string;
  description: string;
  category: string;
  enabled: boolean;
  providerVersion?: string;
  icon?: string;
  documentationUrl?: string;
}

export interface IntegrationConnectionItem {
  id?: number;
  workspaceId?: number;
  providerKey: string;
  connectionName: string;
  authenticationType?: string;
  baseUrl?: string;
  credentialValue?: string;
  status?: string;
  healthStatus?: string;
}

export const integrationApi = {
  getProviders: () =>
    apiClient.get<IntegrationProviderItem[]>('/integrations/providers').then((r) => r.data).catch(() => []),
  getConnectors: () =>
    apiClient.get<any[]>('/integrations/connectors').then((r) => r.data).catch(() => []),
  getPackages: () =>
    apiClient.get<string[]>('/integrations/packages').then((r) => r.data).catch(() => []),
  createConnection: (data: IntegrationConnectionItem) =>
    apiClient.post<IntegrationConnectionItem>('/integrations/connections', data).then((r) => r.data),
  validateConnection: (id: number) =>
    apiClient.post<{ valid: boolean }>(`/integrations/connections/${id}/validate`).then((r) => r.data),
  getHealth: () =>
    apiClient.get<Record<string, any>>('/integrations/health').then((r) => r.data).catch(() => ({ status: 'UP' })),
  getStatistics: () =>
    apiClient.get<Record<string, any>>('/integrations/statistics').then((r) => r.data).catch(() => ({ activeConnections: 0 })),
};
