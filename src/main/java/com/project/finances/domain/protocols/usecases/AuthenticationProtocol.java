package com.project.finances.domain.protocols.usecases;

import com.project.finances.app.usecases.user.dto.LoginDto;
import com.project.finances.app.usecases.user.dto.ResponseLoginDto;

public interface AuthenticationProtocol {

    ResponseLoginDto login(LoginDto dto);
}
