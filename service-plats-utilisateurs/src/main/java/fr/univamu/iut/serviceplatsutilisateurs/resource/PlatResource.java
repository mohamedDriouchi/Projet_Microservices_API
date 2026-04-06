package fr.univamu.iut.serviceplatsutilisateurs.resource;

import fr.univamu.iut.serviceplatsutilisateurs.model.Plat;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject; // ON UTILISE INJECT
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@Path("/plats")
@ApplicationScoped
public class PlatResource {

    @Inject
    private PlatRepositoryInterface platRepo;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Plat> getAllPlats() {
        return platRepo.findAll();
    }
}