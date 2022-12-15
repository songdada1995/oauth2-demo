package com.example.oauth2.service;

import com.alibaba.fastjson.JSONObject;
import com.example.oauth2.domain.OAuth2AccessTokenVO;
import com.example.oauth2.utils.OkHttpUtil;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

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
    @Value("${security.oauth2.access-token-uri}")
    private String accessTokenUri;
    @Value("${security.oauth2.authorize-uri}")
    private String authorizeUri;

    public OAuth2AccessTokenVO callbackToken(String code) throws Exception {
        log.info("授权码：{}", code);
        // 调用获取token接口
        String bodyString = this.callTokenAPI(code);
        OAuth2AccessTokenVO oauth2AccessTokenVO = JSONObject.parseObject(bodyString, OAuth2AccessTokenVO.class);
        log.info("oauth2AccessTokenVO：{}", oauth2AccessTokenVO);
        return oauth2AccessTokenVO;
    }

    public String callTokenAPI(String code) throws Exception {
        // 构建请求头
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Authorization", buildAuthorization(clientId, clientSecret));
        // 请求参数
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("grant_type", "authorization_code");
        paramMap.put("redirect_uri", redirectUri);
        paramMap.put("code", code);
        // 构建请求体
        RequestBody requestBody = OkHttpUtil.buildRequestBody(OkHttpUtil.MEDIA_TYPE_FORM_URLENCODED, paramMap);

        Request request = new Request.Builder()
                .url(accessTokenUri)
                .headers(headers == null ? new Headers.Builder().build() : Headers.of(headers))
                .post(requestBody)
                .build();
        Response response = OkHttpUtil.getClient().newCall(request).execute();
        if (response == null) {
            return null;
        }
        ResponseBody responseBody = response.body();
        if (responseBody == null) {
            return null;
        }
        String bodyString = responseBody.string();
        if (StringUtils.isBlank(bodyString)) {
            return null;
        }
        log.info("callbackToken调用api返回:{}", bodyString);
        return bodyString;
    }

    private String buildAuthorization(String clientId, String clientSecret) {
        StringBuilder sb = new StringBuilder();
        sb.append(clientId).append(":").append(clientSecret);
        byte[] encodeBytes = Base64.getEncoder().encode(sb.toString().getBytes());
        return "Basic " + new String(encodeBytes);
    }

    public String getAuthorizationUrl() {
        String authorizationUrl = authorizeUri + "?client_id=" + clientId
                + "&redirect_uri=" + redirectUri
                + "&scope=read_userinfo"
                + "&response_type=code";
        log.info("授权地址:{}", authorizationUrl);
        return authorizationUrl;
    }
}
