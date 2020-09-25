package com.sxdx.workflow.auth.constant;

/**
 * 端点常量
 */
public interface EndpointConstant {

    String ALL = "/**";

    String OAUTH_ALL = "/oauth/**";

    String ACTUATOR_ALL = "/actuator/**";

    //授权端点
    String OAUTH_AUTHORIZE = "/oauth/authorize";

    //资源服务访问的令牌解析端点
    String OAUTH_CHECK_TOKEN = "/oauth/check_token";

    //用户确认授权提交端点
    String OAUTH_CONFIRM_ACCESS = "/oauth/confirm_access";

    //令牌端点
    String OAUTH_TOKEN = "/oauth/token";

    //公钥秘钥的端点（使用JWT令牌需要）
    String OAUTH_TOKEN_KEY = "/oauth/token_key";

    //授权服务错误信息端点
    String OAUTH_ERROR = "/oauth/error";


}
