package com.project.finances.domain.usecases.contact.repository;

import com.project.finances.domain.entity.ContactInvite;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ContactInviteQuery {
    private final ContactInviteRepository contactInviteRepository;

    public Optional<ContactInvite> findById(String id, String contactUserReceiveInviteId){
        return contactInviteRepository.findByIdAndByUserReceiveInviteId(id, contactUserReceiveInviteId);
    }

    public Optional<ContactInvite> findByIdAndStatusPending(String id, String contactUserReceiveInviteId){
        return contactInviteRepository.findByIdAndByUserReceiveInviteIdAndStatusPending(id, contactUserReceiveInviteId);
    }

    public List<ContactInvite> getContacts(String userContactId) {
        return contactInviteRepository.getContacts(userContactId);
    }

    public List<ContactInvite> listInvitesPending(String userReceiveInviteId) {
        return contactInviteRepository.findAllContactsByStatusPending(userReceiveInviteId);
    }

    public boolean alreadyExistsInvite(String userSendInviteId, String userReceiveInviteId){
        return contactInviteRepository.findByUserSendInviteIdAndUserReceiveInviteId(userSendInviteId, userReceiveInviteId).isPresent();
    }
}
