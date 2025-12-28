package org.acme.ControllerREST;

import java.util.List;

import org.acme.DTO.DeveloperDTO;
import org.acme.DTO.DeveloperUpdateDTO;
import org.acme.DTO.PaginatedResponse;
import org.acme.Models.Developer;
import org.acme.Multiparts.MultiPartAvatar;
import org.acme.Service.DeveloperService;
import org.acme.Service.FileService;
import org.jboss.logging.Logger;
import org.jboss.resteasy.reactive.MultipartForm;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/developers")
@RolesAllowed({ "User" })
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DeveloperController {

    @Inject
    DeveloperService developerService;

    @Inject 
    FileService fileService;

    private static final Logger LOG = Logger.getLogger(DeveloperController.class);

    @POST
    @Path("/{id}/avatar")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response uploadAvatar(@MultipartForm MultiPartAvatar data, @PathParam("id") Long id) {
        LOG.info("here!!!");
        try {
            developerService.saveAvatar(data.avatar, id);
            return Response.ok("{\"status\": \"success\"}").build();
        }
        catch (Exception e) {
            String errorMsg = "Error: " + e.getClass().getName() + " - " + e.getMessage();
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"" + errorMsg + "\"}")
                    .build();
        }
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllDevelopers(
        @QueryParam("page") int page,
        @QueryParam("size") int size,
        @QueryParam("name") String name,
        @QueryParam("fullStack") Boolean fullStack) {
        LOG.info("Fetching developers: page=" + page + ", size=" + size + ", name=" + name + ", fullStack=" + fullStack);
        List<Developer> developers = developerService.getAllDevelopers(page, size, name, fullStack);
        PaginatedResponse<Developer> paginatedResponse = new PaginatedResponse<>(developers, developerService.getSizeDevelopers(page, size, name, fullStack));
        return Response.ok(paginatedResponse).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDeveloperById(@PathParam("id") Long id) {
        try{
            Developer developer = developerService.getDeveloperById(id);
            return Response.ok(developer).build();
        }catch(Exception e){
            return Response.status(Response.Status.NOT_FOUND)
                            .entity(e.getMessage())
                            .build();
        }
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addDeveloper(DeveloperDTO developer) {
        Developer addDeveloper = developerService.addDeveloper(developer);
        return Response.status(Response.Status.CREATED)
                .entity(addDeveloper)
                .build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateDeveloper(@PathParam("id") Long id, DeveloperUpdateDTO updatedDeveloper) {
        try {
            Developer developer = developerService.updateDeveloper(id, updatedDeveloper);
            return Response.ok(developer).build();
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteDeveloper(@PathParam("id") Long id) {
        try{
            Developer developer = developerService.deleteDeveloper(id);
            return Response.ok(developer).build();
        }catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        }
    }
}