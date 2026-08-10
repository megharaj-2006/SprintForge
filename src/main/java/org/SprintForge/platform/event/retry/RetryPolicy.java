package org.SprintForge.platform.event.retry;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RetryPolicy {

    public enum BackoffType {
        IMMEDIATE,
        FIXED,
        EXPONENTIAL
    }

    @Builder.Default
    private int maxAttempts = 3;

    @Builder.Default
    private long initialDelayMs = 500;

    @Builder.Default
    private double multiplier = 2.0;

    @Builder.Default
    private BackoffType backoffType = BackoffType.EXPONENTIAL;

    public long calculateDelayMs(int attempt) {
        return switch (backoffType) {
            case IMMEDIATE -> 0;
            case FIXED -> initialDelayMs;
            case EXPONENTIAL -> (long) (initialDelayMs * Math.pow(multiplier, Math.max(0, attempt - 1)));
        };
    }
}
