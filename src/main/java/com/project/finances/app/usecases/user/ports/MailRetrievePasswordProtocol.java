package com.project.finances.app.usecases.user.ports;

import com.project.finances.domain.entity.User;

public interface MailRetrievePasswordProtocol {

    void sendEmailRetrievePassword(User user, String code);

}
