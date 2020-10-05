package com.sxdx.workflow.auth.controller;


import com.sxdx.common.exception.base.CommonException;
import com.sxdx.common.util.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class AuthorizationController {

    @Autowired
    private ConsumerTokenServices consumerTokenServices;

    /**
     * 获取当前用户信息
     * @param authentication
     * @return
     */
    @GetMapping("/authentication")
    public Object authentication(Authentication authentication){
        return authentication;
    }

    /**
     * 退出登录
     * @param request
     * @return
     * @throws CommonException
     */
    @DeleteMapping("signout")
    public CommonResponse signout(HttpServletRequest request) throws CommonException {
        String authorization = request.getHeader("Authorization");
        String token = StringUtils.replace(authorization, "Bearer ", "");

        if (!consumerTokenServices.revokeToken(token)) {
            throw new CommonException("退出登录失败");
        }
        return new CommonResponse().message("退出登录成功");
    }
}
