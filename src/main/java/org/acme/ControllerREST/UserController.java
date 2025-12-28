package org.acme.ControllerREST;

import org.acme.Multiparts.MultiPartAvatar;
import org.acme.Service.UserService;
import org.jboss.resteasy.reactive.MultipartForm;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/users")
@RolesAllowed({ "User" })
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserController {

    @Inject
    UserService userService;

    @POST
    @Path("/avatar")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response uploadAvatar(@MultipartForm MultiPartAvatar data) {
        try {
            userService.saveAvatar(data.avatar);
            return Response.ok("Received").build();
        }
        catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    @GET
    @Path("/avatar")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAvatar() {
        try {
            String avatarURL = userService.getAvatarURL();
            return Response.ok("{\"photoURL\": \"" + avatarURL + "\"}").build();
        }
        catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }
}
