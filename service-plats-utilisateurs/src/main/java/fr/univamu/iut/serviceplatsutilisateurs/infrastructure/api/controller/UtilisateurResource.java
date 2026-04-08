package fr.univamu.iut.serviceplatsutilisateurs.infrastructure.api.controller;

import fr.univamu.iut.serviceplatsutilisateurs.domain.model.Utilisateur;
import fr.univamu.iut.serviceplatsutilisateurs.domain.repository.UtilisateurRepositoryInterface;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

/**
 * Contrôleur REST pour la gestion des utilisateurs.
 * Agit comme un adaptateur entre l'API Web (JAX-RS) et la couche métier du domaine.
 * Fournit les endpoints pour les opérations CRUD sur les utilisateurs.
 *
 * @author Service Plats-Utilisateurs
 * @version 1.0
 */
@Path("/utilisateurs")
@RequestScoped
public class UtilisateurResource {

    /** Repository pour accéder et gérer les utilisateurs (injecté par CDI) */
    @Inject
    private UtilisateurRepositoryInterface userRepo;

    /**
     * Récupère la liste complète des utilisateurs.
     * Endpoint: GET /api/utilisateurs
     *
     * @return une liste JSON de tous les utilisateurs disponibles
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Utilisateur> getAllUsers() {
        return userRepo.findAll();
    }

    /**
     * Récupère un utilisateur spécifique par son identifiant.
     * Endpoint: GET /api/utilisateurs/{id}
     *
     * @param id l'identifiant de l'utilisateur à récupérer
     * @return l'utilisateur au format JSON si trouvé, erreur 404 sinon
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserById(@PathParam("id") int id) {
        Utilisateur user = userRepo.findById(id);
        return (user != null) ? Response.ok(user).build() : Response.status(Response.Status.NOT_FOUND).build();
    }

    /**
     * Crée un nouvel utilisateur.
     * Endpoint: POST /api/utilisateurs
     *
     * @param user l'objet utilisateur à créer (au format JSON)
     * @return une réponse 201 CREATED avec l'utilisateur créé, ou 400 BAD_REQUEST en cas d'erreur
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(Utilisateur user) {
        if (userRepo.save(user)) {
            return Response.status(Response.Status.CREATED).entity(user).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    /**
     * Met à jour un utilisateur existant.
     * Endpoint: PUT /api/utilisateurs/{id}
     *
     * @param id l'identifiant de l'utilisateur à mettre à jour
     * @param user l'objet utilisateur contenant les nouvelles données (au format JSON)
     * @return une réponse 200 OK en cas de succès, 404 NOT_FOUND si l'utilisateur n'existe pas
     */
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

    /**
     * Supprime un utilisateur par son identifiant.
     * Endpoint: DELETE /api/utilisateurs/{id}
     *
     * @param id l'identifiant de l'utilisateur à supprimer
     * @return une réponse 204 NO_CONTENT en cas de succès, 404 NOT_FOUND si l'utilisateur n'existe pas
     */
    @DELETE
    @Path("/{id}")
    public Response deleteUser(@PathParam("id") int id) {
        return userRepo.delete(id) ? Response.noContent().build() : Response.status(Response.Status.NOT_FOUND).build();
    }
}