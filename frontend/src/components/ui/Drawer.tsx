import React from 'react';
import { motion, AnimatePresence } from 'framer-motion';
import { X } from 'lucide-react';
import { cn } from '../../lib/utils';

export interface DrawerProps {
  open: boolean;
  onClose: () => void;
  title?: React.ReactNode;
  subtitle?: React.ReactNode;
  children: React.ReactNode;
  className?: string;
  width?: 'md' | 'lg' | 'xl' | '2xl';
}

export function Drawer({
  open,
  onClose,
  title,
  subtitle,
  children,
  className,
  width = 'xl',
}: DrawerProps) {
  const widthClasses = {
    md: 'max-w-md',
    lg: 'max-w-lg',
    xl: 'max-w-xl',
    '2xl': 'max-w-2xl',
  };

  return (
    <AnimatePresence>
      {open && (
        <div className="fixed inset-0 z-50 overflow-hidden">
          {/* Backdrop */}
          <motion.div
            initial={{ opacity: 0 }}
            animate={{ opacity: 1 }}
            exit={{ opacity: 0 }}
            transition={{ duration: 0.15 }}
            onClick={onClose}
            className="fixed inset-0 bg-black/50 backdrop-blur-xs"
          />

          {/* Drawer content */}
          <div className="fixed inset-y-0 right-0 flex pl-10 max-w-full">
            <motion.div
              initial={{ x: '100%' }}
              animate={{ x: 0 }}
              exit={{ x: '100%' }}
              transition={{
                type: 'spring',
                stiffness: 400,
                damping: 35,
              }}
              className={cn(
                'w-screen bg-surface border-l border-border shadow-2xl flex flex-col focus:outline-none',
                widthClasses[width],
                className
              )}
            >
              {/* Header */}
              <div className="h-12 px-5 border-b border-border/80 flex items-center justify-between shrink-0 bg-surface/50 backdrop-blur-md">
                <div className="flex items-center gap-3 min-w-0">
                  {title && (
                    <div className="text-xs font-semibold text-foreground truncate tracking-tight">
                      {title}
                    </div>
                  )}
                  {subtitle && (
                    <div className="text-xs text-muted-foreground truncate">{subtitle}</div>
                  )}
                </div>
                <button
                  onClick={onClose}
                  className="text-muted-foreground hover:text-foreground rounded p-1 hover:bg-surface-hover transition-colors"
                  aria-label="Close drawer"
                >
                  <X className="w-4 h-4" />
                </button>
              </div>

              {/* Scrollable Body */}
              <div className="flex-1 overflow-y-auto p-5">{children}</div>
            </motion.div>
          </div>
        </div>
      )}
    </AnimatePresence>
  );
}
