package com.example.demo.controller;

import com.example.demo.core.ret.RetResult;
import com.example.demo.core.ret.RetResponse;
import com.example.demo.core.utils.ApplicationUtils;
import com.example.demo.model.SysPermissionInit;
import com.example.demo.service.SysPermissionInitService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Pre_fantasy
 * @Description: SysPermissionInitController类
 * @date 2018/07/02 20:52
 */
@RestController
@RequestMapping("/sysPermissionInit")
public class SysPermissionInitController {

    @Resource
    private SysPermissionInitService sysPermissionInitService;

    @PostMapping("/insert")
    public RetResult<Integer> insert(SysPermissionInit sysPermissionInit) throws Exception {
        sysPermissionInit.setId(ApplicationUtils.getUUID());
        Integer state = sysPermissionInitService.insert(sysPermissionInit);
        return RetResponse.makeOKRsp(state);
    }

    @PostMapping("/deleteById")
    public RetResult<Integer> deleteById(@RequestParam String id) throws Exception {
        Integer state = sysPermissionInitService.deleteById(id);
        return RetResponse.makeOKRsp(state);
    }

    @PostMapping("/update")
    public RetResult<Integer> update(SysPermissionInit sysPermissionInit) throws Exception {
        Integer state = sysPermissionInitService.update(sysPermissionInit);
        return RetResponse.makeOKRsp(state);
    }

    @PostMapping("/selectById")
    public RetResult<SysPermissionInit> selectById(@RequestParam String id) throws Exception {
        SysPermissionInit sysPermissionInit = sysPermissionInitService.selectById(id);
        return RetResponse.makeOKRsp(sysPermissionInit);
    }

    /**
     * @param page 页码
     * @param size 每页条数
     * @Description: 分页查询
     * @Reutrn RetResult<PageInfo   <   SysPermissionInit>>
     */
    @PostMapping("/list")
    public RetResult<PageInfo<SysPermissionInit>> list(@RequestParam(defaultValue = "0") Integer page,
                                                       @RequestParam(defaultValue = "0") Integer size) throws Exception {
        PageHelper.startPage(page, size);
        List<SysPermissionInit> list = sysPermissionInitService.selectAll();
        PageInfo<SysPermissionInit> pageInfo = new PageInfo<SysPermissionInit>(list);
        return RetResponse.makeOKRsp(pageInfo);
    }

    /*public RetResult<PageInfo<SysPermissionInit>> selectAllOrderBySort(@RequestParam(defaultValue = "0") Integer page,
                                                                       @RequestParam(defaultValue = "0") Integer size) throws Exception {
        PageHelper.startPage(page, size);
        List<SysPermissionInit> list = sysPermissionInitService.selectAll();
        PageInfo<SysPermissionInit> pageInfo = new PageInfo<SysPermissionInit>(list);
        return RetResponse.makeOKRsp(pageInfo);
    }*/
}