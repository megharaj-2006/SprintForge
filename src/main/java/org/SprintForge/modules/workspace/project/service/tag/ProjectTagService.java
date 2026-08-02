package org.SprintForge.modules.workspace.project.service.tag;

import org.SprintForge.modules.workspace.project.entity.ProjectTag;

import java.util.List;

public interface ProjectTagService {
    ProjectTag createTag(Long projectId, String name, String color, String description, Long actorId);
    List<ProjectTag> getTags(Long projectId);
    ProjectTag getTag(Long tagId);
    void deleteTag(Long tagId, Long actorId);
}
