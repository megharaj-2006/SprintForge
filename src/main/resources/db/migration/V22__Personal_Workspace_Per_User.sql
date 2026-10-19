-- V22: Personal workspace per user and remove auto-joining workspace 1
CREATE OR REPLACE FUNCTION public.add_new_user_to_default_workspace()
RETURNS trigger
LANGUAGE plpgsql
AS $function$
DECLARE
    new_ws_id bigint;
    ws_slug text;
BEGIN
    ws_slug := lower(regexp_replace(NEW.username, '[^a-zA-Z0-9]', '-', 'g')) || '-' || NEW.id;
    INSERT INTO workspaces (name, slug, description, owner_id, created_at, updated_at, created_by, updated_by, is_deleted, version, is_archived)
    VALUES (NEW.username || '''s Workspace', ws_slug, 'Personal workspace for ' || NEW.username, NEW.id, NOW(), NOW(), NEW.username, NEW.username, false, 0, false)
    RETURNING id INTO new_ws_id;

    INSERT INTO workspace_members (workspace_id, user_id, role_id, created_at, updated_at, created_by, updated_by, is_deleted, version, status, joined_via_invite, joined_at, is_favorite_workspace, is_starred)
    VALUES (new_ws_id, NEW.id, 1, NOW(), NOW(), 'SYSTEM', 'SYSTEM', false, 0, 'ACTIVE', false, NOW(), true, true);

    INSERT INTO notifications (recipient_id, title, message, type, is_read, created_at, is_deleted, version)
    VALUES (NEW.id, 'Welcome to SprintForge!', 'Welcome to SprintForge! Get started by creating your first project and inviting your team.', 'SYSTEM', false, NOW(), false, 0);

    RETURN NEW;
END;
$function$;
