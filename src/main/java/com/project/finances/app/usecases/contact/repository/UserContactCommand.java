package com.project.finances.app.usecases.contact.repository;

import com.project.finances.domain.entity.UserContact;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserContactCommand {
    private final UserContactRepository userContactRepository;

    public UserContact makeUserPublic(UserContact userContactToSave){
        return userContactRepository.save(userContactToSave);
    }
}
