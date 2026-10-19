import React, { useState } from 'react';
import type { Task, TaskStatus } from '../../types';
import { StatusGlyph, PriorityBadge } from '../ui/Badge';
import { useUIStore } from '../../store/useUIStore';
import { taskApi } from '../../api/endpoints';
import { Trash2, UserCheck, CheckCircle2, ArrowRight } from 'lucide-react';

interface TaskDataTableProps {
  tasks: Task[];
  onTasksUpdated?: () => void;
}

export function TaskDataTable({ tasks, onTasksUpdated }: TaskDataTableProps) {
  const { openTaskDrawer } = useUIStore();
  const [selectedIds, setSelectedIds] = useState<number[]>([]);
  const [focusedIndex, setFocusedIndex] = useState<number>(0);

  const toggleSelect = (id: number) => {
    setSelectedIds((prev) =>
      prev.includes(id) ? prev.filter((i) => i !== id) : [...prev, id]
    );
  };

  const selectAll = () => {
    if (selectedIds.length === tasks.length) {
      setSelectedIds([]);
    } else {
      setSelectedIds(tasks.map((t) => t.id));
    }
  };

  const handleBulkStatus = async (status: TaskStatus) => {
    try {
      await taskApi.bulkUpdate({ taskIds: selectedIds, status });
      setSelectedIds([]);
      onTasksUpdated?.();
    } catch {
      // Handle error
    }
  };

  const handleBulkDelete = async () => {
    if (!window.confirm(`Delete ${selectedIds.length} tasks?`)) return;
    try {
      await Promise.all(selectedIds.map((id) => taskApi.deleteTask(id)));
      setSelectedIds([]);
      onTasksUpdated?.();
    } catch {
      // Handle error
    }
  };

  return (
    <div className="relative border border-border rounded-lg overflow-hidden bg-surface">
      {/* Table */}
      <table className="w-full text-left text-xs border-collapse">
        <thead>
          <tr className="border-b border-border bg-surface-hover/60 text-muted-foreground font-medium select-none">
            <th className="w-8 px-3 py-2 text-center">
              <input
                type="checkbox"
                checked={selectedIds.length === tasks.length && tasks.length > 0}
                onChange={selectAll}
                className="rounded border-border text-primary focus:ring-0"
              />
            </th>
            <th className="w-20 px-2 py-2">ID</th>
            <th className="px-3 py-2">Title</th>
            <th className="w-28 px-3 py-2">Status</th>
            <th className="w-24 px-3 py-2">Priority</th>
            <th className="w-28 px-3 py-2">Assignee</th>
            <th className="w-20 px-3 py-2 text-right">Points</th>
          </tr>
        </thead>
        <tbody className="divide-y divide-border/60">
          {tasks.map((task, idx) => {
            const isSelected = selectedIds.includes(task.id);
            const isFocused = idx === focusedIndex;

            return (
              <tr
                key={task.id}
                onClick={() => openTaskDrawer(task.id)}
                className={`group cursor-pointer hover:bg-surface-hover/80 transition-colors select-none ${
                  isSelected ? 'bg-surface-active' : ''
                } ${isFocused ? 'ring-1 ring-inset ring-foreground/40' : ''}`}
              >
                <td
                  className="w-8 px-3 py-2 text-center"
                  onClick={(e) => {
                    e.stopPropagation();
                    toggleSelect(task.id);
                  }}
                >
                  <input
                    type="checkbox"
                    checked={isSelected}
                    onChange={() => {}}
                    className="rounded border-border text-foreground focus:ring-0"
                  />
                </td>
                <td className="w-20 px-2 py-2 text-[11px] tabular-nums font-medium text-muted-foreground group-hover:text-foreground transition-colors">
                  {task.identifier || `TASK-${task.id}`}
                </td>
                <td className="px-3 py-2 font-medium text-foreground">
                  <div className="flex items-center gap-2">
                    <span className="truncate">{task.title}</span>
                    {task.labels && task.labels.length > 0 && (
                      <span className="text-[10px] text-muted-foreground bg-muted/40 px-1 py-0.2 rounded border border-border">
                        {task.labels[0]}
                      </span>
                    )}
                  </div>
                </td>
                <td className="w-28 px-3 py-2">
                  <div className="flex items-center gap-1.5 text-muted-foreground">
                    <StatusGlyph status={task.status} />
                    <span className="capitalize text-[11px]">{task.status.toLowerCase().replace('_', ' ')}</span>
                  </div>
                </td>
                <td className="w-24 px-3 py-2">
                  <PriorityBadge priority={task.priority} />
                </td>
                <td className="w-28 px-3 py-2 text-muted-foreground text-[11px]">
                  {task.assignee?.username || '—'}
                </td>
                <td className="w-20 px-3 py-2 text-right tabular-nums text-muted-foreground">
                  {task.storyPoints ?? '—'}
                </td>
              </tr>
            );
          })}
        </tbody>
      </table>

      {tasks.length === 0 && (
        <div className="py-16 text-center text-xs text-muted-foreground">
          No tasks found in this view. Press <kbd className="bg-muted/60 px-1 rounded border border-border text-[11px]">c</kbd> to create a task.
        </div>
      )}

      {/* Floating Bulk Action Bar */}
      {selectedIds.length > 0 && (
        <div className="fixed bottom-6 left-1/2 -translate-x-1/2 z-40 bg-surface border border-border shadow-2xl rounded-full px-4 py-2 flex items-center gap-3 text-xs animate-slide-up">
          <span className="font-medium text-foreground">
            {selectedIds.length} selected
          </span>
          <div className="h-4 w-px bg-border" />
          <button
            onClick={() => handleBulkStatus('DONE')}
            className="flex items-center gap-1.5 text-emerald-500 hover:text-emerald-400 font-medium px-2 py-1 rounded hover:bg-surface-hover transition-colors"
          >
            <CheckCircle2 className="w-3.5 h-3.5" />
            <span>Mark Done</span>
          </button>
          <button
            onClick={() => handleBulkStatus('IN_PROGRESS')}
            className="flex items-center gap-1.5 text-amber-500 hover:text-amber-400 font-medium px-2 py-1 rounded hover:bg-surface-hover transition-colors"
          >
            <ArrowRight className="w-3.5 h-3.5" />
            <span>In Progress</span>
          </button>
          <button
            onClick={handleBulkDelete}
            className="flex items-center gap-1.5 text-red-500 hover:text-red-400 font-medium px-2 py-1 rounded hover:bg-surface-hover transition-colors"
          >
            <Trash2 className="w-3.5 h-3.5" />
            <span>Delete</span>
          </button>
        </div>
      )}
    </div>
  );
}
