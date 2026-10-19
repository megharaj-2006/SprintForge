import React, { useState, useRef, useEffect, useCallback } from 'react';
import {
  X,
  Sparkles,
  Download,
  Check,
  RefreshCw,
  Palette,
  Type,
  Cpu,
  User as UserIcon,
  Layers,
  Circle,
  Square,
  Copy,
} from 'lucide-react';
import { Button } from '../ui/Button';
import { SprintForgeIcon } from '../brand/SprintForgeLogo';

interface AvatarStudioModalProps {
  isOpen: boolean;
  onClose: () => void;
  onSaveAvatar: (avatarDataUrl: string) => void;
  initialText?: string;
}

type StudioTab = 'monogram' | 'generative' | 'persona' | 'presets';

export function AvatarStudioModal({
  isOpen,
  onClose,
  onSaveAvatar,
  initialText = 'SF',
}: AvatarStudioModalProps) {
  const canvasRef = useRef<HTMLCanvasElement | null>(null);
  const [activeTab, setActiveTab] = useState<StudioTab>('monogram');
  const [previewShape, setPreviewShape] = useState<'circle' | 'squircle'>('circle');
  const [copied, setCopied] = useState(false);

  // --- Monogram State ---
  const [monogramText, setMonogramText] = useState(initialText.slice(0, 3).toUpperCase());
  const [fontFamily, setFontFamily] = useState<'Inter' | 'JetBrains Mono' | 'Space Grotesk' | 'Syne' | 'Cinzel'>('Inter');
  const [fontWeight, setFontWeight] = useState<'600' | '700' | '900'>('700');
  const [monogramTheme, setMonogramTheme] = useState<
    'obsidian' | 'white' | 'matrix' | 'aurora' | 'sunset' | 'slate'
  >('obsidian');
  const [patternStyle, setPatternStyle] = useState<'none' | 'dots' | 'stripes' | 'grid'>('dots');
  const [ringStyle, setRingStyle] = useState<'none' | 'single' | 'double' | 'segmented'>('single');
  const [ringColor, setRingColor] = useState<'#ffffff' | '#71717a' | '#22c55e' | '#06b6d4' | '#f59e0b'>('#ffffff');

  // --- Generative State ---
  const [genSeed, setGenSeed] = useState(1337);
  const [genGridSize, setGenGridSize] = useState<4 | 6 | 8>(6);
  const [genSymmetry, setGenSymmetry] = useState<'mirror' | 'quad' | 'none'>('mirror');
  const [genPalette, setGenPalette] = useState<'noir' | 'silver' | 'matrix' | 'cyber' | 'ember'>('noir');

  // --- Persona State ---
  const [skinTone, setSkinTone] = useState<'fair' | 'warm' | 'tan' | 'deep' | 'chrome'>('chrome');
  const [hairStyle, setHairStyle] = useState<'buzz' | 'sidepart' | 'curly' | 'beanie' | 'headset'>('beanie');
  const [hairColor, setHairColor] = useState<'#09090b' | '#27272a' | '#ffffff' | '#71717a' | '#38bdf8'>('#09090b');
  const [glasses, setGlasses] = useState<'none' | 'frames' | 'visor' | 'sunglasses'>('frames');
  const [outfitColor, setOutfitColor] = useState<'#09090b' | '#18181b' | '#ffffff' | '#27272a'>('#18181b');
  const [badgeType, setBadgeType] = useState<'none' | 'terminal' | 'verified' | 'sparkle'>('terminal');

  // --- Presets Data ---
  const PRESET_LIST = [
    {
      id: 'linear-obsidian',
      label: 'Linear Obsidian',
      preview: 'linear-gradient(135deg, #18181b 0%, #09090b 100%)',
      apply: () => {
        setActiveTab('monogram');
        setMonogramTheme('obsidian');
        setPatternStyle('dots');
        setRingStyle('single');
        setRingColor('#ffffff');
      },
    },
    {
      id: 'mono-light',
      label: 'Minimalist Titanium',
      preview: 'linear-gradient(135deg, #f4f4f5 0%, #d4d4d8 100%)',
      apply: () => {
        setActiveTab('monogram');
        setMonogramTheme('white');
        setPatternStyle('none');
        setRingStyle('double');
        setRingColor('#71717a');
      },
    },
    {
      id: 'matrix-terminal',
      label: 'Emerald Matrix',
      preview: 'linear-gradient(135deg, #052e16 0%, #022c22 100%)',
      apply: () => {
        setActiveTab('monogram');
        setMonogramTheme('matrix');
        setPatternStyle('grid');
        setRingStyle('segmented');
        setRingColor('#22c55e');
      },
    },
    {
      id: 'cyber-aurora',
      label: 'Midnight Aurora',
      preview: 'linear-gradient(135deg, #0f172a 0%, #312e81 100%)',
      apply: () => {
        setActiveTab('monogram');
        setMonogramTheme('aurora');
        setPatternStyle('stripes');
        setRingStyle('single');
        setRingColor('#06b6d4');
      },
    },
    {
      id: 'sunset-core',
      label: 'Carbon Sunset',
      preview: 'linear-gradient(135deg, #1c1917 0%, #451a03 100%)',
      apply: () => {
        setActiveTab('monogram');
        setMonogramTheme('sunset');
        setPatternStyle('dots');
        setRingStyle('single');
        setRingColor('#f59e0b');
      },
    },
    {
      id: 'cyber-identicon',
      label: 'Hex Identicon',
      preview: 'radial-gradient(circle, #27272a 10%, #09090b 90%)',
      apply: () => {
        setActiveTab('generative');
        setGenPalette('noir');
        setGenGridSize(6);
        setGenSymmetry('mirror');
        setGenSeed(Math.floor(Math.random() * 10000));
      },
    },
    {
      id: 'matrix-glitch',
      label: 'Glitch Hacker',
      preview: 'radial-gradient(circle, #14532d 10%, #052e16 90%)',
      apply: () => {
        setActiveTab('generative');
        setGenPalette('matrix');
        setGenGridSize(8);
        setGenSymmetry('quad');
        setGenSeed(Math.floor(Math.random() * 10000));
      },
    },
    {
      id: 'tech-lead-beanie',
      label: 'Tech Lead Persona',
      preview: 'linear-gradient(135deg, #18181b 0%, #27272a 100%)',
      apply: () => {
        setActiveTab('persona');
        setHairStyle('beanie');
        setGlasses('frames');
        setBadgeType('terminal');
        setSkinTone('chrome');
      },
    },
  ];

  // Render Engine on Canvas (512x512)
  const renderCanvas = useCallback(() => {
    const canvas = canvasRef.current;
    if (!canvas) return;
    const ctx = canvas.getContext('2d');
    if (!ctx) return;

    const size = 512;
    canvas.width = size;
    canvas.height = size;

    ctx.clearRect(0, 0, size, size);

    // --- TAB 1: MONOGRAM STUDIO ---
    if (activeTab === 'monogram') {
      // 1. Background Gradient
      let bgGrad = ctx.createLinearGradient(0, 0, size, size);
      if (monogramTheme === 'obsidian') {
        bgGrad.addColorStop(0, '#1c1c1f');
        bgGrad.addColorStop(0.5, '#09090b');
        bgGrad.addColorStop(1, '#000000');
      } else if (monogramTheme === 'white') {
        bgGrad.addColorStop(0, '#ffffff');
        bgGrad.addColorStop(0.7, '#f4f4f5');
        bgGrad.addColorStop(1, '#e4e4e7');
      } else if (monogramTheme === 'matrix') {
        bgGrad.addColorStop(0, '#064e3b');
        bgGrad.addColorStop(0.5, '#022c22');
        bgGrad.addColorStop(1, '#021a14');
      } else if (monogramTheme === 'aurora') {
        bgGrad.addColorStop(0, '#1e1b4b');
        bgGrad.addColorStop(0.5, '#0f172a');
        bgGrad.addColorStop(1, '#020617');
      } else if (monogramTheme === 'sunset') {
        bgGrad.addColorStop(0, '#292524');
        bgGrad.addColorStop(0.5, '#1c1917');
        bgGrad.addColorStop(1, '#0c0a09');
      } else {
        bgGrad.addColorStop(0, '#27272a');
        bgGrad.addColorStop(1, '#18181b');
      }

      ctx.fillStyle = bgGrad;
      ctx.fillRect(0, 0, size, size);

      // 2. Pattern Overlay
      if (patternStyle === 'dots') {
        ctx.fillStyle = monogramTheme === 'white' ? 'rgba(0, 0, 0, 0.05)' : 'rgba(255, 255, 255, 0.06)';
        const step = 24;
        for (let x = 12; x < size; x += step) {
          for (let y = 12; y < size; y += step) {
            ctx.beginPath();
            ctx.arc(x, y, 2.5, 0, Math.PI * 2);
            ctx.fill();
          }
        }
      } else if (patternStyle === 'stripes') {
        ctx.strokeStyle = monogramTheme === 'white' ? 'rgba(0, 0, 0, 0.04)' : 'rgba(255, 255, 255, 0.05)';
        ctx.lineWidth = 3;
        for (let i = -size; i < size * 2; i += 28) {
          ctx.beginPath();
          ctx.moveTo(i, 0);
          ctx.lineTo(i + size, size);
          ctx.stroke();
        }
      } else if (patternStyle === 'grid') {
        ctx.strokeStyle = monogramTheme === 'white' ? 'rgba(0, 0, 0, 0.04)' : 'rgba(255, 255, 255, 0.05)';
        ctx.lineWidth = 1.5;
        const step = 32;
        for (let i = 0; i < size; i += step) {
          ctx.beginPath();
          ctx.moveTo(i, 0);
          ctx.lineTo(i, size);
          ctx.stroke();
          ctx.beginPath();
          ctx.moveTo(0, i);
          ctx.lineTo(size, i);
          ctx.stroke();
        }
      }

      // 3. Ring Borders
      if (ringStyle !== 'none') {
        ctx.save();
        ctx.strokeStyle = ringColor;
        ctx.lineWidth = 8;
        if (ringStyle === 'single') {
          ctx.beginPath();
          ctx.arc(size / 2, size / 2, size / 2 - 20, 0, Math.PI * 2);
          ctx.stroke();
        } else if (ringStyle === 'double') {
          ctx.beginPath();
          ctx.arc(size / 2, size / 2, size / 2 - 16, 0, Math.PI * 2);
          ctx.stroke();
          ctx.lineWidth = 2;
          ctx.beginPath();
          ctx.arc(size / 2, size / 2, size / 2 - 32, 0, Math.PI * 2);
          ctx.stroke();
        } else if (ringStyle === 'segmented') {
          ctx.lineWidth = 8;
          ctx.setLineDash([30, 15]);
          ctx.beginPath();
          ctx.arc(size / 2, size / 2, size / 2 - 20, 0, Math.PI * 2);
          ctx.stroke();
        }
        ctx.restore();
      }

      // 4. Center Typography
      ctx.save();
      ctx.textAlign = 'center';
      ctx.textBaseline = 'middle';
      const fontSize = monogramText.length > 2 ? 160 : 190;
      ctx.font = `${fontWeight} ${fontSize}px "${fontFamily}", sans-serif`;

      // Text Shadow / Glow
      if (monogramTheme === 'white') {
        ctx.fillStyle = '#09090b';
      } else {
        ctx.shadowColor = ringColor;
        ctx.shadowBlur = 24;
        ctx.fillStyle = '#ffffff';
      }

      ctx.fillText(monogramText || 'SF', size / 2, size / 2 + 8);
      ctx.restore();
    }

    // --- TAB 2: GENERATIVE IDENTICON ---
    else if (activeTab === 'generative') {
      // Background
      const isDark = genPalette !== 'silver';
      ctx.fillStyle = isDark ? '#09090b' : '#f4f4f5';
      ctx.fillRect(0, 0, size, size);

      // Seed Pseudo-Random Generator
      let s = genSeed;
      const pseudoRandom = () => {
        s = (s * 9301 + 49297) % 233280;
        return s / 233280;
      };

      const cols = genGridSize;
      const rows = genGridSize;
      const padding = 64;
      const cellSize = (size - padding * 2) / cols;

      // Color Palette Selection
      let fillColors = ['#ffffff', '#a1a1aa', '#52525b'];
      if (genPalette === 'noir') {
        fillColors = ['#ffffff', '#d4d4d8', '#71717a'];
      } else if (genPalette === 'silver') {
        fillColors = ['#09090b', '#27272a', '#71717a'];
      } else if (genPalette === 'matrix') {
        fillColors = ['#22c55e', '#4ade80', '#15803d'];
      } else if (genPalette === 'cyber') {
        fillColors = ['#06b6d4', '#38bdf8', '#0284c7'];
      } else if (genPalette === 'ember') {
        fillColors = ['#f59e0b', '#fbbf24', '#b45309'];
      }

      // Half-grid computation for symmetry
      const halfCols = Math.ceil(cols / 2);
      const grid: boolean[][] = [];
      for (let y = 0; y < rows; y++) {
        grid[y] = [];
        for (let x = 0; x < halfCols; x++) {
          grid[y][x] = pseudoRandom() > 0.42;
        }
        // Mirror horizontally
        for (let x = halfCols; x < cols; x++) {
          grid[y][x] = genSymmetry === 'none' ? pseudoRandom() > 0.42 : grid[y][cols - 1 - x];
        }
      }

      // Quad symmetry
      if (genSymmetry === 'quad') {
        for (let y = Math.ceil(rows / 2); y < rows; y++) {
          for (let x = 0; x < cols; x++) {
            grid[y][x] = grid[rows - 1 - y][x];
          }
        }
      }

      // Render Grid Cells with Rounded Rectangles
      for (let y = 0; y < rows; y++) {
        for (let x = 0; x < cols; x++) {
          if (grid[y][x]) {
            const colorIdx = Math.floor(pseudoRandom() * fillColors.length);
            ctx.fillStyle = fillColors[colorIdx];
            const px = padding + x * cellSize;
            const py = padding + y * cellSize;
            const radius = 12;

            ctx.beginPath();
            ctx.roundRect(px + 4, py + 4, cellSize - 8, cellSize - 8, radius);
            ctx.fill();
          }
        }
      }

      // High-tech subtle bounding ring
      ctx.strokeStyle = isDark ? 'rgba(255, 255, 255, 0.15)' : 'rgba(0, 0, 0, 0.15)';
      ctx.lineWidth = 4;
      ctx.strokeRect(padding - 8, padding - 8, size - (padding - 8) * 2, size - (padding - 8) * 2);
    }

    // --- TAB 3: DEVELOPER PERSONA ---
    else if (activeTab === 'persona') {
      // 1. Sleek Dark Gradient Background
      const bgGrad = ctx.createRadialGradient(size / 2, size / 2, 80, size / 2, size / 2, size / 2);
      bgGrad.addColorStop(0, '#27272a');
      bgGrad.addColorStop(1, '#09090b');
      ctx.fillStyle = bgGrad;
      ctx.fillRect(0, 0, size, size);

      // Skin Tone Map
      let skinHex = '#e2e8f0';
      if (skinTone === 'fair') skinHex = '#fed7aa';
      if (skinTone === 'warm') skinHex = '#fdba74';
      if (skinTone === 'tan') skinHex = '#d97706';
      if (skinTone === 'deep') skinHex = '#78350f';
      if (skinTone === 'chrome') skinHex = '#cbd5e1';

      // 2. Torso / Hoodie
      ctx.fillStyle = outfitColor;
      ctx.beginPath();
      ctx.moveTo(120, size);
      ctx.bezierCurveTo(120, 390, 200, 370, 256, 370);
      ctx.bezierCurveTo(312, 370, 392, 390, 392, size);
      ctx.closePath();
      ctx.fill();

      // Hoodie Neckline V
      ctx.fillStyle = '#09090b';
      ctx.beginPath();
      ctx.moveTo(226, 370);
      ctx.lineTo(256, 420);
      ctx.lineTo(286, 370);
      ctx.closePath();
      ctx.fill();

      // 3. Head & Neck
      // Neck
      ctx.fillStyle = skinHex;
      ctx.fillRect(230, 310, 52, 70);

      // Head Base Oval
      ctx.beginPath();
      ctx.ellipse(256, 230, 95, 115, 0, 0, Math.PI * 2);
      ctx.fill();

      // 4. Hair Styles
      ctx.fillStyle = hairColor;
      if (hairStyle === 'buzz') {
        ctx.beginPath();
        ctx.ellipse(256, 175, 96, 55, 0, Math.PI, Math.PI * 2);
        ctx.fill();
      } else if (hairStyle === 'sidepart') {
        ctx.beginPath();
        ctx.arc(256, 200, 102, Math.PI * 0.95, Math.PI * 2.1);
        ctx.lineTo(360, 240);
        ctx.lineTo(340, 150);
        ctx.lineTo(210, 140);
        ctx.closePath();
        ctx.fill();
      } else if (hairStyle === 'curly') {
        for (let angle = Math.PI * 0.9; angle <= Math.PI * 2.1; angle += 0.22) {
          const cx = 256 + Math.cos(angle) * 98;
          const cy = 210 + Math.sin(angle) * 98;
          ctx.beginPath();
          ctx.arc(cx, cy, 32, 0, Math.PI * 2);
          ctx.fill();
        }
      } else if (hairStyle === 'beanie') {
        // Tech Beanie Hat
        ctx.fillStyle = '#18181b';
        ctx.beginPath();
        ctx.roundRect(150, 130, 212, 95, [45, 45, 12, 12]);
        ctx.fill();
        // Beanie cuff
        ctx.fillStyle = '#27272a';
        ctx.beginPath();
        ctx.roundRect(144, 200, 224, 34, 10);
        ctx.fill();
      } else if (hairStyle === 'headset') {
        // Gamer / Audio Dev Headset band
        ctx.strokeStyle = '#e4e4e7';
        ctx.lineWidth = 14;
        ctx.beginPath();
        ctx.arc(256, 220, 110, Math.PI, Math.PI * 2);
        ctx.stroke();
        // Earcups
        ctx.fillStyle = '#09090b';
        ctx.beginPath();
        ctx.roundRect(132, 190, 26, 60, 12);
        ctx.roundRect(354, 190, 26, 60, 12);
        ctx.fill();
      }

      // 5. Eyes & Eyebrows
      ctx.fillStyle = '#09090b';
      // Left eye & Right eye
      ctx.beginPath();
      ctx.arc(215, 235, 7, 0, Math.PI * 2);
      ctx.arc(297, 235, 7, 0, Math.PI * 2);
      ctx.fill();

      // Friendly subtle smile
      ctx.strokeStyle = '#09090b';
      ctx.lineWidth = 3.5;
      ctx.beginPath();
      ctx.arc(256, 280, 22, 0.15 * Math.PI, 0.85 * Math.PI);
      ctx.stroke();

      // 6. Glasses / Eyewear
      if (glasses === 'frames') {
        ctx.strokeStyle = '#09090b';
        ctx.lineWidth = 5;
        // Left rim
        ctx.beginPath();
        ctx.roundRect(185, 215, 60, 42, 8);
        ctx.stroke();
        // Right rim
        ctx.beginPath();
        ctx.roundRect(267, 215, 60, 42, 8);
        ctx.stroke();
        // Bridge
        ctx.beginPath();
        ctx.moveTo(245, 230);
        ctx.lineTo(267, 230);
        ctx.stroke();
      } else if (glasses === 'visor') {
        // Cybernetic HUD Visor
        ctx.fillStyle = 'rgba(6, 182, 212, 0.85)';
        ctx.beginPath();
        ctx.roundRect(175, 215, 162, 36, 18);
        ctx.fill();
        ctx.strokeStyle = '#ffffff';
        ctx.lineWidth = 2;
        ctx.stroke();
      } else if (glasses === 'sunglasses') {
        ctx.fillStyle = '#09090b';
        ctx.beginPath();
        ctx.roundRect(185, 215, 60, 40, 6);
        ctx.roundRect(267, 215, 60, 40, 6);
        ctx.fill();
        ctx.fillRect(245, 225, 22, 6);
      }

      // 7. Tech Badge Overlay on Corner
      if (badgeType === 'terminal') {
        // Circular badge bottom-right
        ctx.fillStyle = '#09090b';
        ctx.beginPath();
        ctx.arc(410, 410, 48, 0, Math.PI * 2);
        ctx.fill();
        ctx.strokeStyle = '#22c55e';
        ctx.lineWidth = 4;
        ctx.stroke();

        ctx.fillStyle = '#22c55e';
        ctx.font = 'bold 36px "JetBrains Mono", monospace';
        ctx.textAlign = 'center';
        ctx.textBaseline = 'middle';
        ctx.fillText('>_', 410, 412);
      } else if (badgeType === 'verified') {
        ctx.fillStyle = '#09090b';
        ctx.beginPath();
        ctx.arc(410, 410, 48, 0, Math.PI * 2);
        ctx.fill();
        ctx.strokeStyle = '#38bdf8';
        ctx.lineWidth = 4;
        ctx.stroke();

        ctx.fillStyle = '#38bdf8';
        ctx.font = 'bold 44px sans-serif';
        ctx.textAlign = 'center';
        ctx.textBaseline = 'middle';
        ctx.fillText('✓', 410, 412);
      } else if (badgeType === 'sparkle') {
        ctx.fillStyle = '#09090b';
        ctx.beginPath();
        ctx.arc(410, 410, 48, 0, Math.PI * 2);
        ctx.fill();
        ctx.strokeStyle = '#f59e0b';
        ctx.lineWidth = 4;
        ctx.stroke();

        ctx.fillStyle = '#f59e0b';
        ctx.font = '38px sans-serif';
        ctx.textAlign = 'center';
        ctx.textBaseline = 'middle';
        ctx.fillText('✦', 410, 412);
      }
    }
  }, [
    activeTab,
    monogramText,
    fontFamily,
    fontWeight,
    monogramTheme,
    patternStyle,
    ringStyle,
    ringColor,
    genSeed,
    genGridSize,
    genSymmetry,
    genPalette,
    skinTone,
    hairStyle,
    hairColor,
    glasses,
    outfitColor,
    badgeType,
  ]);

  useEffect(() => {
    if (isOpen) {
      renderCanvas();
    }
  }, [isOpen, renderCanvas]);

  if (!isOpen) return null;

  const handleApply = () => {
    const canvas = canvasRef.current;
    if (!canvas) return;
    const dataUrl = canvas.toDataURL('image/png');
    onSaveAvatar(dataUrl);
    onClose();
  };

  const handleDownload = () => {
    const canvas = canvasRef.current;
    if (!canvas) return;
    const dataUrl = canvas.toDataURL('image/png');
    const link = document.createElement('a');
    link.download = `sprintforge-avatar-${Date.now()}.png`;
    link.href = dataUrl;
    link.click();
  };

  const handleCopyToClipboard = async () => {
    const canvas = canvasRef.current;
    if (!canvas) return;
    try {
      canvas.toBlob(async (blob) => {
        if (!blob) return;
        await navigator.clipboard.write([
          new ClipboardItem({ 'image/png': blob }),
        ]);
        setCopied(true);
        setTimeout(() => setCopied(false), 2000);
      });
    } catch {
      // Fallback
      setCopied(true);
      setTimeout(() => setCopied(false), 2000);
    }
  };

  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center p-4 bg-black/40 dark:bg-black/80 backdrop-blur-sm animate-in fade-in duration-200">
      <div className="relative w-full max-w-4xl bg-white dark:bg-zinc-950 border border-zinc-200 dark:border-zinc-800 rounded-2xl shadow-2xl overflow-hidden flex flex-col max-h-[92vh] text-zinc-900 dark:text-white">
        {/* Modal Header */}
        <div className="px-6 py-4 border-b border-zinc-200 dark:border-zinc-800/80 flex items-center justify-between bg-zinc-50 dark:bg-zinc-900/40">
          <div className="flex items-center gap-3">
            <div className="w-8 h-8 rounded-lg bg-zinc-100 dark:bg-zinc-800 flex items-center justify-center border border-zinc-200 dark:border-zinc-700">
              <SprintForgeIcon className="w-4.5 h-4.5 text-zinc-800 dark:text-white" />
            </div>
            <div>
              <h2 className="text-base font-semibold text-zinc-900 dark:text-white tracking-tight flex items-center gap-2">
                SprintForge Avatar Studio
                <span className="text-[10px] uppercase font-mono px-2 py-0.5 rounded bg-zinc-100 text-zinc-600 border border-zinc-200 dark:bg-zinc-800 dark:text-zinc-400 dark:border-zinc-700">
                  Built-in Engine
                </span>
              </h2>
              <p className="text-xs text-zinc-500 dark:text-zinc-400">
                Craft custom Linear-style monograms, algorithmic cyber identicons, or developer avatars.
              </p>
            </div>
          </div>
          <button
            onClick={onClose}
            className="p-1.5 rounded-lg text-zinc-400 hover:text-zinc-900 dark:hover:text-white hover:bg-zinc-100 dark:hover:bg-zinc-800 transition-colors"
          >
            <X className="w-5 h-5" />
          </button>
        </div>

        {/* Modal Body */}
        <div className="flex-1 overflow-y-auto grid grid-cols-1 md:grid-cols-12 gap-6 p-6">
          {/* Left Canvas Preview Area */}
          <div className="md:col-span-5 flex flex-col items-center justify-center gap-4 bg-zinc-50 dark:bg-zinc-900/30 p-6 rounded-xl border border-zinc-200 dark:border-zinc-800/60">
            <div className="relative group">
              <div
                className={`transition-all duration-300 shadow-xl border border-zinc-300 dark:border-zinc-700/80 overflow-hidden ${
                  previewShape === 'circle' ? 'rounded-full' : 'rounded-3xl'
                }`}
                style={{ width: 240, height: 240 }}
              >
                <canvas
                  ref={canvasRef}
                  style={{ width: '100%', height: '100%', display: 'block' }}
                />
              </div>

              {/* High-res indicator badge */}
              <div className="absolute -bottom-2 -right-2 bg-white/95 dark:bg-zinc-900/90 text-[10px] font-mono px-2 py-0.5 rounded-full border border-zinc-200 dark:border-zinc-700 text-zinc-700 dark:text-zinc-300 shadow-md">
                512 × 512 HD
              </div>
            </div>

            {/* Shape Preview Toggle */}
            <div className="flex items-center gap-2 bg-zinc-100 dark:bg-zinc-900 border border-zinc-200 dark:border-zinc-800 rounded-lg p-1 text-xs">
              <button
                onClick={() => setPreviewShape('circle')}
                className={`flex items-center gap-1.5 px-3 py-1 rounded-md transition-colors ${
                  previewShape === 'circle'
                    ? 'bg-white text-zinc-900 shadow-xs dark:bg-zinc-800 dark:text-white font-medium'
                    : 'text-zinc-500 hover:text-zinc-900 dark:text-zinc-400 dark:hover:text-zinc-200'
                }`}
              >
                <Circle className="w-3.5 h-3.5" />
                Circle
              </button>
              <button
                onClick={() => setPreviewShape('squircle')}
                className={`flex items-center gap-1.5 px-3 py-1 rounded-md transition-colors ${
                  previewShape === 'squircle'
                    ? 'bg-white text-zinc-900 shadow-xs dark:bg-zinc-800 dark:text-white font-medium'
                    : 'text-zinc-500 hover:text-zinc-900 dark:text-zinc-400 dark:hover:text-zinc-200'
                }`}
              >
                <Square className="w-3.5 h-3.5" />
                Squircle
              </button>
            </div>

            {/* Quick Actions */}
            <div className="flex items-center gap-2 w-full pt-2">
              <button
                onClick={handleDownload}
                className="flex-1 flex items-center justify-center gap-1.5 py-2 px-3 text-xs font-medium rounded-lg bg-white dark:bg-zinc-900 border border-zinc-200 dark:border-zinc-800 text-zinc-700 dark:text-zinc-300 hover:text-zinc-900 dark:hover:text-white hover:bg-zinc-100 dark:hover:bg-zinc-800 transition-colors"
                title="Download PNG to disk"
              >
                <Download className="w-3.5 h-3.5" />
                Save PNG
              </button>
              <button
                onClick={handleCopyToClipboard}
                className="flex items-center justify-center gap-1.5 py-2 px-3 text-xs font-medium rounded-lg bg-white dark:bg-zinc-900 border border-zinc-200 dark:border-zinc-800 text-zinc-700 dark:text-zinc-300 hover:text-zinc-900 dark:hover:text-white hover:bg-zinc-100 dark:hover:bg-zinc-800 transition-colors"
                title="Copy to clipboard"
              >
                {copied ? <Check className="w-3.5 h-3.5 text-emerald-500" /> : <Copy className="w-3.5 h-3.5" />}
                {copied ? 'Copied' : 'Copy'}
              </button>
            </div>
          </div>

          {/* Right Controls Panel */}
          <div className="md:col-span-7 flex flex-col">
            {/* Tabs Navigation */}
            <div className="flex items-center gap-1 border-b border-zinc-200 dark:border-zinc-800 pb-3 mb-4">
              <button
                onClick={() => setActiveTab('monogram')}
                className={`flex items-center gap-2 px-3 py-1.5 rounded-lg text-xs font-medium transition-all ${
                  activeTab === 'monogram'
                    ? 'bg-zinc-900 text-white dark:bg-white dark:text-black font-semibold'
                    : 'text-zinc-500 hover:text-zinc-900 hover:bg-zinc-100 dark:text-zinc-400 dark:hover:text-zinc-200 dark:hover:bg-zinc-900'
                }`}
              >
                <Type className="w-3.5 h-3.5" />
                Monogram
              </button>
              <button
                onClick={() => setActiveTab('generative')}
                className={`flex items-center gap-2 px-3 py-1.5 rounded-lg text-xs font-medium transition-all ${
                  activeTab === 'generative'
                    ? 'bg-zinc-900 text-white dark:bg-white dark:text-black font-semibold'
                    : 'text-zinc-500 hover:text-zinc-900 hover:bg-zinc-100 dark:text-zinc-400 dark:hover:text-zinc-200 dark:hover:bg-zinc-900'
                }`}
              >
                <Cpu className="w-3.5 h-3.5" />
                Algorithmic
              </button>
              <button
                onClick={() => setActiveTab('persona')}
                className={`flex items-center gap-2 px-3 py-1.5 rounded-lg text-xs font-medium transition-all ${
                  activeTab === 'persona'
                    ? 'bg-zinc-900 text-white dark:bg-white dark:text-black font-semibold'
                    : 'text-zinc-500 hover:text-zinc-900 hover:bg-zinc-100 dark:text-zinc-400 dark:hover:text-zinc-200 dark:hover:bg-zinc-900'
                }`}
              >
                <UserIcon className="w-3.5 h-3.5" />
                Dev Persona
              </button>
              <button
                onClick={() => setActiveTab('presets')}
                className={`flex items-center gap-2 px-3 py-1.5 rounded-lg text-xs font-medium transition-all ${
                  activeTab === 'presets'
                    ? 'bg-zinc-900 text-white dark:bg-white dark:text-black font-semibold'
                    : 'text-zinc-500 hover:text-zinc-900 hover:bg-zinc-100 dark:text-zinc-400 dark:hover:text-zinc-200 dark:hover:bg-zinc-900'
                }`}
              >
                <Layers className="w-3.5 h-3.5" />
                Presets
              </button>
            </div>

            {/* TAB CONTENT: MONOGRAM */}
            {activeTab === 'monogram' && (
              <div className="space-y-4 text-xs">
                {/* Text & Font */}
                <div className="grid grid-cols-2 gap-3">
                  <div>
                    <label className="block text-zinc-600 dark:text-zinc-400 mb-1 font-medium">Initials / Glyphs</label>
                    <input
                      type="text"
                      maxLength={3}
                      value={monogramText}
                      onChange={(e) => setMonogramText(e.target.value.toUpperCase())}
                      className="w-full px-3 py-2 bg-zinc-50 dark:bg-zinc-900 border border-zinc-200 dark:border-zinc-800 rounded-lg text-zinc-900 dark:text-white font-mono text-center text-sm focus:outline-none focus:border-zinc-400 dark:focus:border-zinc-500"
                      placeholder="SF"
                    />
                  </div>
                  <div>
                    <label className="block text-zinc-600 dark:text-zinc-400 mb-1 font-medium">Font Family</label>
                    <select
                      value={fontFamily}
                      onChange={(e) => setFontFamily(e.target.value as any)}
                      className="w-full px-3 py-2 bg-zinc-50 dark:bg-zinc-900 border border-zinc-200 dark:border-zinc-800 rounded-lg text-zinc-900 dark:text-white text-xs focus:outline-none focus:border-zinc-400 dark:focus:border-zinc-500"
                    >
                      <option value="Inter">Inter (Clean Modern)</option>
                      <option value="Space Grotesk">Space Grotesk (Tech)</option>
                      <option value="JetBrains Mono">JetBrains Mono (Code)</option>
                      <option value="Syne">Syne (Geometric Art)</option>
                      <option value="Cinzel">Cinzel (Executive)</option>
                    </select>
                  </div>
                </div>

                {/* Color Theme */}
                <div>
                  <label className="block text-zinc-600 dark:text-zinc-400 mb-1.5 font-medium">Theme Gradient</label>
                  <div className="grid grid-cols-3 gap-2">
                    {[
                      { id: 'obsidian', label: 'Linear Obsidian' },
                      { id: 'white', label: 'Titanium White' },
                      { id: 'matrix', label: 'Matrix Emerald' },
                      { id: 'aurora', label: 'Midnight Aurora' },
                      { id: 'sunset', label: 'Carbon Sunset' },
                      { id: 'slate', label: 'Slate Minimal' },
                    ].map((th) => (
                      <button
                        key={th.id}
                        onClick={() => setMonogramTheme(th.id as any)}
                        className={`px-2.5 py-2 rounded-lg border text-left transition-all ${
                          monogramTheme === th.id
                            ? 'bg-zinc-900 text-white border-zinc-900 dark:bg-zinc-800 dark:border-white dark:text-white font-medium shadow-xs'
                            : 'bg-zinc-50 border-zinc-200 text-zinc-600 hover:text-zinc-900 hover:border-zinc-300 dark:bg-zinc-900/60 dark:border-zinc-800 dark:text-zinc-400 dark:hover:text-zinc-200'
                        }`}
                      >
                        {th.label}
                      </button>
                    ))}
                  </div>
                </div>

                {/* Texture Pattern */}
                <div>
                  <label className="block text-zinc-600 dark:text-zinc-400 mb-1.5 font-medium">Pattern Overlay</label>
                  <div className="grid grid-cols-4 gap-2">
                    {[
                      { id: 'none', label: 'Solid' },
                      { id: 'dots', label: 'Dot Matrix' },
                      { id: 'stripes', label: 'Carbon' },
                      { id: 'grid', label: 'Grid Mesh' },
                    ].map((pat) => (
                      <button
                        key={pat.id}
                        onClick={() => setPatternStyle(pat.id as any)}
                        className={`py-1.5 px-2 rounded-lg border text-center transition-all ${
                          patternStyle === pat.id
                            ? 'bg-zinc-900 text-white border-zinc-900 dark:bg-zinc-800 dark:border-white dark:text-white font-medium shadow-xs'
                            : 'bg-zinc-50 border-zinc-200 text-zinc-600 hover:text-zinc-900 hover:border-zinc-300 dark:bg-zinc-900/60 dark:border-zinc-800 dark:text-zinc-400 dark:hover:text-zinc-200'
                        }`}
                      >
                        {pat.label}
                      </button>
                    ))}
                  </div>
                </div>

                {/* Ring Border & Accent */}
                <div className="grid grid-cols-2 gap-3">
                  <div>
                    <label className="block text-zinc-600 dark:text-zinc-400 mb-1.5 font-medium">Ring Border</label>
                    <div className="grid grid-cols-2 gap-1.5">
                      {[
                        { id: 'none', label: 'None' },
                        { id: 'single', label: 'Single' },
                        { id: 'double', label: 'Double' },
                        { id: 'segmented', label: 'Status' },
                      ].map((r) => (
                        <button
                          key={r.id}
                          onClick={() => setRingStyle(r.id as any)}
                          className={`py-1 px-2 rounded-md border text-center transition-all ${
                            ringStyle === r.id
                              ? 'bg-zinc-900 text-white border-zinc-900 dark:bg-zinc-800 dark:border-white dark:text-white font-medium shadow-xs'
                              : 'bg-zinc-50 border-zinc-200 text-zinc-600 hover:text-zinc-900 hover:border-zinc-300 dark:bg-zinc-900 dark:border-zinc-800 dark:text-zinc-400 dark:hover:text-zinc-200'
                          }`}
                        >
                          {r.label}
                        </button>
                      ))}
                    </div>
                  </div>
                  <div>
                    <label className="block text-zinc-600 dark:text-zinc-400 mb-1.5 font-medium">Ring Color</label>
                    <div className="flex items-center gap-2 pt-1">
                      {[
                        { color: '#ffffff', label: 'White' },
                        { color: '#71717a', label: 'Slate' },
                        { color: '#22c55e', label: 'Emerald' },
                        { color: '#06b6d4', label: 'Cyan' },
                        { color: '#f59e0b', label: 'Amber' },
                      ].map((c) => (
                        <button
                          key={c.color}
                          onClick={() => setRingColor(c.color as any)}
                          className={`w-6 h-6 rounded-full border-2 transition-transform ${
                            ringColor === c.color ? 'scale-125 border-zinc-900 dark:border-white shadow-xs' : 'border-zinc-300 dark:border-transparent'
                          }`}
                          style={{ backgroundColor: c.color }}
                          title={c.label}
                        />
                      ))}
                    </div>
                  </div>
                </div>
              </div>
            )}

            {/* TAB CONTENT: GENERATIVE IDENTICON */}
            {activeTab === 'generative' && (
              <div className="space-y-4 text-xs">
                <div className="flex items-center justify-between bg-zinc-50 dark:bg-zinc-900 border border-zinc-200 dark:border-zinc-800 rounded-lg p-3">
                  <div>
                    <div className="font-semibold text-zinc-900 dark:text-white">Infinite Seed Generator</div>
                    <div className="text-zinc-500 dark:text-zinc-400 font-mono text-[11px]">Seed: #{genSeed}</div>
                  </div>
                  <Button
                    variant="outline"
                    size="sm"
                    onClick={() => setGenSeed(Math.floor(Math.random() * 99999))}
                    className="flex items-center gap-1.5 text-xs bg-white hover:bg-zinc-100 text-zinc-900 border-zinc-300 dark:bg-zinc-800 dark:hover:bg-zinc-700 dark:text-white dark:border-zinc-700"
                  >
                    <RefreshCw className="w-3.5 h-3.5" />
                    Roll Random
                  </Button>
                </div>

                <div className="grid grid-cols-2 gap-3">
                  <div>
                    <label className="block text-zinc-600 dark:text-zinc-400 mb-1.5 font-medium">Grid Resolution</label>
                    <div className="grid grid-cols-3 gap-2">
                      {[4, 6, 8].map((size) => (
                        <button
                          key={size}
                          onClick={() => setGenGridSize(size as any)}
                          className={`py-1.5 rounded-lg border text-center transition-all ${
                            genGridSize === size
                              ? 'bg-zinc-900 text-white border-zinc-900 dark:bg-zinc-800 dark:border-white dark:text-white font-medium shadow-xs'
                              : 'bg-zinc-50 border-zinc-200 text-zinc-600 hover:text-zinc-900 hover:border-zinc-300 dark:bg-zinc-900 dark:border-zinc-800 dark:text-zinc-400 dark:hover:text-zinc-200'
                          }`}
                        >
                          {size} × {size}
                        </button>
                      ))}
                    </div>
                  </div>

                  <div>
                    <label className="block text-zinc-600 dark:text-zinc-400 mb-1.5 font-medium">Symmetry Rule</label>
                    <div className="grid grid-cols-3 gap-2">
                      {[
                        { id: 'mirror', label: 'Mirror' },
                        { id: 'quad', label: '4-Way' },
                        { id: 'none', label: 'Free' },
                      ].map((sym) => (
                        <button
                          key={sym.id}
                          onClick={() => setGenSymmetry(sym.id as any)}
                          className={`py-1.5 rounded-lg border text-center transition-all ${
                            genSymmetry === sym.id
                              ? 'bg-zinc-900 text-white border-zinc-900 dark:bg-zinc-800 dark:border-white dark:text-white font-medium shadow-xs'
                              : 'bg-zinc-50 border-zinc-200 text-zinc-600 hover:text-zinc-900 hover:border-zinc-300 dark:bg-zinc-900 dark:border-zinc-800 dark:text-zinc-400 dark:hover:text-zinc-200'
                          }`}
                        >
                          {sym.label}
                        </button>
                      ))}
                    </div>
                  </div>
                </div>

                <div>
                  <label className="block text-zinc-600 dark:text-zinc-400 mb-1.5 font-medium">Color Palette</label>
                  <div className="grid grid-cols-5 gap-2">
                    {[
                      { id: 'noir', label: 'Noir' },
                      { id: 'silver', label: 'Titanium' },
                      { id: 'matrix', label: 'Matrix' },
                      { id: 'cyber', label: 'Cyber' },
                      { id: 'ember', label: 'Ember' },
                    ].map((pal) => (
                      <button
                        key={pal.id}
                        onClick={() => setGenPalette(pal.id as any)}
                        className={`py-2 rounded-lg border text-center transition-all ${
                          genPalette === pal.id
                            ? 'bg-zinc-900 text-white border-zinc-900 dark:bg-zinc-800 dark:border-white dark:text-white font-medium shadow-xs'
                            : 'bg-zinc-50 border-zinc-200 text-zinc-600 hover:text-zinc-900 hover:border-zinc-300 dark:bg-zinc-900 dark:border-zinc-800 dark:text-zinc-400 dark:hover:text-zinc-200'
                        }`}
                      >
                        {pal.label}
                      </button>
                    ))}
                  </div>
                </div>
              </div>
            )}

            {/* TAB CONTENT: PERSONA */}
            {activeTab === 'persona' && (
              <div className="space-y-4 text-xs">
                {/* Skin tone */}
                <div>
                  <label className="block text-zinc-600 dark:text-zinc-400 mb-1.5 font-medium">Skin Tone</label>
                  <div className="grid grid-cols-5 gap-2">
                    {[
                      { id: 'chrome', label: 'Synthetic' },
                      { id: 'fair', label: 'Light' },
                      { id: 'warm', label: 'Warm' },
                      { id: 'tan', label: 'Tan' },
                      { id: 'deep', label: 'Deep' },
                    ].map((st) => (
                      <button
                        key={st.id}
                        onClick={() => setSkinTone(st.id as any)}
                        className={`py-1.5 rounded-lg border text-center transition-all ${
                          skinTone === st.id
                            ? 'bg-zinc-900 text-white border-zinc-900 dark:bg-zinc-800 dark:border-white dark:text-white font-medium shadow-xs'
                            : 'bg-zinc-50 border-zinc-200 text-zinc-600 hover:text-zinc-900 hover:border-zinc-300 dark:bg-zinc-900 dark:border-zinc-800 dark:text-zinc-400 dark:hover:text-zinc-200'
                        }`}
                      >
                        {st.label}
                      </button>
                    ))}
                  </div>
                </div>

                {/* Hair & Headwear */}
                <div className="grid grid-cols-2 gap-3">
                  <div>
                    <label className="block text-zinc-600 dark:text-zinc-400 mb-1.5 font-medium">Headwear & Hair</label>
                    <select
                      value={hairStyle}
                      onChange={(e) => setHairStyle(e.target.value as any)}
                      className="w-full px-3 py-2 bg-zinc-50 dark:bg-zinc-900 border border-zinc-200 dark:border-zinc-800 rounded-lg text-zinc-900 dark:text-white text-xs focus:outline-none focus:border-zinc-400 dark:focus:border-zinc-500"
                    >
                      <option value="beanie">Tech Beanie</option>
                      <option value="headset">Developer Headset</option>
                      <option value="buzz">Buzzcut</option>
                      <option value="sidepart">Executive Side Part</option>
                      <option value="curly">Curly Volume</option>
                    </select>
                  </div>
                  <div>
                    <label className="block text-zinc-600 dark:text-zinc-400 mb-1.5 font-medium">Eyewear</label>
                    <select
                      value={glasses}
                      onChange={(e) => setGlasses(e.target.value as any)}
                      className="w-full px-3 py-2 bg-zinc-50 dark:bg-zinc-900 border border-zinc-200 dark:border-zinc-800 rounded-lg text-zinc-900 dark:text-white text-xs focus:outline-none focus:border-zinc-400 dark:focus:border-zinc-500"
                    >
                      <option value="frames">Code Frames</option>
                      <option value="visor">Cyber Visor</option>
                      <option value="sunglasses">Black Shades</option>
                      <option value="none">No Glasses</option>
                    </select>
                  </div>
                </div>

                {/* Badge Overlay */}
                <div>
                  <label className="block text-zinc-600 dark:text-zinc-400 mb-1.5 font-medium">Specialty Badge</label>
                  <div className="grid grid-cols-4 gap-2">
                    {[
                      { id: 'terminal', label: '>_ Terminal' },
                      { id: 'verified', label: '✓ Verified' },
                      { id: 'sparkle', label: '✦ Elite' },
                      { id: 'none', label: 'None' },
                    ].map((b) => (
                      <button
                        key={b.id}
                        onClick={() => setBadgeType(b.id as any)}
                        className={`py-1.5 px-2 rounded-lg border text-center transition-all ${
                          badgeType === b.id
                            ? 'bg-zinc-900 text-white border-zinc-900 dark:bg-zinc-800 dark:border-white dark:text-white font-medium shadow-xs'
                            : 'bg-zinc-50 border-zinc-200 text-zinc-600 hover:text-zinc-900 hover:border-zinc-300 dark:bg-zinc-900 dark:border-zinc-800 dark:text-zinc-400 dark:hover:text-zinc-200'
                        }`}
                      >
                        {b.label}
                      </button>
                    ))}
                  </div>
                </div>
              </div>
            )}

            {/* TAB CONTENT: PRESETS */}
            {activeTab === 'presets' && (
              <div className="grid grid-cols-2 gap-3 text-xs overflow-y-auto max-h-[300px] pr-1">
                {PRESET_LIST.map((preset) => (
                  <button
                    key={preset.id}
                    onClick={preset.apply}
                    className="flex items-center gap-3 p-3 rounded-xl border border-zinc-200 dark:border-zinc-800 bg-zinc-50 dark:bg-zinc-900/40 hover:bg-zinc-100 dark:hover:bg-zinc-800/60 hover:border-zinc-300 dark:hover:border-zinc-700 transition-all text-left group"
                  >
                    <div
                      className="w-10 h-10 rounded-full border border-zinc-300 dark:border-zinc-700 flex-shrink-0 group-hover:scale-105 transition-transform"
                      style={{ background: preset.preview }}
                    />
                    <div>
                      <div className="font-semibold text-zinc-900 dark:text-white text-xs">{preset.label}</div>
                      <div className="text-[11px] text-zinc-500">Instant One-Click</div>
                    </div>
                  </button>
                ))}
              </div>
            )}

            {/* Action Bar */}
            <div className="mt-auto pt-6 border-t border-zinc-200 dark:border-zinc-800/80 flex items-center justify-end gap-3">
              <Button variant="ghost" onClick={onClose} className="text-zinc-500 hover:text-zinc-900 dark:text-zinc-400 dark:hover:text-white text-xs">
                Cancel
              </Button>
              <Button
                onClick={handleApply}
                className="bg-zinc-900 hover:bg-zinc-800 text-white dark:bg-white dark:hover:bg-zinc-200 dark:text-black font-semibold text-xs px-5 shadow-lg flex items-center gap-2"
              >
                <Check className="w-4 h-4" />
                Apply to Profile
              </Button>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
