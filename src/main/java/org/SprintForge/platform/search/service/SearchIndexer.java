package org.SprintForge.platform.search.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.SprintForge.platform.event.event.BaseDomainEvent;
import org.SprintForge.platform.event.event.TaskCreatedEvent;
import org.SprintForge.platform.event.event.TaskUpdatedEvent;
import org.SprintForge.platform.event.event.ProjectArchivedEvent;
import org.springframework.stereotype.Service;

@Slf4j
@Service("platformSearchIndexer")
@RequiredArgsConstructor
public class SearchIndexer {

    private final SearchIndexService searchIndexService;

    public void processEvent(BaseDomainEvent event) {
        if (event instanceof TaskCreatedEvent taskCreated) {
            searchIndexService.indexEntity("TASK", taskCreated.getAggregateId(), taskCreated.getWorkspaceId(), taskCreated.getProjectId(), taskCreated.getTitle(), taskCreated.getDescription(), "task assignee:" + taskCreated.getAssigneeId());
        } else if (event instanceof TaskUpdatedEvent taskUpdated) {
            searchIndexService.indexEntity("TASK", taskUpdated.getAggregateId(), taskUpdated.getWorkspaceId(), taskUpdated.getProjectId(), "Task " + taskUpdated.getAggregateId(), "Updated " + taskUpdated.getFieldName() + " to " + taskUpdated.getNewValue(), "task updated " + taskUpdated.getFieldName());
        } else if (event instanceof ProjectArchivedEvent projectArchived) {
            searchIndexService.archiveEntityIndex("PROJECT", projectArchived.getAggregateId());
        } else if (event.getAggregateType() != null && event.getAggregateId() != null) {
            searchIndexService.indexEntity(event.getAggregateType(), event.getAggregateId(), event.getWorkspaceId(), event.getProjectId(), event.getEventType(), "Event triggered by " + event.getTriggeredBy(), event.getCategory() != null ? event.getCategory().name() : "");
        }
    }
}
