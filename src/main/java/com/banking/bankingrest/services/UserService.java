package com.banking.bankingrest.services;

import com.banking.bankingrest.entity.User;
import com.banking.bankingrest.enums.Roles;
import com.banking.bankingrest.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User register(String userName, String password, String email, String role) {
        UUID id = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();
        User user = new User(id, userName, password, email, role, now, now);
        repository.save(user);
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByUsername(username);
        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException(username);
        }
        return (UserDetails) User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .role(getRole(user))
                .build();
    }

    private String getRole(User user) {
        if (user.getRole().isEmpty()) {
            return Roles.USER.name();
        }
        return user.getRole();
    }
}
