import React from 'react';

export function GithubLogo({ className = "w-5 h-5" }: { className?: string }) {
  return (
    <svg className={className} viewBox="0 0 24 24" fill="currentColor">
      <path
        fillRule="evenodd"
        clipRule="evenodd"
        d="M12 2C6.477 2 2 6.484 2 12.017c0 4.425 2.865 8.18 6.839 9.504.5.092.682-.217.682-.483 0-.237-.008-.868-.013-1.703-2.782.605-3.369-1.343-3.369-1.343-.454-1.158-1.11-1.466-1.11-1.466-.908-.62.069-.608.069-.608 1.003.07 1.53 1.032 1.53 1.032.892 1.53 2.341 1.088 2.91.832.092-.647.35-1.088.636-1.338-2.22-.253-4.555-1.113-4.555-4.951 0-1.093.39-1.988 1.029-2.688-.103-.253-.446-1.272.098-2.65 0 0 .84-.27 2.75 1.026A9.564 9.564 0 0112 6.844c.85.004 1.705.115 2.504.337 1.909-1.296 2.747-1.027 2.747-1.027.546 1.379.202 2.398.1 2.651.64.7 1.028 1.595 1.028 2.688 0 3.848-2.339 4.695-4.566 4.943.359.309.678.92.678 1.855 0 1.338-.012 2.419-.012 2.747 0 .268.18.58.688.482A10.019 10.019 0 0022 12.017C22 6.484 17.522 2 12 2z"
      />
    </svg>
  );
}

export function GitlabLogo({ className = "w-5 h-5" }: { className?: string }) {
  return (
    <svg className={className} viewBox="0 0 24 24" fill="none">
      <path d="M23.955 13.587l-1.342-4.135-2.664-8.189c-.135-.417-.724-.417-.86 0L16.425 9.45H7.575L4.911 1.263c-.136-.417-.725-.417-.86 0L1.387 9.452.045 13.587c-.161.494.015 1.037.433 1.34l11.522 8.37 11.522-8.37c.418-.303.594-.846.433-1.34z" fill="#E24329" />
      <path d="M12 23.297l-4.425-13.847h8.85L12 23.297z" fill="#E24329" />
      <path d="M12 23.297L7.575 9.45H1.387l10.613 13.847z" fill="#FC6D26" />
      <path d="M1.387 9.45l-.954 2.937c-.161.494.015 1.037.433 1.34L12 23.297 1.387 9.45z" fill="#FCA326" />
      <path d="M12 23.297l4.425-13.847h6.188L12 23.297z" fill="#FC6D26" />
      <path d="M22.613 9.45l.954 2.937c.161.494-.015 1.037-.433 1.34L12 23.297 22.613 9.45z" fill="#FCA326" />
    </svg>
  );
}

export function SlackLogo({ className = "w-5 h-5" }: { className?: string }) {
  return (
    <svg className={className} viewBox="0 0 24 24" fill="none">
      <path d="M5.042 15.165a2.528 2.528 0 0 1-2.52 2.523A2.528 2.528 0 0 1 0 15.165a2.527 2.527 0 0 1 2.522-2.52h2.52v2.52zM6.313 15.165a2.527 2.527 0 0 1 2.521-2.52 2.528 2.528 0 0 1 2.521 2.52v6.313A2.528 2.528 0 0 1 8.834 24a2.528 2.528 0 0 1-2.521-2.522v-6.313z" fill="#E01E5A" />
      <path d="M8.834 5.042a2.528 2.528 0 0 1-2.521-2.52A2.528 2.528 0 0 1 8.834 0a2.528 2.528 0 0 1 2.521 2.522v2.52H8.834zM8.834 6.313a2.528 2.528 0 0 1 2.521 2.521 2.528 2.528 0 0 1-2.521 2.521H2.522A2.528 2.528 0 0 1 0 8.834a2.528 2.528 0 0 1 2.522-2.521h6.312z" fill="#36C5F0" />
      <path d="M18.956 8.834a2.528 2.528 0 0 1 2.522-2.521A2.528 2.528 0 0 1 24 8.834a2.528 2.528 0 0 1-2.522 2.521h-2.522V8.834zM17.688 8.834a2.528 2.528 0 0 1-2.523 2.521 2.527 2.527 0 0 1-2.52-2.521V2.522A2.527 2.527 0 0 1 15.165 0a2.528 2.528 0 0 1 2.523 2.522v6.312z" fill="#2EB67D" />
      <path d="M15.165 18.956a2.528 2.528 0 0 1 2.523 2.522A2.528 2.528 0 0 1 15.165 24a2.527 2.527 0 0 1-2.52-2.522v-2.522h2.52zM15.165 17.688a2.527 2.527 0 0 1-2.52-2.523 2.528 2.528 0 0 1 2.52-2.52h6.313A2.528 2.528 0 0 1 24 15.165a2.528 2.528 0 0 1-2.522 2.523h-6.313z" fill="#ECB22E" />
    </svg>
  );
}

export function TeamsLogo({ className = "w-5 h-5" }: { className?: string }) {
  return (
    <svg className={className} viewBox="0 0 24 24" fill="none">
      <path d="M16.5 7.5a2 2 0 1 0 0-4 2 2 0 0 0 0 4z" fill="#5059C9" />
      <path d="M19.5 9h-6a1.5 1.5 0 0 0-1.5 1.5V15h2v-4.5h4V15h2v-4.5A1.5 1.5 0 0 0 19.5 9z" fill="#5059C9" />
      <path d="M9.5 9a2.5 2.5 0 1 0 0-5 2.5 2.5 0 0 0 0 5z" fill="#7B83EB" />
      <path d="M13.5 11h-8A2.5 2.5 0 0 0 3 13.5V20h13v-6.5a2.5 2.5 0 0 0-2.5-2.5z" fill="#7B83EB" />
      <rect x="2" y="8" width="10" height="10" rx="2" fill="#4B53BC" />
      <path d="M5.5 10.5h3v1.2H7.6v4.3H6.4v-4.3H5.5v-1.2z" fill="#FFFFFF" />
    </svg>
  );
}

export function JiraLogo({ className = "w-5 h-5" }: { className?: string }) {
  return (
    <svg className={className} viewBox="0 0 24 24" fill="none">
      <path
        d="M11.53 2c0 5.264-4.267 9.53-9.53 9.53v.94c5.263 0 9.53 4.267 9.53 9.53h.94c0-5.263 4.267-9.53 9.53-9.53v-.94c-5.263 0-9.53-4.266-9.53-9.53h-.94z"
        fill="#2684FF"
      />
      <path
        d="M2 11.53C7.264 11.53 11.53 7.263 11.53 2H2v9.53z"
        fill="#0052CC"
      />
      <path
        d="M11.53 22c0-5.263 4.267-9.53 9.53-9.53V22h-9.53z"
        fill="#0052CC"
      />
    </svg>
  );
}

export function LinearLogo({ className = "w-5 h-5" }: { className?: string }) {
  return (
    <svg className={className} viewBox="0 0 100 100" fill="none">
      <path
        d="M5.00024 49.9998C5.00024 25.147 25.1474 4.99976 50.0002 4.99976C63.4831 4.99976 75.5492 10.9427 83.7436 20.4048L20.4053 83.7432C10.9432 75.5488 5.00024 63.4827 5.00024 49.9998Z"
        fill="#5E6AD2"
      />
      <path
        d="M29.5891 89.2811L89.2816 29.5886C92.9378 35.6514 95.0002 42.6074 95.0002 49.9998C95.0002 74.8526 74.8531 94.9998 50.0002 94.9998C42.6079 94.9998 35.6518 92.9373 29.5891 89.2811Z"
        fill="#5E6AD2"
      />
      <path
        d="M23.5186 86.4812L86.4819 23.5178C88.2435 25.5678 89.7895 27.8184 91.0772 30.2227L30.2235 91.0763C27.8192 89.7887 25.5686 88.2427 23.5186 86.4812Z"
        fill="#7F8BE8"
      />
    </svg>
  );
}

export function GoogleCalendarLogo({ className = "w-5 h-5" }: { className?: string }) {
  return (
    <svg className={className} viewBox="0 0 24 24" fill="none">
      <rect x="3" y="4" width="18" height="17" rx="3" fill="#FFFFFF" />
      <path d="M19 4h-2V2h-2v2H9V2H7v2H5c-1.1 0-2 .9-2 2v2h18V6c0-1.1-.9-2-2-2z" fill="#4285F4" />
      <path d="M3 8v12c0 1.1.9 2 2 2h2V8H3z" fill="#EA4335" />
      <path d="M17 22h2c1.1 0 2-.9 2-2V8h-4v14z" fill="#FBBC05" />
      <path d="M7 18h10v4H7z" fill="#34A853" />
      <rect x="6.5" y="7.5" width="11" height="11" rx="1.5" fill="#FFFFFF" />
      <text x="12" y="16.2" textAnchor="middle" fontSize="9" fontWeight="800" fill="#4285F4" fontFamily="Inter, system-ui, sans-serif">31</text>
    </svg>
  );
}

export function OutlookLogo({ className = "w-5 h-5" }: { className?: string }) {
  return (
    <svg className={className} viewBox="0 0 24 24" fill="none">
      <path d="M13 5l8 4v10a1 1 0 0 1-1 1h-7V5z" fill="#0078D4" />
      <path d="M13 5L6 9v11h7V5z" fill="#1490DF" />
      <path d="M13 5L6 9l7 4 8-4-8-4z" fill="#28A8EA" />
      <rect x="2" y="6" width="10" height="12" rx="2" fill="#005A9E" />
      <circle cx="7" cy="12" r="3" fill="#FFFFFF" />
      <circle cx="7" cy="12" r="1.6" fill="#005A9E" />
    </svg>
  );
}

export function NotionLogo({ className = "w-5 h-5" }: { className?: string }) {
  return (
    <svg className={className} viewBox="0 0 24 24" fill="currentColor">
      <path d="M4.459 4.208c.746.606 1.026.56 2.428.466l13.215-.793c.28 0 .047-.28-.046-.326L17.86 1.83c-.467-.373-.933-.56-1.586-.513L3.386 2.296c-.466.047-.56.327-.373.56l1.446 1.352zm.933 3.684v13.076c0 .746.373 1.026 1.213.98l14.475-.84c.84-.046.933-.56.933-1.12V6.914c0-.606-.233-.886-.793-.84l-15.035.886c-.607.047-.793.373-.793.933zm13.82.746c.094.42.094.84-.28.887l-.98.14v10.125c-.466.28-.933.42-1.353.42-.653 0-.933-.187-1.446-.84l-5.18-8.12v7.794l1.4.327c.093.42-.14.84-.513.84l-3.92.233c-.093-.373.093-.793.42-.84l1.213-.28V9.387l-1.353-.14c-.093-.42.093-.84.466-.887l3.967-.233 5.413 8.353V9.107l-1.167-.14c-.093-.42.14-.84.513-.887l3.827-.233z" />
    </svg>
  );
}

export function WebhooksLogo({ className = "w-5 h-5" }: { className?: string }) {
  return (
    <svg className={className} viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
      <path d="M18 16.98h-5.99c-1.1 0-1.95.94-2.48 1.9A4 4 0 0 1 2 17c0-2.21 1.79-4 4-4h1" />
      <path d="M6 7.02h5.99c1.1 0 1.95-.94 2.48-1.9A4 4 0 0 1 22 7c0 2.21-1.79 4-4 4h-1" />
      <circle cx="6" cy="17" r="1.5" fill="currentColor" />
      <circle cx="18" cy="7" r="1.5" fill="currentColor" />
      <polyline points="15 14 18 17 15 20" />
      <polyline points="9 10 6 7 9 4" />
    </svg>
  );
}

export function PlatformLogo({
  platform,
  className = "w-5 h-5",
}: {
  platform: string;
  className?: string;
}) {
  switch (platform.toLowerCase()) {
    case 'github':
      return <GithubLogo className={className} />;
    case 'gitlab':
      return <GitlabLogo className={className} />;
    case 'slack':
      return <SlackLogo className={className} />;
    case 'teams':
    case 'microsoft-teams':
      return <TeamsLogo className={className} />;
    case 'jira':
      return <JiraLogo className={className} />;
    case 'linear':
      return <LinearLogo className={className} />;
    case 'google-calendar':
    case 'calendar':
      return <GoogleCalendarLogo className={className} />;
    case 'outlook':
    case 'microsoft-outlook':
      return <OutlookLogo className={className} />;
    case 'notion':
      return <NotionLogo className={className} />;
    case 'webhooks':
    case 'webhook':
    default:
      return <WebhooksLogo className={className} />;
  }
}
