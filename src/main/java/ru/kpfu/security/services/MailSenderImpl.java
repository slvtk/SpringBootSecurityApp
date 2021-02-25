package ru.kpfu.security.services;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class MailSenderImpl implements MailSender {

    private final JavaMailSender javaMailSender;

    public MailSenderImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }


    @Override
    public void send(String to, String text) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setText(generateHtmlMessage(text), true);
            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private String generateHtmlMessage(String text) {
        return "<a href='" + text + "'>Confirm email address</a>";
    }
}
