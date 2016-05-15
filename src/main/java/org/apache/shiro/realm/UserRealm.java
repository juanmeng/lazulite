/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

/**
 * 
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package org.apache.shiro.realm;


import com.daphne.lazulite.sys.auth.service.UserAuthService;
import com.daphne.lazulite.sys.user.entity.User;
import com.daphne.lazulite.sys.user.exception.*;
import com.daphne.lazulite.sys.user.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * <p>User: 
 * <p>Date: 13-3-12 下午9:05
 * <p>Version: 1.0
 */
@Component
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;
    @Autowired
    private UserAuthService userAuthService;

    private static final Logger log = LoggerFactory.getLogger("es-error");

//    @Autowired
//    public UserRealm(ApplicationContext ctx) {
//        super();
//        //不能注入 因为获取bean依赖顺序问题造成可能拿不到某些bean报错
//        //why？
//        //因为spring在查找findAutowireCandidates时对FactoryBean做了优化，即只获取Bean，但不会autowire属性，
//        //所以如果我们的bean在依赖它的bean之前初始化，那么就得不到ObjectType（永远是Repository）
//        //所以此处我们先getBean一下 就没有问题了
//        ctx.getBeansOfType(SimpleBaseRepositoryFactoryBean.class);
//    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = (String) principals.getPrimaryPrincipal();
        User user = userService.findByUsername(username);

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.setRoles(userAuthService.findStringRoles(user));
        authorizationInfo.setStringPermissions(userAuthService.findStringPermissions(user));

        return authorizationInfo;
    }

    private static final String OR_OPERATOR = " or ";
    private static final String AND_OPERATOR = " and ";
    private static final String NOT_OPERATOR = "not ";

    /**
     * 支持or and not 关键词  不支持and or混用
     *
     * @param principals
     * @param permission
     * @return
     */
    public boolean isPermitted(PrincipalCollection principals, String permission) {
        if (permission.contains(OR_OPERATOR)) {
            String[] permissions = permission.split(OR_OPERATOR);
            for (String orPermission : permissions) {
                if (isPermittedWithNotOperator(principals, orPermission)) {
                    return true;
                }
            }
            return false;
        } else if (permission.contains(AND_OPERATOR)) {
            String[] permissions = permission.split(AND_OPERATOR);
            for (String orPermission : permissions) {
                if (!isPermittedWithNotOperator(principals, orPermission)) {
                    return false;
                }
            }
            return true;
        } else {
            return isPermittedWithNotOperator(principals, permission);
        }
    }

    private boolean isPermittedWithNotOperator(PrincipalCollection principals, String permission) {
        if (permission.startsWith(NOT_OPERATOR)) {
            return !super.isPermitted(principals, permission.substring(NOT_OPERATOR.length()));
        } else {
            return super.isPermitted(principals, permission);
        }
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String username = upToken.getUsername().trim();
//        String password = "";
//        if (upToken.getPassword() != null) {
//            password = new String(upToken.getPassword());
//        }
        User user = userService.findByUsername(username);

        if(user == null) {
            throw new UnknownAccountException();//没找到帐号
        }


        //交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配，如果觉得人家的不好可以自定义实现
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user.getUsername(), //用户名
                user.getPassword(), //密码
                ByteSource.Util.bytes(user.getUsername()+user.getSalt()),//salt
                getName()  //realm name
        );
        return authenticationInfo;
    }

}