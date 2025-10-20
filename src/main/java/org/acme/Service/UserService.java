package org.acme.Service;

import org.acme.Models.User;
import org.acme.Repository.UserRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class UserService {

    @Inject
    UserRepository userRepository;

    User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
