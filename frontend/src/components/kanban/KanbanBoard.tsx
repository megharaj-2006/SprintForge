import React, { useState } from 'react';
import type { Task, TaskStatus } from '../../types';
import { KanbanColumn } from './KanbanColumn';
import { taskApi } from '../../api/endpoints';

interface KanbanBoardProps {
  tasks: Task[];
  onTaskMoved?: () => void;
}

export function KanbanBoard({ tasks: initialTasks, onTaskMoved }: KanbanBoardProps) {
  const [tasks, setTasks] = useState<Task[]>(initialTasks);

  // Sync state with incoming props
  React.useEffect(() => {
    setTasks(initialTasks);
  }, [initialTasks]);

  const columns: { status: TaskStatus; title: string }[] = [
    { status: 'TODO', title: 'To Do' },
    { status: 'IN_PROGRESS', title: 'In Progress' },
    { status: 'IN_REVIEW', title: 'In Review' },
    { status: 'DONE', title: 'Done' },
  ];

  const handleDropTask = async (taskId: number, newStatus: TaskStatus) => {
    const originalTasks = [...tasks];
    const targetTask = tasks.find((t) => t.id === taskId);
    if (!targetTask || targetTask.status === newStatus) return;

    // 1. Instant Optimistic UI Update
    setTasks((prev) =>
      prev.map((t) => (t.id === taskId ? { ...t, status: newStatus } : t))
    );

    // 2. Send API mutation to backend
    try {
      await taskApi.changeStatus(taskId, newStatus);
      onTaskMoved?.();
    } catch {
      // 3. Rollback on failure
      setTasks(originalTasks);
    }
  };

  return (
    <div className="flex gap-4 overflow-x-auto pb-6 h-full items-start">
      {columns.map((col) => {
        const colTasks = tasks.filter((t) => t.status === col.status);
        return (
          <KanbanColumn
            key={col.status}
            status={col.status}
            title={col.title}
            tasks={colTasks}
            onDropTask={handleDropTask}
          />
        );
      })}
    </div>
  );
}
