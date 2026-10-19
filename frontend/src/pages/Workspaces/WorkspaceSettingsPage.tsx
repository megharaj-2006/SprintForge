import React, { useState } from 'react';
import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import { useWorkspaceStore } from '../../store/useWorkspaceStore';
import { workspaceApi, notificationApi } from '../../api/endpoints';
import { Button } from '../../components/ui/Button';
import { Modal } from '../../components/ui/Modal';
import type { WorkspaceMember, ActivityFeedItem } from '../../types';
import {
  Settings,
  Users,
  Shield,
  History,
  Plus,
  Trash2,
  Mail,
  UserCheck,
  CheckCircle2,
  AlertCircle,
  Building,
} from 'lucide-react';

type SettingsTab = 'general' | 'members' | 'roles' | 'audit';

export function WorkspaceSettingsPage() {
  const queryClient = useQueryClient();
  const { currentWorkspace, loadWorkspaces } = useWorkspaceStore();
  const workspaceId = currentWorkspace?.id;

  const [activeTab, setActiveTab] = useState<SettingsTab>('members');
  const [inviteModalOpen, setInviteModalOpen] = useState(false);
  const [inviteEmail, setInviteEmail] = useState('');
  const [inviteRole, setInviteRole] = useState('DEVELOPER');
  const [inviteStatus, setInviteStatus] = useState<string | null>(null);

  // General settings state
  const [wsName, setWsName] = useState(currentWorkspace?.name || '');
  const [wsDesc, setWsDesc] = useState(currentWorkspace?.description || '');

  // Members Query
  const {
    data: members = [],
    isLoading: isMembersLoading,
  } = useQuery<WorkspaceMember[]>({
    queryKey: ['workspaceMembers', workspaceId],
    queryFn: async () => {
      if (!workspaceId) return [];
      const res = await workspaceApi.getMembers(workspaceId);
      return Array.isArray(res) ? res : [];
    },
    enabled: Boolean(workspaceId),
  });

  // Activity Feed Query
  const {
    data: auditLogs = [],
    isLoading: isAuditLoading,
  } = useQuery<ActivityFeedItem[]>({
    queryKey: ['workspaceAudit', workspaceId],
    queryFn: async () => {
      if (!workspaceId) return [];
      const res = await notificationApi.getActivityFeed(String(workspaceId));
      return Array.isArray(res) ? res : [];
    },
    enabled: Boolean(workspaceId),
  });

  // Invite Member Mutation
  const inviteMutation = useMutation({
    mutationFn: async () => {
      if (!workspaceId) throw new Error('No workspace selected');
      return workspaceApi.inviteMember(workspaceId, {
        email: inviteEmail,
        role: inviteRole,
      });
    },
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['workspaceMembers', workspaceId] });
      setInviteModalOpen(false);
      setInviteEmail('');
      setInviteStatus('Invitation sent successfully.');
      setTimeout(() => setInviteStatus(null), 4000);
    },
    onError: (err: any) => {
      setInviteStatus(err.response?.data?.message || err.message || 'Invitation failed.');
    },
  });

  // Update Role Mutation (DEC-001)
  const updateRoleMutation = useMutation({
    mutationFn: async ({ memberId, role }: { memberId: number; role: string }) => {
      if (!workspaceId) return;
      return workspaceApi.updateMemberRole(workspaceId, memberId, role);
    },
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['workspaceMembers', workspaceId] });
    },
  });

  // Remove Member Mutation
  const removeMemberMutation = useMutation({
    mutationFn: async (memberId: number) => {
      if (!workspaceId) return;
      return workspaceApi.removeMember(workspaceId, memberId);
    },
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['workspaceMembers', workspaceId] });
    },
  });

  // Save General Settings Mutation
  const updateWorkspaceMutation = useMutation({
    mutationFn: async () => {
      if (!workspaceId) return;
      return workspaceApi.updateWorkspace(workspaceId, {
        name: wsName,
        description: wsDesc,
      });
    },
    onSuccess: () => {
      loadWorkspaces();
      alert('Workspace updated.');
    },
  });

  return (
    <div className="flex flex-col h-full overflow-y-auto select-none p-6 max-w-7xl mx-auto space-y-6">
      {/* Header */}
      <div className="flex flex-wrap items-center justify-between gap-4 pb-5 border-b border-border/80">
        <div>
          <h1 className="text-xl font-semibold tracking-tight text-foreground flex items-center gap-2.5">
            <Settings className="w-5 h-5 text-foreground" />
            Workspace Administration
          </h1>
          <p className="text-xs text-muted-foreground mt-1">
            Manage members, role assignments (Option C DEC-001), security permissions, and audit logs for{' '}
            <span className="font-semibold text-foreground">{currentWorkspace?.name || 'SprintForge'}</span>
          </p>
        </div>

        {activeTab === 'members' && (
          <Button
            onClick={() => setInviteModalOpen(true)}
            size="sm"
            variant="primary"
            className="text-xs h-8 font-medium"
          >
            <Plus className="w-3.5 h-3.5 mr-1" />
            Invite Member
          </Button>
        )}
      </div>

      {inviteStatus && (
        <div className="p-3 rounded-md bg-surface-raised border border-border text-foreground text-xs flex items-center gap-2 shadow-2xs">
          <CheckCircle2 className="w-4 h-4 shrink-0 text-emerald-500" />
          <span>{inviteStatus}</span>
        </div>
      )}

      {/* Tabs */}
      <div className="flex items-center gap-2 border-b border-border/60 py-2">
        <button
          onClick={() => setActiveTab('members')}
          className={`flex items-center gap-1.5 px-3 py-1.5 rounded-md text-xs font-medium transition-all ${
            activeTab === 'members'
              ? 'bg-surface-hover text-foreground shadow-xs'
              : 'text-muted-foreground hover:text-foreground'
          }`}
        >
          <Users className="w-3.5 h-3.5" />
          <span>Members & Roles ({members.length})</span>
        </button>

        <button
          onClick={() => setActiveTab('roles')}
          className={`flex items-center gap-1.5 px-3 py-1.5 rounded-md text-xs font-medium transition-all ${
            activeTab === 'roles'
              ? 'bg-surface-hover text-foreground shadow-xs'
              : 'text-muted-foreground hover:text-foreground'
          }`}
        >
          <Shield className="w-3.5 h-3.5" />
          <span>Permission Matrix</span>
        </button>

        <button
          onClick={() => setActiveTab('audit')}
          className={`flex items-center gap-1.5 px-3 py-1.5 rounded-md text-xs font-medium transition-all ${
            activeTab === 'audit'
              ? 'bg-surface-hover text-foreground shadow-xs'
              : 'text-muted-foreground hover:text-foreground'
          }`}
        >
          <History className="w-3.5 h-3.5" />
          <span>Audit Log</span>
        </button>

        <button
          onClick={() => setActiveTab('general')}
          className={`flex items-center gap-1.5 px-3 py-1.5 rounded-md text-xs font-medium transition-all ${
            activeTab === 'general'
              ? 'bg-surface-hover text-foreground shadow-xs'
              : 'text-muted-foreground hover:text-foreground'
          }`}
        >
          <Building className="w-3.5 h-3.5" />
          <span>General Settings</span>
        </button>
      </div>

      {/* TAB 1: Members & Role Management (Option C DEC-001) */}
      {activeTab === 'members' && (
        <div className="border border-border/80 rounded-xl bg-surface overflow-hidden">
          <div className="px-5 py-3 border-b border-border/80 flex items-center justify-between">
            <h3 className="text-xs font-semibold uppercase tracking-wider text-muted-foreground">
              Workspace Roster ({members.length})
            </h3>
            <span className="text-[11px] text-muted-foreground">
              Roles control authorization for platform actions
            </span>
          </div>

          {isMembersLoading ? (
            <div className="py-12 text-center text-xs text-muted-foreground">
              Loading members...
            </div>
          ) : members.length === 0 ? (
            <div className="p-8 text-center text-xs text-muted-foreground">
              No additional members in this workspace. Invite teammates to collaborate.
            </div>
          ) : (
            <div className="overflow-x-auto">
              <table className="w-full text-left text-xs border-collapse">
                <thead>
                  <tr className="border-b border-border bg-surface-hover/50 text-muted-foreground font-medium">
                    <th className="px-5 py-2.5">User</th>
                    <th className="px-4 py-2.5">Email</th>
                    <th className="px-4 py-2.5">Workspace Role</th>
                    <th className="px-4 py-2.5">Joined</th>
                    <th className="px-4 py-2.5 text-right">Actions</th>
                  </tr>
                </thead>
                <tbody className="divide-y divide-border/60">
                  {members.map((m) => (
                    <tr key={m.id} className="hover:bg-surface-hover/40 transition-colors">
                      <td className="px-5 py-3">
                        <div className="flex items-center gap-2.5">
                          <div className="w-7 h-7 rounded-full bg-surface-raised border border-border text-foreground flex items-center justify-center font-semibold uppercase text-xs shadow-2xs">
                            {m.user?.username ? m.user.username[0] : 'U'}
                          </div>
                          <span className="font-semibold text-foreground">
                            {m.user?.username || `Member #${m.userId}`}
                          </span>
                        </div>
                      </td>
                      <td className="px-4 py-3 text-muted-foreground text-[11px]">
                        {m.user?.email || '—'}
                      </td>
                      <td className="px-4 py-3">
                        <select
                          value={m.role}
                          onChange={(e) =>
                            updateRoleMutation.mutate({
                              memberId: m.id,
                              role: e.target.value,
                            })
                          }
                          className="h-7 px-2 rounded-md border border-border/80 bg-background text-xs font-medium text-foreground focus:outline-none focus:ring-1 focus:ring-foreground"
                        >
                          <option value="ADMIN">ADMIN</option>
                          <option value="DEVELOPER">DEVELOPER</option>
                          <option value="VIEWER">VIEWER</option>
                        </select>
                      </td>
                      <td className="px-4 py-3 text-muted-foreground tabular-nums text-[11px]">
                        {m.joinedAt ? new Date(m.joinedAt).toLocaleDateString() : 'Active'}
                      </td>
                      <td className="px-4 py-3 text-right">
                        <button
                          onClick={() => {
                            if (window.confirm('Remove member from workspace?')) {
                              removeMemberMutation.mutate(m.id);
                            }
                          }}
                          className="p-1 rounded text-muted-foreground hover:text-rose-500 hover:bg-rose-500/10 transition-colors"
                          title="Remove member"
                        >
                          <Trash2 className="w-3.5 h-3.5" />
                        </button>
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          )}
        </div>
      )}

      {/* TAB 2: Permission Matrix */}
      {activeTab === 'roles' && (
        <div className="border border-border/80 rounded-xl bg-surface p-5 space-y-4">
          <div>
            <h3 className="text-sm font-semibold text-foreground">Role Permissions Matrix</h3>
            <p className="text-xs text-muted-foreground">Capabilities mapped across platform authorization tiers</p>
          </div>

          <div className="overflow-x-auto">
            <table className="w-full text-left text-xs border-collapse border border-border/60 rounded-lg">
              <thead>
                <tr className="border-b border-border bg-surface-hover text-muted-foreground font-medium">
                  <th className="px-4 py-2.5">Platform Capability</th>
                  <th className="px-4 py-2.5 text-center">ADMIN</th>
                  <th className="px-4 py-2.5 text-center">DEVELOPER</th>
                  <th className="px-4 py-2.5 text-center">VIEWER</th>
                </tr>
              </thead>
              <tbody className="divide-y divide-border/60">
                <tr>
                  <td className="px-4 py-2.5 font-medium text-foreground">Create & Move Issues</td>
                  <td className="text-center text-emerald-500">✓</td>
                  <td className="text-center text-emerald-500">✓</td>
                  <td className="text-center text-muted-foreground/40">—</td>
                </tr>
                <tr>
                  <td className="px-4 py-2.5 font-medium text-foreground">Manage Project Sprints</td>
                  <td className="text-center text-emerald-500">✓</td>
                  <td className="text-center text-emerald-500">✓</td>
                  <td className="text-center text-muted-foreground/40">—</td>
                </tr>
                <tr>
                  <td className="px-4 py-2.5 font-medium text-foreground">Log Risks & Architecture Decisions</td>
                  <td className="text-center text-emerald-500">✓</td>
                  <td className="text-center text-emerald-500">✓</td>
                  <td className="text-center text-muted-foreground/40">—</td>
                </tr>
                <tr>
                  <td className="px-4 py-2.5 font-medium text-foreground">Workspace Member & Role Promotion</td>
                  <td className="text-center text-emerald-500">✓</td>
                  <td className="text-center text-muted-foreground/40">—</td>
                  <td className="text-center text-muted-foreground/40">—</td>
                </tr>
                <tr>
                  <td className="px-4 py-2.5 font-medium text-foreground">Execute Automation Workflows</td>
                  <td className="text-center text-emerald-500">✓</td>
                  <td className="text-center text-emerald-500">✓</td>
                  <td className="text-center text-muted-foreground/40">—</td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      )}

      {/* TAB 3: Audit Log */}
      {activeTab === 'audit' && (
        <div className="border border-border/80 rounded-xl bg-surface p-5 space-y-4">
          <div>
            <h3 className="text-sm font-semibold text-foreground">Workspace Audit Trail</h3>
            <p className="text-xs text-muted-foreground">Immutable history of member activities and state transitions</p>
          </div>

          {isAuditLoading ? (
            <div className="py-12 text-center text-xs text-muted-foreground">Loading audit feed...</div>
          ) : auditLogs.length === 0 ? (
            <div className="p-8 text-center text-xs text-muted-foreground">
              No recent audit records logged for this workspace.
            </div>
          ) : (
            <div className="space-y-2">
              {auditLogs.map((log) => (
                <div key={log.id} className="p-3 rounded-lg border border-border/60 bg-surface-hover/30 flex items-center justify-between text-xs">
                  <div className="flex items-center gap-2.5">
                    <History className="w-3.5 h-3.5 text-foreground" />
                    <span className="font-medium text-foreground">{log.action}</span>
                    <span className="text-muted-foreground">— {log.targetType || log.entityType || 'Entity'} {log.targetName || log.entityId || ''}</span>
                  </div>
                  <span className="tabular-nums text-[11px] text-muted-foreground">
                    {log.timestamp || log.createdAt ? new Date(log.timestamp || (log.createdAt as string)).toLocaleTimeString() : 'Just now'}
                  </span>
                </div>
              ))}
            </div>
          )}
        </div>
      )}

      {/* TAB 4: General Settings */}
      {activeTab === 'general' && (
        <div className="border border-border/80 rounded-xl bg-surface p-5 max-w-xl space-y-4">
          <div>
            <h3 className="text-sm font-semibold text-foreground">Workspace Profile</h3>
            <p className="text-xs text-muted-foreground">Primary workspace identity</p>
          </div>

          <div>
            <label className="block text-xs font-medium text-muted-foreground mb-1">
              Workspace Name
            </label>
            <input
              type="text"
              value={wsName}
              onChange={(e) => setWsName(e.target.value)}
              className="w-full h-8 px-3 rounded-md border border-border bg-background text-xs focus:outline-none focus:ring-1 focus:ring-primary"
            />
          </div>

          <div>
            <label className="block text-xs font-medium text-muted-foreground mb-1">
              Description
            </label>
            <textarea
              value={wsDesc}
              onChange={(e) => setWsDesc(e.target.value)}
              rows={3}
              className="w-full p-2.5 rounded-md border border-border bg-background text-xs focus:outline-none focus:ring-1 focus:ring-primary resize-none"
            />
          </div>

          <Button
            size="sm"
            disabled={updateWorkspaceMutation.isPending}
            onClick={() => updateWorkspaceMutation.mutate()}
          >
            {updateWorkspaceMutation.isPending ? 'Saving...' : 'Save Changes'}
          </Button>
        </div>
      )}

      {/* Invite Member Modal */}
      <Modal isOpen={inviteModalOpen} onClose={() => setInviteModalOpen(false)} title="Invite Member to Workspace">
        <div className="space-y-3 pt-2">
          <div>
            <label className="block text-xs font-medium text-muted-foreground mb-1">Teammate Email *</label>
            <div className="relative">
              <Mail className="w-4 h-4 text-muted-foreground/60 absolute left-3 top-2.5" />
              <input
                type="email"
                value={inviteEmail}
                onChange={(e) => setInviteEmail(e.target.value)}
                placeholder="developer@acme.corp"
                autoFocus
                className="w-full h-8 pl-9 pr-3 rounded-md border border-border bg-background text-xs focus:outline-none focus:ring-1 focus:ring-primary"
              />
            </div>
          </div>

          <div>
            <label className="block text-xs font-medium text-muted-foreground mb-1">Assigned Role</label>
            <select
              value={inviteRole}
              onChange={(e) => setInviteRole(e.target.value)}
              className="w-full h-8 px-2 rounded-md border border-border bg-background text-xs focus:outline-none focus:ring-1 focus:ring-primary"
            >
              <option value="DEVELOPER">DEVELOPER (Default Engineering Access)</option>
              <option value="ADMIN">ADMIN (Full Workspace Management)</option>
              <option value="VIEWER">VIEWER (Read-Only Observer)</option>
            </select>
          </div>

          <div className="flex justify-end gap-2 pt-3 border-t border-border">
            <Button variant="outline" size="sm" onClick={() => setInviteModalOpen(false)}>
              Cancel
            </Button>
            <Button
              size="sm"
              disabled={!inviteEmail.trim() || inviteMutation.isPending}
              onClick={() => inviteMutation.mutate()}
            >
              {inviteMutation.isPending ? 'Sending...' : 'Send Invitation'}
            </Button>
          </div>
        </div>
      </Modal>
    </div>
  );
}

export default WorkspaceSettingsPage;
