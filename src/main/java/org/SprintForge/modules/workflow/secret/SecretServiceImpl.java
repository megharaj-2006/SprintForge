package org.SprintForge.modules.workflow.secret;

import lombok.RequiredArgsConstructor;
import org.SprintForge.common.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SecretServiceImpl implements SecretService {

    private final WorkflowSecretRepository repository;
    private final EncryptionService encryptionService;

    @Override
    @Transactional
    public WorkflowSecret createSecret(Long workspaceId, String name, String rawValue, String scope) {
        WorkflowSecret secret = new WorkflowSecret();
        secret.setWorkspaceId(workspaceId);
        secret.setName(name);
        secret.setValueEncrypted(encryptionService.encrypt(rawValue));
        secret.setScope(scope != null ? scope : "WORKSPACE");
        return repository.save(secret);
    }

    @Override
    @Transactional(readOnly = true)
    public List<WorkflowSecret> getSecrets(Long workspaceId) {
        List<WorkflowSecret> secrets = repository.findByWorkspaceId(workspaceId);
        // Mask encrypted values for security in response
        secrets.forEach(s -> s.setValueEncrypted("********"));
        return secrets;
    }

    @Override
    @Transactional(readOnly = true)
    public String getDecryptedSecret(Long workspaceId, String name) {
        WorkflowSecret secret = repository.findByWorkspaceIdAndName(workspaceId, name)
                .orElseThrow(() -> new ResourceNotFoundException("Secret not found: " + name));
        return encryptionService.decrypt(secret.getValueEncrypted());
    }

    @Override
    @Transactional
    public void deleteSecret(Long secretId) {
        repository.deleteById(secretId);
    }
}
