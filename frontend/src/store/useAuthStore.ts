import { create } from 'zustand';
import type { User, AuthResponse, SignupResponse } from '../types';
import { authApi, userApi } from '../api/endpoints';

interface AuthState {
  user: User | null;
  token: string | null;
  refreshToken: string | null;
  isAuthenticated: boolean;
  isLoading: boolean;
  login: (data: { usernameOrEmail: string; password: string }) => Promise<void>;
  signup: (data: { username: string; email: string; password: string }) => Promise<SignupResponse>;
  verifyEmail: (data: { email: string; code: string }) => Promise<void>;
  resendVerification: (email: string) => Promise<void>;
  logout: () => Promise<void>;
  fetchCurrentUser: () => Promise<void>;
  setUser: (user: User | null) => void;
}


export const useAuthStore = create<AuthState>((set, get) => ({
  user: null,
  token: localStorage.getItem('sprintforge_token'),
  refreshToken: localStorage.getItem('sprintforge_refresh_token'),
  isAuthenticated: Boolean(localStorage.getItem('sprintforge_token')),
  isLoading: false,

  login: async (credentials) => {
    set({ isLoading: true });
    try {
      const res: AuthResponse = await authApi.login(credentials);
      localStorage.setItem('sprintforge_token', res.token);
      localStorage.setItem('sprintforge_refresh_token', res.refreshToken);

      set({
        token: res.token,
        refreshToken: res.refreshToken,
        isAuthenticated: true,
        user: {
          id: 1, // Fallback until profile fetched
          username: res.username,
          email: res.email,
          role: res.role,
        },
      });

      // Fetch complete user profile
      await get().fetchCurrentUser();
    } finally {
      set({ isLoading: false });
    }
  },

  signup: async (data) => {
    set({ isLoading: true });
    try {
      const res: SignupResponse = await authApi.signup(data);
      return res;
    } finally {
      set({ isLoading: false });
    }
  },

  verifyEmail: async (data) => {
    set({ isLoading: true });
    try {
      const res: AuthResponse = await authApi.verifyEmail(data);
      localStorage.setItem('sprintforge_token', res.token);
      localStorage.setItem('sprintforge_refresh_token', res.refreshToken);

      set({
        token: res.token,
        refreshToken: res.refreshToken,
        isAuthenticated: true,
        user: {
          id: 1,
          username: res.username,
          email: res.email,
          role: res.role,
        },
      });

      await get().fetchCurrentUser();
    } finally {
      set({ isLoading: false });
    }
  },

  resendVerification: async (email) => {
    await authApi.resendVerification(email);
  },

  logout: async () => {
    try {
      await authApi.logout();
    } catch {
      // Ignore network errors on logout
    } finally {
      localStorage.removeItem('sprintforge_token');
      localStorage.removeItem('sprintforge_refresh_token');
      localStorage.removeItem('sprintforge_workspace_id');
      set({
        user: null,
        token: null,
        refreshToken: null,
        isAuthenticated: false,
      });
      window.location.href = '/';
    }
  },

  fetchCurrentUser: async () => {
    try {
      const user = await userApi.getMe();
      set({ user });
    } catch {
      // If profile fetch fails but token is valid, preserve basic auth state
    }
  },

  setUser: (user) => set({ user }),
}));
