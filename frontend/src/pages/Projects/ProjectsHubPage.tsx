import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import { useWorkspaceStore } from '../../store/useWorkspaceStore';
import { projectApi } from '../../api/endpoints';
import { Button } from '../../components/ui/Button';
import { Modal } from '../../components/ui/Modal';
import type { Project } from '../../types';
import {
  FolderKanban,
  Plus,
  Search,
  ExternalLink,
  Users,
  Layers,
  ArrowRight,
  Shield,
  Activity,
  Archive,
} from 'lucide-react';
import { motion } from 'framer-motion';

export function ProjectsHubPage() {
  const navigate = useNavigate();
  const queryClient = useQueryClient();
  const { currentWorkspace, selectProject } = useWorkspaceStore();
  const [searchQuery, setSearchQuery] = useState('');
  const [createModalOpen, setCreateModalOpen] = useState(false);

  // Form state
  const [name, setName] = useState('');
  const [identifier, setIdentifier] = useState('');
  const [description, setDescription] = useState('');
  const [visibility, setVisibility] = useState('WORKSPACE');
  const [formError, setFormError] = useState<string | null>(null);

  const workspaceId = currentWorkspace?.id;

  const { data: projects = [], isLoading } = useQuery<Project[]>({
    queryKey: ['projects', workspaceId],
    queryFn: async () => {
      if (!workspaceId) return [];
      const res = await projectApi.getProjects(workspaceId);
      return Array.isArray(res) ? res : [];
    },
    enabled: Boolean(workspaceId),
  });

  const createMutation = useMutation({
    mutationFn: async () => {
      if (!workspaceId) throw new Error('No active workspace selected.');
      return projectApi.createProject({
        workspaceId,
        name: name.trim(),
        identifier: identifier.toUpperCase().trim(),
        description: description.trim() || undefined,
        visibility,
      });
    },
    onSuccess: (newProject) => {
      queryClient.invalidateQueries({ queryKey: ['projects', workspaceId] });
      setCreateModalOpen(false);
      setName('');
      setIdentifier('');
      setDescription('');
      selectProject(newProject);
      navigate(`/projects/${newProject.id}/board`);
    },
    onError: (err: any) => {
      const vErrors = err.response?.data?.validationErrors;
      const detailMsg = vErrors ? Object.values(vErrors).join(', ') : null;
      const firstError = err.response?.data?.errors?.[0]?.message;
      setFormError(
        detailMsg ||
        firstError ||
        err.response?.data?.message ||
        err.message ||
        'Failed to create project.'
      );
    },
  });

  const handleCreateSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    setFormError(null);
    if (!name.trim()) {
      setFormError('Project name is required.');
      return;
    }
    if (name.trim().length < 2 || name.trim().length > 100) {
      setFormError('Project name must be between 2 and 100 characters.');
      return;
    }
    if (!identifier.trim()) {
      setFormError('Project identifier (key) is required.');
      return;
    }
    if (identifier.trim().length < 2 || identifier.trim().length > 10) {
      setFormError('Project identifier (key) must be between 2 and 10 characters.');
      return;
    }
    createMutation.mutate();
  };

  const filteredProjects = projects.filter(
    (p) =>
      p.name.toLowerCase().includes(searchQuery.toLowerCase()) ||
      p.identifier?.toLowerCase().includes(searchQuery.toLowerCase()) ||
      p.key?.toLowerCase().includes(searchQuery.toLowerCase()) ||
      p.description?.toLowerCase().includes(searchQuery.toLowerCase())
  );

  return (
    <div className="flex flex-col h-full overflow-y-auto select-none p-6 max-w-7xl mx-auto">
      {/* Top Header */}
      <div className="flex flex-wrap items-center justify-between gap-4 pb-6 border-b border-border/80">
        <div>
          <h1 className="text-xl font-semibold tracking-tight text-foreground flex items-center gap-2.5">
            <Layers className="w-5 h-5 text-primary" />
            Projects Hub
          </h1>
          <p className="text-xs text-muted-foreground mt-1">
            Workspaces, repositories, and engineering initiatives for {currentWorkspace?.name || 'SprintForge'}
          </p>
        </div>

        <Button
          onClick={() => setCreateModalOpen(true)}
          className="text-xs h-8 px-3 font-medium"
        >
          <Plus className="w-3.5 h-3.5 mr-1.5" />
          Create Project
        </Button>
      </div>

      {/* Omnibar & Search */}
      <div className="py-4 flex items-center justify-between gap-4">
        <div className="relative w-full max-w-sm">
          <Search className="w-3.5 h-3.5 text-muted-foreground/60 absolute left-3 top-2.5" />
          <input
            type="text"
            value={searchQuery}
            onChange={(e) => setSearchQuery(e.target.value)}
            placeholder="Search projects by name or key..."
            className="w-full h-8 pl-9 pr-3 rounded-md border border-border/80 bg-surface text-xs placeholder:text-muted-foreground/50 focus:outline-none focus:ring-1 focus:ring-primary"
          />
        </div>
        <div className="text-xs text-muted-foreground">
          Showing <span className="font-semibold text-foreground">{filteredProjects.length}</span> projects
        </div>
      </div>

      {/* Projects Grid */}
      {isLoading ? (
        <div className="py-20 text-center text-xs text-muted-foreground">
          Loading projects...
        </div>
      ) : filteredProjects.length === 0 ? (
        <div className="border border-dashed border-border rounded-xl p-12 text-center my-8 bg-surface/30">
          <FolderKanban className="w-12 h-12 text-muted-foreground/40 mx-auto mb-3" />
          <h3 className="text-sm font-semibold text-foreground">No Projects Found</h3>
          <p className="text-xs text-muted-foreground mt-1 mb-4 max-w-md mx-auto">
            Get started by initializing your first engineering project in this workspace.
          </p>
          <Button
            onClick={() => setCreateModalOpen(true)}
            size="sm"
            className="text-xs h-8"
          >
            <Plus className="w-3.5 h-3.5 mr-1" />
            Create Project
          </Button>
        </div>
      ) : (
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4 mt-2">
          {filteredProjects.map((project) => (
            <motion.div
              key={project.id}
              whileHover={{ y: -2 }}
              transition={{ duration: 0.15 }}
              onClick={() => {
                selectProject(project);
                navigate(`/projects/${project.id}/board`);
              }}
              className="group cursor-pointer rounded-xl border border-border/80 bg-surface p-5 hover:border-foreground/30 hover:shadow-lg transition-all flex flex-col justify-between"
            >
              <div>
                <div className="flex items-start justify-between gap-3 mb-3">
                  <div className="w-9 h-9 rounded-lg bg-surface-raised border border-border flex items-center justify-center font-bold text-xs text-foreground shrink-0 shadow-2xs">
                    {project.identifier || project.key || 'PRJ'}
                  </div>
                  <span className="px-2 py-0.5 rounded text-[10px] font-medium uppercase tracking-wider bg-surface-hover text-muted-foreground border border-border">
                    {project.status || 'ACTIVE'}
                  </span>
                </div>

                <h3 className="text-sm font-semibold text-foreground group-hover:text-foreground transition-colors">
                  {project.name}
                </h3>
                <p className="text-xs text-muted-foreground mt-1 line-clamp-2 leading-relaxed">
                  {project.description || 'No description provided.'}
                </p>
              </div>

              <div className="pt-4 mt-4 border-t border-border/60 flex items-center justify-between text-xs text-muted-foreground">
                <div className="flex items-center gap-1.5 text-[11px]">
                  <Activity className="w-3.5 h-3.5 text-emerald-500" />
                  <span>Sprint Active</span>
                </div>
                <div className="flex items-center gap-1 text-foreground text-xs font-medium group-hover:translate-x-0.5 transition-transform">
                  <span>Open Board</span>
                  <ArrowRight className="w-3 h-3" />
                </div>
              </div>
            </motion.div>
          ))}
        </div>
      )}

      {/* Create Project Modal */}
      <Modal
        isOpen={createModalOpen}
        onClose={() => setCreateModalOpen(false)}
        title="Create New Project"
      >
        <form onSubmit={handleCreateSubmit} className="space-y-4 pt-2">
          {formError && (
            <div className="p-3 rounded-md bg-rose-500/10 border border-rose-500/20 text-rose-400 text-xs">
              {formError}
            </div>
          )}

          <div>
            <label className="block text-xs font-medium text-muted-foreground mb-1">
              Project Name *
            </label>
            <input
              type="text"
              value={name}
              onChange={(e) => {
                setName(e.target.value);
                if (!identifier) {
                  const autoKey = e.target.value
                    .replace(/[^a-zA-Z]/g, '')
                    .slice(0, 4)
                    .toUpperCase();
                  setIdentifier(autoKey);
                }
              }}
              placeholder="e.g. Core Platform Engine"
              required
              className="w-full h-8 px-3 rounded-md border border-border bg-background text-xs placeholder:text-muted-foreground/50 focus:outline-none focus:ring-1 focus:ring-primary"
            />
          </div>

          <div>
            <label className="block text-xs font-medium text-muted-foreground mb-1">
              Project Key (Identifier) *
            </label>
            <input
              type="text"
              value={identifier}
              onChange={(e) => setIdentifier(e.target.value.toUpperCase())}
              placeholder="e.g. CORE"
              maxLength={8}
              required
              className="w-full h-8 px-3 rounded-md border border-border bg-background text-xs uppercase tracking-wide placeholder:text-muted-foreground/50 focus:outline-none focus:ring-1 focus:ring-foreground"
            />
            <p className="text-[10px] text-muted-foreground mt-1">
              Short prefix for all task IDs (e.g. CORE-101)
            </p>
          </div>

          <div>
            <label className="block text-xs font-medium text-muted-foreground mb-1">
              Description
            </label>
            <textarea
              value={description}
              onChange={(e) => setDescription(e.target.value)}
              placeholder="Primary initiatives and engineering goals for this project..."
              rows={3}
              className="w-full p-2.5 rounded-md border border-border bg-background text-xs placeholder:text-muted-foreground/50 focus:outline-none focus:ring-1 focus:ring-primary resize-none"
            />
          </div>

          <div>
            <label className="block text-xs font-medium text-muted-foreground mb-1">
              Visibility
            </label>
            <select
              value={visibility}
              onChange={(e) => setVisibility(e.target.value)}
              className="w-full h-8 px-2 rounded-md border border-border bg-background text-xs text-foreground focus:outline-none focus:ring-1 focus:ring-primary"
            >
              <option value="WORKSPACE">Workspace (All Members)</option>
              <option value="PUBLIC">Public</option>
              <option value="PRIVATE">Private (Restricted)</option>
            </select>
          </div>

          <div className="flex items-center justify-end gap-2 pt-3 border-t border-border">
            <Button
              type="button"
              variant="outline"
              size="sm"
              onClick={() => setCreateModalOpen(false)}
            >
              Cancel
            </Button>
            <Button
              type="submit"
              size="sm"
              disabled={createMutation.isPending}
            >
              {createMutation.isPending ? 'Creating...' : 'Create Project'}
            </Button>
          </div>
        </form>
      </Modal>
    </div>
  );
}

export default ProjectsHubPage;
