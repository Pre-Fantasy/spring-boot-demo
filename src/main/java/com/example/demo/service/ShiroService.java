package com.example.demo.service;

import java.util.Map;

/**
 * @author dell
 * @create 2018-07-03 10:43
 * @desc 创建shiroService，动态修改权限
 **/
public interface ShiroService {

    Map<String, String> loadFilterChaindefinitions();

    /*动态修改权限*/
    void updatePermission();
}
