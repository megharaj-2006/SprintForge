package org.SprintForge.platform.storage.dto;

import java.util.List;

public class StorageDashboardResponse {
    private long totalFiles;
    private long totalStorageUsedBytes;
    private long duplicateFilesCount;
    private double uploadSuccessRate;
    private List<StoredFileResponse> largestFiles;

    public StorageDashboardResponse() {}

    public StorageDashboardResponse(long totalFiles, long totalStorageUsedBytes, long duplicateFilesCount, double uploadSuccessRate, List<StoredFileResponse> largestFiles) {
        this.totalFiles = totalFiles;
        this.totalStorageUsedBytes = totalStorageUsedBytes;
        this.duplicateFilesCount = duplicateFilesCount;
        this.uploadSuccessRate = uploadSuccessRate;
        this.largestFiles = largestFiles;
    }

    public static StorageDashboardResponseBuilder builder() {
        return new StorageDashboardResponseBuilder();
    }

    public long getTotalFiles() { return totalFiles; }
    public void setTotalFiles(long totalFiles) { this.totalFiles = totalFiles; }

    public long getTotalStorageUsedBytes() { return totalStorageUsedBytes; }
    public void setTotalStorageUsedBytes(long totalStorageUsedBytes) { this.totalStorageUsedBytes = totalStorageUsedBytes; }

    public long getDuplicateFilesCount() { return duplicateFilesCount; }
    public void setDuplicateFilesCount(long duplicateFilesCount) { this.duplicateFilesCount = duplicateFilesCount; }

    public double getUploadSuccessRate() { return uploadSuccessRate; }
    public void setUploadSuccessRate(double uploadSuccessRate) { this.uploadSuccessRate = uploadSuccessRate; }

    public List<StoredFileResponse> getLargestFiles() { return largestFiles; }
    public void setLargestFiles(List<StoredFileResponse> largestFiles) { this.largestFiles = largestFiles; }

    public static class StorageDashboardResponseBuilder {
        private long totalFiles;
        private long totalStorageUsedBytes;
        private long duplicateFilesCount;
        private double uploadSuccessRate;
        private List<StoredFileResponse> largestFiles;

        public StorageDashboardResponseBuilder totalFiles(long totalFiles) { this.totalFiles = totalFiles; return this; }
        public StorageDashboardResponseBuilder totalStorageUsedBytes(long totalStorageUsedBytes) { this.totalStorageUsedBytes = totalStorageUsedBytes; return this; }
        public StorageDashboardResponseBuilder duplicateFilesCount(long duplicateFilesCount) { this.duplicateFilesCount = duplicateFilesCount; return this; }
        public StorageDashboardResponseBuilder uploadSuccessRate(double uploadSuccessRate) { this.uploadSuccessRate = uploadSuccessRate; return this; }
        public StorageDashboardResponseBuilder largestFiles(List<StoredFileResponse> largestFiles) { this.largestFiles = largestFiles; return this; }

        public StorageDashboardResponse build() {
            return new StorageDashboardResponse(totalFiles, totalStorageUsedBytes, duplicateFilesCount, uploadSuccessRate, largestFiles);
        }
    }
}
