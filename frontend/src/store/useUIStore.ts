import { create } from 'zustand';

interface UIState {
  sidebarCollapsed: boolean;
  toggleSidebar: () => void;
  setSidebarCollapsed: (collapsed: boolean) => void;

  commandPaletteOpen: boolean;
  setCommandPaletteOpen: (open: boolean) => void;
  toggleCommandPalette: () => void;

  copilotOpen: boolean;
  setCopilotOpen: (open: boolean) => void;
  toggleCopilot: () => void;

  createTaskModalOpen: boolean;
  setCreateTaskModalOpen: (open: boolean) => void;

  activeTaskDrawerId: number | null;
  openTaskDrawer: (taskId: number) => void;
  closeTaskDrawer: () => void;
}

export const useUIStore = create<UIState>((set) => ({
  sidebarCollapsed: false,
  toggleSidebar: () => set((state) => ({ sidebarCollapsed: !state.sidebarCollapsed })),
  setSidebarCollapsed: (collapsed) => set({ sidebarCollapsed: collapsed }),

  commandPaletteOpen: false,
  setCommandPaletteOpen: (open) => set({ commandPaletteOpen: open }),
  toggleCommandPalette: () => set((state) => ({ commandPaletteOpen: !state.commandPaletteOpen })),

  copilotOpen: false,
  setCopilotOpen: (open) => set({ copilotOpen: open }),
  toggleCopilot: () => set((state) => ({ copilotOpen: !state.copilotOpen })),

  createTaskModalOpen: false,
  setCreateTaskModalOpen: (open) => set({ createTaskModalOpen: open }),

  activeTaskDrawerId: null,
  openTaskDrawer: (taskId) => set({ activeTaskDrawerId: taskId }),
  closeTaskDrawer: () => set({ activeTaskDrawerId: null }),
}));
