package org.acme.Repository;

import org.acme.Models.User;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserRepository {
    public User findByUsername(String username) {
        return User.find("username", username).firstResult();
    }

    public void save(User user) {
        user.persist();
    }

    public User updateURL(User user, String photoURL) {
        user.photoURL = photoURL;
        return user;
    }
}
