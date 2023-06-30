package cn.hnist.sharo.mcinema.core.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Map;


@Service
public class MailSendService{
    @Resource
    JavaMailSenderImpl mailSender;

    @Value("${spring.mail.username}")
    private String from;

    /**
     * <h3>发送文字邮件</h3>
     * @param to 收件人
     * @param subject 主题
     * @param text 文字内容
     */
    @Async
    public void send(String to, String subject, String text){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(to);
        mailMessage.setFrom(from);
        mailMessage.setSubject(subject);
        mailMessage.setText(text);
        mailSender.send(mailMessage);
    }

    /**
     * 发送带有文件附件的邮件
     * @param to 收件人
     * @param subject 主题
     * @param text 文字内容
     * @param html 是否开启html转换
     * @param data 文件数据 Map<String,File>
     * @throws MessagingException
     */
    @Async
    public void send(String to, String subject, String text,boolean html, Map<String, File> data) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,"utf-8");
        helper.setFrom(from);
        helper.setTo(to);

        helper.setSubject(subject);
        helper.setText(text,html);

        for (Map.Entry<String, File> entry : data.entrySet()) {
            String key = entry.getKey();
            File value = entry.getValue();
            helper.addAttachment(key, value);
        }

        mailSender.send(helper.getMimeMessage());
    }

    public String getFrom() {
        return from;
    }
}
