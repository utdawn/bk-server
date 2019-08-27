package com.gcwl.bkserver.config;

import com.gcwl.bkserver.entity.Role;
import com.gcwl.bkserver.service.impl.UserServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * 父类AuthorizingRealm中isPermitied使用验证权限
 */
public class CustomRealm extends AuthorizingRealm {

    @Autowired
    private UserServiceImpl userServiceImpl;

    /**
     * 授权（验证权限时调用）
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String username = (String) SecurityUtils.getSubject().getPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Set<String> permissionSet = new HashSet<String>();
        // 取出当前用户的角色
        Set<Role> roleSet = userServiceImpl.getUserByUserName(username).getRoles();
        // 取出当前用户的权限
        for(Role role: roleSet){
            permissionSet.addAll(role.getPermissions());
        }
        // 将权限加入SimpleAuthorizationInfo中，
        // 使得框架可以获取到用户所拥有的权限
        info.setStringPermissions(permissionSet);
        return info;
    }

    /**
     * 这里可以注入userService
     * private UserService userService;
     * <p>
     * 获取即将需要认证的信息
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("-------身份认证方法--------");
        String userName = (String) authenticationToken.getPrincipal();
        String userPwd = new String((char[]) authenticationToken.getCredentials());
        //根据用户名从数据库获取密码
        String password = userServiceImpl.getPwdByUserName(userName);
        if (userName == null) {
            throw new AccountException("用户名不正确");
        } else if (!userPwd.equals(userPwd)) {
            throw new AccountException("密码不正确");
        }
        //交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配
        return new SimpleAuthenticationInfo(userName, password,
                ByteSource.Util.bytes(userName + "salt"), getName());
    }
}