import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useUIStore } from '../../store/useUIStore';
import { useWorkspaceStore } from '../../store/useWorkspaceStore';
import { useAuthStore } from '../../store/useAuthStore';
import { SprintForgeIcon } from '../brand/SprintForgeLogo';
import {
  Search,
  Sparkles,
  Bell,
  Menu,
  ChevronRight,
  LogOut,
  User as UserIcon,
  Shield,
  ExternalLink,
  Check,
  CheckCheck,
  CheckSquare,
  MessageSquare,
  AlertTriangle,
  GitPullRequest,
  X,
  Settings,
  Clock,
  Trash2,
} from 'lucide-react';

interface NotificationItem {
  id: string;
  type: 'assignment' | 'mention' | 'review' | 'alert' | 'system';
  title: string;
  body: string;
  timestamp: string;
  read: boolean;
  link?: string;
}

export function TopNavbar() {
  const { toggleSidebar, toggleCommandPalette, toggleCopilot } = useUIStore();
  const { currentWorkspace, currentProject } = useWorkspaceStore();
  const { user, logout } = useAuthStore();
  const navigate = useNavigate();

  const [userMenuOpen, setUserMenuOpen] = useState(false);
  const [notificationsOpen, setNotificationsOpen] = useState(false);
  const [notificationFilter, setNotificationFilter] = useState<'all' | 'unread' | 'mentions'>('all');

  // Clean notifications: single welcome notification for the user
  const [notifications, setNotifications] = useState<NotificationItem[]>([
    {
      id: 'notif-welcome',
      type: 'system',
      title: 'Welcome to SprintForge!',
      body: 'Welcome to SprintForge! Get started by creating your first project and inviting your team.',
      timestamp: 'Just now',
      read: false,
      link: '/projects',
    },
  ]);

  const avatarSrc = user?.profilePicture || user?.avatar || user?.avatarUrl;
  const unreadCount = notifications.filter((n) => !n.read).length;

  const markAllAsRead = () => {
    setNotifications((prev) => prev.map((n) => ({ ...n, read: true })));
  };

  const markItemAsRead = (id: string) => {
    setNotifications((prev) =>
      prev.map((n) => (n.id === id ? { ...n, read: true } : n))
    );
  };

  const removeNotification = (id: string, e: React.MouseEvent) => {
    e.stopPropagation();
    setNotifications((prev) => prev.filter((n) => n.id !== id));
  };

  const handleNotificationClick = (notif: NotificationItem) => {
    markItemAsRead(notif.id);
    setNotificationsOpen(false);
    if (notif.link) {
      navigate(notif.link);
    }
  };

  const filteredNotifications = notifications.filter((n) => {
    if (notificationFilter === 'unread') return !n.read;
    if (notificationFilter === 'mentions') return n.type === 'mention';
    return true;
  });

  const getNotifIcon = (type: NotificationItem['type']) => {
    switch (type) {
      case 'assignment':
        return <CheckSquare className="w-3.5 h-3.5 text-blue-500 shrink-0" />;
      case 'mention':
        return <MessageSquare className="w-3.5 h-3.5 text-amber-500 shrink-0" />;
      case 'review':
        return <GitPullRequest className="w-3.5 h-3.5 text-purple-500 shrink-0" />;
      case 'alert':
        return <AlertTriangle className="w-3.5 h-3.5 text-rose-500 shrink-0" />;
      case 'system':
      default:
        return <Sparkles className="w-3.5 h-3.5 text-emerald-500 shrink-0" />;
    }
  };

  return (
    <header className="h-11 border-b border-border/80 bg-surface/50 backdrop-blur-md px-4 flex items-center justify-between shrink-0 select-none z-30">
      {/* Left: Mobile Toggle & Breadcrumbs */}
      <div className="flex items-center gap-2 text-xs min-w-0">
        <button
          onClick={toggleSidebar}
          className="lg:hidden p-1 rounded hover:bg-surface-hover text-muted-foreground hover:text-foreground mr-1"
          aria-label="Toggle navigation"
        >
          <Menu className="w-4 h-4" />
        </button>

        <div className="flex items-center gap-1.5 text-muted-foreground truncate">
          <Link to="/projects" className="flex items-center gap-1.5 hover:opacity-80 transition-opacity">
            <SprintForgeIcon className="w-3.5 h-3.5 text-foreground shrink-0" />
            <span className="font-semibold text-foreground truncate">
              {currentWorkspace?.name || 'SprintForge'}
            </span>
          </Link>
          {currentProject && (
            <>
              <ChevronRight className="w-3.5 h-3.5 text-muted-foreground/60 shrink-0" />
              <span className="truncate font-medium text-foreground/90">
                {currentProject.name}
              </span>
            </>
          )}
        </div>
      </div>

      {/* Middle: Universal Search Bar Trigger */}
      <div className="flex-1 max-w-sm mx-4 hidden md:block">
        <button
          onClick={toggleCommandPalette}
          className="w-full h-7 px-2.5 rounded-md border border-border/70 bg-surface-hover/50 hover:bg-surface-hover hover:border-border text-xs text-muted-foreground flex items-center justify-between transition-all"
        >
          <div className="flex items-center gap-2">
            <Search className="w-3.5 h-3.5 text-muted-foreground" />
            <span className="text-[11px]">Search or jump to...</span>
          </div>
          <kbd className="text-[10px] font-mono text-muted-foreground/80 bg-muted/50 px-1.5 py-0.5 rounded border border-border/60">
            ⌘K
          </kbd>
        </button>
      </div>

      {/* Right: Actions, AI Copilot, Notifications & User */}
      <div className="flex items-center gap-1.5 shrink-0">
        {/* Mobile Search Button */}
        <button
          onClick={toggleCommandPalette}
          className="md:hidden p-1.5 text-muted-foreground hover:text-foreground rounded hover:bg-surface-hover"
          title="Search (⌘K)"
        >
          <Search className="w-4 h-4" />
        </button>

        {/* AI Copilot trigger */}
        <button
          onClick={toggleCopilot}
          className="flex items-center gap-1.5 px-2 py-1 rounded text-xs font-medium text-foreground hover:bg-surface-hover border border-border/80 transition-colors shadow-2xs"
          title="Ask AI Copilot (⌘J)"
        >
          <Sparkles className="w-3.5 h-3.5 text-foreground" />
          <span className="hidden sm:inline text-[11px]">Copilot</span>
          <kbd className="hidden sm:inline text-[9px] opacity-70">⌘J</kbd>
        </button>

        {/* Notifications Bell with Interactive Popover */}
        <div className="relative">
          <button
            onClick={() => {
              setNotificationsOpen((prev) => !prev);
              setUserMenuOpen(false);
            }}
            className={`relative p-1.5 rounded transition-colors ${
              notificationsOpen
                ? 'bg-surface-active text-foreground'
                : 'text-muted-foreground hover:text-foreground hover:bg-surface-hover'
            }`}
            title="Notifications"
          >
            <Bell className="w-4 h-4" />
            {unreadCount > 0 && (
              <span className="absolute top-1 right-1 w-2 h-2 rounded-full bg-blue-500 animate-pulse" />
            )}
          </button>

          {/* Notifications Dropdown Panel */}
          {notificationsOpen && (
            <>
              <div
                className="fixed inset-0 z-40"
                onClick={() => setNotificationsOpen(false)}
              />
              <div className="absolute right-0 mt-1.5 w-80 sm:w-96 bg-surface border border-border shadow-2xl rounded-xl z-50 text-xs overflow-hidden animate-scale-in">
                {/* Header */}
                <div className="p-3 border-b border-border flex items-center justify-between bg-surface-raised/50">
                  <div className="flex items-center gap-2">
                    <span className="font-semibold text-foreground text-sm">Notifications</span>
                    {unreadCount > 0 && (
                      <span className="px-1.5 py-0.5 rounded-full text-[10px] font-semibold bg-blue-500/15 text-blue-500 dark:text-blue-400 border border-blue-500/20">
                        {unreadCount} new
                      </span>
                    )}
                  </div>
                  <div className="flex items-center gap-1.5">
                    {unreadCount > 0 && (
                      <button
                        onClick={markAllAsRead}
                        className="flex items-center gap-1 text-[11px] text-muted-foreground hover:text-foreground p-1 rounded hover:bg-surface-hover transition-colors"
                        title="Mark all as read"
                      >
                        <CheckCheck className="w-3.5 h-3.5" />
                        <span>Mark read</span>
                      </button>
                    )}
                    <button
                      onClick={() => setNotificationsOpen(false)}
                      className="p-1 rounded text-muted-foreground hover:text-foreground hover:bg-surface-hover transition-colors"
                    >
                      <X className="w-3.5 h-3.5" />
                    </button>
                  </div>
                </div>

                {/* Filter Tabs */}
                <div className="flex items-center gap-1 px-3 py-1.5 border-b border-border/80 bg-surface text-[11px]">
                  <button
                    onClick={() => setNotificationFilter('all')}
                    className={`px-2 py-0.5 rounded-md font-medium transition-colors ${
                      notificationFilter === 'all'
                        ? 'bg-surface-active text-foreground font-semibold shadow-xs'
                        : 'text-muted-foreground hover:text-foreground'
                    }`}
                  >
                    All ({notifications.length})
                  </button>
                  <button
                    onClick={() => setNotificationFilter('unread')}
                    className={`px-2 py-0.5 rounded-md font-medium transition-colors ${
                      notificationFilter === 'unread'
                        ? 'bg-surface-active text-foreground font-semibold shadow-xs'
                        : 'text-muted-foreground hover:text-foreground'
                    }`}
                  >
                    Unread ({unreadCount})
                  </button>
                  <button
                    onClick={() => setNotificationFilter('mentions')}
                    className={`px-2 py-0.5 rounded-md font-medium transition-colors ${
                      notificationFilter === 'mentions'
                        ? 'bg-surface-active text-foreground font-semibold shadow-xs'
                        : 'text-muted-foreground hover:text-foreground'
                    }`}
                  >
                    Mentions
                  </button>
                </div>

                {/* Notification List */}
                <div className="max-h-80 overflow-y-auto divide-y divide-border/60">
                  {filteredNotifications.length === 0 ? (
                    <div className="p-8 text-center text-muted-foreground">
                      <CheckCircleNotificationEmpty />
                      <p className="font-medium text-foreground text-xs mt-2">All caught up!</p>
                      <p className="text-[11px] text-muted-foreground mt-0.5">No notifications in this view.</p>
                    </div>
                  ) : (
                    filteredNotifications.map((notif) => (
                      <div
                        key={notif.id}
                        onClick={() => handleNotificationClick(notif)}
                        className={`p-3 flex items-start gap-2.5 hover:bg-surface-hover/70 cursor-pointer transition-colors relative group ${
                          !notif.read ? 'bg-surface-raised/30' : ''
                        }`}
                      >
                        <div className="mt-0.5 p-1 rounded bg-surface-raised border border-border shrink-0">
                          {getNotifIcon(notif.type)}
                        </div>
                        <div className="flex-1 min-w-0 pr-6">
                          <div className="flex items-center justify-between gap-2">
                            <span
                              className={`truncate font-semibold text-xs ${
                                !notif.read ? 'text-foreground' : 'text-muted-foreground'
                              }`}
                            >
                              {notif.title}
                            </span>
                            <span className="text-[10px] text-muted-foreground shrink-0 tabular-nums">
                              {notif.timestamp}
                            </span>
                          </div>
                          <p className="text-[11px] text-muted-foreground line-clamp-2 mt-0.5">
                            {notif.body}
                          </p>
                        </div>

                        {/* Unread dot or dismiss */}
                        <div className="absolute right-3 top-3 flex items-center gap-1">
                          {!notif.read && (
                            <span className="w-1.5 h-1.5 rounded-full bg-blue-500 shrink-0" />
                          )}
                          <button
                            onClick={(e) => removeNotification(notif.id, e)}
                            className="opacity-0 group-hover:opacity-100 p-1 text-muted-foreground hover:text-foreground rounded transition-opacity"
                            title="Dismiss"
                          >
                            <X className="w-3 h-3" />
                          </button>
                        </div>
                      </div>
                    ))
                  )}
                </div>

                {/* Footer */}
                <div className="p-2 border-t border-border bg-surface-raised/40 flex items-center justify-between text-[11px]">
                  <Link
                    to="/settings/profile"
                    onClick={() => setNotificationsOpen(false)}
                    className="flex items-center gap-1.5 text-muted-foreground hover:text-foreground transition-colors"
                  >
                    <Settings className="w-3 h-3" />
                    <span>Notification Settings</span>
                  </Link>
                  {notifications.length > 0 && (
                    <button
                      onClick={() => setNotifications([])}
                      className="text-muted-foreground hover:text-rose-500 transition-colors"
                    >
                      Clear all
                    </button>
                  )}
                </div>
              </div>
            </>
          )}
        </div>

        <div className="h-4 w-px bg-border/80 mx-1" />

        {/* User Avatar & Dropdown */}
        <div className="relative">
          <button
            onClick={() => {
              setUserMenuOpen((prev) => !prev);
              setNotificationsOpen(false);
            }}
            className="flex items-center gap-2 p-1 rounded-full hover:ring-2 hover:ring-border transition-all"
            title={user?.fullName || user?.username || 'User Profile'}
          >
            {avatarSrc ? (
              <img
                src={avatarSrc}
                alt={user?.username || 'User'}
                className="w-6 h-6 rounded-full object-cover border border-border"
              />
            ) : (
              <div className="w-6 h-6 rounded-full bg-surface-raised border border-border text-foreground text-[10px] font-bold flex items-center justify-center uppercase shadow-2xs">
                {user?.username?.slice(0, 2) || 'SF'}
              </div>
            )}
          </button>

          {/* User Dropdown Menu */}
          {userMenuOpen && (
            <>
              <div
                className="fixed inset-0 z-40"
                onClick={() => setUserMenuOpen(false)}
              />
              <div className="absolute right-0 mt-1.5 w-56 bg-surface border border-border shadow-xl rounded-lg py-1 z-50 text-xs animate-scale-in">
                <div className="px-3 py-2 border-b border-border flex items-center gap-2.5">
                  {avatarSrc ? (
                    <img
                      src={avatarSrc}
                      alt={user?.username || 'User'}
                      className="w-8 h-8 rounded-full object-cover border border-border shrink-0"
                    />
                  ) : (
                    <div className="w-8 h-8 rounded-full bg-surface-raised border border-border text-foreground text-xs font-bold flex items-center justify-center uppercase shrink-0 shadow-2xs">
                      {user?.username?.slice(0, 2) || 'SF'}
                    </div>
                  )}
                  <div className="min-w-0 flex-1">
                    <p className="font-semibold text-foreground truncate">
                      {user?.fullName || user?.username}
                    </p>
                    <p className="text-[11px] text-muted-foreground truncate">{user?.email}</p>
                    <span className="inline-block mt-1 text-[9px] font-semibold bg-surface-raised border border-border px-1.5 py-0.2 rounded text-foreground uppercase tracking-wider">
                      {user?.role || 'DEVELOPER'}
                    </span>
                  </div>
                </div>

                <Link
                  to="/settings/profile"
                  onClick={() => setUserMenuOpen(false)}
                  className="flex items-center gap-2 px-3 py-1.5 text-foreground hover:bg-surface-hover transition-colors"
                >
                  <UserIcon className="w-3.5 h-3.5 text-muted-foreground" />
                  <span>Profile Settings</span>
                </Link>

                <Link
                  to="/admin/platform"
                  onClick={() => setUserMenuOpen(false)}
                  className="flex items-center gap-2 px-3 py-1.5 text-foreground hover:bg-surface-hover transition-colors"
                >
                  <Shield className="w-3.5 h-3.5 text-muted-foreground" />
                  <span>Platform Administration</span>
                </Link>

                <div className="border-t border-border my-1" />

                <button
                  onClick={logout}
                  className="w-full text-left flex items-center gap-2 px-3 py-1.5 text-red-500 hover:bg-red-500/10 transition-colors"
                >
                  <LogOut className="w-3.5 h-3.5" />
                  <span>Sign Out</span>
                </button>
              </div>
            </>
          )}
        </div>
      </div>
    </header>
  );
}

function CheckCircleNotificationEmpty() {
  return (
    <div className="w-9 h-9 rounded-full bg-surface-raised border border-border mx-auto flex items-center justify-center text-muted-foreground">
      <Check className="w-4 h-4 text-emerald-500" />
    </div>
  );
}
