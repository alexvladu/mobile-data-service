package org.acme.Service;

import java.util.ArrayList;
import java.util.List;

import org.acme.DTO.DeveloperDTO;
import org.acme.DTO.DeveloperUpdateDTO;
import org.acme.Models.Developer;
import org.acme.Models.User;
import org.acme.Repository.DeveloperRepository;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.resteasy.reactive.multipart.FileUpload;

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

    @Inject
    FileService fileService;

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
    public long getSizeDevelopers(int page, int size, String name, Boolean fullStack) {
        User user = userService.findByUsername(jwt.getName());
        String query = "user.id = ?1";
        List<Object> params = new ArrayList<>();
        params.add(user.id);

        int idx = 2;
        if (name != null && !name.isBlank()) {
            query += " and lower(name) like ?" + idx++;
            params.add("%" + name.toLowerCase() + "%");
        }
        if (fullStack != null) {
            query += " and fullStack = ?" + idx++;
            params.add(fullStack);
        }

        return developerRepository
                .find(query, Sort.by("id"), params.toArray())
                .page(Page.of(page, size))
                .count();
    }

    @Transactional
    public List<Developer> getAllDevelopers(int page, int size, String name, Boolean fullStack) {
        User user = userService.findByUsername(jwt.getName());
        String query = "user.id = ?1";
        List<Object> params = new ArrayList<>();
        params.add(user.id);

        int idx = 2;
        if (name != null && !name.isBlank()) {
            query += " and lower(name) like ?" + idx++;
            params.add("%" + name.toLowerCase() + "%");
        }
        if (fullStack != null) {
            query += " and fullStack = ?" + idx++;
            params.add(fullStack);
        }

        return developerRepository
                .find(query, Sort.by("id"), params.toArray())
                .page(Page.of(page, size))
                .list();
    }

    @Transactional
    public void addDeveloper(DeveloperDTO developer){
        User user = userService.findByUsername(jwt.getName());
        Developer newDeveloper = new Developer(developer.getName(), developer.getAge(), developer.getEndDate(), developer.getFullStack(), null, null, null, user);
        developerRepository.persist(newDeveloper);
    }

    @Transactional
    public Developer updateDeveloper(Long id, DeveloperUpdateDTO updatedDeveloper) {
        Developer currentDeveloper = developerRepository.findById(id);
        if (currentDeveloper == null) {
            throw new RuntimeException("Developer not found with id " + id);
        }
        currentDeveloper.setName(updatedDeveloper.getName());
        currentDeveloper.setAge(updatedDeveloper.getAge());
        currentDeveloper.setEndDate(updatedDeveloper.getEndDate());
        currentDeveloper.setFullStack(updatedDeveloper.getFullStack());
        currentDeveloper.setLat(updatedDeveloper.getLat());
        currentDeveloper.setLng(updatedDeveloper.getLng());
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


    @Transactional
    public void saveAvatar(FileUpload file, Long id) throws Exception {
        Developer developer = getDeveloperById(id);
        String photoURL = fileService.processAndSaveFile(file);
        this.developerRepository.updateURL(developer, photoURL);
    }
}
