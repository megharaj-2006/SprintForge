import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import {
  Check,
  HelpCircle,
  ChevronDown,
  ArrowRight,
  ShieldCheck,
  Zap,
  Sparkles,
} from 'lucide-react';
import { Button } from '../../components/ui/Button';
import { SpotlightCard } from '../../components/marketing/SpotlightCard';
import { ScrollReveal } from '../../components/marketing/ScrollReveal';

export function PricingPage() {
  const [annualBilling, setAnnualBilling] = useState(true);
  const [openFaq, setOpenFaq] = useState<number | null>(null);

  const toggleFaq = (index: number) => {
    setOpenFaq(openFaq === index ? null : index);
  };

  const plans = [
    {
      name: 'Developer',
      tagline: 'For individual developers and open source contributors.',
      priceMonthly: '$0',
      priceAnnual: '$0',
      period: 'forever',
      ctaText: 'Start Free Forever',
      ctaLink: '/signup?plan=developer',
      variant: 'outline' as const,
      popular: false,
      features: [
        'Unlimited issues and personal boards',
        'Full keyboard shortcut suite (Cmd+K, C)',
        'Core Kanban and backlog triage',
        'Standard Markdown and code syntax styling',
        'Access to 642 REST endpoints',
        'Community Discord & Forum support',
      ],
    },
    {
      name: 'Pro',
      tagline: 'For high-velocity engineering squads building fast.',
      priceMonthly: '$18',
      priceAnnual: '$14',
      period: 'per user / month',
      ctaText: 'Start 14-Day Free Trial',
      ctaLink: '/signup?plan=pro',
      variant: 'primary' as const,
      popular: true,
      features: [
        'Everything in Developer, plus:',
        'Synchronized 2-week automated cycles',
        'Autonomous AI Copilot (Cmd+J)',
        'Visual Workflow Studio (DAG engine)',
        'Strategic Company OKRs alignment',
        'GitHub, GitLab & Slack webhooks',
        'Advanced team burndown and cycle analytics',
        'Priority email support (< 2 hr response)',
      ],
    },
    {
      name: 'Enterprise',
      tagline: 'For security-conscious organizations requiring strict compliance.',
      priceMonthly: '$48',
      priceAnnual: '$39',
      period: 'per user / month',
      ctaText: 'Contact Enterprise Sales',
      ctaLink: '/contact?plan=enterprise',
      variant: 'outline' as const,
      popular: false,
      features: [
        'Everything in Pro, plus:',
        'SAML 2.0 / SSO (Okta, Azure AD, Google)',
        'Cryptographic SHA-256 Audit Ledger',
        'Quantitative 5x5 Risk Matrix & ISO27001 readiness',
        '99.99% Uptime SLA guarantee',
        'Dedicated Technical Account Architect',
        'Custom data residency & VPC deployment',
        '1-Click Jira / Linear migration assistant',
      ],
    },
  ];

  const faqs = [
    {
      q: 'Can I switch between plans at any time?',
      a: 'Yes. You can upgrade, downgrade, or cancel your subscription at any point from your Workspace Settings. Prorated credits are automatically calculated and applied to future billing cycles.',
    },
    {
      q: 'How does the 14-day free trial for the Pro plan work?',
      a: 'When you start a Pro trial, your team receives full, unrestricted access to all Pro features including the Autonomous AI Copilot and Visual Workflow Studio for 14 days without requiring credit card details upfront.',
    },
    {
      q: 'Do you offer discounts for educational institutions and open source projects?',
      a: 'Yes. We provide 100% free Pro subscriptions to verified open source projects and accredited academic research laboratories. Contact our team to apply.',
    },
    {
      q: 'How secure is SprintForge and where is customer data stored?',
      a: 'All data is encrypted in transit using TLS 1.3 and at rest using AES-256. We maintain strict row-level workspace tenant isolation on top of managed PostgreSQL clusters. Enterprise customers can designate specific AWS, GCP, or EU cloud regions.',
    },
    {
      q: 'Can I import my existing issues from Jira or Linear?',
      a: 'Yes. SprintForge provides single-click importers that ingest your epics, tasks, subtasks, custom labels, assignees, and historical attachments in less than 2 minutes.',
    },
    {
      q: 'What kind of support is included with the Enterprise plan?',
      a: 'Enterprise plans include a dedicated Technical Account Architect, 24/7 emergency incident coverage with a sub-15 minute response time SLA, custom deployment assistance, and quarterly architecture reviews.',
    },
  ];

  return (
    <div className="py-20 px-4 sm:px-6 lg:px-8 max-w-7xl mx-auto">
      {/* Header */}
      <ScrollReveal className="text-center max-w-3xl mx-auto mb-16">
        <span className="text-[10px] font-bold uppercase tracking-widest px-3 py-1 rounded-full bg-foreground text-background inline-block mb-4">
          Transparent Pricing
        </span>
        <h1 className="text-4xl sm:text-6xl font-extrabold tracking-tight text-foreground leading-tight">
          Simple plans. <br />
          Built for teams of every scale.
        </h1>
        <p className="mt-4 text-sm sm:text-base text-muted-foreground leading-relaxed">
          Start free forever as an individual. Upgrade to Pro or Enterprise as your engineering organization accelerates.
        </p>

        {/* Billing Toggle */}
        <div className="mt-10 inline-flex items-center gap-3 p-1 rounded-full border border-border/80 bg-surface/80 backdrop-blur-md">
          <button
            onClick={() => setAnnualBilling(false)}
            className={`px-4 py-1.5 rounded-full text-xs font-semibold transition-all ${
              !annualBilling
                ? 'bg-foreground text-background shadow-sm'
                : 'text-muted-foreground hover:text-foreground'
            }`}
          >
            Monthly Billing
          </button>
          <button
            onClick={() => setAnnualBilling(true)}
            className={`px-4 py-1.5 rounded-full text-xs font-semibold transition-all flex items-center gap-1.5 ${
              annualBilling
                ? 'bg-foreground text-background shadow-sm'
                : 'text-muted-foreground hover:text-foreground'
            }`}
          >
            <span>Annual Billing</span>
            <span className="px-1.5 py-0.2 rounded-full text-[10px] bg-emerald-500/15 text-emerald-400 font-bold border border-emerald-500/20">
              Save 20%
            </span>
          </button>
        </div>
      </ScrollReveal>

      {/* Pricing Cards */}
      <div className="grid grid-cols-1 lg:grid-cols-3 gap-8 mb-24">
        {plans.map((plan, idx) => (
          <ScrollReveal key={plan.name} delay={idx * 0.15}>
            <SpotlightCard
              className={`h-full flex flex-col justify-between ${
                plan.popular
                  ? 'border-foreground shadow-2xl ring-1 ring-foreground relative'
                  : 'border-border/80'
              }`}
            >
              {plan.popular && (
                <div className="absolute top-0 right-6 -translate-y-1/2">
                  <span className="text-[10px] font-bold uppercase tracking-wider px-3 py-1 rounded-full bg-foreground text-background shadow-md">
                    Most Popular
                  </span>
                </div>
              )}

              <div>
                <div className="flex items-center justify-between mb-2">
                  <h3 className="text-xl font-bold text-foreground">{plan.name}</h3>
                  {plan.popular && <Sparkles className="w-4 h-4 text-foreground" />}
                </div>
                <p className="text-xs text-muted-foreground leading-relaxed min-h-[32px] mb-6">
                  {plan.tagline}
                </p>

                {/* Price Display */}
                <div className="flex items-baseline gap-1.5 mb-6 pb-6 border-b border-border/60">
                  <span className="text-4xl font-extrabold text-foreground tabular-nums">
                    {annualBilling ? plan.priceAnnual : plan.priceMonthly}
                  </span>
                  <span className="text-xs text-muted-foreground">/ {plan.period}</span>
                </div>

                {/* Feature List */}
                <div className="space-y-3 mb-8">
                  <span className="text-xs font-semibold text-foreground uppercase tracking-wider block">
                    What's included:
                  </span>
                  {plan.features.map((feat, fIdx) => (
                    <div key={fIdx} className="flex items-start gap-2.5 text-xs text-muted-foreground">
                      <Check className="w-4 h-4 text-foreground shrink-0 mt-0.5" />
                      <span className="leading-relaxed">{feat}</span>
                    </div>
                  ))}
                </div>
              </div>

              {/* Action Button */}
              <Link to={plan.ctaLink} className="w-full block">
                <Button
                  variant={plan.variant}
                  size="md"
                  className="w-full h-10 font-semibold"
                  icon={<ArrowRight className="w-4 h-4 order-last" />}
                >
                  {plan.ctaText}
                </Button>
              </Link>
            </SpotlightCard>
          </ScrollReveal>
        ))}
      </div>

      {/* Feature Comparison Checklist */}
      <section className="mb-24 pt-16 border-t border-border/60">
        <ScrollReveal className="text-center max-w-3xl mx-auto mb-12">
          <h2 className="text-2xl sm:text-3xl font-extrabold text-foreground">
            Detailed Feature Breakdown
          </h2>
          <p className="text-xs text-muted-foreground mt-2">
            Compare capabilities across every SprintForge plan.
          </p>
        </ScrollReveal>

        <div className="overflow-x-auto rounded-2xl border border-border/80 bg-surface/70 backdrop-blur-xl shadow-xl">
          <table className="w-full text-left text-xs border-collapse">
            <thead>
              <tr className="border-b border-border bg-surface-hover/50 text-muted-foreground font-semibold">
                <th className="px-6 py-4">Capability</th>
                <th className="px-6 py-4 text-center">Developer</th>
                <th className="px-6 py-4 text-center bg-foreground/5 text-foreground font-bold">Pro</th>
                <th className="px-6 py-4 text-center">Enterprise</th>
              </tr>
            </thead>
            <tbody className="divide-y divide-border/60">
              {[
                { feature: 'Keyboard Omnisearch (Cmd+K)', dev: true, pro: true, ent: true },
                { feature: 'Unlimited Issues & Projects', dev: true, pro: true, ent: true },
                { feature: 'REST API & Webhooks Access', dev: 'Public', pro: 'Full Access', ent: 'Custom Endpoints' },
                { feature: 'Bi-Weekly Cycles & Burndown', dev: false, pro: true, ent: true },
                { feature: 'Autonomous AI Copilot (Cmd+J)', dev: false, pro: true, ent: true },
                { feature: 'Visual Workflow Studio (DAG)', dev: false, pro: true, ent: true },
                { feature: 'Strategic Company OKRs', dev: false, pro: true, ent: true },
                { feature: 'SAML 2.0 / Okta SSO', dev: false, pro: false, ent: true },
                { feature: 'Cryptographic SHA-256 Audit Trail', dev: false, pro: false, ent: true },
                { feature: '5x5 Risk Severity Matrix', dev: false, pro: false, ent: true },
                { feature: '99.99% Guaranteed SLA', dev: false, pro: false, ent: true },
              ].map((row, idx) => (
                <tr key={idx} className="hover:bg-surface-hover/40 transition-colors">
                  <td className="px-6 py-3.5 font-medium text-foreground">{row.feature}</td>
                  <td className="px-6 py-3.5 text-center">
                    {typeof row.dev === 'boolean' ? (
                      row.dev ? <Check className="w-4 h-4 mx-auto text-foreground" /> : <span className="text-muted-foreground/40">—</span>
                    ) : (
                      <span className="text-muted-foreground font-medium">{row.dev}</span>
                    )}
                  </td>
                  <td className="px-6 py-3.5 text-center bg-foreground/5">
                    {typeof row.pro === 'boolean' ? (
                      row.pro ? <Check className="w-4 h-4 mx-auto text-foreground font-bold" /> : <span className="text-muted-foreground/40">—</span>
                    ) : (
                      <span className="text-foreground font-semibold">{row.pro}</span>
                    )}
                  </td>
                  <td className="px-6 py-3.5 text-center">
                    {typeof row.ent === 'boolean' ? (
                      row.ent ? <Check className="w-4 h-4 mx-auto text-foreground font-bold" /> : <span className="text-muted-foreground/40">—</span>
                    ) : (
                      <span className="text-foreground font-semibold">{row.ent}</span>
                    )}
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </section>

      {/* Frequently Asked Questions */}
      <section className="max-w-3xl mx-auto">
        <ScrollReveal className="text-center mb-12">
          <h2 className="text-2xl sm:text-3xl font-extrabold text-foreground">
            Frequently Asked Questions
          </h2>
          <p className="text-xs text-muted-foreground mt-2">
            Everything you need to know about SprintForge licensing and billing.
          </p>
        </ScrollReveal>

        <div className="space-y-3">
          {faqs.map((faq, idx) => (
            <div
              key={idx}
              className="rounded-xl border border-border/80 bg-surface/70 backdrop-blur-md overflow-hidden transition-all"
            >
              <button
                onClick={() => toggleFaq(idx)}
                className="w-full px-5 py-4 flex items-center justify-between text-left text-xs font-semibold text-foreground hover:bg-surface-hover transition-colors"
              >
                <span>{faq.q}</span>
                <ChevronDown
                  className={`w-4 h-4 text-muted-foreground transition-transform duration-200 ${
                    openFaq === idx ? 'rotate-180 text-foreground' : ''
                  }`}
                />
              </button>
              {openFaq === idx && (
                <div className="px-5 pb-4 pt-1 text-xs text-muted-foreground leading-relaxed border-t border-border/40">
                  {faq.a}
                </div>
              )}
            </div>
          ))}
        </div>
      </section>
    </div>
  );
}

export default PricingPage;
