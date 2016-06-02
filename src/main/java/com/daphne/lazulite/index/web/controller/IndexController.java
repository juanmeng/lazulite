/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.daphne.lazulite.index.web.controller;

import com.daphne.lazulite.common.Constants;
import com.daphne.lazulite.sys.user.entity.User;
import com.daphne.lazulite.sys.user.service.UserStatusHistoryService;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

/**
 * Created by junfu on 2016/5/4.
 */
@RequestMapping
@Controller
public class IndexController {
   /* @RequestMapping("/")
    public String home() {
        return "redirect:index.html";
    }*/

    @Value(value = "${shiro.login-url}")
    private String loginUrl;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private UserStatusHistoryService userStatusHistoryService;

    @RequestMapping(value = "api/account/login") //spring3.2.2 bug see  http://jinnianshilongnian.iteye.com/blog/1831408
    public String loginForm(HttpServletRequest request, ModelMap model) {

//        //表示退出
//        if (!StringUtils.isEmpty(request.getParameter("logout"))) {
//            model.addAttribute(Constants.MESSAGE, messageSource.getMessage("user.logout.success", null, null));
//        }
//
//        //表示用户删除了 @see org.apache.shiro.web.filter.user.SysUserFilter
//        if (!StringUtils.isEmpty(request.getParameter("notfound"))) {
//            model.addAttribute(Constants.ERROR, messageSource.getMessage("user.notfound", null, null));
//        }
//
//        //表示用户被管理员强制退出
//        if (!StringUtils.isEmpty(request.getParameter("forcelogout"))) {
//            model.addAttribute(Constants.ERROR, messageSource.getMessage("user.forcelogout", null, null));
//        }
//
//        //表示用户输入的验证码错误
//        if (!StringUtils.isEmpty(request.getParameter("jcaptchaError"))) {
//            model.addAttribute(Constants.ERROR, messageSource.getMessage("jcaptcha.validate.error", null, null));
//        }
//
//
//        //表示用户锁定了 @see org.apache.shiro.web.filter.user.SysUserFilter
//        if (!StringUtils.isEmpty(request.getParameter("blocked"))) {
//            User user = (User) request.getAttribute(Constants.CURRENT_USER);
//            String reason = userStatusHistoryService.getLastReason(user);
//            model.addAttribute(Constants.ERROR, messageSource.getMessage("user.blocked", new Object[]{reason}, null));
//        }
//
//        if (!StringUtils.isEmpty(request.getParameter("unknown"))) {
//            model.addAttribute(Constants.ERROR, messageSource.getMessage("user.unknown.error", null, null));
//        }
//
//        //登录失败了 提取错误消息
//        Exception shiroLoginFailureEx =
//                (Exception) request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
//        if (shiroLoginFailureEx != null) {
//            model.addAttribute(Constants.ERROR, shiroLoginFailureEx.getMessage());
//        }

        //如果用户直接到登录页面 先退出一下
        //原因：isAccessAllowed实现是subject.isAuthenticated()---->即如果用户验证通过 就允许访问
        // 这样会导致登录一直死循环
//        Subject subject = SecurityUtils.getSubject();
//        if (subject != null && subject.isAuthenticated()) {
//            subject.logout();
//        }


        //如果同时存在错误消息 和 普通消息  只保留错误消息
        if (model.containsAttribute(Constants.ERROR)) {
            model.remove(Constants.MESSAGE);
        }

        return "redirect:/#/page/login";
    }

}
