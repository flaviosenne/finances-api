package com.project.finances.domain.repository;

import com.project.finances.app.dto.user.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserCommand {
    private final UserRepository repository;

    public void save(UserDto dto){
        dto.withPasswordHashed("hash");
        repository.save(UserDto.of(dto));
    }
}
