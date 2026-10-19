import React, { useEffect, useState } from 'react';
import { authApi } from '../../api/endpoints';

interface OAuthButtonsProps {
  mode?: 'signin' | 'signup';
}

interface ProviderInfo {
  enabled: boolean;
  clientId: string;
  authorizeUrl: string;
  scope: string;
}

// Built-in fallback client IDs provided by user
const FALLBACK_CLIENT_IDS: Record<string, string> = {
  google: '753092667845-9sh5o48gsptd24fqdtmhu63a16tfeeb5.apps.googleusercontent.com',
  github: 'Ov23lixv5xlWAXxWdKGA',
  linkedin: '86pieiclgcdqnz',
  microsoft: 'd39b23c5-918b-40af-ad6d-cbc0677d8075',
};

export function OAuthButtons({ mode = 'signin' }: OAuthButtonsProps) {
  const [providers, setProviders] = useState<Record<string, ProviderInfo>>({});
  const [loadingProvider, setLoadingProvider] = useState<string | null>(null);

  useEffect(() => {
    let isMounted = true;
    async function loadConfig() {
      try {
        const dummyRedirect = `${window.location.origin}/auth/callback/google`;
        const res = await authApi.getOAuthPublicConfig(dummyRedirect);
        if (isMounted && res?.providers) {
          setProviders(res.providers);
        }
      } catch (err) {
        // Fallback gracefully to default client IDs if offline
      }
    }
    loadConfig();
    return () => {
      isMounted = false;
    };
  }, []);

  const handleOAuthLogin = (provider: 'google' | 'github' | 'linkedin' | 'microsoft') => {
    setLoadingProvider(provider);
    const redirectUri = `${window.location.origin}/auth/callback/${provider}`;
    const state = Math.random().toString(36).substring(2, 15) + Math.random().toString(36).substring(2, 15);
    sessionStorage.setItem(`oauth_state_${provider}`, state);

    const backendInfo = providers[provider];
    const clientId = backendInfo?.clientId || FALLBACK_CLIENT_IDS[provider];

    let targetUrl = '';

    switch (provider) {
      case 'google': {
        const params = new URLSearchParams({
          client_id: clientId,
          redirect_uri: redirectUri,
          response_type: 'code',
          scope: 'openid email profile',
          access_type: 'offline',
          prompt: 'consent',
          state,
        });
        targetUrl = `https://accounts.google.com/o/oauth2/v2/auth?${params.toString()}`;
        break;
      }
      case 'github': {
        const params = new URLSearchParams({
          client_id: clientId,
          redirect_uri: redirectUri,
          scope: 'read:user user:email',
          state,
        });
        targetUrl = `https://github.com/login/oauth/authorize?${params.toString()}`;
        break;
      }
      case 'linkedin': {
        const params = new URLSearchParams({
          client_id: clientId,
          redirect_uri: redirectUri,
          response_type: 'code',
          scope: 'openid profile email',
          state,
        });
        targetUrl = `https://www.linkedin.com/oauth/v2/authorization?${params.toString()}`;
        break;
      }
      case 'microsoft': {
        const params = new URLSearchParams({
          client_id: clientId,
          redirect_uri: redirectUri,
          response_type: 'code',
          scope: 'openid profile email User.Read offline_access',
          response_mode: 'query',
          state,
        });
        targetUrl = `https://login.microsoftonline.com/common/oauth2/v2.0/authorize?${params.toString()}`;
        break;
      }
    }

    if (targetUrl) {
      window.location.href = targetUrl;
    } else {
      setLoadingProvider(null);
    }
  };

  return (
    <div className="space-y-3">
      <div className="relative flex items-center justify-center my-3">
        <div className="absolute inset-0 flex items-center">
          <div className="w-full border-t border-border/80" />
        </div>
        <div className="relative bg-surface/90 px-2 text-[11px] uppercase tracking-wider text-muted-foreground/80 font-medium">
          Or continue with
        </div>
      </div>

      <div className="grid grid-cols-2 gap-2">
        {/* Google */}
        <button
          type="button"
          onClick={() => handleOAuthLogin('google')}
          disabled={loadingProvider !== null}
          className="flex items-center justify-center gap-2 h-9 px-3 rounded-md border border-border/80 bg-background/60 hover:bg-surface hover:border-foreground/40 text-xs font-medium text-foreground transition-all cursor-pointer disabled:opacity-50 group"
          title="Sign in with Google"
        >
          <svg className="w-4 h-4 shrink-0" viewBox="0 0 24 24">
            <path
              fill="#4285F4"
              d="M23.745 12.27c0-.7-.06-1.4-.19-2.07H12v4.51h6.6c-.29 1.52-1.14 2.82-2.4 3.68v3.05h3.88c2.27-2.09 3.665-5.17 3.665-9.17z"
            />
            <path
              fill="#34A853"
              d="M12 24c3.24 0 5.95-1.08 7.93-2.91l-3.88-3.05c-1.08.72-2.45 1.16-4.05 1.16-3.12 0-5.77-2.1-6.72-4.93H1.25v3.15C3.26 21.36 7.33 24 12 24z"
            />
            <path
              fill="#FBBC05"
              d="M5.28 14.27c-.25-.72-.38-1.49-.38-2.27s.13-1.55.38-2.27V6.58H1.25C.45 8.18 0 9.99 0 12s.45 3.82 1.25 5.42l4.03-3.15z"
            />
            <path
              fill="#EA4335"
              d="M12 4.75c1.77 0 3.35.61 4.6 1.8l3.42-3.42C17.95 1.19 15.24 0 12 0 7.33 0 3.26 2.64 1.25 6.58l4.03 3.15c.95-2.83 3.6-4.93 6.72-4.93z"
            />
          </svg>
          <span className="truncate">Google</span>
        </button>

        {/* GitHub */}
        <button
          type="button"
          onClick={() => handleOAuthLogin('github')}
          disabled={loadingProvider !== null}
          className="flex items-center justify-center gap-2 h-9 px-3 rounded-md border border-border/80 bg-background/60 hover:bg-surface hover:border-foreground/40 text-xs font-medium text-foreground transition-all cursor-pointer disabled:opacity-50 group"
          title="Sign in with GitHub"
        >
          <svg className="w-4 h-4 shrink-0 fill-current text-foreground" viewBox="0 0 24 24">
            <path fillRule="evenodd" clipRule="evenodd" d="M12 2C6.477 2 2 6.484 2 12.017c0 4.425 2.865 8.18 6.839 9.504.5.092.682-.217.682-.483 0-.237-.008-.868-.013-1.703-2.782.605-3.369-1.343-3.369-1.343-.454-1.158-1.11-1.466-1.11-1.466-.908-.62.069-.608.069-.608 1.003.07 1.53 1.032 1.53 1.032.892 1.53 2.341 1.088 2.91.832.092-.647.35-1.088.636-1.338-2.22-.253-4.555-1.113-4.555-4.951 0-1.093.39-1.988 1.029-2.688-.103-.253-.446-1.272.098-2.65 0 0 .84-.27 2.75 1.026A9.564 9.564 0 0112 6.844c.85.004 1.705.115 2.504.337 1.909-1.296 2.747-1.027 2.747-1.027.546 1.379.202 2.398.1 2.651.64.7 1.028 1.595 1.028 2.688 0 3.848-2.339 4.695-4.566 4.943.359.309.678.92.678 1.855 0 1.338-.012 2.419-.012 2.747 0 .268.18.58.688.482A10.019 10.019 0 0022 12.017C22 6.484 17.522 2 12 2z" />
          </svg>
          <span className="truncate">GitHub</span>
        </button>

        {/* LinkedIn */}
        <button
          type="button"
          onClick={() => handleOAuthLogin('linkedin')}
          disabled={loadingProvider !== null}
          className="flex items-center justify-center gap-2 h-9 px-3 rounded-md border border-border/80 bg-background/60 hover:bg-surface hover:border-foreground/40 text-xs font-medium text-foreground transition-all cursor-pointer disabled:opacity-50 group"
          title="Sign in with LinkedIn"
        >
          <svg className="w-4 h-4 shrink-0 fill-[#0A66C2]" viewBox="0 0 24 24">
            <path d="M19 3a2 2 0 0 1 2 2v14a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h14m-.5 15.5v-5.3a3.26 3.26 0 0 0-3.26-3.26c-.85 0-1.84.52-2.28 1.3v-1.11h-2.79v8.37h2.79v-4.93c0-.77.62-1.4 1.39-1.4a1.4 1.4 0 0 1 1.4 1.4v4.93h2.75M6.46 8.76a1.53 1.53 0 0 0 1.54-1.53 1.54 1.54 0 1 0-1.54 1.53m1.39 9.74v-8.37H5.07v8.37h2.78z" />
          </svg>
          <span className="truncate">LinkedIn</span>
        </button>

        {/* Microsoft */}
        <button
          type="button"
          onClick={() => handleOAuthLogin('microsoft')}
          disabled={loadingProvider !== null}
          className="flex items-center justify-center gap-2 h-9 px-3 rounded-md border border-border/80 bg-background/60 hover:bg-surface hover:border-foreground/40 text-xs font-medium text-foreground transition-all cursor-pointer disabled:opacity-50 group"
          title="Sign in with Microsoft"
        >
          <svg className="w-4 h-4 shrink-0" viewBox="0 0 23 23">
            <path fill="#f35325" d="M1 1h10v10H1z" />
            <path fill="#81bc06" d="M12 1h10v10H12z" />
            <path fill="#05a6f0" d="M1 12h10v10H1z" />
            <path fill="#ffba08" d="M12 12h10v10H12z" />
          </svg>
          <span className="truncate">Microsoft</span>
        </button>
      </div>

      {loadingProvider && (
        <div className="text-center text-[11px] text-muted-foreground animate-pulse">
          Connecting to {loadingProvider.charAt(0).toUpperCase() + loadingProvider.slice(1)}...
        </div>
      )}
    </div>
  );
}

export default OAuthButtons;
