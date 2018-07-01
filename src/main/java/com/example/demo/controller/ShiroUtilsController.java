package com.example.demo.controller;

import com.example.demo.model.UserInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthenticatedException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author  Pre_fantasy
 * @create  2018-07-01 14:24
 * @desc    shiroUtilsController
 **/
@RestController
@RequestMapping("shiroUtils")
public class ShiroUtilsController {

    @GetMapping("/noLogin")
    public void noLogin() {
        throw new UnauthenticatedException();
    }

    @GetMapping("noAuthorize")
    public void noAuthorize() {
        throw new UnauthenticatedException();
    }

    @PostMapping("/getNewUser")
    public UserInfo getNewUser() {
        UserInfo userInfo = (UserInfo) SecurityUtils.getSubject().getPrincipal();
        return userInfo;
    }
}
