import React, { useEffect, useState } from 'react';
import { Drawer } from '../ui/Drawer';
import { Button } from '../ui/Button';
import { StatusBadge, PriorityBadge } from '../ui/Badge';
import { useUIStore } from '../../store/useUIStore';
import {
  taskApi,
  subtaskApi,
  checklistApi,
  commentApi,
  attachmentApi,
  timeTrackingApi,
} from '../../api/endpoints';
import type {
  Task,
  TaskStatus,
  Subtask,
  ChecklistItem,
  TaskComment,
  TaskAttachment,
  TimeEntry,
} from '../../types';
import {
  Clock,
  Paperclip,
  MessageSquare,
  CheckCircle,
  Plus,
  Trash2,
  Calendar,
  User,
  History,
  Send,
  Sparkles,
} from 'lucide-react';

export function TaskDetailDrawer({ onTaskUpdated }: { onTaskUpdated?: () => void }) {
  const { activeTaskDrawerId, closeTaskDrawer } = useUIStore();
  const [task, setTask] = useState<Task | null>(null);
  const [isLoading, setIsLoading] = useState(false);
  const [activeTab, setActiveTab] = useState<'details' | 'checklist' | 'comments' | 'timelog' | 'attachments'>('details');

  // Related data
  const [subtasks, setSubtasks] = useState<Subtask[]>([]);
  const [newSubtaskTitle, setNewSubtaskTitle] = useState('');
  const [comments, setComments] = useState<TaskComment[]>([]);
  const [newCommentText, setNewCommentText] = useState('');
  const [attachments, setAttachments] = useState<TaskAttachment[]>([]);
  const [timeEntries, setTimeEntries] = useState<TimeEntry[]>([]);
  const [logMinutes, setLogMinutes] = useState(30);

  useEffect(() => {
    if (!activeTaskDrawerId) {
      setTask(null);
      return;
    }

    const loadTaskData = async () => {
      setIsLoading(true);
      try {
        const t = await taskApi.getTaskById(activeTaskDrawerId);
        setTask(t);

        // Fetch subtasks, comments, attachments, timelogs in parallel
        const [st, cm, at, te] = await Promise.allSettled([
          subtaskApi.getSubtasks(activeTaskDrawerId),
          commentApi.getComments(activeTaskDrawerId),
          attachmentApi.getAttachments(activeTaskDrawerId),
          timeTrackingApi.getTimeEntries(activeTaskDrawerId),
        ]);

        if (st.status === 'fulfilled') setSubtasks(st.value || []);
        if (cm.status === 'fulfilled') setComments(cm.value || []);
        if (at.status === 'fulfilled') setAttachments(at.value || []);
        if (te.status === 'fulfilled') setTimeEntries(te.value || []);
      } catch {
        // Handle error
      } finally {
        setIsLoading(false);
      }
    };

    loadTaskData();
  }, [activeTaskDrawerId]);

  if (!activeTaskDrawerId) return null;

  const handleStatusChange = async (newStatus: TaskStatus) => {
    if (!task) return;
    try {
      const updated = await taskApi.changeStatus(task.id, newStatus);
      setTask(updated);
      onTaskUpdated?.();
    } catch {
      // rollback or show toast
    }
  };

  const handleAddSubtask = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!newSubtaskTitle.trim() || !task) return;
    try {
      const st = await subtaskApi.createSubtask(task.id, newSubtaskTitle.trim());
      setSubtasks((prev) => [...prev, st]);
      setNewSubtaskTitle('');
    } catch {
      // Fallback local update
      setSubtasks((prev) => [
        ...prev,
        { id: Date.now(), taskId: task.id, title: newSubtaskTitle, completed: false, orderIndex: prev.length },
      ]);
      setNewSubtaskTitle('');
    }
  };

  const handleToggleSubtask = async (id: number, currentCompleted: boolean) => {
    try {
      await subtaskApi.toggleSubtask(id, !currentCompleted);
      setSubtasks((prev) =>
        prev.map((s) => (s.id === id ? { ...s, completed: !currentCompleted } : s))
      );
    } catch {
      setSubtasks((prev) =>
        prev.map((s) => (s.id === id ? { ...s, completed: !currentCompleted } : s))
      );
    }
  };

  const handleAddComment = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!newCommentText.trim() || !task) return;
    try {
      const cm = await commentApi.addComment(task.id, newCommentText.trim());
      setComments((prev) => [...prev, cm]);
      setNewCommentText('');
    } catch {
      // Fallback
      setComments((prev) => [
        ...prev,
        {
          id: Date.now(),
          taskId: task.id,
          userId: 1,
          user: { id: 1, username: 'CurrentUser', email: 'user@sprintforge.com', role: 'DEVELOPER' },
          content: newCommentText,
          createdAt: new Date().toISOString(),
        },
      ]);
      setNewCommentText('');
    }
  };

  const handleLogTime = async () => {
    if (!task || logMinutes <= 0) return;
    try {
      const entry = await timeTrackingApi.logTime(task.id, { durationMinutes: logMinutes });
      setTimeEntries((prev) => [entry, ...prev]);
    } catch {
      setTimeEntries((prev) => [
        {
          id: Date.now(),
          taskId: task.id,
          userId: 1,
          durationMinutes: logMinutes,
          loggedAt: new Date().toISOString(),
        },
        ...prev,
      ]);
    }
  };

  return (
    <Drawer
      open={Boolean(activeTaskDrawerId)}
      onClose={closeTaskDrawer}
      title={
        <div className="flex items-center gap-2">
          <span className="tabular-nums text-[11px] text-muted-foreground bg-surface-raised px-1.5 py-0.5 rounded border border-border font-medium">
            {task?.identifier || `TASK-${activeTaskDrawerId}`}
          </span>
          <span className="text-xs font-semibold text-foreground truncate">
            {task?.title || 'Loading task...'}
          </span>
        </div>
      }
      subtitle={task?.dueDate ? `Due ${task.dueDate}` : undefined}
      width="2xl"
    >
      {isLoading && !task ? (
        <div className="py-20 text-center text-xs text-muted-foreground">
          Loading task details...
        </div>
      ) : task ? (
        <div className="space-y-6">
          {/* Top Attributes Bar */}
          <div className="p-3 bg-surface-hover/50 rounded-lg border border-border/80 flex flex-wrap items-center justify-between gap-3 text-xs">
            <div className="flex items-center gap-3">
              <div>
                <label className="text-[10px] text-muted-foreground block mb-0.5">Status</label>
                <select
                  value={task.status}
                  onChange={(e) => handleStatusChange(e.target.value as TaskStatus)}
                  className="bg-surface border border-border rounded px-2 py-1 text-xs text-foreground focus:outline-none focus:ring-1 focus:ring-foreground font-medium"
                >
                  <option value="TODO">To Do</option>
                  <option value="IN_PROGRESS">In Progress</option>
                  <option value="IN_REVIEW">In Review</option>
                  <option value="DONE">Done</option>
                  <option value="CANCELLED">Cancelled</option>
                </select>
              </div>

              <div>
                <label className="text-[10px] text-muted-foreground block mb-0.5">Priority</label>
                <PriorityBadge priority={task.priority} />
              </div>

              <div>
                <label className="text-[10px] text-muted-foreground block mb-0.5">Estimate</label>
                <span className="text-xs tabular-nums font-medium px-2 py-0.5 rounded bg-surface-raised border border-border text-foreground">
                  {task.storyPoints ?? 0} pts
                </span>
              </div>
            </div>

            <div className="flex items-center gap-2">
              <span className="text-[10px] text-muted-foreground">
                Assignee: {task.assignee?.username || 'Unassigned'}
              </span>
            </div>
          </div>

          {/* Description */}
          <div>
            <h4 className="text-xs font-semibold text-foreground uppercase tracking-wider mb-1.5">
              Description
            </h4>
            <div className="text-xs text-foreground/90 whitespace-pre-wrap leading-relaxed p-3 rounded-md bg-surface border border-border/60">
              {task.description || 'No description provided for this task.'}
            </div>
          </div>

          {/* Tab Navigation */}
          <div className="border-b border-border flex items-center gap-4 text-xs">
            <button
              onClick={() => setActiveTab('details')}
              className={`pb-2 font-medium transition-colors border-b-2 -mb-px ${
                activeTab === 'details'
                  ? 'border-foreground text-foreground font-semibold'
                  : 'border-transparent text-muted-foreground hover:text-foreground'
              }`}
            >
              Subtasks ({subtasks.length})
            </button>
            <button
              onClick={() => setActiveTab('comments')}
              className={`pb-2 font-medium transition-colors border-b-2 -mb-px ${
                activeTab === 'comments'
                  ? 'border-foreground text-foreground font-semibold'
                  : 'border-transparent text-muted-foreground hover:text-foreground'
              }`}
            >
              Discussion ({comments.length})
            </button>
            <button
              onClick={() => setActiveTab('timelog')}
              className={`pb-2 font-medium transition-colors border-b-2 -mb-px ${
                activeTab === 'timelog'
                  ? 'border-foreground text-foreground font-semibold'
                  : 'border-transparent text-muted-foreground hover:text-foreground'
              }`}
            >
              Time Tracking ({timeEntries.length})
            </button>
            <button
              onClick={() => setActiveTab('attachments')}
              className={`pb-2 font-medium transition-colors border-b-2 -mb-px ${
                activeTab === 'attachments'
                  ? 'border-foreground text-foreground font-semibold'
                  : 'border-transparent text-muted-foreground hover:text-foreground'
              }`}
            >
              Files ({attachments.length})
            </button>
          </div>

          {/* Tab Content: Subtasks */}
          {activeTab === 'details' && (
            <div className="space-y-3">
              <div className="space-y-1.5">
                {subtasks.map((st) => (
                  <div
                    key={st.id}
                    className="flex items-center justify-between p-2 rounded border border-border hover:bg-surface-hover text-xs transition-colors"
                  >
                    <label className="flex items-center gap-2.5 cursor-pointer flex-1 min-w-0">
                      <input
                        type="checkbox"
                        checked={st.completed}
                        onChange={() => handleToggleSubtask(st.id, st.completed)}
                        className="rounded border-border text-primary focus:ring-0"
                      />
                      <span className={st.completed ? 'line-through text-muted-foreground' : 'text-foreground'}>
                        {st.title}
                      </span>
                    </label>
                  </div>
                ))}
              </div>

              {/* Add subtask */}
              <form onSubmit={handleAddSubtask} className="flex gap-2 pt-1">
                <input
                  type="text"
                  value={newSubtaskTitle}
                  onChange={(e) => setNewSubtaskTitle(e.target.value)}
                  placeholder="Add a new subtask or acceptance criterion..."
                  className="flex-1 bg-surface border border-border rounded px-2.5 py-1.5 text-xs text-foreground placeholder:text-muted-foreground focus:outline-none focus:ring-1 focus:ring-primary"
                />
                <Button type="submit" variant="secondary" size="sm" icon={<Plus className="w-3.5 h-3.5" />}>
                  Add
                </Button>
              </form>
            </div>
          )}

          {/* Tab Content: Comments */}
          {activeTab === 'comments' && (
            <div className="space-y-4">
              <div className="space-y-3">
                {comments.length === 0 ? (
                  <div className="py-6 text-center text-xs text-muted-foreground">
                    No comments yet. Start the technical discussion below.
                  </div>
                ) : (
                  comments.map((cm) => (
                    <div key={cm.id} className="p-3 rounded-lg border border-border bg-surface text-xs space-y-1">
                      <div className="flex items-center justify-between text-muted-foreground">
                        <span className="font-medium text-foreground">{cm.user?.username || 'Member'}</span>
                        <span className="text-[10px]">{new Date(cm.createdAt).toLocaleTimeString()}</span>
                      </div>
                      <p className="text-foreground/90 leading-relaxed whitespace-pre-wrap">{cm.content}</p>
                    </div>
                  ))
                )}
              </div>

              {/* Add comment */}
              <form onSubmit={handleAddComment} className="relative">
                <textarea
                  value={newCommentText}
                  onChange={(e) => setNewCommentText(e.target.value)}
                  placeholder="Write a comment with @mentions or markdown..."
                  rows={2}
                  className="w-full bg-surface border border-border rounded px-3 py-2 text-xs text-foreground placeholder:text-muted-foreground focus:outline-none focus:ring-1 focus:ring-primary resize-none pr-10"
                />
                <button
                  type="submit"
                  disabled={!newCommentText.trim()}
                  className="absolute right-2.5 bottom-3 text-muted-foreground hover:text-primary disabled:opacity-40"
                >
                  <Send className="w-4 h-4" />
                </button>
              </form>
            </div>
          )}

          {/* Tab Content: Time Tracking */}
          {activeTab === 'timelog' && (
            <div className="space-y-4">
              <div className="p-3 bg-surface rounded border border-border flex items-center justify-between">
                <div className="text-xs">
                  <span className="text-muted-foreground">Logged: </span>
                  <span className="font-semibold text-foreground">
                    {timeEntries.reduce((acc, t) => acc + t.durationMinutes, 0) / 60} hrs
                  </span>
                </div>
                <div className="flex items-center gap-2">
                  <input
                    type="number"
                    min={5}
                    step={15}
                    value={logMinutes}
                    onChange={(e) => setLogMinutes(Number(e.target.value))}
                    className="w-20 bg-background border border-border rounded px-2 py-1 text-xs text-foreground tabular-nums"
                  />
                  <span className="text-xs text-muted-foreground">min</span>
                  <Button variant="secondary" size="xs" onClick={handleLogTime}>
                    Log Work
                  </Button>
                </div>
              </div>

              <div className="space-y-1.5 text-xs">
                {timeEntries.map((te) => (
                  <div key={te.id} className="p-2 border border-border rounded flex items-center justify-between text-muted-foreground">
                    <span className="tabular-nums">{te.durationMinutes} minutes</span>
                    <span className="text-[10px] tabular-nums">{new Date(te.loggedAt).toLocaleDateString()}</span>
                  </div>
                ))}
              </div>
            </div>
          )}

          {/* Tab Content: Attachments */}
          {activeTab === 'attachments' && (
            <div className="space-y-3">
              <div className="border-2 border-dashed border-border rounded-lg p-6 text-center hover:border-foreground/40 transition-colors cursor-pointer text-xs text-muted-foreground">
                <Paperclip className="w-5 h-5 mx-auto mb-1 text-muted-foreground" />
                <span>Drag and drop project assets, screenshots, or logs</span>
              </div>
              <div className="space-y-1.5">
                {attachments.map((at) => (
                  <div key={at.id} className="p-2 rounded border border-border flex items-center justify-between text-xs">
                    <span className="truncate">{at.fileName}</span>
                    <span className="text-[10px] text-muted-foreground tabular-nums">
                      {(at.fileSize / 1024).toFixed(1)} KB
                    </span>
                  </div>
                ))}
              </div>
            </div>
          )}
        </div>
      ) : null}
    </Drawer>
  );
}
