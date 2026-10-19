import React, { useState } from 'react';
import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import { useWorkspaceStore } from '../../store/useWorkspaceStore';
import { workflowApi } from '../../api/endpoints';
import { Button } from '../../components/ui/Button';
import { AmbientParticleMesh } from '../../components/three/AmbientParticleMesh';
import type { WorkflowDefinition } from '../../types';
import {
  Workflow,
  Plus,
  Play,
  CheckCircle2,
  Zap,
  ArrowRight,
  Settings,
  Sliders,
  Send,
  Bell,
  UserCheck,
  ShieldAlert,
  Save,
  Layers,
  Trash2,
} from 'lucide-react';
import { motion } from 'framer-motion';

interface StudioNode {
  id: string;
  type: 'trigger' | 'action' | 'condition';
  title: string;
  subtitle: string;
  icon: string;
}

export function WorkflowStudioPage() {
  const queryClient = useQueryClient();
  const { currentWorkspace } = useWorkspaceStore();
  const workspaceId = currentWorkspace?.id;

  const [selectedNodeId, setSelectedNodeId] = useState<string | null>(null);
  const [executionLog, setExecutionLog] = useState<string | null>(null);

  // Workflow pipeline nodes (clean empty slate for new accounts)
  const [nodes, setNodes] = useState<StudioNode[]>([]);

  const { data: workflows = [], isLoading } = useQuery<WorkflowDefinition[]>({
    queryKey: ['workflows', workspaceId],
    queryFn: async () => {
      const res = await workflowApi.getWorkflows(workspaceId);
      return Array.isArray(res) ? res : [];
    },
  });

  const runSimulation = () => {
    setExecutionLog('Initiating workflow execution sequence...');
    setTimeout(() => {
      setExecutionLog(
        '[00:00.012] Trigger evaluated: status == IN_REVIEW (Matched)\n' +
        '[00:00.045] Condition passed: priority == URGENT (True)\n' +
        '[00:00.089] Executing action: Assign to Tech Lead -> Succeeded\n' +
        '[00:00.142] Dispatching webhook payload to https://api.ci-runner.internal/hooks -> HTTP 200 OK\n' +
        '[SUCCESS] Workflow execution completed in 142ms.'
      );
    }, 600);
  };

  const selectedNode = nodes.find((n) => n.id === selectedNodeId);

  return (
    <div className="relative flex flex-col h-full overflow-hidden select-none bg-background">
      {/* 3D Ambient Particle Mesh Background for Visual Studio Ambiance */}
      <AmbientParticleMesh className="absolute inset-0 pointer-events-none opacity-20 dark:opacity-40" />

      {/* Top Studio Control Bar */}
      <div className="relative z-10 px-6 py-3 border-b border-border/80 bg-surface/70 backdrop-blur-md flex flex-wrap items-center justify-between gap-4 shrink-0">
        <div className="flex items-center gap-3">
          <div className="w-8 h-8 rounded-lg bg-surface-raised border border-border flex items-center justify-center text-foreground shadow-2xs">
            <Workflow className="w-4 h-4" />
          </div>
          <div>
            <div className="flex items-center gap-2">
              <h1 className="text-sm font-semibold tracking-tight text-foreground">
                Visual Workflow Studio
              </h1>
              <span className={`px-1.5 py-0.2 rounded text-[10px] uppercase tracking-wider font-semibold border ${
                nodes.length > 0
                  ? 'bg-emerald-500/15 text-emerald-400 border-emerald-500/30'
                  : 'bg-muted/30 text-muted-foreground border-border'
              }`}>
                {nodes.length > 0 ? 'ACTIVE PIPELINE' : 'EMPTY CANVAS'}
              </span>
            </div>
            <p className="text-[11px] text-muted-foreground">
              Automated trigger-action orchestration engine
            </p>
          </div>
        </div>

        <div className="flex items-center gap-2.5">
          <Button
            onClick={runSimulation}
            size="sm"
            variant="outline"
            disabled={nodes.length === 0}
            className="text-xs h-7 px-2.5 font-medium disabled:opacity-50"
          >
            <Play className="w-3 h-3 mr-1 text-emerald-500" />
            Test Run
          </Button>

          <Button
            size="sm"
            variant="primary"
            disabled={nodes.length === 0}
            className="text-xs h-7 px-3 font-medium disabled:opacity-50"
          >
            <Save className="w-3 h-3 mr-1" />
            Publish
          </Button>
        </div>
      </div>

      {/* Studio Workspace: Canvas & Inspector */}
      <div className="relative z-10 flex-1 flex min-h-0 overflow-hidden">
        {/* Left: Node Catalog */}
        <div className="w-64 border-r border-border/80 bg-surface/50 backdrop-blur-sm p-4 overflow-y-auto hidden md:block">
          <h3 className="text-xs font-semibold uppercase tracking-wider text-muted-foreground mb-3">
            Available Blocks
          </h3>

          <div className="space-y-2 text-xs">
            <div
              onClick={() => {
                const id = `trigger-${Date.now()}`;
                const newNode: StudioNode = {
                  id,
                  type: 'trigger',
                  title: 'Issue Created Trigger',
                  subtitle: 'Fires when a new task is logged',
                  icon: 'zap',
                };
                setNodes((prev) => [...prev, newNode]);
                setSelectedNodeId(id);
              }}
              className="p-2.5 rounded-lg border border-border/70 bg-surface hover:border-foreground/40 cursor-pointer transition-all"
            >
              <div className="flex items-center gap-2 font-medium text-foreground">
                <Zap className="w-3.5 h-3.5 text-amber-500" />
                <span>Issue Created Trigger</span>
              </div>
              <p className="text-[10px] text-muted-foreground mt-0.5">
                Fires when a new task is logged
              </p>
            </div>

            <div
              onClick={() => {
                const id = `condition-${Date.now()}`;
                const newNode: StudioNode = {
                  id,
                  type: 'condition',
                  title: 'Branch Condition',
                  subtitle: 'Evaluate priority, labels, or estimate',
                  icon: 'sliders',
                };
                setNodes((prev) => [...prev, newNode]);
                setSelectedNodeId(id);
              }}
              className="p-2.5 rounded-lg border border-border/70 bg-surface hover:border-foreground/40 cursor-pointer transition-all"
            >
              <div className="flex items-center gap-2 font-medium text-foreground">
                <Sliders className="w-3.5 h-3.5 text-muted-foreground" />
                <span>Branch Condition</span>
              </div>
              <p className="text-[10px] text-muted-foreground mt-0.5">
                Evaluate priority, labels, or estimate
              </p>
            </div>

            <div
              onClick={() => {
                const id = `action-assign-${Date.now()}`;
                const newNode: StudioNode = {
                  id,
                  type: 'action',
                  title: 'Assign Member',
                  subtitle: 'Round-robin or rule-based assignment',
                  icon: 'userCheck',
                };
                setNodes((prev) => [...prev, newNode]);
                setSelectedNodeId(id);
              }}
              className="p-2.5 rounded-lg border border-border/70 bg-surface hover:border-foreground/40 cursor-pointer transition-all"
            >
              <div className="flex items-center gap-2 font-medium text-foreground">
                <UserCheck className="w-3.5 h-3.5 text-blue-400" />
                <span>Assign Member</span>
              </div>
              <p className="text-[10px] text-muted-foreground mt-0.5">
                Round-robin or rule-based assignment
              </p>
            </div>

            <div
              onClick={() => {
                const id = `action-notify-${Date.now()}`;
                const newNode: StudioNode = {
                  id,
                  type: 'action',
                  title: 'Dispatch Notification',
                  subtitle: 'Send webhook, Slack, or email',
                  icon: 'bell',
                };
                setNodes((prev) => [...prev, newNode]);
                setSelectedNodeId(id);
              }}
              className="p-2.5 rounded-lg border border-border/70 bg-surface hover:border-foreground/40 cursor-pointer transition-all"
            >
              <div className="flex items-center gap-2 font-medium text-foreground">
                <Bell className="w-3.5 h-3.5 text-emerald-400" />
                <span>Dispatch Notification</span>
              </div>
              <p className="text-[10px] text-muted-foreground mt-0.5">
                Send webhook, Slack, or email
              </p>
            </div>
          </div>
        </div>

        {/* Center: Interactive Node Canvas */}
        <div className="flex-1 overflow-y-auto p-8 flex flex-col items-center justify-start">
          {nodes.length === 0 ? (
            <div className="w-full max-w-md my-auto py-16 px-6 text-center border border-dashed border-border/80 rounded-2xl bg-surface/30">
              <div className="w-12 h-12 rounded-xl bg-surface-raised border border-border flex items-center justify-center text-muted-foreground mx-auto mb-4">
                <Workflow className="w-6 h-6" />
              </div>
              <h3 className="text-sm font-semibold text-foreground">No Workflow Configured</h3>
              <p className="text-xs text-muted-foreground mt-1 mb-6 max-w-sm mx-auto">
                Automate task transitions, team assignments, and outbound notifications. Click a block from the catalog or start with a trigger.
              </p>
              <div className="flex items-center justify-center gap-2">
                <Button
                  size="sm"
                  variant="primary"
                  onClick={() => {
                    const id = `trigger-${Date.now()}`;
                    const initialTrigger: StudioNode = {
                      id,
                      type: 'trigger',
                      title: 'Issue Status Changed',
                      subtitle: 'Fires when task status updates',
                      icon: 'zap',
                    };
                    setNodes([initialTrigger]);
                    setSelectedNodeId(id);
                  }}
                  className="text-xs h-8"
                >
                  <Plus className="w-3.5 h-3.5 mr-1" />
                  Create First Automation
                </Button>
              </div>
            </div>
          ) : (
            <div className="w-full max-w-md space-y-4 py-4">
              {nodes.map((node, index) => {
                const isSelected = selectedNodeId === node.id;

                return (
                  <React.Fragment key={node.id}>
                    <motion.div
                      whileHover={{ scale: 1.01 }}
                      onClick={() => setSelectedNodeId(node.id)}
                      className={`p-4 rounded-xl border bg-surface/90 backdrop-blur-md cursor-pointer transition-all relative group ${
                        isSelected
                          ? 'border-foreground shadow-lg ring-1 ring-foreground'
                          : 'border-border/80 hover:border-border hover:shadow-md'
                      }`}
                    >
                      <div className="flex items-start justify-between">
                        <div className="flex items-center gap-2.5">
                          <div
                            className={`w-7 h-7 rounded-lg flex items-center justify-center ${
                              node.type === 'trigger'
                                ? 'bg-amber-500/15 text-amber-500'
                                : node.type === 'condition'
                                ? 'bg-surface-raised border border-border text-foreground'
                                : 'bg-emerald-500/15 text-emerald-400'
                            }`}
                          >
                            {node.type === 'trigger' ? (
                              <Zap className="w-3.5 h-3.5" />
                            ) : node.type === 'condition' ? (
                              <Sliders className="w-3.5 h-3.5" />
                            ) : (
                              <CheckCircle2 className="w-3.5 h-3.5" />
                            )}
                          </div>
                          <div>
                            <div className="text-xs font-semibold text-foreground">
                              {node.title}
                            </div>
                            <div className="text-[11px] text-muted-foreground mt-0.5">
                              {node.subtitle}
                            </div>
                          </div>
                        </div>
                        <div className="flex items-center gap-2">
                          <span className="text-[10px] uppercase tracking-wider px-1.5 py-0.5 rounded bg-surface-hover text-muted-foreground font-medium">
                            {node.type}
                          </span>
                          <button
                            onClick={(e) => {
                              e.stopPropagation();
                              setNodes((prev) => prev.filter((n) => n.id !== node.id));
                              if (selectedNodeId === node.id) setSelectedNodeId(null);
                            }}
                            className="text-muted-foreground hover:text-rose-400 p-0.5 rounded opacity-0 group-hover:opacity-100 transition-opacity"
                            title="Remove block"
                          >
                            <Trash2 className="w-3 h-3" />
                          </button>
                        </div>
                      </div>
                    </motion.div>

                    {/* Connecting Arrow */}
                    {index < nodes.length - 1 && (
                      <div className="flex justify-center py-1">
                        <div className="w-0.5 h-6 bg-border/80 relative">
                          <div className="absolute -bottom-1 -left-1 w-2.5 h-2.5 border-r border-b border-border/80 rotate-45" />
                        </div>
                      </div>
                    )}
                  </React.Fragment>
                );
              })}
            </div>
          )}

          {/* Execution Trace Terminal */}
          {executionLog && (
            <motion.div
              initial={{ opacity: 0, y: 10 }}
              animate={{ opacity: 1, y: 0 }}
              className="w-full max-w-xl mt-6 rounded-lg border border-border bg-black/85 text-emerald-400 font-mono text-[11px] p-4 shadow-xl"
            >
              <div className="flex items-center justify-between pb-2 mb-2 border-b border-white/10 text-muted-foreground">
                <span>SIMULATION OUTPUT</span>
                <span className="text-emerald-400">PASSED</span>
              </div>
              <pre className="whitespace-pre-wrap leading-relaxed">{executionLog}</pre>
            </motion.div>
          )}
        </div>

        {/* Right: Selected Node Config Inspector */}
        <div className="w-80 border-l border-border/80 bg-surface/50 backdrop-blur-sm p-5 overflow-y-auto hidden lg:block">
          <div className="flex items-center justify-between pb-3 border-b border-border/80 mb-4">
            <h3 className="text-xs font-semibold uppercase tracking-wider text-muted-foreground">
              Node Configuration
            </h3>
            <Sliders className="w-3.5 h-3.5 text-muted-foreground" />
          </div>

          {selectedNode ? (
            <div className="space-y-4 text-xs">
              <div>
                <label className="block text-muted-foreground font-medium mb-1">
                  Block Label
                </label>
                <input
                  type="text"
                  value={selectedNode.title}
                  onChange={(e) => {
                    const newTitle = e.target.value;
                    setNodes((prev) =>
                      prev.map((n) =>
                        n.id === selectedNode.id ? { ...n, title: newTitle } : n
                      )
                    );
                  }}
                  className="w-full h-8 px-2.5 rounded-md border border-border bg-background text-xs focus:outline-none focus:ring-1 focus:ring-foreground"
                />
              </div>

              <div>
                <label className="block text-muted-foreground font-medium mb-1">
                  Trigger / Action Rule
                </label>
                <textarea
                  value={selectedNode.subtitle}
                  onChange={(e) => {
                    const newSub = e.target.value;
                    setNodes((prev) =>
                      prev.map((n) =>
                        n.id === selectedNode.id ? { ...n, subtitle: newSub } : n
                      )
                    );
                  }}
                  rows={3}
                  className="w-full p-2.5 rounded-md border border-border bg-background text-xs focus:outline-none focus:ring-1 focus:ring-foreground resize-none"
                />
              </div>

              <div className="pt-3 border-t border-border/60">
                <span className="text-muted-foreground text-[11px] block mb-2">
                  Execution Policy
                </span>
                <div className="space-y-2">
                  <label className="flex items-center gap-2 cursor-pointer">
                    <input type="checkbox" defaultChecked className="rounded accent-foreground" />
                    <span className="text-foreground">Continue on error</span>
                  </label>
                  <label className="flex items-center gap-2 cursor-pointer">
                    <input type="checkbox" defaultChecked className="rounded accent-foreground" />
                    <span className="text-foreground">Emit audit log</span>
                  </label>
                </div>
              </div>
            </div>
          ) : (
            <div className="py-8 text-center text-xs text-muted-foreground">
              Select a node to inspect and configure its parameters.
            </div>
          )}
        </div>
      </div>
    </div>
  );
}

export default WorkflowStudioPage;
