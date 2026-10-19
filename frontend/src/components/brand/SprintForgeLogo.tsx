import React from 'react';
import { cn } from '../../lib/utils';

export interface SprintForgeIconProps extends React.SVGProps<SVGSVGElement> {
  size?: number | string;
}

/**
 * Official SprintForge Vector Icon Mark (Interlocking S Badge).
 * Responsive vector path extracted directly from brand design.
 */
export function SprintForgeIcon({
  className,
  size,
  width,
  height,
  ...props
}: SprintForgeIconProps) {
  return (
    <svg
      viewBox="-8 0 327 327"
      fill="currentColor"
      preserveAspectRatio="xMidYMid meet"
      className={cn('inline-block shrink-0', className)}
      style={{
        width: size || width,
        height: size || height,
        ...props.style,
      }}
      aria-label="SprintForge Logo"
      {...props}
    >
      <g transform="translate(0, 327) scale(0.1, -0.1)">
        <path d="M1180 3059 c-250 -41 -440 -133 -611 -296 -92 -88 -165 -191 -224 -316 -60 -127 -86 -229 -92 -368 -14 -291 73 -529 281 -771 l49 -58 402 0 c381 0 402 1 422 19 11 11 46 84 77 163 35 89 66 152 82 166 25 22 26 22 388 22 380 0 387 -1 385 -44 0 -12 -24 -82 -53 -156 -40 -104 -58 -138 -77 -147 -16 -9 -81 -13 -202 -13 -165 0 -178 -1 -194 -20 -10 -11 -42 -82 -71 -158 -29 -75 -63 -147 -74 -159 l-21 -23 -571 0 c-675 0 -621 9 -674 -116 -101 -240 -202 -499 -202 -519 0 -14 11 -32 26 -44 27 -21 32 -21 829 -21 671 0 817 2 891 15 298 51 553 204 731 439 64 85 138 243 169 361 37 140 39 338 5 470 -44 168 -119 300 -247 435 l-77 80 -403 0 -403 0 -26 -22 c-16 -15 -41 -66 -70 -143 -25 -66 -54 -131 -64 -145 -33 -45 -63 -49 -421 -49 -288 -1 -339 1 -358 15 -12 8 -22 19 -22 25 0 22 111 303 122 311 7 4 97 8 199 8 164 0 189 2 206 18 11 9 46 83 77 164 37 94 65 153 79 163 18 13 100 15 592 15 547 0 571 1 602 20 17 10 37 28 43 39 16 31 230 569 230 580 0 5 -12 23 -26 40 l-26 31 -811 -1 c-447 -1 -837 -5 -867 -10z" />
      </g>
    </svg>
  );
}

export type LogoSize = 'xs' | 'sm' | 'md' | 'lg' | 'xl';
export type LogoVariant = 'icon' | 'badge' | 'full' | 'stacked';

export interface SprintForgeLogoProps {
  variant?: LogoVariant;
  size?: LogoSize;
  badge?: boolean;
  showVersion?: boolean;
  versionText?: string;
  className?: string;
  textClassName?: string;
  iconClassName?: string;
  badgeClassName?: string;
}

const sizeConfig = {
  xs: {
    badge: 'w-5 h-5 rounded-md',
    icon: 'w-3 h-3',
    text: 'text-xs',
  },
  sm: {
    badge: 'h-7 w-7 rounded-lg',
    icon: 'w-4 h-4',
    text: 'text-sm font-semibold',
  },
  md: {
    badge: 'w-8 h-8 rounded-lg',
    icon: 'w-4.5 h-4.5',
    text: 'text-base font-semibold',
  },
  lg: {
    badge: 'w-10 h-10 rounded-lg',
    icon: 'w-6 h-6',
    text: 'text-lg font-bold',
  },
  xl: {
    badge: 'w-12 h-12 rounded-xl',
    icon: 'w-7 h-7',
    text: 'text-xl font-bold',
  },
};

/**
 * Unified SprintForge Brand Logo component supporting icon, badge, and full lockups.
 */
export function SprintForgeLogo({
  variant = 'full',
  size = 'sm',
  badge = true,
  showVersion = false,
  versionText = 'v2.0',
  className,
  textClassName,
  iconClassName,
  badgeClassName,
}: SprintForgeLogoProps) {
  const config = sizeConfig[size];

  // Pure icon variant
  if (variant === 'icon') {
    return (
      <SprintForgeIcon
        className={cn(config.icon, iconClassName, className)}
      />
    );
  }

  // Render the badge element
  const badgeElement = badge ? (
    <div
      className={cn(
        'bg-foreground text-background flex items-center justify-center shrink-0 shadow-xs transition-transform',
        config.badge,
        badgeClassName
      )}
    >
      <SprintForgeIcon className={cn(config.icon, 'text-background', iconClassName)} />
    </div>
  ) : (
    <SprintForgeIcon className={cn(config.icon, 'text-foreground', iconClassName)} />
  );

  // Standalone badge variant
  if (variant === 'badge') {
    return <div className={cn('inline-flex', className)}>{badgeElement}</div>;
  }

  // Stacked variant (icon/badge on top, wordmark below)
  if (variant === 'stacked') {
    return (
      <div className={cn('flex flex-col items-center gap-2 select-none', className)}>
        {badgeElement}
        <span
          className={cn(
            'tracking-tight text-foreground font-semibold',
            config.text,
            textClassName
          )}
        >
          SprintForge
        </span>
      </div>
    );
  }

  // Default 'full' variant (horizontal lockup)
  return (
    <div className={cn('inline-flex items-center gap-2.5 select-none', className)}>
      {badgeElement}
      <span
        className={cn(
          'tracking-tight text-foreground',
          config.text,
          textClassName
        )}
      >
        SprintForge
      </span>
      {showVersion && (
        <span className="hidden sm:inline-block text-[10px] uppercase font-semibold tracking-wider px-1.5 py-0.5 rounded-full bg-surface-raised border border-border text-muted-foreground">
          {versionText}
        </span>
      )}
    </div>
  );
}

export default SprintForgeLogo;
