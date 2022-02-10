package com.cyecize.demo.configuration.security.authloader;

public interface AuthenticationLoader {
    boolean loadAuthentication(String accessToken);
}
