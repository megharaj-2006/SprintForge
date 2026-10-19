import React, { useState } from 'react';
import { useQuery } from '@tanstack/react-query';
import { taskApi } from '../../api/endpoints';
import { useUIStore } from '../../store/useUIStore';
import { useAuthStore } from '../../store/useAuthStore';
import { TaskDataTable } from '../../components/tasks/TaskDataTable';
import { Button } from '../../components/ui/Button';
import type { Task } from '../../types';
import {
  CheckSquare,
  Clock,
  AlertTriangle,
  CheckCircle2,
  Plus,
  RefreshCw,
  Filter,
} from 'lucide-react';

export function MyTasksPage() {
  const { user } = useAuthStore();
  const { setCreateTaskModalOpen } = useUIStore();
  const [activeTab, setActiveTab] = useState<'assigned' | 'created' | 'urgent'>('assigned');

  const {
    data: myTasks = [],
    isLoading,
    refetch,
    isFetching,
  } = useQuery<Task[]>({
    queryKey: ['myTasks'],
    queryFn: async () => {
      const res = await taskApi.getMyTasks();
      return Array.isArray(res) ? res : [];
    },
    refetchInterval: 10000,
  });

  const filteredTasks = myTasks.filter((task) => {
    if (activeTab === 'urgent') {
      return task.priority === 'URGENT' || task.priority === 'CRITICAL' || task.priority === 'HIGH';
    }
    if (activeTab === 'created') {
      return task.reporterId === user?.id;
    }
    return true; // Default assigned
  });

  const dueSoonCount = myTasks.filter((t) => t.dueDate && t.status !== 'DONE').length;
  const completedCount = myTasks.filter((t) => t.status === 'DONE').length;
  const urgentCount = myTasks.filter((t) => (t.priority === 'URGENT' || t.priority === 'CRITICAL' || t.priority === 'HIGH') && t.status !== 'DONE').length;

  return (
    <div className="flex flex-col h-full overflow-y-auto select-none p-6 max-w-7xl mx-auto">
      {/* Header */}
      <div className="flex flex-wrap items-center justify-between gap-4 pb-5 border-b border-border/80">
        <div>
          <h1 className="text-xl font-semibold tracking-tight text-foreground flex items-center gap-2.5">
            <CheckSquare className="w-5 h-5 text-primary" />
            My Tasks
          </h1>
          <p className="text-xs text-muted-foreground mt-1">
            Personal engineering cockpit for {user?.username || 'you'}
          </p>
        </div>

        <div className="flex items-center gap-2">
          {isFetching && (
            <RefreshCw className="w-3.5 h-3.5 text-muted-foreground animate-spin mr-1" />
          )}
          <Button
            onClick={() => setCreateTaskModalOpen(true)}
            size="sm"
            className="text-xs h-8 font-medium"
          >
            <Plus className="w-3.5 h-3.5 mr-1" />
            New Issue (c)
          </Button>
        </div>
      </div>

      {/* Metric Cards Banner */}
      <div className="grid grid-cols-1 sm:grid-cols-3 gap-3 my-5">
        <div className="p-3.5 rounded-lg border border-border/80 bg-surface flex items-center justify-between">
          <div>
            <div className="text-[11px] font-medium text-muted-foreground">Active Assigned</div>
            <div className="text-xl font-semibold text-foreground mt-0.5 tabular-nums tracking-tight">
              {myTasks.filter((t) => t.status !== 'DONE').length}
            </div>
          </div>
          <Clock className="w-5 h-5 text-foreground/70" />
        </div>

        <div className="p-3.5 rounded-lg border border-border/80 bg-surface flex items-center justify-between">
          <div>
            <div className="text-[11px] font-medium text-muted-foreground">Urgent / High</div>
            <div className="text-xl font-semibold text-amber-500 mt-0.5 tabular-nums tracking-tight">
              {urgentCount}
            </div>
          </div>
          <AlertTriangle className="w-5 h-5 text-amber-500/70" />
        </div>

        <div className="p-3.5 rounded-lg border border-border/80 bg-surface flex items-center justify-between">
          <div>
            <div className="text-[11px] font-medium text-muted-foreground">Completed</div>
            <div className="text-xl font-semibold text-emerald-500 mt-0.5 tabular-nums tracking-tight">
              {completedCount}
            </div>
          </div>
          <CheckCircle2 className="w-5 h-5 text-emerald-500/70" />
        </div>
      </div>

      {/* Tabs */}
      <div className="flex items-center justify-between border-b border-border/60 pb-2 mb-4">
        <div className="flex items-center gap-2">
          <button
            onClick={() => setActiveTab('assigned')}
            className={`px-3 py-1.5 rounded-md text-xs font-medium transition-all ${
              activeTab === 'assigned'
                ? 'bg-surface-hover text-foreground shadow-xs'
                : 'text-muted-foreground hover:text-foreground'
            }`}
          >
            Assigned to me ({myTasks.length})
          </button>
          <button
            onClick={() => setActiveTab('urgent')}
            className={`px-3 py-1.5 rounded-md text-xs font-medium transition-all ${
              activeTab === 'urgent'
                ? 'bg-surface-hover text-foreground shadow-xs'
                : 'text-muted-foreground hover:text-foreground'
            }`}
          >
            Urgent Attention ({urgentCount})
          </button>
          <button
            onClick={() => setActiveTab('created')}
            className={`px-3 py-1.5 rounded-md text-xs font-medium transition-all ${
              activeTab === 'created'
                ? 'bg-surface-hover text-foreground shadow-xs'
                : 'text-muted-foreground hover:text-foreground'
            }`}
          >
            Reported by me
          </button>
        </div>
      </div>

      {/* Task List */}
      {isLoading ? (
        <div className="py-20 text-center text-xs text-muted-foreground">
          Loading personal tasks...
        </div>
      ) : filteredTasks.length === 0 ? (
        <div className="border border-dashed border-border rounded-xl p-12 text-center my-6 bg-surface/30">
          <CheckSquare className="w-10 h-10 text-muted-foreground/40 mx-auto mb-3" />
          <h3 className="text-sm font-semibold text-foreground">Zero pending tasks</h3>
          <p className="text-xs text-muted-foreground mt-1 mb-4">
            You're completely clear. Create a new task or take one from the project backlog.
          </p>
          <Button
            onClick={() => setCreateTaskModalOpen(true)}
            size="sm"
            className="text-xs h-8"
          >
            <Plus className="w-3.5 h-3.5 mr-1" />
            Create Issue (c)
          </Button>
        </div>
      ) : (
        <TaskDataTable tasks={filteredTasks} onTasksUpdated={() => refetch()} />
      )}
    </div>
  );
}

export default MyTasksPage;
