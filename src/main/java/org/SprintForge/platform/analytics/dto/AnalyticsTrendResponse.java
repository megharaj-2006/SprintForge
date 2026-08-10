package org.SprintForge.platform.analytics.dto;

import java.util.Map;

public class AnalyticsTrendResponse {
    private String metricName;
    private double currentValue;
    private double previousValue;
    private double percentageChange;
    private String trendDirection;
    private Map<String, Object> details;

    public AnalyticsTrendResponse() {}

    public AnalyticsTrendResponse(String metricName, double currentValue, double previousValue, double percentageChange, String trendDirection, Map<String, Object> details) {
        this.metricName = metricName;
        this.currentValue = currentValue;
        this.previousValue = previousValue;
        this.percentageChange = percentageChange;
        this.trendDirection = trendDirection;
        this.details = details;
    }

    public static AnalyticsTrendResponseBuilder builder() {
        return new AnalyticsTrendResponseBuilder();
    }

    public String getMetricName() { return metricName; }
    public void setMetricName(String metricName) { this.metricName = metricName; }

    public double getCurrentValue() { return currentValue; }
    public void setCurrentValue(double currentValue) { this.currentValue = currentValue; }

    public double getPreviousValue() { return previousValue; }
    public void setPreviousValue(double previousValue) { this.previousValue = previousValue; }

    public double getPercentageChange() { return percentageChange; }
    public void setPercentageChange(double percentageChange) { this.percentageChange = percentageChange; }

    public String getTrendDirection() { return trendDirection; }
    public void setTrendDirection(String trendDirection) { this.trendDirection = trendDirection; }

    public Map<String, Object> getDetails() { return details; }
    public void setDetails(Map<String, Object> details) { this.details = details; }

    public static class AnalyticsTrendResponseBuilder {
        private String metricName;
        private double currentValue;
        private double previousValue;
        private double percentageChange;
        private String trendDirection;
        private Map<String, Object> details;

        public AnalyticsTrendResponseBuilder metricName(String metricName) { this.metricName = metricName; return this; }
        public AnalyticsTrendResponseBuilder currentValue(double currentValue) { this.currentValue = currentValue; return this; }
        public AnalyticsTrendResponseBuilder previousValue(double previousValue) { this.previousValue = previousValue; return this; }
        public AnalyticsTrendResponseBuilder percentageChange(double percentageChange) { this.percentageChange = percentageChange; return this; }
        public AnalyticsTrendResponseBuilder trendDirection(String trendDirection) { this.trendDirection = trendDirection; return this; }
        public AnalyticsTrendResponseBuilder details(Map<String, Object> details) { this.details = details; return this; }

        public AnalyticsTrendResponse build() {
            return new AnalyticsTrendResponse(metricName, currentValue, previousValue, percentageChange, trendDirection, details);
        }
    }
}
