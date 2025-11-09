package org.acme.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "app_user")
public class User extends PanacheEntity{
    @Column(unique = true)
    public String username;

    @JsonIgnore
    public String password;

    @JsonIgnore
    public String role;

    @JsonIgnore
    public String photoURL;

    public User(){

    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", role='" + role + '\'' +
                ", photoURL='" + photoURL + '\'' +
                '}';
    }
}