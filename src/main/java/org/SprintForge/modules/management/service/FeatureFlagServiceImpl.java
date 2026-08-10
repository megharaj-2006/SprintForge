package org.SprintForge.modules.management.service;

import lombok.RequiredArgsConstructor;
import org.SprintForge.common.exception.ResourceNotFoundException;
import org.SprintForge.modules.management.entity.FeatureFlagEntity;
import org.SprintForge.modules.management.repository.MgmtFeatureFlagRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FeatureFlagServiceImpl implements FeatureFlagService {

    private final MgmtFeatureFlagRepository featureFlagRepository;

    @Override
    @Transactional(readOnly = true)
    public List<FeatureFlagEntity> getFeatureFlags() {
        return featureFlagRepository.findAll();
    }

    @Override
    @Transactional
    public FeatureFlagEntity updateFeatureFlag(String flagKey, boolean enabled, Integer rolloutPercentage) {
        FeatureFlagEntity flag = featureFlagRepository.findByFlagKey(flagKey)
                .orElseThrow(() -> new ResourceNotFoundException("FeatureFlag not found for key: " + flagKey));
        flag.setEnabled(enabled);
        if (rolloutPercentage != null) flag.setRolloutPercentage(rolloutPercentage);

        return featureFlagRepository.save(flag);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isFeatureEnabled(String flagKey) {
        return featureFlagRepository.findByFlagKey(flagKey)
                .map(FeatureFlagEntity::getEnabled)
                .orElse(false);
    }
}
