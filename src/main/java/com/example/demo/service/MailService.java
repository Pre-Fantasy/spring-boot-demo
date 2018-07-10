package com.example.demo.service;

import com.example.demo.model.Mail;

import javax.servlet.http.HttpServletRequest;

/**
 * @author  Pre_fantasy
 * @create  2018-07-09 10:36
 * @desc    邮件业务接口
 **/
public interface MailService {

    /*发送邮件*/
    void sendSimpleMail(Mail mail);

    /*发送附带邮件*/
    void sendAttachmentsMail(Mail mail, HttpServletRequest request);

    /*发送静态资源， 一张照片*/
    void sendInlineMail(Mail mail) throws Exception;

    /*发送模板邮件*/
    void sendTemplateMail(Mail mail);
}
