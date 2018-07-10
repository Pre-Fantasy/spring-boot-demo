package com.example.demo.model;

import java.util.Map;

/**
 * @author  Pre_fantasy
 * @create  2018-07-09 9:50
 * @desc    邮件实体类
 **/
public class Mail {

    /*发送给多人*/
    private String[] to;

    /*抄送*/
    private String[] cc;

    /*邮件标题*/
    private String subject;

    /*邮件内容 简单文本和附件必填 其余的不需要*/
    private String  text;

    /*魔板需要的数据 发送魔板邮件必填*/
    private Map<String, String> templateModel;

    /*选用哪个魔板， 发送邮件必填*/
    private String templateName;

    public String[] getTo() {
        return to;
    }

    public void setTo(String[] to) {
        this.to = to;
    }

    public String[] getCc() {
        return cc;
    }

    public void setCc(String[] cc) {
        this.cc = cc;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Map<String, String> getTemplateModel() {
        return templateModel;
    }

    public void setTemplateModel(Map<String, String> templateModel) {
        this.templateModel = templateModel;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }
}
