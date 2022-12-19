package com.example.oauth2.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author songbo
 * @version 1.0
 * @date 2022/12/13 19:27
 */
@Slf4j
@Service
public class OAuth2TokenService {

    @Value("${security.oauth2.client.client-id}")
    private String clientId;
    @Value("${security.oauth2.client.client-secret}")
    private String clientSecret;
    @Value("${security.oauth2.redirect_uri}")
    private String redirectUri;
    @Value("${security.oauth2.authorize-uri}")
    private String authorizeUri;

    public String getAuthorizationUrl() {
        String authorizationUrl = authorizeUri + "?client_id=" + clientId
                + "&redirect_uri=" + redirectUri
                + "&scope=read_userinfo"
                + "&response_type=token"
                + "&state=" + System.currentTimeMillis();
        log.info("授权地址:{}", authorizationUrl);
        return authorizationUrl;
    }
}
