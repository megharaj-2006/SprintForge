package org.SprintForge.platform.reporting.domain;

import jakarta.persistence.*;
import java.time.Instant;

@Entity(name = "PlatformReport")
@Table(name = "platform_reports")
public class PlatformReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "report_type", nullable = false)
    private String reportType;

    @Column(name = "filters", columnDefinition = "TEXT")
    private String filters;

    @Column(name = "generated_by", nullable = false)
    private String generatedBy;

    @Column(name = "generated_at", nullable = false)
    private Instant generatedAt = Instant.now();

    @Column(name = "format", nullable = false)
    private String format;

    @Column(name = "download_url")
    private String downloadUrl;

    @Column(name = "status", nullable = false)
    private String status = "COMPLETED";

    public PlatformReport() {}

    public PlatformReport(Long id, String name, String reportType, String filters, String generatedBy, Instant generatedAt, String format, String downloadUrl, String status) {
        this.id = id;
        this.name = name;
        this.reportType = reportType;
        this.filters = filters;
        this.generatedBy = generatedBy;
        this.generatedAt = generatedAt != null ? generatedAt : Instant.now();
        this.format = format;
        this.downloadUrl = downloadUrl;
        this.status = status != null ? status : "COMPLETED";
    }

    public static PlatformReportBuilder builder() {
        return new PlatformReportBuilder();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getReportType() { return reportType; }
    public void setReportType(String reportType) { this.reportType = reportType; }

    public String getFilters() { return filters; }
    public void setFilters(String filters) { this.filters = filters; }

    public String getGeneratedBy() { return generatedBy; }
    public void setGeneratedBy(String generatedBy) { this.generatedBy = generatedBy; }

    public Instant getGeneratedAt() { return generatedAt; }
    public void setGeneratedAt(Instant generatedAt) { this.generatedAt = generatedAt; }

    public String getFormat() { return format; }
    public void setFormat(String format) { this.format = format; }

    public String getDownloadUrl() { return downloadUrl; }
    public void setDownloadUrl(String downloadUrl) { this.downloadUrl = downloadUrl; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public static class PlatformReportBuilder {
        private Long id;
        private String name;
        private String reportType;
        private String filters;
        private String generatedBy;
        private Instant generatedAt = Instant.now();
        private String format;
        private String downloadUrl;
        private String status = "COMPLETED";

        public PlatformReportBuilder id(Long id) { this.id = id; return this; }
        public PlatformReportBuilder name(String name) { this.name = name; return this; }
        public PlatformReportBuilder reportType(String reportType) { this.reportType = reportType; return this; }
        public PlatformReportBuilder filters(String filters) { this.filters = filters; return this; }
        public PlatformReportBuilder generatedBy(String generatedBy) { this.generatedBy = generatedBy; return this; }
        public PlatformReportBuilder generatedAt(Instant generatedAt) { this.generatedAt = generatedAt; return this; }
        public PlatformReportBuilder format(String format) { this.format = format; return this; }
        public PlatformReportBuilder downloadUrl(String downloadUrl) { this.downloadUrl = downloadUrl; return this; }
        public PlatformReportBuilder status(String status) { this.status = status; return this; }

        public PlatformReport build() {
            return new PlatformReport(id, name, reportType, filters, generatedBy, generatedAt, format, downloadUrl, status);
        }
    }
}
