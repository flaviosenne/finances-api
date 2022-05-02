package com.project.finances.domain.usecases.contact.repository;

import com.project.finances.domain.entity.Contact;
import com.project.finances.domain.entity.UserContact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

interface ContactRepository extends JpaRepository<Contact, String> {

    @Query("select c from Contact c join c.userReceive u where u.id = :userInviteReceiveId and c.id = :id")
    Optional<Contact> findByIdAndByUserReceiveInviteId(String id, String userInviteReceiveId);

    @Query("select c from Contact c join c.userReceive u " +
            "where c.status = StatusInvite.ACCEPT " +
            "and u.id = :userContactId")
    List<Contact> getContacts(String userContactId);
}
