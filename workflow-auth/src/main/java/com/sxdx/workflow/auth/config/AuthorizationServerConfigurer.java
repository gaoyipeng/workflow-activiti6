package com.sxdx.workflow.auth.config;

import com.sxdx.workflow.auth.service.OauthUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.InMemoryAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * 认证服务器配置
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfigurer extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private TokenStore tokenStore;
    @Autowired
    private ClientDetailsService clientDetailsService;
    @Autowired
    private AuthorizationCodeServices authorizationCodeServices;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private OauthUserDetailService userDetailService;


    /**
     * 配置客户端详情信息
     * @param clients
     * @throws Exception
     */
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("kiki")
                .secret(passwordEncoder.encode("123456"))
                .authorizedGrantTypes("password","authorization_code","client_credentials" ,"implicit","refresh_token")
                .autoApprove(true)
                .scopes("all")
                .redirectUris("https://www.baidu.com")
                .resourceIds("kiki-resource","activiti-rest");
    }

    /**
     * 配置令牌端点的安全约束
     * @param security
     * @throws Exception
     */
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("permitAll()")
                .allowFormAuthenticationForClients();//允许表单认证
    }

    /**ClientCredentialsTokenEndpointFilter
     * 配置令牌（token）的访问端点和令牌服务（token service）
     * @param endpoints
     * @throws Exception
     */
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .authenticationManager(authenticationManager)//认证管理器
                .authorizationCodeServices(authorizationCodeServices)//授权码服务
                .tokenServices(tokenServices())//令牌管理服务
                .userDetailsService(userDetailService)
                .allowedTokenEndpointRequestMethods(HttpMethod.POST);
    }

    /**
     * 令牌管理服务
     * @return
     */
    @Primary
    @Bean
    public DefaultTokenServices tokenServices(){
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setClientDetailsService(clientDetailsService);//客户端详情服务
        tokenServices.setTokenStore(tokenStore);//token存储策略
        tokenServices.setSupportRefreshToken(true);//支持token刷新
        tokenServices.setAccessTokenValiditySeconds(60 * 60 * 24);//令牌默认有效期
        tokenServices.setRefreshTokenValiditySeconds(60 * 60 * 24 * 7);//刷新令牌默认有效期
        return tokenServices;
    }

    /**
     * 授权码服务：配置授权码模式下授权码存储方式
     * @return
     */
    @Bean
    public AuthorizationCodeServices authorizationCodeServices(){
        return new InMemoryAuthorizationCodeServices();
    }

}
