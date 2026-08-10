package org.SprintForge.platform;

import org.SprintForge.platform.event.event.TaskCreatedEvent;
import org.SprintForge.platform.event.publisher.PlatformEventPublisher;
import org.SprintForge.platform.search.dto.*;
import org.SprintForge.platform.search.service.*;
import org.SprintForge.platform.storage.dto.*;
import org.SprintForge.platform.storage.service.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class SearchAndStoragePlatformIntegrationTest {

    @Autowired
    private PlatformEventPublisher platformEventPublisher;

    @Autowired
    private GlobalSearchService globalSearchService;

    @Autowired
    private SearchIndexService searchIndexService;

    @Autowired
    private AdvancedSearchService advancedSearchService;

    @Autowired
    private SearchDashboardService searchDashboardService;

    @Autowired
    private UploadService uploadService;

    @Autowired
    private DownloadService downloadService;

    @Autowired
    private StorageService storageService;

    @Autowired
    private FileVersionService fileVersionService;

    @Autowired
    private QuotaService quotaService;

    @Autowired
    private StorageDashboardService storageDashboardService;

    @Test
    public void testGlobalSearchAndEventIndexing() {
        // Explicitly index an entity and publish event
        TaskCreatedEvent event = TaskCreatedEvent.create("task-999", "Refactor Search Engine", "Build elastic specification search", "user-dev", "ws-100", "proj-200", "user-dev");
        event.setEventId("evt-search-001");
        platformEventPublisher.publish(event);

        // Search global
        GlobalSearchRequest request = GlobalSearchRequest.builder()
                .query("Search Engine")
                .workspaceId("ws-100")
                .userId("user-dev")
                .page(0)
                .size(10)
                .build();

        List<SearchResultResponse> results = globalSearchService.search(request);
        assertFalse(results.isEmpty());
        SearchResultResponse item = results.get(0);
        assertEquals("TASK", item.getEntityType());
        assertEquals("task-999", item.getEntityId());

        // Suggestions
        SearchSuggestionResponse suggestions = globalSearchService.getSuggestions(SearchSuggestionRequest.builder()
                .prefix("Refactor")
                .limit(5)
                .build());
        assertFalse(suggestions.getSuggestions().isEmpty());

        // History
        List<SearchHistoryResponse> history = globalSearchService.getUserHistory("user-dev", 5);
        assertFalse(history.isEmpty());
        assertEquals("Search Engine", history.get(0).getQueryText());
    }

    @Test
    public void testAdvancedSearchAndSavedSearch() {
        searchIndexService.indexEntity("PROJECT", "proj-300", "ws-100", "proj-300", "SprintForge Mobile App", "React Native client app", "mobile react native");

        AdvancedSearchRequest request = AdvancedSearchRequest.builder()
                .query("Mobile")
                .workspaceId("ws-100")
                .matchMode("AND")
                .filters(List.of(SearchFilterRule.builder().field("entityType").operator("EQUALS").value("PROJECT").build()))
                .build();

        List<SearchResultResponse> results = advancedSearchService.advancedSearch(request);
        assertFalse(results.isEmpty());
        assertEquals("PROJECT", results.get(0).getEntityType());

        // Save Search
        SavedSearchResponse saved = advancedSearchService.saveSearch(SaveSearchRequest.builder()
                .name("Mobile Projects")
                .queryText("Mobile")
                .workspaceId("ws-100")
                .userId("user-dev")
                .isShared(true)
                .build());

        assertNotNull(saved.getId());
        assertEquals("Mobile Projects", saved.getName());

        List<SavedSearchResponse> userSaved = advancedSearchService.getSavedSearches("user-dev", "ws-100");
        assertFalse(userSaved.isEmpty());
    }

    @Test
    public void testFileStorageLifecycle() {
        byte[] fileBytes = "Hello SprintForge Storage Platform!".getBytes();
        FileUploadRequest uploadReq = FileUploadRequest.builder()
                .workspaceId("ws-100")
                .projectId("proj-200")
                .ownerId("user-dev")
                .originalName("architecture_spec.txt")
                .mimeType("text/plain")
                .content(fileBytes)
                .build();

        FileUploadResponse uploadRes = uploadService.uploadFile(uploadReq);
        assertNotNull(uploadRes.getId());
        assertEquals("architecture_spec.txt", uploadRes.getOriginalName());
        assertEquals(fileBytes.length, uploadRes.getSizeBytes());

        // Download
        byte[] downloaded = downloadService.downloadFile(uploadRes.getId(), "user-dev");
        assertArrayEquals(fileBytes, downloaded);

        // Version History
        List<FileVersionResponse> versions = fileVersionService.getFileVersions(uploadRes.getId());
        assertFalse(versions.isEmpty());
        assertEquals(1, versions.get(0).getVersionNumber());

        // Delete & Restore
        storageService.deleteFile(uploadRes.getId());
        StoredFileResponse deletedFile = storageService.getFileDetails(uploadRes.getId());
        assertTrue(deletedFile.isDeleted());

        StoredFileResponse restoredFile = storageService.restoreFile(uploadRes.getId());
        assertFalse(restoredFile.isDeleted());
    }

    @Test
    public void testStorageQuotaAndDashboards() {
        FileQuotaResponse quota = quotaService.checkQuota("ws-100");
        assertNotNull(quota);
        assertFalse(quota.isQuotaExceeded());

        SearchDashboardResponse searchDash = searchDashboardService.getSearchMetrics();
        assertNotNull(searchDash);

        StorageDashboardResponse storageDash = storageDashboardService.getStorageMetrics();
        assertNotNull(storageDash);
    }
}
