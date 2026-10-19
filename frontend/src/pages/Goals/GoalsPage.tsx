import React, { useState } from 'react';
import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import { useWorkspaceStore } from '../../store/useWorkspaceStore';
import { goalApi } from '../../api/endpoints';
import { Button } from '../../components/ui/Button';
import { Modal } from '../../components/ui/Modal';
import type { Objective, KeyResult } from '../../types';
import {
  Target,
  Plus,
  TrendingUp,
  CheckCircle2,
  Calendar,
  AlertCircle,
  ChevronDown,
  ChevronRight,
  Sparkles,
} from 'lucide-react';

export function GoalsPage() {
  const queryClient = useQueryClient();
  const { currentWorkspace } = useWorkspaceStore();
  const workspaceId = currentWorkspace?.id;

  const [expandedIds, setExpandedIds] = useState<number[]>([]);
  const [createModalOpen, setCreateModalOpen] = useState(false);
  const [title, setTitle] = useState('');
  const [description, setDescription] = useState('');
  const [targetDate, setTargetDate] = useState('');

  const { data: objectives = [], isLoading } = useQuery<Objective[]>({
    queryKey: ['objectives', workspaceId],
    queryFn: async () => {
      if (!workspaceId) return [];
      const res = await goalApi.getObjectives(workspaceId);
      return Array.isArray(res) ? res : [];
    },
    enabled: Boolean(workspaceId),
  });

  const toggleExpand = (id: number) => {
    setExpandedIds((prev) =>
      prev.includes(id) ? prev.filter((i) => i !== id) : [...prev, id]
    );
  };

  const createMutation = useMutation({
    mutationFn: async () => {
      if (!workspaceId) throw new Error('No active workspace');
      return goalApi.createObjective({
        workspaceId,
        title,
        description,
        targetDate,
        status: 'IN_PROGRESS',
      });
    },
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['objectives', workspaceId] });
      setCreateModalOpen(false);
      setTitle('');
      setDescription('');
      setTargetDate('');
    },
  });

  const updateKrMutation = useMutation({
    mutationFn: async ({ id, value }: { id: number; value: number }) => {
      return goalApi.updateKeyResult(id, value);
    },
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['objectives', workspaceId] });
    },
  });

  return (
    <div className="flex flex-col h-full overflow-y-auto select-none p-6 max-w-7xl mx-auto">
      {/* Top Header */}
      <div className="flex flex-wrap items-center justify-between gap-4 pb-5 border-b border-border/80">
        <div>
          <h1 className="text-xl font-semibold tracking-tight text-foreground flex items-center gap-2.5">
            <Target className="w-5 h-5 text-foreground" />
            Strategic Goals & OKRs
          </h1>
          <p className="text-xs text-muted-foreground mt-1">
            Company objectives, quarterly key results, and outcome measurement for {currentWorkspace?.name || 'SprintForge'}
          </p>
        </div>

        <Button
          onClick={() => setCreateModalOpen(true)}
          variant="primary"
          className="text-xs h-8 px-3 font-medium"
        >
          <Plus className="w-3.5 h-3.5 mr-1" />
          New Objective
        </Button>
      </div>

      {/* Objectives Tree */}
      <div className="mt-6 space-y-4">
        {isLoading ? (
          <div className="py-20 text-center text-xs text-muted-foreground">
            Loading objectives...
          </div>
        ) : objectives.length === 0 ? (
          <div className="border border-dashed border-border rounded-xl p-12 text-center my-6 bg-surface/30">
            <Target className="w-12 h-12 text-muted-foreground/40 mx-auto mb-3" />
            <h3 className="text-sm font-semibold text-foreground">No Objectives Defined</h3>
            <p className="text-xs text-muted-foreground mt-1 mb-4 max-w-sm mx-auto">
              Define measurable engineering and product goals with associated key results.
            </p>
            <Button
              onClick={() => setCreateModalOpen(true)}
              size="sm"
              variant="primary"
              className="text-xs h-8"
            >
              <Plus className="w-3.5 h-3.5 mr-1" />
              Define First Objective
            </Button>
          </div>
        ) : (
          objectives.map((obj) => {
            const isExpanded = expandedIds.includes(obj.id);
            const progress = obj.progressPercentage || 0;

            return (
              <div
                key={obj.id}
                className="border border-border/80 rounded-xl bg-surface overflow-hidden transition-all"
              >
                {/* Objective Header */}
                <div
                  onClick={() => toggleExpand(obj.id)}
                  className="p-5 flex items-start justify-between gap-4 cursor-pointer hover:bg-surface-hover/50 transition-colors"
                >
                  <div className="flex items-start gap-3 min-w-0">
                    <button className="mt-0.5 text-muted-foreground hover:text-foreground">
                      {isExpanded ? (
                        <ChevronDown className="w-4 h-4" />
                      ) : (
                        <ChevronRight className="w-4 h-4" />
                      )}
                    </button>
                    <div>
                      <div className="flex items-center gap-2">
                        <h3 className="text-sm font-semibold text-foreground">
                          {obj.title}
                        </h3>
                        <span
                          className={`px-2 py-0.5 rounded text-[10px] font-semibold uppercase tracking-wider ${
                            obj.status === 'COMPLETED'
                              ? 'bg-emerald-500/15 text-emerald-400 border border-emerald-500/30'
                              : obj.status === 'AT_RISK'
                              ? 'bg-rose-500/15 text-rose-400 border border-rose-500/30'
                              : 'bg-surface-raised text-foreground border border-border'
                          }`}
                        >
                          {obj.status || 'IN_PROGRESS'}
                        </span>
                      </div>
                      {obj.description && (
                        <p className="text-xs text-muted-foreground mt-1 leading-relaxed">
                          {obj.description}
                        </p>
                      )}
                    </div>
                  </div>

                  {/* Progress Indicator */}
                  <div className="flex items-center gap-4 shrink-0">
                    {obj.targetDate && (
                      <div className="hidden sm:flex items-center gap-1.5 text-xs text-muted-foreground tabular-nums">
                        <Calendar className="w-3.5 h-3.5" />
                        <span>{obj.targetDate}</span>
                      </div>
                    )}
                    <div className="w-28 text-right">
                      <div className="flex justify-between text-[11px] tabular-nums mb-1">
                        <span className="text-muted-foreground">Progress</span>
                        <span className="font-semibold text-foreground">{progress}%</span>
                      </div>
                      <div className="w-full h-1.5 rounded-full bg-muted overflow-hidden">
                        <div
                          className="h-full bg-foreground rounded-full transition-all duration-300"
                          style={{ width: `${progress}%` }}
                        />
                      </div>
                    </div>
                  </div>
                </div>

                {/* Nested Key Results */}
                {isExpanded && (
                  <div className="px-5 pb-5 pt-1 border-t border-border/60 bg-surface-hover/20 space-y-3">
                    <div className="text-[11px] font-semibold uppercase tracking-wider text-muted-foreground pt-2">
                      Key Results ({obj.keyResults?.length || 0})
                    </div>

                    {!obj.keyResults || obj.keyResults.length === 0 ? (
                      <div className="p-4 text-center text-xs text-muted-foreground italic">
                        No key results attached to this objective.
                      </div>
                    ) : (
                      obj.keyResults.map((kr) => {
                        const krPct = kr.progressPercentage || Math.min(100, Math.round((kr.currentValue / (kr.targetValue || 1)) * 100));

                        return (
                          <div
                            key={kr.id}
                            className="p-3 rounded-lg border border-border/80 bg-surface flex flex-wrap items-center justify-between gap-4 text-xs"
                          >
                            <div className="flex items-center gap-2.5 min-w-0">
                              <TrendingUp className="w-4 h-4 text-foreground shrink-0" />
                              <div>
                                <span className="font-medium text-foreground">{kr.title}</span>
                                <div className="text-[11px] text-muted-foreground tabular-nums mt-0.5">
                                  {kr.currentValue} / {kr.targetValue} {kr.unit}
                                </div>
                              </div>
                            </div>

                            {/* Value Slider / Quick update */}
                            <div className="flex items-center gap-3">
                              <input
                                type="range"
                                min={0}
                                max={kr.targetValue}
                                value={kr.currentValue}
                                onChange={(e) => {
                                  updateKrMutation.mutate({
                                    id: kr.id,
                                    value: Number(e.target.value),
                                  });
                                }}
                                className="w-28 accent-foreground cursor-pointer"
                              />
                              <span className="tabular-nums font-semibold text-foreground text-xs w-9 text-right">
                                {krPct}%
                              </span>
                            </div>
                          </div>
                        );
                      })
                    )}
                  </div>
                )}
              </div>
            );
          })
        )}
      </div>

      {/* Create Objective Modal */}
      <Modal
        isOpen={createModalOpen}
        onClose={() => setCreateModalOpen(false)}
        title="Create Strategic Objective"
      >
        <div className="space-y-3 pt-2">
          <div>
            <label className="block text-xs font-medium text-muted-foreground mb-1">
              Objective Title *
            </label>
            <input
              type="text"
              value={title}
              onChange={(e) => setTitle(e.target.value)}
              placeholder="e.g. Elevate Core API P99 Latency below 50ms"
              className="w-full h-8 px-3 rounded-md border border-border bg-background text-xs focus:outline-none focus:ring-1 focus:ring-primary"
            />
          </div>

          <div>
            <label className="block text-xs font-medium text-muted-foreground mb-1">
              Description
            </label>
            <textarea
              value={description}
              onChange={(e) => setDescription(e.target.value)}
              placeholder="Strategic rationale and executive alignment..."
              rows={3}
              className="w-full p-2.5 rounded-md border border-border bg-background text-xs focus:outline-none focus:ring-1 focus:ring-primary resize-none"
            />
          </div>

          <div>
            <label className="block text-xs font-medium text-muted-foreground mb-1">
              Target Completion Date
            </label>
            <input
              type="date"
              value={targetDate}
              onChange={(e) => setTargetDate(e.target.value)}
              className="w-full h-8 px-3 rounded-md border border-border bg-background text-xs focus:outline-none focus:ring-1 focus:ring-primary"
            />
          </div>

          <div className="flex justify-end gap-2 pt-3 border-t border-border">
            <Button
              variant="outline"
              size="sm"
              onClick={() => setCreateModalOpen(false)}
            >
              Cancel
            </Button>
            <Button
              size="sm"
              disabled={!title.trim() || createMutation.isPending}
              onClick={() => createMutation.mutate()}
            >
              {createMutation.isPending ? 'Creating...' : 'Create Objective'}
            </Button>
          </div>
        </div>
      </Modal>
    </div>
  );
}

export default GoalsPage;
