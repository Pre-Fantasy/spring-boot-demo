package com.example.demo.controller;

import com.example.demo.core.ret.RetResponse;
import com.example.demo.core.ret.RetResult;
import com.example.demo.model.UserInfo;
import com.example.demo.service.UserInforService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/userInfo")
@Api(tags = {"用户操作接口"}, description = "userInfoControler")
public class UserInfoController {

    @Resource
    private UserInforService userInfoService;

    @PostMapping("/hello")
    public String hello(){
        return "hello SprintBoot";

    }

    @ApiOperation(value="查询用户", notes="根据用户ID查找用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id", value="用户ID", required=true, dataType="String", paramType="query")
    })
    @PostMapping("/selectUserById")
    public RetResult<UserInfo> selectUserInfoById(@RequestParam String id){
        UserInfo userInfo = userInfoService.selectById(id);
        return RetResponse.makeOKRsp(userInfo);
    }

    /**
     *  @author Pre_fantasy
     *  @create 2018/6/1 17:54
     *  @param  id      Integer
     *  @return RerResult</UserInfo>
     *  @desc   测试用户不存在异常
     */
    @PostMapping("/testException")
    public RetResult<UserInfo> testException (@RequestParam String id) {

        List list = null;
        list.size();
        UserInfo userInfo = userInfoService.selectById(id);
        return RetResponse.makeOKRsp(userInfo);
    }


    /**
     *  @author Pre_fantasy
     *  @create 2018/6/2 22:52
     *  @param  {page(Integer), size(Intger)}
     *  @return RetResult<PageInfo<UserInfo>>
     *  @desc   查询用户分页列表
     */
    @ApiOperation(value = "查询用户", notes = "分页查询用户所有")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页码",
                    dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "每页显示条数",
                    dataType = "Integer", paramType = "query")
    })
    @PostMapping("/selectAll")
    public RetResult<PageInfo<UserInfo>> selectAll(@RequestParam(defaultValue = "0") Integer page,
                                                   @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page,size);
        List<UserInfo> userInfoList = userInfoService.selectAll();
        PageInfo<UserInfo> pageInfo = new PageInfo<>(userInfoList);
        return RetResponse.makeOKRsp(pageInfo);
    }




}
