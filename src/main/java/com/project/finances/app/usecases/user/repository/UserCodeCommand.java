package com.project.finances.app.usecases.user.repository;

import com.project.finances.domain.entity.User;
import com.project.finances.domain.entity.UserCode;
import com.project.finances.app.usecases.user.utils.UserUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserCodeCommand {
    private final UserCodeRepository repository;

    public String save(User user){
        Optional<UserCode> userCodeOptional = repository.findByUserId(user.getId());

        userCodeOptional.ifPresent(this::invalidateCode);

        UserCode userCodeToSave = UserCode.builder()
                .user(user).isValid(true).build()
                .withCode(UserUtils.generateCode());

        UserCode userCodeSaved = repository.save(userCodeToSave);

        return userCodeSaved.getCode();
    }

    public UserCode invalidateCode(UserCode userCode){
        userCode.disableCode();
        return repository.save(userCode);
    }

}
