package org.SprintForge.modules.workspace.task.service;

import org.SprintForge.common.exception.BusinessRuleException;
import org.SprintForge.common.exception.ResourceNotFoundException;
import org.SprintForge.modules.workspace.task.dto.request.*;
import org.SprintForge.modules.workspace.task.dto.response.ChecklistResponse;
import org.SprintForge.modules.workspace.task.dto.response.ChecklistItemResponse;
import org.SprintForge.modules.workspace.task.entity.Checklist;
import org.SprintForge.modules.workspace.task.entity.ChecklistItem;
import org.SprintForge.modules.workspace.task.entity.Task;
import org.SprintForge.modules.workspace.task.event.*;
import org.SprintForge.modules.workspace.task.mapper.ChecklistMapper;
import org.SprintForge.modules.workspace.task.repository.ChecklistItemRepository;
import org.SprintForge.modules.workspace.task.repository.ChecklistRepository;
import org.SprintForge.modules.workspace.task.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ChecklistServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private ChecklistRepository checklistRepository;

    @Mock
    private ChecklistItemRepository checklistItemRepository;

    @Mock
    private ChecklistMapper checklistMapper;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    private ChecklistService checklistService;

    private Task mockTask;
    private Checklist mockChecklist;
    private ChecklistItem mockItem;

    @BeforeEach
    void setUp() {
        checklistService = new ChecklistServiceImpl(
                taskRepository,
                checklistRepository,
                checklistItemRepository,
                checklistMapper,
                eventPublisher
        );

        mockTask = new Task();
        mockTask.setId(10L);
        mockTask.setArchived(false);
        mockTask.setDeleted(false);

        mockChecklist = new Checklist();
        mockChecklist.setId(20L);
        mockChecklist.setTaskId(10L);
        mockChecklist.setTitle("Test Checklist");
        mockChecklist.setPosition(0);
        mockChecklist.setDeleted(false);

        mockItem = new ChecklistItem();
        mockItem.setId(30L);
        mockItem.setChecklistId(20L);
        mockItem.setTitle("Test Item");
        mockItem.setPosition(0);
        mockItem.setCompleted(false);
        mockItem.setDeleted(false);
    }

    @Test
    void createChecklist_Success() {
        CreateChecklistRequest request = new CreateChecklistRequest("New Checklist");

        when(taskRepository.findById(10L)).thenReturn(Optional.of(mockTask));
        when(checklistRepository.findByTaskIdAndIsDeletedFalseOrderByPositionAsc(10L)).thenReturn(new ArrayList<>());
        when(checklistRepository.save(any(Checklist.class))).thenAnswer(inv -> {
            Checklist c = inv.getArgument(0);
            c.setId(20L);
            return c;
        });

        ChecklistResponse expectedResponse = new ChecklistResponse();
        expectedResponse.setId(20L);
        expectedResponse.setTitle("New Checklist");
        expectedResponse.setPosition(0);
        when(checklistMapper.toResponse(any(Checklist.class), any())).thenReturn(expectedResponse);

        ChecklistResponse response = checklistService.createChecklist(10L, request, 1L);

        assertNotNull(response);
        assertEquals(20L, response.getId());
        assertEquals("New Checklist", response.getTitle());
        verify(checklistRepository).save(any(Checklist.class));
        verify(eventPublisher).publishEvent(any(ChecklistCreatedEvent.class));
    }

    @Test
    void createChecklist_TaskNotFound() {
        CreateChecklistRequest request = new CreateChecklistRequest("New Checklist");
        when(taskRepository.findById(10L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> checklistService.createChecklist(10L, request, 1L));
        verify(checklistRepository, never()).save(any());
    }

    @Test
    void createChecklist_TaskArchived() {
        CreateChecklistRequest request = new CreateChecklistRequest("New Checklist");
        mockTask.setArchived(true);
        when(taskRepository.findById(10L)).thenReturn(Optional.of(mockTask));

        assertThrows(BusinessRuleException.class, () -> checklistService.createChecklist(10L, request, 1L));
        verify(checklistRepository, never()).save(any());
    }

    @Test
    void updateChecklist_Success() {
        UpdateChecklistRequest request = new UpdateChecklistRequest("Updated Checklist");
        when(checklistRepository.findByIdAndIsDeletedFalse(20L)).thenReturn(Optional.of(mockChecklist));
        when(taskRepository.findById(10L)).thenReturn(Optional.of(mockTask));
        when(checklistRepository.save(any(Checklist.class))).thenReturn(mockChecklist);

        ChecklistResponse expectedResponse = new ChecklistResponse();
        expectedResponse.setId(20L);
        expectedResponse.setTitle("Updated Checklist");
        when(checklistMapper.toResponse(any(Checklist.class), any())).thenReturn(expectedResponse);

        ChecklistResponse response = checklistService.updateChecklist(20L, request, 1L);

        assertNotNull(response);
        assertEquals("Updated Checklist", response.getTitle());
        verify(checklistRepository).save(mockChecklist);
    }

    @Test
    void deleteChecklist_Success() {
        when(checklistRepository.findByIdAndIsDeletedFalse(20L)).thenReturn(Optional.of(mockChecklist));
        when(taskRepository.findById(10L)).thenReturn(Optional.of(mockTask));
        when(checklistItemRepository.findByChecklistIdAndIsDeletedFalse(20L)).thenReturn(List.of(mockItem));
        when(checklistRepository.findByTaskIdAndIsDeletedFalseOrderByPositionAsc(10L)).thenReturn(new ArrayList<>());

        checklistService.deleteChecklist(20L, 1L);

        assertTrue(mockChecklist.isDeleted());
        assertTrue(mockItem.isDeleted());
        verify(checklistRepository).save(mockChecklist);
        verify(checklistItemRepository).save(mockItem);
        verify(eventPublisher).publishEvent(any(ChecklistDeletedEvent.class));
    }

    @Test
    void addItem_Success() {
        CreateChecklistItemRequest request = new CreateChecklistItemRequest("New Item", "Description");
        when(checklistRepository.findByIdAndIsDeletedFalse(20L)).thenReturn(Optional.of(mockChecklist));
        when(taskRepository.findById(10L)).thenReturn(Optional.of(mockTask));
        when(checklistItemRepository.findByChecklistIdAndIsDeletedFalseOrderByPositionAsc(20L)).thenReturn(new ArrayList<>());
        when(checklistItemRepository.save(any(ChecklistItem.class))).thenAnswer(inv -> {
            ChecklistItem item = inv.getArgument(0);
            item.setId(30L);
            return item;
        });

        ChecklistItemResponse expectedResponse = new ChecklistItemResponse();
        expectedResponse.setId(30L);
        expectedResponse.setTitle("New Item");
        when(checklistMapper.toResponse(any(ChecklistItem.class))).thenReturn(expectedResponse);

        ChecklistItemResponse response = checklistService.addItem(20L, request, 1L);

        assertNotNull(response);
        assertEquals(30L, response.getId());
        verify(checklistItemRepository).save(any(ChecklistItem.class));
        verify(eventPublisher).publishEvent(any(ChecklistItemAddedEvent.class));
    }

    @Test
    void completeItem_Success() {
        when(checklistItemRepository.findByIdAndIsDeletedFalse(30L)).thenReturn(Optional.of(mockItem));
        when(checklistRepository.findByIdAndIsDeletedFalse(20L)).thenReturn(Optional.of(mockChecklist));
        when(taskRepository.findById(10L)).thenReturn(Optional.of(mockTask));
        when(checklistItemRepository.save(any(ChecklistItem.class))).thenReturn(mockItem);

        ChecklistItemResponse expectedResponse = new ChecklistItemResponse();
        expectedResponse.setId(30L);
        expectedResponse.setCompleted(true);
        when(checklistMapper.toResponse(any(ChecklistItem.class))).thenReturn(expectedResponse);

        ChecklistItemResponse response = checklistService.completeItem(30L, true, 1L);

        assertNotNull(response);
        assertTrue(response.getCompleted());
        assertTrue(mockItem.getCompleted());
        assertNotNull(mockItem.getCompletedBy());
        assertNotNull(mockItem.getCompletedAt());
        verify(eventPublisher).publishEvent(any(ChecklistItemCompletedEvent.class));
    }

    @Test
    void reorderItems_Success() {
        ChecklistItem item1 = new ChecklistItem();
        item1.setId(31L);
        item1.setChecklistId(20L);
        item1.setPosition(0);

        ChecklistItem item2 = new ChecklistItem();
        item2.setId(32L);
        item2.setChecklistId(20L);
        item2.setPosition(1);

        List<MoveChecklistItemRequest> request = List.of(
                new MoveChecklistItemRequest(31L, 1),
                new MoveChecklistItemRequest(32L, 0)
        );

        when(checklistItemRepository.findByIdAndIsDeletedFalse(31L)).thenReturn(Optional.of(item1));
        when(checklistRepository.findByIdAndIsDeletedFalse(20L)).thenReturn(Optional.of(mockChecklist));
        when(taskRepository.findById(10L)).thenReturn(Optional.of(mockTask));
        when(checklistItemRepository.findByChecklistIdAndIsDeletedFalseOrderByPositionAsc(20L)).thenReturn(List.of(item1, item2));

        checklistService.reorderItems(request, 1L);

        assertEquals(1, item1.getPosition());
        assertEquals(0, item2.getPosition());
        verify(checklistItemRepository, times(2)).save(any(ChecklistItem.class));
    }

    @Test
    void reorderItems_EmptyChecklist() {
        List<MoveChecklistItemRequest> request = List.of(new MoveChecklistItemRequest(31L, 0));
        when(checklistItemRepository.findByIdAndIsDeletedFalse(31L)).thenReturn(Optional.of(mockItem));
        when(checklistRepository.findByIdAndIsDeletedFalse(20L)).thenReturn(Optional.of(mockChecklist));
        when(taskRepository.findById(10L)).thenReturn(Optional.of(mockTask));
        when(checklistItemRepository.findByChecklistIdAndIsDeletedFalseOrderByPositionAsc(20L)).thenReturn(Collections.emptyList());

        assertThrows(BusinessRuleException.class, () -> checklistService.reorderItems(request, 1L));
    }

    @Test
    void reorderItems_DuplicatePositions() {
        List<MoveChecklistItemRequest> request = List.of(
                new MoveChecklistItemRequest(31L, 0),
                new MoveChecklistItemRequest(32L, 0)
        );
        when(checklistItemRepository.findByIdAndIsDeletedFalse(31L)).thenReturn(Optional.of(mockItem));
        when(checklistRepository.findByIdAndIsDeletedFalse(20L)).thenReturn(Optional.of(mockChecklist));
        when(taskRepository.findById(10L)).thenReturn(Optional.of(mockTask));

        assertThrows(BusinessRuleException.class, () -> checklistService.reorderItems(request, 1L));
    }
}
