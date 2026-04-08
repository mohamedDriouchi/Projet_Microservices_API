package fr.univamu.iut.serviceplatsutilisateurs.infrastructure.api.controller;

import fr.univamu.iut.serviceplatsutilisateurs.domain.model.Plat;
import fr.univamu.iut.serviceplatsutilisateurs.domain.repository.PlatRepositoryInterface;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

/**
 * Contrôleur REST pour la gestion des plats.
 * Agit comme un adaptateur entre l'API Web (JAX-RS) et la couche métier du domaine.
 * Fournit les endpoints pour les opérations CRUD sur les plats.
 *
 * @author Service Plats-Utilisateurs
 * @version 1.0
 */
@Path("/plats")
@RequestScoped
public class PlatResource {

    /** Repository pour accéder et gérer les plats (injecté par CDI) */
    @Inject
    private PlatRepositoryInterface platRepo;

    /**
     * Récupère la liste complète des plats.
     * Endpoint: GET /api/plats
     *
     * @return une liste JSON de tous les plats disponibles
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Plat> getAllPlats() {
        return platRepo.findAll();
    }

    /**
     * Récupère un plat spécifique par son identifiant.
     * Endpoint: GET /api/plats/{id}
     *
     * @param id l'identifiant du plat à récupérer
     * @return le plat au format JSON si trouvé, erreur 404 sinon
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPlatById(@PathParam("id") int id) {
        Plat plat = platRepo.findById(id);
        return (plat != null) ? Response.ok(plat).build() : Response.status(Response.Status.NOT_FOUND).build();
    }

    /**
     * Crée un nouveau plat.
     * Endpoint: POST /api/plats
     *
     * @param plat l'objet plat à créer (au format JSON)
     * @return une réponse 201 CREATED avec le plat créé, ou 400 BAD_REQUEST en cas d'erreur
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createPlat(Plat plat) {
        if (platRepo.save(plat)) {
            return Response.status(Response.Status.CREATED).entity(plat).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    /**
     * Met à jour un plat existant.
     * Endpoint: PUT /api/plats/{id}
     *
     * @param id l'identifiant du plat à mettre à jour
     * @param plat l'objet plat contenant les nouvelles données (au format JSON)
     * @return une réponse 200 OK en cas de succès, 404 NOT_FOUND si le plat n'existe pas
     */
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updatePlat(@PathParam("id") int id, Plat plat) {
        plat.setId(id); // Force l'ID de l'URL dans l'objet
        if (platRepo.save(plat)) {
            return Response.ok().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    /**
     * Supprime un plat par son identifiant.
     * Endpoint: DELETE /api/plats/{id}
     *
     * @param id l'identifiant du plat à supprimer
     * @return une réponse 204 NO_CONTENT en cas de succès, 404 NOT_FOUND si le plat n'existe pas
     */
    @DELETE
    @Path("/{id}")
    public Response deletePlat(@PathParam("id") int id) {
        return platRepo.delete(id) ? Response.noContent().build() : Response.status(Response.Status.NOT_FOUND).build();
    }
}