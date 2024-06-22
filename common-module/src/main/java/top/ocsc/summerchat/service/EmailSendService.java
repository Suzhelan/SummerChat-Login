package top.ocsc.summerchat.service;

import jakarta.mail.internet.MimeMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;

@Service
public class EmailSendService {

    private static final Logger log = LogManager.getLogger(EmailSendService.class);
    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String from;

    @Autowired
    private TemplateEngine templateEngine;


    public Boolean sendSimpleMail(String to, String title, String content) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(from);
        msg.setTo(to);
        msg.setSubject(title);
        msg.setText(content);
        try {
            javaMailSender.send(msg);
        } catch (MailException ex) {
            log.error("发送失败：{}", ex.getMessage());
            return false;
        }
        return true;
    }


    public void sendHtmlMail(String receiver, String subject, String htmlText) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);       // 发送者
            helper.setTo(receiver);       // 接受者
            helper.setSubject(subject);   // 邮件主题
            if (StringUtils.hasLength(htmlText)) {
                helper.setText(htmlText, true);  // HTML
            }
            javaMailSender.send(message);
        } catch (Exception ex) {
            // TODO 异常处理
            log.error("发送失败：{}", ex.getMessage());
        }
    }

    public void sendSimpleMailWithAttachment(String receiver, String subject, String htmlText, String filePath) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);             // 发送者
            helper.setTo(receiver);             // 接受者
            helper.setSubject(subject);         // 邮件主题
            helper.setText(htmlText, true);  // 邮件正文
            File file = new File(filePath);
            FileSystemResource fileResource = new FileSystemResource(file);
            helper.addAttachment(file.getName(), fileResource);  // 附件
            javaMailSender.send(message);
        } catch (Exception ex) {
            // TODO 异常处理
            log.error("发送失败：{}", ex.getMessage());
        }
    }


    public void sendRegistrationVerificationCodeEmail(String to, String operationName, String code) {
        Context context = new Context();
        context.setVariable("operationName", operationName);
        context.setVariable("verifyCode", code);
        String emailContent = templateEngine.process("email", context);
        sendHtmlMail(to, "验证码", emailContent);
    }

}
