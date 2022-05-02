package com.project.finances.domain.usecases.contact.repository;

import com.project.finances.domain.entity.Contact;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ContactQuery {
    private final ContactRepository contactRepository;

    public Optional<Contact> findById(String id, String contactUserReceiveInviteId){
        return contactRepository.findByIdAndByUserReceiveInviteId(id, contactUserReceiveInviteId);
    }

    public List<Contact> getContacts(String userContactId) {
        return contactRepository.getContacts(userContactId);
    }
}
