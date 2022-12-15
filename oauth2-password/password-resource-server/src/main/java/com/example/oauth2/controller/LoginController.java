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
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private OAuth2TokenService oauth2TokenService;

    /**
     * 登录接口
     *
     * @param username
     * @param password
     * @return
     */
    @GetMapping("/token")
    public OAuth2AccessTokenVO getToken(@RequestParam(value = "username", required = false) String username,
                                        @RequestParam(value = "password", required = false) String password) {
        log.info("LoginController.getToken，username：{}", username);
        log.info("LoginController.getToken，password：{}", password);
        try {
            return oauth2TokenService.getToken(username, password);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
