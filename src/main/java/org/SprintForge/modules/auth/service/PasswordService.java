package org.SprintForge.modules.auth.service;

public interface PasswordService {
    String encode(CharSequence rawPassword);
    boolean matches(CharSequence rawPassword, String encodedPassword);
}
