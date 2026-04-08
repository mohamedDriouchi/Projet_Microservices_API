package fr.univamu.iut.serviceplatsutilisateurs.resource;

import fr.univamu.iut.serviceplatsutilisateurs.model.Plat;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
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

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPlatById(@PathParam("id") int id) {
        Plat plat = platRepo.findById(id);
        return (plat != null) ? Response.ok(plat).build() : Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createPlat(Plat plat) {
        if (platRepo.save(plat)) {
            return Response.status(Response.Status.CREATED).entity(plat).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updatePlat(@PathParam("id") int id, Plat plat) {
        plat.setId(id); // On force l'ID de l'URL dans l'objet
        // Note: il faudra ajouter la méthode update dans ton Repository plus tard
        // Pour l'instant, on peut réutiliser save si ta logique SQL gère le "ON DUPLICATE KEY UPDATE"
        if (platRepo.save(plat)) {
            return Response.ok().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deletePlat(@PathParam("id") int id) {
        return platRepo.delete(id) ? Response.noContent().build() : Response.status(Response.Status.NOT_FOUND).build();
    }
}