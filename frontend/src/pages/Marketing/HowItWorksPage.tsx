import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import {
  ArrowRight,
  Terminal,
  Zap,
  Workflow,
  ShieldCheck,
  Cpu,
  Database,
  Lock,
  GitBranch,
  CheckCircle2,
  Layers,
  ChevronRight,
  RefreshCw,
  GitPullRequest,
} from 'lucide-react';
import { Button } from '../../components/ui/Button';
import { SpotlightCard } from '../../components/marketing/SpotlightCard';
import { ScrollReveal } from '../../components/marketing/ScrollReveal';

export function HowItWorksPage() {
  const [activeStep, setActiveStep] = useState(1);

  const steps = [
    {
      num: 1,
      title: 'Ingest & Triage at The Speed of Thought',
      tag: 'Step 01 / Capture',
      desc: 'Capture ideas, bugs, and architectural specs without losing focus. Hit C anywhere in the app to draft an issue with markdown code blocks, assignees, and estimates. Or ingest directly via CLI, Git hooks, or webhook endpoints.',
      code: `$ sprintforge issue create --title "Zero-copy buffer allocation" --points 5 --priority URGENT
✓ Issue ENG-402 created in 12ms. Assigned to @alex`,
      features: [
        'Single-key modal summoning (Press "C")',
        'Sub-millisecond API response times',
        'Automatic Git branch name generation (e.g. eng-402-zero-copy)',
      ],
    },
    {
      num: 2,
      title: 'Cadence-Driven Cycles & Burndown',
      tag: 'Step 02 / Orchestrate',
      desc: 'Eliminate chaotic milestone drift. SprintForge automatically segments team efforts into synchronized 2-week cycles. Track scope changes in real-time, compute velocity trends, and rollover incomplete tasks with zero administrative friction.',
      code: `Cycle 24 • Oct 02 - Oct 16
Committed: 42 pts | Completed: 38 pts (90.4%)
P95 Cycle Time: 1.8 days (-0.4 days vs Cycle 23)`,
      features: [
        'Automated bi-weekly cycle rollover',
        'Real-time scope addition alerts',
        'Historical velocity and capacity curves',
      ],
    },
    {
      num: 3,
      title: 'Deterministic Visual Workflow Automation',
      tag: 'Step 03 / Automate',
      desc: 'Design reactive state machines without writing brittle custom scripts. When a PR merges or an issue transitions to "In Review", automatically reassign the reviewer, dispatch webhook payloads to Slack/Discord, and trigger CI verification.',
      code: `TRIGGER: on_status_change(status == 'IN_REVIEW')
CONDITION: pr.approvals >= 2 && ci.status == 'PASSED'
ACTION: transition(status = 'DONE') -> emit_audit_event()`,
      features: [
        'Visual Directed Acyclic Graph (DAG) canvas',
        'Conditional branching and policy gates',
        'Outbound webhook delivery with HMAC verification',
      ],
    },
    {
      num: 4,
      title: 'Continuous Governance & Cryptographic Audit',
      tag: 'Step 04 / Verify',
      desc: 'Meet enterprise SOC2 and ISO27001 requirements out of the box. Quantify risks with dynamic 5x5 severity matrices, enforce role-based access control, and rely on an immutable SHA-256 audit log that records every single workspace transaction.',
      code: `AUDIT_LOG_ENTRY #91823:
Timestamp: 2026-10-16T17:28:45Z
Actor: user_88 (Admin) | Action: PERMISSION_GRANT
Hash: 8f4b23c9a1e04... (VERIFIED)`,
      features: [
        'Integrated 5x5 Risk Severity Matrix',
        'Tamper-evident audit log ledger',
        'Role-Based Access Control (RBAC) workspace isolation',
      ],
    },
  ];

  return (
    <div className="py-20 px-4 sm:px-6 lg:px-8 max-w-7xl mx-auto">
      {/* Header */}
      <ScrollReveal className="text-center max-w-3xl mx-auto mb-20">
        <span className="text-[10px] font-bold uppercase tracking-widest px-3 py-1 rounded-full bg-foreground text-background inline-block mb-4">
          The Engineering Lifecycle
        </span>
        <h1 className="text-4xl sm:text-6xl font-extrabold tracking-tight text-foreground leading-tight">
          How SprintForge Works.
        </h1>
        <p className="mt-4 text-sm sm:text-base text-muted-foreground leading-relaxed">
          From issue drafting to automated deployment verification in sub-seconds. Explore the 4-stage engine powering elite engineering organizations.
        </p>
      </ScrollReveal>

      {/* Step Selector Pills */}
      <div className="flex flex-wrap items-center justify-center gap-2 mb-16">
        {steps.map((s) => (
          <button
            key={s.num}
            onClick={() => setActiveStep(s.num)}
            className={`px-4 py-2 rounded-lg text-xs font-semibold transition-all flex items-center gap-2 ${
              activeStep === s.num
                ? 'bg-foreground text-background shadow-md'
                : 'bg-surface/80 border border-border/80 text-muted-foreground hover:text-foreground hover:bg-surface-hover'
            }`}
          >
            <span className="w-4 h-4 rounded-full bg-surface-raised border border-border flex items-center justify-center text-[10px] font-bold">
              {s.num}
            </span>
            <span>{s.title.split(' ')[0]} {s.title.split(' ')[1]}</span>
          </button>
        ))}
      </div>

      {/* Active Step Showcase */}
      {steps
        .filter((s) => s.num === activeStep)
        .map((s) => (
          <ScrollReveal key={s.num} className="mb-24">
            <div className="grid grid-cols-1 lg:grid-cols-2 gap-8 items-center rounded-3xl border border-border/80 bg-surface/70 backdrop-blur-2xl p-8 sm:p-12 shadow-2xl">
              <div>
                <span className="text-xs font-semibold uppercase tracking-wider text-muted-foreground block mb-2">
                  {s.tag}
                </span>
                <h2 className="text-2xl sm:text-3xl font-extrabold text-foreground mb-4">
                  {s.title}
                </h2>
                <p className="text-xs sm:text-sm text-muted-foreground leading-relaxed mb-6">
                  {s.desc}
                </p>

                <div className="space-y-2.5">
                  {s.features.map((f, idx) => (
                    <div key={idx} className="flex items-center gap-2 text-xs text-foreground font-medium">
                      <CheckCircle2 className="w-4 h-4 text-emerald-400 shrink-0" />
                      <span>{f}</span>
                    </div>
                  ))}
                </div>
              </div>

              {/* Code Terminal Mockup */}
              <div className="rounded-2xl border border-border/80 bg-black/90 p-5 shadow-xl text-emerald-400 font-mono text-xs">
                <div className="flex items-center justify-between pb-3 mb-3 border-b border-white/10 text-muted-foreground text-[10px]">
                  <span className="flex items-center gap-1.5">
                    <span className="w-2 h-2 rounded-full bg-emerald-400" />
                    SprintForge Sub-Millisecond Kernel
                  </span>
                  <span>POSIX Terminal</span>
                </div>
                <pre className="whitespace-pre-wrap leading-relaxed font-sans">{s.code}</pre>
              </div>
            </div>
          </ScrollReveal>
        ))}

      {/* Technical Architecture Deep Dive */}
      <section className="mt-20 pt-16 border-t border-border/60">
        <ScrollReveal className="text-center max-w-3xl mx-auto mb-16">
          <span className="text-xs font-semibold uppercase tracking-wider text-muted-foreground block mb-2">
            Technical Rigor
          </span>
          <h2 className="text-3xl font-extrabold text-foreground">
            Architected for zero data loss and sub-15ms latency.
          </h2>
        </ScrollReveal>

        <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
          <SpotlightCard>
            <div className="h-9 w-9 rounded-lg bg-foreground text-background flex items-center justify-center font-bold mb-4">
              <Cpu className="w-4 h-4" />
            </div>
            <h3 className="text-sm font-bold text-foreground mb-1">
              Java 21 & Spring Boot Core
            </h3>
            <p className="text-xs text-muted-foreground leading-relaxed">
              Powered by Java 21 virtual threads (Project Loom) handling thousands of concurrent issue updates with sub-millisecond dispatch and minimal memory overhead.
            </p>
          </SpotlightCard>

          <SpotlightCard>
            <div className="h-9 w-9 rounded-lg bg-foreground text-background flex items-center justify-center font-bold mb-4">
              <Database className="w-4 h-4" />
            </div>
            <h3 className="text-sm font-bold text-foreground mb-1">
              PostgreSQL ACID Consistency
            </h3>
            <p className="text-xs text-muted-foreground leading-relaxed">
              Strict relational integrity with row-level workspace tenant isolation. Eliminates data corruption, phantom reads, and sync conflicts across distributed squads.
            </p>
          </SpotlightCard>

          <SpotlightCard>
            <div className="h-9 w-9 rounded-lg bg-foreground text-background flex items-center justify-center font-bold mb-4">
              <Lock className="w-4 h-4" />
            </div>
            <h3 className="text-sm font-bold text-foreground mb-1">
              Zero-Trust Cryptographic Tokens
            </h3>
            <p className="text-xs text-muted-foreground leading-relaxed">
              Stateless JWT authentication with instantaneous cryptographic verification. Every API endpoint enforces granular workspace and project permission policies.
            </p>
          </SpotlightCard>
        </div>
      </section>

      {/* Bottom CTA */}
      <div className="mt-24 text-center">
        <Link to="/signup">
          <Button variant="primary" size="lg" className="h-11 px-8 text-sm font-semibold" icon={<ArrowRight className="w-4 h-4 order-last" />}>
            Start Building with SprintForge
          </Button>
        </Link>
      </div>
    </div>
  );
}

export default HowItWorksPage;
