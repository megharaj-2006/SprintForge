import React, { useState } from 'react';
import {
  Mail,
  Building,
  Users,
  ShieldCheck,
  CheckCircle2,
  Clock,
  ArrowRight,
  MapPin,
  MessageSquare,
  Lock,
} from 'lucide-react';
import { Button } from '../../components/ui/Button';
import { SpotlightCard } from '../../components/marketing/SpotlightCard';
import { ScrollReveal } from '../../components/marketing/ScrollReveal';

export function ContactPage() {
  const [formData, setFormData] = useState({
    name: '',
    email: '',
    company: '',
    teamSize: '20-50',
    inquiryType: 'ENTERPRISE_DEMO',
    message: '',
  });

  const [submitted, setSubmitted] = useState(false);
  const [isLoading, setIsLoading] = useState(false);

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    setIsLoading(true);
    setTimeout(() => {
      setIsLoading(false);
      setSubmitted(true);
    }, 700);
  };

  return (
    <div className="py-20 px-4 sm:px-6 lg:px-8 max-w-7xl mx-auto">
      {/* Header */}
      <ScrollReveal className="text-center max-w-3xl mx-auto mb-16">
        <span className="text-[10px] font-bold uppercase tracking-widest px-3 py-1 rounded-full bg-foreground text-background inline-block mb-4">
          Contact Us
        </span>
        <h1 className="text-4xl sm:text-6xl font-extrabold tracking-tight text-foreground leading-tight">
          Talk to our team.
        </h1>
        <p className="mt-4 text-sm sm:text-base text-muted-foreground leading-relaxed">
          Whether you need a custom Enterprise VPC deployment, an architectural security review, or migration assistance from Jira, our engineers are ready.
        </p>
      </ScrollReveal>

      <div className="grid grid-cols-1 lg:grid-cols-12 gap-12 items-start">
        {/* Left Column: Guarantees & Office Hubs (Span 5) */}
        <div className="lg:col-span-5 space-y-8">
          <ScrollReveal delay={0.1}>
            <div className="rounded-2xl border border-border/80 bg-surface/70 backdrop-blur-xl p-6 sm:p-8 space-y-6 shadow-lg">
              <h3 className="text-lg font-bold text-foreground">
                Enterprise Commitments
              </h3>

              <div className="space-y-4 text-xs">
                <div className="flex items-start gap-3">
                  <Clock className="w-4 h-4 text-emerald-400 shrink-0 mt-0.5" />
                  <div>
                    <span className="font-semibold text-foreground block">
                      Sub-15 Minute Response Time
                    </span>
                    <span className="text-muted-foreground leading-relaxed">
                      Enterprise inquiries are routed directly to Senior Solutions Architects on call.
                    </span>
                  </div>
                </div>

                <div className="flex items-start gap-3">
                  <ShieldCheck className="w-4 h-4 text-emerald-400 shrink-0 mt-0.5" />
                  <div>
                    <span className="font-semibold text-foreground block">
                      Comprehensive Security Pack
                    </span>
                    <span className="text-muted-foreground leading-relaxed">
                      Instant access to our SOC2 Type II audit report, ISO27001 certificate, and penetration test summaries.
                    </span>
                  </div>
                </div>

                <div className="flex items-start gap-3">
                  <Users className="w-4 h-4 text-emerald-400 shrink-0 mt-0.5" />
                  <div>
                    <span className="font-semibold text-foreground block">
                      Guided Migration Assistance
                    </span>
                    <span className="text-muted-foreground leading-relaxed">
                      Zero downtime issue import from Jira Cloud, Linear, Asana, or GitHub Projects.
                    </span>
                  </div>
                </div>
              </div>
            </div>
          </ScrollReveal>

          {/* Office Hubs */}
          <ScrollReveal delay={0.2}>
            <div className="space-y-4">
              <h4 className="text-xs font-semibold uppercase tracking-wider text-muted-foreground">
                Engineering Centers
              </h4>
              <div className="grid grid-cols-1 sm:grid-cols-3 gap-3 text-xs">
                <div className="p-3.5 rounded-xl border border-border/70 bg-surface/50">
                  <div className="font-semibold text-foreground flex items-center gap-1.5 mb-1">
                    <MapPin className="w-3.5 h-3.5 text-foreground" />
                    San Francisco
                  </div>
                  <span className="text-muted-foreground text-[11px]">Market Street HQ</span>
                </div>
                <div className="p-3.5 rounded-xl border border-border/70 bg-surface/50">
                  <div className="font-semibold text-foreground flex items-center gap-1.5 mb-1">
                    <MapPin className="w-3.5 h-3.5 text-foreground" />
                    London
                  </div>
                  <span className="text-muted-foreground text-[11px]">Shoreditch Hub</span>
                </div>
                <div className="p-3.5 rounded-xl border border-border/70 bg-surface/50">
                  <div className="font-semibold text-foreground flex items-center gap-1.5 mb-1">
                    <MapPin className="w-3.5 h-3.5 text-foreground" />
                    Bengaluru
                  </div>
                  <span className="text-muted-foreground text-[11px]">Indiranagar Center</span>
                </div>
              </div>
            </div>
          </ScrollReveal>
        </div>

        {/* Right Column: Contact Form (Span 7) */}
        <div className="lg:col-span-7">
          <ScrollReveal delay={0.2}>
            <SpotlightCard className="p-8 sm:p-10 border-border/90 shadow-2xl">
              {submitted ? (
                <div className="py-12 text-center space-y-4">
                  <div className="w-12 h-12 rounded-full bg-emerald-500/15 text-emerald-400 flex items-center justify-center mx-auto border border-emerald-500/30">
                    <CheckCircle2 className="w-6 h-6" />
                  </div>
                  <h3 className="text-xl font-bold text-foreground">
                    Message Received
                  </h3>
                  <p className="text-xs text-muted-foreground max-w-sm mx-auto leading-relaxed">
                    Thank you. A Senior Solutions Architect has been assigned and will reply to <strong className="text-foreground">{formData.email}</strong> shortly.
                  </p>
                  <Button
                    variant="outline"
                    size="sm"
                    onClick={() => {
                      setSubmitted(false);
                      setFormData({
                        name: '',
                        email: '',
                        company: '',
                        teamSize: '20-50',
                        inquiryType: 'ENTERPRISE_DEMO',
                        message: '',
                      });
                    }}
                  >
                    Send another inquiry
                  </Button>
                </div>
              ) : (
                <form onSubmit={handleSubmit} className="space-y-4 text-xs">
                  <div>
                    <label className="block font-medium text-foreground mb-1.5">
                      Inquiry Category
                    </label>
                    <div className="grid grid-cols-2 sm:grid-cols-3 gap-2">
                      {[
                        { id: 'ENTERPRISE_DEMO', label: 'Enterprise Demo' },
                        { id: 'SECURITY_AUDIT', label: 'Security & SOC2' },
                        { id: 'MIGRATION', label: 'Jira Migration' },
                        { id: 'VPC_DEPLOYMENT', label: 'Custom VPC / Cloud' },
                        { id: 'PARTNERSHIP', label: 'Tech Partner' },
                        { id: 'SUPPORT', label: 'Developer Help' },
                      ].map((type) => (
                        <button
                          key={type.id}
                          type="button"
                          onClick={() => setFormData({ ...formData, inquiryType: type.id })}
                          className={`p-2 rounded-lg text-left text-[11px] font-medium border transition-all ${
                            formData.inquiryType === type.id
                              ? 'bg-foreground text-background border-foreground font-semibold shadow-xs'
                              : 'bg-surface border-border/80 text-muted-foreground hover:text-foreground hover:bg-surface-hover'
                          }`}
                        >
                          {type.label}
                        </button>
                      ))}
                    </div>
                  </div>

                  <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
                    <div>
                      <label className="block font-medium text-foreground mb-1">
                        Full Name *
                      </label>
                      <input
                        type="text"
                        required
                        value={formData.name}
                        onChange={(e) => setFormData({ ...formData, name: e.target.value })}
                        placeholder="Alex Vance"
                        className="w-full h-9 px-3 rounded-md border border-border bg-background text-xs placeholder:text-muted-foreground/50 focus:outline-none focus:ring-1 focus:ring-foreground"
                      />
                    </div>
                    <div>
                      <label className="block font-medium text-foreground mb-1">
                        Work Email *
                      </label>
                      <input
                        type="email"
                        required
                        value={formData.email}
                        onChange={(e) => setFormData({ ...formData, email: e.target.value })}
                        placeholder="alex@company.com"
                        className="w-full h-9 px-3 rounded-md border border-border bg-background text-xs placeholder:text-muted-foreground/50 focus:outline-none focus:ring-1 focus:ring-foreground"
                      />
                    </div>
                  </div>

                  <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
                    <div>
                      <label className="block font-medium text-foreground mb-1">
                        Company Name *
                      </label>
                      <input
                        type="text"
                        required
                        value={formData.company}
                        onChange={(e) => setFormData({ ...formData, company: e.target.value })}
                        placeholder="Acme Systems"
                        className="w-full h-9 px-3 rounded-md border border-border bg-background text-xs placeholder:text-muted-foreground/50 focus:outline-none focus:ring-1 focus:ring-foreground"
                      />
                    </div>
                    <div>
                      <label className="block font-medium text-foreground mb-1">
                        Engineering Team Size
                      </label>
                      <select
                        value={formData.teamSize}
                        onChange={(e) => setFormData({ ...formData, teamSize: e.target.value })}
                        className="w-full h-9 px-3 rounded-md border border-border bg-background text-xs focus:outline-none focus:ring-1 focus:ring-foreground"
                      >
                        <option value="1-10">1 - 10 engineers</option>
                        <option value="10-50">10 - 50 engineers</option>
                        <option value="50-200">50 - 200 engineers</option>
                        <option value="200+">200+ engineers</option>
                      </select>
                    </div>
                  </div>

                  <div>
                    <label className="block font-medium text-foreground mb-1">
                      Project Context & Technical Requirements
                    </label>
                    <textarea
                      rows={4}
                      value={formData.message}
                      onChange={(e) => setFormData({ ...formData, message: e.target.value })}
                      placeholder="Tell us about your team's current project management bottlenecks, custom SSO needs, or expected migration timeline..."
                      className="w-full p-3 rounded-md border border-border bg-background text-xs placeholder:text-muted-foreground/50 focus:outline-none focus:ring-1 focus:ring-foreground resize-none"
                    />
                  </div>

                  <div className="pt-2">
                    <Button
                      type="submit"
                      variant="primary"
                      size="lg"
                      isLoading={isLoading}
                      className="w-full h-10 font-semibold"
                      icon={<ArrowRight className="w-4 h-4 order-last" />}
                    >
                      Submit Consultation Request
                    </Button>
                  </div>

                  <p className="text-[10px] text-muted-foreground text-center pt-1">
                    By submitting, you agree to our non-disclosure terms. Your technical data is never shared.
                  </p>
                </form>
              )}
            </SpotlightCard>
          </ScrollReveal>
        </div>
      </div>
    </div>
  );
}

export default ContactPage;
