package org.SprintForge.modules.workspace.project.service.tag;

import lombok.RequiredArgsConstructor;
import org.SprintForge.common.exception.ConflictException;
import org.SprintForge.common.exception.ResourceNotFoundException;
import org.SprintForge.modules.workspace.project.entity.ProjectTag;
import org.SprintForge.modules.workspace.project.repository.ProjectTagRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectTagServiceImpl implements ProjectTagService {

    private final ProjectTagRepository tagRepository;

    @Override
    @Transactional
    public ProjectTag createTag(Long projectId, String name, String color, String description, Long actorId) {
        if (tagRepository.existsByProjectIdAndNameAndIsDeletedFalse(projectId, name)) {
            throw new ConflictException("Tag with name '" + name + "' already exists in this project.");
        }
        ProjectTag tag = new ProjectTag();
        tag.setProjectId(projectId);
        tag.setName(name);
        tag.setColor(color != null ? color : "#10B981");
        tag.setDescription(description);
        return tagRepository.save(tag);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectTag> getTags(Long projectId) {
        return tagRepository.findByProjectIdAndIsDeletedFalse(projectId);
    }

    @Override
    @Transactional(readOnly = true)
    public ProjectTag getTag(Long tagId) {
        return tagRepository.findById(tagId)
                .filter(t -> !t.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Tag not found with ID: " + tagId));
    }

    @Override
    @Transactional
    public void deleteTag(Long tagId, Long actorId) {
        ProjectTag tag = getTag(tagId);
        tag.markDeleted(actorId != null ? actorId.toString() : "SYSTEM");
        tagRepository.save(tag);
    }
}
