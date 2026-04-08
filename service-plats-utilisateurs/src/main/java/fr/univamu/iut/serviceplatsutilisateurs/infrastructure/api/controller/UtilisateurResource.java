package fr.univamu.iut.serviceplatsutilisateurs.infrastructure.api.controller;

import fr.univamu.iut.serviceplatsutilisateurs.domain.model.Utilisateur;
import fr.univamu.iut.serviceplatsutilisateurs.domain.repository.UtilisateurRepositoryInterface;
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
        return (user != null) ? Response.ok(user).build() : Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(Utilisateur user) {
        if (userRepo.save(user)) {
            return Response.status(Response.Status.CREATED).entity(user).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateUser(@PathParam("id") int id, Utilisateur user) {
        user.setId(id);
        if (userRepo.save(user)) {
            return Response.ok().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteUser(@PathParam("id") int id) {
        return userRepo.delete(id) ? Response.noContent().build() : Response.status(Response.Status.NOT_FOUND).build();
    }
}