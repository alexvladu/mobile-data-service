package org.acme.Service;

import org.acme.Models.User;

import at.favre.lib.crypto.bcrypt.BCrypt;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class AuthService {
    @Transactional
    public String register(String username, String password, String role) {
        User user = new User();
        user.username=username;
        user.password = BCrypt.withDefaults().hashToString(12, password.toCharArray());
        user.role = (role != null) ? role : "User";
        user.persist();

        return Jwt.issuer("https://127.0.0.1")
        .upn(user.username)
        .groups(user.role)
        .sign();
    }

    public String login(String username, String password) {
        User user = User.find("username", username).firstResult();
        if (user == null) {
            throw new WebApplicationException("User inexistent", Response.Status.UNAUTHORIZED);
        }
        var result = BCrypt.verifyer().verify(password.toCharArray(), user.password);
        if (!result.verified) {
            throw new WebApplicationException("Parolă invalidă", Response.Status.UNAUTHORIZED);
        }

        return Jwt.issuer("https://127.0.0.1")
                .upn(user.username)
                .groups(user.role)
                .sign();
    }
}