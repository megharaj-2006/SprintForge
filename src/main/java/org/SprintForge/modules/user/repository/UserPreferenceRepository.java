package org.SprintForge.modules.user.repository;

import org.SprintForge.modules.user.entity.UserPreference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserPreferenceRepository extends JpaRepository<UserPreference, Long>, JpaSpecificationExecutor<UserPreference> {
    Optional<UserPreference> findByUserId(Long userId);
}