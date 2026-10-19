import React, { useState } from 'react';
import { useQuery } from '@tanstack/react-query';
import { adminApi } from '../../api/endpoints';
import { useThemeStore } from '../../store/useThemeStore';
import { Button } from '../../components/ui/Button';
import {
  Server,
  Database,
  Activity,
  Cpu,
  CheckCircle2,
  AlertTriangle,
  RefreshCw,
  Clock,
  ToggleLeft,
  ToggleRight,
  ShieldAlert,
  Play,
  Key,
  Sun,
  Moon,
  Monitor,
} from 'lucide-react';

interface ScheduledJob {
  id: string;
  name: string;
  cron: string;
  lastRun: string;
  status: 'SUCCESS' | 'RUNNING' | 'FAILED';
  duration: string;
}

export function PlatformAdminPage() {
  const { theme, setTheme } = useThemeStore();
  const [featureFlags, setFeatureFlags] = useState([
    { id: 'ai-copilot', label: 'AI Natural Language Copilot (/api/v1/ai)', enabled: true },
    { id: 'threejs-canvas', label: 'Three.js Visual Canvas Accelerators', enabled: true },
    { id: 'webhooks', label: 'Outbound Automation Webhook Dispatcher', enabled: true },
    { id: 'granular-audit', label: 'Immutable Enterprise Audit Logging', enabled: true },
  ]);

  const [jobs, setJobs] = useState<ScheduledJob[]>([
    {
      id: 'job-1',
      name: 'Sprint Burndown Snapshot Aggregator',
      cron: '0 0 * * * ?',
      lastRun: '12 minutes ago',
      status: 'SUCCESS',
      duration: '420ms',
    },
    {
      id: 'job-2',
      name: 'Stale Refresh Token Pruning Worker',
      cron: '0 0 2 * * ?',
      lastRun: '13 hours ago',
      status: 'SUCCESS',
      duration: '1.2s',
    },
    {
      id: 'job-3',
      name: 'Outbound Notification Queue Worker',
      cron: '*/30 * * * * *',
      lastRun: '18 seconds ago',
      status: 'SUCCESS',
      duration: '48ms',
    },
  ]);

  const { data: health, isLoading, refetch, isFetching } = useQuery({
    queryKey: ['adminHealth'],
    queryFn: async () => {
      return adminApi.getHealth();
    },
    refetchInterval: 15000,
  });

  const toggleFlag = (id: string) => {
    setFeatureFlags((prev) =>
      prev.map((f) => (f.id === id ? { ...f, enabled: !f.enabled } : f))
    );
  };

  const triggerJob = (id: string) => {
    alert(`Triggered manual execution for ${id}`);
  };

  return (
    <div className="flex flex-col h-full overflow-y-auto select-none p-6 max-w-7xl mx-auto space-y-6">
      {/* Top Header */}
      <div className="flex flex-wrap items-center justify-between gap-4 pb-5 border-b border-border/80">
        <div>
          <h1 className="text-xl font-semibold tracking-tight text-foreground flex items-center gap-2.5">
            <Server className="w-5 h-5 text-foreground" />
            Platform Diagnostics & Operations
          </h1>
          <p className="text-xs text-muted-foreground mt-1">
            Spring Boot 4.1.0 runtime health, background job scheduler, and global feature flags
          </p>
        </div>

        <div className="flex items-center gap-2">
          {isFetching && (
            <RefreshCw className="w-3.5 h-3.5 text-muted-foreground animate-spin mr-1" />
          )}
          <Button
            onClick={() => refetch()}
            size="sm"
            variant="outline"
            className="text-xs h-8 font-medium"
          >
            Refresh Diagnostics
          </Button>
        </div>
      </div>

      {/* Subsystem Health Cards */}
      <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-4">
        <div className="p-4 rounded-xl border border-border/80 bg-surface">
          <div className="flex items-center justify-between">
            <span className="text-xs font-medium text-muted-foreground">Spring Boot Runtime</span>
            <CheckCircle2 className="w-4 h-4 text-emerald-500" />
          </div>
          <div className="mt-2 flex items-baseline gap-2">
            <span className="text-xl font-bold tabular-nums tracking-tight text-foreground">
              {health?.status || 'UP'}
            </span>
            <span className="text-[11px] text-emerald-500 tabular-nums">v4.1.0</span>
          </div>
          <p className="text-[11px] text-muted-foreground mt-1">JVM 21 (Temurin 64-bit)</p>
        </div>

        <div className="p-4 rounded-xl border border-border/80 bg-surface">
          <div className="flex items-center justify-between">
            <span className="text-xs font-medium text-muted-foreground">PostgreSQL Database</span>
            <Database className="w-4 h-4 text-foreground" />
          </div>
          <div className="mt-2 flex items-baseline gap-2">
            <span className="text-xl font-bold tabular-nums tracking-tight text-foreground">CONNECTED</span>
            <span className="text-[11px] text-muted-foreground tabular-nums">10/10 pool</span>
          </div>
          <p className="text-[11px] text-muted-foreground mt-1">HikariCP connection pool active</p>
        </div>

        <div className="p-4 rounded-xl border border-border/80 bg-surface">
          <div className="flex items-center justify-between">
            <span className="text-xs font-medium text-muted-foreground">Memory / Heap</span>
            <Cpu className="w-4 h-4 text-foreground" />
          </div>
          <div className="mt-2 flex items-baseline gap-2">
            <span className="text-xl font-bold tabular-nums tracking-tight text-foreground">348 MB</span>
            <span className="text-[11px] text-muted-foreground tabular-nums">/ 2048 MB</span>
          </div>
          <p className="text-[11px] text-muted-foreground mt-1">G1GC garbage collection healthy</p>
        </div>

        <div className="p-4 rounded-xl border border-border/80 bg-surface">
          <div className="flex items-center justify-between">
            <span className="text-xs font-medium text-muted-foreground">License Tier</span>
            <Key className="w-4 h-4 text-amber-500" />
          </div>
          <div className="mt-2 flex items-baseline gap-2">
            <span className="text-xl font-bold tabular-nums tracking-tight text-foreground">ENTERPRISE</span>
          </div>
          <p className="text-[11px] text-muted-foreground mt-1">Unlimited seats & API throughput</p>
        </div>
      </div>

      {/* Background Job Scheduler */}
      <div className="border border-border/80 rounded-xl bg-surface overflow-hidden">
        <div className="px-5 py-3 border-b border-border/80 flex items-center justify-between">
          <h3 className="text-xs font-semibold uppercase tracking-wider text-muted-foreground flex items-center gap-2">
            <Clock className="w-3.5 h-3.5" />
            Background Scheduled Jobs & Queues
          </h3>
          <span className="text-[11px] text-muted-foreground">
            Spring @Scheduled / Async Execution
          </span>
        </div>

        <div className="overflow-x-auto">
          <table className="w-full text-left text-xs border-collapse">
            <thead>
              <tr className="border-b border-border bg-surface-hover/50 text-muted-foreground font-medium">
                <th className="px-5 py-2.5">Job Name</th>
                <th className="px-4 py-2.5">Cron Schedule</th>
                <th className="px-4 py-2.5">Last Run</th>
                <th className="px-4 py-2.5">Duration</th>
                <th className="px-4 py-2.5">Status</th>
                <th className="px-4 py-2.5 text-right">Actions</th>
              </tr>
            </thead>
            <tbody className="divide-y divide-border/60">
              {jobs.map((job) => (
                <tr key={job.id} className="hover:bg-surface-hover/40 transition-colors">
                  <td className="px-5 py-3 font-semibold text-foreground">
                    {job.name}
                  </td>
                  <td className="px-4 py-3 tabular-nums text-[11px] text-muted-foreground">
                    {job.cron}
                  </td>
                  <td className="px-4 py-3 text-muted-foreground text-[11px]">
                    {job.lastRun}
                  </td>
                  <td className="px-4 py-3 tabular-nums text-[11px] text-muted-foreground">
                    {job.duration}
                  </td>
                  <td className="px-4 py-3">
                    <span className="px-2 py-0.5 rounded text-[10px] font-semibold uppercase bg-emerald-500/15 text-emerald-400 border border-emerald-500/30">
                      {job.status}
                    </span>
                  </td>
                  <td className="px-4 py-3 text-right">
                    <button
                      onClick={() => triggerJob(job.id)}
                      className="px-2 py-1 rounded border border-border bg-background hover:bg-surface-hover text-[11px] font-medium text-foreground transition-colors inline-flex items-center gap-1"
                    >
                      <Play className="w-3 h-3 text-primary" />
                      <span>Trigger</span>
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>

      {/* Platform Theme & Appearance Settings */}
      <div className="border border-border/80 rounded-xl bg-surface p-5 space-y-4">
        <div>
          <h3 className="text-sm font-semibold text-foreground flex items-center gap-2">
            <Sun className="w-4 h-4 text-primary" />
            Platform Theme & Workspace Appearance
          </h3>
          <p className="text-xs text-muted-foreground mt-0.5">
            Configure system-wide visual theme across the SprintForge interface
          </p>
        </div>

        <div className="grid grid-cols-1 sm:grid-cols-3 gap-3 pt-1">
          <button
            onClick={() => setTheme('light')}
            className={`p-3.5 rounded-xl border text-left transition-all flex items-center justify-between ${
              theme === 'light'
                ? 'bg-surface-active text-foreground border-foreground font-semibold shadow-xs'
                : 'bg-surface hover:bg-surface-hover border-border text-muted-foreground hover:text-foreground'
            }`}
          >
            <div className="flex items-center gap-2.5">
              <Sun className="w-4 h-4 text-amber-500" />
              <div>
                <div className="text-xs font-semibold">Light Mode</div>
                <div className="text-[11px] opacity-75">Crisp high-contrast white</div>
              </div>
            </div>
            {theme === 'light' && <CheckCircle2 className="w-4 h-4 text-foreground" />}
          </button>

          <button
            onClick={() => setTheme('dark')}
            className={`p-3.5 rounded-xl border text-left transition-all flex items-center justify-between ${
              theme === 'dark'
                ? 'bg-surface-active text-foreground border-foreground font-semibold shadow-xs'
                : 'bg-surface hover:bg-surface-hover border-border text-muted-foreground hover:text-foreground'
            }`}
          >
            <div className="flex items-center gap-2.5">
              <Moon className="w-4 h-4 text-blue-400" />
              <div>
                <div className="text-xs font-semibold">Dark Mode</div>
                <div className="text-[11px] opacity-75">Pitch-black Linear obsidian</div>
              </div>
            </div>
            {theme === 'dark' && <CheckCircle2 className="w-4 h-4 text-foreground" />}
          </button>

          <button
            onClick={() => setTheme('system')}
            className={`p-3.5 rounded-xl border text-left transition-all flex items-center justify-between ${
              theme === 'system'
                ? 'bg-surface-active text-foreground border-foreground font-semibold shadow-xs'
                : 'bg-surface hover:bg-surface-hover border-border text-muted-foreground hover:text-foreground'
            }`}
          >
            <div className="flex items-center gap-2.5">
              <Monitor className="w-4 h-4 text-muted-foreground" />
              <div>
                <div className="text-xs font-semibold">System Sync</div>
                <div className="text-[11px] opacity-75">Follows OS preference</div>
              </div>
            </div>
            {theme === 'system' && <CheckCircle2 className="w-4 h-4 text-foreground" />}
          </button>
        </div>
      </div>

      {/* Feature Flags Management */}
      <div className="border border-border/80 rounded-xl bg-surface p-5 space-y-4">
        <div>
          <h3 className="text-sm font-semibold text-foreground">Enterprise Feature Flags</h3>
          <p className="text-xs text-muted-foreground">Dynamically activate or throttle platform modules</p>
        </div>

        <div className="space-y-3 pt-2">
          {featureFlags.map((flag) => (
            <div
              key={flag.id}
              className="p-3 rounded-lg border border-border/70 bg-surface-hover/30 flex items-center justify-between text-xs"
            >
              <div className="font-medium text-foreground">{flag.label}</div>
              <button
                onClick={() => toggleFlag(flag.id)}
                className={`p-1 rounded transition-colors ${
                  flag.enabled ? 'text-primary' : 'text-muted-foreground'
                }`}
              >
                {flag.enabled ? (
                  <ToggleRight className="w-6 h-6" />
                ) : (
                  <ToggleLeft className="w-6 h-6" />
                )}
              </button>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}

export default PlatformAdminPage;
