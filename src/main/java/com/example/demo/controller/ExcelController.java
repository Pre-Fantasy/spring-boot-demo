package com.example.demo.controller;

import com.example.demo.core.constant.ExcelConstant;
import com.example.demo.core.ret.RetResponse;
import com.example.demo.core.ret.RetResult;
import com.example.demo.core.utils.ExcelUtils;
import com.example.demo.model.ExcelData;
import com.example.demo.model.UserInfo;
import com.example.demo.service.UserInfoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Pre_fantasy
 * @create 2018-07-10 16:51
 * @desc 下载excel表格api
 **/
@RestController
@RequestMapping("excel")
public class ExcelController {

    @Resource
    private UserInfoService userInfoService;

    @PostMapping("/test")
    public RetResult<Integer> test() {
        int rowIndex = 0;
        List<UserInfo> list = userInfoService.selectAll();
        ExcelData data = new ExcelData();
        data.setName("htllo");
        List<String> titles = new ArrayList<>();
        titles.add("ID");
        titles.add("userName");
        titles.add("password");
        data.setTitles(titles);
        List<List<Object>> rows = new ArrayList<>();
        for (int i = 0, length = list.size(); i < length; i ++) {
            UserInfo userInfo = list.get(i);
            List<Object> row = new ArrayList<>();
            row.add(userInfo.getId());
            row.add(userInfo.getUserName());
            row.add(userInfo.getPassword());
            rows.add(row);
        }
        data.setRows(rows);
        try {
            rowIndex = ExcelUtils.generateExcel(data, ExcelConstant.FILE_PATH + ExcelConstant.FILE_NAME);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return RetResponse.makeOKRsp(Integer.valueOf(rowIndex));
    }

    @GetMapping("/test2")
    public void test2(HttpServletResponse response) {
        int rowIndex = 0;
        List<UserInfo> list = userInfoService.selectAll();
        ExcelData data = new ExcelData();
        data.setName("hello");
        List<String> titles = new ArrayList<>();
        titles.add("ID");
        titles.add("userName");
        titles.add("password");
        data.setTitles(titles);

        List<List<Object>> rows = new ArrayList();
        for(int i = 0, length = list.size();i<length;i++){
            UserInfo userInfo = list.get(i);
            List<Object> row = new ArrayList();
            row.add(userInfo.getId());
            row.add(userInfo.getUserName());
            row.add(userInfo.getPassword());
            rows.add(row);
        }
        data.setRows(rows);

        try{
            ExcelUtils.exportExcel(response,"test2",data);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
