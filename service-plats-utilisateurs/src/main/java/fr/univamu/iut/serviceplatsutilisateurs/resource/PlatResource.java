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
        if (plat == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(plat).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createPlat(Plat plat) {
        if (platRepo.save(plat)) {
            return Response.status(Response.Status.CREATED).entity(plat).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deletePlat(@PathParam("id") int id) {
        if (platRepo.delete(id)) {
            return Response.noContent().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}