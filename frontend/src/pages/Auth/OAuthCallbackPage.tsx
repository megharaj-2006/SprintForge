import React, { useEffect, useState } from 'react';
import { useParams, useSearchParams, useNavigate, Link } from 'react-router-dom';
import { authApi } from '../../api/endpoints';
import { useAuthStore } from '../../store/useAuthStore';
import { Button } from '../../components/ui/Button';
import {
  Sparkles,
  AlertCircle,
  ArrowRight,
  ShieldCheck,
  CheckCircle2,
} from 'lucide-react';
import { SprintForgeLogo } from '../../components/brand/SprintForgeLogo';

export function OAuthCallbackPage() {
  const { provider } = useParams<{ provider: string }>();
  const [searchParams] = useSearchParams();
  const navigate = useNavigate();
  const { setUser, fetchCurrentUser } = useAuthStore();

  const [status, setStatus] = useState<'exchanging' | 'success' | 'error'>('exchanging');
  const [errorMessage, setErrorMessage] = useState<string | null>(null);

  const providerName = provider
    ? provider.charAt(0).toUpperCase() + provider.slice(1).toLowerCase()
    : 'OAuth Provider';

  useEffect(() => {
    const code = searchParams.get('code');
    const error = searchParams.get('error');
    const errorDescription = searchParams.get('error_description');

    if (error) {
      setStatus('error');
      setErrorMessage(errorDescription || `Authentication with ${providerName} was cancelled or declined.`);
      return;
    }

    if (!code) {
      setStatus('error');
      setErrorMessage(`No authorization code received from ${providerName}.`);
      return;
    }

    if (!provider) {
      setStatus('error');
      setErrorMessage('Missing OAuth provider specification.');
      return;
    }

    const currentProvider = provider;
    let isMounted = true;

    async function handleExchange() {
      try {
        const redirectUri = `${window.location.origin}/auth/callback/${currentProvider}`;
        const response = await authApi.exchangeOAuthToken(currentProvider, {
          code: code as string,
          redirectUri,
        });

        if (!isMounted) return;

        // Persist SprintForge tokens
        localStorage.setItem('sprintforge_token', response.token);
        if (response.refreshToken) {
          localStorage.setItem('sprintforge_refresh_token', response.refreshToken);
        }

        useAuthStore.setState({
          token: response.token,
          refreshToken: response.refreshToken || null,
          isAuthenticated: true,
          user: {
            id: 1,
            username: response.username,
            email: response.email,
            role: response.role,
          },
        });

        setStatus('success');

        // Fetch complete hydrated profile claims
        try {
          await fetchCurrentUser();
        } catch {}

        // Smooth transition to workspace
        setTimeout(() => {
          navigate('/projects', { replace: true });
        }, 600);
      } catch (err: any) {
        if (!isMounted) return;
        setStatus('error');
        const detail =
          err.response?.data?.message ||
          err.response?.data?.error ||
          err.message ||
          `Failed to authenticate with ${providerName}.`;
        setErrorMessage(detail);
      }
    }

    handleExchange();

    return () => {
      isMounted = false;
    };
  }, [provider, searchParams, navigate, fetchCurrentUser, providerName]);

  return (
    <div className="min-h-screen bg-background text-foreground flex flex-col justify-center items-center p-4 selection:bg-zinc-800 selection:text-white">
      <div className="w-full max-w-md p-8 rounded-2xl bg-surface border border-border shadow-2xl text-center space-y-6 animate-scale-in">
        {/* Brand Header */}
        <div className="flex flex-col items-center justify-center">
          <Link to="/" className="inline-block hover:scale-105 transition-transform">
            <SprintForgeLogo variant="stacked" size="xl" />
          </Link>
        </div>

        {/* Status Views */}
        {status === 'exchanging' && (
          <div className="space-y-4 py-4">
            <div className="relative w-12 h-12 mx-auto flex items-center justify-center">
              <div className="absolute inset-0 rounded-full border-2 border-border border-t-foreground animate-spin" />
              <Sparkles className="w-5 h-5 text-foreground animate-pulse" />
            </div>
            <div>
              <h3 className="font-semibold text-foreground text-sm">
                Authenticating with {providerName}
              </h3>
              <p className="text-xs text-muted-foreground mt-1">
                Validating identity claims & synchronizing developer profile...
              </p>
            </div>
          </div>
        )}

        {status === 'success' && (
          <div className="space-y-4 py-4 animate-in fade-in duration-200">
            <div className="w-12 h-12 rounded-full bg-emerald-500/15 border border-emerald-500/30 text-emerald-500 mx-auto flex items-center justify-center shadow-xs">
              <CheckCircle2 className="w-6 h-6" />
            </div>
            <div>
              <h3 className="font-semibold text-foreground text-sm">
                Welcome to SprintForge!
              </h3>
              <p className="text-xs text-muted-foreground mt-1">
                Identity verified. Entering workspace...
              </p>
            </div>
          </div>
        )}

        {status === 'error' && (
          <div className="space-y-4 py-2 animate-in fade-in duration-200">
            <div className="w-12 h-12 rounded-full bg-red-500/15 border border-red-500/30 text-red-500 mx-auto flex items-center justify-center shadow-xs">
              <AlertCircle className="w-6 h-6" />
            </div>
            <div>
              <h3 className="font-semibold text-foreground text-sm">
                Authentication Failed
              </h3>
              <p className="text-xs text-muted-foreground mt-1 px-4 leading-relaxed">
                {errorMessage}
              </p>
            </div>
            <div className="pt-2 flex flex-col gap-2">
              <Link to="/login">
                <Button className="w-full text-xs font-semibold bg-foreground text-background hover:opacity-90">
                  Return to Sign In
                </Button>
              </Link>
              <Link to="/signup">
                <Button variant="outline" className="w-full text-xs font-medium">
                  Create an Account
                </Button>
              </Link>
            </div>
          </div>
        )}

        <div className="pt-2 border-t border-border/60 flex items-center justify-center gap-1.5 text-[11px] text-muted-foreground">
          <ShieldCheck className="w-3.5 h-3.5 text-muted-foreground" />
          <span>Stateless JWT & TLS 1.3 End-to-End Encrypted</span>
        </div>
      </div>
    </div>
  );
}

export default OAuthCallbackPage;
