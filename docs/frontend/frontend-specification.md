# SprintForge — Pre-Implementation Frontend Specification

Version: 1.0 (Production Blueprint)  
Author: Lead Frontend Architect & Systems Engineer  
Status: **Pending Final Approval (Phase 7 Gate)**

---

## 1. Product & Visual Direction

SprintForge is an enterprise project management platform tailored for high-velocity software engineering teams.
- **Archetype:** Linear-inspired ultra-dense, keyboard-first workstation.
- **Visual Tone:** Serious, razor-sharp, monochromatic, technical elegance.
- **AI Experience:** Minimalist contextual copilot drawer (`Cmd+J`), avoiding noisy chatbot widgets.
- **Motion Philosophy:** Ultra-fast 120ms–200ms cubic-bezier spring micro-interactions for UI elements; targeted Three.js canvas on Auth and Visual Workflow Studio.

## 2. Approved Design System & Design Tokens

### 2.1 Color Tokens (Slate Monochrome + Electric Indigo)
```css
:root {
  /* Light Mode */
  --bg-app: #FFFFFF;
  --bg-surface: #F8F9FA;
  --bg-surface-hover: #ECEEF1;
  --border-subtle: #E3E5E8;
  --border-strong: #D0D3D8;
  --text-primary: #0F172A;
  --text-secondary: #475569;
  --text-muted: #94A3B8;
  --accent-primary: #5E6AD2;
  --accent-hover: #4F5ABF;
}
.dark {
  /* Dark Mode */
  --bg-app: #08090A;
  --bg-surface: #0E1015;
  --bg-surface-hover: #16181F;
  --border-subtle: #1F2228;
  --border-strong: #2E333D;
  --text-primary: #F8FAFC;
  --text-secondary: #94A3B8;
  --text-muted: #64748B;
  --accent-primary: #5E6AD2;
  --accent-hover: #6E7BE2;
}
```

### 2.2 Typography Scale & Rules
- **Font Family Primary:** `'Inter', -apple-system, BlinkMacSystemFont, sans-serif`
- **Font Family Monospace:** `'JetBrains Mono', 'Fira Code', monospace`
- **Font Feature Settings:** `"cv02", "cv03", "cv04", "cv11", "tnum"` (Tabular figures enabled globally)
- **Type Scale:**
  - Micro (`11px` / `0.6875rem`): Status badges, timestamp metadata, keyboard shortcut badges.
  - Small (`12px` / `0.75rem`): Table row cells, secondary labels, filter pills.
  - Body (`13px` / `0.8125rem`): Task titles, card text, comment markdown, form inputs.
  - Subheading (`14px` / `0.875rem`): Section headers, modal field labels, popover headers.
  - Heading (`16px` / `1.0rem`): Page titles, drawer headers, card aggregate titles.
  - Display (`20px` / `1.25rem`): Executive dashboard KPI numerals, major modal titles.

## 3. Keyboard-First Interaction System

| Key | Action | Scope |
| :--- | :--- | :--- |
| `Cmd+K` / `Ctrl+K` | Open Universal Omnisearch & Command Palette | Global |
| `Cmd+J` / `Ctrl+J` | Toggle Floating AI Copilot Drawer | Global |
| `c` | Open Floating Task Creation Modal | Global |
| `j` / `k` (or `↓`/`↑`) | Navigate next / previous list row or card | List / Board |
| `Enter` / `Space` | Open Task Detail Drawer for selected item | List / Board |
| `s` | Open Status Transition Popover for selected task | List / Board |
| `p` | Open Priority Picker Popover for selected task | List / Board |
| `a` | Open Assignee Picker Popover for selected task | List / Board |
| `x` | Toggle multi-select checkbox for current row | List View |
| `Esc` | Close active drawer, modal, or clear selection | Global |
| `/` | Focus search filter bar | Active View |

## 4. Technical Frontend Architecture

- **Core Framework:** React 19 + TypeScript + Vite.
- **3D Graphics & Visual Canvas:** Three.js + React Three Fiber (`@react-three/fiber`, `@react-three/drei`).
- **Motion:** Framer Motion (`framer-motion`) with custom cubic-bezier spring physics (`type: 'spring', stiffness: 400, damping: 30`).
- **Styling:** Tailwind CSS + Radix UI unstyled primitives (`@radix-ui/react-*`).
- **Server State & Caching:** TanStack Query v5 (`@tanstack/react-query`).
- **Client State:** Zustand (`theme`, `activeWorkspace`, `activeFilters`, `commandPaletteOpen`, `copilotOpen`).
- **Routing:** React Router v7 with protected route guards checking JWT claims and role permissions.
- **HTTP Client:** Axios instance with request interceptor (attaching `Authorization: Bearer <token>`, `X-Workspace-ID`, `X-User-Id`) and response interceptor (handling silent 401 token refresh via `POST /api/v1/auth/refresh`).
- **Icons:** Lucide React (`lucide-react`) + Linear-style custom SVG status glyphs.
- **Data Visualization:** Recharts for sprint burndown, velocity charts, and workload heatmaps.

## 5. Complete Endpoint Coverage Matrix Verification

- Total Discovered Endpoints: **642**
- Total Endpoints Mapped: **642 (100.0%)**
- Complete mapping ledger available in [`docs/frontend/endpoint-coverage-matrix.md`](endpoint-coverage-matrix.md).
- Full parameter and schema catalog available in [`docs/frontend/api-inventory.md`](api-inventory.md).

## 6. Implementation Phasing & Acceptance Criteria

Upon explicit user approval, implementation proceeds through the following sequential sprints:
1. **Sprint 1 (Core Foundation):** Vite + React 19 + TS scaffolding, Tailwind slate monochrome theme, typography setup, Axios JWT interceptors, Zustand stores.
2. **Sprint 2 (Auth & App Shell):** Three.js ambient login/signup pages, Collapsible Sidebar, Top Nav with breadcrumbs, Cmd+K Command Palette, Global Hotkeys.
3. **Sprint 3 (Workspace & Projects):** Workspace switcher, Organization settings, Member management, Project Hub, Project Settings.
4. **Sprint 4 (Task Engine & Kanban):** Interactive Drag-and-Drop Board, Data Table List View, Gantt Roadmap, Floating Task Creator (`c`), Task Detail Drawer.
5. **Sprint 5 (Agile & Governance):** Sprints, Burndown charts, Epics, 5x5 Risk Matrix, Decision records, Change requests, Document management.
6. **Sprint 6 (Analytics, AI & Workflows):** Executive Cockpit, Floating AI Copilot (`Cmd+J`), Three.js Visual Workflow Studio, Integration Marketplace.
7. **Sprint 7 (Polish, Verification & Final Audit):** Verification against all 642 endpoints, responsive testing, accessibility audit.
