package org.SprintForge.platform.scheduler.executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.SprintForge.platform.scheduler.domain.JobType;
import org.SprintForge.platform.scheduler.domain.PlatformJob;
import org.SprintForge.platform.search.service.SearchRebuildService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("platformSearchReindexJobExecutor")
public class SearchReindexJobExecutor implements JobExecutor {

    private static final Logger log = LoggerFactory.getLogger(SearchReindexJobExecutor.class);
    private final SearchRebuildService searchRebuildService;

    @Autowired
    public SearchReindexJobExecutor(SearchRebuildService searchRebuildService) {
        this.searchRebuildService = searchRebuildService;
    }

    @Override
    public JobType getSupportedJobType() {
        return JobType.SEARCH_REINDEX;
    }

    @Override
    public void execute(PlatformJob job) throws Exception {
        log.info("Executing search reindex job: id={}, payload={}", job.getId(), job.getPayload());
        if (job.getPayload() != null && !job.getPayload().isBlank()) {
            searchRebuildService.rebuildEntityIndex(job.getPayload());
        } else {
            searchRebuildService.rebuildFullIndex();
        }
        job.setResult("Successfully rebuilt search index");
    }
}
