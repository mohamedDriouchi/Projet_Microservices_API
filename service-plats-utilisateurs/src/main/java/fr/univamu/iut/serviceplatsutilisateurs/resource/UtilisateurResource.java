package fr.univamu.iut.serviceplatsutilisateurs.resource;

import fr.univamu.iut.serviceplatsutilisateurs.model.Utilisateur;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/utilisateurs")
@ApplicationScoped
public class UtilisateurResource {

    @Inject
    private UtilisateurRepositoryInterface userRepo;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Utilisateur> getAllUsers() {
        return userRepo.findAll();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserById(@PathParam("id") int id) {
        Utilisateur user = userRepo.findById(id);
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(user).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createUser(Utilisateur user) {
        if (userRepo.save(user)) {
            return Response.status(Response.Status.CREATED).entity(user).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteUser(@PathParam("id") int id) {
        if (userRepo.delete(id)) {
            return Response.noContent().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}