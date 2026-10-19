/// <reference types="vite/client" />
import axios, { type AxiosRequestConfig } from 'axios';

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || '';

export const apiClient = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

let isRefreshing = false;
let failedQueue: Array<{
  resolve: (value?: unknown) => void;
  reject: (reason?: unknown) => void;
  config: AxiosRequestConfig;
}> = [];

const processQueue = (error: unknown, token: string | null = null) => {
  failedQueue.forEach((prom) => {
    if (error) {
      prom.reject(error);
    } else if (prom.config.headers) {
      prom.config.headers['Authorization'] = `Bearer ${token}`;
      apiClient(prom.config).then(prom.resolve).catch(prom.reject);
    }
  });
  failedQueue = [];
};

// Request Interceptor: Attach JWT & Active Workspace ID
apiClient.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('sprintforge_token');
    const workspaceId = localStorage.getItem('sprintforge_workspace_id');

    const isAuth = config.url?.includes('/api/v1/auth/');
    if (token && config.headers && !isAuth) {
      config.headers['Authorization'] = `Bearer ${token}`;
    }
    if (workspaceId && config.headers && !config.headers['X-Workspace-ID']) {
      config.headers['X-Workspace-ID'] = workspaceId;
    }
    return config;
  },
  (error) => Promise.reject(error)
);

// Response Interceptor: Silent Auto-Refresh on 401
apiClient.interceptors.response.use(
  (response) => response,
  async (error) => {
    const originalRequest = error.config;

    // Do not attempt refresh on auth endpoints (login/signup/refresh itself)
    if (
      error.response?.status === 401 &&
      !originalRequest._retry &&
      !originalRequest.url?.includes('/api/v1/auth/')
    ) {
      if (isRefreshing) {
        return new Promise((resolve, reject) => {
          failedQueue.push({ resolve, reject, config: originalRequest });
        });
      }

      originalRequest._retry = true;
      isRefreshing = true;

      const refreshToken = localStorage.getItem('sprintforge_refresh_token');

      if (!refreshToken) {
        isRefreshing = false;
        localStorage.removeItem('sprintforge_token');
        localStorage.removeItem('sprintforge_refresh_token');
        if (window.location.pathname !== '/login') {
          window.location.href = '/login';
        }
        return Promise.reject(error);
      }

      try {
        const { data } = await axios.post(`${API_BASE_URL}/api/v1/auth/refresh`, {
          refreshToken,
        });

        const newToken = data.token;
        const newRefreshToken = data.refreshToken;

        localStorage.setItem('sprintforge_token', newToken);
        if (newRefreshToken) {
          localStorage.setItem('sprintforge_refresh_token', newRefreshToken);
        }

        if (originalRequest.headers) {
          originalRequest.headers['Authorization'] = `Bearer ${newToken}`;
        }
        processQueue(null, newToken);
        return apiClient(originalRequest);
      } catch (refreshErr) {
        processQueue(refreshErr, null);
        localStorage.removeItem('sprintforge_token');
        localStorage.removeItem('sprintforge_refresh_token');
        if (window.location.pathname !== '/login') {
          window.location.href = '/login';
        }
        return Promise.reject(refreshErr);
      } finally {
        isRefreshing = false;
      }
    }

    return Promise.reject(error);
  }
);
