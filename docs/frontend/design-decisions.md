# SprintForge — Complete Design Decision Register

This document is the persistent, authoritative record of all design, architectural, and UI/UX decisions approved by the Lead Architect and Stakeholder for SprintForge.

---

### DEC-001: Backend Integration & Security

- **Decision Title / Question:** How should the `DEVELOPER` role mismatch on new user registration (`POST /api/v1/auth/signup`) be reconciled with `@PreAuthorize("hasAnyRole('USER', 'ADMIN')")`?
- **Options Presented:** A: Change backend default role to USER; B: Change controller annotations to include DEVELOPER; C: Manage role via WorkspaceMemberController / WorkspaceRoleController admin interface after signup.
- **User Approved Answer:** **Option C**
- **Final Approved Specification:** Retain backend unchanged; newly registered users with role `DEVELOPER` will be promoted/managed via WorkspaceMember / WorkspaceRole admin interface.
- **Engineering Rationale:** Avoids invasive backend code changes during read-only frontend phase and preserves existing backend architecture.
- **Affected Code & UI Areas:** `Auth flow, Workspace Member Management, Role Assignment UI`
- **Status:** `APPROVED`

---

### DEC-002: Backend Integration & API Scope

- **Decision Title / Question:** How should the 8 empty skeleton controller stubs in `org.SprintForge.modules.*` be handled?
- **Options Presented:** A: Bypass empty stubs and target active workspace and platform controllers; B: Wait for backend implementation of stubs.
- **User Approved Answer:** **Option A (Approved)**
- **Final Approved Specification:** Bypass empty skeleton stubs; frontend integrates with active production endpoints in `FileStorageController`, `PlatformNotificationController`, `ActivityFeedController`, `TaskController`, `AdvancedSearchController`, and `PlatformManagementController`.
- **Engineering Rationale:** The backend already has full implementations of storage, notifications, activity feeds, task transitions, search, and platform management in other packages.
- **Affected Code & UI Areas:** `API Client routing, Navigation links`
- **Status:** `APPROVED`

---

### DEC-003: Visual Identity & Design Archetype

- **Decision Title / Question:** What visual aesthetic and brand personality should SprintForge embody?
- **Options Presented:** A: Heavy inspiration from Linear — ultra-dense, keyboard-first engineering workstation with high polish and minimalist AI; B: Standard SaaS dashboard; C: Enterprise corporate portal.
- **User Approved Answer:** **Linear aesthetic with ultra-dense keyboard-first engineering, professional minimalist AI, subtle scroll and hover animations, and inspiration from Google Studio.**
- **Final Approved Specification:** Linear-Grade Engineering Workstation: Ultra-dense information architecture, precision typography, subtle spring micro-interactions, dark/light slate monochrome palette, and non-intrusive AI copilot.
- **Engineering Rationale:** Matches the engineering persona of SprintForge developers and technical project managers.
- **Affected Code & UI Areas:** `Global design system, Layout, Typography, All Components`
- **Status:** `APPROVED`

---

### DEC-004: Framework & Build Tooling

- **Decision Title / Question:** Which frontend framework and architecture should be established for SprintForge?
- **Options Presented:** A: React 19 + Vite + TypeScript (SPA); B: Next.js 15 App Router; C: React 19 + Vite + React Three Fiber / Three.js + Framer Motion.
- **User Approved Answer:** **React 19 + Vite + React Three Fiber / Three.js — Vite SPA architecture equipped with Three.js canvas for subtle 3D ambient visual effects in Workflow Studio and Auth pages, alongside Linear-grade UI.**
- **Final Approved Specification:** React 19 + TypeScript + Vite + React Three Fiber / Three.js + Framer Motion + Tailwind CSS + TanStack Query v5 + Zustand + React Router v7.
- **Engineering Rationale:** Instant SPA page navigation with zero layout flash, seamless client-side state, lightweight deployment alongside Spring Boot, and GPU-accelerated 3D ambient canvas where needed.
- **Affected Code & UI Areas:** `Project scaffolding, Build system, Runtime dependencies`
- **Status:** `APPROVED`

---

### DEC-005: Typography & Font Stack

- **Decision Title / Question:** Which typography font stack and numeral rendering rules should be locked in for the Linear aesthetic?
- **Options Presented:** A: Inter Variable with Linear OpenType features (`cv02`, `cv03`, `cv04`, `cv11`, `tnum`) + JetBrains Mono; B: Geist Sans / Geist Mono; C: System font stack.
- **User Approved Answer:** **Inter Variable with Linear OpenType features + JetBrains Mono for code and IDs, compact 11-14px dense scale.**
- **Final Approved Specification:** Inter Variable (`font-sans`) with OpenType features (`cv02`, `cv03`, `cv04`, `cv11`, `tnum`) for ultra-clean grotesque typography + JetBrains Mono (`font-mono`) for task IDs (`SPR-102`), Git hashes, code blocks, and metrics.
- **Engineering Rationale:** Exact match to Linear's signature typography hierarchy and high numerical readability.
- **Affected Code & UI Areas:** `Tailwind typography config, All text components`
- **Status:** `APPROVED`

---

### DEC-006: Color System & Semantic Tokens

- **Decision Title / Question:** How should primary interactive accents and surfaces be styled within the Slate Monochrome palette?
- **Options Presented:** A: Linear-signature Electric Indigo (`#5E6AD2`) for primary CTA, active focus rings, and selection states; B: Pure High-Contrast Monochrome; C: Technical Cyan.
- **User Approved Answer:** **Linear-signature Electric Indigo (`#5E6AD2`) for primary CTA, active focus rings, and selection states, keeping all backgrounds, cards, and navigation in pure Slate Monochrome.**
- **Final Approved Specification:** Slate Monochrome surfaces (Zinc/Slate `#090A0F`, `#12131A`, `#1A1C24` in dark; `#FFFFFF`, `#F8F9FA`, `#ECEEF1` in light) paired with Linear Electric Indigo (`#5E6AD2`) for primary interactive triggers, active tab indicators, and keyboard focus rings.
- **Engineering Rationale:** Preserves elite monochrome professionalism while maintaining instantaneous visual recognition of actionable elements.
- **Affected Code & UI Areas:** `Tailwind color tokens, Button variants, Focus states, Navigation highlights`
- **Status:** `APPROVED`

---

### DEC-007: Theme Support & Mode Switcher

- **Decision Title / Question:** Should SprintForge support Dark Mode, Light Mode, or both?
- **Options Presented:** A: Dark mode only; B: Light mode only; C: Both modes with system preference detection.
- **User Approved Answer:** **Both mode with system preference detection.**
- **Final Approved Specification:** Dual-Theme (Dark & Light) with automated OS system-preference detection (`window.matchMedia('(prefers-color-scheme: dark)')`) and manual override toggle stored in user preferences (`/api/v1/users/preferences/theme`).
- **Engineering Rationale:** Provides flexibility for both daylight environments and late-night developer sessions.
- **Affected Code & UI Areas:** `Theme provider, Tailwind `darkMode: 'class'`, All surfaces and borders`
- **Status:** `APPROVED`

---

### DEC-008: Navigation & Layout Architecture

- **Decision Title / Question:** What layout and navigation structure should be implemented?
- **Options Presented:** A: Collapsible left sidebar with top global omnisearch (Cmd+K) and breadcrumbs; B: Top nav bar only; C: Dual sidebars.
- **User Approved Answer:** **Collapsible left sidebar with top global omnisearch (Cmd+K) and breadcrumbs.**
- **Final Approved Specification:** Three-zone layout: Collapsible left navigation sidebar (Workspace switcher, Project tree, Views, Settings) + Top contextual toolbar with breadcrumbs, project filters, and Omnisearch (`Cmd+K`) + Right-side sliding task details drawer (`/tasks/:id`).
- **Engineering Rationale:** Maximizes horizontal screen real estate for wide Kanban boards and data tables while keeping search and navigation 1 keystroke away.
- **Affected Code & UI Areas:** `Root layout, App Shell, Global Omnisearch component`
- **Status:** `APPROVED`

---

### DEC-009: Keyboard-First Architecture

- **Decision Title / Question:** How extensive should the keyboard-first shortcut system be across SprintForge?
- **Options Presented:** A: Full Linear Single-Key Navigation Suite; B: Moderate keyboard navigation.
- **User Approved Answer:** **Full Linear Single-Key Navigation Suite (`c` create, `j`/`k` navigate, `a` assign, `p` priority, `s` status, `x` select, `Cmd+K` palette).**
- **Final Approved Specification:** Comprehensive Linear Keyboard Shortcuts: `c` (new task modal), `j`/`k` (list traversal), `x` (multi-select rows), `a` (assignee picker), `p` (priority picker), `s` (status transition popover), `Enter` (open drawer), `Esc` (dismiss), `/` (search filter), `Cmd+K` (command palette).
- **Engineering Rationale:** Enables 100% mouse-free project management for senior software engineers.
- **Affected Code & UI Areas:** `Global hotkey listener hook, Command palette, List and Board views`
- **Status:** `APPROVED`

---

### DEC-010: Task Management & View Switcher

- **Decision Title / Question:** How should project task layouts and task creation be presented?
- **Options Presented:** A: Interchangeable Views (Board, List, Timeline/Roadmap) with persistent user preference + Linear-style floating modal (`c` key) for rapid creation; B: Board-first layout with side panel; C: List-first table.
- **User Approved Answer:** **Interchangeable Views (Board, List, Timeline/Roadmap) with persistent user preference + Linear-style floating modal (`c` key) for rapid task creation without leaving the screen.**
- **Final Approved Specification:** Multi-view project layout allowing instant switching between Kanban Board, Data Table List, and Gantt Roadmap, plus centered floating creation modal triggered by `c`.
- **Engineering Rationale:** Supports both visual scrum teams, detailed backlog groomers, and milestone planners seamlessly.
- **Affected Code & UI Areas:** `Project views, Task creation modal, Kanban board`
- **Status:** `APPROVED`

---

### DEC-011: 3D Canvas & Motion Effects

- **Decision Title / Question:** Where should Three.js 3D visual effects and animations be focused?
- **Options Presented:** A: Targeted High-Impact Three.js Canvas on Auth and Workflow Studio; rest uses 120-250ms Framer Motion micro-springs; B: Widespread 3D backgrounds; C: Minimal Three.js.
- **User Approved Answer:** **Targeted High-Impact Three.js Canvas — Subtle interactive particle/mesh on Auth pages and dynamic interactive canvas in Visual Workflow Studio; rest of app uses ultra-fast 120-250ms Framer Motion micro-springs for zero latency.**
- **Final Approved Specification:** Targeted Three.js/R3F visual highlights on `/login`, `/signup`, and `/workflows` canvas; snappy 120-200ms Framer Motion springs for modals, dropdowns, and cards everywhere else.
- **Engineering Rationale:** High visual prestige on entry points and node editors without compromising 60fps rendering in dense data views.
- **Affected Code & UI Areas:** `Auth pages, Visual Workflow Studio, Motion utilities`
- **Status:** `APPROVED`

---

### DEC-012: Data Synchronization & Polling Strategy

- **Decision Title / Question:** Which client-side data synchronization and background polling strategy should we adopt (since no WebSockets exist)?
- **Options Presented:** A: Smart Adaptive Polling + Instant Optimistic Updates (10s active boards, 3s workflows/jobs, 30s notifications); B: Conservative 30s polling; C: Aggressive 5s polling.
- **User Approved Answer:** **Smart Adaptive Polling + Instant Optimistic Updates — 10s for active boards, 3s for running workflow/jobs, 30s for notifications; immediate UI update on user action with automatic rollback and toast on failure.**
- **Final Approved Specification:** TanStack Query v5 adaptive polling intervals (`10s` board, `3s` executing jobs/workflows, `30s` notification counts), paired with immediate optimistic cache updates and rollback on network errors.
- **Engineering Rationale:** Mimics real-time responsiveness without overloading the Spring Boot PostgreSQL backend.
- **Affected Code & UI Areas:** `Query client provider, Custom hooks, Mutation handlers`
- **Status:** `APPROVED`

---

### DEC-013: Iconography & Graphic Assets

- **Decision Title / Question:** Which icon library and iconography style should we standardize on for SprintForge?
- **Options Presented:** A: Lucide React (1.5px stroke, 14-16px dense size) + Linear-style geometric SVG status/priority glyphs + minimalist monochromatic vector empty states; B: Phosphor Icons; C: Heroicons.
- **User Approved Answer:** **Lucide React (1.5px stroke, 14-16px dense size) + Linear-style geometric SVG status/priority glyphs + minimalist monochromatic vector empty states.**
- **Final Approved Specification:** Lucide React 1.5px stroke + custom SVG status symbols (circular quadrant glyphs for Backlog, Todo, In Progress, In Review, Done, Canceled) + clean vector wireframes for empty states.
- **Engineering Rationale:** Exact match to Linear's clean, razor-sharp visual language.
- **Affected Code & UI Areas:** `Icon components, Status badges, Empty state illustrations`
- **Status:** `APPROVED`

---

### DEC-014: Session Management & Silent Refresh

- **Decision Title / Question:** How should JWT expiration and session renewal be handled when an API request returns HTTP 401?
- **Options Presented:** A: Silent Auto-Refresh Interceptor (call `/api/v1/auth/refresh`, queue and replay pending requests); B: Strict immediate logout.
- **User Approved Answer:** **Silent Auto-Refresh Interceptor — On 401, automatically call `/api/v1/auth/refresh`, queue and replay pending requests seamlessly without interrupting the user. Only redirect to `/login` if refresh fails or expires.**
- **Final Approved Specification:** Axios/Fetch response interceptor catching 401 errors, acquiring a fresh JWT via `POST /api/v1/auth/refresh`, and seamlessly replaying original requests. User only redirected if refresh token itself is expired or revoked.
- **Engineering Rationale:** Flawless uninterrupted user experience with zero unexpected logouts during active work sessions.
- **Affected Code & UI Areas:** `API client interceptors, Auth store`
- **Status:** `APPROVED`

---

### DEC-015: Viewport & Responsive Prioritization

- **Decision Title / Question:** What is the viewport and device priority for SprintForge?
- **Options Presented:** A: Desktop/Laptop First with Responsive Mobile Companion; B: Desktop Only; C: Mobile-First.
- **User Approved Answer:** **Desktop/Laptop First with Responsive Mobile Companion — Optimized for keyboard-driven widescreen engineering (1280px+), gracefully adapting to tablets and mobile with collapsible sidebars and vertical stacked Kanban columns.**
- **Final Approved Specification:** Desktop/Laptop first (1280px+ widescreen density), with responsive adaptation for tablets (collapsible sidebar, touch sheets) and mobile (bottom navigation, segmented board tabs).
- **Engineering Rationale:** SprintForge is primarily a desktop workstation for developers and technical leads, but requires mobile companion accessibility for on-the-go issue triage and approval workflows.
- **Affected Code & UI Areas:** `Responsive media queries, Grid containers, Mobile navigation`
- **Status:** `APPROVED`

---

### DEC-016: AI Copilot Experience

- **Decision Title / Question:** How should the SprintForge AI Assistant be invoked and displayed?
- **Options Presented:** A: Floating copilot drawer accessible anywhere via `Cmd+J`; B: Standalone page only; C: In-line modal.
- **User Approved Answer:** **Floating copilot drawer accessible anywhere (Cmd+J).**
- **Final Approved Specification:** Global slide-over AI Copilot Drawer invoked via `Cmd+J` or floating button, supporting prompt engineering, task breakdown, automatic sprint summaries, and workflow automation directly in context.
- **Engineering Rationale:** Provides omnipresent contextual AI assistance without interrupting the engineer's active screen.
- **Affected Code & UI Areas:** `AICopilotDrawer component, Global hotkey manager, AI API integration`
- **Status:** `APPROVED`

---

