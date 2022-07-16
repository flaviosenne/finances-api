package com.project.finances.app.usecases.user.ports;

import com.project.finances.domain.entity.User;

public interface MailCreateAccountProtocol {

    void sendEmailActiveAccount(User user, String code);

}
