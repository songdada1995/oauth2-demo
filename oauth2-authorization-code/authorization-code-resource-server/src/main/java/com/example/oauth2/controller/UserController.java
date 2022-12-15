package com.example.oauth2.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author songbo
 * @version 1.0
 * @date 2022/12/14 10:14
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @RequestMapping("/login_user")
    public Authentication getLoginUser(Authentication authentication) {
        log.info("当前用户登录信息：{}", JSONObject.toJSONString(authentication));
        return authentication;
    }

}
