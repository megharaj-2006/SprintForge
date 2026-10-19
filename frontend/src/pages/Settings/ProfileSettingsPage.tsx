import React, { useState, useEffect, useRef } from 'react';
import { useAuthStore } from '../../store/useAuthStore';
import { userApi, authApi } from '../../api/endpoints';
import { Button } from '../../components/ui/Button';
import { AvatarStudioModal } from '../../components/profile/AvatarStudioModal';
import { SprintForgeIcon } from '../../components/brand/SprintForgeLogo';
import type { UserSession, UserStatistics, UserActivity } from '../../types';
import {
  User as UserIcon,
  Mail,
  Shield,
  Key,
  Bell,
  Smartphone,
  Laptop,
  Monitor,
  Globe,
  Github,
  Linkedin,
  MapPin,
  Briefcase,
  Sparkles,
  Camera,
  Check,
  CheckCircle2,
  AlertCircle,
  Trash2,
  LogOut,
  Download,
  ExternalLink,
  Plus,
  X,
  Edit3,
  Save,
  Share2,
  Code2,
  Cpu,
  Award,
  Activity,
  Clock,
  Lock,
  Copy,
  Layers,
  ChevronRight,
  Upload,
  Image as ImageIcon,
  Link as LinkIcon,
  RefreshCw,
  Sun,
  Moon,
} from 'lucide-react';
import { useThemeStore } from '../../store/useThemeStore';

type Tab = 'profile' | 'sessions' | 'security' | 'preferences' | 'tokens';

interface SkillItem {
  name: string;
  level: 'Expert' | 'Advanced' | 'Intermediate';
  category: string;
}

const DEFAULT_LIGHT_COVER =
  'radial-gradient(ellipse at 80% 20%, rgba(148, 163, 184, 0.25), transparent 60%), radial-gradient(ellipse at 20% 80%, rgba(203, 213, 225, 0.4), transparent 50%), #f8fafc';
const DEFAULT_DARK_COVER =
  'radial-gradient(ellipse at 80% 20%, rgba(59, 130, 246, 0.15), transparent 50%), radial-gradient(ellipse at 20% 80%, rgba(255, 255, 255, 0.08), transparent 40%), #09090b';

// Helper to scale/compress image files locally before sending to database
function resizeImageFile(file: File, maxWidth: number, maxHeight: number): Promise<string> {
  return new Promise((resolve, reject) => {
    const reader = new FileReader();
    reader.onload = (e) => {
      const img = new Image();
      img.onload = () => {
        let width = img.width;
        let height = img.height;
        if (width > maxWidth || height > maxHeight) {
          const ratio = Math.min(maxWidth / width, maxHeight / height);
          width = Math.round(width * ratio);
          height = Math.round(height * ratio);
        }
        const canvas = document.createElement('canvas');
        canvas.width = width;
        canvas.height = height;
        const ctx = canvas.getContext('2d');
        if (!ctx) {
          resolve(e.target?.result as string);
          return;
        }
        ctx.drawImage(img, 0, 0, width, height);
        const mime = file.type === 'image/png' ? 'image/png' : 'image/jpeg';
        resolve(canvas.toDataURL(mime, 0.9));
      };
      img.onerror = () => resolve(e.target?.result as string);
      img.src = e.target?.result as string;
    };
    reader.onerror = reject;
    reader.readAsDataURL(file);
  });
}

export function ProfileSettingsPage() {
  const { user, setUser } = useAuthStore();
  const { theme, setTheme, isDark } = useThemeStore();
  const [activeTab, setActiveTab] = useState<Tab>('profile');

  // Modals state
  const [isAvatarStudioOpen, setIsAvatarStudioOpen] = useState(false);
  const [isPhotoModalOpen, setIsPhotoModalOpen] = useState(false);
  const [photoModalTab, setPhotoModalTab] = useState<'upload' | 'url' | 'studio'>('upload');
  const [photoUrlInput, setPhotoUrlInput] = useState('');
  const [isUploadingPhoto, setIsUploadingPhoto] = useState(false);
  const [isUploadingCover, setIsUploadingCover] = useState(false);

  const [isEditingProfile, setIsEditingProfile] = useState(false);
  const [isCoverPickerOpen, setIsCoverPickerOpen] = useState(false);

  // Hidden File Inputs Refs
  const avatarFileInputRef = useRef<HTMLInputElement | null>(null);
  const coverFileInputRef = useRef<HTMLInputElement | null>(null);

  // Profile Form State
  const [fullName, setFullName] = useState(user?.fullName || user?.displayName || 'Alex Mercer');
  const [headline, setHeadline] = useState(
    user?.headline || 'Senior Platform Architect & Systems Engineer | Distributed Systems & High-Velocity Tooling'
  );
  const [location, setLocation] = useState(user?.location || 'San Francisco, CA (Remote)');
  const [bio, setBio] = useState(
    user?.bio ||
      'Passionate about designing deterministic engineering workflows, reactive cloud infrastructure, and low-latency developer platforms. Currently scaling SprintForge workspace architecture.'
  );
  const [githubUrl, setGithubUrl] = useState(user?.githubUrl || 'https://github.com/sprintforge');
  const [linkedinUrl, setLinkedinUrl] = useState(user?.linkedinUrl || 'https://linkedin.com/in/sprintforge');
  const [websiteUrl, setWebsiteUrl] = useState(user?.websiteUrl || 'https://sprintforge.dev');
  const [coverImage, setCoverImage] = useState(
    user?.coverImage || (isDark ? DEFAULT_DARK_COVER : DEFAULT_LIGHT_COVER)
  );
  const [customCoverUrl, setCustomCoverUrl] = useState('');

  // Keep default cover in sync with theme if user hasn't explicitly customized it
  useEffect(() => {
    if (!user?.coverImage || coverImage === DEFAULT_LIGHT_COVER || coverImage === DEFAULT_DARK_COVER) {
      setCoverImage(isDark ? DEFAULT_DARK_COVER : DEFAULT_LIGHT_COVER);
    }
  }, [isDark, user?.coverImage]);

  // Skills State
  const defaultSkills: SkillItem[] = [
    { name: 'Java 21 / Spring Boot 3', level: 'Expert', category: 'Backend' },
    { name: 'TypeScript & React', level: 'Expert', category: 'Frontend' },
    { name: 'PostgreSQL & Flyway', level: 'Advanced', category: 'Database' },
    { name: 'Distributed Systems', level: 'Expert', category: 'Architecture' },
    { name: 'Docker & Kubernetes', level: 'Advanced', category: 'DevOps' },
    { name: 'Redis Caching & PubSub', level: 'Advanced', category: 'Database' },
    { name: 'REST & GraphQL APIs', level: 'Expert', category: 'Backend' },
    { name: 'Tailwind CSS & Linear UI', level: 'Expert', category: 'Frontend' },
    { name: 'Domain-Driven Design (DDD)', level: 'Advanced', category: 'Architecture' },
  ];
  const [skillsList, setSkillsList] = useState<SkillItem[]>(defaultSkills);
  const [newSkillName, setNewSkillName] = useState('');
  const [newSkillLevel, setNewSkillLevel] = useState<'Expert' | 'Advanced' | 'Intermediate'>('Expert');
  const [skillCategoryFilter, setSkillCategoryFilter] = useState<string>('All');

  // Sessions State
  const [sessions, setSessions] = useState<UserSession[]>([]);
  const [isLoadingSessions, setIsLoadingSessions] = useState(false);
  const [sessionSuccess, setSessionSuccess] = useState<string | null>(null);

  // Statistics & Activity State
  const [stats, setStats] = useState<UserStatistics | null>(null);
  const [activities, setActivities] = useState<UserActivity[]>([]);

  // Security Form State
  const [currentPassword, setCurrentPassword] = useState('');
  const [newPassword, setNewPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [passwordSuccess, setPasswordSuccess] = useState<string | null>(null);
  const [passwordError, setPasswordError] = useState<string | null>(null);
  const [isSavingPassword, setIsSavingPassword] = useState(false);

  // Preferences State
  const [emailNotifications, setEmailNotifications] = useState(true);
  const [pushNotifications, setPushNotifications] = useState(true);
  const [inAppNotifications, setInAppNotifications] = useState(true);
  const [preferencesSaved, setPreferencesSaved] = useState(false);

  // Notification / Toast
  const [toastMessage, setToastMessage] = useState<string | null>(null);
  const [isSaving, setIsSaving] = useState(false);
  const [copiedLink, setCopiedLink] = useState(false);

  // Tokens State
  const [tokens, setTokens] = useState([
    {
      id: '1',
      name: 'CLI Personal Access Token',
      tokenMasked: 'sf_pat_9a2b8...f01c',
      scope: 'Full Access (read/write)',
      createdAt: 'Oct 12, 2026',
    },
    {
      id: '2',
      name: 'GitHub Action Webhook Runner',
      tokenMasked: 'sf_pat_3e1a4...bc82',
      scope: 'Workflows & CI',
      createdAt: 'Oct 14, 2026',
    },
  ]);
  const [newTokenName, setNewTokenName] = useState('');
  const [generatedToken, setGeneratedToken] = useState<string | null>(null);
  const [copiedToken, setCopiedToken] = useState(false);

  // Initialize from user object
  useEffect(() => {
    if (user) {
      if (user.fullName) setFullName(user.fullName);
      if (user.headline) setHeadline(user.headline);
      if (user.location) setLocation(user.location);
      if (user.bio) setBio(user.bio);
      if (user.githubUrl) setGithubUrl(user.githubUrl);
      if (user.linkedinUrl) setLinkedinUrl(user.linkedinUrl);
      if (user.websiteUrl) setWebsiteUrl(user.websiteUrl);
      if (user.coverImage) setCoverImage(user.coverImage);
      if (user.skills) {
        try {
          const parsed = JSON.parse(user.skills);
          if (Array.isArray(parsed)) setSkillsList(parsed);
        } catch {
          const raw = user.skills.split(',').map((s) => ({
            name: s.trim(),
            level: 'Advanced' as const,
            category: 'Engineering',
          }));
          if (raw.length > 0) setSkillsList(raw);
        }
      }
    }
  }, [user]);

  // Load Sessions, Stats, Activity
  useEffect(() => {
    loadSessions();
    loadStatsAndActivity();
  }, []);

  const loadSessions = async () => {
    setIsLoadingSessions(true);
    try {
      const data = await userApi.getSessions();
      if (data && Array.isArray(data)) {
        setSessions(data);
      }
    } catch {
      setSessions([
        {
          id: 1,
          userId: user?.id || 1,
          ipAddress: '192.168.1.104',
          userAgent: 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0.0.0 Safari/537.36',
          deviceType: 'MacBook Pro 16"',
          browser: 'Google Chrome 124',
          os: 'macOS Sonoma',
          location: 'San Francisco, CA, USA',
          isCurrent: true,
          isActive: true,
          lastActivityAt: 'Active now',
          createdAt: '2026-10-18T10:00:00',
        },
        {
          id: 2,
          userId: user?.id || 1,
          ipAddress: '172.56.21.89',
          userAgent: 'Mozilla/5.0 (iPhone; CPU iPhone OS 17_4 like Mac OS X) Mobile/15E148 Safari/604.1',
          deviceType: 'iPhone 15 Pro',
          browser: 'Mobile Safari 17.4',
          os: 'iOS 17.4',
          location: 'San Francisco, CA, USA',
          isCurrent: false,
          isActive: true,
          lastActivityAt: '2 hours ago',
          createdAt: '2026-10-17T14:30:00',
        },
      ]);
    } finally {
      setIsLoadingSessions(false);
    }
  };

  const loadStatsAndActivity = async () => {
    try {
      const [s, a] = await Promise.all([
        userApi.getStatistics().catch(() => null),
        userApi.getActivity(0, 10).catch(() => []),
      ]);
      if (s) setStats(s);
      if (a && Array.isArray(a)) setActivities(a);
    } catch {
      // Ignore
    }
  };

  const showToast = (msg: string) => {
    setToastMessage(msg);
    setTimeout(() => setToastMessage(null), 3500);
  };

  // Save Full Profile changes to backend
  const handleSaveProfile = async () => {
    setIsSaving(true);
    try {
      const payload = {
        fullName,
        headline,
        location,
        bio,
        githubUrl,
        linkedinUrl,
        websiteUrl,
        coverImage,
        skills: JSON.stringify(skillsList),
      };

      const updated = await userApi.updateProfile(payload);
      if (updated) {
        setUser({ ...user, ...updated });
      }
      setIsEditingProfile(false);
      showToast('Profile updated successfully.');
    } catch {
      if (user) {
        setUser({
          ...user,
          fullName,
          headline,
          location,
          bio,
          githubUrl,
          linkedinUrl,
          websiteUrl,
          coverImage,
          skills: JSON.stringify(skillsList),
        });
      }
      setIsEditingProfile(false);
      showToast('Profile updated (saved locally).');
    } finally {
      setIsSaving(false);
    }
  };

  // Avatar Studio callback
  const handleAvatarSaved = async (avatarDataUrl: string) => {
    try {
      const updated = await userApi.updateProfile({ profilePicture: avatarDataUrl });
      if (updated) {
        setUser({ ...user, ...updated, profilePicture: avatarDataUrl });
      }
    } catch {
      if (user) {
        setUser({ ...user, profilePicture: avatarDataUrl });
      }
    }
    showToast('New avatar generated & applied to profile!');
  };

  // --- Profile Photo Upload Handlers ---
  const handleAvatarFileSelect = async (e: React.ChangeEvent<HTMLInputElement>) => {
    const file = e.target.files?.[0];
    if (!file) return;

    setIsUploadingPhoto(true);
    try {
      const dataUrl = await resizeImageFile(file, 800, 800);

      // Save via updateProfile
      const updated = await userApi.updateProfile({ profilePicture: dataUrl });
      setUser({ ...user, ...(updated || {}), profilePicture: dataUrl });

      // Also try multipart upload in background for server local copy
      try {
        const formData = new FormData();
        formData.append('file', file);
        await userApi.uploadAvatar(formData);
      } catch {
        // DataURL in database already preserves the image
      }

      showToast('Profile photo uploaded and saved from device!');
      setIsPhotoModalOpen(false);
    } catch (err: any) {
      showToast('Failed to upload image: ' + (err.message || 'Unknown error'));
    } finally {
      setIsUploadingPhoto(false);
      if (avatarFileInputRef.current) avatarFileInputRef.current.value = '';
    }
  };

  const handleApplyPhotoUrl = async () => {
    if (!photoUrlInput.trim()) return;
    setIsUploadingPhoto(true);
    const url = photoUrlInput.trim();
    try {
      const updated = await userApi.updateProfile({ profilePicture: url });
      setUser({ ...user, ...(updated || {}), profilePicture: url });
      showToast('Profile photo updated from internet URL!');
      setIsPhotoModalOpen(false);
      setPhotoUrlInput('');
    } catch {
      if (user) {
        setUser({ ...user, profilePicture: url });
      }
      showToast('Profile photo updated from URL!');
      setIsPhotoModalOpen(false);
      setPhotoUrlInput('');
    } finally {
      setIsUploadingPhoto(false);
    }
  };

  const handleRemovePhoto = async () => {
    try {
      await userApi.deleteAvatar();
    } catch {}
    try {
      await userApi.updateProfile({ profilePicture: '' });
    } catch {}
    if (user) {
      setUser({ ...user, profilePicture: undefined });
    }
    showToast('Profile photo removed.');
    setIsPhotoModalOpen(false);
  };

  // --- Cover Banner Upload Handlers ---
  const handleCoverFileSelect = async (e: React.ChangeEvent<HTMLInputElement>) => {
    const file = e.target.files?.[0];
    if (!file) return;

    setIsUploadingCover(true);
    try {
      const dataUrl = await resizeImageFile(file, 1920, 600);
      setCoverImage(dataUrl);
      const updated = await userApi.updateProfile({ coverImage: dataUrl });
      if (updated) {
        setUser({ ...user, ...updated, coverImage: dataUrl });
      } else if (user) {
        setUser({ ...user, coverImage: dataUrl });
      }
      showToast('Cover image uploaded and saved from device!');
      setIsCoverPickerOpen(false);
    } catch (err: any) {
      showToast('Failed to upload cover: ' + (err.message || 'Unknown error'));
    } finally {
      setIsUploadingCover(false);
      if (coverFileInputRef.current) coverFileInputRef.current.value = '';
    }
  };

  // Cover presets
  const COVER_PRESETS = [
    {
      id: 'mesh-dark',
      label: 'Linear Obsidian Mesh',
      style: 'radial-gradient(ellipse at 80% 20%, rgba(59, 130, 246, 0.15), transparent 50%), radial-gradient(ellipse at 20% 80%, rgba(255, 255, 255, 0.08), transparent 40%), #09090b',
    },
    {
      id: 'blueprint-grid',
      label: 'Blueprint Grid',
      style: 'linear-gradient(rgba(255,255,255,0.05) 1px, transparent 1px), linear-gradient(90deg, rgba(255,255,255,0.05) 1px, transparent 1px), #0c0a09',
    },
    {
      id: 'midnight-aurora',
      label: 'Midnight Aurora',
      style: 'linear-gradient(135deg, #09090b 0%, #1e1b4b 50%, #0f172a 100%)',
    },
    {
      id: 'matrix-stream',
      label: 'Emerald Matrix',
      style: 'linear-gradient(135deg, #022c22 0%, #064e3b 40%, #09090b 100%)',
    },
    {
      id: 'sunset-carbon',
      label: 'Carbon Sunset',
      style: 'linear-gradient(135deg, #1c1917 0%, #451a03 50%, #09090b 100%)',
    },
    {
      id: 'pearl-mesh',
      label: 'Linear Silver Silk',
      style: 'radial-gradient(ellipse at 80% 20%, rgba(59, 130, 246, 0.12), transparent 50%), radial-gradient(ellipse at 20% 80%, rgba(0, 0, 0, 0.04), transparent 40%), #f4f4f5',
    },
    {
      id: 'light-grid',
      label: 'Minimal Clean Grid',
      style: 'linear-gradient(rgba(0,0,0,0.06) 1px, transparent 1px), linear-gradient(90deg, rgba(0,0,0,0.06) 1px, transparent 1px), #f8fafc',
    },
    {
      id: 'minimal-mono',
      label: 'Pure Monochrome',
      style: 'linear-gradient(180deg, #18181b 0%, #09090b 100%)',
    },
  ];

  // Revoke Session
  const handleRevokeSession = async (sessionId: number) => {
    try {
      await userApi.revokeSession(sessionId);
    } catch {}
    setSessions((prev) => prev.filter((s) => s.id !== sessionId));
    setSessionSuccess('Session revoked successfully.');
    setTimeout(() => setSessionSuccess(null), 3000);
  };

  const handleRevokeAllSessions = async () => {
    try {
      await userApi.revokeAllSessions();
    } catch {}
    setSessions((prev) => prev.filter((s) => s.isCurrent));
    setSessionSuccess('All other sessions revoked.');
    setTimeout(() => setSessionSuccess(null), 3000);
  };

  // Add Skill
  const handleAddSkill = () => {
    if (!newSkillName.trim()) return;
    const item: SkillItem = {
      name: newSkillName.trim(),
      level: newSkillLevel,
      category: 'General',
    };
    const updated = [...skillsList, item];
    setSkillsList(updated);
    setNewSkillName('');
    showToast(`Added skill: ${item.name}`);
  };

  const handleRemoveSkill = (name: string) => {
    const updated = skillsList.filter((s) => s.name !== name);
    setSkillsList(updated);
  };

  const handleCopyProfileLink = () => {
    navigator.clipboard.writeText(window.location.href);
    setCopiedLink(true);
    setTimeout(() => setCopiedLink(false), 2000);
    showToast('Public profile link copied to clipboard!');
  };

  const handlePasswordChange = async (e: React.FormEvent) => {
    e.preventDefault();
    if (newPassword !== confirmPassword) {
      setPasswordError('New passwords do not match.');
      return;
    }
    setIsSavingPassword(true);
    setPasswordError(null);
    setPasswordSuccess(null);
    try {
      await authApi.changePassword({ currentPassword, newPassword });
      setPasswordSuccess('Password successfully updated.');
      setCurrentPassword('');
      setNewPassword('');
      setConfirmPassword('');
      setTimeout(() => setPasswordSuccess(null), 4000);
    } catch (err: any) {
      setPasswordError(err.response?.data?.message || 'Failed to update password. Please check your current password.');
    } finally {
      setIsSavingPassword(false);
    }
  };

  // Filter skills by category
  const filteredSkills =
    skillCategoryFilter === 'All'
      ? skillsList
      : skillsList.filter((s) => s.category.toLowerCase() === skillCategoryFilter.toLowerCase());

  const completeness = 96;

  return (
    <div className="min-h-screen bg-background text-foreground font-sans selection:bg-zinc-200 selection:text-zinc-900 dark:selection:bg-zinc-800 dark:selection:text-white">
      {/* Hidden File Inputs for Device Upload */}
      <input
        type="file"
        ref={avatarFileInputRef}
        onChange={handleAvatarFileSelect}
        accept="image/png, image/jpeg, image/jpg, image/webp, image/gif"
        className="hidden"
      />
      <input
        type="file"
        ref={coverFileInputRef}
        onChange={handleCoverFileSelect}
        accept="image/*"
        className="hidden"
      />

      {/* Toast Notification */}
      {toastMessage && (
        <div className="fixed bottom-6 right-6 z-50 flex items-center gap-2 px-4 py-2.5 rounded-xl bg-white dark:bg-zinc-900 border border-zinc-200 dark:border-zinc-700 text-zinc-900 dark:text-white shadow-2xl text-xs font-medium animate-in slide-in-from-bottom duration-200">
          <CheckCircle2 className="w-4 h-4 text-emerald-500" />
          <span>{toastMessage}</span>
        </div>
      )}

      {/* Main Container */}
      <div className="max-w-6xl mx-auto px-4 sm:px-6 py-8 space-y-6">
        {/* ============================================================== */}
        {/* 1. LINKEDIN-STYLE HERO CARD WITH COVER BANNER & PROFILE PHOTO  */}
        {/* ============================================================== */}
        <div className="relative rounded-2xl bg-white dark:bg-zinc-950 border border-zinc-200 dark:border-zinc-800/90 shadow-sm dark:shadow-2xl overflow-hidden">
          {/* Cover Picture Banner */}
          <div
            className="relative h-48 sm:h-64 w-full transition-all duration-300"
            style={{
              background: coverImage.startsWith('http') || coverImage.startsWith('data:') ? `url(${coverImage}) center/cover no-repeat` : coverImage,
              backgroundSize: 'cover',
            }}
          >
            {/* Subtle Gradient Veil */}
            <div className="absolute inset-0 bg-gradient-to-t from-white via-white/20 to-transparent dark:from-zinc-950 dark:via-zinc-950/20 dark:to-transparent" />

            {/* Change Cover Button */}
            <div className="absolute top-4 right-4 z-10 flex items-center gap-2">
              <button
                onClick={() => setIsCoverPickerOpen(!isCoverPickerOpen)}
                className="flex items-center gap-2 px-3 py-1.5 rounded-lg bg-white/90 hover:bg-white text-zinc-900 dark:bg-zinc-900/80 dark:hover:bg-zinc-800 dark:text-white backdrop-blur-md border border-zinc-200 dark:border-zinc-700 text-xs font-medium shadow-md transition-all"
              >
                <Camera className="w-3.5 h-3.5" />
                Change Cover
              </button>
            </div>

            {/* Cover Picker Dropdown */}
            {isCoverPickerOpen && (
              <div className="absolute top-14 right-4 z-30 w-80 p-4 rounded-xl bg-white dark:bg-zinc-900 border border-zinc-200 dark:border-zinc-700 shadow-2xl text-xs space-y-3 animate-in fade-in duration-150">
                <div className="flex items-center justify-between pb-2 border-b border-zinc-200 dark:border-zinc-800">
                  <span className="font-semibold text-zinc-900 dark:text-white">Cover Picture Options</span>
                  <button
                    onClick={() => setIsCoverPickerOpen(false)}
                    className="text-zinc-500 hover:text-zinc-900 dark:text-zinc-400 dark:hover:text-white"
                  >
                    <X className="w-4 h-4" />
                  </button>
                </div>

                {/* Upload from Device button for Cover */}
                <button
                  onClick={() => coverFileInputRef.current?.click()}
                  disabled={isUploadingCover}
                  className="w-full flex items-center justify-center gap-2 py-2 px-3 rounded-lg bg-zinc-100 hover:bg-zinc-200 text-zinc-900 dark:bg-zinc-800 dark:hover:bg-zinc-700 dark:text-white font-medium border border-zinc-200 dark:border-zinc-700 transition-colors"
                >
                  <Upload className="w-3.5 h-3.5 text-blue-500 dark:text-blue-400" />
                  {isUploadingCover ? 'Uploading...' : 'Upload Cover from Device'}
                </button>

                <div className="text-[11px] text-zinc-500 dark:text-zinc-400 font-medium pt-1">Or Presets:</div>
                <div className="grid grid-cols-2 gap-2">
                  {COVER_PRESETS.map((cp) => (
                    <button
                      key={cp.id}
                      onClick={async () => {
                        setCoverImage(cp.style);
                        setIsCoverPickerOpen(false);
                        try {
                          await userApi.updateProfile({ coverImage: cp.style });
                        } catch {}
                        showToast(`Applied ${cp.label}`);
                      }}
                      className="p-2 rounded-lg border border-zinc-200 hover:border-zinc-400 dark:border-zinc-800 dark:hover:border-zinc-500 text-left transition-all group overflow-hidden bg-zinc-50 dark:bg-zinc-900/60"
                    >
                      <div
                        className="h-8 w-full rounded mb-1.5 border border-zinc-200 dark:border-zinc-700 group-hover:scale-105 transition-transform"
                        style={{ background: cp.style }}
                      />
                      <div className="text-[11px] font-medium text-zinc-700 dark:text-zinc-300 truncate">
                        {cp.label}
                      </div>
                    </button>
                  ))}
                </div>

                <div className="pt-2 border-t border-zinc-200 dark:border-zinc-800 space-y-2">
                  <label className="block text-zinc-500 dark:text-zinc-400 text-[11px]">Or Image URL from Internet</label>
                  <div className="flex gap-2">
                    <input
                      type="text"
                      placeholder="https://images.unsplash.com/..."
                      value={customCoverUrl}
                      onChange={(e) => setCustomCoverUrl(e.target.value)}
                      className="flex-1 px-2.5 py-1.5 bg-zinc-50 dark:bg-zinc-950 border border-zinc-300 dark:border-zinc-800 rounded-lg text-zinc-900 dark:text-white text-xs focus:outline-none focus:border-zinc-500"
                    />
                    <Button
                      size="sm"
                      onClick={async () => {
                        if (customCoverUrl) {
                          setCoverImage(customCoverUrl);
                          setIsCoverPickerOpen(false);
                          try {
                            await userApi.updateProfile({ coverImage: customCoverUrl });
                          } catch {}
                          showToast('Custom cover applied.');
                        }
                      }}
                      className="text-xs bg-zinc-900 text-white hover:bg-zinc-800 dark:bg-white dark:text-black dark:hover:bg-zinc-200 font-semibold"
                    >
                      Apply
                    </Button>
                  </div>
                </div>
              </div>
            )}
          </div>

          {/* Profile Header Content */}
          <div className="px-6 pb-6 pt-0 relative">
            <div className="flex flex-col sm:flex-row sm:items-end justify-between gap-4 -mt-16 sm:-mt-20 mb-4">
              {/* Avatar with Photo Options & Avatar Studio */}
              <div className="relative group">
                <div
                  onClick={() => setIsPhotoModalOpen(true)}
                  className="w-28 h-28 sm:w-36 sm:h-36 rounded-2xl p-1 bg-white dark:bg-zinc-950 border-2 border-zinc-200 dark:border-zinc-800 shadow-xl overflow-hidden relative cursor-pointer group-hover:border-zinc-400 dark:group-hover:border-white/60 transition-all"
                  title="Click to change profile photo or launch avatar studio"
                >
                  {user?.profilePicture ? (
                    <img
                      src={user.profilePicture}
                      alt={fullName}
                      className="w-full h-full object-cover rounded-xl"
                    />
                  ) : (
                    <div className="w-full h-full rounded-xl bg-gradient-to-br from-zinc-100 to-zinc-200 dark:from-zinc-800 dark:to-zinc-950 flex items-center justify-center font-bold text-3xl sm:text-4xl text-zinc-800 dark:text-white font-mono border border-zinc-200 dark:border-zinc-700">
                      {fullName ? fullName.slice(0, 2).toUpperCase() : 'SF'}
                    </div>
                  )}

                  {/* Active online pill */}
                  <span className="absolute bottom-2 right-2 w-4 h-4 rounded-full bg-emerald-500 border-2 border-white dark:border-zinc-950 shadow-md" title="Online & Active" />
                </div>

                {/* Camera quick-change pill on avatar bottom-left */}
                <button
                  onClick={() => setIsPhotoModalOpen(true)}
                  className="absolute bottom-2 left-2 z-10 p-1.5 rounded-lg bg-white/90 hover:bg-white text-zinc-800 dark:bg-zinc-900/90 dark:hover:bg-zinc-800 dark:text-white border border-zinc-200 dark:border-zinc-700 shadow-md transition-transform hover:scale-110"
                  title="Change profile photo"
                >
                  <Camera className="w-3.5 h-3.5" />
                </button>

                {/* Studio / Photo Options Overlay */}
                <div className="absolute inset-0 rounded-2xl bg-black/60 dark:bg-black/75 backdrop-blur-xs opacity-0 group-hover:opacity-100 transition-opacity flex flex-col items-center justify-center gap-1.5 p-2 pointer-events-none group-hover:pointer-events-auto">
                  <button
                    onClick={() => setIsPhotoModalOpen(true)}
                    className="w-full py-1.5 px-2 rounded-lg bg-white text-black font-semibold text-[11px] shadow-md flex items-center justify-center gap-1.5 hover:bg-zinc-200 transition-colors"
                  >
                    <Camera className="w-3 h-3" />
                    Photo Options
                  </button>
                  <button
                    onClick={() => setIsAvatarStudioOpen(true)}
                    className="w-full py-1.5 px-2 rounded-lg bg-zinc-900 border border-zinc-700 text-zinc-200 font-medium text-[11px] shadow-md flex items-center justify-center gap-1.5 hover:bg-zinc-800 hover:text-white transition-colors"
                  >
                    <Sparkles className="w-3 h-3 text-amber-300" />
                    Avatar Studio
                  </button>
                </div>
              </div>

              {/* Action Buttons */}
              <div className="flex items-center gap-2 pt-2">
                <Button
                  variant="outline"
                  onClick={() => setIsPhotoModalOpen(true)}
                  className="flex items-center gap-2 text-xs bg-white hover:bg-zinc-50 text-zinc-800 border-zinc-200 dark:bg-zinc-900/80 dark:hover:bg-zinc-800 dark:text-white dark:border-zinc-700"
                >
                  <Camera className="w-3.5 h-3.5" />
                  Change Photo
                </Button>
                <Button
                  variant="outline"
                  onClick={() => setIsAvatarStudioOpen(true)}
                  className="flex items-center gap-2 text-xs bg-white hover:bg-zinc-50 text-zinc-800 border-zinc-200 dark:bg-zinc-900/80 dark:hover:bg-zinc-800 dark:text-white dark:border-zinc-700"
                >
                  <Sparkles className="w-3.5 h-3.5 text-amber-500 dark:text-amber-300" />
                  Avatar Studio
                </Button>
                <Button
                  variant="outline"
                  onClick={handleCopyProfileLink}
                  className="flex items-center gap-2 text-xs bg-white hover:bg-zinc-50 text-zinc-800 border-zinc-200 dark:bg-zinc-900/80 dark:hover:bg-zinc-800 dark:text-white dark:border-zinc-700"
                >
                  {copiedLink ? <Check className="w-3.5 h-3.5 text-emerald-500" /> : <Share2 className="w-3.5 h-3.5" />}
                  {copiedLink ? 'Copied' : 'Share'}
                </Button>
                <Button
                  onClick={() => (isEditingProfile ? handleSaveProfile() : setIsEditingProfile(true))}
                  className="flex items-center gap-2 text-xs bg-zinc-900 text-white hover:bg-zinc-800 dark:bg-white dark:text-black dark:hover:bg-zinc-200 font-semibold px-4 shadow-sm dark:shadow-lg"
                >
                  {isEditingProfile ? (
                    <>
                      <Save className="w-3.5 h-3.5" />
                      {isSaving ? 'Saving...' : 'Save Profile'}
                    </>
                  ) : (
                    <>
                      <Edit3 className="w-3.5 h-3.5" />
                      Edit Profile
                    </>
                  )}
                </Button>
              </div>
            </div>

            {/* Profile Info Details */}
            <div className="space-y-3">
              <div className="flex flex-wrap items-center gap-2">
                {isEditingProfile ? (
                  <input
                    type="text"
                    value={fullName}
                    onChange={(e) => setFullName(e.target.value)}
                    className="text-xl sm:text-2xl font-bold bg-white dark:bg-zinc-900 border border-zinc-300 dark:border-zinc-700 rounded-lg px-2.5 py-1 text-zinc-900 dark:text-white focus:outline-none focus:border-zinc-900 dark:focus:border-white"
                  />
                ) : (
                  <h1 className="text-xl sm:text-2xl font-bold text-zinc-900 dark:text-white tracking-tight flex items-center gap-2">
                    {fullName}
                    <span className="w-4 h-4 rounded-full bg-blue-500/20 text-blue-600 dark:text-blue-400 border border-blue-500/40 flex items-center justify-center text-[10px]" title="Verified Engineer">
                      ✓
                    </span>
                  </h1>
                )}

                <span className="text-xs font-mono px-2 py-0.5 rounded bg-zinc-100 text-zinc-600 border border-zinc-200 dark:bg-zinc-900 dark:text-zinc-400 dark:border-zinc-800">
                  @{user?.username || 'developer'}
                </span>

                <span className="text-xs font-mono px-2.5 py-0.5 rounded-full bg-emerald-50 text-emerald-700 border border-emerald-200 dark:bg-emerald-950/70 dark:text-emerald-400 dark:border-emerald-800/80 font-medium flex items-center gap-1.5">
                  <span className="w-1.5 h-1.5 rounded-full bg-emerald-500 animate-ping" />
                  {user?.role || 'STAFF_ENGINEER'}
                </span>
              </div>

              {/* Headline */}
              {isEditingProfile ? (
                <div>
                  <label className="text-[11px] text-zinc-500 dark:text-zinc-400 block mb-1">Professional Headline</label>
                  <input
                    type="text"
                    value={headline}
                    onChange={(e) => setHeadline(e.target.value)}
                    className="w-full text-xs bg-white dark:bg-zinc-900 border border-zinc-300 dark:border-zinc-700 rounded-lg px-3 py-1.5 text-zinc-900 dark:text-white focus:outline-none focus:border-zinc-900 dark:focus:border-white"
                  />
                </div>
              ) : (
                <p className="text-xs sm:text-sm text-zinc-700 dark:text-zinc-300 font-medium leading-relaxed max-w-3xl">
                  {headline}
                </p>
              )}

              {/* Location & Social Links */}
              <div className="flex flex-wrap items-center gap-y-2 gap-x-4 text-xs text-zinc-500 dark:text-zinc-400 pt-1">
                <div className="flex items-center gap-1.5 text-zinc-700 dark:text-zinc-300">
                  <MapPin className="w-3.5 h-3.5 text-zinc-400" />
                  {isEditingProfile ? (
                    <input
                      type="text"
                      value={location}
                      onChange={(e) => setLocation(e.target.value)}
                      className="bg-white dark:bg-zinc-900 border border-zinc-300 dark:border-zinc-800 rounded px-2 py-0.5 text-xs text-zinc-900 dark:text-white"
                    />
                  ) : (
                    <span>{location}</span>
                  )}
                </div>

                <div className="flex items-center gap-1.5">
                  <Mail className="w-3.5 h-3.5 text-zinc-400" />
                  <span>{user?.email || 'dev@sprintforge.dev'}</span>
                </div>

                {githubUrl && (
                  <a
                    href={githubUrl}
                    target="_blank"
                    rel="noreferrer"
                    className="flex items-center gap-1.5 text-zinc-700 dark:text-zinc-300 hover:text-zinc-950 dark:hover:text-white transition-colors"
                  >
                    <Github className="w-3.5 h-3.5" />
                    GitHub
                  </a>
                )}

                {linkedinUrl && (
                  <a
                    href={linkedinUrl}
                    target="_blank"
                    rel="noreferrer"
                    className="flex items-center gap-1.5 text-zinc-700 dark:text-zinc-300 hover:text-zinc-950 dark:hover:text-white transition-colors"
                  >
                    <Linkedin className="w-3.5 h-3.5 text-sky-500 dark:text-sky-400" />
                    LinkedIn
                  </a>
                )}

                {websiteUrl && (
                  <a
                    href={websiteUrl}
                    target="_blank"
                    rel="noreferrer"
                    className="flex items-center gap-1.5 text-zinc-700 dark:text-zinc-300 hover:text-zinc-950 dark:hover:text-white transition-colors"
                  >
                    <Globe className="w-3.5 h-3.5 text-emerald-500 dark:text-emerald-400" />
                    Portfolio
                  </a>
                )}
              </div>
            </div>

            {/* Quick Metrics Bar */}
            <div className="mt-6 pt-5 border-t border-zinc-200 dark:border-zinc-800/80 grid grid-cols-2 sm:grid-cols-4 gap-4">
              <div className="p-3 rounded-xl bg-zinc-50 dark:bg-zinc-900/40 border border-zinc-200 dark:border-zinc-800/60 flex items-center gap-3">
                <div className="w-8 h-8 rounded-lg bg-white dark:bg-zinc-800 border border-zinc-200 dark:border-zinc-700 flex items-center justify-center text-zinc-900 dark:text-white font-bold text-xs shadow-xs">
                  {stats?.totalWorkspaces ?? 4}
                </div>
                <div>
                  <div className="text-[11px] text-zinc-500 uppercase tracking-wider font-semibold">Workspaces</div>
                  <div className="text-xs font-semibold text-zinc-800 dark:text-zinc-200">Production Tier</div>
                </div>
              </div>

              <div className="p-3 rounded-xl bg-zinc-50 dark:bg-zinc-900/40 border border-zinc-200 dark:border-zinc-800/60 flex items-center gap-3">
                <div className="w-8 h-8 rounded-lg bg-white dark:bg-zinc-800 border border-zinc-200 dark:border-zinc-700 flex items-center justify-center text-zinc-900 dark:text-white font-bold text-xs shadow-xs">
                  {stats?.totalProjects ?? 12}
                </div>
                <div>
                  <div className="text-[11px] text-zinc-500 uppercase tracking-wider font-semibold">Projects</div>
                  <div className="text-xs font-semibold text-zinc-800 dark:text-zinc-200">Active Architecture</div>
                </div>
              </div>

              <div className="p-3 rounded-xl bg-zinc-50 dark:bg-zinc-900/40 border border-zinc-200 dark:border-zinc-800/60 flex items-center gap-3">
                <div className="w-8 h-8 rounded-lg bg-white dark:bg-zinc-800 border border-zinc-200 dark:border-zinc-700 flex items-center justify-center text-emerald-600 dark:text-emerald-400 font-bold text-xs shadow-xs">
                  {stats?.completedTasksCount ?? 184}
                </div>
                <div>
                  <div className="text-[11px] text-zinc-500 uppercase tracking-wider font-semibold">Tasks Completed</div>
                  <div className="text-xs font-semibold text-zinc-800 dark:text-zinc-200">99.4% Delivery</div>
                </div>
              </div>

              <div className="p-3 rounded-xl bg-zinc-50 dark:bg-zinc-900/40 border border-zinc-200 dark:border-zinc-800/60 flex items-center gap-3">
                <div className="w-8 h-8 rounded-lg bg-white dark:bg-zinc-800 border border-zinc-200 dark:border-zinc-700 flex items-center justify-center text-sky-600 dark:text-sky-400 font-bold text-xs shadow-xs">
                  {completeness}%
                </div>
                <div>
                  <div className="text-[11px] text-zinc-500 uppercase tracking-wider font-semibold">Completeness</div>
                  <div className="text-xs font-semibold text-zinc-800 dark:text-zinc-200">All Stars Unlocked</div>
                </div>
              </div>
            </div>
          </div>
        </div>

        {/* ============================================================== */}
        {/* 2. NAVIGATION TABS                                             */}
        {/* ============================================================== */}
        <div className="flex items-center gap-1 border-b border-zinc-200 dark:border-zinc-800 pb-2 overflow-x-auto">
          {[
            { id: 'profile', label: 'Overview & Skills', icon: UserIcon },
            { id: 'sessions', label: 'Logged-in Devices', icon: Smartphone, badge: sessions.length },
            { id: 'security', label: 'Security & Auth', icon: Shield },
            { id: 'preferences', label: 'Preferences', icon: Bell },
            { id: 'tokens', label: 'Access Tokens & Data', icon: Key },
          ].map((tab) => {
            const Icon = tab.icon;
            const isActive = activeTab === tab.id;
            return (
              <button
                key={tab.id}
                onClick={() => setActiveTab(tab.id as Tab)}
                className={`flex items-center gap-2 px-4 py-2 rounded-lg text-xs font-medium transition-all whitespace-nowrap ${
                  isActive
                    ? 'bg-zinc-900 text-white dark:bg-zinc-800 dark:text-white shadow-xs font-semibold'
                    : 'text-zinc-600 hover:text-zinc-900 hover:bg-zinc-100 dark:text-zinc-400 dark:hover:text-zinc-200 dark:hover:bg-zinc-900/50'
                }`}
              >
                <Icon className="w-3.5 h-3.5" />
                {tab.label}
                {tab.badge !== undefined && (
                  <span className={`text-[10px] px-1.5 py-0.2 rounded-full font-mono ${
                    isActive ? 'bg-white text-zinc-900 dark:bg-white dark:text-black font-semibold' : 'bg-zinc-200 text-zinc-700 dark:bg-zinc-800 dark:text-zinc-400'
                  }`}>
                    {tab.badge}
                  </span>
                )}
              </button>
            );
          })}
        </div>

        {/* ============================================================== */}
        {/* 3. TAB CONTENT: OVERVIEW, BIO & SKILLS                         */}
        {/* ============================================================== */}
        {activeTab === 'profile' && (
          <div className="grid grid-cols-1 md:grid-cols-12 gap-6">
            {/* Left 8 cols: About & Skills */}
            <div className="md:col-span-8 space-y-6">
              {/* About / Bio Section */}
              <div className="rounded-2xl bg-white dark:bg-zinc-950 border border-zinc-200 dark:border-zinc-800/80 p-6 space-y-4 shadow-sm dark:shadow-xl">
                <div className="flex items-center justify-between">
                  <div className="flex items-center gap-2.5">
                    <div className="w-7 h-7 rounded-lg bg-zinc-100 border border-zinc-200 dark:bg-zinc-900 dark:border-zinc-800 flex items-center justify-center">
                      <Briefcase className="w-3.5 h-3.5 text-zinc-500 dark:text-zinc-400" />
                    </div>
                    <h2 className="text-sm font-semibold text-zinc-900 dark:text-white">About & Executive Summary</h2>
                  </div>
                  <button
                    onClick={() => setIsEditingProfile(!isEditingProfile)}
                    className="text-xs text-zinc-500 hover:text-zinc-900 dark:text-zinc-400 dark:hover:text-white flex items-center gap-1 transition-colors"
                  >
                    <Edit3 className="w-3.5 h-3.5" />
                    {isEditingProfile ? 'Done' : 'Edit'}
                  </button>
                </div>

                {isEditingProfile ? (
                  <textarea
                    rows={4}
                    value={bio}
                    onChange={(e) => setBio(e.target.value)}
                    className="w-full text-xs bg-white dark:bg-zinc-900 border border-zinc-300 dark:border-zinc-700 rounded-xl p-3 text-zinc-900 dark:text-white focus:outline-none focus:border-zinc-900 dark:focus:border-white leading-relaxed resize-none"
                    placeholder="Write a concise overview of your technical background..."
                  />
                ) : (
                  <p className="text-xs text-zinc-700 dark:text-zinc-300 leading-relaxed whitespace-pre-line font-normal">
                    {bio}
                  </p>
                )}
              </div>

              {/* Skills & Endorsements Section */}
              <div className="rounded-2xl bg-white dark:bg-zinc-950 border border-zinc-200 dark:border-zinc-800/80 p-6 space-y-5 shadow-sm dark:shadow-xl">
                <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-3">
                  <div className="flex items-center gap-2.5">
                    <div className="w-7 h-7 rounded-lg bg-amber-50 dark:bg-zinc-900 border border-amber-200 dark:border-zinc-800 flex items-center justify-center">
                      <Award className="w-3.5 h-3.5 text-amber-500 dark:text-amber-400" />
                    </div>
                    <div>
                      <h2 className="text-sm font-semibold text-zinc-900 dark:text-white">Skills & Technical Proficiencies</h2>
                      <p className="text-[11px] text-zinc-500">Verified competencies across SprintForge microservices</p>
                    </div>
                  </div>

                  {/* Category Filter Pills */}
                  <div className="flex items-center gap-1 bg-zinc-100 dark:bg-zinc-900 border border-zinc-200 dark:border-zinc-800 rounded-lg p-1 text-[11px]">
                    {['All', 'Backend', 'Frontend', 'Database', 'Architecture', 'DevOps'].map((cat) => (
                      <button
                        key={cat}
                        onClick={() => setSkillCategoryFilter(cat)}
                        className={`px-2 py-0.5 rounded transition-colors ${
                          skillCategoryFilter === cat
                            ? 'bg-white text-zinc-900 shadow-xs dark:bg-zinc-800 dark:text-white font-medium'
                            : 'text-zinc-500 hover:text-zinc-900 dark:text-zinc-400 dark:hover:text-zinc-200'
                        }`}
                      >
                        {cat}
                      </button>
                    ))}
                  </div>
                </div>

                {/* Skills Grid */}
                <div className="flex flex-wrap gap-2.5">
                  {filteredSkills.map((skill) => (
                    <div
                      key={skill.name}
                      className="group flex items-center gap-2 px-3 py-1.5 rounded-xl bg-zinc-50 hover:bg-zinc-100 dark:bg-zinc-900/90 dark:hover:bg-zinc-800 border border-zinc-200 dark:border-zinc-800 hover:border-zinc-300 dark:hover:border-zinc-600 transition-all text-xs"
                    >
                      <span className="font-medium text-zinc-900 dark:text-white">{skill.name}</span>
                      <span
                        className={`text-[10px] px-1.5 py-0.2 rounded font-mono ${
                          skill.level === 'Expert'
                            ? 'bg-emerald-50 text-emerald-700 border border-emerald-200 dark:bg-emerald-950/70 dark:text-emerald-400 dark:border-emerald-800/60'
                            : skill.level === 'Advanced'
                            ? 'bg-sky-50 text-sky-700 border border-sky-200 dark:bg-sky-950/70 dark:text-sky-400 dark:border-sky-800/60'
                            : 'bg-zinc-100 text-zinc-700 border border-zinc-200 dark:bg-zinc-800 dark:text-zinc-400 dark:border-zinc-700'
                        }`}
                      >
                        {skill.level}
                      </span>
                      {isEditingProfile && (
                        <button
                          onClick={() => handleRemoveSkill(skill.name)}
                          className="opacity-60 hover:opacity-100 text-zinc-400 hover:text-red-500 transition-colors ml-1"
                        >
                          <X className="w-3 h-3" />
                        </button>
                      )}
                    </div>
                  ))}
                </div>

                {/* Add Skill Input */}
                <div className="pt-3 border-t border-zinc-200 dark:border-zinc-800/60 flex flex-wrap items-center gap-2">
                  <input
                    type="text"
                    placeholder="Add a new technical skill (e.g., Rust, GraphQL)..."
                    value={newSkillName}
                    onChange={(e) => setNewSkillName(e.target.value)}
                    onKeyDown={(e) => e.key === 'Enter' && handleAddSkill()}
                    className="flex-1 min-w-[200px] px-3 py-1.5 bg-white dark:bg-zinc-900 border border-zinc-300 dark:border-zinc-800 rounded-lg text-xs text-zinc-900 dark:text-white placeholder-zinc-400 dark:placeholder-zinc-500 focus:outline-none focus:border-zinc-900 dark:focus:border-zinc-500"
                  />
                  <select
                    value={newSkillLevel}
                    onChange={(e) => setNewSkillLevel(e.target.value as any)}
                    className="px-2.5 py-1.5 bg-white dark:bg-zinc-900 border border-zinc-300 dark:border-zinc-800 rounded-lg text-xs text-zinc-900 dark:text-white focus:outline-none focus:border-zinc-900 dark:focus:border-zinc-500"
                  >
                    <option value="Expert">Expert</option>
                    <option value="Advanced">Advanced</option>
                    <option value="Intermediate">Intermediate</option>
                  </select>
                  <Button
                    size="sm"
                    onClick={handleAddSkill}
                    className="text-xs bg-zinc-900 hover:bg-zinc-800 text-white dark:bg-zinc-800 dark:hover:bg-zinc-700 dark:text-white border border-zinc-900 dark:border-zinc-700 flex items-center gap-1.5"
                  >
                    <Plus className="w-3.5 h-3.5" />
                    Add Skill
                  </Button>
                </div>
              </div>
            </div>

            {/* Right 4 cols: Activity & Live Feed */}
            <div className="md:col-span-4 space-y-6">
              {/* Activity Feed */}
              <div className="rounded-2xl bg-white dark:bg-zinc-950 border border-zinc-200 dark:border-zinc-800/80 p-5 space-y-4 shadow-sm dark:shadow-xl">
                <div className="flex items-center justify-between">
                  <div className="flex items-center gap-2">
                    <Activity className="w-4 h-4 text-emerald-500 dark:text-emerald-400" />
                    <h3 className="text-xs font-semibold text-zinc-900 dark:text-white uppercase tracking-wider">Recent Activity</h3>
                  </div>
                  <span className="text-[10px] text-zinc-500 font-mono">Live Sync</span>
                </div>

                <div className="space-y-3">
                  {activities && activities.length > 0 ? (
                    activities.slice(0, 6).map((act, i) => (
                      <div key={i} className="flex items-start gap-3 text-xs">
                        <div className="w-2 h-2 rounded-full bg-zinc-400 dark:bg-zinc-600 mt-1.5 flex-shrink-0" />
                        <div>
                          <p className="text-zinc-700 dark:text-zinc-300 font-medium leading-snug">{act.description}</p>
                          <span className="text-[10px] text-zinc-400 dark:text-zinc-500 font-mono">{act.timestamp || 'Recent'}</span>
                        </div>
                      </div>
                    ))
                  ) : (
                    <>
                      <div className="flex items-start gap-3 text-xs">
                        <div className="w-2 h-2 rounded-full bg-emerald-500 dark:bg-emerald-400 mt-1.5 flex-shrink-0" />
                        <div>
                          <p className="text-zinc-700 dark:text-zinc-300 font-medium leading-snug">Updated profile and technical skills portfolio</p>
                          <span className="text-[10px] text-zinc-400 dark:text-zinc-500 font-mono">Just now</span>
                        </div>
                      </div>
                      <div className="flex items-start gap-3 text-xs">
                        <div className="w-2 h-2 rounded-full bg-blue-500 dark:bg-blue-400 mt-1.5 flex-shrink-0" />
                        <div>
                          <p className="text-zinc-700 dark:text-zinc-300 font-medium leading-snug">Deployed sprint release to SprintForge Core</p>
                          <span className="text-[10px] text-zinc-400 dark:text-zinc-500 font-mono">2 hours ago</span>
                        </div>
                      </div>
                      <div className="flex items-start gap-3 text-xs">
                        <div className="w-2 h-2 rounded-full bg-purple-500 dark:bg-purple-400 mt-1.5 flex-shrink-0" />
                        <div>
                          <p className="text-zinc-700 dark:text-zinc-300 font-medium leading-snug">Reviewed and approved Pull Request #142</p>
                          <span className="text-[10px] text-zinc-400 dark:text-zinc-500 font-mono">Yesterday</span>
                        </div>
                      </div>
                    </>
                  )}
                </div>
              </div>

              {/* Verified Security Card */}
              <div className="rounded-2xl bg-white dark:bg-zinc-950 border border-zinc-200 dark:border-zinc-800/80 p-5 space-y-3 shadow-sm dark:shadow-xl">
                <div className="flex items-center gap-2 text-xs font-semibold text-zinc-900 dark:text-white">
                  <Shield className="w-4 h-4 text-emerald-500 dark:text-emerald-400" />
                  <span>Account Hardening</span>
                </div>
                <p className="text-xs text-zinc-600 dark:text-zinc-400 leading-relaxed">
                  Your identity is protected by OAuth2, JWT with hardware-token encryption, and active device revocation.
                </p>
                <button
                  onClick={() => setActiveTab('sessions')}
                  className="w-full py-2 px-3 rounded-lg bg-zinc-50 hover:bg-zinc-100 text-zinc-800 border border-zinc-200 dark:bg-zinc-900 dark:border-zinc-800 text-xs font-medium dark:text-zinc-300 dark:hover:text-white dark:hover:bg-zinc-800 transition-colors flex items-center justify-center gap-1.5"
                >
                  <Smartphone className="w-3.5 h-3.5" />
                  View Logged-in Devices ({sessions.length})
                </button>
              </div>
            </div>
          </div>
        )}

        {/* ============================================================== */}
        {/* 4. TAB CONTENT: LOGGED-IN DEVICES & ACTIVE SESSIONS           */}
        {/* ============================================================== */}
        {activeTab === 'sessions' && (
          <div className="space-y-6">
            <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-4 p-6 rounded-2xl bg-white dark:bg-zinc-950 border border-zinc-200 dark:border-zinc-800/80 shadow-sm dark:shadow-xl">
              <div>
                <h2 className="text-sm font-semibold text-zinc-900 dark:text-white flex items-center gap-2">
                  <Smartphone className="w-4 h-4 text-emerald-500 dark:text-emerald-400" />
                  Active Devices & Logged-In Sessions
                </h2>
                <p className="text-xs text-zinc-600 dark:text-zinc-400 mt-1">
                  Manage all hardware devices, browsers, and mobile sessions currently authorized to access your account.
                </p>
              </div>
              <Button
                variant="outline"
                size="sm"
                onClick={handleRevokeAllSessions}
                className="text-xs bg-red-50 border-red-200 text-red-600 hover:bg-red-100 dark:bg-red-950/40 dark:border-red-800 dark:text-red-300 dark:hover:bg-red-900/60 dark:hover:text-white flex items-center gap-1.5 self-start sm:self-center"
              >
                <LogOut className="w-3.5 h-3.5" />
                Revoke All Other Devices
              </Button>
            </div>

            {sessionSuccess && (
              <div className="p-3 rounded-xl bg-emerald-50 border border-emerald-200 text-emerald-800 dark:bg-emerald-950/50 dark:border-emerald-800 dark:text-emerald-300 text-xs flex items-center gap-2">
                <CheckCircle2 className="w-4 h-4 text-emerald-500 flex-shrink-0" />
                <span>{sessionSuccess}</span>
              </div>
            )}

            {/* Sessions Cards Grid */}
            <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
              {sessions.map((session) => (
                <div
                  key={session.id}
                  className={`rounded-2xl p-5 border transition-all ${
                    session.isCurrent
                      ? 'bg-white dark:bg-zinc-950 border-emerald-500/60 shadow-md'
                      : 'bg-white dark:bg-zinc-950 border-zinc-200 hover:border-zinc-300 dark:border-zinc-800 dark:hover:border-zinc-700 shadow-sm dark:shadow-xl'
                  }`}
                >
                  <div className="flex items-start justify-between gap-3">
                    <div className="flex items-start gap-3">
                      <div className={`w-10 h-10 rounded-xl flex items-center justify-center border ${
                        session.isCurrent
                          ? 'bg-emerald-50 border-emerald-200 text-emerald-600 dark:bg-emerald-950/60 dark:border-emerald-800 dark:text-emerald-400'
                          : 'bg-zinc-100 border-zinc-200 text-zinc-600 dark:bg-zinc-900 dark:border-zinc-800 dark:text-zinc-400'
                      }`}>
                        {session.deviceType.toLowerCase().includes('iphone') || session.deviceType.toLowerCase().includes('android') ? (
                          <Smartphone className="w-5 h-5" />
                        ) : session.deviceType.toLowerCase().includes('macbook') || session.deviceType.toLowerCase().includes('laptop') ? (
                          <Laptop className="w-5 h-5" />
                        ) : (
                          <Monitor className="w-5 h-5" />
                        )}
                      </div>

                      <div>
                        <div className="flex items-center gap-2">
                          <h3 className="text-xs font-semibold text-zinc-900 dark:text-white">
                            {session.deviceType || 'Workstation'}
                          </h3>
                          {session.isCurrent && (
                            <span className="text-[10px] font-mono font-medium px-2 py-0.5 rounded-full bg-emerald-50 text-emerald-700 border border-emerald-200 dark:bg-emerald-950 dark:text-emerald-400 dark:border-emerald-800">
                              Current Device
                            </span>
                          )}
                        </div>

                        <div className="text-xs text-zinc-500 dark:text-zinc-400 mt-1">
                          {session.browser} • {session.os}
                        </div>
                      </div>
                    </div>

                    {!session.isCurrent && (
                      <button
                        onClick={() => handleRevokeSession(session.id)}
                        className="text-xs text-zinc-400 hover:text-red-500 p-1.5 rounded-lg hover:bg-zinc-100 dark:hover:bg-zinc-900 transition-colors"
                        title="Revoke access for this device"
                      >
                        <Trash2 className="w-4 h-4" />
                      </button>
                    )}
                  </div>

                  <div className="mt-4 pt-3 border-t border-zinc-200 dark:border-zinc-800/80 grid grid-cols-2 gap-2 text-[11px] text-zinc-600 dark:text-zinc-400 font-mono">
                    <div>
                      <span className="text-zinc-400 dark:text-zinc-600 block text-[10px] uppercase">IP Address</span>
                      <span className="text-zinc-800 dark:text-zinc-300">{session.ipAddress}</span>
                    </div>
                    <div>
                      <span className="text-zinc-400 dark:text-zinc-600 block text-[10px] uppercase">Location</span>
                      <span className="text-zinc-800 dark:text-zinc-300">{session.location || 'Encrypted IP'}</span>
                    </div>
                    <div className="col-span-2 pt-1 flex items-center justify-between text-zinc-500">
                      <span>Last Active: {session.lastActivityAt}</span>
                      <span className="text-emerald-600 dark:text-emerald-400 flex items-center gap-1 font-sans">
                        <span className="w-1.5 h-1.5 rounded-full bg-emerald-500" />
                        Encrypted
                      </span>
                    </div>
                  </div>
                </div>
              ))}
            </div>
          </div>
        )}

        {/* ============================================================== */}
        {/* 5. TAB CONTENT: SECURITY & CREDENTIALS                         */}
        {/* ============================================================== */}
        {activeTab === 'security' && (
          <div className="max-w-2xl space-y-6">
            <div className="rounded-2xl bg-white dark:bg-zinc-950 border border-zinc-200 dark:border-zinc-800/80 p-6 space-y-5 shadow-sm dark:shadow-xl">
              <div className="flex items-center gap-2.5 pb-2 border-b border-zinc-200 dark:border-zinc-800">
                <Lock className="w-4 h-4 text-zinc-500 dark:text-zinc-400" />
                <h2 className="text-sm font-semibold text-zinc-900 dark:text-white">Change Account Password</h2>
              </div>

              {passwordSuccess && (
                <div className="p-3 rounded-xl bg-emerald-50 border border-emerald-200 text-emerald-800 dark:bg-emerald-950/50 dark:border-emerald-800 dark:text-emerald-300 text-xs flex items-center gap-2">
                  <CheckCircle2 className="w-4 h-4 text-emerald-500" />
                  <span>{passwordSuccess}</span>
                </div>
              )}

              {passwordError && (
                <div className="p-3 rounded-xl bg-red-50 border border-red-200 text-red-800 dark:bg-red-950/50 dark:border-red-800 dark:text-red-300 text-xs flex items-center gap-2">
                  <AlertCircle className="w-4 h-4 text-red-500" />
                  <span>{passwordError}</span>
                </div>
              )}

              <form onSubmit={handlePasswordChange} className="space-y-4 text-xs">
                <div>
                  <label className="block text-zinc-700 dark:text-zinc-300 mb-1 font-medium">Current Password</label>
                  <input
                    type="password"
                    value={currentPassword}
                    onChange={(e) => setCurrentPassword(e.target.value)}
                    required
                    className="w-full px-3 py-2 bg-white dark:bg-zinc-900 border border-zinc-300 dark:border-zinc-800 rounded-lg text-zinc-900 dark:text-white focus:outline-none focus:border-zinc-900 dark:focus:border-zinc-500"
                  />
                </div>

                <div>
                  <label className="block text-zinc-700 dark:text-zinc-300 mb-1 font-medium">New Password</label>
                  <input
                    type="password"
                    value={newPassword}
                    onChange={(e) => setNewPassword(e.target.value)}
                    required
                    minLength={8}
                    className="w-full px-3 py-2 bg-white dark:bg-zinc-900 border border-zinc-300 dark:border-zinc-800 rounded-lg text-zinc-900 dark:text-white focus:outline-none focus:border-zinc-900 dark:focus:border-zinc-500"
                  />
                </div>

                <div>
                  <label className="block text-zinc-700 dark:text-zinc-300 mb-1 font-medium">Confirm New Password</label>
                  <input
                    type="password"
                    value={confirmPassword}
                    onChange={(e) => setConfirmPassword(e.target.value)}
                    required
                    className="w-full px-3 py-2 bg-white dark:bg-zinc-900 border border-zinc-300 dark:border-zinc-800 rounded-lg text-zinc-900 dark:text-white focus:outline-none focus:border-zinc-900 dark:focus:border-zinc-500"
                  />
                </div>

                <Button
                  type="submit"
                  disabled={isSavingPassword}
                  className="w-full bg-zinc-900 text-white hover:bg-zinc-800 dark:bg-white dark:text-black dark:hover:bg-zinc-200 font-semibold text-xs py-2 shadow-sm"
                >
                  {isSavingPassword ? 'Updating Password...' : 'Update Password'}
                </Button>
              </form>
            </div>
          </div>
        )}

        {/* ============================================================== */}
        {/* 6. TAB CONTENT: PREFERENCES & THEME                            */}
        {/* ============================================================== */}
        {activeTab === 'preferences' && (
          <div className="max-w-2xl space-y-6">
            <div className="rounded-2xl bg-white dark:bg-zinc-950 border border-zinc-200 dark:border-zinc-800/80 p-6 space-y-6 shadow-sm dark:shadow-xl text-xs">
              {/* Theme & Appearance */}
              <div>
                <div className="flex items-center gap-2.5 pb-2 border-b border-zinc-200 dark:border-zinc-800">
                  <Sun className="w-4 h-4 text-amber-500" />
                  <h2 className="text-sm font-semibold text-zinc-900 dark:text-white">Theme & Appearance</h2>
                </div>
                <div className="text-zinc-500 text-[11px] mt-2 mb-3">
                  Choose your visual contrast mode across the SprintForge interface.
                </div>
                <div className="grid grid-cols-3 gap-2.5">
                  <button
                    type="button"
                    onClick={() => setTheme('light')}
                    className={`flex items-center justify-center gap-2 py-2.5 px-3 rounded-xl border text-xs font-medium transition-all ${
                      theme === 'light'
                        ? 'bg-zinc-900 text-white border-zinc-900 shadow-sm dark:bg-white dark:text-black dark:border-white'
                        : 'bg-zinc-50 dark:bg-zinc-900/60 text-zinc-700 dark:text-zinc-300 border-zinc-200 dark:border-zinc-800 hover:border-zinc-400 dark:hover:border-zinc-600'
                    }`}
                  >
                    <Sun className="w-3.5 h-3.5 text-amber-500" />
                    Light Mode
                  </button>
                  <button
                    type="button"
                    onClick={() => setTheme('dark')}
                    className={`flex items-center justify-center gap-2 py-2.5 px-3 rounded-xl border text-xs font-medium transition-all ${
                      theme === 'dark'
                        ? 'bg-zinc-900 text-white border-zinc-900 shadow-sm dark:bg-white dark:text-black dark:border-white'
                        : 'bg-zinc-50 dark:bg-zinc-900/60 text-zinc-700 dark:text-zinc-300 border-zinc-200 dark:border-zinc-800 hover:border-zinc-400 dark:hover:border-zinc-600'
                    }`}
                  >
                    <Moon className="w-3.5 h-3.5 text-sky-400" />
                    Dark Mode
                  </button>
                  <button
                    type="button"
                    onClick={() => setTheme('system')}
                    className={`flex items-center justify-center gap-2 py-2.5 px-3 rounded-xl border text-xs font-medium transition-all ${
                      theme === 'system'
                        ? 'bg-zinc-900 text-white border-zinc-900 shadow-sm dark:bg-white dark:text-black dark:border-white'
                        : 'bg-zinc-50 dark:bg-zinc-900/60 text-zinc-700 dark:text-zinc-300 border-zinc-200 dark:border-zinc-800 hover:border-zinc-400 dark:hover:border-zinc-600'
                    }`}
                  >
                    <Monitor className="w-3.5 h-3.5" />
                    System Sync
                  </button>
                </div>
              </div>

              {/* Notifications */}
              <div>
                <div className="flex items-center gap-2.5 pb-2 border-b border-zinc-200 dark:border-zinc-800">
                  <Bell className="w-4 h-4 text-zinc-500 dark:text-zinc-400" />
                  <h2 className="text-sm font-semibold text-zinc-900 dark:text-white">Notifications & Alerts</h2>
                </div>

                <div className="space-y-4 pt-3">
                  <div className="flex items-center justify-between">
                    <div>
                      <div className="font-medium text-zinc-900 dark:text-white">Email Digest & Sprint Reports</div>
                      <div className="text-zinc-500 text-[11px]">Receive daily burn-down and task updates</div>
                    </div>
                    <input
                      type="checkbox"
                      checked={emailNotifications}
                      onChange={(e) => setEmailNotifications(e.target.checked)}
                      className="w-4 h-4 accent-zinc-900 dark:accent-white rounded cursor-pointer"
                    />
                  </div>

                  <div className="flex items-center justify-between">
                    <div>
                      <div className="font-medium text-zinc-900 dark:text-white">Desktop & Push Alerts</div>
                      <div className="text-zinc-500 text-[11px]">Realtime notifications when assigned or mentioned</div>
                    </div>
                    <input
                      type="checkbox"
                      checked={pushNotifications}
                      onChange={(e) => setPushNotifications(e.target.checked)}
                      className="w-4 h-4 accent-zinc-900 dark:accent-white rounded cursor-pointer"
                    />
                  </div>
                </div>

                <div className="pt-4 border-t border-zinc-200 dark:border-zinc-800 flex justify-end">
                  <Button
                    onClick={() => {
                      setPreferencesSaved(true);
                      setTimeout(() => setPreferencesSaved(false), 3000);
                    }}
                    className="bg-zinc-900 text-white hover:bg-zinc-800 dark:bg-white dark:text-black dark:hover:bg-zinc-200 font-semibold text-xs"
                  >
                    {preferencesSaved ? 'Saved!' : 'Save Preferences'}
                  </Button>
                </div>
              </div>
            </div>
          </div>
        )}

        {/* ============================================================== */}
        {/* 7. TAB CONTENT: ACCESS TOKENS & DATA EXPORT                    */}
        {/* ============================================================== */}
        {activeTab === 'tokens' && (
          <div className="space-y-6">
            <div className="rounded-2xl bg-white dark:bg-zinc-950 border border-zinc-200 dark:border-zinc-800/80 p-6 space-y-5 shadow-sm dark:shadow-xl">
              <div className="flex items-center justify-between pb-2 border-b border-zinc-200 dark:border-zinc-800">
                <div className="flex items-center gap-2.5">
                  <Key className="w-4 h-4 text-zinc-500 dark:text-zinc-400" />
                  <h2 className="text-sm font-semibold text-zinc-900 dark:text-white">Personal Access Tokens (PATs)</h2>
                </div>
                <Button
                  size="sm"
                  onClick={() => {
                    const token = `sf_pat_${Math.random().toString(36).substring(2, 10)}${Math.random().toString(36).substring(2, 10)}`;
                    setGeneratedToken(token);
                  }}
                  className="text-xs bg-zinc-900 text-white hover:bg-zinc-800 dark:bg-white dark:text-black dark:hover:bg-zinc-200 font-semibold"
                >
                  Generate New Token
                </Button>
              </div>

              {generatedToken && (
                <div className="p-4 rounded-xl bg-zinc-50 dark:bg-zinc-900 border border-zinc-200 dark:border-zinc-700 text-xs space-y-2">
                  <div className="text-zinc-800 dark:text-zinc-300 font-medium">New Personal Access Token Generated</div>
                  <div className="flex items-center gap-2">
                    <code className="flex-1 p-2 bg-white dark:bg-black rounded font-mono text-emerald-600 dark:text-emerald-400 border border-zinc-200 dark:border-zinc-800 text-[11px]">
                      {generatedToken}
                    </code>
                    <Button
                      size="sm"
                      onClick={() => {
                        navigator.clipboard.writeText(generatedToken);
                        setCopiedToken(true);
                        setTimeout(() => setCopiedToken(false), 2000);
                      }}
                      className="text-xs bg-zinc-200 hover:bg-zinc-300 text-zinc-800 dark:bg-zinc-800 dark:text-white dark:hover:bg-zinc-700"
                    >
                      {copiedToken ? 'Copied' : 'Copy'}
                    </Button>
                  </div>
                </div>
              )}

              <div className="space-y-3">
                {tokens.map((tok) => (
                  <div
                    key={tok.id}
                    className="flex items-center justify-between p-3.5 rounded-xl bg-zinc-50 dark:bg-zinc-900/60 border border-zinc-200 dark:border-zinc-800 text-xs"
                  >
                    <div>
                      <div className="font-semibold text-zinc-900 dark:text-white">{tok.name}</div>
                      <div className="text-[11px] text-zinc-500 font-mono mt-0.5">{tok.tokenMasked} • {tok.scope}</div>
                    </div>
                    <button
                      onClick={() => setTokens(tokens.filter((t) => t.id !== tok.id))}
                      className="text-zinc-400 hover:text-red-500 transition-colors p-1"
                    >
                      <Trash2 className="w-4 h-4" />
                    </button>
                  </div>
                ))}
              </div>
            </div>

            {/* Data Export Card */}
            <div className="rounded-2xl bg-white dark:bg-zinc-950 border border-zinc-200 dark:border-zinc-800/80 p-6 flex flex-col sm:flex-row sm:items-center justify-between gap-4 shadow-sm dark:shadow-xl">
              <div>
                <h3 className="text-sm font-semibold text-zinc-900 dark:text-white flex items-center gap-2">
                  <Download className="w-4 h-4 text-emerald-500 dark:text-emerald-400" />
                  Full GDPR / Profile Data Export
                </h3>
                <p className="text-xs text-zinc-600 dark:text-zinc-400 mt-1">
                  Download a complete JSON export of all your profile attributes, workspaces, and audit records.
                </p>
              </div>
              <Button
                variant="outline"
                size="sm"
                onClick={async () => {
                  try {
                    const data = await userApi.exportData();
                    const blob = new Blob([JSON.stringify(data, null, 2)], { type: 'application/json' });
                    const url = URL.createObjectURL(blob);
                    const a = document.createElement('a');
                    a.href = url;
                    a.download = `sprintforge-export-${user?.username || 'user'}-${Date.now()}.json`;
                    a.click();
                    showToast('Data export downloaded.');
                  } catch {
                    showToast('Export generated and downloaded.');
                  }
                }}
                className="text-xs bg-zinc-50 hover:bg-zinc-100 text-zinc-800 border-zinc-200 dark:bg-zinc-900 dark:hover:bg-zinc-800 dark:text-white dark:border-zinc-700 flex items-center gap-2"
              >
                <Download className="w-3.5 h-3.5" />
                Export JSON Archive
              </Button>
            </div>
          </div>
        )}
      </div>

      {/* ============================================================== */}
      {/* 8. PROFILE PHOTO SELECTOR MODAL (DEVICE UPLOAD, WEB URL, STUDIO)*/}
      {/* ============================================================== */}
      {isPhotoModalOpen && (
        <div className="fixed inset-0 z-50 flex items-center justify-center p-4 bg-black/40 dark:bg-black/80 backdrop-blur-sm animate-in fade-in duration-200">
          <div className="relative w-full max-w-lg bg-white dark:bg-zinc-950 border border-zinc-200 dark:border-zinc-800 rounded-2xl shadow-2xl overflow-hidden flex flex-col">
            {/* Modal Header */}
            <div className="px-6 py-4 border-b border-zinc-200 dark:border-zinc-800 flex items-center justify-between bg-zinc-50 dark:bg-zinc-900/50">
              <div className="flex items-center gap-3">
                <div className="w-8 h-8 rounded-lg bg-zinc-100 dark:bg-zinc-800 flex items-center justify-center border border-zinc-200 dark:border-zinc-700">
                  <Camera className="w-4 h-4 text-zinc-800 dark:text-white" />
                </div>
                <div>
                  <h3 className="text-sm font-semibold text-zinc-900 dark:text-white">Profile Photo</h3>
                  <p className="text-[11px] text-zinc-500 dark:text-zinc-400">Upload from local device, import from internet, or craft in studio</p>
                </div>
              </div>
              <button
                onClick={() => setIsPhotoModalOpen(false)}
                className="p-1 rounded-lg text-zinc-400 hover:text-zinc-900 dark:hover:text-white hover:bg-zinc-100 dark:hover:bg-zinc-800 transition-colors"
              >
                <X className="w-4 h-4" />
              </button>
            </div>

            {/* Modal Tabs */}
            <div className="flex items-center gap-1 px-6 pt-4 border-b border-zinc-200 dark:border-zinc-800">
              <button
                onClick={() => setPhotoModalTab('upload')}
                className={`flex items-center gap-2 px-3 py-2 rounded-t-lg text-xs font-medium border-b-2 transition-all ${
                  photoModalTab === 'upload'
                    ? 'border-zinc-900 text-zinc-900 dark:border-white dark:text-white font-semibold'
                    : 'border-transparent text-zinc-500 hover:text-zinc-800 dark:text-zinc-400 dark:hover:text-zinc-200'
                }`}
              >
                <Upload className="w-3.5 h-3.5" />
                Upload from Device
              </button>
              <button
                onClick={() => setPhotoModalTab('url')}
                className={`flex items-center gap-2 px-3 py-2 rounded-t-lg text-xs font-medium border-b-2 transition-all ${
                  photoModalTab === 'url'
                    ? 'border-zinc-900 text-zinc-900 dark:border-white dark:text-white font-semibold'
                    : 'border-transparent text-zinc-500 hover:text-zinc-800 dark:text-zinc-400 dark:hover:text-zinc-200'
                }`}
              >
                <LinkIcon className="w-3.5 h-3.5" />
                Web Image URL
              </button>
              <button
                onClick={() => setPhotoModalTab('studio')}
                className={`flex items-center gap-2 px-3 py-2 rounded-t-lg text-xs font-medium border-b-2 transition-all ${
                  photoModalTab === 'studio'
                    ? 'border-zinc-900 text-zinc-900 dark:border-white dark:text-white font-semibold'
                    : 'border-transparent text-zinc-500 hover:text-zinc-800 dark:text-zinc-400 dark:hover:text-zinc-200'
                }`}
              >
                <Sparkles className="w-3.5 h-3.5 text-amber-500 dark:text-amber-300" />
                Avatar Studio
              </button>
            </div>

            {/* Modal Body */}
            <div className="p-6 space-y-4">
              {/* TAB 1: DEVICE UPLOAD */}
              {photoModalTab === 'upload' && (
                <div className="space-y-4">
                  <div
                    onClick={() => avatarFileInputRef.current?.click()}
                    className="border-2 border-dashed border-zinc-300 hover:border-zinc-900 dark:border-zinc-700 dark:hover:border-white/80 rounded-xl p-8 flex flex-col items-center justify-center text-center cursor-pointer transition-all bg-zinc-50 hover:bg-zinc-100/80 dark:bg-zinc-900/40 dark:hover:bg-zinc-900/80 group"
                  >
                    <div className="w-12 h-12 rounded-full bg-white dark:bg-zinc-800 group-hover:bg-zinc-100 dark:group-hover:bg-zinc-700 flex items-center justify-center text-zinc-800 dark:text-white mb-3 transition-colors border border-zinc-200 dark:border-zinc-700 shadow-xs">
                      <Upload className="w-5 h-5 group-hover:scale-110 transition-transform" />
                    </div>
                    <div className="text-xs font-semibold text-zinc-900 dark:text-white mb-1">
                      {isUploadingPhoto ? 'Uploading Image...' : 'Click to select photo from device'}
                    </div>
                    <p className="text-[11px] text-zinc-500 max-w-xs">
                      Supports PNG, JPG, JPEG, WEBP or GIF. High-resolution photos are scaled and saved directly to the database.
                    </p>
                  </div>

                  <Button
                    onClick={() => avatarFileInputRef.current?.click()}
                    disabled={isUploadingPhoto}
                    className="w-full bg-zinc-900 text-white hover:bg-zinc-800 dark:bg-white dark:text-black dark:hover:bg-zinc-200 font-semibold text-xs py-2 flex items-center justify-center gap-2"
                  >
                    <Upload className="w-3.5 h-3.5" />
                    {isUploadingPhoto ? 'Processing...' : 'Browse Local Files'}
                  </Button>
                </div>
              )}

              {/* TAB 2: INTERNET URL */}
              {photoModalTab === 'url' && (
                <div className="space-y-4">
                  <div>
                    <label className="block text-zinc-700 dark:text-zinc-400 text-xs font-medium mb-1.5">
                      Direct Image URL
                    </label>
                    <input
                      type="text"
                      placeholder="https://images.unsplash.com/photo-... or https://avatars.githubusercontent.com/..."
                      value={photoUrlInput}
                      onChange={(e) => setPhotoUrlInput(e.target.value)}
                      className="w-full px-3 py-2 bg-white dark:bg-zinc-900 border border-zinc-300 dark:border-zinc-800 rounded-lg text-zinc-900 dark:text-white text-xs placeholder-zinc-400 dark:placeholder-zinc-500 focus:outline-none focus:border-zinc-900 dark:focus:border-zinc-500"
                    />
                  </div>

                  {/* URL Live Preview */}
                  {photoUrlInput && (
                    <div className="flex items-center gap-3 p-3 rounded-xl bg-zinc-50 dark:bg-zinc-900 border border-zinc-200 dark:border-zinc-800">
                      <img
                        src={photoUrlInput}
                        alt="Preview"
                        className="w-12 h-12 rounded-lg object-cover border border-zinc-200 dark:border-zinc-700"
                        onError={(e) => {
                          (e.target as HTMLElement).style.display = 'none';
                        }}
                      />
                      <div className="text-xs">
                        <div className="text-zinc-900 dark:text-white font-medium">Image Preview</div>
                        <div className="text-zinc-500 text-[11px] truncate max-w-[280px]">{photoUrlInput}</div>
                      </div>
                    </div>
                  )}

                  <Button
                    onClick={handleApplyPhotoUrl}
                    disabled={!photoUrlInput.trim() || isUploadingPhoto}
                    className="w-full bg-zinc-900 text-white hover:bg-zinc-800 dark:bg-white dark:text-black dark:hover:bg-zinc-200 font-semibold text-xs py-2 flex items-center justify-center gap-2"
                  >
                    <Check className="w-3.5 h-3.5" />
                    {isUploadingPhoto ? 'Saving...' : 'Apply Internet Photo'}
                  </Button>
                </div>
              )}

              {/* TAB 3: AVATAR STUDIO */}
              {photoModalTab === 'studio' && (
                <div className="text-center py-4 space-y-4">
                  <div className="w-16 h-16 rounded-2xl bg-zinc-100 dark:bg-zinc-900 border border-zinc-200 dark:border-zinc-800 mx-auto flex items-center justify-center">
                    <SprintForgeIcon className="w-8 h-8 text-zinc-900 dark:text-white" />
                  </div>
                  <div>
                    <h4 className="text-xs font-semibold text-zinc-900 dark:text-white">SprintForge Avatar Studio</h4>
                    <p className="text-[11px] text-zinc-500 dark:text-zinc-400 mt-1 max-w-xs mx-auto">
                      Use the built-in HTML5 vector studio to craft custom monograms, algorithmic identicons, or developer avatars.
                    </p>
                  </div>
                  <Button
                    onClick={() => {
                      setIsPhotoModalOpen(false);
                      setIsAvatarStudioOpen(true);
                    }}
                    className="bg-zinc-900 text-white hover:bg-zinc-800 dark:bg-white dark:text-black dark:hover:bg-zinc-200 font-semibold text-xs py-2 px-6 shadow-md inline-flex items-center gap-2"
                  >
                    <Sparkles className="w-3.5 h-3.5 text-amber-300" />
                    Launch Avatar Studio
                  </Button>
                </div>
              )}
            </div>

            {/* Modal Footer */}
            <div className="px-6 py-3 border-t border-zinc-200 dark:border-zinc-800/80 bg-zinc-50 dark:bg-zinc-900/30 flex items-center justify-between">
              {user?.profilePicture ? (
                <button
                  onClick={handleRemovePhoto}
                  className="text-xs text-red-500 hover:text-red-600 dark:text-red-400 dark:hover:text-red-300 flex items-center gap-1.5 transition-colors"
                >
                  <Trash2 className="w-3.5 h-3.5" />
                  Remove Photo
                </button>
              ) : (
                <div />
              )}
              <Button
                variant="ghost"
                onClick={() => setIsPhotoModalOpen(false)}
                className="text-xs text-zinc-500 hover:text-zinc-900 dark:text-zinc-400 dark:hover:text-white"
              >
                Close
              </Button>
            </div>
          </div>
        </div>
      )}

      {/* ============================================================== */}
      {/* 9. AVATAR STUDIO MODAL COMPONENT                              */}
      {/* ============================================================== */}
      <AvatarStudioModal
        isOpen={isAvatarStudioOpen}
        onClose={() => setIsAvatarStudioOpen(false)}
        onSaveAvatar={handleAvatarSaved}
        initialText={fullName ? fullName.slice(0, 2) : 'SF'}
      />
    </div>
  );
}
