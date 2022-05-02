package com.project.finances.domain.usecases.contact.repository;

import com.project.finances.domain.entity.UserContact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

interface UserContactRepository extends JpaRepository<UserContact, String> {
    @Query("select c from UserContact c join c.user u where u.id = :userId")
    Optional<UserContact> findByUserId(String userId);

    @Query("select c from UserContact c where c.username like %:username%")
    List<UserContact> search(String username);
}
