package com.project.finances.domain.usecases.contact.repository;

import com.project.finances.domain.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

interface ContactRepository extends JpaRepository<Contact, String> {

    @Query("select c from Contact c join c.userReceive u where u.id = :userInviteReceiveId and c.id = :id")
    Optional<Contact> findByIdAndByUserReceiveInviteId(String id, String userInviteReceiveId);


    @Query("select c from Contact c join c.userReceive u " +
            "where u.id = :userInviteReceiveId and c.id = :id " +
            "and c.status = 'PENDING' ")
    Optional<Contact> findByIdAndByUserReceiveInviteIdAndStatusPending(String id, String userInviteReceiveId);


    @Query("select c from Contact c join c.userReceive u " +
            "where c.status = 'ACCEPT' " +
            "and u.id = :userContactId")
    List<Contact> getContacts(String userContactId);

    @Query("select c from Contact c " +
            "join c.userRequest sendInvite " +
            "join c.userReceive receiveInvite " +
            "where (sendInvite.id = :userSendInviteId and receiveInvite.id = :userReceiveInviteId) OR " +
            "(sendInvite.id = :userReceiveInviteId and receiveInvite.id = :userSendInviteId)")
    Optional<Contact> findByUserSendInviteIdAndUserReceiveInviteId(String userSendInviteId, String userReceiveInviteId);

    @Query("select c from Contact c " +
            "join c.userReceive receiveInvite " +
            "join receiveInvite.user user " +
            "where user.id = :userReceiveInviteId")
    List<Contact> findAllContactsByStatusPending(String userReceiveInviteId);
}
