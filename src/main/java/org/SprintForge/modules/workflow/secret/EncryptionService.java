package org.SprintForge.modules.workflow.secret;

public interface EncryptionService {
    String encrypt(String plainText);
    String decrypt(String cipherText);
}
