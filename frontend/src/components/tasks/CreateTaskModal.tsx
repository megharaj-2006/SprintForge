import React, { useState } from 'react';
import { Modal } from '../ui/Modal';
import { Button } from '../ui/Button';
import { useUIStore } from '../../store/useUIStore';
import { useWorkspaceStore } from '../../store/useWorkspaceStore';
import { taskApi } from '../../api/endpoints';
import type { TaskStatus, TaskPriority, TaskType } from '../../types';
import { StatusBadge, PriorityBadge } from '../ui/Badge';
import { Calendar, User, Hash, AlertCircle } from 'lucide-react';

export function CreateTaskModal({ onTaskCreated }: { onTaskCreated?: () => void }) {
  const { createTaskModalOpen, setCreateTaskModalOpen } = useUIStore();
  const { currentProject, projects } = useWorkspaceStore();

  const [projectId, setProjectId] = useState<number>(currentProject?.id || 1);
  const [title, setTitle] = useState('');
  const [description, setDescription] = useState('');
  const [status, setStatus] = useState<TaskStatus>('TODO');
  const [priority, setPriority] = useState<TaskPriority>('MEDIUM');
  const [type, setType] = useState<TaskType>('TASK');
  const [storyPoints, setStoryPoints] = useState<number>(2);
  const [dueDate, setDueDate] = useState<string>('');
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const handleSubmit = async (e?: React.FormEvent) => {
    if (e) e.preventDefault();
    if (!title.trim() || isSubmitting) return;

    const targetProjId = currentProject?.id || projectId;
    setIsSubmitting(true);
    setError(null);

    try {
      await taskApi.createTask(targetProjId, {
        title: title.trim(),
        description: description.trim() || undefined,
        status,
        priority,
        type,
        storyPoints,
        dueDate: dueDate || undefined,
      });

      setTitle('');
      setDescription('');
      setStatus('TODO');
      setPriority('MEDIUM');
      setCreateTaskModalOpen(false);
      onTaskCreated?.();
    } catch (err: unknown) {
      const errObj = err as { response?: { data?: { message?: string } } };
      setError(errObj.response?.data?.message || 'Failed to create task. Please try again.');
    } finally {
      setIsSubmitting(false);
    }
  };

  return (
    <Modal
      open={createTaskModalOpen}
      onOpenChange={setCreateTaskModalOpen}
      title="Create Task"
      description="Quickly capture tasks and issues without leaving the current view."
      maxWidth="lg"
    >
      <form onSubmit={handleSubmit} className="space-y-4">
        {error && (
          <div className="p-2.5 rounded-md bg-red-500/10 border border-red-500/20 text-red-500 text-xs flex items-center gap-2">
            <AlertCircle className="w-4 h-4 shrink-0" />
            <span>{error}</span>
          </div>
        )}

        {/* Project Selector if more than 1 project exists */}
        {projects.length > 1 && (
          <div>
            <label className="text-[11px] font-medium text-muted-foreground block mb-1">
              Project
            </label>
            <select
              value={currentProject?.id || projectId}
              onChange={(e) => setProjectId(Number(e.target.value))}
              className="w-full bg-surface border border-border rounded-md px-2.5 py-1.5 text-xs text-foreground focus:outline-none focus:ring-1 focus:ring-primary"
            >
              {projects.map((p) => (
                <option key={p.id} value={p.id}>
                  {p.identifier} — {p.name}
                </option>
              ))}
            </select>
          </div>
        )}

        {/* Title */}
        <div>
          <input
            autoFocus
            type="text"
            required
            value={title}
            onChange={(e) => setTitle(e.target.value)}
            placeholder="Issue or task title..."
            className="w-full bg-surface border border-border rounded-md px-3 py-2 text-sm text-foreground placeholder:text-muted-foreground focus:outline-none focus:ring-1 focus:ring-primary font-medium"
          />
        </div>

        {/* Description */}
        <div>
          <textarea
            value={description}
            onChange={(e) => setDescription(e.target.value)}
            placeholder="Add description, acceptance criteria, or context (markdown supported)..."
            rows={3}
            className="w-full bg-surface border border-border rounded-md px-3 py-2 text-xs text-foreground placeholder:text-muted-foreground focus:outline-none focus:ring-1 focus:ring-primary resize-none"
          />
        </div>

        {/* Attribute Pills Grid */}
        <div className="grid grid-cols-2 sm:grid-cols-4 gap-2 pt-1 border-t border-border">
          {/* Status */}
          <div>
            <label className="text-[10px] text-muted-foreground font-medium block mb-1">
              Status
            </label>
            <select
              value={status}
              onChange={(e) => setStatus(e.target.value as TaskStatus)}
              className="w-full bg-surface border border-border rounded px-2 py-1 text-xs text-foreground focus:outline-none focus:ring-1 focus:ring-primary"
            >
              <option value="BACKLOG">Backlog</option>
              <option value="TODO">Todo</option>
              <option value="IN_PROGRESS">In Progress</option>
              <option value="IN_REVIEW">In Review</option>
              <option value="DONE">Done</option>
            </select>
          </div>

          {/* Priority */}
          <div>
            <label className="text-[10px] text-muted-foreground font-medium block mb-1">
              Priority
            </label>
            <select
              value={priority}
              onChange={(e) => setPriority(e.target.value as TaskPriority)}
              className="w-full bg-surface border border-border rounded px-2 py-1 text-xs text-foreground focus:outline-none focus:ring-1 focus:ring-primary"
            >
              <option value="LOW">Low</option>
              <option value="MEDIUM">Medium</option>
              <option value="HIGH">High</option>
              <option value="CRITICAL">Urgent</option>
            </select>
          </div>

          {/* Type */}
          <div>
            <label className="text-[10px] text-muted-foreground font-medium block mb-1">
              Type
            </label>
            <select
              value={type}
              onChange={(e) => setType(e.target.value as TaskType)}
              className="w-full bg-surface border border-border rounded px-2 py-1 text-xs text-foreground focus:outline-none focus:ring-1 focus:ring-primary"
            >
              <option value="TASK">Task</option>
              <option value="BUG">Bug</option>
              <option value="FEATURE">Feature</option>
              <option value="SPIKE">Spike</option>
              <option value="IMPROVEMENT">Improvement</option>
            </select>
          </div>

          {/* Story Points */}
          <div>
            <label className="text-[10px] text-muted-foreground font-medium block mb-1">
              Estimate (Points)
            </label>
            <input
              type="number"
              min={0}
              max={100}
              value={storyPoints}
              onChange={(e) => setStoryPoints(Number(e.target.value))}
              className="w-full bg-surface border border-border rounded px-2 py-1 text-xs text-foreground focus:outline-none focus:ring-1 focus:ring-primary tnum"
            />
          </div>
        </div>

        {/* Due Date */}
        <div>
          <label className="text-[10px] text-muted-foreground font-medium block mb-1">
            Due Date (Optional)
          </label>
          <input
            type="date"
            value={dueDate}
            onChange={(e) => setDueDate(e.target.value)}
            className="w-full sm:w-1/2 bg-surface border border-border rounded px-2.5 py-1 text-xs text-foreground focus:outline-none focus:ring-1 focus:ring-primary"
          />
        </div>

        {/* Footer actions */}
        <div className="pt-3 border-t border-border flex items-center justify-between">
          <span className="text-[10px] text-muted-foreground font-medium">
            Press <kbd className="bg-muted/40 px-1 rounded border border-border">ESC</kbd> to cancel
          </span>
          <div className="flex items-center gap-2">
            <Button
              type="button"
              variant="ghost"
              size="sm"
              onClick={() => setCreateTaskModalOpen(false)}
            >
              Cancel
            </Button>
            <Button
              type="submit"
              variant="primary"
              size="sm"
              isLoading={isSubmitting}
              shortcut="↵"
            >
              Create Task
            </Button>
          </div>
        </div>
      </form>
    </Modal>
  );
}
