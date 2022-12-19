package com.example.oauth2.controller;

import com.example.oauth2.domain.OAuth2AccessTokenVO;
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
@RequestMapping("/client_login")
public class ClientLoginController {

    @Autowired
    private OAuth2TokenService oauth2TokenService;

    /**
     * 获取客户端凭证token
     *
     * @return
     */
    @GetMapping("/token")
    public OAuth2AccessTokenVO getToken() {
        try {
            return oauth2TokenService.getToken();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
