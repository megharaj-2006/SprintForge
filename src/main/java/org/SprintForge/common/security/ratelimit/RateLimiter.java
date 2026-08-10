package org.SprintForge.common.security.ratelimit;

public interface RateLimiter {
    boolean isAllowed(String clientKey, String actionKey, int maxRequestsPerWindow, long windowMillis);
}
