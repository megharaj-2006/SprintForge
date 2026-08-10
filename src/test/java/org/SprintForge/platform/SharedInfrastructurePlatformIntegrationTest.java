package org.SprintForge.platform;

import org.SprintForge.platform.analytics.dto.AnalyticsSnapshotResponse;
import org.SprintForge.platform.analytics.dto.AnalyticsTrendResponse;
import org.SprintForge.platform.analytics.service.AnalyticsPlatformService;
import org.SprintForge.platform.audit.dto.AuditRecordResponse;
import org.SprintForge.platform.audit.dto.CreateAuditRequest;
import org.SprintForge.platform.audit.service.AuditService;
import org.SprintForge.platform.cache.dto.CacheStatisticsResponse;
import org.SprintForge.platform.cache.service.CacheService;
import org.SprintForge.platform.dev.service.DeveloperUtilityService;
import org.SprintForge.platform.featureflag.dto.FeatureFlagResponse;
import org.SprintForge.platform.featureflag.service.FeatureFlagService;
import org.SprintForge.platform.monitoring.dto.HealthCheckResponse;
import org.SprintForge.platform.monitoring.service.HealthService;
import org.SprintForge.platform.reporting.dto.GenerateReportRequest;
import org.SprintForge.platform.reporting.dto.ReportResponse;
import org.SprintForge.platform.reporting.service.ReportPlatformService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class SharedInfrastructurePlatformIntegrationTest {

    @Autowired
    private AuditService auditService;

    @Autowired
    private AnalyticsPlatformService analyticsService;

    @Autowired
    private ReportPlatformService reportService;

    @Autowired
    private CacheService cacheService;

    @Autowired
    private FeatureFlagService featureFlagService;

    @Autowired
    private HealthService healthService;

    @Autowired
    private DeveloperUtilityService developerUtilityService;

    @Test
    public void testAuditPlatform() {
        CreateAuditRequest req = CreateAuditRequest.builder()
                .workspaceId("ws-999")
                .projectId("proj-111")
                .entityType("TASK")
                .entityId("task-55")
                .action("STATUS_CHANGED")
                .actorId("user-777")
                .oldValue("IN_PROGRESS")
                .newValue("DONE")
                .ipAddress("127.0.0.1")
                .build();

        AuditRecordResponse record = auditService.recordAudit(req);
        assertNotNull(record.getId());
        assertEquals("STATUS_CHANGED", record.getAction());

        List<AuditRecordResponse> entityAudits = auditService.getAuditByEntity("TASK", "task-55", 0, 10);
        assertFalse(entityAudits.isEmpty());

        List<AuditRecordResponse> userAudits = auditService.getAuditByUser("user-777", 0, 10);
        assertFalse(userAudits.isEmpty());
    }

    @Test
    public void testAnalyticsPlatform() {
        AnalyticsSnapshotResponse snapshot = analyticsService.captureSnapshot("ws-999", "proj-111", "TASK_METRICS", "{ totalTasks: 42, completedTasks: 30 }");
        assertNotNull(snapshot.getId());
        assertEquals("TASK_METRICS", snapshot.getSnapshotType());

        List<AnalyticsSnapshotResponse> snapshots = analyticsService.getSnapshots("ws-999", "TASK_METRICS", 0, 10);
        assertFalse(snapshots.isEmpty());

        AnalyticsTrendResponse trend = analyticsService.calculateTrend("Velocity", 30.0, 20.0);
        assertEquals("UP", trend.getTrendDirection());
        assertEquals(50.0, trend.getPercentageChange());
    }

    @Test
    public void testReportingEngine() {
        GenerateReportRequest req = GenerateReportRequest.builder()
                .name("Quarterly Sprint Velocity Report")
                .reportType("PORTFOLIO")
                .filters("{ sprintId: 10 }")
                .generatedBy("user-777")
                .format("PDF")
                .build();

        ReportResponse report = reportService.generateReport(req);
        assertNotNull(report.getId());
        assertEquals("PDF", report.getFormat());
        assertTrue(report.getDownloadUrl().endsWith(".pdf"));

        ReportResponse fetched = reportService.getReport(report.getId());
        assertEquals("Quarterly Sprint Velocity Report", fetched.getName());
    }

    @Test
    public void testCachePlatform() {
        cacheService.put("testKey", "WORKSPACE", "testValue", 3600);
        Object cached = cacheService.get("testKey");
        assertEquals("testValue", cached);

        CacheStatisticsResponse stats = cacheService.getStatistics();
        assertNotNull(stats);
        assertTrue(stats.getCacheHits() > 0);

        cacheService.clear("WORKSPACE");
    }

    @Test
    public void testFeatureFlags() {
        FeatureFlagResponse flag = featureFlagService.createOrUpdateFlag("AI_AUTO_ASSIGN", "Automated task assignment via AI", true, "GLOBAL", null, 100);
        assertNotNull(flag.getId());
        assertTrue(flag.isEnabled());

        boolean isEnabled = featureFlagService.isEnabled("AI_AUTO_ASSIGN");
        assertTrue(isEnabled);

        FeatureFlagResponse toggled = featureFlagService.toggleFlag(flag.getId(), false);
        assertFalse(toggled.isEnabled());
    }

    @Test
    public void testHealthAndMonitoring() {
        HealthCheckResponse health = healthService.checkHealth();
        assertNotNull(health);
        assertEquals("UP", health.getStatus());
        assertTrue(health.getComponents().containsKey("database"));
    }

    @Test
    public void testDeveloperUtilities() {
        Map<String, Object> reindexRes = developerUtilityService.reindexSearch();
        assertEquals("SUCCESS", reindexRes.get("status"));

        Map<String, Object> cacheClearRes = developerUtilityService.clearCache();
        assertEquals("SUCCESS", cacheClearRes.get("status"));

        Map<String, Object> seedRes = developerUtilityService.seedDemoWorkspace();
        assertEquals("SUCCESS", seedRes.get("status"));
    }
}
