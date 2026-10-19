import React from 'react';
import type { Task } from '../../types';
import { PriorityBadge } from '../ui/Badge';
import { useUIStore } from '../../store/useUIStore';
import { CheckSquare, MessageSquare, Paperclip, Clock } from 'lucide-react';

interface TaskCardProps {
  task: Task;
  onDragStart?: (e: React.DragEvent, task: Task) => void;
}

export function TaskCard({ task, onDragStart }: TaskCardProps) {
  const { openTaskDrawer } = useUIStore();

  const handleDragStart = (e: React.DragEvent) => {
    e.dataTransfer.setData('text/plain', String(task.id));
    e.dataTransfer.effectAllowed = 'move';
    onDragStart?.(e, task);
  };

  return (
    <div
      draggable
      onDragStart={handleDragStart}
      onClick={() => openTaskDrawer(task.id)}
      className="group relative bg-surface border border-border/80 hover:border-border hover:shadow-md rounded-lg p-3 cursor-grab active:cursor-grabbing transition-all duration-150 space-y-2 select-none"
    >
      {/* Header: ID & Priority */}
      <div className="flex items-center justify-between text-[11px]">
        <span className="tabular-nums text-muted-foreground group-hover:text-foreground font-medium transition-colors">
          {task.identifier || `TASK-${task.id}`}
        </span>
        <PriorityBadge priority={task.priority} />
      </div>

      {/* Title */}
      <h3 className="text-xs font-medium text-foreground line-clamp-2 leading-snug">
        {task.title}
      </h3>

      {/* Labels */}
      {task.labels && task.labels.length > 0 && (
        <div className="flex flex-wrap gap-1">
          {task.labels.map((lbl) => (
            <span
              key={lbl}
              className="text-[10px] bg-muted/40 text-muted-foreground px-1.5 py-0.5 rounded border border-border/50"
            >
              {lbl}
            </span>
          ))}
        </div>
      )}

      {/* Footer: Metadata & Assignee */}
      <div className="pt-1.5 border-t border-border/40 flex items-center justify-between text-[10px] text-muted-foreground">
        <div className="flex items-center gap-2">
          {task.storyPoints !== undefined && (
            <span className="tabular-nums bg-surface-raised border border-border/70 px-1 rounded text-foreground font-medium">
              {task.storyPoints}pt
            </span>
          )}
          {task.dueDate && (
            <span className="flex items-center gap-1">
              <Clock className="w-2.5 h-2.5" />
              <span>{task.dueDate}</span>
            </span>
          )}
        </div>

        {task.assignee ? (
          <div
            className="w-4 h-4 rounded-full bg-surface-raised border border-border text-foreground text-[9px] font-bold flex items-center justify-center uppercase shrink-0 shadow-2xs"
            title={`Assigned to ${task.assignee.username}`}
          >
            {task.assignee.username.slice(0, 2)}
          </div>
        ) : (
          <div className="w-4 h-4 rounded-full border border-dashed border-border" />
        )}
      </div>
    </div>
  );
}
