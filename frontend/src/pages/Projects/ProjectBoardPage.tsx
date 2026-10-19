import React, { useState, useEffect, useMemo } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { useQuery } from '@tanstack/react-query';
import { useWorkspaceStore } from '../../store/useWorkspaceStore';
import { useUIStore } from '../../store/useUIStore';
import { taskApi, projectApi, sprintApi } from '../../api/endpoints';
import { KanbanBoard } from '../../components/kanban/KanbanBoard';
import { TaskDataTable } from '../../components/tasks/TaskDataTable';
import { RoadmapTimeline } from '../../components/projects/RoadmapTimeline';
import { ViewSwitcher, type ActiveView } from '../../components/projects/ViewSwitcher';
import { Button } from '../../components/ui/Button';
import type { Task, TaskPriority, TaskStatus } from '../../types';
import {
  Plus,
  Search,
  Filter,
  Layers,
  Sparkles,
  RefreshCw,
  FolderKanban,
  AlertCircle,
} from 'lucide-react';

export function ProjectBoardPage() {
  const { projectId } = useParams<{ projectId?: string }>();
  const navigate = useNavigate();
  const { currentProject, projects, selectProject } = useWorkspaceStore();
  const { setCreateTaskModalOpen } = useUIStore();

  const [activeView, setActiveView] = useState<ActiveView>('board');
  const [searchQuery, setSearchQuery] = useState('');
  const [statusFilter, setStatusFilter] = useState<string>('ALL');
  const [priorityFilter, setPriorityFilter] = useState<string>('ALL');

  // Sync route param with Workspace store
  useEffect(() => {
    if (projectId) {
      const p = projects.find((proj) => String(proj.id) === projectId);
      if (p) selectProject(p);
    } else if (projects.length > 0 && !currentProject) {
      selectProject(projects[0]);
    }
  }, [projectId, projects, currentProject, selectProject]);

  const activeProjectId = projectId ? Number(projectId) : currentProject?.id;

  // Fetch Project Tasks with 10s adaptive polling (DEC-004)
  const {
    data: tasks = [],
    isLoading: isTasksLoading,
    refetch,
    isFetching,
  } = useQuery<Task[]>({
    queryKey: ['tasks', activeProjectId],
    queryFn: async () => {
      if (!activeProjectId) return [];
      const res = await taskApi.getTasks({ projectId: activeProjectId });
      return Array.isArray(res) ? res : ((res as any)?.content || []);
    },
    enabled: Boolean(activeProjectId),
    refetchInterval: 10000,
  });

  // Fetch Sprints for Roadmap
  const { data: sprints = [] } = useQuery({
    queryKey: ['sprints', activeProjectId],
    queryFn: async () => {
      if (!activeProjectId) return [];
      const res = await sprintApi.getSprintsByProject(activeProjectId);
      return Array.isArray(res) ? res : [];
    },
    enabled: Boolean(activeProjectId),
  });

  // Client-side filtering for ultra-responsive search
  const filteredTasks = useMemo(() => {
    return tasks.filter((task) => {
      const matchesSearch =
        searchQuery === '' ||
        task.title.toLowerCase().includes(searchQuery.toLowerCase()) ||
        task.identifier?.toLowerCase().includes(searchQuery.toLowerCase()) ||
        task.taskKey?.toLowerCase().includes(searchQuery.toLowerCase()) ||
        task.description?.toLowerCase().includes(searchQuery.toLowerCase());

      const matchesStatus =
        statusFilter === 'ALL' || task.status === statusFilter;

      const matchesPriority =
        priorityFilter === 'ALL' || task.priority === priorityFilter;

      return matchesSearch && matchesStatus && matchesPriority;
    });
  }, [tasks, searchQuery, statusFilter, priorityFilter]);

  if (!activeProjectId && projects.length === 0) {
    return (
      <div className="flex flex-col items-center justify-center h-[calc(100vh-44px)] p-6 text-center">
        <FolderKanban className="w-12 h-12 text-muted-foreground/40 mb-3" />
        <h2 className="text-base font-semibold text-foreground">No Projects Found</h2>
        <p className="text-xs text-muted-foreground mt-1 max-w-sm">
          Create or select a project from your workspace to begin tracking issues and sprints.
        </p>
        <Button
          onClick={() => navigate('/projects')}
          className="mt-4 text-xs h-8"
        >
          Go to All Projects
        </Button>
      </div>
    );
  }

  return (
    <div className="flex flex-col h-full overflow-hidden select-none">
      {/* Top Project Subheader */}
      <div className="px-5 py-3 border-b border-border/80 bg-surface/40 flex flex-wrap items-center justify-between gap-3 shrink-0">
        <div className="flex items-center gap-3 min-w-0">
          <div className="w-8 h-8 rounded-lg bg-surface-raised border border-border flex items-center justify-center font-bold text-xs text-foreground shrink-0 shadow-2xs">
            {currentProject?.identifier || currentProject?.key || 'PRJ'}
          </div>
          <div className="min-w-0">
            <div className="flex items-center gap-2">
              <h1 className="text-sm font-semibold tracking-tight text-foreground truncate">
                {currentProject?.name || 'Project Board'}
              </h1>
              {isFetching && (
                <RefreshCw className="w-3 h-3 text-muted-foreground animate-spin" />
              )}
            </div>
            <p className="text-[11px] text-muted-foreground truncate">
              {currentProject?.description || 'Active sprint backlog and issue tracking board'}
            </p>
          </div>
        </div>

        {/* Action Controls & View Switcher */}
        <div className="flex items-center gap-2.5">
          <ViewSwitcher activeView={activeView} onViewChange={setActiveView} />

          <Button
            onClick={() => setCreateTaskModalOpen(true)}
            size="sm"
            variant="primary"
            className="h-7 text-xs px-2.5 font-medium shadow-xs"
          >
            <Plus className="w-3.5 h-3.5 mr-1" />
            <span>New Issue</span>
            <kbd className="ml-1.5 px-1 py-0.2 bg-foreground/10 text-current rounded text-[10px]">
              c
            </kbd>
          </Button>
        </div>
      </div>

      {/* Filter and Quick Omnibar */}
      <div className="px-5 py-2 border-b border-border/60 bg-surface/20 flex items-center justify-between gap-3 text-xs shrink-0">
        <div className="flex items-center gap-2 flex-1 max-w-md">
          <div className="relative w-full">
            <Search className="w-3.5 h-3.5 text-muted-foreground/60 absolute left-2.5 top-2" />
            <input
              type="text"
              value={searchQuery}
              onChange={(e) => setSearchQuery(e.target.value)}
              placeholder="Filter by title, key, or description..."
              className="w-full h-7 pl-8 pr-3 rounded-md border border-border/80 bg-background/80 text-xs placeholder:text-muted-foreground/50 focus:outline-none focus:ring-1 focus:ring-primary focus:border-primary"
            />
          </div>
        </div>

        <div className="flex items-center gap-2">
          {/* Status Select */}
          <select
            value={statusFilter}
            onChange={(e) => setStatusFilter(e.target.value)}
            className="h-7 px-2 rounded-md border border-border/80 bg-background/80 text-xs text-muted-foreground focus:outline-none focus:ring-1 focus:ring-primary"
          >
            <option value="ALL">All Statuses</option>
            <option value="TODO">To Do</option>
            <option value="IN_PROGRESS">In Progress</option>
            <option value="IN_REVIEW">In Review</option>
            <option value="DONE">Done</option>
            <option value="CANCELLED">Cancelled</option>
          </select>

          {/* Priority Select */}
          <select
            value={priorityFilter}
            onChange={(e) => setPriorityFilter(e.target.value)}
            className="h-7 px-2 rounded-md border border-border/80 bg-background/80 text-xs text-muted-foreground focus:outline-none focus:ring-1 focus:ring-primary"
          >
            <option value="ALL">All Priorities</option>
            <option value="URGENT">Urgent</option>
            <option value="HIGH">High</option>
            <option value="MEDIUM">Medium</option>
            <option value="LOW">Low</option>
          </select>
        </div>
      </div>

      {/* Main Board View Container */}
      <div className="flex-1 p-5 overflow-y-auto">
        {isTasksLoading ? (
          <div className="flex items-center justify-center h-48 text-muted-foreground text-xs">
            <RefreshCw className="w-4 h-4 animate-spin mr-2" />
            Loading project issues...
          </div>
        ) : filteredTasks.length === 0 && tasks.length === 0 ? (
          <div className="border border-dashed border-border rounded-xl p-12 text-center max-w-lg mx-auto my-8 bg-surface/30">
            <FolderKanban className="w-10 h-10 text-muted-foreground/40 mx-auto mb-3" />
            <h3 className="text-sm font-semibold text-foreground">No issues created yet</h3>
            <p className="text-xs text-muted-foreground mt-1 mb-4">
              Get started by creating your first issue in this project.
            </p>
            <Button
              onClick={() => setCreateTaskModalOpen(true)}
              size="sm"
              className="text-xs h-8"
            >
              <Plus className="w-3.5 h-3.5 mr-1" />
              Create First Issue (c)
            </Button>
          </div>
        ) : (
          <>
            {activeView === 'board' && (
              <KanbanBoard tasks={filteredTasks} onTaskMoved={() => refetch()} />
            )}
            {activeView === 'list' && (
              <TaskDataTable tasks={filteredTasks} onTasksUpdated={() => refetch()} />
            )}
            {activeView === 'timeline' && (
              <RoadmapTimeline tasks={filteredTasks} sprints={sprints} />
            )}
          </>
        )}
      </div>
    </div>
  );
}

export default ProjectBoardPage;
