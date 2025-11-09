package org.acme.Service;

import org.acme.Models.User;
import org.acme.Repository.UserRepository;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.resteasy.reactive.multipart.FileUpload;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class UserService {

    @Inject
    UserRepository userRepository;

    @Inject
    FileService fileService;

    @Inject
    JsonWebToken jwt;

    public User getCurrentUser(){
        User user=userRepository.findByUsername(jwt.getName());
        if(user==null){
            throw new RuntimeException("Not Authentificated");
        }
        return user;
    }

    @Transactional
    public void saveAvatar(FileUpload file) throws Exception {
        User user = getCurrentUser();
        String photoURL = fileService.processAndSaveFile(file);
        this.userRepository.updateURL(user, photoURL);
    }

    public String getAvatarURL() throws Exception {
        User user = getCurrentUser();
        return getPhotoURL(user);
    }


    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public String getPhotoURL(User user) {
        return user.photoURL;
    }

    public User updatePhotoURL(User user, String photoURL) {
        return userRepository.updateURL(user, photoURL);
    }
}
