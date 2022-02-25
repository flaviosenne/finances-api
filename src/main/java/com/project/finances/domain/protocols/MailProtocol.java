package com.project.finances.domain.protocols;

public interface MailProtocol {

    void sendEmail(String content, String subject, String[] to);
}
