import { useEffect } from 'react';
import { useUIStore } from '../store/useUIStore';

export function useKeyboardShortcuts() {
  const {
    toggleCommandPalette,
    toggleCopilot,
    setCreateTaskModalOpen,
    closeTaskDrawer,
    activeTaskDrawerId,
    commandPaletteOpen,
    copilotOpen,
    createTaskModalOpen,
  } = useUIStore();

  useEffect(() => {
    const handleKeyDown = (e: KeyboardEvent) => {
      const target = e.target as HTMLElement;
      const isInput =
        target.tagName === 'INPUT' ||
        target.tagName === 'TEXTAREA' ||
        target.isContentEditable;

      // Cmd+K / Ctrl+K: Universal Command Palette
      if ((e.metaKey || e.ctrlKey) && e.key.toLowerCase() === 'k') {
        e.preventDefault();
        toggleCommandPalette();
        return;
      }

      // Cmd+J / Ctrl+J: AI Copilot Drawer
      if ((e.metaKey || e.ctrlKey) && e.key.toLowerCase() === 'j') {
        e.preventDefault();
        toggleCopilot();
        return;
      }

      // Escape: Dismiss open drawers/modals
      if (e.key === 'Escape') {
        if (commandPaletteOpen) {
          useUIStore.getState().setCommandPaletteOpen(false);
          return;
        }
        if (createTaskModalOpen) {
          setCreateTaskModalOpen(false);
          return;
        }
        if (copilotOpen) {
          useUIStore.getState().setCopilotOpen(false);
          return;
        }
        if (activeTaskDrawerId) {
          closeTaskDrawer();
          return;
        }
      }

      // If user is currently typing in an input or textarea, do NOT trigger single-key hotkeys
      if (isInput) return;

      // 'c': Create new task modal
      if (e.key === 'c' && !e.metaKey && !e.ctrlKey) {
        e.preventDefault();
        setCreateTaskModalOpen(true);
        return;
      }
    };

    window.addEventListener('keydown', handleKeyDown);
    return () => window.removeEventListener('keydown', handleKeyDown);
  }, [
    toggleCommandPalette,
    toggleCopilot,
    setCreateTaskModalOpen,
    closeTaskDrawer,
    activeTaskDrawerId,
    commandPaletteOpen,
    copilotOpen,
    createTaskModalOpen,
  ]);
}
