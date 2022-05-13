package com.project.finances.app.usecases.contact.repository;

import com.project.finances.domain.entity.ContactInvite;
import com.project.finances.domain.entity.UserContact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

interface ContactInviteRepository extends JpaRepository<ContactInvite, String> {

    @Query("select c from ContactInvite c join c.userReceive u where u.id = :userInviteReceiveId and c.id = :id")
    Optional<ContactInvite> findByIdAndByUserReceiveInviteId(String id, String userInviteReceiveId);


    @Query("select c from ContactInvite c join c.userReceive u " +
            "where u.id = :userInviteReceiveId and c.id = :id " +
            "and c.status = 'PENDING' ")
    Optional<ContactInvite> findByIdAndByUserReceiveInviteIdAndStatusPending(String id, String userInviteReceiveId);


    @Query("select c from ContactInvite c " +
            "join c.userRequest sendInvite " +
            "join c.userReceive receiveInvite " +
            "where c.status = 'ACCEPT' " +
            "and  (sendInvite.id = :userContactId OR receiveInvite.id = :userContactId) ")
    List<ContactInvite> getContacts(String userContactId);

    @Query("select c from ContactInvite c " +
            "join c.userRequest sendInvite " +
            "join c.userReceive receiveInvite " +
            "where (sendInvite.id = :userSendInviteId and receiveInvite.id = :userReceiveInviteId) OR " +
            "(sendInvite.id = :userReceiveInviteId and receiveInvite.id = :userSendInviteId)")
    Optional<ContactInvite> findByUserSendInviteIdAndUserReceiveInviteId(String userSendInviteId, String userReceiveInviteId);

    @Query("select c from ContactInvite c " +
            "join c.userReceive receiveInvite " +
            "join receiveInvite.user user " +
            "where user.id = :userReceiveInviteId")
    List<ContactInvite> findAllContactsByStatusPending(String userReceiveInviteId);
}
