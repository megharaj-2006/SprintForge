import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';
import path from 'path';

const backendTarget = 'http://127.0.0.1:8080';

const createBackendProxy = () => ({
  target: backendTarget,
  changeOrigin: true,
  configure: (proxy: any) => {
    proxy.on('proxyReq', (proxyReq: any, req: any) => {
      // Strip oversized browser localhost cookies that exceed Tomcat's 8KB header limit.
      // SprintForge is 100% stateless JWT and does not use session cookies.
      proxyReq.removeHeader('cookie');
      proxyReq.removeHeader('Cookie');
    });
    proxy.on('proxyRes', (proxyRes: any, req: any) => {
      if (proxyRes.statusCode >= 400) {
        let body = '';
        proxyRes.on('data', (chunk: any) => { body += chunk; });
        proxyRes.on('end', () => {
          console.log(`[PROXY RES ${proxyRes.statusCode}] ${req.method} ${req.url} -> ${body.slice(0, 300)}`);
        });
      }
    });
    proxy.on('error', (err: any, req: any) => {
      console.log(`[PROXY ERR] ${req.method} ${req.url} -> ${err.message}`);
    });
  },
});

// https://vite.dev/config/
export default defineConfig({
  plugins: [react()],
  resolve: {
    alias: {
      '@': path.resolve(__dirname, './src'),
    },
  },
  server: {
    host: '0.0.0.0',
    port: 5173,
    proxy: {
      '/api': createBackendProxy(),
      '/uploads': createBackendProxy(),
      '/storage': createBackendProxy(),
      '/search': createBackendProxy(),
      '/scheduler': createBackendProxy(),
      '/workflows': createBackendProxy(),
      '/notifications': createBackendProxy(),
      '/activity': createBackendProxy(),
      '/health': createBackendProxy(),
      '/metrics': createBackendProxy(),
      '/audit': createBackendProxy(),
      '/cache': createBackendProxy(),
      '/feature-flags': createBackendProxy(),
      '/platform': createBackendProxy(),
      '/dev': createBackendProxy(),
    },
  },
  build: {
    chunkSizeWarningLimit: 1200,
    rollupOptions: {
      output: {
        manualChunks: {
          vendor: ['react', 'react-dom', 'react-router-dom', '@tanstack/react-query', 'zustand'],
          three: ['three', '@react-three/fiber', '@react-three/drei'],
          charts: ['recharts'],
          icons: ['lucide-react'],
        },
      },
    },
  },
});
