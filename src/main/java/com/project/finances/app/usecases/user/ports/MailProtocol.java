package com.project.finances.app.usecases.user.ports;

public interface MailProtocol {

    void sendEmail(String content, String subject, String[] to);
}
