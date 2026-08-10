package org.SprintForge.modules.integration.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.SprintForge.common.entity.BaseEntity;
import org.SprintForge.modules.integration.entity.enums.CredentialType;

import java.time.LocalDateTime;

@Entity
@Table(name = "credential_references")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CredentialReference extends BaseEntity {

    @Column(name = "credential_key", nullable = false, unique = true)
    private String credentialKey;

    @Enumerated(EnumType.STRING)
    @Column(name = "credential_type", nullable = false)
    private CredentialType credentialType = CredentialType.API_KEY;

    @Column(name = "encrypted_reference", nullable = false, columnDefinition = "TEXT")
    private String encryptedReference;

    @Column(name = "scope", nullable = false)
    private String scope = "WORKSPACE";

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
