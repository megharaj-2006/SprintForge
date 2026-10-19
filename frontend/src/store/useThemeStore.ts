import { create } from 'zustand';

type Theme = 'dark' | 'light' | 'system';

interface ThemeState {
  theme: Theme;
  isDark: boolean;
  setTheme: (theme: Theme) => void;
  toggleTheme: () => void;
}

function resolveIsDark(theme: Theme): boolean {
  if (theme === 'system') {
    return window.matchMedia('(prefers-color-scheme: dark)').matches;
  }
  return theme === 'dark';
}

function applyTheme(isDark: boolean) {
  if (isDark) {
    document.documentElement.classList.add('dark');
    document.documentElement.style.colorScheme = 'dark';
  } else {
    document.documentElement.classList.remove('dark');
    document.documentElement.style.colorScheme = 'light';
  }
}

const savedTheme = (localStorage.getItem('sprintforge-theme') as Theme) || 'system';
const initialIsDark = resolveIsDark(savedTheme);
applyTheme(initialIsDark);

export const useThemeStore = create<ThemeState>((set, get) => ({
  theme: savedTheme,
  isDark: initialIsDark,

  setTheme: (theme: Theme) => {
    localStorage.setItem('sprintforge-theme', theme);
    const isDark = resolveIsDark(theme);
    applyTheme(isDark);
    set({ theme, isDark });
  },

  toggleTheme: () => {
    // If currently dark (either via explicit 'dark' or 'system' resolving to dark), toggle to 'light'
    const currentlyDark = get().isDark;
    const next: Theme = currentlyDark ? 'light' : 'dark';
    get().setTheme(next);
  },
}));

// Listen to OS preference changes when in 'system' mode
if (typeof window !== 'undefined') {
  window.matchMedia('(prefers-color-scheme: dark)').addEventListener('change', (e) => {
    if (useThemeStore.getState().theme === 'system') {
      applyTheme(e.matches);
      useThemeStore.setState({ isDark: e.matches });
    }
  });
}
