import React from 'react';
import { NavLink, useLocation } from 'react-router-dom';
import { useWorkspaceStore } from '../../store/useWorkspaceStore';
import { useUIStore } from '../../store/useUIStore';
import { useAuthStore } from '../../store/useAuthStore';
import { cn } from '../../lib/utils';
import { SprintForgeIcon } from '../brand/SprintForgeLogo';
import {
  FolderKanban,
  CheckSquare,
  Sparkles,
  Workflow,
  Shield,
  Target,
  BarChart2,
  Settings,
  Plus,
  Users,
  ChevronLeft,
  ChevronRight,
  Layers,
  FileText,
  AlertTriangle,
  GitPullRequest,
  ChevronsUpDown,
  User as UserIcon,
} from 'lucide-react';

export function CollapsibleSidebar() {
  const { currentWorkspace, workspaces, selectWorkspace, currentProject } = useWorkspaceStore();
  const { sidebarCollapsed, toggleSidebar, setCreateTaskModalOpen } = useUIStore();
  const { user } = useAuthStore();
  const location = useLocation();

  const avatarSrc = user?.profilePicture || user?.avatar || user?.avatarUrl;

  const navLinks = [
    {
      to: currentProject ? `/projects/${currentProject.id}/board` : '/projects',
      label: 'Board',
      icon: <FolderKanban className="w-4 h-4 shrink-0" />,
    },
    {
      to: '/my-tasks',
      label: 'My Tasks',
      icon: <CheckSquare className="w-4 h-4 shrink-0" />,
    },
    {
      to: '/projects',
      label: 'All Projects',
      icon: <Layers className="w-4 h-4 shrink-0" />,
    },
    {
      to: currentProject ? `/projects/${currentProject.id}/governance` : '/governance',
      label: 'Governance & Risks',
      icon: <Shield className="w-4 h-4 shrink-0" />,
    },
    {
      to: '/goals',
      label: 'Strategic OKRs',
      icon: <Target className="w-4 h-4 shrink-0" />,
    },
    {
      to: '/analytics',
      label: 'Analytics & Insights',
      icon: <BarChart2 className="w-4 h-4 shrink-0" />,
    },
    {
      to: '/workflows',
      label: 'Workflow Studio',
      icon: <Workflow className="w-4 h-4 shrink-0" />,
    },
    {
      to: '/settings/integrations',
      label: 'Integrations',
      icon: <GitPullRequest className="w-4 h-4 shrink-0" />,
    },
    {
      to: '/admin/platform',
      label: 'Platform Admin',
      icon: <Settings className="w-4 h-4 shrink-0" />,
    },
  ];

  const isLinkActive = (to: string, label: string) => {
    if (label === 'All Projects') {
      return location.pathname === '/projects' || location.pathname === '/projects/';
    }
    if (label === 'Board') {
      return (
        location.pathname.includes('/board') ||
        (location.pathname.startsWith('/projects/') &&
          !location.pathname.includes('/governance') &&
          location.pathname !== '/projects' &&
          location.pathname !== '/projects/')
      );
    }
    if (label === 'Governance & Risks') {
      return location.pathname.includes('/governance');
    }
    return location.pathname.startsWith(to);
  };

  return (
    <aside
      className={cn(
        'h-screen bg-surface border-r border-border/80 flex flex-col justify-between shrink-0 select-none transition-all duration-200 z-30',
        sidebarCollapsed ? 'w-14' : 'w-56'
      )}
    >
      {/* Top: Workspace Selector & New Task CTA */}
      <div className="p-3 border-b border-border/60 space-y-2">
        {/* Workspace Switcher */}
        <div className="flex items-center justify-between">
          <div className="flex items-center gap-2 min-w-0">
            <div className="w-7 h-7 rounded-lg bg-foreground text-background font-bold flex items-center justify-center text-xs shrink-0 shadow-xs">
              {(!currentWorkspace?.name || currentWorkspace.name === 'SprintForge' || currentWorkspace.name.toLowerCase().includes('sprintforge')) ? (
                <SprintForgeIcon className="w-4 h-4 text-background" />
              ) : (
                currentWorkspace.name.slice(0, 2).toUpperCase()
              )}
            </div>
            {!sidebarCollapsed && (
              <div className="truncate text-left leading-tight">
                <p className="text-xs font-semibold text-foreground truncate">
                  {currentWorkspace?.name || 'SprintForge'}
                </p>
                <span className="text-[10px] text-muted-foreground uppercase font-medium tracking-wider">
                  Workspace
                </span>
              </div>
            )}
          </div>
        </div>

        {/* Rapid New Task CTA Button */}
        <button
          onClick={() => setCreateTaskModalOpen(true)}
          className={cn(
            'w-full bg-surface-hover hover:bg-surface-hover/80 text-foreground border border-border/80 rounded-md py-1.5 px-2 text-xs font-medium flex items-center justify-between transition-colors shadow-2xs',
            sidebarCollapsed ? 'justify-center px-0' : ''
          )}
          title="New Task (C)"
        >
          <div className="flex items-center gap-1.5">
            <Plus className="w-3.5 h-3.5 text-foreground shrink-0" />
            {!sidebarCollapsed && <span>New Task</span>}
          </div>
          {!sidebarCollapsed && (
            <kbd className="text-[10px] text-muted-foreground bg-muted/60 px-1 py-0.2 rounded border border-border">
              C
            </kbd>
          )}
        </button>
      </div>

      {/* Main Navigation Links */}
      <div className="flex-1 overflow-y-auto px-2 py-3 space-y-0.5">
        {!sidebarCollapsed && (
          <div className="px-2 pb-1 text-[10px] font-semibold uppercase tracking-wider text-muted-foreground/80">
            Workspace
          </div>
        )}

        {navLinks.map((link) => {
          const isActive = isLinkActive(link.to, link.label);
          return (
            <NavLink
              key={link.label}
              to={link.to}
              className={cn(
                'flex items-center gap-2.5 px-2.5 py-1.5 rounded-md text-xs font-medium transition-colors group',
                isActive
                  ? 'bg-surface-active text-foreground font-semibold shadow-2xs'
                  : 'text-muted-foreground hover:text-foreground hover:bg-surface-hover',
                sidebarCollapsed ? 'justify-center px-0' : ''
              )}
              title={sidebarCollapsed ? link.label : undefined}
            >
              <span className={cn(isActive ? 'text-foreground' : 'text-muted-foreground group-hover:text-foreground')}>
                {link.icon}
              </span>
              {!sidebarCollapsed && <span className="truncate">{link.label}</span>}
            </NavLink>
          );
        })}
      </div>

      {/* Footer: User profile, Workspace Members, Collapse Toggle */}
      <div className="p-2 border-t border-border/60 space-y-1">
        {/* User Profile Quick Link with Avatar / DP */}
        <NavLink
          to="/settings/profile"
          className={cn(
            'flex items-center gap-2.5 px-2 py-1.5 rounded-md text-xs text-muted-foreground hover:text-foreground hover:bg-surface-hover transition-colors group',
            location.pathname.startsWith('/settings/profile') || location.pathname.startsWith('/profile')
              ? 'bg-surface-active text-foreground font-semibold'
              : '',
            sidebarCollapsed ? 'justify-center px-0' : ''
          )}
          title="Profile & Preferences"
        >
          {avatarSrc ? (
            <img
              src={avatarSrc}
              alt={user?.username || 'User Profile'}
              className="w-5 h-5 rounded-full object-cover shrink-0 border border-border"
            />
          ) : (
            <div className="w-5 h-5 rounded-full bg-surface-raised border border-border text-foreground text-[9px] font-bold flex items-center justify-center uppercase shrink-0">
              {user?.username?.slice(0, 2) || 'SF'}
            </div>
          )}
          {!sidebarCollapsed && (
            <div className="truncate text-left leading-tight min-w-0">
              <span className="truncate block font-medium text-foreground group-hover:text-foreground">
                {user?.fullName || user?.username || 'Profile'}
              </span>
            </div>
          )}
        </NavLink>

        <NavLink
          to="/workspace/settings"
          className={cn(
            'flex items-center gap-2.5 px-2.5 py-1.5 rounded-md text-xs text-muted-foreground hover:text-foreground hover:bg-surface-hover transition-colors',
            location.pathname.startsWith('/workspace/settings')
              ? 'bg-surface-active text-foreground font-semibold'
              : '',
            sidebarCollapsed ? 'justify-center px-0' : ''
          )}
          title="Workspace Settings & Members"
        >
          <Users className="w-4 h-4 shrink-0 text-muted-foreground" />
          {!sidebarCollapsed && <span>Members & Roles</span>}
        </NavLink>

        <button
          onClick={toggleSidebar}
          className={cn(
            'w-full flex items-center gap-2.5 px-2.5 py-1.5 rounded-md text-xs text-muted-foreground hover:text-foreground hover:bg-surface-hover transition-colors',
            sidebarCollapsed ? 'justify-center px-0' : 'justify-between'
          )}
          title={sidebarCollapsed ? 'Expand sidebar' : 'Collapse sidebar'}
        >
          {!sidebarCollapsed && <span className="text-[11px]">Collapse</span>}
          {sidebarCollapsed ? (
            <ChevronRight className="w-4 h-4 shrink-0" />
          ) : (
            <ChevronLeft className="w-4 h-4 shrink-0" />
          )}
        </button>
      </div>
    </aside>
  );
}
