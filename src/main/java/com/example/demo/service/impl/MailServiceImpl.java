package com.example.demo.service.impl;

import com.example.demo.core.constant.MailContant;
import com.example.demo.core.utils.UploadActionUtil;
import com.example.demo.model.Mail;
import com.example.demo.service.MailService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author  Pre_fantasy
 * @create  2018-07-09 10:42
 * @desc    邮件 业务接口实现类
 **/

@Service
public class MailServiceImpl implements MailService {

    private static final Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);

    @Resource
    @Qualifier("javaMailSender")
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    @Resource
    private FreeMarkerConfigurer freeMarkerConfigurer;


    /**
     *  @author Pre_fantasy
     *  @create 2018/7/9 10:46
     *  @param  {mail<Mail>}
     *  @return
     *  @desc   发送简单邮件
     */
    @Override
    public void sendSimpleMail(Mail mail) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setBcc(mail.getCc());
        message.setFrom(from);
        message.setTo(mail.getTo());
        message.setSubject(mail.getSubject());
        message.setText(mail.getText());
        mailSender.send(message);
    }

    /**
     *  @author Pre_fantasy
     *  @create 2018/7/9 10:50
     *  @param  {mail<Mail>, request<HttpServletRequest>}
     *  @return
     *  @desc   发送附件
     */
    @Override
    public void sendAttachmentsMail(Mail mail, HttpServletRequest request) {

        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(from);
            helper.setTo(mail.getTo());
            helper.setSubject(mail.getSubject());
            helper.setText(mail.getText());
            List<String> list = UploadActionUtil.uploadFile(request);
            for (int i = 1, length = list.size(); i <= length; i++) {
                String fileName = list.get(i - 1);
                String fileType = fileName.substring(fileName.lastIndexOf("."));
                FileSystemResource file = new FileSystemResource(new File(fileName));
                helper.addAttachment("附件-" + i + fileType, file);
            }
            mailSender.send(mimeMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *  @author Pre_fantasy
     *  @create 2018/7/9 11:01
     *  @param  {mai<Mail>}
     *  @return
     *  @desc   发送静态资源，一张照片
     */
    @Override
    public void sendInlineMail(Mail mail) throws Exception {
        try {

            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(from);
            helper.setTo(mail.getTo());
            helper.setSubject(mail.getSubject());
            helper.setText("<html><body><img src=\"cid:chuchen\" ></body></html>", true);
            FileSystemResource file = new FileSystemResource(
                    new File("C:\\Users\\dell\\Desktop\\微信截图_20180509235954.png"));
            /*addInline函数中资源名称出城需要与正文中cid:chucheng 对应起来*/
            helper.addInline("chuchen", file);
            mailSender.send(mimeMessage);
        } catch (Exception e) {
            logger.error("发送要加你失败");
        }

    }

    /**
     *  @author Pre_fantasy
     *  @create 2018/7/9 11:16
     *  @param  {mail<Mail>}
     *  @return 
     *  @desc   发送模板邮件
     */
    @Override
    public void sendTemplateMail(Mail mail) {
        MimeMessage message = null;
        try {
            message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(mail.getTo());
            helper.setSubject(mail.getSubject());
            /*读取html模板*/
            freemarker.template.Configuration cfg = getConfiguration();
            Template template = cfg.getTemplate(mail.getTemplateName() + ".ftl");
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, mail.getTemplateModel());
            helper.setText(html, true);
        } catch (Exception e) {

        }
        mailSender.send(message);
    }

    private static freemarker.template.Configuration getConfiguration() throws IOException{
        freemarker.template.Configuration cfg = new freemarker.template.Configuration(freemarker.template.Configuration.VERSION_2_3_23);
        cfg.setDirectoryForTemplateLoading(new File(MailContant.TEMPLATEPATH));
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
        return cfg;
    }


}
