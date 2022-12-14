package com.example.oauth2.controller;

import com.example.oauth2.domain.OAuth2AccessTokenVO;
import com.example.oauth2.service.OAuth2TokenService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Administrator
 */
@Slf4j
@RestController
@RequestMapping("/oauth2_token")
public class OAuth2TokenController {

    @Autowired
    private OAuth2TokenService oauth2TokenService;

    private static final String ERROR_ACCESS_DENIED = "access_denied";

    /**
     * 授权回调地址
     *
     * @param code 授权码
     * @return
     */
    @GetMapping("/callback")
    public OAuth2AccessTokenVO callback(@RequestParam(value = "code", required = false) String code,
                                        @RequestParam(value = "error", required = false) String error,
                                        @RequestParam(value = "error_description", required = false) String error_description) {
        log.info("OAuth2TokenController.callback，code：{}", code);
        log.info("OAuth2TokenController.callback，error：{}", error);
        log.info("OAuth2TokenController.callback，error_description：{}", error_description);
        try {
            if (StringUtils.isNotEmpty(error)) {
                OAuth2AccessTokenVO errorTokenVO = new OAuth2AccessTokenVO();
                errorTokenVO.setError(error);
                errorTokenVO.setErrorDescription(error_description);
                if (ERROR_ACCESS_DENIED.equals(error)) {
                    log.info("OAuth2TokenController.callback，授权拒绝");
                    return errorTokenVO;
                } else {
                    log.info("OAuth2TokenController.callback，授权出错");
                    return errorTokenVO;
                }
            }

            return oauth2TokenService.callbackToken(code);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取授权链接
     *
     * @return
     */
    @GetMapping(value = "/get_authorization_url")
    public String getAuthorizationUrl() {
        try {
            return oauth2TokenService.getAuthorizationUrl();
        } catch (Exception e) {
            log.error("获取授权链接异常：", e);
            return "获取授权链接异常，" + e.getMessage();
        }
    }

}
