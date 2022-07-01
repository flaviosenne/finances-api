package com.project.finances.app.usecases.user.ports;

import com.project.finances.app.usecases.user.auth.dto.LoginDto;
import com.project.finances.app.usecases.user.auth.dto.ResponseLoginDto;

public interface AuthenticationProtocol {

    ResponseLoginDto login(LoginDto dto);
}
