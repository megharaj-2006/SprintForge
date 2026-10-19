import React, { useState } from 'react';
import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import { useWorkspaceStore } from '../../store/useWorkspaceStore';
import { governanceApi } from '../../api/endpoints';
import { Button } from '../../components/ui/Button';
import { Modal } from '../../components/ui/Modal';
import type { Risk, DecisionRecord, ChangeRequest, GovernanceDocument } from '../../types';
import {
  Shield,
  AlertTriangle,
  FileCheck,
  GitPullRequest,
  FileText,
  Plus,
  RefreshCw,
  CheckCircle2,
  Clock,
  Layers,
  Info,
} from 'lucide-react';

type GovernanceTab = 'risks' | 'decisions' | 'changes' | 'documents';

export function GovernancePage() {
  const queryClient = useQueryClient();
  const { currentProject, projects } = useWorkspaceStore();
  const [activeTab, setActiveTab] = useState<GovernanceTab>('risks');

  const projectId = currentProject?.id || (projects[0]?.id as number);

  // Risk Query
  const {
    data: risks = [],
    isLoading: isRisksLoading,
    refetch: refetchRisks,
  } = useQuery<Risk[]>({
    queryKey: ['risks', projectId],
    queryFn: async () => {
      if (!projectId) return [];
      const res = await governanceApi.getRisks(projectId);
      return Array.isArray(res) ? res : [];
    },
    enabled: Boolean(projectId),
  });

  // Decision Records Query
  const {
    data: decisions = [],
    isLoading: isDecisionsLoading,
    refetch: refetchDecisions,
  } = useQuery<DecisionRecord[]>({
    queryKey: ['decisions', projectId],
    queryFn: async () => {
      if (!projectId) return [];
      const res = await governanceApi.getDecisions(projectId);
      return Array.isArray(res) ? res : [];
    },
    enabled: Boolean(projectId),
  });

  // Change Requests Query
  const {
    data: changes = [],
    isLoading: isChangesLoading,
    refetch: refetchChanges,
  } = useQuery<ChangeRequest[]>({
    queryKey: ['changes', projectId],
    queryFn: async () => {
      if (!projectId) return [];
      const res = await governanceApi.getChanges(projectId);
      return Array.isArray(res) ? res : [];
    },
    enabled: Boolean(projectId),
  });

  // Governance Documents Query
  const {
    data: documents = [],
    isLoading: isDocsLoading,
  } = useQuery<GovernanceDocument[]>({
    queryKey: ['documents', projectId],
    queryFn: async () => {
      if (!projectId) return [];
      const res = await governanceApi.getDocuments(projectId);
      return Array.isArray(res) ? res : [];
    },
    enabled: Boolean(projectId),
  });

  // Modal States
  const [riskModalOpen, setRiskModalOpen] = useState(false);
  const [newRiskTitle, setNewRiskTitle] = useState('');
  const [newRiskDesc, setNewRiskDesc] = useState('');
  const [newRiskSeverity, setNewRiskSeverity] = useState<'LOW' | 'MEDIUM' | 'HIGH' | 'CRITICAL'>('MEDIUM');
  const [newRiskProbability, setNewRiskProbability] = useState<'RARE' | 'UNLIKELY' | 'POSSIBLE' | 'LIKELY' | 'ALMOST_CERTAIN'>('POSSIBLE');
  const [newRiskMitigation, setNewRiskMitigation] = useState('');

  const [decisionModalOpen, setDecisionModalOpen] = useState(false);
  const [newDecisionTitle, setNewDecisionTitle] = useState('');
  const [newDecisionContext, setNewDecisionContext] = useState('');
  const [newDecisionJustification, setNewDecisionJustification] = useState('');
  const [newDecisionConsequences, setNewDecisionConsequences] = useState('');

  // Mutations
  const createRiskMutation = useMutation({
    mutationFn: async () => {
      return governanceApi.createRisk(projectId, {
        title: newRiskTitle,
        description: newRiskDesc,
        severity: newRiskSeverity,
        probability: newRiskProbability,
        mitigationPlan: newRiskMitigation,
        status: 'IDENTIFIED',
        impactScore: newRiskSeverity === 'CRITICAL' ? 9 : newRiskSeverity === 'HIGH' ? 6 : 3,
      });
    },
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['risks', projectId] });
      setRiskModalOpen(false);
      setNewRiskTitle('');
      setNewRiskDesc('');
      setNewRiskMitigation('');
    },
  });

  const createDecisionMutation = useMutation({
    mutationFn: async () => {
      return governanceApi.createDecision(projectId, {
        title: newDecisionTitle,
        context: newDecisionContext,
        decision: newDecisionJustification,
        consequences: newDecisionConsequences,
        status: 'ACCEPTED',
      });
    },
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['decisions', projectId] });
      setDecisionModalOpen(false);
      setNewDecisionTitle('');
      setNewDecisionContext('');
      setNewDecisionJustification('');
      setNewDecisionConsequences('');
    },
  });

  // Probability numeric mapping: 1 (RARE) to 5 (ALMOST_CERTAIN)
  const probToNum = (p: string) => {
    switch (p) {
      case 'ALMOST_CERTAIN': return 5;
      case 'LIKELY': return 4;
      case 'POSSIBLE': return 3;
      case 'UNLIKELY': return 2;
      default: return 1;
    }
  };

  // Severity numeric mapping: 1 (LOW) to 4 (CRITICAL)
  const sevToNum = (s: string) => {
    switch (s) {
      case 'CRITICAL': return 4;
      case 'HIGH': return 3;
      case 'MEDIUM': return 2;
      default: return 1;
    }
  };

  const probLabels = ['Almost Certain', 'Likely', 'Possible', 'Unlikely', 'Rare'];
  const sevLabels = ['Low', 'Medium', 'High', 'Critical'];

  return (
    <div className="flex flex-col h-full overflow-y-auto select-none p-6 max-w-7xl mx-auto">
      {/* Header */}
      <div className="flex flex-wrap items-center justify-between gap-4 pb-5 border-b border-border/80">
        <div>
          <h1 className="text-xl font-semibold tracking-tight text-foreground flex items-center gap-2.5">
            <Shield className="w-5 h-5 text-foreground" />
            Project Governance & Risk Register
          </h1>
          <p className="text-xs text-muted-foreground mt-1">
            Enterprise compliance, architectural decision log (ADR), and 5×5 risk matrix for{' '}
            <span className="font-semibold text-foreground">{currentProject?.name || 'SprintForge'}</span>
          </p>
        </div>

        <div className="flex items-center gap-2">
          {activeTab === 'risks' && (
            <Button
              onClick={() => setRiskModalOpen(true)}
              size="sm"
              className="text-xs h-8 font-medium"
            >
              <Plus className="w-3.5 h-3.5 mr-1" />
              Log Risk
            </Button>
          )}
          {activeTab === 'decisions' && (
            <Button
              onClick={() => setDecisionModalOpen(true)}
              size="sm"
              className="text-xs h-8 font-medium"
            >
              <Plus className="w-3.5 h-3.5 mr-1" />
              New Decision
            </Button>
          )}
        </div>
      </div>

      {/* Tabs */}
      <div className="flex items-center gap-2 border-b border-border/60 py-3 mb-5">
        <button
          onClick={() => setActiveTab('risks')}
          className={`flex items-center gap-1.5 px-3 py-1.5 rounded-md text-xs font-medium transition-all ${
            activeTab === 'risks'
              ? 'bg-surface-hover text-foreground shadow-xs'
              : 'text-muted-foreground hover:text-foreground'
          }`}
        >
          <AlertTriangle className="w-3.5 h-3.5" />
          <span>5×5 Risk Matrix ({risks.length})</span>
        </button>

        <button
          onClick={() => setActiveTab('decisions')}
          className={`flex items-center gap-1.5 px-3 py-1.5 rounded-md text-xs font-medium transition-all ${
            activeTab === 'decisions'
              ? 'bg-surface-hover text-foreground shadow-xs'
              : 'text-muted-foreground hover:text-foreground'
          }`}
        >
          <FileCheck className="w-3.5 h-3.5" />
          <span>Decision Log (ADR) ({decisions.length})</span>
        </button>

        <button
          onClick={() => setActiveTab('changes')}
          className={`flex items-center gap-1.5 px-3 py-1.5 rounded-md text-xs font-medium transition-all ${
            activeTab === 'changes'
              ? 'bg-surface-hover text-foreground shadow-xs'
              : 'text-muted-foreground hover:text-foreground'
          }`}
        >
          <GitPullRequest className="w-3.5 h-3.5" />
          <span>Change Requests ({changes.length})</span>
        </button>

        <button
          onClick={() => setActiveTab('documents')}
          className={`flex items-center gap-1.5 px-3 py-1.5 rounded-md text-xs font-medium transition-all ${
            activeTab === 'documents'
              ? 'bg-surface-hover text-foreground shadow-xs'
              : 'text-muted-foreground hover:text-foreground'
          }`}
        >
          <FileText className="w-3.5 h-3.5" />
          <span>Compliance Docs ({documents.length})</span>
        </button>
      </div>

      {/* TAB 1: 5x5 Risk Matrix & Risk Register */}
      {activeTab === 'risks' && (
        <div className="space-y-6">
          {/* Visual 5x5 Heatmap Matrix */}
          <div className="border border-border/80 rounded-xl bg-surface p-5">
            <div className="flex items-center justify-between mb-4">
              <div>
                <h3 className="text-sm font-semibold text-foreground">5×5 Probability vs Severity Heatmap</h3>
                <p className="text-xs text-muted-foreground">Interactive risk classification grid</p>
              </div>
              <div className="flex items-center gap-3 text-[11px] text-muted-foreground">
                <span className="flex items-center gap-1.5">
                  <span className="w-2.5 h-2.5 rounded-sm bg-emerald-500/30 border border-emerald-500/60" /> Low
                </span>
                <span className="flex items-center gap-1.5">
                  <span className="w-2.5 h-2.5 rounded-sm bg-amber-500/30 border border-amber-500/60" /> Moderate
                </span>
                <span className="flex items-center gap-1.5">
                  <span className="w-2.5 h-2.5 rounded-sm bg-rose-500/30 border border-rose-500/60" /> Critical
                </span>
              </div>
            </div>

            <div className="overflow-x-auto">
              <div className="min-w-[600px] border border-border/70 rounded-lg overflow-hidden">
                <div className="grid grid-cols-5 text-center text-[11px] font-semibold bg-surface-hover border-b border-border py-2 text-muted-foreground">
                  <div className="text-left pl-3">Likelihood \ Severity</div>
                  <div>Low (1)</div>
                  <div>Medium (2)</div>
                  <div>High (3)</div>
                  <div>Critical (4)</div>
                </div>

                {/* 5 rows: 5 (Almost Certain) down to 1 (Rare) */}
                {[5, 4, 3, 2, 1].map((probLevel) => (
                  <div key={probLevel} className="grid grid-cols-5 border-b border-border/50 last:border-b-0">
                    <div className="p-2.5 text-xs font-medium text-muted-foreground bg-surface-hover/30 border-r border-border/60 flex items-center">
                      {probLabels[5 - probLevel]} ({probLevel})
                    </div>
                    {[1, 2, 3, 4].map((sevLevel) => {
                      const score = probLevel * sevLevel;
                      const cellRisks = risks.filter(
                        (r) => probToNum(r.probability) === probLevel && sevToNum(r.severity) === sevLevel
                      );

                      let bgClass = 'bg-emerald-500/10 hover:bg-emerald-500/20 text-emerald-400';
                      if (score >= 12) {
                        bgClass = 'bg-rose-500/15 hover:bg-rose-500/25 text-rose-400';
                      } else if (score >= 6) {
                        bgClass = 'bg-amber-500/15 hover:bg-amber-500/25 text-amber-400';
                      }

                      return (
                        <div
                          key={sevLevel}
                          className={`p-2 min-h-[50px] border-r border-border/50 last:border-r-0 flex flex-col items-center justify-center transition-colors cursor-default ${bgClass}`}
                        >
                          <span className="tabular-nums text-xs font-bold">{score}</span>
                          {cellRisks.length > 0 && (
                            <span className="text-[10px] px-1.5 py-0.2 rounded-full bg-foreground/10 text-foreground font-semibold mt-1">
                              {cellRisks.length} {cellRisks.length === 1 ? 'risk' : 'risks'}
                            </span>
                          )}
                        </div>
                      );
                    })}
                  </div>
                ))}
              </div>
            </div>
          </div>

          {/* Risk Register Table */}
          <div className="border border-border/80 rounded-xl bg-surface overflow-hidden">
            <div className="px-5 py-3 border-b border-border/80 flex items-center justify-between">
              <h3 className="text-xs font-semibold uppercase tracking-wider text-muted-foreground">
                Active Risk Register ({risks.length})
              </h3>
            </div>

            {isRisksLoading ? (
              <div className="py-12 text-center text-xs text-muted-foreground">Loading risks...</div>
            ) : risks.length === 0 ? (
              <div className="p-8 text-center text-xs text-muted-foreground">
                Zero identified risks recorded for this project.
              </div>
            ) : (
              <div className="overflow-x-auto">
                <table className="w-full text-left text-xs border-collapse">
                  <thead>
                    <tr className="border-b border-border bg-surface-hover/50 text-muted-foreground font-medium">
                      <th className="px-4 py-2.5">Risk Description</th>
                      <th className="px-3 py-2.5">Likelihood</th>
                      <th className="px-3 py-2.5">Severity</th>
                      <th className="px-3 py-2.5">Status</th>
                      <th className="px-4 py-2.5">Mitigation Plan</th>
                    </tr>
                  </thead>
                  <tbody className="divide-y divide-border/60">
                    {risks.map((r) => (
                      <tr key={r.id} className="hover:bg-surface-hover/40 transition-colors">
                        <td className="px-4 py-3">
                          <div className="font-semibold text-foreground">{r.title}</div>
                          {r.description && (
                            <div className="text-[11px] text-muted-foreground mt-0.5 line-clamp-1">
                              {r.description}
                            </div>
                          )}
                        </td>
                        <td className="px-3 py-3 tabular-nums text-[11px] text-muted-foreground">
                          {r.probability}
                        </td>
                        <td className="px-3 py-3">
                          <span
                            className={`px-2 py-0.5 rounded text-[10px] font-semibold uppercase ${
                              r.severity === 'CRITICAL'
                                ? 'bg-rose-500/15 text-rose-400 border border-rose-500/30'
                                : r.severity === 'HIGH'
                                ? 'bg-amber-500/15 text-amber-400 border border-amber-500/30'
                                : 'bg-emerald-500/15 text-emerald-400 border border-emerald-500/30'
                            }`}
                          >
                            {r.severity}
                          </span>
                        </td>
                        <td className="px-3 py-3 font-medium text-muted-foreground">
                          {r.status}
                        </td>
                        <td className="px-4 py-3 text-muted-foreground text-[11px] max-w-xs truncate">
                          {r.mitigationPlan || 'None documented'}
                        </td>
                      </tr>
                    ))}
                  </tbody>
                </table>
              </div>
            )}
          </div>
        </div>
      )}

      {/* TAB 2: Architecture Decision Log (ADR) */}
      {activeTab === 'decisions' && (
        <div className="space-y-4">
          {isDecisionsLoading ? (
            <div className="py-12 text-center text-xs text-muted-foreground">Loading decisions...</div>
          ) : decisions.length === 0 ? (
            <div className="border border-dashed border-border rounded-xl p-12 text-center my-4 bg-surface/30">
              <FileCheck className="w-10 h-10 text-muted-foreground/40 mx-auto mb-3" />
              <h3 className="text-sm font-semibold text-foreground">No Architecture Decisions Logged</h3>
              <p className="text-xs text-muted-foreground mt-1 mb-4">
                Record major technical choices, architectural designs, and trade-offs.
              </p>
              <Button onClick={() => setDecisionModalOpen(true)} size="sm" className="text-xs h-8">
                <Plus className="w-3.5 h-3.5 mr-1" />
                Record First Decision
              </Button>
            </div>
          ) : (
            <div className="space-y-3">
              {decisions.map((d) => (
                <div
                  key={d.id}
                  className="rounded-xl border border-border/80 bg-surface p-5 hover:border-primary/50 transition-colors"
                >
                  <div className="flex items-start justify-between gap-3 mb-2">
                    <h3 className="text-sm font-semibold text-foreground">{d.title}</h3>
                    <span className="px-2 py-0.5 rounded text-[10px] font-semibold uppercase tracking-wider bg-emerald-500/15 text-emerald-400 border border-emerald-500/30">
                      {d.status}
                    </span>
                  </div>

                  <div className="grid grid-cols-1 md:grid-cols-3 gap-4 text-xs mt-3 pt-3 border-t border-border/60">
                    <div>
                      <span className="font-semibold text-muted-foreground block mb-1">Context</span>
                      <p className="text-muted-foreground/90 leading-relaxed">{d.context}</p>
                    </div>
                    <div>
                      <span className="font-semibold text-muted-foreground block mb-1">Decision</span>
                      <p className="text-foreground leading-relaxed">{d.decision}</p>
                    </div>
                    <div>
                      <span className="font-semibold text-muted-foreground block mb-1">Consequences</span>
                      <p className="text-muted-foreground/90 leading-relaxed">{d.consequences || 'None noted'}</p>
                    </div>
                  </div>
                </div>
              ))}
            </div>
          )}
        </div>
      )}

      {/* TAB 3: Change Request Pipeline */}
      {activeTab === 'changes' && (
        <div className="space-y-4">
          {isChangesLoading ? (
            <div className="py-12 text-center text-xs text-muted-foreground">Loading change requests...</div>
          ) : changes.length === 0 ? (
            <div className="border border-dashed border-border rounded-xl p-12 text-center my-4 bg-surface/30">
              <GitPullRequest className="w-10 h-10 text-muted-foreground/40 mx-auto mb-3" />
              <h3 className="text-sm font-semibold text-foreground">Zero Active Change Requests</h3>
              <p className="text-xs text-muted-foreground mt-1">
                Formal production deployment change requests will appear here.
              </p>
            </div>
          ) : (
            <div className="space-y-3">
              {changes.map((c) => (
                <div key={c.id} className="rounded-xl border border-border bg-surface p-4 text-xs">
                  <div className="flex items-center justify-between">
                    <span className="font-semibold text-foreground">{c.title}</span>
                    <span className="px-2 py-0.5 rounded text-[10px] font-semibold bg-surface-hover text-muted-foreground">
                      {c.status}
                    </span>
                  </div>
                </div>
              ))}
            </div>
          )}
        </div>
      )}

      {/* TAB 4: Document Repository */}
      {activeTab === 'documents' && (
        <div className="space-y-4">
          {isDocsLoading ? (
            <div className="py-12 text-center text-xs text-muted-foreground">Loading documents...</div>
          ) : documents.length === 0 ? (
            <div className="border border-dashed border-border rounded-xl p-12 text-center my-4 bg-surface/30">
              <FileText className="w-10 h-10 text-muted-foreground/40 mx-auto mb-3" />
              <h3 className="text-sm font-semibold text-foreground">No Governance Documents Uploaded</h3>
              <p className="text-xs text-muted-foreground mt-1">
                Security whitepapers, ISO/SOC2 audits, and compliance specs.
              </p>
            </div>
          ) : (
            <div className="grid grid-cols-1 md:grid-cols-2 gap-3">
              {documents.map((doc) => (
                <div key={doc.id} className="rounded-xl border border-border bg-surface p-4 flex items-center justify-between text-xs">
                  <div className="flex items-center gap-3">
                    <FileText className="w-5 h-5 text-primary" />
                    <div>
                      <div className="font-semibold text-foreground">{doc.title}</div>
                      <div className="text-[11px] text-muted-foreground">{doc.category} • v{doc.version}</div>
                    </div>
                  </div>
                </div>
              ))}
            </div>
          )}
        </div>
      )}

      {/* Log Risk Modal */}
      <Modal isOpen={riskModalOpen} onClose={() => setRiskModalOpen(false)} title="Log New Project Risk">
        <div className="space-y-3 pt-2">
          <div>
            <label className="block text-xs font-medium text-muted-foreground mb-1">Risk Title *</label>
            <input
              type="text"
              value={newRiskTitle}
              onChange={(e) => setNewRiskTitle(e.target.value)}
              placeholder="e.g. Third-party authentication API rate-limiting failure"
              className="w-full h-8 px-3 rounded-md border border-border bg-background text-xs focus:outline-none focus:ring-1 focus:ring-primary"
            />
          </div>

          <div className="grid grid-cols-2 gap-3">
            <div>
              <label className="block text-xs font-medium text-muted-foreground mb-1">Probability</label>
              <select
                value={newRiskProbability}
                onChange={(e) => setNewRiskProbability(e.target.value as any)}
                className="w-full h-8 px-2 rounded-md border border-border bg-background text-xs focus:outline-none focus:ring-1 focus:ring-primary"
              >
                <option value="RARE">Rare (1)</option>
                <option value="UNLIKELY">Unlikely (2)</option>
                <option value="POSSIBLE">Possible (3)</option>
                <option value="LIKELY">Likely (4)</option>
                <option value="ALMOST_CERTAIN">Almost Certain (5)</option>
              </select>
            </div>

            <div>
              <label className="block text-xs font-medium text-muted-foreground mb-1">Severity</label>
              <select
                value={newRiskSeverity}
                onChange={(e) => setNewRiskSeverity(e.target.value as any)}
                className="w-full h-8 px-2 rounded-md border border-border bg-background text-xs focus:outline-none focus:ring-1 focus:ring-primary"
              >
                <option value="LOW">Low (1)</option>
                <option value="MEDIUM">Medium (2)</option>
                <option value="HIGH">High (3)</option>
                <option value="CRITICAL">Critical (4)</option>
              </select>
            </div>
          </div>

          <div>
            <label className="block text-xs font-medium text-muted-foreground mb-1">Mitigation Plan</label>
            <textarea
              value={newRiskMitigation}
              onChange={(e) => setNewRiskMitigation(e.target.value)}
              placeholder="Engineering controls, fallback circuit breaker, backup credentials..."
              rows={3}
              className="w-full p-2.5 rounded-md border border-border bg-background text-xs focus:outline-none focus:ring-1 focus:ring-primary resize-none"
            />
          </div>

          <div className="flex justify-end gap-2 pt-3 border-t border-border">
            <Button variant="outline" size="sm" onClick={() => setRiskModalOpen(false)}>
              Cancel
            </Button>
            <Button
              size="sm"
              disabled={!newRiskTitle.trim() || createRiskMutation.isPending}
              onClick={() => createRiskMutation.mutate()}
            >
              {createRiskMutation.isPending ? 'Saving...' : 'Log Risk'}
            </Button>
          </div>
        </div>
      </Modal>

      {/* Record Decision Modal */}
      <Modal isOpen={decisionModalOpen} onClose={() => setDecisionModalOpen(false)} title="Record Architecture Decision (ADR)">
        <div className="space-y-3 pt-2">
          <div>
            <label className="block text-xs font-medium text-muted-foreground mb-1">Decision Title *</label>
            <input
              type="text"
              value={newDecisionTitle}
              onChange={(e) => setNewDecisionTitle(e.target.value)}
              placeholder="e.g. Adopt PostgreSQL with JPA for Multi-Tenant Isolation"
              className="w-full h-8 px-3 rounded-md border border-border bg-background text-xs focus:outline-none focus:ring-1 focus:ring-primary"
            />
          </div>

          <div>
            <label className="block text-xs font-medium text-muted-foreground mb-1">Context & Problem Statement *</label>
            <textarea
              value={newDecisionContext}
              onChange={(e) => setNewDecisionContext(e.target.value)}
              placeholder="What situation or problem does this decision address?"
              rows={2}
              className="w-full p-2.5 rounded-md border border-border bg-background text-xs focus:outline-none focus:ring-1 focus:ring-primary resize-none"
            />
          </div>

          <div>
            <label className="block text-xs font-medium text-muted-foreground mb-1">Decision Justification *</label>
            <textarea
              value={newDecisionJustification}
              onChange={(e) => setNewDecisionJustification(e.target.value)}
              placeholder="What was decided and why was this approach chosen over alternatives?"
              rows={2}
              className="w-full p-2.5 rounded-md border border-border bg-background text-xs focus:outline-none focus:ring-1 focus:ring-primary resize-none"
            />
          </div>

          <div>
            <label className="block text-xs font-medium text-muted-foreground mb-1">Consequences & Trade-offs</label>
            <textarea
              value={newDecisionConsequences}
              onChange={(e) => setNewDecisionConsequences(e.target.value)}
              placeholder="What are the downstream effects or compromises?"
              rows={2}
              className="w-full p-2.5 rounded-md border border-border bg-background text-xs focus:outline-none focus:ring-1 focus:ring-primary resize-none"
            />
          </div>

          <div className="flex justify-end gap-2 pt-3 border-t border-border">
            <Button variant="outline" size="sm" onClick={() => setDecisionModalOpen(false)}>
              Cancel
            </Button>
            <Button
              size="sm"
              disabled={!newDecisionTitle.trim() || !newDecisionContext.trim() || !newDecisionJustification.trim() || createDecisionMutation.isPending}
              onClick={() => createDecisionMutation.mutate()}
            >
              {createDecisionMutation.isPending ? 'Saving...' : 'Record Decision'}
            </Button>
          </div>
        </div>
      </Modal>
    </div>
  );
}

export default GovernancePage;
