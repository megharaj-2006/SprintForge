import React, { useEffect, useState } from 'react';
import { Command } from 'cmdk';
import { useNavigate } from 'react-router-dom';
import { useUIStore } from '../../store/useUIStore';
import { useWorkspaceStore } from '../../store/useWorkspaceStore';
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
  User as UserIcon,
} from 'lucide-react';
import { SprintForgeIcon } from '../brand/SprintForgeLogo';

export function CommandPalette() {
  const { commandPaletteOpen, setCommandPaletteOpen, setCreateTaskModalOpen, toggleCopilot } =
    useUIStore();
  const { projects, currentWorkspace } = useWorkspaceStore();
  const navigate = useNavigate();
  const [search, setSearch] = useState('');

  useEffect(() => {
    if (!commandPaletteOpen) {
      setSearch('');
    }
  }, [commandPaletteOpen]);

  if (!commandPaletteOpen) return null;

  const runAction = (callback: () => void) => {
    setCommandPaletteOpen(false);
    callback();
  };

  return (
    <div className="fixed inset-0 z-50 flex items-start justify-center pt-20 p-4">
      {/* Backdrop */}
      <div
        className="fixed inset-0 bg-black/60 backdrop-blur-xs transition-opacity"
        onClick={() => setCommandPaletteOpen(false)}
      />

      {/* Command Palette Card */}
      <div className="relative w-full max-w-xl bg-surface border border-border shadow-2xl rounded-xl overflow-hidden focus:outline-none">
        <Command
          className="w-full flex flex-col"
          loop
          value={search}
          onValueChange={setSearch}
        >
          <div className="flex items-center px-4 border-b border-border">
            <Command.Input
              autoFocus
              placeholder="Type a command or search (projects, tasks, actions)..."
              className="w-full h-11 bg-transparent text-sm text-foreground placeholder:text-muted-foreground focus:outline-none"
            />
            <kbd className="text-[10px] font-mono text-muted-foreground bg-muted/40 px-1.5 py-0.5 rounded border border-border">
              ESC
            </kbd>
          </div>

          <Command.List className="max-h-80 overflow-y-auto p-2">
            <Command.Empty className="py-6 text-center text-xs text-muted-foreground">
              No results found for "{search}".
            </Command.Empty>

            {/* Quick Actions */}
            <Command.Group
              heading="Quick Actions"
              className="text-[10px] uppercase tracking-wider font-semibold text-muted-foreground px-2 py-1.5"
            >
              <Command.Item
                onSelect={() => runAction(() => setCreateTaskModalOpen(true))}
                className="flex items-center justify-between px-2.5 py-2 text-xs rounded-md text-foreground cursor-pointer hover:bg-surface-hover aria-selected:bg-surface-hover transition-colors"
              >
                <div className="flex items-center gap-2">
                  <Plus className="w-3.5 h-3.5 text-foreground" />
                  <span>Create new task...</span>
                </div>
                <kbd className="text-[10px] text-muted-foreground bg-muted/60 px-1 rounded border border-border">C</kbd>
              </Command.Item>

              <Command.Item
                onSelect={() => runAction(() => toggleCopilot())}
                className="flex items-center justify-between px-2.5 py-2 text-xs rounded-md text-foreground cursor-pointer hover:bg-surface-hover aria-selected:bg-surface-hover transition-colors"
              >
                <div className="flex items-center gap-2">
                  <Sparkles className="w-3.5 h-3.5 text-foreground" />
                  <span>Ask SprintForge AI Copilot...</span>
                </div>
                <kbd className="text-[10px] text-muted-foreground bg-muted/60 px-1 rounded border border-border">⌘J</kbd>
              </Command.Item>
            </Command.Group>

            {/* Navigation Destinations */}
            <Command.Group
              heading="Navigation"
              className="text-[10px] uppercase tracking-wider font-semibold text-muted-foreground px-2 py-1.5 mt-2"
            >
              <Command.Item
                onSelect={() => runAction(() => navigate('/projects'))}
                className="flex items-center gap-2 px-2.5 py-2 text-xs rounded-md text-foreground cursor-pointer hover:bg-surface-hover aria-selected:bg-surface-hover transition-colors"
              >
                <FolderKanban className="w-3.5 h-3.5 text-muted-foreground" />
                <span>Projects Hub</span>
              </Command.Item>

              <Command.Item
                onSelect={() => runAction(() => navigate('/my-tasks'))}
                className="flex items-center gap-2 px-2.5 py-2 text-xs rounded-md text-foreground cursor-pointer hover:bg-surface-hover aria-selected:bg-surface-hover transition-colors"
              >
                <CheckSquare className="w-3.5 h-3.5 text-muted-foreground" />
                <span>My Assigned Tasks</span>
              </Command.Item>

              <Command.Item
                onSelect={() => runAction(() => navigate('/goals'))}
                className="flex items-center gap-2 px-2.5 py-2 text-xs rounded-md text-foreground cursor-pointer hover:bg-surface-hover aria-selected:bg-surface-hover transition-colors"
              >
                <Target className="w-3.5 h-3.5 text-muted-foreground" />
                <span>Strategic OKRs & Goals</span>
              </Command.Item>

              <Command.Item
                onSelect={() => runAction(() => navigate('/analytics'))}
                className="flex items-center gap-2 px-2.5 py-2 text-xs rounded-md text-foreground cursor-pointer hover:bg-surface-hover aria-selected:bg-surface-hover transition-colors"
              >
                <BarChart2 className="w-3.5 h-3.5 text-muted-foreground" />
                <span>Analytics & Executive Dashboard</span>
              </Command.Item>

              <Command.Item
                onSelect={() => runAction(() => navigate('/workflows'))}
                className="flex items-center gap-2 px-2.5 py-2 text-xs rounded-md text-foreground cursor-pointer hover:bg-surface-hover aria-selected:bg-surface-hover transition-colors"
              >
                <Workflow className="w-3.5 h-3.5 text-muted-foreground" />
                <span>Visual Workflow Studio</span>
              </Command.Item>

              <Command.Item
                onSelect={() => runAction(() => navigate('/settings/profile'))}
                className="flex items-center gap-2 px-2.5 py-2 text-xs rounded-md text-foreground cursor-pointer hover:bg-surface-hover aria-selected:bg-surface-hover transition-colors"
              >
                <UserIcon className="w-3.5 h-3.5 text-muted-foreground" />
                <span>Profile & Personal Settings</span>
              </Command.Item>

              <Command.Item
                onSelect={() => runAction(() => navigate('/workspace/settings'))}
                className="flex items-center gap-2 px-2.5 py-2 text-xs rounded-md text-foreground cursor-pointer hover:bg-surface-hover aria-selected:bg-surface-hover transition-colors"
              >
                <Users className="w-3.5 h-3.5 text-cyan-400" />
                <span>Workspace Members & Roles</span>
              </Command.Item>

              <Command.Item
                onSelect={() => runAction(() => navigate('/admin/platform'))}
                className="flex items-center gap-2 px-2.5 py-2 text-xs rounded-md text-foreground cursor-pointer hover:bg-surface-hover aria-selected:bg-surface-hover transition-colors"
              >
                <Shield className="w-3.5 h-3.5 text-rose-400" />
                <span>Platform Administration & Licenses</span>
              </Command.Item>
            </Command.Group>

            {/* Active Projects */}
            {projects.length > 0 && (
              <Command.Group
                heading="Projects"
                className="text-[10px] uppercase tracking-wider font-semibold text-muted-foreground px-2 py-1.5 mt-2"
              >
                {projects.map((proj) => (
                  <Command.Item
                    key={proj.id}
                    onSelect={() => runAction(() => navigate(`/projects/${proj.id}/board`))}
                    className="flex items-center justify-between px-2.5 py-2 text-xs rounded-md text-foreground cursor-pointer hover:bg-surface-hover aria-selected:bg-surface-hover transition-colors"
                  >
                    <div className="flex items-center gap-2 truncate">
                      <span className="text-[10px] font-mono text-muted-foreground bg-muted/40 px-1 py-0.5 rounded">
                        {proj.identifier}
                      </span>
                      <span className="truncate">{proj.name}</span>
                    </div>
                    <span className="text-[10px] text-muted-foreground capitalize">
                      {proj.status.toLowerCase()}
                    </span>
                  </Command.Item>
                ))}
              </Command.Group>
            )}
          </Command.List>

          <div className="px-4 py-2 border-t border-border flex items-center justify-between text-[11px] text-muted-foreground bg-surface/40">
            <div className="flex items-center gap-2">
              <span>Use</span>
              <kbd className="font-mono bg-muted/40 px-1 rounded border border-border">↑</kbd>
              <kbd className="font-mono bg-muted/40 px-1 rounded border border-border">↓</kbd>
              <span>to navigate</span>
              <kbd className="font-mono bg-muted/40 px-1 rounded border border-border ml-2">↵</kbd>
              <span>to select</span>
            </div>
            <div className="flex items-center gap-1.5 font-medium text-foreground/80">
              <SprintForgeIcon className="w-3 h-3 text-foreground" />
              <span>SprintForge Omnisearch</span>
            </div>
          </div>
        </Command>
      </div>
    </div>
  );
}
