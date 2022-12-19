package com.example.oauth2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("/")
public class PermitDemoController {

    @GetMapping("/permit_demo")
    public String permitDemo() {
        return "这里是permit_demo";
    }

}
