import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import { ArrowRight, CheckCircle2, ShieldCheck, Terminal, Github, Twitter, Linkedin } from 'lucide-react';
import { Button } from '../ui/Button';
import { SprintForgeLogo } from '../brand/SprintForgeLogo';

export function MarketingFooter() {
  const [email, setEmail] = useState('');
  const [subscribed, setSubscribed] = useState(false);

  const handleSubscribe = (e: React.FormEvent) => {
    e.preventDefault();
    if (!email) return;
    setSubscribed(true);
    setEmail('');
    setTimeout(() => setSubscribed(false), 4000);
  };

  return (
    <footer className="border-t border-border/80 bg-surface/50 backdrop-blur-xl transition-colors">
      <div className="mx-auto max-w-7xl px-4 py-14 sm:px-6 lg:px-8">
        <div className="grid grid-cols-2 gap-8 md:grid-cols-5">
          {/* Col 1: Brand & Mission */}
          <div className="col-span-2">
            <Link to="/" className="inline-block select-none mb-3">
              <SprintForgeLogo variant="full" size="sm" />
            </Link>
            <p className="text-xs text-muted-foreground leading-relaxed max-w-sm mb-4">
              The precision project management platform designed for elite engineering squads. Ultra-dense keyboard-first ergonomics, autonomous AI copilot, and enterprise-grade governance.
            </p>

            {/* Newsletter input */}
            <form onSubmit={handleSubscribe} className="space-y-2 max-w-sm">
              <span className="text-xs font-semibold text-foreground block">
                Stay updated on platform releases
              </span>
              <div className="flex gap-2">
                <input
                  type="email"
                  value={email}
                  onChange={(e) => setEmail(e.target.value)}
                  placeholder="engineer@company.com"
                  className="w-full h-8 px-3 rounded-md border border-border bg-background text-xs placeholder:text-muted-foreground/50 focus:outline-none focus:ring-1 focus:ring-foreground"
                />
                <Button type="submit" variant="primary" size="sm" className="shrink-0">
                  {subscribed ? 'Subscribed' : 'Join'}
                </Button>
              </div>
              {subscribed && (
                <p className="text-[11px] text-emerald-400 flex items-center gap-1">
                  <CheckCircle2 className="w-3 h-3" /> You are on the release channel.
                </p>
              )}
            </form>
          </div>

          {/* Col 2: Product */}
          <div className="space-y-3">
            <h4 className="text-xs font-semibold uppercase tracking-wider text-foreground">
              Product
            </h4>
            <ul className="space-y-2 text-xs text-muted-foreground">
              <li>
                <Link to="/#features" className="hover:text-foreground transition-colors">
                  Issue Tracking
                </Link>
              </li>
              <li>
                <Link to="/#features" className="hover:text-foreground transition-colors">
                  Autonomous Copilot
                </Link>
              </li>
              <li>
                <Link to="/#features" className="hover:text-foreground transition-colors">
                  Visual Workflow Studio
                </Link>
              </li>
              <li>
                <Link to="/how-it-works" className="hover:text-foreground transition-colors">
                  How It Works
                </Link>
              </li>
              <li>
                <Link to="/pricing" className="hover:text-foreground transition-colors">
                  Pricing Plans
                </Link>
              </li>
            </ul>
          </div>

          {/* Col 3: Architecture */}
          <div className="space-y-3">
            <h4 className="text-xs font-semibold uppercase tracking-wider text-foreground">
              Architecture
            </h4>
            <ul className="space-y-2 text-xs text-muted-foreground">
              <li>
                <span className="text-foreground/80 font-medium">Java 21 / Spring Boot</span>
              </li>
              <li>
                <span className="text-foreground/80 font-medium">PostgreSQL ACID Storage</span>
              </li>
              <li>
                <span className="text-foreground/80 font-medium">Zero-Trust JWT Auth</span>
              </li>
              <li>
                <span className="text-foreground/80 font-medium">642 REST Endpoints</span>
              </li>
              <li>
                <Link to="/docs" className="hover:text-foreground transition-colors">
                  API Specifications
                </Link>
              </li>
            </ul>
          </div>

          {/* Col 4: Resources & Legal */}
          <div className="space-y-3">
            <h4 className="text-xs font-semibold uppercase tracking-wider text-foreground">
              Resources
            </h4>
            <ul className="space-y-2 text-xs text-muted-foreground">
              <li>
                <Link to="/docs" className="hover:text-foreground transition-colors">
                  Developer Documentation
                </Link>
              </li>
              <li>
                <Link to="/docs" className="hover:text-foreground transition-colors">
                  Keyboard Shortcuts
                </Link>
              </li>
              <li>
                <Link to="/about" className="hover:text-foreground transition-colors">
                  Company & Manifesto
                </Link>
              </li>
              <li>
                <Link to="/contact" className="hover:text-foreground transition-colors">
                  Enterprise Support
                </Link>
              </li>
              <li>
                <a
                  href="https://github.com/megharaj-2006/SprintForge"
                  target="_blank"
                  rel="noreferrer"
                  className="hover:text-foreground transition-colors flex items-center gap-1"
                >
                  GitHub Repository <ArrowRight className="w-3 h-3" />
                </a>
              </li>
            </ul>
          </div>
        </div>

        {/* Bottom Bar */}
        <div className="mt-12 pt-6 border-t border-border/60 flex flex-col sm:flex-row items-center justify-between gap-4 text-xs text-muted-foreground">
          <div className="flex items-center gap-3">
            <span>© {new Date().getFullYear()} SprintForge Systems, Inc.</span>
            <span>•</span>
            <span className="flex items-center gap-1.5 text-emerald-400">
              <span className="h-1.5 w-1.5 rounded-full bg-emerald-400 animate-pulse" />
              All Systems Operational (99.99% Uptime)
            </span>
          </div>

          <div className="flex items-center gap-4 text-muted-foreground">
            <span className="flex items-center gap-1 text-[11px] text-foreground font-medium">
              <ShieldCheck className="w-3.5 h-3.5 text-emerald-400" /> SOC2 Type II Ready
            </span>
            <span>•</span>
            <Link to="/about" className="hover:text-foreground transition-colors">
              Privacy & Security
            </Link>
          </div>
        </div>
      </div>
    </footer>
  );
}

export default MarketingFooter;
