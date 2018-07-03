package com.example.demo.controller;

import com.example.demo.core.ret.RetResult;
import com.example.demo.core.ret.RetResponse;
import com.example.demo.core.ret.ServiceException;
import com.example.demo.core.utils.ApplicationUtils;
import com.example.demo.model.UserInfo;
import com.example.demo.service.UserInfoService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Pre_fantasy
 * @Description: UserInfoController类
 * @date 2018/07/02 20:47
 */
@RestController
@RequestMapping("/userInfo")
public class UserInfoController {

    @Resource
    private UserInfoService userInfoService;

    @PostMapping("/insert")
    public RetResult<Integer> insert(UserInfo userInfo) throws Exception {
        userInfo.setId(ApplicationUtils.getUUID());
        Integer state = userInfoService.insert(userInfo);
        return RetResponse.makeOKRsp(state);
    }

    @PostMapping("/deleteById")
    public RetResult<Integer> deleteById(@RequestParam String id) throws Exception {
        Integer state = userInfoService.deleteById(id);
        return RetResponse.makeOKRsp(state);
    }

    @PostMapping("/update")
    public RetResult<Integer> update(UserInfo userInfo) throws Exception {
        Integer state = userInfoService.update(userInfo);
        return RetResponse.makeOKRsp(state);
    }

    @PostMapping("/selectById")
    public RetResult<UserInfo> selectById(@RequestParam String id) throws Exception {
        UserInfo userInfo = userInfoService.selectById(id);
        return RetResponse.makeOKRsp(userInfo);
    }

    /**
     * @param page 页码
     * @param size 每页条数
     * @Description: 分页查询
     * @Reutrn RetResult<PageInfo   <   UserInfo>>
     */
    @PostMapping("/list")
    public RetResult<PageInfo<UserInfo>> list(@RequestParam(defaultValue = "0") Integer page,
                                              @RequestParam(defaultValue = "0") Integer size) throws Exception {
        PageHelper.startPage(page, size);
        List<UserInfo> list = userInfoService.selectAll();
        PageInfo<UserInfo> pageInfo = new PageInfo<UserInfo>(list);
        return RetResponse.makeOKRsp(pageInfo);
    }

    @PostMapping("/login")
    public RetResult<UserInfo> login(String userName, String password) {
        Subject currentUser = SecurityUtils.getSubject();
        //登录
        try {
            currentUser.login(new UsernamePasswordToken(userName, password));
        }catch (IncorrectCredentialsException i){
            throw new ServiceException("密码输入错误");
        }
        //从session取出用户信息
        UserInfo user = (UserInfo) currentUser.getPrincipal();
        return RetResponse.makeOKRsp(user);
    }

}