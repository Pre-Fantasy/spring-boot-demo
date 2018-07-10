package com.example.demo.controller;

import com.example.demo.core.constant.MailContant;
import com.example.demo.core.ret.RetResponse;
import com.example.demo.core.ret.RetResult;
import com.example.demo.core.utils.ApplicationUtils;
import com.example.demo.model.Mail;
import com.example.demo.service.MailService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author dell
 * @create 2018-07-09 14:00
 * @desc 发送邮件的api接口
 **/
@RestController
@RequestMapping("/mail")
public class MailController {

    @Resource
    private MailService mailService;

    @PostMapping("/sendTemplateMail")
    public RetResult<String> sendTemplateMail(Mail mail) throws Exception {
        String[] array = {"Pre_fantasy@163.com"};
        mail.setTo(array);
        String identifyingCode = ApplicationUtils.getNumStringRandom(6);
        mail.setSubject("欢迎注册出城");
        mail.setTemplateName(MailContant.RETGISTEREMPLATE);
        Map<String, String> map = new HashMap<>();
        map.put("identifyingCode", identifyingCode);
        map.put("to", mail.getTo()[0]);
        mail.setTemplateModel(map);
        mailService.sendTemplateMail(mail);
        return RetResponse.makeOKRsp(identifyingCode);
    }

    @PostMapping("/sendAttachmentsMail")
    public RetResult<String> sendAttacmentsMail(Mail mail, HttpServletRequest request) {
        mail.setSubject("测试附件");
        mailService.sendAttachmentsMail(mail, request);
        return RetResponse.makeOKRsp();
    }
}
