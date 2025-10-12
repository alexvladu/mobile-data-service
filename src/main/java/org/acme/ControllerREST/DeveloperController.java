package org.acme.ControllerREST;

import java.util.List;

import org.acme.Models.Developer;
import org.acme.Service.DeveloperService;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/developers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DeveloperController {

    @Inject
    DeveloperService developerService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllDevelopers() {
        List<Developer> developers = developerService.getAllDevelopers();
        return Response.ok(developers).build();
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
    public Response addDeveloper(Developer developer) {
        developerService.addDeveloper(developer);
        return Response.status(Response.Status.CREATED)
                .entity(developer)
                .build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateDeveloper(@PathParam("id") Long id, Developer updatedDeveloper) {
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