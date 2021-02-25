package ru.kpfu.security.services;

public interface MailSender {
    void send(String to, String text);
}
