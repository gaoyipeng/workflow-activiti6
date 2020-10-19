package com.sxdx.common.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

import java.util.Collection;

/**
 * @program: workflow-activiti
 * @description: 常用工具类
 * @author: garnett
 * @create: 2020-10-18 18:02
 **/
@Slf4j
public class workFlowUtil {

    private static Authentication getOauth2Authentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication;
    }

    /**
     * 获取当前用户名称
     *
     * @return String 用户名
     */
    public static String getCurrentUsername() {
        String name = getOauth2Authentication().getName();
        return name;
    }

    /**
     * 获取当前用户权限集
     *
     * @return Collection<GrantedAuthority>权限集
     */
    public static Collection<GrantedAuthority> getCurrentUserAuthority() {
        return (Collection<GrantedAuthority>) getOauth2Authentication().getAuthorities();
    }

    /**
     * 获取当前令牌内容
     *
     * @return String 令牌内容
     */
    public static String getCurrentTokenValue() {
        try {
            OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) getOauth2Authentication().getDetails();
            return details.getTokenValue();
        } catch (Exception ignore) {
            return null;
        }
    }
}
