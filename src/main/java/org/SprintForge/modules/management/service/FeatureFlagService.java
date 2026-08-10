package org.SprintForge.modules.management.service;

import org.SprintForge.modules.management.entity.FeatureFlagEntity;

import java.util.List;

public interface FeatureFlagService {
    List<FeatureFlagEntity> getFeatureFlags();
    FeatureFlagEntity updateFeatureFlag(String flagKey, boolean enabled, Integer rolloutPercentage);
    boolean isFeatureEnabled(String flagKey);
}
