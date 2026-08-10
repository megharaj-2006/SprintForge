package org.SprintForge.modules.integration.repository;

import org.SprintForge.modules.integration.entity.CredentialReference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CredentialReferenceRepository extends JpaRepository<CredentialReference, Long> {
    Optional<CredentialReference> findByCredentialKey(String credentialKey);
}
