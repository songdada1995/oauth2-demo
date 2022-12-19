package com.example.oauth2.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("/example")
public class ExampleController {

    @RequestMapping("/hello")
    public String hello() {
        return "world";
    }

}
