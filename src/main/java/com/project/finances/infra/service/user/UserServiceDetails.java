package com.project.finances.infra.service.user;

import com.project.finances.domain.usecases.user.repository.UserQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.project.finances.domain.exception.messages.MessagesException.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class UserServiceDetails implements UserDetailsService {

    private final UserQuery userQuery;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userQuery.findByUsername(email).orElseThrow(
                () -> new UsernameNotFoundException(USER_NOT_FOUND));
    }
}
