package org.acme.Service;

import java.util.List;

import org.acme.DTO.DeveloperDTO;
import org.acme.Models.Developer;
import org.acme.Models.User;
import org.acme.Repository.DeveloperRepository;
import org.eclipse.microprofile.jwt.JsonWebToken;

import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class DeveloperService {
    

    @Inject
    JsonWebToken jwt;

    @Inject
    UserService userService;

    private final DeveloperRepository developerRepository;
    public DeveloperService(DeveloperRepository developerRepository){
        this.developerRepository=developerRepository;
    }

    @Transactional
    public Developer getDeveloperById(Long id) {
        Developer developer=developerRepository.findById(id);
        if (developer == null) {
            throw new RuntimeException("Developer not found with id " + id);
        }
        return developerRepository.findById(id);
    }


    @Transactional
    public long getDevelopersSize() {
        User user = userService.findByUsername(jwt.getName());
        return developerRepository.find("user.id = ?1", Sort.by("id"), user.id).count();
    }

    @Transactional
    public List<Developer> getAllDevelopers(int page, int size) {
        User user = userService.findByUsername(jwt.getName());
        return developerRepository
            .find("user.id = ?1", Sort.by("id"), user.id)
            .page(Page.of(page, size))
            .list();
    }

    @Transactional
    public void addDeveloper(DeveloperDTO developer){
        User user = userService.findByUsername(jwt.getName());
        Developer newDeveloper = new Developer(developer.getName(), developer.getAge(), developer.getEndDate(), developer.getFullStack(), user);
        developerRepository.persist(newDeveloper);
    }

    @Transactional
    public Developer updateDeveloper(Long id, Developer updatedDeveloper) {
        Developer currentDeveloper = developerRepository.findById(id);
        if (currentDeveloper == null) {
            throw new RuntimeException("Developer not found with id " + id);
        }
        currentDeveloper.setName(updatedDeveloper.getName());
        currentDeveloper.setAge(updatedDeveloper.getAge());
        currentDeveloper.setEndDate(updatedDeveloper.getEndDate());
        currentDeveloper.setFullStack(updatedDeveloper.getFullStack());
        return currentDeveloper;
    }

    @Transactional
    public Developer deleteDeveloper(Long id) {
        Developer developer = developerRepository.findById(id);
        if (developer == null) {
            throw new RuntimeException("Developer not found with id " + id);
        }
        developerRepository.delete(developer);
        return developer;
    }

}
