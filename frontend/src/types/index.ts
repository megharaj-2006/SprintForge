export type TaskStatus = 'BACKLOG' | 'TODO' | 'IN_PROGRESS' | 'IN_REVIEW' | 'DONE' | 'ARCHIVED' | 'CANCELLED';
export type TaskPriority = 'LOW' | 'MEDIUM' | 'HIGH' | 'CRITICAL' | 'URGENT';
export type TaskType = 'TASK' | 'BUG' | 'FEATURE' | 'SPIKE' | 'IMPROVEMENT';

export interface User {
  id: number;
  username: string;
  email: string;
  role?: string;
  roleName?: string;
  roles?: string[];
  fullName?: string;
  displayName?: string;
  firstName?: string;
  lastName?: string;
  avatarUrl?: string;
  avatar?: string;
  profilePicture?: string;
  coverImage?: string;
  headline?: string;
  bio?: string;
  location?: string;
  skills?: string;
  githubUrl?: string;
  linkedinUrl?: string;
  websiteUrl?: string;
  department?: string;
  jobTitle?: string;
  preferences?: UserPreferences;
  stats?: UserStatistics;
  lastLogin?: string;
  lastActiveAt?: string;
  createdAt?: string;
  updatedAt?: string;
}

export interface UserPreferences {
  theme: string;
  language: string;
  timezone: string;
  dateFormat?: string;
  emailNotifications?: boolean;
  pushNotifications?: boolean;
  inAppNotifications?: boolean;
  taskReminderEnabled?: boolean;
}

export interface UserStatistics {
  userId?: number;
  totalWorkspaces?: number;
  totalProjects?: number;
  assignedTasksCount?: number;
  completedTasksCount?: number;
  totalCommentsCount?: number;
}

export interface UserActivity {
  id: number;
  userId: number;
  action: string;
  entityType?: string;
  entityId?: number;
  description: string;
  timestamp: string;
}

export interface UserSession {
  id: number;
  userId: number;
  ipAddress: string;
  userAgent: string;
  deviceType: string;
  browser: string;
  os: string;
  location: string;
  isCurrent: boolean;
  isActive: boolean;
  lastActivityAt: string;
  expiresAt?: string;
  createdAt?: string;
}

export interface AuthResponse {
  token: string;
  refreshToken: string;
  username: string;
  email: string;
  role: string;
}

export interface SignupResponse {
  verificationRequired: boolean;
  email: string;
  message: string;
}


export interface Workspace {
  id: number;
  name: string;
  slug: string;
  description?: string;
  avatarUrl?: string;
  ownerId?: number;
  createdAt?: string;
  updatedAt?: string;
}

export interface WorkspaceMember {
  id: number;
  workspaceId: number;
  userId: number;
  user: User;
  role: string;
  joinedAt: string;
}

export interface Project {
  id: number;
  workspaceId: number;
  name: string;
  identifier: string; // e.g. "SPR"
  key?: string; // Alias for identifier
  description?: string;
  status: 'PLANNING' | 'ACTIVE' | 'ON_HOLD' | 'COMPLETED' | 'ARCHIVED';
  visibility: 'PUBLIC' | 'PRIVATE';
  leadId?: number;
  lead?: User;
  startDate?: string;
  targetDate?: string;
  taskCount?: number;
  completedTaskCount?: number;
}

export interface Task {
  id: number;
  projectId: number;
  identifier: string; // e.g. "SPR-102"
  taskKey?: string; // Alias for identifier
  title: string;
  description?: string;
  status: TaskStatus;
  priority: TaskPriority;
  type: TaskType;
  storyPoints?: number;
  assigneeId?: number;
  assignee?: User;
  reporterId?: number;
  reporter?: User;
  sprintId?: number;
  epicId?: number;
  dueDate?: string;
  estimatedHours?: number;
  loggedHours?: number;
  labels?: string[];
  createdAt: string;
  updatedAt: string;
}

export interface Subtask {
  id: number;
  taskId: number;
  title: string;
  completed: boolean;
  orderIndex: number;
}

export interface ChecklistItem {
  id: number;
  checklistId: number;
  content: string;
  completed: boolean;
  orderIndex: number;
}

export interface TaskComment {
  id: number;
  taskId: number;
  userId: number;
  user: User;
  content: string;
  createdAt: string;
  reactions?: { emoji: string; count: number; users: string[] }[];
}

export interface TaskAttachment {
  id: number;
  taskId: number;
  fileName: string;
  fileSize: number;
  contentType: string;
  downloadUrl: string;
  createdAt: string;
}

export interface TimeEntry {
  id: number;
  taskId: number;
  userId: number;
  user?: User;
  durationMinutes: number;
  description?: string;
  loggedAt: string;
}

export interface Sprint {
  id: number;
  projectId: number;
  name: string;
  goal?: string;
  status: 'PLANNED' | 'ACTIVE' | 'COMPLETED' | 'CANCELLED';
  startDate?: string;
  endDate?: string;
  totalPoints?: number;
  completedPoints?: number;
}

export interface Epic {
  id: number;
  projectId: number;
  name: string;
  summary?: string;
  color?: string;
  status: string;
  progressPercentage?: number;
}

export interface Milestone {
  id: number;
  projectId: number;
  title: string;
  dueDate: string;
  completed: boolean;
  description?: string;
}

export interface Risk {
  id: number;
  projectId: number;
  title: string;
  description?: string;
  severity: 'LOW' | 'MEDIUM' | 'HIGH' | 'CRITICAL';
  probability: 'RARE' | 'UNLIKELY' | 'POSSIBLE' | 'LIKELY' | 'ALMOST_CERTAIN';
  impactScore: number;
  status: 'IDENTIFIED' | 'ANALYZING' | 'MITIGATING' | 'MONITORING' | 'CLOSED';
  mitigationPlan?: string;
  ownerId?: number;
  owner?: User;
}

export interface DecisionRecord {
  id: number;
  projectId: number;
  title: string;
  context: string;
  decision: string;
  consequences?: string;
  status: 'PROPOSED' | 'ACCEPTED' | 'SUPERSEDED' | 'REJECTED';
  authorId?: number;
  author?: User;
  createdAt: string;
}

export interface ChangeRequest {
  id: number;
  projectId: number;
  title: string;
  justification: string;
  impactAssessment?: string;
  status: 'PENDING' | 'APPROVED' | 'REJECTED' | 'REVISED';
  submittedBy?: User;
  reviewedBy?: User;
  createdAt: string;
}

export interface GovernanceDocument {
  id: number;
  projectId: number;
  title: string;
  category: string;
  version: string;
  fileUrl: string;
  uploadedBy?: User;
  updatedAt: string;
}

export interface Objective {
  id: number;
  workspaceId: number;
  projectId?: number;
  title: string;
  description?: string;
  status: 'NOT_STARTED' | 'IN_PROGRESS' | 'COMPLETED' | 'AT_RISK';
  progressPercentage: number;
  targetDate?: string;
  keyResults: KeyResult[];
}

export interface KeyResult {
  id: number;
  objectiveId: number;
  title: string;
  currentValue: number;
  targetValue: number;
  unit: string;
  progressPercentage: number;
}

export interface WorkflowDefinition {
  id: number;
  workspaceId: number;
  name: string;
  description?: string;
  version: number;
  status: 'DRAFT' | 'PUBLISHED' | 'ARCHIVED';
  nodesCount: number;
  updatedAt: string;
}

export interface WorkflowExecution {
  id: string;
  workflowId: number;
  workflowName: string;
  status: 'PENDING' | 'RUNNING' | 'COMPLETED' | 'FAILED' | 'CANCELLED';
  startedAt: string;
  durationMs?: number;
  errorMessage?: string;
}

export interface NotificationItem {
  id: number;
  userId: number;
  title: string;
  message: string;
  read: boolean;
  type: 'TASK_ASSIGNED' | 'STATUS_CHANGED' | 'MENTION' | 'DEADLINE_APPROACHING' | 'SYSTEM';
  link?: string;
  createdAt: string;
}

export interface ActivityFeedItem {
  id: number;
  actorId: number;
  actorName: string;
  action: string;
  targetType: string;
  targetName: string;
  timestamp: string;
  entityType?: string;
  entityId?: number;
  createdAt?: string;
}

export interface ApiResponse<T> {
  success: boolean;
  message: string;
  data: T;
  timestamp: string;
  status: number;
}

export interface ApiErrorResponse {
  status: number;
  error: string;
  message: string;
  path: string;
  code: string;
}

export interface ValidationErrorResponse extends ApiErrorResponse {
  errors: { field: string; rejectedValue: unknown; message: string }[];
  validationErrors: Record<string, string>;
}
