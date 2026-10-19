import React from 'react';
import { Columns3, List, GanttChart } from 'lucide-react';
import { cn } from '../../lib/utils';

export type ActiveView = 'board' | 'list' | 'timeline';

interface ViewSwitcherProps {
  activeView: ActiveView;
  onViewChange: (view: ActiveView) => void;
}

export function ViewSwitcher({ activeView, onViewChange }: ViewSwitcherProps) {
  return (
    <div className="flex items-center p-0.5 bg-surface-hover/80 rounded-lg border border-border/80 text-xs">
      <button
        onClick={() => onViewChange('board')}
        className={cn(
          'flex items-center gap-1.5 px-2.5 py-1 rounded-md font-medium transition-all duration-150',
          activeView === 'board'
            ? 'bg-surface text-foreground shadow-xs'
            : 'text-muted-foreground hover:text-foreground'
        )}
      >
        <Columns3 className="w-3.5 h-3.5" />
        <span>Board</span>
      </button>

      <button
        onClick={() => onViewChange('list')}
        className={cn(
          'flex items-center gap-1.5 px-2.5 py-1 rounded-md font-medium transition-all duration-150',
          activeView === 'list'
            ? 'bg-surface text-foreground shadow-xs'
            : 'text-muted-foreground hover:text-foreground'
        )}
      >
        <List className="w-3.5 h-3.5" />
        <span>List</span>
      </button>

      <button
        onClick={() => onViewChange('timeline')}
        className={cn(
          'flex items-center gap-1.5 px-2.5 py-1 rounded-md font-medium transition-all duration-150',
          activeView === 'timeline'
            ? 'bg-surface text-foreground shadow-xs'
            : 'text-muted-foreground hover:text-foreground'
        )}
      >
        <GanttChart className="w-3.5 h-3.5" />
        <span>Roadmap</span>
      </button>
    </div>
  );
}
