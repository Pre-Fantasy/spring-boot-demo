package com.example.demo.controller;

import com.example.demo.model.UserInfo;
import com.example.demo.service.ShiroService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private ShiroService shiroService;

    @GetMapping("/noLogin")
    public void noLogin() {
        throw new UnauthenticatedException();
    }

    @GetMapping("noAuthorize")
    public void noAuthorize() {
        throw new UnauthorizedException();
    }

    @PostMapping("/getNewUser")
    public UserInfo getNewUser() {
        UserInfo userInfo = (UserInfo) SecurityUtils.getSubject().getPrincipal();
        return userInfo;
    }

    @PostMapping("/updatePermission")
    public void updatePermission() throws Exception {
        shiroService.updatePermission();
    }
}
