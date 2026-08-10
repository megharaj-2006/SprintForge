package org.SprintForge.modules.workflow;

import org.SprintForge.modules.workflow.secret.EncryptionService;
import org.SprintForge.modules.workflow.secret.EncryptionServiceImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class EncryptionServiceTest {

    @Test
    void testEncryptionAndDecryption() {
        EncryptionService service = new EncryptionServiceImpl();
        String secret = "MySuperSecretKey123!";

        String encrypted = service.encrypt(secret);
        assertNotEquals(secret, encrypted);

        String decrypted = service.decrypt(encrypted);
        assertEquals(secret, decrypted);
    }
}
