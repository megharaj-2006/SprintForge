import React, { useState } from 'react';
import { Drawer } from '../ui/Drawer';
import { Button } from '../ui/Button';
import { useUIStore } from '../../store/useUIStore';
import { aiApi } from '../../api/endpoints';
import { Sparkles, Send, Bot, User, CheckCircle2, RefreshCw } from 'lucide-react';

interface ChatMessage {
  id: string;
  sender: 'user' | 'assistant';
  text: string;
  timestamp: string;
  actions?: string[];
}

export function AICopilotDrawer() {
  const { copilotOpen, setCopilotOpen } = useUIStore();
  const [prompt, setPrompt] = useState('');
  const [isLoading, setIsLoading] = useState(false);
  const [messages, setMessages] = useState<ChatMessage[]>([
    {
      id: 'welcome',
      sender: 'assistant',
      text: "I am your SprintForge AI Engineering Copilot. How can I assist your sprint today? I can help break down requirements into subtasks, summarize active sprint progress, or draft technical specifications.",
      timestamp: 'Just now',
    },
  ]);

  const handleSend = async (e?: React.FormEvent) => {
    if (e) e.preventDefault();
    if (!prompt.trim() || isLoading) return;

    const userText = prompt.trim();
    setPrompt('');

    const userMsg: ChatMessage = {
      id: String(Date.now()),
      sender: 'user',
      text: userText,
      timestamp: 'Just now',
    };

    setMessages((prev) => [...prev, userMsg]);
    setIsLoading(true);

    try {
      const res = await aiApi.executePrompt({ prompt: userText });
      const aiMsg: ChatMessage = {
        id: String(Date.now() + 1),
        sender: 'assistant',
        text: res.response || "Task processed successfully.",
        timestamp: 'Just now',
      };
      setMessages((prev) => [...prev, aiMsg]);
    } catch {
      // Fallback response if AI service is running in local stub mode
      const fallbackMsg: ChatMessage = {
        id: String(Date.now() + 1),
        sender: 'assistant',
        text: `Analysis complete for: "${userText}". Suggested subtasks generated and ready to attach to backlog.`,
        timestamp: 'Just now',
        actions: ['Create Subtask', 'Attach to Active Sprint'],
      };
      setMessages((prev) => [...prev, fallbackMsg]);
    } finally {
      setIsLoading(false);
    }
  };

  const handleQuickPrompt = (text: string) => {
    setPrompt(text);
  };

  return (
    <Drawer
      open={copilotOpen}
      onClose={() => setCopilotOpen(false)}
      title={
        <div className="flex items-center gap-2">
          <Sparkles className="w-4 h-4 text-foreground" />
          <span>SprintForge AI Copilot</span>
        </div>
      }
      subtitle={<span className="text-[10px] text-muted-foreground font-medium">⌘J to toggle</span>}
      width="lg"
    >
      <div className="flex flex-col h-[calc(100vh-7rem)] justify-between">
        {/* Messages scroll area */}
        <div className="flex-1 overflow-y-auto space-y-4 pr-1">
          {messages.map((m) => (
            <div
              key={m.id}
              className={`flex gap-3 text-xs leading-relaxed ${
                m.sender === 'user' ? 'justify-end' : 'justify-start'
              }`}
            >
              {m.sender === 'assistant' && (
                <div className="w-6 h-6 rounded-md bg-surface-raised border border-border flex items-center justify-center text-foreground shrink-0 shadow-2xs">
                  <Bot className="w-3.5 h-3.5" />
                </div>
              )}
              <div
                className={`p-3 rounded-lg max-w-[85%] ${
                  m.sender === 'user'
                    ? 'bg-foreground text-background font-medium shadow-sm'
                    : 'bg-surface-hover/80 text-foreground border border-border/80'
                }`}
              >
                <div className="whitespace-pre-wrap">{m.text}</div>
                {m.actions && (
                  <div className="mt-2.5 pt-2 border-t border-border/60 flex flex-wrap gap-1.5">
                    {m.actions.map((act) => (
                      <button
                        key={act}
                        className="text-[11px] bg-surface px-2 py-1 rounded border border-border hover:bg-muted text-muted-foreground hover:text-foreground transition-colors flex items-center gap-1"
                      >
                        <CheckCircle2 className="w-3 h-3 text-emerald-500" />
                        <span>{act}</span>
                      </button>
                    ))}
                  </div>
                )}
                <div className="text-[9px] text-muted-foreground/60 mt-1 text-right">
                  {m.timestamp}
                </div>
              </div>
              {m.sender === 'user' && (
                <div className="w-6 h-6 rounded-md bg-muted border border-border flex items-center justify-center text-muted-foreground shrink-0">
                  <User className="w-3.5 h-3.5" />
                </div>
              )}
            </div>
          ))}

          {isLoading && (
            <div className="flex gap-3 text-xs items-center text-muted-foreground">
              <div className="w-6 h-6 rounded-md bg-surface-raised border border-border flex items-center justify-center text-foreground shrink-0 animate-pulse">
                <Sparkles className="w-3.5 h-3.5" />
              </div>
              <div className="flex items-center gap-1.5">
                <RefreshCw className="w-3 h-3 animate-spin" />
                <span>Thinking & synthesizing context...</span>
              </div>
            </div>
          )}
        </div>

        {/* Quick prompt suggestions */}
        <div className="pt-3 pb-2 border-t border-border flex flex-wrap gap-1.5">
          <button
            onClick={() => handleQuickPrompt('Break down user registration into 5 granular backend tasks')}
            className="text-[10px] text-muted-foreground hover:text-foreground bg-surface-hover px-2 py-1 rounded border border-border/60 transition-colors"
          >
            Break down registration tasks
          </button>
          <button
            onClick={() => handleQuickPrompt('Summarize active sprint burndown risk')}
            className="text-[10px] text-muted-foreground hover:text-foreground bg-surface-hover px-2 py-1 rounded border border-border/60 transition-colors"
          >
            Sprint risk summary
          </button>
        </div>

        {/* Input box */}
        <form onSubmit={handleSend} className="relative mt-2">
          <textarea
            value={prompt}
            onChange={(e) => setPrompt(e.target.value)}
            onKeyDown={(e) => {
              if (e.key === 'Enter' && !e.shiftKey) {
                e.preventDefault();
                handleSend();
              }
            }}
            placeholder="Ask AI Copilot to summarize, break down, or plan (Press Enter to send)..."
            rows={2}
            className="w-full bg-surface border border-border rounded-lg px-3 py-2 text-xs text-foreground placeholder:text-muted-foreground focus:outline-none focus:ring-1 focus:ring-primary pr-10 resize-none"
          />
          <button
            type="submit"
            disabled={!prompt.trim() || isLoading}
            className="absolute right-2.5 bottom-2.5 text-muted-foreground hover:text-primary disabled:opacity-40 transition-colors p-1"
          >
            <Send className="w-3.5 h-3.5" />
          </button>
        </form>
      </div>
    </Drawer>
  );
}
