package org.acme.Service;

import java.util.List;

import org.acme.Models.Developer;
import org.acme.Repository.DeveloperRepository;

import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class DeveloperService {
    
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
    public List<Developer> getAllDevelopers() {
        return developerRepository.findAll(Sort.by("id")).list();
    }

    @Transactional
    public void addDeveloper(Developer developer){
        developerRepository.persist(developer);
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
