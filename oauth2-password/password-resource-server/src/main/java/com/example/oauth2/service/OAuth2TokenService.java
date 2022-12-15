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
    @Value("${security.oauth2.access-token-uri}")
    private String accessTokenUri;

    public OAuth2AccessTokenVO getToken(String username, String password) throws Exception {
        // 调用获取token接口
        String bodyString = this.callTokenAPI(username, password);
        OAuth2AccessTokenVO oauth2AccessTokenVO = JSONObject.parseObject(bodyString, OAuth2AccessTokenVO.class);
        log.info("oauth2AccessTokenVO：{}", oauth2AccessTokenVO);
        return oauth2AccessTokenVO;
    }

    public String callTokenAPI(String username, String password) throws Exception {
        // 构建请求头
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Authorization", buildAuthorization(clientId, clientSecret));
        // 请求参数
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("grant_type", "password");
        paramMap.put("username", username);
        paramMap.put("password", password);
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

}
