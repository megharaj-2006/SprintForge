import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import { motion } from 'framer-motion';
import {
  ArrowRight,
  Sparkles,
  Command,
  Zap,
  ShieldCheck,
  Workflow,
  BarChart2,
  CheckCircle2,
  Lock,
  Layers,
  Terminal,
  Clock,
  ChevronRight,
  TrendingUp,
  Cpu,
  FileCheck2,
  Users,
} from 'lucide-react';
import { Button } from '../../components/ui/Button';
import { SpotlightCard } from '../../components/marketing/SpotlightCard';
import { ScrollReveal } from '../../components/marketing/ScrollReveal';
import { HeroInteractivePreview } from '../../components/marketing/HeroInteractivePreview';
import { TerrainWireframeCanvas } from '../../components/three/TerrainWireframeCanvas';

export function LandingPage() {
  const [activeFeatureTab, setActiveFeatureTab] = useState<'cycles' | 'workflows' | 'okrs' | 'governance'>('cycles');

  return (
    <div className="relative overflow-hidden">
      {/* 3D Interactive Topographic Wireframe Stage in Hero Background */}
      <TerrainWireframeCanvas className="pointer-events-none absolute inset-0 h-[880px] w-full overflow-hidden" />

      {/* ======================================================== */}
      {/* SECTION 1: HERO */}
      {/* ======================================================== */}
      <section className="relative pt-24 pb-16 md:pt-32 md:pb-24 px-4 sm:px-6 lg:px-8 max-w-7xl mx-auto text-center">
        {/* Release Announcement Pill */}
        <ScrollReveal delay={0.1} direction="down">
          <Link
            to="/how-it-works"
            className="inline-flex items-center gap-2 rounded-full border border-border/80 bg-surface/70 backdrop-blur-md px-3.5 py-1 text-xs font-medium text-foreground hover:border-foreground/30 hover:bg-surface-hover/80 transition-all mb-8 shadow-xs"
          >
            <span className="flex h-2 w-2 rounded-full bg-foreground" />
            <span>SprintForge 2.0 is Live</span>
            <span className="text-muted-foreground">•</span>
            <span className="text-muted-foreground flex items-center gap-0.5">
              Explore Architecture <ChevronRight className="w-3 h-3" />
            </span>
          </Link>
        </ScrollReveal>

        {/* Hero Headline */}
        <ScrollReveal delay={0.2}>
          <h1 className="text-4xl sm:text-6xl lg:text-7xl font-extrabold tracking-tight text-foreground leading-[1.08] max-w-4xl mx-auto">
            Engineered for speed. <br />
            <span className="text-muted-foreground font-semibold">
              Built for high-velocity teams.
            </span>
          </h1>
        </ScrollReveal>

        {/* Subtitle */}
        <ScrollReveal delay={0.3}>
          <p className="mt-6 text-base sm:text-lg text-muted-foreground max-w-2xl mx-auto leading-relaxed">
            The project management platform designed with linear precision, sub-millisecond execution, autonomous AI intelligence, and enterprise-grade governance.
          </p>
        </ScrollReveal>

        {/* Primary CTAs */}
        <ScrollReveal delay={0.4}>
          <div className="mt-8 flex flex-col sm:flex-row items-center justify-center gap-3 sm:gap-4">
            <Link to="/signup">
              <Button
                variant="primary"
                size="lg"
                className="w-full sm:w-auto h-11 px-6 text-sm font-semibold shadow-lg hover:shadow-xl transition-all"
                icon={<ArrowRight className="w-4 h-4 order-last" />}
              >
                Start for free
              </Button>
            </Link>
            <Link to="/login">
              <Button
                variant="outline"
                size="lg"
                className="w-full sm:w-auto h-11 px-6 text-sm font-semibold bg-surface/60 backdrop-blur-md hover:bg-surface-hover transition-all"
              >
                Sign in to workspace
              </Button>
            </Link>
          </div>

          <div className="mt-4 flex items-center justify-center gap-2 text-xs text-muted-foreground">
            <span>Instant signup</span>
            <span>•</span>
            <span>No credit card required</span>
            <span>•</span>
            <span>Free forever for developers</span>
          </div>
        </ScrollReveal>

        {/* Interactive Window Preview */}
        <ScrollReveal delay={0.5} className="mt-14 sm:mt-20">
          <HeroInteractivePreview />
        </ScrollReveal>
      </section>

      {/* ======================================================== */}
      {/* SECTION 2: SOCIAL PROOF / ENTERPRISE LOGOS */}
      {/* ======================================================== */}
      <section className="py-14 border-y border-border/60 bg-surface/30 backdrop-blur-sm">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 text-center">
          <p className="text-xs font-semibold uppercase tracking-wider text-muted-foreground mb-8">
            Powering high-velocity engineering organizations
          </p>
          <div className="grid grid-cols-2 sm:grid-cols-3 md:grid-cols-6 gap-8 items-center justify-center opacity-70 grayscale hover:grayscale-0 transition-all">
            {[
              'VELOCE LABS',
              'HYPERSCALE',
              'MONOLITH',
              'VECTOR CLOUD',
              'APEX ARCH',
              'QUANTUM CORE',
            ].map((company) => (
              <div
                key={company}
                className="text-xs sm:text-sm font-bold tracking-widest text-foreground hover:text-foreground transition-colors select-none"
              >
                {company}
              </div>
            ))}
          </div>
        </div>
      </section>

      {/* ======================================================== */}
      {/* SECTION 3: BENTO GRID VALUE PILLARS (ANTIGRAVITY HOVER) */}
      {/* ======================================================== */}
      <section id="features" className="py-24 px-4 sm:px-6 lg:px-8 max-w-7xl mx-auto">
        <ScrollReveal>
          <div className="text-center max-w-3xl mx-auto mb-16">
            <h2 className="text-xs font-semibold uppercase tracking-wider text-muted-foreground mb-3">
              Core Capabilities
            </h2>
            <h3 className="text-3xl sm:text-4xl font-extrabold tracking-tight text-foreground">
              Built from first principles for uncompromising teams.
            </h3>
            <p className="text-sm sm:text-base text-muted-foreground mt-4 leading-relaxed">
              Every interaction is engineered to eliminate context switching, reduce latency, and empower engineers to spend time building rather than managing tools.
            </p>
          </div>
        </ScrollReveal>

        <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
          {/* Card 1: Keyboard-First Navigation (Span 2) */}
          <ScrollReveal delay={0.1} className="md:col-span-2">
            <SpotlightCard className="h-full flex flex-col justify-between">
              <div>
                <div className="h-10 w-10 rounded-xl bg-foreground text-background flex items-center justify-center font-bold mb-4 shadow-sm">
                  <Command className="w-5 h-5" />
                </div>
                <h4 className="text-lg font-bold text-foreground mb-2">
                  Keyboard-First Ergonomics
                </h4>
                <p className="text-xs text-muted-foreground leading-relaxed mb-6">
                  Navigate the entire application without touching your mouse. Open the Omnisearch Command Palette (<kbd className="px-1.5 py-0.5 rounded border border-border bg-muted/60 text-foreground font-medium text-[11px]">Cmd+K</kbd>), draft issues with <kbd className="px-1.5 py-0.5 rounded border border-border bg-muted/60 text-foreground font-medium text-[11px]">C</kbd>, cycle focus with arrow keys, and execute bulk operations instantly.
                </p>
              </div>

              {/* Command Palette Visual Mock */}
              <div className="rounded-xl border border-border/80 bg-background/80 p-3 text-xs space-y-2">
                <div className="flex items-center gap-2 pb-2 border-b border-border/60 text-muted-foreground">
                  <Command className="w-3.5 h-3.5 text-foreground" />
                  <span className="text-foreground font-medium">Type a command or jump to issue...</span>
                </div>
                <div className="space-y-1">
                  <div className="flex items-center justify-between p-1.5 rounded bg-surface-active text-foreground font-medium">
                    <span>Create new issue in Core Engine</span>
                    <kbd className="text-[10px] bg-muted/60 px-1 rounded border border-border">C</kbd>
                  </div>
                  <div className="flex items-center justify-between p-1.5 rounded text-muted-foreground hover:bg-surface-hover">
                    <span>Switch to Active Kanban Board</span>
                    <kbd className="text-[10px] bg-muted/60 px-1 rounded border border-border">G B</kbd>
                  </div>
                  <div className="flex items-center justify-between p-1.5 rounded text-muted-foreground hover:bg-surface-hover">
                    <span>Summon Autonomous AI Copilot</span>
                    <kbd className="text-[10px] bg-muted/60 px-1 rounded border border-border">⌘J</kbd>
                  </div>
                </div>
              </div>
            </SpotlightCard>
          </ScrollReveal>

          {/* Card 2: Autonomous Copilot (Span 1) */}
          <ScrollReveal delay={0.2} className="md:col-span-1">
            <SpotlightCard className="h-full flex flex-col justify-between">
              <div>
                <div className="h-10 w-10 rounded-xl bg-foreground text-background flex items-center justify-center font-bold mb-4 shadow-sm">
                  <Sparkles className="w-5 h-5" />
                </div>
                <h4 className="text-lg font-bold text-foreground mb-2">
                  Autonomous AI Copilot
                </h4>
                <p className="text-xs text-muted-foreground leading-relaxed mb-6">
                  Context-aware assistant integrated into your issue graph. Summarizes pull requests, recommends story points, and spots recurring delivery bottlenecks.
                </p>
              </div>

              <div className="rounded-xl border border-border/80 bg-background/80 p-3 text-xs space-y-2">
                <div className="flex items-center gap-2 text-foreground font-medium">
                  <Sparkles className="w-3.5 h-3.5" />
                  <span>Cycle 24 Bottleneck Detected</span>
                </div>
                <p className="text-[11px] text-muted-foreground leading-relaxed">
                  "PR review turnaround increased to 2.1 days. 3 blocked tasks recommended for team reassignment."
                </p>
                <div className="pt-2 border-t border-border/60 flex items-center justify-between text-[10px]">
                  <span className="text-foreground font-semibold">1-Click Auto Reassign</span>
                  <ArrowRight className="w-3 h-3 text-foreground" />
                </div>
              </div>
            </SpotlightCard>
          </ScrollReveal>

          {/* Card 3: Visual Workflow Studio (Span 1) */}
          <ScrollReveal delay={0.3} className="md:col-span-1">
            <SpotlightCard className="h-full flex flex-col justify-between">
              <div>
                <div className="h-10 w-10 rounded-xl bg-foreground text-background flex items-center justify-center font-bold mb-4 shadow-sm">
                  <Workflow className="w-5 h-5" />
                </div>
                <h4 className="text-lg font-bold text-foreground mb-2">
                  Visual Workflow Studio
                </h4>
                <p className="text-xs text-muted-foreground leading-relaxed mb-6">
                  Design complex issue lifecycle state machines using visual DAG blocks. Trigger webhooks, enforce compliance, and dispatch notifications automatically.
                </p>
              </div>

              <div className="rounded-xl border border-border/80 bg-background/80 p-3 text-xs space-y-2">
                <div className="flex items-center justify-between text-[11px] font-semibold text-foreground">
                  <span>DAG Execution Engine</span>
                  <span className="text-emerald-400">ACTIVE</span>
                </div>
                <div className="flex items-center gap-1.5 text-[10px] text-muted-foreground">
                  <span className="px-1.5 py-0.5 rounded bg-muted/60 text-foreground font-medium">Trigger</span>
                  <span>→</span>
                  <span className="px-1.5 py-0.5 rounded bg-muted/60 text-foreground font-medium">Branch</span>
                  <span>→</span>
                  <span className="px-1.5 py-0.5 rounded bg-muted/60 text-foreground font-medium">Assign</span>
                </div>
              </div>
            </SpotlightCard>
          </ScrollReveal>

          {/* Card 4: Enterprise Governance & Risk Matrices (Span 2) */}
          <ScrollReveal delay={0.4} className="md:col-span-2">
            <SpotlightCard className="h-full flex flex-col justify-between">
              <div>
                <div className="h-10 w-10 rounded-xl bg-foreground text-background flex items-center justify-center font-bold mb-4 shadow-sm">
                  <ShieldCheck className="w-5 h-5" />
                </div>
                <h4 className="text-lg font-bold text-foreground mb-2">
                  Enterprise Governance & Tamper-Evident Ledger
                </h4>
                <p className="text-xs text-muted-foreground leading-relaxed mb-6">
                  Built-in ISO27001 and SOC2 risk management matrix. Quantify project hazards by likelihood and severity. Every issue change, permission grant, and role update is committed to a cryptographic audit trail.
                </p>
              </div>

              {/* Mini Risk Matrix Mock */}
              <div className="grid grid-cols-3 gap-2 rounded-xl border border-border/80 bg-background/80 p-3 text-xs">
                <div className="p-2 rounded bg-surface border border-border text-center">
                  <span className="text-[10px] text-muted-foreground block">Critical Severity</span>
                  <span className="text-sm font-bold text-rose-400 tabular-nums">0 active</span>
                </div>
                <div className="p-2 rounded bg-surface border border-border text-center">
                  <span className="text-[10px] text-muted-foreground block">High Severity</span>
                  <span className="text-sm font-bold text-amber-500 tabular-nums">2 mitigated</span>
                </div>
                <div className="p-2 rounded bg-surface border border-border text-center">
                  <span className="text-[10px] text-muted-foreground block">Audit Trail</span>
                  <span className="text-sm font-bold text-emerald-400">100% Verified</span>
                </div>
              </div>
            </SpotlightCard>
          </ScrollReveal>
        </div>
      </section>

      {/* ======================================================== */}
      {/* SECTION 4: INTERACTIVE FEATURE DEEP-DIVE TABS */}
      {/* ======================================================== */}
      <section className="py-24 border-t border-border/60 bg-surface/20">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <ScrollReveal>
            <div className="text-center max-w-3xl mx-auto mb-12">
              <h2 className="text-xs font-semibold uppercase tracking-wider text-muted-foreground mb-3">
                Precision Workflows
              </h2>
              <h3 className="text-3xl sm:text-4xl font-extrabold tracking-tight text-foreground">
                Engineered for clarity at every scale.
              </h3>
            </div>
          </ScrollReveal>

          {/* Tab Selector */}
          <div className="flex flex-wrap items-center justify-center gap-2 mb-10">
            {[
              { id: 'cycles', label: 'Cycles & Sprints', icon: <TrendingUp className="w-3.5 h-3.5" /> },
              { id: 'workflows', label: 'Visual Automation', icon: <Workflow className="w-3.5 h-3.5" /> },
              { id: 'okrs', label: 'Strategic OKRs', icon: <Layers className="w-3.5 h-3.5" /> },
              { id: 'governance', label: 'Risk & Audit Matrix', icon: <ShieldCheck className="w-3.5 h-3.5" /> },
            ].map((tab) => (
              <button
                key={tab.id}
                onClick={() => setActiveFeatureTab(tab.id as any)}
                className={`flex items-center gap-2 px-4 py-2 rounded-lg text-xs font-semibold transition-all ${
                  activeFeatureTab === tab.id
                    ? 'bg-foreground text-background shadow-md'
                    : 'bg-surface/80 border border-border/80 text-muted-foreground hover:text-foreground hover:bg-surface-hover'
                }`}
              >
                {tab.icon}
                {tab.label}
              </button>
            ))}
          </div>

          {/* Active Tab Panel */}
          <div className="max-w-4xl mx-auto rounded-2xl border border-border/80 bg-surface/80 backdrop-blur-xl p-8 shadow-xl">
            {activeFeatureTab === 'cycles' && (
              <div className="space-y-4">
                <div className="flex items-center justify-between">
                  <h4 className="text-base font-bold text-foreground">Automated Sprint Cycles</h4>
                  <span className="text-xs text-muted-foreground tabular-nums">Sprint 24 • Oct 02 - Oct 16</span>
                </div>
                <p className="text-xs text-muted-foreground leading-relaxed">
                  SprintForge automatically groups work into fixed engineering cadences. Unfinished tasks gracefully roll over into the next cycle or return to the triage backlog, preserving historical velocity metrics.
                </p>
                <div className="grid grid-cols-1 sm:grid-cols-3 gap-3 pt-2">
                  <div className="p-3 rounded-lg border border-border/60 bg-background/60">
                    <span className="text-[10px] text-muted-foreground block">Committed Velocity</span>
                    <span className="text-lg font-bold text-foreground tabular-nums">42 pts</span>
                  </div>
                  <div className="p-3 rounded-lg border border-border/60 bg-background/60">
                    <span className="text-[10px] text-muted-foreground block">Completed</span>
                    <span className="text-lg font-bold text-emerald-400 tabular-nums">38 pts (90%)</span>
                  </div>
                  <div className="p-3 rounded-lg border border-border/60 bg-background/60">
                    <span className="text-[10px] text-muted-foreground block">Avg Cycle Time</span>
                    <span className="text-lg font-bold text-foreground tabular-nums">1.8 days</span>
                  </div>
                </div>
              </div>
            )}

            {activeFeatureTab === 'workflows' && (
              <div className="space-y-4">
                <div className="flex items-center justify-between">
                  <h4 className="text-base font-bold text-foreground">Deterministic DAG Automation</h4>
                  <span className="text-xs text-emerald-400 font-semibold">Zero-code Rule Engine</span>
                </div>
                <p className="text-xs text-muted-foreground leading-relaxed">
                  Eliminate repetitive manual steps. When an issue status changes or PR merges, dispatch webhooks, notify team channels, update assignees, and trigger external CI pipelines without writing brittle glue scripts.
                </p>
                <div className="p-4 rounded-lg border border-border/60 bg-background/60 font-mono text-xs space-y-2">
                  <div className="text-muted-foreground">// Automated trigger block</div>
                  <div className="text-foreground">
                    <span className="text-amber-500 font-semibold">ON</span> issue.priority == 'URGENT' <span className="text-amber-500 font-semibold">DO</span> notify(oncall_team) & lock_sprint()
                  </div>
                </div>
              </div>
            )}

            {activeFeatureTab === 'okrs' && (
              <div className="space-y-4">
                <div className="flex items-center justify-between">
                  <h4 className="text-base font-bold text-foreground">Strategic Company OKRs</h4>
                  <span className="text-xs text-muted-foreground">Q4 Engineering Objectives</span>
                </div>
                <p className="text-xs text-muted-foreground leading-relaxed">
                  Connect daily task commits to top-level organizational key results. See real-time progress percentages calculated dynamically from linked issue completions.
                </p>
                <div className="space-y-3 pt-2">
                  <div>
                    <div className="flex justify-between text-xs font-medium mb-1">
                      <span className="text-foreground">Achieve &lt;100ms P99 API Latency Worldwide</span>
                      <span className="tabular-nums text-foreground">84%</span>
                    </div>
                    <div className="h-2 rounded-full bg-muted/60 overflow-hidden">
                      <div className="h-full bg-foreground rounded-full w-[84%]" />
                    </div>
                  </div>
                </div>
              </div>
            )}

            {activeFeatureTab === 'governance' && (
              <div className="space-y-4">
                <div className="flex items-center justify-between">
                  <h4 className="text-base font-bold text-foreground">Cryptographic Audit Ledger</h4>
                  <span className="text-xs text-muted-foreground flex items-center gap-1">
                    <ShieldCheck className="w-3.5 h-3.5 text-emerald-400" />
                    Immutable Hash Chains
                  </span>
                </div>
                <p className="text-xs text-muted-foreground leading-relaxed">
                  Every user interaction, workspace role grant, and task deletion is committed with an immutable timestamp and actor hash. Export compliance bundles directly for SOC2 and ISO27001 auditors with zero manual log scraping.
                </p>
              </div>
            )}
          </div>
        </div>
      </section>

      {/* ======================================================== */}
      {/* SECTION 5: PERFORMANCE COMPARISON TABLE */}
      {/* ======================================================== */}
      <section className="py-24 px-4 sm:px-6 lg:px-8 max-w-7xl mx-auto">
        <ScrollReveal>
          <div className="text-center max-w-3xl mx-auto mb-16">
            <h2 className="text-xs font-semibold uppercase tracking-wider text-muted-foreground mb-3">
              Performance Benchmark
            </h2>
            <h3 className="text-3xl sm:text-4xl font-extrabold tracking-tight text-foreground">
              Why engineers switch from legacy trackers.
            </h3>
            <p className="text-sm text-muted-foreground mt-4">
              Measured under identical conditions with 5,000 active issues and 50 concurrent members.
            </p>
          </div>
        </ScrollReveal>

        <ScrollReveal delay={0.2}>
          <div className="overflow-x-auto rounded-2xl border border-border/80 bg-surface/70 backdrop-blur-xl shadow-xl">
            <table className="w-full text-left text-xs border-collapse">
              <thead>
                <tr className="border-b border-border/80 bg-surface-hover/50 text-muted-foreground font-semibold">
                  <th className="px-6 py-4">Benchmark Metric</th>
                  <th className="px-6 py-4 text-foreground font-bold bg-foreground/5">
                    SprintForge 2.0
                  </th>
                  <th className="px-6 py-4">Jira Cloud</th>
                  <th className="px-6 py-4">Asana</th>
                  <th className="px-6 py-4">ClickUp</th>
                </tr>
              </thead>
              <tbody className="divide-y divide-border/60">
                {[
                  {
                    metric: 'Cold Start / Initial Load',
                    sf: '< 120ms',
                    jira: '3,400ms',
                    asana: '2,800ms',
                    clickup: '3,100ms',
                    win: true,
                  },
                  {
                    metric: 'Issue Creation Keystrokes',
                    sf: '1 (Key: C)',
                    jira: '4 Clicks + Dialog',
                    asana: '3 Clicks',
                    clickup: '4 Clicks',
                    win: true,
                  },
                  {
                    metric: 'Memory Footprint',
                    sf: '< 45 MB',
                    jira: '380 MB',
                    asana: '290 MB',
                    clickup: '420 MB',
                    win: true,
                  },
                  {
                    metric: 'Backend API Response Time',
                    sf: '< 15ms (Java 21)',
                    jira: '450ms',
                    asana: '320ms',
                    clickup: '510ms',
                    win: true,
                  },
                  {
                    metric: 'Keyboard Omnisearch',
                    sf: 'Instant (< 5ms)',
                    jira: 'Laggy (~800ms)',
                    asana: 'Search Dialog',
                    clickup: 'Modal Search',
                    win: true,
                  },
                  {
                    metric: 'Optimistic UI & Rollback',
                    sf: 'Native',
                    jira: 'None (Block & Spin)',
                    asana: 'Partial',
                    clickup: 'Partial',
                    win: true,
                  },
                ].map((row, idx) => (
                  <tr key={idx} className="hover:bg-surface-hover/40 transition-colors">
                    <td className="px-6 py-4 font-medium text-foreground">
                      {row.metric}
                    </td>
                    <td className="px-6 py-4 font-bold text-foreground bg-foreground/5 tabular-nums">
                      <span className="flex items-center gap-1.5">
                        <CheckCircle2 className="w-3.5 h-3.5 text-emerald-400" />
                        {row.sf}
                      </span>
                    </td>
                    <td className="px-6 py-4 text-muted-foreground tabular-nums">
                      {row.jira}
                    </td>
                    <td className="px-6 py-4 text-muted-foreground tabular-nums">
                      {row.asana}
                    </td>
                    <td className="px-6 py-4 text-muted-foreground tabular-nums">
                      {row.clickup}
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </ScrollReveal>
      </section>

      {/* ======================================================== */}
      {/* SECTION 6: TESTIMONIALS */}
      {/* ======================================================== */}
      <section className="py-24 border-t border-border/60 bg-surface/30">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <ScrollReveal>
            <div className="text-center max-w-3xl mx-auto mb-16">
              <h2 className="text-xs font-semibold uppercase tracking-wider text-muted-foreground mb-3">
                Engineering Testimonials
              </h2>
              <h3 className="text-3xl sm:text-4xl font-extrabold tracking-tight text-foreground">
                Loved by teams who value craftsmanship.
              </h3>
            </div>
          </ScrollReveal>

          <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
            {[
              {
                quote:
                  'SprintForge cut our issue triage latency by 70%. The keyboard ergonomics and zero-latency feel make returning to legacy enterprise software unthinkable.',
                author: 'Liam Vance',
                role: 'VP of Engineering, Veloce Labs',
              },
              {
                quote:
                  'The Java 21 Spring Boot core combined with sub-millisecond API response times gives our 400-person team the fastest project management tool we have ever used.',
                author: 'Aria Chen',
                role: 'Staff Infrastructure Architect, HyperScale',
              },
              {
                quote:
                  'The built-in SOC2 risk matrix and immutable audit trail allowed us to pass our enterprise security audit in 3 days without writing custom reporting scripts.',
                author: 'Devon Miller',
                role: 'Head of Security & Governance, Apex Core',
              },
            ].map((t, i) => (
              <ScrollReveal key={i} delay={i * 0.15}>
                <SpotlightCard className="h-full flex flex-col justify-between">
                  <p className="text-xs leading-relaxed text-foreground/90 italic mb-6">
                    "{t.quote}"
                  </p>
                  <div className="pt-4 border-t border-border/60">
                    <div className="text-xs font-bold text-foreground">{t.author}</div>
                    <div className="text-[11px] text-muted-foreground">{t.role}</div>
                  </div>
                </SpotlightCard>
              </ScrollReveal>
            ))}
          </div>
        </div>
      </section>

      {/* ======================================================== */}
      {/* SECTION 7: FINAL CALL TO ACTION (CTA) */}
      {/* ======================================================== */}
      <section className="py-24 px-4 sm:px-6 lg:px-8 max-w-7xl mx-auto">
        <ScrollReveal>
          <div className="relative overflow-hidden rounded-3xl border border-border/80 bg-gradient-to-b from-surface/90 to-surface/40 backdrop-blur-2xl p-10 sm:p-16 text-center shadow-2xl">
            {/* Ambient Spotlight */}
            <div className="absolute top-0 left-1/2 -translate-x-1/2 w-96 h-48 bg-foreground/10 blur-3xl pointer-events-none rounded-full" />

            <div className="relative z-10 max-w-3xl mx-auto space-y-6">
              <span className="inline-block text-[10px] font-bold uppercase tracking-widest px-2.5 py-1 rounded-full bg-foreground text-background">
                Get Started Today
              </span>
              <h2 className="text-3xl sm:text-5xl font-extrabold tracking-tight text-foreground">
                Ready to accelerate your engineering cycle?
              </h2>
              <p className="text-sm sm:text-base text-muted-foreground max-w-xl mx-auto leading-relaxed">
                Join forward-thinking software squads building with extreme precision, speed, and elegance.
              </p>

              <div className="pt-4 flex flex-col sm:flex-row items-center justify-center gap-3 sm:gap-4">
                <Link to="/signup">
                  <Button
                    variant="primary"
                    size="lg"
                    className="h-11 px-8 text-sm font-semibold shadow-xl hover:shadow-2xl transition-all"
                    icon={<ArrowRight className="w-4 h-4 order-last" />}
                  >
                    Start Free Forever
                  </Button>
                </Link>
                <Link to="/contact">
                  <Button
                    variant="outline"
                    size="lg"
                    className="h-11 px-8 text-sm font-semibold bg-surface/80 hover:bg-surface-hover"
                  >
                    Talk to Enterprise Sales
                  </Button>
                </Link>
              </div>

              <div className="pt-6 flex flex-wrap items-center justify-center gap-6 text-xs text-muted-foreground">
                <span className="flex items-center gap-1.5">
                  <CheckCircle2 className="w-3.5 h-3.5 text-emerald-400" /> Free for individuals
                </span>
                <span className="flex items-center gap-1.5">
                  <CheckCircle2 className="w-3.5 h-3.5 text-emerald-400" /> Instant cloud setup
                </span>
                <span className="flex items-center gap-1.5">
                  <CheckCircle2 className="w-3.5 h-3.5 text-emerald-400" /> 99.99% SLA guarantee
                </span>
              </div>
            </div>
          </div>
        </ScrollReveal>
      </section>
    </div>
  );
}

export default LandingPage;
