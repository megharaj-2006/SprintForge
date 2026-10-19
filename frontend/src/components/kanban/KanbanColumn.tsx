import React, { useState } from 'react';
import type { Task, TaskStatus } from '../../types';
import { TaskCard } from './TaskCard';
import { StatusGlyph } from '../ui/Badge';
import { Plus } from 'lucide-react';
import { useUIStore } from '../../store/useUIStore';

interface KanbanColumnProps {
  status: TaskStatus;
  title: string;
  tasks: Task[];
  onDropTask: (taskId: number, newStatus: TaskStatus) => void;
}

export function KanbanColumn({ status, title, tasks, onDropTask }: KanbanColumnProps) {
  const [isOver, setIsOver] = useState(false);
  const { setCreateTaskModalOpen } = useUIStore();

  const handleDragOver = (e: React.DragEvent) => {
    e.preventDefault();
    e.dataTransfer.dropEffect = 'move';
    if (!isOver) setIsOver(true);
  };

  const handleDragLeave = () => {
    setIsOver(false);
  };

  const handleDrop = (e: React.DragEvent) => {
    e.preventDefault();
    setIsOver(false);
    const taskIdStr = e.dataTransfer.getData('text/plain');
    if (taskIdStr) {
      onDropTask(Number(taskIdStr), status);
    }
  };

  return (
    <div
      onDragOver={handleDragOver}
      onDragLeave={handleDragLeave}
      onDrop={handleDrop}
      className={`flex-1 min-w-[280px] max-w-[340px] flex flex-col bg-surface/30 rounded-xl border transition-all duration-150 p-2.5 ${
        isOver ? 'border-foreground/50 bg-surface-hover/80 ring-1 ring-foreground/20' : 'border-border/70'
      }`}
    >
      {/* Column Header */}
      <div className="flex items-center justify-between px-1.5 py-1 mb-2">
        <div className="flex items-center gap-2">
          <StatusGlyph status={status} />
          <h3 className="text-xs font-semibold text-foreground tracking-tight">{title}</h3>
          <span className="tabular-nums text-[10px] font-medium text-muted-foreground bg-surface-raised border border-border px-1.5 py-0.5 rounded-full shadow-2xs">
            {tasks.length}
          </span>
        </div>

        <button
          onClick={() => setCreateTaskModalOpen(true)}
          className="text-muted-foreground hover:text-foreground p-1 rounded hover:bg-surface-hover transition-colors"
          title="Add task to this status (C)"
        >
          <Plus className="w-3.5 h-3.5" />
        </button>
      </div>

      {/* Cards List */}
      <div className="flex-1 overflow-y-auto space-y-2 pr-0.5 min-h-[300px]">
        {tasks.map((task) => (
          <TaskCard key={task.id} task={task} />
        ))}

        {tasks.length === 0 && (
          <div className="h-28 border border-dashed border-border/60 rounded-lg flex items-center justify-center text-[11px] text-muted-foreground/60 select-none">
            Drop cards here
          </div>
        )}
      </div>
    </div>
  );
}
