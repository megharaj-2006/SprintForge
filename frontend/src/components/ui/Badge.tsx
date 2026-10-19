import React from 'react';
import { cn } from '../../lib/utils';
import type { TaskStatus, TaskPriority } from '../../types';

export function StatusGlyph({ status }: { status: TaskStatus }) {
  switch (status) {
    case 'BACKLOG':
      return (
        <svg className="w-3.5 h-3.5 text-status-backlog shrink-0" viewBox="0 0 16 16" fill="none">
          <circle cx="8" cy="8" r="6" stroke="currentColor" strokeWidth="1.5" strokeDasharray="2 2" />
        </svg>
      );
    case 'TODO':
      return (
        <svg className="w-3.5 h-3.5 text-muted-foreground shrink-0" viewBox="0 0 16 16" fill="none">
          <circle cx="8" cy="8" r="6" stroke="currentColor" strokeWidth="1.5" />
        </svg>
      );
    case 'IN_PROGRESS':
      return (
        <svg className="w-3.5 h-3.5 text-amber-500 shrink-0" viewBox="0 0 16 16" fill="none">
          <circle cx="8" cy="8" r="6" stroke="currentColor" strokeWidth="1.5" />
          <path d="M8 2 A6 6 0 0 1 14 8 L8 8 Z" fill="currentColor" />
        </svg>
      );
    case 'IN_REVIEW':
      return (
        <svg className="w-3.5 h-3.5 text-sky-400 shrink-0" viewBox="0 0 16 16" fill="none">
          <circle cx="8" cy="8" r="6" stroke="currentColor" strokeWidth="1.5" />
          <path d="M8 2 A6 6 0 1 1 2 8 L8 8 Z" fill="currentColor" />
        </svg>
      );
    case 'DONE':
      return (
        <svg className="w-3.5 h-3.5 text-emerald-500 shrink-0" viewBox="0 0 16 16" fill="none">
          <circle cx="8" cy="8" r="6" fill="currentColor" />
          <path d="M5 8 L7 10 L11 6" stroke="black" strokeWidth="1.5" strokeLinecap="round" strokeLinejoin="round" />
        </svg>
      );
    case 'ARCHIVED':
    case 'CANCELLED':
      return (
        <svg className="w-3.5 h-3.5 text-muted-foreground shrink-0" viewBox="0 0 16 16" fill="none">
          <circle cx="8" cy="8" r="6" stroke="currentColor" strokeWidth="1.5" />
          <line x1="4" y1="4" x2="12" y2="12" stroke="currentColor" strokeWidth="1.5" />
        </svg>
      );
    default:
      return null;
  }
}

export function StatusBadge({ status, className }: { status: TaskStatus; className?: string }) {
  const labels: Record<TaskStatus, string> = {
    BACKLOG: 'Backlog',
    TODO: 'Todo',
    IN_PROGRESS: 'In Progress',
    IN_REVIEW: 'In Review',
    DONE: 'Done',
    ARCHIVED: 'Archived',
    CANCELLED: 'Canceled',
  };

  return (
    <span
      className={cn(
        'inline-flex items-center gap-1.5 text-[11px] font-medium text-foreground/80 px-2 py-0.5 rounded border border-border/60 bg-surface/60 shrink-0',
        className
      )}
    >
      <StatusGlyph status={status} />
      <span>{labels[status] || status}</span>
    </span>
  );
}

export function PriorityBadge({ priority, className }: { priority: TaskPriority; className?: string }) {
  const configs: Record<TaskPriority, { label: string; color: string; icon: React.ReactNode }> = {
    URGENT: {
      label: 'Urgent',
      color: 'text-red-500',
      icon: (
        <svg className="w-3 h-3 text-red-500" viewBox="0 0 16 16" fill="currentColor">
          <path d="M8 1 L15 14 L1 14 Z M8 5 v4 M8 11 v1" stroke="currentColor" strokeWidth="1.5" strokeLinecap="round" />
        </svg>
      ),
    },
    CRITICAL: {
      label: 'Critical',
      color: 'text-red-500',
      icon: (
        <svg className="w-3 h-3 text-red-500" viewBox="0 0 16 16" fill="currentColor">
          <path d="M8 1 L15 14 L1 14 Z M8 5 v4 M8 11 v1" stroke="currentColor" strokeWidth="1.5" strokeLinecap="round" />
        </svg>
      ),
    },
    HIGH: {
      label: 'High',
      color: 'text-orange-500',
      icon: (
        <div className="flex gap-0.5 items-end h-2.5">
          <span className="w-0.5 h-1 bg-current" />
          <span className="w-0.5 h-1.5 bg-current" />
          <span className="w-0.5 h-2.5 bg-current" />
        </div>
      ),
    },
    MEDIUM: {
      label: 'Medium',
      color: 'text-amber-500',
      icon: (
        <div className="flex gap-0.5 items-end h-2.5">
          <span className="w-0.5 h-1 bg-current" />
          <span className="w-0.5 h-1.5 bg-current" />
          <span className="w-0.5 h-2.5 bg-muted/40" />
        </div>
      ),
    },
    LOW: {
      label: 'Low',
      color: 'text-slate-400',
      icon: (
        <div className="flex gap-0.5 items-end h-2.5">
          <span className="w-0.5 h-1 bg-current" />
          <span className="w-0.5 h-1.5 bg-muted/40" />
          <span className="w-0.5 h-2.5 bg-muted/40" />
        </div>
      ),
    },
  };

  const cfg = configs[priority] || configs.LOW;

  return (
    <span
      className={cn(
        'inline-flex items-center gap-1.5 text-[11px] font-medium text-muted-foreground px-1.5 py-0.5 rounded hover:bg-surface-hover shrink-0',
        className
      )}
      title={`Priority: ${cfg.label}`}
    >
      <span className={cfg.color}>{cfg.icon}</span>
      <span className="text-[11px]">{cfg.label}</span>
    </span>
  );
}
