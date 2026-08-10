package org.SprintForge.platform.reporting.dto;

public class GenerateReportRequest {
    private String name;
    private String reportType;
    private String filters;
    private String generatedBy;
    private String format;

    public GenerateReportRequest() {}

    public GenerateReportRequest(String name, String reportType, String filters, String generatedBy, String format) {
        this.name = name;
        this.reportType = reportType;
        this.filters = filters;
        this.generatedBy = generatedBy;
        this.format = format;
    }

    public static GenerateReportRequestBuilder builder() {
        return new GenerateReportRequestBuilder();
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getReportType() { return reportType; }
    public void setReportType(String reportType) { this.reportType = reportType; }

    public String getFilters() { return filters; }
    public void setFilters(String filters) { this.filters = filters; }

    public String getGeneratedBy() { return generatedBy; }
    public void setGeneratedBy(String generatedBy) { this.generatedBy = generatedBy; }

    public String getFormat() { return format; }
    public void setFormat(String format) { this.format = format; }

    public static class GenerateReportRequestBuilder {
        private String name;
        private String reportType;
        private String filters;
        private String generatedBy;
        private String format;

        public GenerateReportRequestBuilder name(String name) { this.name = name; return this; }
        public GenerateReportRequestBuilder reportType(String reportType) { this.reportType = reportType; return this; }
        public GenerateReportRequestBuilder filters(String filters) { this.filters = filters; return this; }
        public GenerateReportRequestBuilder generatedBy(String generatedBy) { this.generatedBy = generatedBy; return this; }
        public GenerateReportRequestBuilder format(String format) { this.format = format; return this; }

        public GenerateReportRequest build() {
            return new GenerateReportRequest(name, reportType, filters, generatedBy, format);
        }
    }
}
