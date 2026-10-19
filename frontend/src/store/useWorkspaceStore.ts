import { create } from 'zustand';
import type { Workspace, Project } from '../types';
import { workspaceApi, projectApi, userApi } from '../api/endpoints';
import { useAuthStore } from './useAuthStore';

interface WorkspaceState {
  workspaces: Workspace[];
  currentWorkspace: Workspace | null;
  projects: Project[];
  currentProject: Project | null;
  isLoadingWorkspaces: boolean;
  isLoadingProjects: boolean;
  loadWorkspaces: (userId?: number) => Promise<void>;
  selectWorkspace: (workspace: Workspace) => void;
  loadProjects: (workspaceId?: number) => Promise<void>;
  selectProject: (project: Project | null) => void;
}

export const useWorkspaceStore = create<WorkspaceState>((set, get) => ({
  workspaces: [],
  currentWorkspace: null,
  projects: [],
  currentProject: null,
  isLoadingWorkspaces: false,
  isLoadingProjects: false,

  loadWorkspaces: async (explicitUserId?: number) => {
    set({ isLoadingWorkspaces: true });
    try {
      let uid = explicitUserId;
      if (!uid) {
        const currentUser = useAuthStore.getState().user;
        uid = currentUser?.id;
      }
      if (!uid) {
        try {
          const profile = await userApi.getMe();
          if (profile?.id) {
            uid = profile.id;
            useAuthStore.setState({ user: profile });
          }
        } catch {
          // fallback
        }
      }

      const data = await workspaceApi.getWorkspaces(uid);
      const list = Array.isArray(data) ? data : [];
      set({ workspaces: list });

      const storedId = localStorage.getItem('sprintforge_workspace_id');
      const found = list.find((w) => String(w.id) === storedId);

      if (found) {
        get().selectWorkspace(found);
      } else if (list.length > 0) {
        get().selectWorkspace(list[0]);
      }
    } catch {
      // Handle empty or error gracefully
    } finally {
      set({ isLoadingWorkspaces: false });
    }
  },

  selectWorkspace: (workspace: Workspace) => {
    localStorage.setItem('sprintforge_workspace_id', String(workspace.id));
    set({ currentWorkspace: workspace, currentProject: null });
    get().loadProjects(workspace.id);
  },

  loadProjects: async (workspaceId?: number) => {
    const wsId = workspaceId ?? get().currentWorkspace?.id;
    if (!wsId) return;

    set({ isLoadingProjects: true });
    try {
      const data = await projectApi.getProjects(wsId);
      const list = Array.isArray(data) ? data : [];
      set({ projects: list });

      if (list.length > 0 && !get().currentProject) {
        set({ currentProject: list[0] });
      }
    } catch {
      set({ projects: [] });
    } finally {
      set({ isLoadingProjects: false });
    }
  },

  selectProject: (project: Project | null) => {
    set({ currentProject: project });
  },
}));
