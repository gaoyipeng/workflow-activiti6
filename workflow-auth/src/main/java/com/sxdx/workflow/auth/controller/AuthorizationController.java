package com.sxdx.workflow.auth.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthorizationController {

    @GetMapping("/authentication")
    public Object authentication(Authentication authentication){
        return authentication;
    }
}
