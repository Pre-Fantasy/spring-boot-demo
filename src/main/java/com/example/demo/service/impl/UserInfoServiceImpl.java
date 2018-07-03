package com.example.demo.service.impl;

import com.example.demo.dao.UserInfoMapper;
import com.example.demo.model.UserInfo;
import com.example.demo.service.UserInfoService;
import com.example.demo.core.universal.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
* @Description: UserInfoService接口实现类
* @author Pre_fantasy
* @date 2018/07/02 20:47
*/
@Service
public class UserInfoServiceImpl extends AbstractService<UserInfo> implements UserInfoService {

    @Resource
    private UserInfoMapper userInfoMapper;

}