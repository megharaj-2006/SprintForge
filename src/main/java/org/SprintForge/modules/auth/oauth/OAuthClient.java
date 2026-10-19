package org.SprintForge.modules.auth.oauth;

import org.SprintForge.modules.auth.entity.AuthProvider;

public interface OAuthClient {

    AuthProvider getProvider();

    boolean isConfigured();

    String getClientId();

    String getAuthorizeUrl(String redirectUri, String state);

    String getScope();

    OAuthUserInfo exchangeCodeAndFetchProfile(String code, String redirectUri, String codeVerifier);
}
