package org.acme.Repository;

import org.acme.Models.Developer;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DeveloperRepository implements PanacheRepository<Developer> {
    
}
