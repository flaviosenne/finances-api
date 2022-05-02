package com.project.finances.domain.usecases.contact.repository;

import com.project.finances.domain.entity.User;
import com.project.finances.domain.entity.UserContact;
import com.project.finances.domain.exception.BadRequestException;
import com.project.finances.domain.usecases.contact.dto.MakeUserPublicDto;
import com.project.finances.domain.usecases.user.repository.UserQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.project.finances.domain.exception.messages.MessagesException.USER_ALREADY_PUBLIC;
import static com.project.finances.domain.exception.messages.MessagesException.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class UserContactCommand {
    private final UserQuery userQuery;
    private final UserContactRepository userContactRepository;

    public UserContact makeUserPublic(String userId, MakeUserPublicDto dto){
        User user = userQuery.findByIdIsActive(userId).orElseThrow(()-> new BadRequestException(USER_NOT_FOUND));

        if(userContactRepository.findByUserId(user.getId()).isPresent()) throw  new BadRequestException(USER_ALREADY_PUBLIC);

        UserContact userContactToSave = UserContact.builder()
                .user(user)
                .avatar(dto.getAvatar())
                .username(dto.getUsername())
                .build();

        return userContactRepository.save(userContactToSave);
    }
}
