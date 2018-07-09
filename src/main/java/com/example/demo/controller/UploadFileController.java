package com.example.demo.controller;

import com.example.demo.core.ret.RetResponse;
import com.example.demo.core.ret.RetResult;
import com.example.demo.core.utils.UploadActionUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author  Pre_fantasy
 * @create  2018-07-08 14:56
 * @desc    多文件上传接口类
 **/
@RestController
@RequestMapping("/uploadFile")
public class UploadFileController {

    @PostMapping("/upload")
    public RetResult<List<String>> upload(HttpServletRequest request) throws Exception {
        List<String> list = UploadActionUtil.uploadFile(request);
        return RetResponse.makeOKRsp(list);
    }
}
