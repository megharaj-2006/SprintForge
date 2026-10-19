import React from 'react';
import { Link } from 'react-router-dom';
import {
  Zap,
  Command,
  ShieldCheck,
  Cpu,
  ArrowRight,
  Sparkles,
  Heart,
  Globe,
  Terminal,
} from 'lucide-react';
import { Button } from '../../components/ui/Button';
import { SpotlightCard } from '../../components/marketing/SpotlightCard';
import { ScrollReveal } from '../../components/marketing/ScrollReveal';
import { SprintForgeLogo } from '../../components/brand/SprintForgeLogo';

export function AboutPage() {
  const principles = [
    {
      num: '01',
      title: 'Speed is the Foundation',
      desc: 'Latency destroys focus. We optimize every client transition, database index, and network payload for sub-millisecond dispatch. In SprintForge, your thoughts never wait for a spinner.',
      icon: <Zap className="w-5 h-5 text-foreground" />,
    },
    {
      num: '02',
      title: 'Keyboard-First Ergonomics',
      desc: 'Professional software should feel like a musical instrument or a precision terminal. Every action, dialog, search, and navigation is accessible through intuitive, single-stroke keyboard shortcuts.',
      icon: <Command className="w-5 h-5 text-foreground" />,
    },
    {
      num: '03',
      title: 'Autonomous Intelligence, Not Gimmicks',
      desc: 'We reject frivolous chat wrappers. Our AI Copilot operates autonomously in the background—analyzing cycle bottlenecks, drafting integration tests, and predicting sprint burndowns.',
      icon: <Sparkles className="w-5 h-5 text-foreground" />,
    },
    {
      num: '04',
      title: 'Banking-Grade Architecture',
      desc: 'Enterprise trust cannot be retrofitted. We chose Java 21, Spring Boot, and PostgreSQL with strict relational ACID consistency and cryptographic hash chains from Day Zero.',
      icon: <ShieldCheck className="w-5 h-5 text-foreground" />,
    },
  ];

  return (
    <div className="py-20 px-4 sm:px-6 lg:px-8 max-w-7xl mx-auto">
      {/* Header */}
      <ScrollReveal className="text-center max-w-3xl mx-auto mb-20">
        <div className="flex justify-center mb-6">
          <SprintForgeLogo variant="badge" size="xl" badgeClassName="shadow-lg hover:scale-105" />
        </div>
        <span className="text-[10px] font-bold uppercase tracking-widest px-3 py-1 rounded-full bg-surface-raised border border-border text-foreground inline-block mb-4">
          Our Manifesto
        </span>
        <h1 className="text-4xl sm:text-6xl font-extrabold tracking-tight text-foreground leading-tight">
          Tools worthy of the engineers who build the future.
        </h1>
        <p className="mt-6 text-sm sm:text-base text-muted-foreground leading-relaxed">
          SprintForge was founded on a simple realization: the software that coordinates modern technological breakthroughs shouldn't be sluggish, bloated, or frustrating.
        </p>
      </ScrollReveal>

      {/* The Story / Manifesto */}
      <section className="max-w-4xl mx-auto mb-24 rounded-3xl border border-border/80 bg-surface/70 backdrop-blur-2xl p-8 sm:p-14 shadow-2xl">
        <h2 className="text-xl sm:text-2xl font-bold text-foreground mb-6">
          The Death of Sluggish Enterprise Software
        </h2>
        <div className="space-y-4 text-xs sm:text-sm text-muted-foreground leading-relaxed">
          <p>
            Over the past fifteen years, project management tools succumbed to creeping bloat. What began as simple issue tracking turned into labyrinthine enterprise suites filled with unreadable custom fields, slow dropdowns, five-second page loads, and endless context switching.
          </p>
          <p>
            Engineers stopped engaging with their tools. Important technical context was lost in Slack threads and private notes because creating an issue felt like filling out a tax form.
          </p>
          <p className="text-foreground font-medium">
            We built SprintForge to restore joy and velocity to software engineering.
          </p>
          <p>
            Every pixel, animation curve, keyboard keybinding, and database query in SprintForge was designed with extreme care. We pair the aesthetic refinement and keyboard fluidity of Linear with the uncompromising stability and transactional rigor of an enterprise Java 21 backend.
          </p>
        </div>
      </section>

      {/* 4 Foundational Principles */}
      <section className="mb-24">
        <ScrollReveal className="text-center max-w-3xl mx-auto mb-16">
          <span className="text-xs font-semibold uppercase tracking-wider text-muted-foreground block mb-2">
            Guiding Philosophy
          </span>
          <h2 className="text-3xl font-extrabold text-foreground">
            Our Four Engineering Principles
          </h2>
        </ScrollReveal>

        <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
          {principles.map((p) => (
            <ScrollReveal key={p.num} delay={0.1}>
              <SpotlightCard className="h-full p-8 flex flex-col justify-between">
                <div>
                  <div className="flex items-center justify-between mb-4">
                    <div className="h-10 w-10 rounded-xl bg-foreground text-background flex items-center justify-center font-bold">
                      {p.icon}
                    </div>
                    <span className="text-xl font-extrabold text-muted-foreground/30 font-mono">
                      {p.num}
                    </span>
                  </div>
                  <h3 className="text-base font-bold text-foreground mb-2">
                    {p.title}
                  </h3>
                  <p className="text-xs text-muted-foreground leading-relaxed">
                    {p.desc}
                  </p>
                </div>
              </SpotlightCard>
            </ScrollReveal>
          ))}
        </div>
      </section>

      {/* Global Presence & Infrastructure */}
      <section className="mb-24 rounded-2xl border border-border/80 bg-surface/50 p-8 sm:p-12 text-center">
        <div className="max-w-2xl mx-auto space-y-4">
          <Globe className="w-8 h-8 mx-auto text-foreground" />
          <h2 className="text-2xl font-bold text-foreground">
            Built for Distributed Global Engineering
          </h2>
          <p className="text-xs text-muted-foreground leading-relaxed">
            SprintForge operates across 14 distributed edge nodes worldwide with sub-20ms round-trip times. Engineered and maintained by systems architects across San Francisco, London, and Bengaluru.
          </p>
          <div className="pt-4 flex items-center justify-center gap-6 text-xs text-foreground font-semibold">
            <span>99.99% Availability SLA</span>
            <span>•</span>
            <span>SOC2 Type II Certified</span>
            <span>•</span>
            <span>Zero Tracking Telemetry</span>
          </div>
        </div>
      </section>

      {/* Bottom CTA */}
      <div className="text-center">
        <Link to="/signup">
          <Button variant="primary" size="lg" className="h-11 px-8 text-sm font-semibold" icon={<ArrowRight className="w-4 h-4 order-last" />}>
            Experience SprintForge Today
          </Button>
        </Link>
      </div>
    </div>
  );
}

export default AboutPage;
