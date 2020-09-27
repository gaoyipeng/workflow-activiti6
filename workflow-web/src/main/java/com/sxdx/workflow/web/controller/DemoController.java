package com.sxdx.workflow.web.controller;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@EnableOAuth2Sso
@RestController
public class DemoController {

    @GetMapping(name = "/r1")
    public String demo(){
        return "hello";
    }
}
