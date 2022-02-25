package com.project.finances.domain.usecases.user.email;

import com.project.finances.domain.entity.User;

public interface MailCreateAccountProtocol {

    void sendEmail(User user, String code);

}
