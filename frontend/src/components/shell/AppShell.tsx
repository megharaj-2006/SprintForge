import React, { useEffect } from 'react';
import { Outlet } from 'react-router-dom';
import { CollapsibleSidebar } from './CollapsibleSidebar';
import { TopNavbar } from './TopNavbar';
import { CommandPalette } from './CommandPalette';
import { AICopilotDrawer } from './AICopilotDrawer';
import { CreateTaskModal } from '../tasks/CreateTaskModal';
import { TaskDetailDrawer } from '../tasks/TaskDetailDrawer';
import { useKeyboardShortcuts } from '../../hooks/useKeyboardShortcuts';
import { useWorkspaceStore } from '../../store/useWorkspaceStore';
import { useAuthStore } from '../../store/useAuthStore';

export function AppShell() {
  // Bind global Linear-style keyboard hotkeys
  useKeyboardShortcuts();

  const { loadWorkspaces, workspaces } = useWorkspaceStore();
  const { fetchCurrentUser, user } = useAuthStore();

  useEffect(() => {
    if (!user) {
      fetchCurrentUser();
    }
    if (workspaces.length === 0) {
      loadWorkspaces();
    }
  }, [user, fetchCurrentUser, loadWorkspaces, workspaces.length]);

  return (
    <div className="flex h-screen w-screen overflow-hidden bg-background text-foreground select-none font-sans">
      {/* Collapsible Left Navigation Sidebar */}
      <CollapsibleSidebar />

      {/* Main Content Area */}
      <div className="flex-1 flex flex-col min-w-0 h-full overflow-hidden">
        <TopNavbar />
        <main className="flex-1 overflow-y-auto min-w-0 bg-background relative focus:outline-none">
          <Outlet />
        </main>
      </div>

      {/* Global Modals, Drawers & Overlays */}
      <CommandPalette />
      <AICopilotDrawer />
      <CreateTaskModal />
      <TaskDetailDrawer />
    </div>
  );
}

export default AppShell;
