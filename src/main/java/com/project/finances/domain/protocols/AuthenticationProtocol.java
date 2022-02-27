package com.project.finances.domain.protocols;

import com.project.finances.domain.usecases.user.dto.LoginDto;
import com.project.finances.domain.usecases.user.dto.ResponseLoginDto;

public interface AuthenticationProtocol {

    ResponseLoginDto login(LoginDto dto);
}
