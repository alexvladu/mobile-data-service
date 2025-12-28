package org.acme.ControllerREST;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;

@Path("/public")
public class FileController {

    private static final String UPLOAD_DIR = "public/";

    @GET
    @Path("/{fileName}")
    public Response getFile(@PathParam("fileName") String fileName) {
        try {
            // Prevent directory traversal attacks
            if (fileName.contains("..") || fileName.contains("/")) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }

            java.nio.file.Path filePath = Paths.get(UPLOAD_DIR, fileName);
            File file = filePath.toFile();

            if (!file.exists()) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }

            byte[] fileContent = Files.readAllBytes(filePath);
            String contentType = getContentType(fileName);
            return Response.ok(fileContent)
                    .header("Content-Disposition", "inline; filename=\"" + fileName + "\"")
                    .header("Content-Type", contentType)
                    .build();

        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    private String getContentType(String fileName) {
        if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) {
            return "image/jpeg";
        } else if (fileName.endsWith(".png")) {
            return "image/png";
        } else if (fileName.endsWith(".gif")) {
            return "image/gif";
        } else if (fileName.endsWith(".pdf")) {
            return "application/pdf";
        } else if (fileName.endsWith(".txt")) {
            return "text/plain";
        } else {
            return "application/octet-stream";
        }
    }
}
