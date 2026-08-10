package org.SprintForge.common.security.ratelimit;

import org.springframework.stereotype.Component;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class InMemoryRateLimiter implements RateLimiter {

    private final Map<String, Deque<Long>> requestLogs = new ConcurrentHashMap<>();

    @Override
    public boolean isAllowed(String clientKey, String actionKey, int maxRequestsPerWindow, long windowMillis) {
        String compositeKey = clientKey + ":" + actionKey;
        long now = System.currentTimeMillis();
        long cutoff = now - windowMillis;

        Deque<Long> timestamps = requestLogs.computeIfAbsent(compositeKey, k -> new ArrayDeque<>());
        synchronized (timestamps) {
            while (!timestamps.isEmpty() && timestamps.peekFirst() < cutoff) {
                timestamps.pollFirst();
            }
            if (timestamps.size() < maxRequestsPerWindow) {
                timestamps.addLast(now);
                return true;
            }
            return false;
        }
    }
}
