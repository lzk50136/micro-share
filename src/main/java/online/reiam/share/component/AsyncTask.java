package online.reiam.share.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 异步任务
 */
@Component
public class AsyncTask {
    @Value("${spring.mail.username}")
    private String username;
    @Autowired
    private JavaMailSender javaMailSender;

    /**
     * 发送邮件
     */
    @Async
    public void sendMail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        // 设定邮件参数
        message.setFrom(username); //发送者
        message.setTo(to); //接受者
        message.setSubject(subject); //主题
        message.setText(text); //邮件内容
        // 发送邮件
        javaMailSender.send(message);
    }

}