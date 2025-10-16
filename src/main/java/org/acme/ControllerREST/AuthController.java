package org.acme.ControllerREST;

import org.acme.Service.AuthService;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthController {

    @Inject
    private AuthService authService;

    public static class AuthRequest {
        public String username;
        public String password;
    }

    @POST
    @Path("/register")
    public Response register(AuthRequest req) {
        authService.register(req.username, req.password, "User");
        return Response.status(Response.Status.CREATED)
                .entity(req.username)
                .build();
    }

    @POST
    @Path("/login")
    public Response login(AuthRequest req) {
        String token = authService.login(req.username, req.password);
        return Response.ok().entity("{\"token\": \"" + token + "\"}").build();
    }

}
