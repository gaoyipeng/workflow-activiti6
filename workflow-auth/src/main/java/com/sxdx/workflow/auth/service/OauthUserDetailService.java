package com.sxdx.workflow.auth.service;

import com.sxdx.common.entity.AuthUser;
import com.sxdx.workflow.auth.entity.Menu;
import com.sxdx.workflow.auth.entity.SecurityUser;
import com.sxdx.workflow.auth.entity.User;
import com.sxdx.workflow.auth.mapper.MenuMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OauthUserDetailService implements UserDetailsService {

    @Autowired
    private IUserService iUserService;

    @Autowired
    private IMenuService iMenuService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = iUserService.findByName(username);
        if (user != null) {
            //获取当前用户的权限信息
            List<Menu> permissions = iMenuService.findUserPermissionsByUserId(user.getUserId());
            String userPermissions = permissions.stream().map(Menu::getPerms).collect(Collectors.joining(","));

            boolean notLocked = false;
            if (StringUtils.equals(user.STATUS_VALID, user.getStatus())) {
                notLocked = true;
            }
            String password = user.getPassword();
            AuthUser authUser = new AuthUser(user.getUsername(), password, true, true, true, notLocked,
                    AuthorityUtils.commaSeparatedStringToAuthorityList(userPermissions));
            return authUser;
        }else {
            throw new UsernameNotFoundException("用户名不存在");
        }

        /*return new AuthUser(username, user.getPassword(), user.isEnabled(),
                user.isAccountNonExpired(), user.isCredentialsNonExpired(),
                user.isAccountNonLocked(), AuthorityUtils.commaSeparatedStringToAuthorityList("user:add"));*/
    }
}
