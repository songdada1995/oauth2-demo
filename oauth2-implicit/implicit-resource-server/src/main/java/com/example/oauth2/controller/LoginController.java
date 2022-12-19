package com.example.oauth2.controller;

import com.example.oauth2.service.OAuth2TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Administrator
 */
@Slf4j
@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private OAuth2TokenService oauth2TokenService;

    /**
     * 授权回调地址，模拟页面
     *
     * @return
     */
    @GetMapping("/callback")
    public String callback() {
        try {
            return "这里是一个前端页面，url地址栏#后面可获取token";
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
