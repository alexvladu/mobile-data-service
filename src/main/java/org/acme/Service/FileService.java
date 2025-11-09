package org.acme.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.jboss.resteasy.reactive.multipart.FileUpload;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class FileService {

    @Transactional
    public String processAndSaveFile(FileUpload file) throws IOException {

        String originalFileName = file.fileName();
        String fileExtension = getFileExtension(originalFileName);
        String uniqueFileName = generateUniqueFileName(fileExtension);

        createUploadDirectory();
        
        String filePath = UPLOAD_DIR + uniqueFileName;
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

    private static final String UPLOAD_DIR = "src/main/resources/META-INF/resources/public/";

    private void createUploadDirectory() {
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            boolean created = uploadDir.mkdirs();
            if (!created) {
                System.err.println("Failed to create upload directory: " + UPLOAD_DIR);
            }
        }
    }


    private void saveFile(FileUpload file, String destinationPath) throws IOException {
        Path destination = Paths.get(destinationPath);
        Files.copy(file.uploadedFile(), destination, StandardCopyOption.REPLACE_EXISTING);
    }
}
