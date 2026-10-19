import React from 'react';
import { Routes, Route, Navigate, useLocation } from 'react-router-dom';
import { useAuthStore } from './store/useAuthStore';
import { AppShell } from './components/shell/AppShell';

// Marketing Layout & Pages
import { MarketingLayout } from './components/marketing/MarketingLayout';
import { LandingPage } from './pages/Marketing/LandingPage';
import { HowItWorksPage } from './pages/Marketing/HowItWorksPage';
import { PricingPage } from './pages/Marketing/PricingPage';
import { AboutPage } from './pages/Marketing/AboutPage';
import { DocsPage } from './pages/Marketing/DocsPage';
import { ContactPage } from './pages/Marketing/ContactPage';

// Auth Pages
import { LoginPage } from './pages/Auth/LoginPage';
import { SignupPage } from './pages/Auth/SignupPage';
import { OAuthCallbackPage } from './pages/Auth/OAuthCallbackPage';

// Main App Pages
import { ProjectsHubPage } from './pages/Projects/ProjectsHubPage';
import { ProjectBoardPage } from './pages/Projects/ProjectBoardPage';
import { MyTasksPage } from './pages/Tasks/MyTasksPage';
import { GovernancePage } from './pages/Governance/GovernancePage';
import { GoalsPage } from './pages/Goals/GoalsPage';
import { AnalyticsPage } from './pages/Analytics/AnalyticsPage';
import { WorkflowStudioPage } from './pages/Workflows/WorkflowStudioPage';
import { WorkspaceSettingsPage } from './pages/Workspaces/WorkspaceSettingsPage';
import { ProfileSettingsPage } from './pages/Settings/ProfileSettingsPage';
import { IntegrationsPage } from './pages/Settings/IntegrationsPage';
import { PlatformAdminPage } from './pages/Admin/PlatformAdminPage';

function ProtectedRoute({ children }: { children: React.ReactNode }) {
  const { isAuthenticated } = useAuthStore();
  const location = useLocation();

  if (!isAuthenticated) {
    return <Navigate to="/login" state={{ from: location }} replace />;
  }

  return <>{children}</>;
}

function CatchAllRoute() {
  const { isAuthenticated } = useAuthStore();
  return <Navigate to={isAuthenticated ? '/projects' : '/'} replace />;
}

export function App() {
  return (
    <Routes>
      {/* Public Marketing Landing Website */}
      <Route element={<MarketingLayout />}>
        <Route path="/" element={<LandingPage />} />
        <Route path="/how-it-works" element={<HowItWorksPage />} />
        <Route path="/pricing" element={<PricingPage />} />
        <Route path="/about" element={<AboutPage />} />
        <Route path="/docs" element={<DocsPage />} />
        <Route path="/contact" element={<ContactPage />} />
      </Route>

      {/* Public Authentication Routes */}
      <Route path="/login" element={<LoginPage />} />
      <Route path="/signup" element={<SignupPage />} />
      <Route path="/auth/callback/:provider" element={<OAuthCallbackPage />} />

      {/* Authenticated Workspace Application Shell */}
      <Route
        element={
          <ProtectedRoute>
            <AppShell />
          </ProtectedRoute>
        }
      >
        <Route path="/app" element={<Navigate to="/projects" replace />} />
        <Route path="/projects" element={<ProjectsHubPage />} />
        <Route path="/projects/:projectId" element={<ProjectBoardPage />} />
        <Route path="/projects/:projectId/board" element={<ProjectBoardPage />} />
        <Route path="/projects/:projectId/governance" element={<GovernancePage />} />
        <Route path="/my-tasks" element={<MyTasksPage />} />
        <Route path="/governance" element={<GovernancePage />} />
        <Route path="/goals" element={<GoalsPage />} />
        <Route path="/analytics" element={<AnalyticsPage />} />
        <Route path="/workflows" element={<WorkflowStudioPage />} />

        {/* Profile & User Settings */}
        <Route path="/settings/profile" element={<ProfileSettingsPage />} />
        <Route path="/profile" element={<ProfileSettingsPage />} />

        {/* Workspace & Members Settings */}
        <Route path="/settings/workspace" element={<WorkspaceSettingsPage />} />
        <Route path="/workspace/settings" element={<WorkspaceSettingsPage />} />
        <Route path="/settings" element={<Navigate to="/settings/profile" replace />} />
        <Route path="/settings/integrations" element={<IntegrationsPage />} />
        <Route path="/integrations" element={<IntegrationsPage />} />

        {/* Platform Administration */}
        <Route path="/admin" element={<PlatformAdminPage />} />
        <Route path="/admin/platform" element={<PlatformAdminPage />} />
      </Route>

      {/* Intelligent Catch-all Route: If logged in -> /projects; If guest -> / */}
      <Route path="*" element={<CatchAllRoute />} />
    </Routes>
  );
}

export default App;
