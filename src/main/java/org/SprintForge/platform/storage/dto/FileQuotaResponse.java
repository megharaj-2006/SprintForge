package org.SprintForge.platform.storage.dto;

public class FileQuotaResponse {
    private String workspaceId;
    private long usedSpaceBytes;
    private long maxQuotaBytes;
    private double usagePercentage;
    private boolean isQuotaExceeded;

    public FileQuotaResponse() {}

    public FileQuotaResponse(String workspaceId, long usedSpaceBytes, long maxQuotaBytes, double usagePercentage, boolean isQuotaExceeded) {
        this.workspaceId = workspaceId;
        this.usedSpaceBytes = usedSpaceBytes;
        this.maxQuotaBytes = maxQuotaBytes;
        this.usagePercentage = usagePercentage;
        this.isQuotaExceeded = isQuotaExceeded;
    }

    public static FileQuotaResponseBuilder builder() {
        return new FileQuotaResponseBuilder();
    }

    public String getWorkspaceId() { return workspaceId; }
    public void setWorkspaceId(String workspaceId) { this.workspaceId = workspaceId; }

    public long getUsedSpaceBytes() { return usedSpaceBytes; }
    public void setUsedSpaceBytes(long usedSpaceBytes) { this.usedSpaceBytes = usedSpaceBytes; }

    public long getMaxQuotaBytes() { return maxQuotaBytes; }
    public void setMaxQuotaBytes(long maxQuotaBytes) { this.maxQuotaBytes = maxQuotaBytes; }

    public double getUsagePercentage() { return usagePercentage; }
    public void setUsagePercentage(double usagePercentage) { this.usagePercentage = usagePercentage; }

    public boolean isQuotaExceeded() { return isQuotaExceeded; }
    public void setQuotaExceeded(boolean quotaExceeded) { isQuotaExceeded = quotaExceeded; }

    public static class FileQuotaResponseBuilder {
        private String workspaceId;
        private long usedSpaceBytes;
        private long maxQuotaBytes;
        private double usagePercentage;
        private boolean isQuotaExceeded;

        public FileQuotaResponseBuilder workspaceId(String workspaceId) { this.workspaceId = workspaceId; return this; }
        public FileQuotaResponseBuilder usedSpaceBytes(long usedSpaceBytes) { this.usedSpaceBytes = usedSpaceBytes; return this; }
        public FileQuotaResponseBuilder maxQuotaBytes(long maxQuotaBytes) { this.maxQuotaBytes = maxQuotaBytes; return this; }
        public FileQuotaResponseBuilder usagePercentage(double usagePercentage) { this.usagePercentage = usagePercentage; return this; }
        public FileQuotaResponseBuilder isQuotaExceeded(boolean isQuotaExceeded) { this.isQuotaExceeded = isQuotaExceeded; return this; }

        public FileQuotaResponse build() {
            return new FileQuotaResponse(workspaceId, usedSpaceBytes, maxQuotaBytes, usagePercentage, isQuotaExceeded);
        }
    }
}
