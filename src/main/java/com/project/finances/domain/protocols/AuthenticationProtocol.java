package com.project.finances.domain.protocols;

import com.project.finances.app.dto.LoginDto;
import com.project.finances.app.dto.ResponseLoginDto;

public interface AuthenticationProtocol {

    ResponseLoginDto login(LoginDto dto);
}
