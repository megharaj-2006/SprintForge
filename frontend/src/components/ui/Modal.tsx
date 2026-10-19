import React from 'react';
import * as DialogPrimitive from '@radix-ui/react-dialog';
import { motion, AnimatePresence } from 'framer-motion';
import { X } from 'lucide-react';
import { cn } from '../../lib/utils';

export interface ModalProps {
  open?: boolean;
  isOpen?: boolean;
  onOpenChange?: (open: boolean) => void;
  onClose?: () => void;
  title?: string;
  description?: string;
  children: React.ReactNode;
  className?: string;
  maxWidth?: 'sm' | 'md' | 'lg' | 'xl' | '2xl' | 'full';
}

export function Modal({
  open,
  isOpen,
  onOpenChange,
  onClose,
  title,
  description,
  children,
  className,
  maxWidth = 'lg',
}: ModalProps) {
  const isModalOpen = open !== undefined ? open : Boolean(isOpen);
  const handleOpenChange = (val: boolean) => {
    onOpenChange?.(val);
    if (!val) {
      onClose?.();
    }
  };
  const widthClasses = {
    sm: 'max-w-sm',
    md: 'max-w-md',
    lg: 'max-w-lg',
    xl: 'max-w-xl',
    '2xl': 'max-w-2xl',
    full: 'max-w-4xl',
  };

  return (
    <DialogPrimitive.Root open={isModalOpen} onOpenChange={handleOpenChange}>
      <AnimatePresence>
        {isModalOpen && (
          <DialogPrimitive.Portal forceMount>
            <DialogPrimitive.Overlay asChild>
              <motion.div
                initial={{ opacity: 0 }}
                animate={{ opacity: 1 }}
                exit={{ opacity: 0 }}
                transition={{ duration: 0.15 }}
                className="fixed inset-0 z-50 bg-black/60 backdrop-blur-xs"
              />
            </DialogPrimitive.Overlay>

            <div className="fixed inset-0 z-50 flex items-center justify-center p-4">
              <DialogPrimitive.Content asChild>
                <motion.div
                  initial={{ opacity: 0, scale: 0.97, y: 8 }}
                  animate={{ opacity: 1, scale: 1, y: 0 }}
                  exit={{ opacity: 0, scale: 0.97, y: 8 }}
                  transition={{
                    type: 'spring',
                    stiffness: 450,
                    damping: 32,
                  }}
                  className={cn(
                    'w-full bg-surface border border-border shadow-2xl rounded-xl overflow-hidden focus:outline-none flex flex-col',
                    widthClasses[maxWidth],
                    className
                  )}
                >
                  {(title || description) && (
                    <div className="px-5 py-4 border-b border-border/80 flex items-start justify-between shrink-0">
                      <div>
                        {title && (
                          <DialogPrimitive.Title className="text-sm font-semibold text-foreground tracking-tight">
                            {title}
                          </DialogPrimitive.Title>
                        )}
                        {description && (
                          <DialogPrimitive.Description className="text-xs text-muted-foreground mt-0.5">
                            {description}
                          </DialogPrimitive.Description>
                        )}
                      </div>
                      <DialogPrimitive.Close asChild>
                        <button
                          className="text-muted-foreground hover:text-foreground rounded p-1 hover:bg-surface-hover transition-colors"
                          aria-label="Close"
                        >
                          <X className="w-4 h-4" />
                        </button>
                      </DialogPrimitive.Close>
                    </div>
                  )}
                  <div className="p-5 overflow-y-auto max-h-[80vh]">{children}</div>
                </motion.div>
              </DialogPrimitive.Content>
            </div>
          </DialogPrimitive.Portal>
        )}
      </AnimatePresence>
    </DialogPrimitive.Root>
  );
}
