package com.example.oauth2.service;

import com.alibaba.fastjson.JSONObject;
import com.example.oauth2.domain.OAuth2AccessTokenVO;
import com.example.oauth2.utils.OkHttpUtil;
import lombok.extern.slf4j.Slf4j;
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

    public OAuth2AccessTokenVO getToken() throws Exception {
        // 调用获取token接口
        String bodyString = this.callTokenAPI(clientId, clientSecret);
        OAuth2AccessTokenVO oauth2AccessTokenVO = JSONObject.parseObject(bodyString, OAuth2AccessTokenVO.class);
        log.info("oauth2AccessTokenVO：{}", oauth2AccessTokenVO);
        return oauth2AccessTokenVO;
    }

    public String callTokenAPI(String clientId, String clientSecret) throws Exception {
        // 构建请求头
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Authorization", buildAuthorization(clientId, clientSecret));
        // 请求参数
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("grant_type", "client_credentials");

        String bodyString = OkHttpUtil.executePostOkHttpApi(accessTokenUri, headers,
                OkHttpUtil.buildRequestBody(OkHttpUtil.MEDIA_TYPE_FORM_URLENCODED, paramMap));
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
