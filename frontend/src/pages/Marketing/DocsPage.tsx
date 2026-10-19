import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import {
  Search,
  BookOpen,
  Terminal,
  Command,
  Code2,
  Copy,
  Check,
  ShieldCheck,
  Cpu,
  Workflow,
  ExternalLink,
  ChevronRight,
  Layers,
} from 'lucide-react';
import { Button } from '../../components/ui/Button';
import { SprintForgeIcon } from '../../components/brand/SprintForgeLogo';

type DocSection = 'quickstart' | 'keyboard' | 'api' | 'workflows' | 'webhooks' | 'security';

export function DocsPage() {
  const [activeSection, setActiveSection] = useState<DocSection>('quickstart');
  const [copiedCode, setCopiedCode] = useState<string | null>(null);
  const [searchQuery, setSearchQuery] = useState('');

  const copyToClipboard = (text: string, id: string) => {
    navigator.clipboard.writeText(text);
    setCopiedCode(id);
    setTimeout(() => setCopiedCode(null), 2000);
  };

  const navCategories = [
    {
      title: 'Getting Started',
      items: [
        { id: 'quickstart', label: 'Platform Quickstart' },
        { id: 'keyboard', label: 'Keyboard Shortcuts Guide' },
      ],
    },
    {
      title: 'API & Integration',
      items: [
        { id: 'api', label: 'REST API & Endpoints' },
        { id: 'workflows', label: 'Workflow DAG Rules' },
        { id: 'webhooks', label: 'Webhooks & HMAC Verification' },
      ],
    },
    {
      title: 'Enterprise & Security',
      items: [
        { id: 'security', label: 'Cryptographic Audit & Governance' },
      ],
    },
  ];

  return (
    <div className="mx-auto max-w-7xl px-4 py-12 sm:px-6 lg:px-8">
      {/* Docs Header */}
      <div className="mb-10 pb-6 border-b border-border/80 flex flex-col md:flex-row md:items-center justify-between gap-4">
        <div>
          <div className="flex items-center gap-2 text-xs text-muted-foreground mb-1">
            <Link to="/" className="hover:text-foreground flex items-center gap-1.5 font-medium">
              <SprintForgeIcon className="w-3.5 h-3.5 text-foreground" />
              <span>SprintForge</span>
            </Link>
            <ChevronRight className="w-3 h-3 text-muted-foreground/40" />
            <span className="text-foreground font-semibold">Documentation</span>
          </div>
          <h1 className="text-2xl sm:text-3xl font-extrabold text-foreground tracking-tight">
            Developer Documentation & API
          </h1>
        </div>

        {/* Search bar */}
        <div className="relative w-full md:w-72">
          <Search className="w-3.5 h-3.5 absolute left-3 top-1/2 -translate-y-1/2 text-muted-foreground" />
          <input
            type="text"
            value={searchQuery}
            onChange={(e) => setSearchQuery(e.target.value)}
            placeholder="Search guides, endpoints, keys..."
            className="w-full h-8 pl-8 pr-3 rounded-lg border border-border bg-surface text-xs placeholder:text-muted-foreground/50 focus:outline-none focus:ring-1 focus:ring-foreground"
          />
        </div>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-4 gap-8">
        {/* Left Sticky Sidebar */}
        <aside className="md:col-span-1 space-y-6">
          {navCategories.map((cat) => (
            <div key={cat.title} className="space-y-1.5">
              <h3 className="text-[11px] font-bold uppercase tracking-wider text-muted-foreground px-2">
                {cat.title}
              </h3>
              <div className="space-y-0.5">
                {cat.items.map((item) => (
                  <button
                    key={item.id}
                    onClick={() => setActiveSection(item.id as DocSection)}
                    className={`w-full text-left px-2.5 py-1.5 rounded-md text-xs font-medium transition-colors ${
                      activeSection === item.id
                        ? 'bg-foreground text-background font-semibold shadow-xs'
                        : 'text-muted-foreground hover:text-foreground hover:bg-surface-hover'
                    }`}
                  >
                    {item.label}
                  </button>
                ))}
              </div>
            </div>
          ))}

          {/* Quick Stats Pill */}
          <div className="rounded-xl border border-border/80 bg-surface/60 p-3 text-xs space-y-1.5">
            <span className="font-semibold text-foreground block">Platform Status</span>
            <div className="flex items-center justify-between text-[11px] text-muted-foreground">
              <span>Java 21 Spring Boot</span>
              <span className="text-emerald-400 font-semibold">v4.1.0</span>
            </div>
            <div className="flex items-center justify-between text-[11px] text-muted-foreground">
              <span>Documented Endpoints</span>
              <span className="tabular-nums font-semibold text-foreground">642</span>
            </div>
          </div>
        </aside>

        {/* Main Content Pane */}
        <main className="md:col-span-3 space-y-10 min-w-0">
          {/* Quickstart Section */}
          {activeSection === 'quickstart' && (
            <section className="space-y-6">
              <div>
                <h2 className="text-2xl font-bold text-foreground tracking-tight mb-2">
                  Platform Quickstart
                </h2>
                <p className="text-xs sm:text-sm text-muted-foreground leading-relaxed">
                  Get up and running with your first SprintForge workspace in less than two minutes.
                </p>
              </div>

              <div className="space-y-4">
                <h3 className="text-sm font-bold text-foreground">1. Creating an Account and Workspace</h3>
                <p className="text-xs text-muted-foreground leading-relaxed">
                  Navigate to <Link to="/signup" className="text-foreground underline">/signup</Link> to create your root user. Once registered, you will be prompted to create your primary organization workspace and invite team members.
                </p>

                <h3 className="text-sm font-bold text-foreground">2. Generating Personal API Access Tokens</h3>
                <p className="text-xs text-muted-foreground leading-relaxed">
                  To interact with the platform programmatically or via CI pipelines, generate a JWT token via the authentication endpoint:
                </p>

                {/* Code snippet */}
                <div className="rounded-xl border border-border/80 bg-black/90 p-4 relative text-xs">
                  <button
                    onClick={() =>
                      copyToClipboard(
                        `curl -X POST http://localhost:8080/api/v1/auth/login \\\n  -H "Content-Type: application/json" \\\n  -d '{"username": "developer", "password": "secure_password"}'`,
                        'code-quickstart'
                      )
                    }
                    className="absolute top-3 right-3 p-1.5 rounded-md bg-white/10 hover:bg-white/20 text-white"
                  >
                    {copiedCode === 'code-quickstart' ? (
                      <Check className="w-3.5 h-3.5 text-emerald-400" />
                    ) : (
                      <Copy className="w-3.5 h-3.5" />
                    )}
                  </button>
                  <pre className="text-emerald-400 font-mono text-[11px] overflow-x-auto leading-relaxed">
{`curl -X POST http://localhost:8080/api/v1/auth/login \\
  -H "Content-Type: application/json" \\
  -d '{"username": "developer", "password": "secure_password"}'`}
                  </pre>
                </div>
              </div>
            </section>
          )}

          {/* Keyboard Shortcuts Section */}
          {activeSection === 'keyboard' && (
            <section className="space-y-6">
              <div>
                <h2 className="text-2xl font-bold text-foreground tracking-tight mb-2">
                  Keyboard Shortcuts Cheatsheet
                </h2>
                <p className="text-xs sm:text-sm text-muted-foreground leading-relaxed">
                  SprintForge is engineered for home-row navigation. Keep your hands on the keyboard with these zero-latency keybindings.
                </p>
              </div>

              <div className="rounded-2xl border border-border/80 bg-surface/70 backdrop-blur-xl overflow-hidden shadow-lg">
                <table className="w-full text-left text-xs border-collapse">
                  <thead>
                    <tr className="border-b border-border bg-surface-hover/60 text-muted-foreground font-semibold">
                      <th className="px-5 py-3">Shortcut</th>
                      <th className="px-5 py-3">Action</th>
                      <th className="px-5 py-3">Scope</th>
                    </tr>
                  </thead>
                  <tbody className="divide-y divide-border/60">
                    {[
                      { key: 'Cmd + K', desc: 'Open Omnisearch Command Palette', scope: 'Global' },
                      { key: 'Cmd + J', desc: 'Summon Autonomous AI Copilot Drawer', scope: 'Global' },
                      { key: 'C', desc: 'Open Rapid Task Creation Modal', scope: 'Global' },
                      { key: 'G then B', desc: 'Jump to Active Project Kanban Board', scope: 'Global' },
                      { key: 'G then T', desc: 'Jump to My Tasks Page', scope: 'Global' },
                      { key: '↑ / ↓', desc: 'Select previous / next issue card', scope: 'Board & Table' },
                      { key: 'Space', desc: 'Open selected issue detail drawer', scope: 'Board & Table' },
                      { key: 'Esc', desc: 'Close any active modal or drawer', scope: 'Global' },
                    ].map((s, idx) => (
                      <tr key={idx} className="hover:bg-surface-hover/30 transition-colors">
                        <td className="px-5 py-3 font-semibold text-foreground">
                          <kbd className="px-2 py-1 rounded border border-border bg-muted/60 text-[11px] font-medium">
                            {s.key}
                          </kbd>
                        </td>
                        <td className="px-5 py-3 text-foreground">{s.desc}</td>
                        <td className="px-5 py-3 text-muted-foreground text-[11px]">{s.scope}</td>
                      </tr>
                    ))}
                  </tbody>
                </table>
              </div>
            </section>
          )}

          {/* REST API Section */}
          {activeSection === 'api' && (
            <section className="space-y-6">
              <div>
                <h2 className="text-2xl font-bold text-foreground tracking-tight mb-2">
                  REST API Architecture
                </h2>
                <p className="text-xs sm:text-sm text-muted-foreground leading-relaxed">
                  The SprintForge backend exposes 642 robust REST endpoints across 110 controllers built with Java 21 and Spring Boot.
                </p>
              </div>

              <div className="space-y-3">
                <h3 className="text-sm font-bold text-foreground">Core Endpoints Summary</h3>
                <div className="space-y-2 text-xs">
                  <div className="p-3 rounded-lg border border-border/80 bg-surface flex items-center justify-between font-mono text-[11px]">
                    <div className="flex items-center gap-2">
                      <span className="px-1.5 py-0.5 rounded bg-emerald-500/15 text-emerald-400 font-bold">GET</span>
                      <span className="text-foreground">/api/v1/projects/{'{id}'}/tasks</span>
                    </div>
                    <span className="text-muted-foreground">List active tasks with cursor pagination</span>
                  </div>

                  <div className="p-3 rounded-lg border border-border/80 bg-surface flex items-center justify-between font-mono text-[11px]">
                    <div className="flex items-center gap-2">
                      <span className="px-1.5 py-0.5 rounded bg-sky-500/15 text-sky-400 font-bold">POST</span>
                      <span className="text-foreground">/api/v1/tasks</span>
                    </div>
                    <span className="text-muted-foreground">Create task with optimistic ID reservation</span>
                  </div>

                  <div className="p-3 rounded-lg border border-border/80 bg-surface flex items-center justify-between font-mono text-[11px]">
                    <div className="flex items-center gap-2">
                      <span className="px-1.5 py-0.5 rounded bg-amber-500/15 text-amber-500 font-bold">PUT</span>
                      <span className="text-foreground">/api/v1/tasks/{'{id}'}/status</span>
                    </div>
                    <span className="text-muted-foreground">Advance task Kanban column status</span>
                  </div>

                  <div className="p-3 rounded-lg border border-border/80 bg-surface flex items-center justify-between font-mono text-[11px]">
                    <div className="flex items-center gap-2">
                      <span className="px-1.5 py-0.5 rounded bg-purple-500/15 text-foreground font-bold">GET</span>
                      <span className="text-foreground">/api/v1/workspaces/{'{id}'}/audit-logs</span>
                    </div>
                    <span className="text-muted-foreground">Stream tamper-evident audit ledger entries</span>
                  </div>
                </div>
              </div>
            </section>
          )}

          {/* Workflows Section */}
          {activeSection === 'workflows' && (
            <section className="space-y-6">
              <div>
                <h2 className="text-2xl font-bold text-foreground tracking-tight mb-2">
                  Visual Workflow DAG Engine
                </h2>
                <p className="text-xs sm:text-sm text-muted-foreground leading-relaxed">
                  Construct deterministic Directed Acyclic Graph (DAG) state machines to automate complex issue handoffs and compliance sign-offs.
                </p>
              </div>

              <div className="p-4 rounded-xl border border-border/80 bg-surface/70 space-y-3 text-xs">
                <span className="font-bold text-foreground block">Block Types:</span>
                <ul className="space-y-2 text-muted-foreground">
                  <li><strong className="text-foreground">Trigger:</strong> Listens to events like `ISSUE_CREATED`, `STATUS_CHANGED`, or `PR_MERGED`.</li>
                  <li><strong className="text-foreground">Condition:</strong> Evaluates boolean rules on priority, story points, or assignee tags.</li>
                  <li><strong className="text-foreground">Action:</strong> Transitions issue state, assigns team members round-robin, or delivers webhook notifications.</li>
                </ul>
              </div>
            </section>
          )}

          {/* Webhooks Section */}
          {activeSection === 'webhooks' && (
            <section className="space-y-6">
              <div>
                <h2 className="text-2xl font-bold text-foreground tracking-tight mb-2">
                  Webhooks & HMAC Verification
                </h2>
                <p className="text-xs sm:text-sm text-muted-foreground leading-relaxed">
                  SprintForge dispatches real-time HTTPS webhook notifications for all issue, sprint, and membership events. Every request includes an `X-SprintForge-Signature` header computed via HMAC-SHA256.
                </p>
              </div>

              <div className="rounded-xl border border-border/80 bg-black/90 p-4 text-xs">
                <pre className="text-emerald-400 font-mono text-[11px] leading-relaxed">
{`// Verifying webhook signature in Node.js / Express
const crypto = require('crypto');

function verifyWebhook(payload, signature, secret) {
  const hmac = crypto.createHmac('sha256', secret);
  const digest = 'sha256=' + hmac.update(payload).digest('hex');
  return crypto.timingSafeEqual(Buffer.from(digest), Buffer.from(signature));
}`}
                </pre>
              </div>
            </section>
          )}

          {/* Security Section */}
          {activeSection === 'security' && (
            <section className="space-y-6">
              <div>
                <h2 className="text-2xl font-bold text-foreground tracking-tight mb-2">
                  Cryptographic Audit & Enterprise Governance
                </h2>
                <p className="text-xs sm:text-sm text-muted-foreground leading-relaxed">
                  Every state transition is signed and stored in a tamper-evident audit ledger to guarantee compliance with ISO27001, SOC2, and HIPAA regulations.
                </p>
              </div>

              <div className="p-4 rounded-xl border border-border/80 bg-surface/70 text-xs space-y-2">
                <span className="font-bold text-foreground block">Compliance Highlights:</span>
                <ul className="list-disc list-inside space-y-1.5 text-muted-foreground">
                  <li>Row-level PostgreSQL workspace isolation.</li>
                  <li>Zero plain-text password storage (Argon2id hashing).</li>
                  <li>Granular Role-Based Access Control (RBAC): Owner, Admin, Member, Guest.</li>
                  <li>Exportable JSON audit bundles for external compliance auditors.</li>
                </ul>
              </div>
            </section>
          )}
        </main>
      </div>
    </div>
  );
}

export default DocsPage;
