package org.SprintForge.modules.workspace.project.release.service;

import org.SprintForge.common.exception.ConflictException;
import org.SprintForge.modules.workspace.project.entity.Project;
import org.SprintForge.modules.workspace.project.release.dto.request.CreateReleaseRequest;
import org.SprintForge.modules.workspace.project.release.dto.response.ReleaseResponse;
import org.SprintForge.modules.workspace.project.release.entity.Release;
import org.SprintForge.modules.workspace.project.release.entity.enums.ReleaseStatus;
import org.SprintForge.modules.workspace.project.release.entity.enums.ReleaseType;
import org.SprintForge.modules.workspace.project.release.repository.ReleaseRepository;
import org.SprintForge.modules.workspace.project.repository.ProjectRepository;
import org.SprintForge.modules.workspace.task.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReleaseServiceImplTest {

    @Mock
    private ReleaseRepository releaseRepository;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @InjectMocks
    private ReleaseServiceImpl releaseService;

    private Project testProject;
    private Release testRelease;

    @BeforeEach
    void setUp() {
        testProject = new Project();
        testProject.setId(1L);

        testRelease = new Release();
        testRelease.setId(10L);
        testRelease.setProjectId(1L);
        testRelease.setName("Version 1.0");
        testRelease.setReleaseVersion("v1.0.0");
        testRelease.setReleaseType(ReleaseType.MAJOR);
        testRelease.setStatus(ReleaseStatus.PLANNING);
    }

    @Test
    void createRelease_Success() {
        CreateReleaseRequest request = CreateReleaseRequest.builder()
                .name("Version 1.0")
                .version("v1.0.0")
                .releaseType(ReleaseType.MAJOR)
                .build();

        when(projectRepository.findById(1L)).thenReturn(Optional.of(testProject));
        when(releaseRepository.existsByProjectIdAndReleaseVersionAndIsDeletedFalse(1L, "v1.0.0")).thenReturn(false);
        when(releaseRepository.save(any(Release.class))).thenReturn(testRelease);
        when(taskRepository.findByReleaseIdAndIsDeletedFalse(10L)).thenReturn(Collections.emptyList());

        ReleaseResponse response = releaseService.createRelease(1L, request, 100L);

        assertNotNull(response);
        assertEquals("Version 1.0", response.getName());
        assertEquals("v1.0.0", response.getVersion());
    }

    @Test
    void createRelease_Conflict_ThrowsException() {
        CreateReleaseRequest request = CreateReleaseRequest.builder()
                .name("Version 1.0")
                .version("v1.0.0")
                .build();

        when(projectRepository.findById(1L)).thenReturn(Optional.of(testProject));
        when(releaseRepository.existsByProjectIdAndReleaseVersionAndIsDeletedFalse(1L, "v1.0.0")).thenReturn(true);

        assertThrows(ConflictException.class, () -> releaseService.createRelease(1L, request, 100L));
    }
}
