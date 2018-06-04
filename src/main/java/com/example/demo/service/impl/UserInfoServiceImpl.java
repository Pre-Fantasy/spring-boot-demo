package com.example.demo.service.impl;

import com.example.demo.core.universal.AbstractService;
import com.example.demo.core.ret.ServiceException;
import com.example.demo.dao.UserInfoMapper;
import com.example.demo.model.UserInfo;
import com.example.demo.service.UserInforService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserInfoServiceImpl extends AbstractService<UserInfo> implements UserInforService{


    @Resource
    private UserInfoMapper userInfoMapper;

    /**
     *  @author Pre_fantasy
     *  @create 2018/6/3 21:06
     *  @param
     *  @return
     *  @desc
     */
    @Override
    public UserInfo selectById(String id){
        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(id);
        if(userInfo == null){
            throw new ServiceException("暂无该用户");
        }
        return userInfo;
    }

    /**
     *  @author Pre_fantasy
     *  @create 2018/6/2 22:42
     *  @param  page    Integer
     *  @param  size    Integer
     *  @return PageInfo</UserInfo>
     *  @desc   列表分页查询
     */
   /* @Override
    public PageInfo<UserInfo> selectAll(Integer page, Integer size) {
        *//*开启分页查询，写在查询语句上方*//*
        *//*只有紧跟在PageHelper.startPage方法后的第一个Mybatis的查询（Select）方法会被分页。*//*
        PageHelper.startPage(page, size);
        List<UserInfo> userinfoList = userInfoMapper.selectAll();
        PageInfo<UserInfo> pageInfo = new PageInfo<UserInfo>(userinfoList);
        return pageInfo;
    }*/


}
