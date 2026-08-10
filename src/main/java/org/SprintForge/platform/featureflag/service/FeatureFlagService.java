package org.SprintForge.platform.featureflag.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.SprintForge.common.exception.ResourceNotFoundException;
import org.SprintForge.platform.featureflag.domain.FeatureFlag;
import org.SprintForge.platform.featureflag.dto.FeatureFlagResponse;
import org.SprintForge.platform.featureflag.repository.PlatformFeatureFlagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service("platformFeatureFlagService")
public class FeatureFlagService {

    private static final Logger log = LoggerFactory.getLogger(FeatureFlagService.class);
    private final PlatformFeatureFlagRepository featureFlagRepository;

    @Autowired
    public FeatureFlagService(PlatformFeatureFlagRepository featureFlagRepository) {
        this.featureFlagRepository = featureFlagRepository;
    }

    public boolean isEnabled(String flagName) {
        return featureFlagRepository.findByName(flagName)
                .map(FeatureFlag::isEnabled)
                .orElse(false);
    }

    @Transactional
    public FeatureFlagResponse createOrUpdateFlag(String name, String description, boolean enabled, String scope, String workspaceId, int rolloutPercentage) {
        FeatureFlag flag = featureFlagRepository.findByName(name)
                .orElseGet(() -> FeatureFlag.builder().name(name).build());

        flag.setDescription(description);
        flag.setEnabled(enabled);
        flag.setScope(scope != null ? scope : "GLOBAL");
        flag.setWorkspaceId(workspaceId);
        flag.setRolloutPercentage(rolloutPercentage);
        flag.setUpdatedAt(Instant.now());

        FeatureFlag saved = featureFlagRepository.save(flag);
        log.info("Saved feature flag name={} enabled={}", saved.getName(), saved.isEnabled());
        return toFeatureFlagResponse(saved);
    }

    @Transactional
    public FeatureFlagResponse toggleFlag(Long id, boolean enabled) {
        FeatureFlag flag = featureFlagRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("FeatureFlag not found with ID: " + id));

        flag.setEnabled(enabled);
        flag.setUpdatedAt(Instant.now());
        FeatureFlag saved = featureFlagRepository.save(flag);
        return toFeatureFlagResponse(saved);
    }

    public List<FeatureFlagResponse> getAllFlags() {
        return featureFlagRepository.findAll().stream()
                .map(this::toFeatureFlagResponse)
                .collect(Collectors.toList());
    }

    public FeatureFlagResponse toFeatureFlagResponse(FeatureFlag flag) {
        return FeatureFlagResponse.builder()
                .id(flag.getId())
                .name(flag.getName())
                .description(flag.getDescription())
                .enabled(flag.isEnabled())
                .scope(flag.getScope())
                .workspaceId(flag.getWorkspaceId())
                .rolloutPercentage(flag.getRolloutPercentage())
                .createdAt(flag.getCreatedAt())
                .updatedAt(flag.getUpdatedAt())
                .build();
    }
}
