
package com.example.demo.controller;


import com.example.demo.core.ret.RetResponse;
import com.example.demo.core.ret.RetResult;
import com.example.demo.service.RedisService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author  Pre_fantasy
 * @create  2018-06-29 15:54
 * @desc    Redis测试接口
 **/

@RestController
@RequestMapping("redis")
public class RedisController {

    @Resource
    private RedisService redisService;


    @PostMapping("/setRedis")
    public RetResult<String> setRedis(String name) {
        redisService.set("name", name);
        return RetResponse.makeOKRsp(name);
    }

    @PostMapping("/getRedis")
    public RetResult<String> getRedis() {
        String name = redisService.get("name");
        return RetResponse.makeOKRsp(name);
    }

}

