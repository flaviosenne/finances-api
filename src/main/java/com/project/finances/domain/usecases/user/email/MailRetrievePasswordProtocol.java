package com.project.finances.domain.usecases.user.email;

import com.project.finances.domain.entity.User;

public interface MailRetrievePasswordProtocol {

    void sendEmail(User user, String code);

}
