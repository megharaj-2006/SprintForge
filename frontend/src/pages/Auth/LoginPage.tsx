import React, { useState } from 'react';
import { useNavigate, useSearchParams, Link } from 'react-router-dom';
import { useAuthStore } from '../../store/useAuthStore';
import { AmbientParticleMesh } from '../../components/three/AmbientParticleMesh';
import { Button } from '../../components/ui/Button';
import { ArrowRight, AlertCircle, Shield, KeyRound, Mail, CheckCircle2 } from 'lucide-react';
import { motion } from 'framer-motion';
import { OAuthButtons } from '../../components/auth/OAuthButtons';
import { SprintForgeLogo } from '../../components/brand/SprintForgeLogo';

export function LoginPage() {
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const emailParam = searchParams.get('email') || '';

  const { login, isLoading } = useAuthStore();
  const [usernameOrEmail, setUsernameOrEmail] = useState(emailParam);
  const [password, setPassword] = useState('');
  const [error, setError] = useState<string | null>(null);
  const [isUnverified, setIsUnverified] = useState(false);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError(null);
    if (!usernameOrEmail.trim() || !password) {
      setError('Please provide your username or email, and password.');
      return;
    }

    try {
      await login({ usernameOrEmail: usernameOrEmail.trim(), password });
      navigate('/projects');
    } catch (err: any) {
      const data = err.response?.data;
      const validationMsgs = data?.validationErrors
        ? Object.values(data.validationErrors).join('. ')
        : null;
      const errorListMsgs = Array.isArray(data?.errors)
        ? data.errors.map((e: any) => e.message).filter(Boolean).join('. ')
        : null;
      const msg =
        validationMsgs ||
        errorListMsgs ||
        data?.message ||
        data?.error ||
        err.message ||
        'Authentication failed. Please verify credentials.';

      if (msg.toLowerCase().includes('not verified') || msg.toLowerCase().includes('disabled')) {
        setIsUnverified(true);
        setError('Your email address is not verified yet. Please verify your email to log in.');
      } else {
        setIsUnverified(false);
        setError(msg);
      }
    }
  };

  return (
    <div className="relative min-h-screen w-full flex items-center justify-center bg-background px-4 overflow-hidden select-none">
      {/* 3D Ambient Particle Mesh Background */}
      <AmbientParticleMesh className="absolute inset-0 pointer-events-none opacity-50 dark:opacity-70" />

      {/* Decorative subtle ambient glow */}
      <div className="absolute top-1/2 left-1/2 -translate-x-1/2 -translate-y-1/2 w-[500px] h-[500px] bg-foreground/5 rounded-full blur-[140px] pointer-events-none" />

      <motion.div
        initial={{ opacity: 0, y: 12 }}
        animate={{ opacity: 1, y: 0 }}
        transition={{ duration: 0.24, ease: 'easeOut' }}
        className="relative z-10 w-full max-w-[400px] rounded-xl border border-border/80 bg-surface/90 backdrop-blur-xl p-8 shadow-2xl"
      >
        {/* SprintForge Brand Header */}
        <div className="flex flex-col items-center text-center mb-7">
          <Link to="/" className="inline-block mb-3 hover:scale-105 transition-transform">
            <SprintForgeLogo variant="badge" size="lg" />
          </Link>
          <h1 className="text-xl font-semibold tracking-tight text-foreground">
            Sign in to SprintForge
          </h1>
          <p className="text-xs text-muted-foreground mt-1">
            Linear-grade project engineering & issue tracking
          </p>
        </div>

        {isUnverified ? (
          <div className="mb-4 p-3.5 rounded-lg bg-amber-500/10 border border-amber-500/25 text-amber-300 text-xs space-y-2 animate-in fade-in">
            <div className="flex items-start gap-2">
              <AlertCircle className="w-4 h-4 shrink-0 mt-0.5 text-amber-400" />
              <div>
                <span className="font-semibold text-amber-200">Email Verification Required</span>
                <p className="text-muted-foreground mt-0.5 leading-relaxed">
                  Your email has not been verified yet. Please enter the verification code sent to your email.
                </p>
              </div>
            </div>
            <Button
              type="button"
              variant="primary"
              onClick={() =>
                navigate(
                  `/signup?email=${encodeURIComponent(usernameOrEmail.trim())}&step=verify`
                )
              }
              className="w-full h-8 text-xs font-semibold bg-amber-500 hover:bg-amber-400 text-black flex items-center justify-center gap-1.5 cursor-pointer shadow-xs"
            >
              <span>Verify Email Now</span>
              <ArrowRight className="w-3.5 h-3.5" />
            </Button>
          </div>
        ) : error ? (
          <div className="mb-4 p-3 rounded-md bg-rose-500/10 border border-rose-500/20 text-rose-400 text-xs flex items-start gap-2 animate-in fade-in">
            <AlertCircle className="w-4 h-4 shrink-0 mt-0.5 text-rose-500" />
            <span className="leading-relaxed">{error}</span>
          </div>
        ) : null}


        <form onSubmit={handleSubmit} className="space-y-4">
          <div>
            <label className="block text-xs font-medium text-muted-foreground mb-1.5">
              Username or Email
            </label>
            <div className="relative">
              <Mail className="w-4 h-4 text-muted-foreground/60 absolute left-3 top-2.5" />
              <input
                type="text"
                value={usernameOrEmail}
                onChange={(e) => setUsernameOrEmail(e.target.value)}
                placeholder="name@company.com or username"
                required
                autoFocus
                className="w-full h-9 pl-9 pr-3 rounded-md border border-border/80 bg-background/60 text-xs text-foreground placeholder:text-muted-foreground/50 focus:outline-none focus:ring-1 focus:ring-foreground focus:border-foreground transition-all"
              />
            </div>
          </div>

          <div>
            <div className="flex items-center justify-between mb-1.5">
              <label className="block text-xs font-medium text-muted-foreground">
                Password
              </label>
            </div>
            <div className="relative">
              <KeyRound className="w-4 h-4 text-muted-foreground/60 absolute left-3 top-2.5" />
              <input
                type="password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                placeholder="••••••••••••"
                required
                className="w-full h-9 pl-9 pr-3 rounded-md border border-border/80 bg-background/60 text-xs text-foreground placeholder:text-muted-foreground/50 focus:outline-none focus:ring-1 focus:ring-foreground focus:border-foreground transition-all"
              />
            </div>
          </div>

          {/* Quick Pre-seeded Access */}
          <div className="pt-1">
            <div className="flex items-center justify-between text-[11px] text-muted-foreground/80 mb-1.5">
              <span>Quick Fill:</span>
            </div>
            <div className="grid grid-cols-2 gap-2">
              <button
                type="button"
                onClick={() => {
                  setUsernameOrEmail('admin');
                  setPassword('Password123!');
                }}
                className="px-2.5 py-1.5 rounded-md border border-border/80 bg-background/50 hover:bg-surface hover:border-foreground/30 text-[11px] font-medium text-muted-foreground hover:text-foreground transition-all text-center cursor-pointer"
              >
                ⚡ Admin
              </button>
              <button
                type="button"
                onClick={() => {
                  setUsernameOrEmail('demo_user');
                  setPassword('Password123!');
                }}
                className="px-2.5 py-1.5 rounded-md border border-border/80 bg-background/50 hover:bg-surface hover:border-foreground/30 text-[11px] font-medium text-muted-foreground hover:text-foreground transition-all text-center cursor-pointer"
              >
                ⚡ Developer
              </button>
            </div>
          </div>

          <Button
            type="submit"
            variant="primary"
            disabled={isLoading}
            className="w-full h-9 mt-2 text-xs font-medium cursor-pointer"
          >
            {isLoading ? 'Signing in...' : 'Continue with Password'}
            <ArrowRight className="w-3.5 h-3.5 ml-1.5" />
          </Button>
        </form>

        <OAuthButtons mode="signin" />

        <div className="mt-6 pt-5 border-t border-border/50 text-center text-xs text-muted-foreground">
          Don't have an account?{' '}
          <Link
            to="/signup"
            className="text-foreground hover:underline font-semibold"
          >
            Create one
          </Link>
        </div>
      </motion.div>
    </div>
  );
}

export default LoginPage;
