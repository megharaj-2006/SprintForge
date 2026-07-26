package org.SprintForge.modules.auth.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String generateToken(UserDetails userDetails);
    String generateToken(String username);
    String generateToken(String username, String jti);
    String getUsernameFromToken(String token);
    String getJwtIdFromToken(String token);
    boolean validateToken(String token, UserDetails userDetails);
    boolean validateToken(String token);
}
