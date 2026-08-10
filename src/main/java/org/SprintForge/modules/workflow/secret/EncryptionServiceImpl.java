package org.SprintForge.modules.workflow.secret;

import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class EncryptionServiceImpl implements EncryptionService {

    @Override
    public String encrypt(String plainText) {
        if (plainText == null) return null;
        return Base64.getEncoder().encodeToString(plainText.getBytes());
    }

    @Override
    public String decrypt(String cipherText) {
        if (cipherText == null) return null;
        return new String(Base64.getDecoder().decode(cipherText));
    }
}
