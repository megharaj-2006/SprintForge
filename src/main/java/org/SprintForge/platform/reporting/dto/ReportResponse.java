package org.SprintForge.platform.reporting.dto;

import java.time.Instant;

public class ReportResponse {
    private Long id;
    private String name;
    private String reportType;
    private String filters;
    private String generatedBy;
    private Instant generatedAt;
    private String format;
    private String downloadUrl;
    private String status;

    public ReportResponse() {}

    public ReportResponse(Long id, String name, String reportType, String filters, String generatedBy, Instant generatedAt, String format, String downloadUrl, String status) {
        this.id = id;
        this.name = name;
        this.reportType = reportType;
        this.filters = filters;
        this.generatedBy = generatedBy;
        this.generatedAt = generatedAt;
        this.format = format;
        this.downloadUrl = downloadUrl;
        this.status = status;
    }

    public static ReportResponseBuilder builder() {
        return new ReportResponseBuilder();
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

    public static class ReportResponseBuilder {
        private Long id;
        private String name;
        private String reportType;
        private String filters;
        private String generatedBy;
        private Instant generatedAt;
        private String format;
        private String downloadUrl;
        private String status;

        public ReportResponseBuilder id(Long id) { this.id = id; return this; }
        public ReportResponseBuilder name(String name) { this.name = name; return this; }
        public ReportResponseBuilder reportType(String reportType) { this.reportType = reportType; return this; }
        public ReportResponseBuilder filters(String filters) { this.filters = filters; return this; }
        public ReportResponseBuilder generatedBy(String generatedBy) { this.generatedBy = generatedBy; return this; }
        public ReportResponseBuilder generatedAt(Instant generatedAt) { this.generatedAt = generatedAt; return this; }
        public ReportResponseBuilder format(String format) { this.format = format; return this; }
        public ReportResponseBuilder downloadUrl(String downloadUrl) { this.downloadUrl = downloadUrl; return this; }
        public ReportResponseBuilder status(String status) { this.status = status; return this; }

        public ReportResponse build() {
            return new ReportResponse(id, name, reportType, filters, generatedBy, generatedAt, format, downloadUrl, status);
        }
    }
}
