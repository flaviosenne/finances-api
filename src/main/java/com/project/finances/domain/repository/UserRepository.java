package com.project.finances.domain.repository;

import com.project.finances.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

interface UserRepository extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {
}
