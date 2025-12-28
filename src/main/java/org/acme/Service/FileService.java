package org.acme.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.jboss.logging.Logger;
import org.jboss.resteasy.reactive.multipart.FileUpload;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class FileService {

    private static final Logger LOG = Logger.getLogger(FileService.class);

    @Transactional
    public String processAndSaveFile(FileUpload file) throws IOException {

        LOG.info("Processing file: " + file.fileName());
        String originalFileName = file.fileName();
        String fileExtension = getFileExtension(originalFileName);
        String uniqueFileName = generateUniqueFileName(fileExtension);

        createUploadDirectory();
        
        String filePath = getUploadDir() + uniqueFileName;
        LOG.info("File path to save: " + filePath);
        saveFile(file, filePath);
        return uniqueFileName;

    }


    private String getFileExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf(".");
        if (lastDotIndex == -1) {
            return "";
        }
        return fileName.substring(lastDotIndex + 1);
    }

    private String generateUniqueFileName(String extension) {
        return UUID.randomUUID().toString() + "." + extension;
    }

    private String getUploadDir() {
        String dir = "/home/alex/mobile-data-service/public/";
        LOG.info("Upload directory: " + dir);
        return dir;
    }

    private void createUploadDirectory() {
        String uploadDir = getUploadDir();
        File uploadDirFile = new File(uploadDir);
        if (!uploadDirFile.exists()) {
            boolean created = uploadDirFile.mkdirs();
            LOG.info("Directory creation result: " + created + " for " + uploadDir);
            if (!created) {
                LOG.error("Failed to create upload directory: " + uploadDir);
            }
        } else {
            LOG.info("Upload directory already exists: " + uploadDir);
        }
    }


    private void saveFile(FileUpload file, String destinationPath) throws IOException {
        LOG.info("Saving file to: " + destinationPath);
        try {
            Path destination = Paths.get(destinationPath);
            LOG.info("Destination path object created: " + destination);
            LOG.info("Uploaded file: " + file.uploadedFile());
            
            Files.copy(file.uploadedFile(), destination, StandardCopyOption.REPLACE_EXISTING);
            
            LOG.info("File saved successfully!");
            LOG.info("File exists after save: " + Files.exists(destination));
        } catch (Exception e) {
            LOG.error("Error saving file: " + e.getMessage(), e);
            throw e;
        }
    }
}
