package fr.univamu.iut.serviceplatsutilisateurs.resource;

import fr.univamu.iut.serviceplatsutilisateurs.model.Utilisateur;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
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
}