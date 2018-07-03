package com.example.demo.service.impl;

import com.example.demo.model.SysPermissionInit;
import com.example.demo.service.ShiroService;
import com.example.demo.service.SysPermissionInitService;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dell
 * @create 2018-07-03 10:48
 * @desc 动态修改权限实现类
 **/
@Service
public class ShiroServiceImpl implements ShiroService {

    @Autowired
    ShiroFilterFactoryBean shiroFilterFactoryBean;

    @Autowired
    SysPermissionInitService sysPermissionInitService;

    /**
     *  @author Pre_fantasy
     *  @create 2018/7/3 10:58
     *  @param
     *  @return Map<String, String>
     *  @desc   权限初始化
     */
    @Override
    public Map<String, String> loadFilterChaindefinitions() {
        Map<String, String> filterChainDefinitionMap = new HashMap<>();
        List<SysPermissionInit> list = sysPermissionInitService.selectAllOrderBySort();
        for (SysPermissionInit sysPermissionInit : list) {
            filterChainDefinitionMap.put(sysPermissionInit.getUrl(), sysPermissionInit.getPermissionInit());
        }
        return filterChainDefinitionMap;
    }

    /**
     *  @author Pre_fantasy
     *  @create 2018/7/3 10:50
     *  @param
     *  @return
     *  @desc   动态修改权限
     */
    @Override
    public void updatePermission() {

        synchronized (shiroFilterFactoryBean) {
            AbstractShiroFilter shiroFilter = null;
            try {
                shiroFilter = (AbstractShiroFilter) shiroFilterFactoryBean.getObject();
            } catch (Exception e) {
                throw new RuntimeException("get ShiroFileter from shiroFilterFactoryBean error");
            }
            PathMatchingFilterChainResolver filterChainResolver = (PathMatchingFilterChainResolver) shiroFilter.getFilterChainResolver();
            DefaultFilterChainManager manager = (DefaultFilterChainManager) filterChainResolver.getFilterChainManager();
            /*清空老权限*/
            manager.getFilterChains().clear();
            shiroFilterFactoryBean.getFilterChainDefinitionMap().clear();
            shiroFilterFactoryBean.setFilterChainDefinitionMap(loadFilterChaindefinitions());
            /*重新构建生成*/
            Map<String, String> chains = shiroFilterFactoryBean.getFilterChainDefinitionMap();
            for (Map.Entry<String, String> entry : chains.entrySet()) {
                String url = entry.getKey();
                String chainDefinitin = entry.getValue().trim().replace(" ", "");
                manager.createChain(url, chainDefinitin);
            }
            System.out.println("更新权限成功！！");
        }
    }
}
