import React from 'react';
import { cn } from '../../lib/utils';

export interface ButtonProps extends React.ButtonHTMLAttributes<HTMLButtonElement> {
  variant?: 'primary' | 'secondary' | 'ghost' | 'outline' | 'destructive';
  size?: 'xs' | 'sm' | 'md' | 'lg';
  isLoading?: boolean;
  shortcut?: string;
  icon?: React.ReactNode;
}

export const Button = React.forwardRef<HTMLButtonElement, ButtonProps>(
  (
    {
      className,
      variant = 'secondary',
      size = 'sm',
      isLoading = false,
      shortcut,
      icon,
      children,
      disabled,
      ...props
    },
    ref
  ) => {
    const baseStyles =
      'inline-flex items-center justify-center font-medium rounded-md transition-all duration-150 select-none focus:outline-none focus:ring-2 focus:ring-primary/40 disabled:opacity-50 disabled:pointer-events-none active:scale-[0.98]';

    const variants = {
      primary: 'bg-primary text-primary-foreground hover:bg-primary-hover shadow-sm',
      secondary:
        'bg-surface hover:bg-surface-hover text-foreground border border-border shadow-xs',
      ghost: 'hover:bg-surface-hover text-muted-foreground hover:text-foreground',
      outline:
        'bg-transparent hover:bg-surface-hover text-foreground border border-border/80',
      destructive:
        'bg-destructive/10 text-destructive hover:bg-destructive hover:text-white border border-destructive/20',
    };

    const sizes = {
      xs: 'text-[11px] h-6 px-2 gap-1.5',
      sm: 'text-xs h-7 px-2.5 gap-1.5',
      md: 'text-xs h-8 px-3 gap-2',
      lg: 'text-sm h-9 px-4 gap-2',
    };

    return (
      <button
        ref={ref}
        disabled={disabled || isLoading}
        className={cn(baseStyles, variants[variant], sizes[size], className)}
        {...props}
      >
        {isLoading ? (
          <span className="w-3.5 h-3.5 border-2 border-current border-t-transparent rounded-full animate-spin" />
        ) : (
          icon && <span className="shrink-0">{icon}</span>
        )}
        {children}
        {shortcut && (
          <kbd className="ml-1.5 text-[10px] font-mono opacity-60 bg-muted/40 px-1 py-0.5 rounded border border-border/50">
            {shortcut}
          </kbd>
        )}
      </button>
    );
  }
);

Button.displayName = 'Button';
