package com.project.finances.domain.usecases.contact.repository;

import com.project.finances.domain.entity.UserContact;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserContactQuery {
    private final UserContactRepository userContactRepository;

    public boolean userIsPublic(String userId){
        return this.getUserContact(userId).isPresent();
    }

    public Optional<UserContact> getUserContact(String userId){
        return userContactRepository.findByUserId(userId);
    }

    public List<UserContact> searchUsers(String username){
        return userContactRepository.search(username);
    }
}
