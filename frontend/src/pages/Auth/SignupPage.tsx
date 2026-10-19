import React, { useState, useEffect } from 'react';
import { useNavigate, useSearchParams, Link } from 'react-router-dom';
import { useAuthStore } from '../../store/useAuthStore';
import { AmbientParticleMesh } from '../../components/three/AmbientParticleMesh';
import { Button } from '../../components/ui/Button';
import {
  ArrowRight,
  AlertCircle,
  Shield,
  KeyRound,
  Mail,
  User,
  CheckCircle2,
  RotateCw,
  ArrowLeft,
} from 'lucide-react';
import { motion, AnimatePresence } from 'framer-motion';
import { OAuthButtons } from '../../components/auth/OAuthButtons';
import { SprintForgeLogo } from '../../components/brand/SprintForgeLogo';

export function SignupPage() {
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const { signup, verifyEmail, resendVerification, isLoading } = useAuthStore();

  const [step, setStep] = useState<'form' | 'verify'>(
    searchParams.get('step') === 'verify' ? 'verify' : 'form'
  );
  const [username, setUsername] = useState('');
  const [email, setEmail] = useState(searchParams.get('email') || '');
  const [password, setPassword] = useState('');
  const [verificationCode, setVerificationCode] = useState('');

  const [error, setError] = useState<string | null>(null);
  const [emailAlreadyUsed, setEmailAlreadyUsed] = useState(false);
  const [resendCooldown, setResendCooldown] = useState(0);
  const [resendSuccess, setResendSuccess] = useState<string | null>(null);
  const [isResending, setIsResending] = useState(false);

  // Countdown timer for resend verification code
  useEffect(() => {
    if (resendCooldown <= 0) return;
    const timer = setInterval(() => {
      setResendCooldown((prev) => prev - 1);
    }, 1000);
    return () => clearInterval(timer);
  }, [resendCooldown]);

  const handleSignupSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError(null);
    setEmailAlreadyUsed(false);

    if (!username.trim() || !email.trim() || !password) {
      setError('Please provide username, valid email, and password.');
      return;
    }
    if (username.trim().length < 3) {
      setError('Username must be at least 3 characters long.');
      return;
    }
    if (username.trim().length > 50) {
      setError('Username must not exceed 50 characters.');
      return;
    }
    if (password.length < 6) {
      setError('Password must be at least 6 characters long.');
      return;
    }
    if (password.length > 40) {
      setError('Password must not exceed 40 characters.');
      return;
    }

    try {
      const res = await signup({
        username: username.trim(),
        email: email.trim().toLowerCase(),
        password,
      });

      if (res.verificationRequired) {
        setStep('verify');
        setResendCooldown(60);
      } else {
        navigate('/projects');
      }
    } catch (err: any) {
      const data = err.response?.data;
      const validationMsgs = data?.validationErrors
        ? Object.values(data.validationErrors).join('. ')
        : null;
      const errorListMsgs = Array.isArray(data?.errors)
        ? data.errors.map((e: any) => e.message).filter(Boolean).join('. ')
        : null;
      const rawMsg =
        validationMsgs ||
        errorListMsgs ||
        data?.message ||
        data?.error ||
        err.message ||
        'Registration failed.';

      // Detect if email already exists
      if (
        rawMsg.toLowerCase().includes('already in use') ||
        rawMsg.toLowerCase().includes('already registered') ||
        rawMsg.toLowerCase().includes('email is already') ||
        err.response?.status === 409
      ) {
        setEmailAlreadyUsed(true);
        setError('This email address is already registered. Please log in with your existing account.');
      } else {
        setError(rawMsg);
      }
    }
  };

  const handleVerifySubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError(null);
    setResendSuccess(null);

    const cleanCode = verificationCode.trim();
    if (!cleanCode || cleanCode.length < 6) {
      setError('Please enter the 6-digit verification code sent to your email.');
      return;
    }

    try {
      await verifyEmail({
        email: email.trim().toLowerCase(),
        code: cleanCode,
      });
      navigate('/projects');
    } catch (err: any) {
      const data = err.response?.data;
      const msg =
        data?.message ||
        data?.error ||
        err.message ||
        'Verification failed. Please check the code and try again.';
      setError(msg);
    }
  };

  const handleResendCode = async () => {
    if (resendCooldown > 0 || isResending) return;
    setIsResending(true);
    setError(null);
    setResendSuccess(null);

    try {
      await resendVerification(email.trim().toLowerCase());
      setResendSuccess(`A new 6-digit code has been sent to ${email}`);
      setResendCooldown(60);
    } catch (err: any) {
      const data = err.response?.data;
      setError(data?.message || err.message || 'Failed to resend verification email.');
    } finally {
      setIsResending(false);
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
        className="relative z-10 w-full max-w-[420px] rounded-xl border border-border/80 bg-surface/90 backdrop-blur-xl p-8 shadow-2xl"
      >
        <AnimatePresence mode="wait">
          {step === 'form' ? (
            <motion.div
              key="signup-form"
              initial={{ opacity: 0, x: -10 }}
              animate={{ opacity: 1, x: 0 }}
              exit={{ opacity: 0, x: 10 }}
              transition={{ duration: 0.2 }}
            >
              {/* Brand Header */}
              <div className="flex flex-col items-center text-center mb-6">
                <Link to="/" className="inline-block mb-3 hover:scale-105 transition-transform">
                  <SprintForgeLogo variant="badge" size="lg" />
                </Link>
                <h1 className="text-xl font-semibold tracking-tight text-foreground">
                  Create your SprintForge account
                </h1>
                <p className="text-xs text-muted-foreground mt-1">
                  Build software with high-velocity issue tracking and governance
                </p>
              </div>

              {/* Already Registered Email Warning Banner with direct Login action */}
              {emailAlreadyUsed ? (
                <div className="mb-4 p-3.5 rounded-lg bg-amber-500/10 border border-amber-500/25 text-amber-300 text-xs space-y-2.5 animate-in fade-in">
                  <div className="flex items-start gap-2">
                    <AlertCircle className="w-4 h-4 shrink-0 mt-0.5 text-amber-400" />
                    <div>
                      <span className="font-semibold text-amber-200">Account Already Exists</span>
                      <p className="text-muted-foreground mt-0.5 leading-relaxed">
                        The email <strong className="text-foreground">{email}</strong> is already registered. Please sign in with your credentials instead of registering.
                      </p>
                    </div>
                  </div>
                  <Button
                    type="button"
                    variant="primary"
                    onClick={() => navigate(`/login?email=${encodeURIComponent(email)}`)}
                    className="w-full h-8 text-xs font-semibold bg-amber-500 hover:bg-amber-400 text-black flex items-center justify-center gap-1.5 cursor-pointer shadow-xs"
                  >
                    <span>Go to Sign In</span>
                    <ArrowRight className="w-3.5 h-3.5" />
                  </Button>
                </div>
              ) : error ? (
                <div className="mb-4 p-3 rounded-md bg-rose-500/10 border border-rose-500/20 text-rose-400 text-xs flex items-start gap-2 animate-in fade-in">
                  <AlertCircle className="w-4 h-4 shrink-0 mt-0.5 text-rose-500" />
                  <span className="leading-relaxed">{error}</span>
                </div>
              ) : null}

              <form onSubmit={handleSignupSubmit} className="space-y-3.5">
                <div>
                  <label className="block text-xs font-medium text-muted-foreground mb-1">
                    Username
                  </label>
                  <div className="relative">
                    <User className="w-4 h-4 text-muted-foreground/60 absolute left-3 top-2.5" />
                    <input
                      type="text"
                      value={username}
                      onChange={(e) => setUsername(e.target.value)}
                      placeholder="vshanbhag"
                      required
                      autoFocus
                      className="w-full h-9 pl-9 pr-3 rounded-md border border-border/80 bg-background/60 text-xs text-foreground placeholder:text-muted-foreground/50 focus:outline-none focus:ring-1 focus:ring-foreground focus:border-foreground transition-all"
                    />
                  </div>
                </div>

                <div>
                  <label className="block text-xs font-medium text-muted-foreground mb-1">
                    Work Email
                  </label>
                  <div className="relative">
                    <Mail className="w-4 h-4 text-muted-foreground/60 absolute left-3 top-2.5" />
                    <input
                      type="email"
                      value={email}
                      onChange={(e) => {
                        setEmail(e.target.value);
                        setEmailAlreadyUsed(false);
                      }}
                      placeholder="developer@acme.corp"
                      required
                      className="w-full h-9 pl-9 pr-3 rounded-md border border-border/80 bg-background/60 text-xs text-foreground placeholder:text-muted-foreground/50 focus:outline-none focus:ring-1 focus:ring-foreground focus:border-foreground transition-all"
                    />
                  </div>
                </div>

                <div>
                  <label className="block text-xs font-medium text-muted-foreground mb-1">
                    Password
                  </label>
                  <div className="relative">
                    <KeyRound className="w-4 h-4 text-muted-foreground/60 absolute left-3 top-2.5" />
                    <input
                      type="password"
                      value={password}
                      onChange={(e) => setPassword(e.target.value)}
                      placeholder="At least 6 characters"
                      required
                      className="w-full h-9 pl-9 pr-3 rounded-md border border-border/80 bg-background/60 text-xs text-foreground placeholder:text-muted-foreground/50 focus:outline-none focus:ring-1 focus:ring-foreground focus:border-foreground transition-all"
                    />
                  </div>
                </div>

                <Button
                  type="submit"
                  variant="primary"
                  disabled={isLoading}
                  className="w-full h-9 mt-2 text-xs font-medium cursor-pointer"
                >
                  {isLoading ? 'Sending verification...' : 'Continue with Email'}
                  <ArrowRight className="w-3.5 h-3.5 ml-1.5" />
                </Button>
              </form>

              <OAuthButtons mode="signup" />

              <div className="mt-6 pt-5 border-t border-border/50 text-center text-xs text-muted-foreground">
                Already have an account?{' '}
                <Link
                  to="/login"
                  className="text-foreground hover:underline font-semibold"
                >
                  Sign in
                </Link>
              </div>
            </motion.div>
          ) : (
            <motion.div
              key="verify-form"
              initial={{ opacity: 0, x: 10 }}
              animate={{ opacity: 1, x: 0 }}
              exit={{ opacity: 0, x: -10 }}
              transition={{ duration: 0.2 }}
            >
              {/* Verification Header */}
              <div className="flex flex-col items-center text-center mb-6">
                <div className="w-12 h-12 rounded-xl bg-foreground/10 text-foreground border border-border flex items-center justify-center mb-3 shadow-inner">
                  <Mail className="w-6 h-6 text-foreground animate-pulse" />
                </div>
                <h1 className="text-xl font-semibold tracking-tight text-foreground">
                  Verify your email
                </h1>
                <p className="text-xs text-muted-foreground mt-1 px-4 leading-relaxed">
                  We've sent a 6-digit verification code to{' '}
                  <strong className="text-foreground">{email}</strong>. Enter it below to activate your account.
                </p>
              </div>

              {resendSuccess && (
                <div className="mb-4 p-3 rounded-md bg-emerald-500/10 border border-emerald-500/20 text-emerald-400 text-xs flex items-center gap-2 animate-in fade-in">
                  <CheckCircle2 className="w-4 h-4 shrink-0 text-emerald-500" />
                  <span>{resendSuccess}</span>
                </div>
              )}

              {error && (
                <div className="mb-4 p-3 rounded-md bg-rose-500/10 border border-rose-500/20 text-rose-400 text-xs flex items-start gap-2 animate-in fade-in">
                  <AlertCircle className="w-4 h-4 shrink-0 mt-0.5 text-rose-500" />
                  <span className="leading-relaxed">{error}</span>
                </div>
              )}

              <form onSubmit={handleVerifySubmit} className="space-y-4">
                <div>
                  <label className="block text-xs font-medium text-center text-muted-foreground mb-2">
                    6-Digit Verification Code
                  </label>
                  <input
                    type="text"
                    inputMode="numeric"
                    pattern="[0-9]*"
                    maxLength={6}
                    value={verificationCode}
                    onChange={(e) => setVerificationCode(e.target.value.replace(/\D/g, ''))}
                    placeholder="••••••"
                    autoFocus
                    required
                    className="w-full h-12 rounded-lg border border-border/90 bg-background/70 text-center font-mono text-2xl font-bold tracking-[10px] text-foreground placeholder:text-muted-foreground/30 focus:outline-none focus:ring-1 focus:ring-foreground focus:border-foreground transition-all"
                  />
                </div>

                <Button
                  type="submit"
                  variant="primary"
                  disabled={isLoading || verificationCode.length !== 6}
                  className="w-full h-9 text-xs font-semibold cursor-pointer"
                >
                  {isLoading ? 'Verifying identity...' : 'Verify & Enter Workspace'}
                  <ArrowRight className="w-3.5 h-3.5 ml-1.5" />
                </Button>
              </form>

              {/* Resend & Back Actions */}
              <div className="mt-5 pt-4 border-t border-border/50 flex flex-col items-center gap-2.5 text-xs text-muted-foreground">
                <button
                  type="button"
                  onClick={handleResendCode}
                  disabled={resendCooldown > 0 || isResending}
                  className="flex items-center gap-1.5 text-foreground hover:underline font-medium cursor-pointer disabled:opacity-40 disabled:hover:no-underline"
                >
                  <RotateCw className={`w-3.5 h-3.5 ${isResending ? 'animate-spin' : ''}`} />
                  {resendCooldown > 0
                    ? `Resend code in ${resendCooldown}s`
                    : 'Didn\'t receive code? Resend email'}
                </button>

                <button
                  type="button"
                  onClick={() => {
                    setStep('form');
                    setError(null);
                    setResendSuccess(null);
                  }}
                  className="flex items-center gap-1 text-[11px] text-muted-foreground hover:text-foreground transition-colors cursor-pointer mt-1"
                >
                  <ArrowLeft className="w-3 h-3" />
                  <span>Change email address</span>
                </button>
              </div>
            </motion.div>
          )}
        </AnimatePresence>
      </motion.div>
    </div>
  );
}

export default SignupPage;
