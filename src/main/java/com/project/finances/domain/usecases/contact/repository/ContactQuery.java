package com.project.finances.domain.usecases.contact.repository;

import com.project.finances.domain.entity.Contact;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ContactQuery {
    private final ContactRepository contactRepository;

    public Optional<Contact> findById(String id, String contactUserReceiveInviteId){
        return contactRepository.findByIdAndByUserReceiveInviteId(id, contactUserReceiveInviteId);
    }
}
