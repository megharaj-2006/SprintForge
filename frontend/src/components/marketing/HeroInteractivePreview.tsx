import React, { useState } from 'react';
import { motion } from 'framer-motion';
import {
  Sparkles,
  Command,
  Search,
  CheckCircle2,
  Clock,
  ArrowRight,
  ShieldCheck,
  ChevronRight,
  Circle,
  AlertCircle,
  GitCommit,
  Filter,
} from 'lucide-react';
import { SprintForgeIcon } from '../brand/SprintForgeLogo';

interface MockCard {
  id: string;
  identifier: string;
  title: string;
  priority: 'URGENT' | 'HIGH' | 'MEDIUM';
  points: number;
  assignee: string;
  status: 'TODO' | 'IN_PROGRESS' | 'IN_REVIEW' | 'DONE';
}

const INITIAL_CARDS: MockCard[] = [
  {
    id: '1',
    identifier: 'ENG-204',
    title: 'Migrate reactive state cache to sub-millisecond Redis store',
    priority: 'HIGH',
    points: 5,
    assignee: 'Alex V.',
    status: 'IN_PROGRESS',
  },
  {
    id: '2',
    identifier: 'ENG-205',
    title: 'Cryptographic SHA-256 tamper-evident audit ledger',
    priority: 'URGENT',
    points: 8,
    assignee: 'Sarah K.',
    status: 'IN_REVIEW',
  },
  {
    id: '3',
    identifier: 'ENG-208',
    title: 'Zero-latency keyboard shortcut handler for macOS & Linux',
    priority: 'MEDIUM',
    points: 3,
    assignee: 'Elena M.',
    status: 'DONE',
  },
  {
    id: '4',
    identifier: 'ENG-211',
    title: 'Implement visual DAG workflow condition branches',
    priority: 'HIGH',
    points: 5,
    assignee: 'Marcus T.',
    status: 'TODO',
  },
];

export function HeroInteractivePreview() {
  const [activeTab, setActiveTab] = useState<'board' | 'cycles' | 'copilot'>('board');
  const [cards, setCards] = useState<MockCard[]>(INITIAL_CARDS);
  const [hoveredCard, setHoveredCard] = useState<string | null>(null);

  const moveCard = (id: string) => {
    setCards((prev) =>
      prev.map((card) => {
        if (card.id !== id) return card;
        const nextStatus: Record<string, MockCard['status']> = {
          TODO: 'IN_PROGRESS',
          IN_PROGRESS: 'IN_REVIEW',
          IN_REVIEW: 'DONE',
          DONE: 'TODO',
        };
        return { ...card, status: nextStatus[card.status] };
      })
    );
  };

  return (
    <div className="relative mx-auto w-full max-w-5xl group">
      {/* Specular Ambient Glow Behind Window */}
      <div className="absolute -inset-1 rounded-3xl bg-gradient-to-r from-foreground/15 via-foreground/5 to-foreground/15 blur-2xl opacity-60 group-hover:opacity-100 transition-opacity duration-700" />

      {/* Main Glassmorphic macOS Frame */}
      <div className="relative overflow-hidden rounded-2xl border border-border/80 bg-surface/85 backdrop-blur-2xl shadow-2xl transition-all duration-500">
        {/* Top Window Titlebar */}
        <div className="flex h-11 items-center justify-between border-b border-border/80 px-4 bg-surface-hover/30 select-none">
          {/* Traffic Lights */}
          <div className="flex items-center gap-2">
            <div className="h-3 w-3 rounded-full bg-rose-500/80 border border-rose-600/50" />
            <div className="h-3 w-3 rounded-full bg-amber-500/80 border border-amber-600/50" />
            <div className="h-3 w-3 rounded-full bg-emerald-500/80 border border-emerald-600/50" />
            <span className="ml-3 text-[11px] font-medium text-muted-foreground flex items-center gap-1.5">
              <SprintForgeIcon className="w-3.5 h-3.5 text-foreground shrink-0" />
              <span>SprintForge</span>
              <ChevronRight className="w-3 h-3 text-muted-foreground/50" />
              <span className="text-foreground">Core Engine / Cycle 24</span>
            </span>
          </div>

          {/* Quick Omnisearch simulation pill */}
          <div className="hidden sm:flex items-center gap-2 rounded-md border border-border/80 bg-background/60 px-3 py-1 text-xs text-muted-foreground">
            <Search className="w-3.5 h-3.5" />
            <span>Search or jump to...</span>
            <kbd className="ml-2 rounded border border-border bg-muted/60 px-1 text-[10px] tabular-nums font-medium">
              ⌘K
            </kbd>
          </div>

          {/* Right Status */}
          <div className="flex items-center gap-2">
            <span className="flex items-center gap-1.5 text-[11px] font-medium text-emerald-400 bg-emerald-500/10 border border-emerald-500/20 px-2 py-0.5 rounded-full">
              <span className="h-1.5 w-1.5 rounded-full bg-emerald-400 animate-pulse" />
              Live Sync
            </span>
          </div>
        </div>

        {/* View Switcher Tabs */}
        <div className="flex items-center justify-between border-b border-border/60 px-4 py-2 bg-surface/50 text-xs">
          <div className="flex items-center gap-1">
            <button
              onClick={() => setActiveTab('board')}
              className={`px-3 py-1.5 rounded-md font-medium transition-all ${
                activeTab === 'board'
                  ? 'bg-foreground text-background shadow-xs'
                  : 'text-muted-foreground hover:text-foreground hover:bg-surface-hover'
              }`}
            >
              Active Kanban
            </button>
            <button
              onClick={() => setActiveTab('cycles')}
              className={`px-3 py-1.5 rounded-md font-medium transition-all ${
                activeTab === 'cycles'
                  ? 'bg-foreground text-background shadow-xs'
                  : 'text-muted-foreground hover:text-foreground hover:bg-surface-hover'
              }`}
            >
              Sprint Burndown
            </button>
            <button
              onClick={() => setActiveTab('copilot')}
              className={`px-3 py-1.5 rounded-md font-medium transition-all flex items-center gap-1.5 ${
                activeTab === 'copilot'
                  ? 'bg-foreground text-background shadow-xs'
                  : 'text-muted-foreground hover:text-foreground hover:bg-surface-hover'
              }`}
            >
              <Sparkles className="w-3.5 h-3.5" />
              Autonomous Copilot
            </button>
          </div>

          <div className="hidden md:flex items-center gap-3 text-muted-foreground text-[11px]">
            <span className="flex items-center gap-1">
              <Filter className="w-3 h-3" /> 4 Filters
            </span>
            <span>•</span>
            <span className="tabular-nums font-medium">21 story points committed</span>
          </div>
        </div>

        {/* Board Content */}
        {activeTab === 'board' && (
          <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-3 p-4 min-h-[340px] bg-background/40">
            {(['TODO', 'IN_PROGRESS', 'IN_REVIEW', 'DONE'] as const).map((status) => {
              const statusLabels = {
                TODO: 'To Do',
                IN_PROGRESS: 'In Progress',
                IN_REVIEW: 'In Review',
                DONE: 'Done',
              };
              const statusColors = {
                TODO: 'text-muted-foreground',
                IN_PROGRESS: 'text-amber-500',
                IN_REVIEW: 'text-sky-400',
                DONE: 'text-emerald-400',
              };
              const columnCards = cards.filter((c) => c.status === status);

              return (
                <div
                  key={status}
                  className="rounded-xl border border-border/60 bg-surface/40 p-3 flex flex-col justify-start"
                >
                  <div className="flex items-center justify-between mb-3 pb-2 border-b border-border/50">
                    <div className="flex items-center gap-2">
                      <Circle className={`w-2.5 h-2.5 fill-current ${statusColors[status]}`} />
                      <span className="text-xs font-semibold text-foreground">
                        {statusLabels[status]}
                      </span>
                    </div>
                    <span className="text-[10px] tabular-nums font-medium text-muted-foreground bg-muted/50 px-1.5 py-0.5 rounded">
                      {columnCards.length}
                    </span>
                  </div>

                  <div className="space-y-2 flex-1">
                    {columnCards.map((card) => (
                      <motion.div
                        key={card.id}
                        layout
                        whileHover={{ scale: 1.02, y: -2 }}
                        onClick={() => moveCard(card.id)}
                        onMouseEnter={() => setHoveredCard(card.id)}
                        onMouseLeave={() => setHoveredCard(null)}
                        className={`p-3 rounded-lg border bg-surface/90 backdrop-blur-md cursor-pointer transition-all shadow-xs ${
                          hoveredCard === card.id
                            ? 'border-foreground/50 shadow-md ring-1 ring-foreground/20'
                            : 'border-border/80 hover:border-border'
                        }`}
                      >
                        <div className="flex items-center justify-between text-[10px] mb-1.5">
                          <span className="tabular-nums font-semibold text-foreground">
                            {card.identifier}
                          </span>
                          <span
                            className={`px-1.5 py-0.2 rounded font-semibold text-[9px] uppercase ${
                              card.priority === 'URGENT'
                                ? 'bg-rose-500/15 text-rose-400 border border-rose-500/30'
                                : card.priority === 'HIGH'
                                ? 'bg-amber-500/15 text-amber-500 border border-amber-500/30'
                                : 'bg-surface-raised text-muted-foreground border border-border'
                            }`}
                          >
                            {card.priority}
                          </span>
                        </div>
                        <p className="text-xs font-medium text-foreground line-clamp-2 mb-2 leading-relaxed">
                          {card.title}
                        </p>
                        <div className="flex items-center justify-between pt-1.5 border-t border-border/50 text-[10px] text-muted-foreground">
                          <span className="flex items-center gap-1">
                            <span className="w-4 h-4 rounded-full bg-surface-raised border border-border flex items-center justify-center font-bold text-[8px] text-foreground">
                              {card.assignee.charAt(0)}
                            </span>
                            <span>{card.assignee}</span>
                          </span>
                          <span className="tabular-nums font-medium bg-muted/40 px-1 rounded">
                            {card.points} pts
                          </span>
                        </div>
                      </motion.div>
                    ))}

                    {columnCards.length === 0 && (
                      <div className="h-24 rounded-lg border border-dashed border-border/60 flex items-center justify-center text-[11px] text-muted-foreground">
                        Empty state
                      </div>
                    )}
                  </div>
                </div>
              );
            })}
          </div>
        )}

        {/* Cycles Burndown View */}
        {activeTab === 'cycles' && (
          <div className="p-6 min-h-[340px] bg-background/40 flex flex-col justify-between">
            <div>
              <div className="flex items-center justify-between mb-4">
                <div>
                  <h4 className="text-sm font-semibold text-foreground">Sprint 24 Velocity Velocity</h4>
                  <p className="text-xs text-muted-foreground">Oct 02 — Oct 16 • 88% scope completed</p>
                </div>
                <span className="text-xs tabular-nums font-semibold px-2.5 py-1 rounded-full bg-emerald-500/15 text-emerald-400 border border-emerald-500/30">
                  On Track (+3.2 pts ahead)
                </span>
              </div>

              {/* Mock Bar Chart */}
              <div className="h-44 w-full flex items-end gap-3 pt-6 pb-2 border-b border-border/70">
                {[
                  { day: 'Mon', target: 35, actual: 35 },
                  { day: 'Tue', target: 30, actual: 32 },
                  { day: 'Wed', target: 25, actual: 24 },
                  { day: 'Thu', target: 20, actual: 18 },
                  { day: 'Fri', target: 15, actual: 12 },
                  { day: 'Mon', target: 10, actual: 8 },
                  { day: 'Tue', target: 5, actual: 4 },
                  { day: 'Wed', target: 0, actual: 0 },
                ].map((item, i) => (
                  <div key={i} className="flex-1 flex flex-col items-center gap-1.5 h-full justify-end group/bar">
                    <div className="w-full max-w-[28px] flex items-end justify-center gap-1 h-full">
                      <div
                        className="w-1/2 bg-muted/60 rounded-t"
                        style={{ height: `${(item.target / 35) * 100}%` }}
                      />
                      <div
                        className="w-1/2 bg-foreground rounded-t group-hover/bar:bg-foreground/80 transition-all"
                        style={{ height: `${(item.actual / 35) * 100}%` }}
                      />
                    </div>
                    <span className="text-[10px] text-muted-foreground tabular-nums">{item.day}</span>
                  </div>
                ))}
              </div>
            </div>

            <div className="flex items-center justify-between text-xs text-muted-foreground pt-3">
              <span className="flex items-center gap-2">
                <span className="w-2.5 h-2.5 rounded-sm bg-foreground" /> Actual Burndown
                <span className="w-2.5 h-2.5 rounded-sm bg-muted/80 ml-2" /> Ideal Baseline
              </span>
              <span className="tabular-nums font-medium">Auto-synced with Git commit hooks</span>
            </div>
          </div>
        )}

        {/* Autonomous Copilot View */}
        {activeTab === 'copilot' && (
          <div className="p-6 min-h-[340px] bg-background/40 space-y-4">
            <div className="rounded-xl border border-border/80 bg-surface/80 p-4 space-y-3">
              <div className="flex items-center justify-between">
                <div className="flex items-center gap-2">
                  <div className="w-6 h-6 rounded-md bg-foreground text-background flex items-center justify-center font-bold text-xs">
                    <Sparkles className="w-3.5 h-3.5" />
                  </div>
                  <span className="text-xs font-semibold text-foreground">SprintForge AI Copilot</span>
                </div>
                <span className="text-[10px] text-muted-foreground tabular-nums">1.2ms inference</span>
              </div>
              <p className="text-xs text-foreground/90 leading-relaxed">
                "Analyzed 18 closed issues from last cycle. Detected 2 recurring bottle-necks in database migration reviews. Drafted automated GitHub actions workflow and updated team capacity model."
              </p>
              <div className="flex flex-wrap gap-2 pt-1">
                <span className="text-[11px] px-2.5 py-1 rounded-md bg-surface-raised border border-border text-foreground font-medium flex items-center gap-1 cursor-pointer hover:border-foreground/40 transition-colors">
                  <GitCommit className="w-3 h-3 text-muted-foreground" />
                  Apply Migration CI rule
                </span>
                <span className="text-[11px] px-2.5 py-1 rounded-md bg-surface-raised border border-border text-foreground font-medium flex items-center gap-1 cursor-pointer hover:border-foreground/40 transition-colors">
                  <ShieldCheck className="w-3 h-3 text-emerald-400" />
                  Record Governance Audit Event
                </span>
              </div>
            </div>

            <div className="flex items-center justify-between rounded-lg border border-border/60 bg-surface/50 px-3 py-2 text-xs text-muted-foreground">
              <span>Press <kbd className="px-1 py-0.5 rounded border border-border bg-muted/60 text-foreground font-mono text-[10px]">Cmd+J</kbd> anywhere in workspace to summon copilot drawer</span>
              <span className="text-foreground font-medium flex items-center gap-1">
                Zero telemetry leakage <ShieldCheck className="w-3.5 h-3.5 text-emerald-400" />
              </span>
            </div>
          </div>
        )}

        {/* Bottom Interactive Hint Bar */}
        <div className="flex items-center justify-between border-t border-border/80 px-4 py-2.5 bg-surface-hover/20 text-xs text-muted-foreground">
          <div className="flex items-center gap-3">
            <span className="text-[11px] flex items-center gap-1 text-foreground">
              <span className="h-1.5 w-1.5 rounded-full bg-foreground" />
              Click any card to advance its state
            </span>
            <span className="hidden sm:inline text-muted-foreground/40">•</span>
            <span className="hidden sm:inline text-[11px]">
              Optimistic updates with automatic rollback
            </span>
          </div>
          <div className="flex items-center gap-1 text-[11px] text-foreground font-medium">
            <span>Powered by Java 21 & PostgreSQL</span>
          </div>
        </div>
      </div>
    </div>
  );
}

export default HeroInteractivePreview;
