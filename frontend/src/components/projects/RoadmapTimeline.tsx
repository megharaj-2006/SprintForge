import React from 'react';
import type { Task, Sprint } from '../../types';
import { StatusGlyph } from '../ui/Badge';
import { Calendar, Flag, CheckCircle } from 'lucide-react';

interface RoadmapTimelineProps {
  tasks: Task[];
  sprints?: Sprint[];
}

export function RoadmapTimeline({ tasks, sprints = [] }: RoadmapTimelineProps) {
  return (
    <div className="border border-border rounded-lg bg-surface p-5 space-y-6">
      <div className="flex items-center justify-between pb-3 border-b border-border">
        <div>
          <h3 className="text-sm font-semibold text-foreground">Project Roadmap & Milestone Timeline</h3>
          <p className="text-xs text-muted-foreground">Track sprint cadences and task progress over time.</p>
        </div>
      </div>

      {/* Sprints Cadence */}
      {sprints.length > 0 && (
        <div className="space-y-3">
          <h4 className="text-xs font-semibold uppercase tracking-wider text-muted-foreground">
            Sprint Timeline
          </h4>
          <div className="space-y-2">
            {sprints.map((sprint) => (
              <div key={sprint.id} className="p-3 rounded-lg border border-border bg-surface-hover/40 flex items-center justify-between text-xs">
                <div className="flex items-center gap-2.5">
                  <Flag className="w-4 h-4 text-foreground" />
                  <div>
                    <span className="font-semibold text-foreground">{sprint.name}</span>
                    {sprint.goal && <p className="text-[11px] text-muted-foreground">{sprint.goal}</p>}
                  </div>
                </div>
                <div className="flex items-center gap-3 tabular-nums text-[11px] text-muted-foreground">
                  <span>{sprint.startDate || 'Current'} → {sprint.endDate || 'Target'}</span>
                  <span className="px-2 py-0.5 rounded bg-muted/60 text-foreground uppercase font-medium text-[10px]">
                    {sprint.status}
                  </span>
                </div>
              </div>
            ))}
          </div>
        </div>
      )}

      {/* Tasks Milestones Bar */}
      <div className="space-y-3">
        <h4 className="text-xs font-semibold uppercase tracking-wider text-muted-foreground">
          Scheduled Milestones ({tasks.filter((t) => t.dueDate).length})
        </h4>

        <div className="space-y-2">
          {tasks.filter((t) => t.dueDate).map((task) => (
            <div
              key={task.id}
              className="p-2.5 rounded border border-border/80 hover:bg-surface-hover/80 flex items-center justify-between text-xs transition-colors"
            >
              <div className="flex items-center gap-2">
                <StatusGlyph status={task.status} />
                <span className="tabular-nums font-medium text-muted-foreground text-[11px]">{task.identifier}</span>
                <span className="font-medium text-foreground">{task.title}</span>
              </div>
              <div className="flex items-center gap-2 text-muted-foreground text-[11px]">
                <Calendar className="w-3.5 h-3.5" />
                <span>{task.dueDate}</span>
              </div>
            </div>
          ))}

          {tasks.filter((t) => t.dueDate).length === 0 && (
            <div className="py-8 text-center text-xs text-muted-foreground">
              No tasks with due dates scheduled yet. Set due dates on tasks to plot them on the roadmap.
            </div>
          )}
        </div>
      </div>
    </div>
  );
}
