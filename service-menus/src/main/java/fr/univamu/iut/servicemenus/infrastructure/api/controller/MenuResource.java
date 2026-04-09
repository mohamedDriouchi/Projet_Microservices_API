package fr.univamu.iut.servicemenus.infrastructure.api.controller;

import fr.univamu.iut.servicemenus.domain.model.Menu;
import fr.univamu.iut.servicemenus.domain.repository.MenuRepositoryInterface;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

/**
 * Contrôleur REST pour la gestion des menus.
 * Agit comme un adaptateur entre l'API Web (JAX-RS) et la couche métier du domaine.
 * Fournit les endpoints pour les opérations CRUD sur les menus.
 *
 * @author Service Menus
 * @version 1.0
 */
@Path("/menus")
@RequestScoped
public class MenuResource {

    /** Repository pour accéder et gérer les menus (injecté par CDI) */
    @Inject
    private MenuRepositoryInterface menuRepo;

    /**
     * Récupère la liste complète des menus.
     * Endpoint: GET /api/menus
     *
     * @return une liste JSON de tous les menus disponibles
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Menu> getAllMenus() {
        return menuRepo.findAll();
    }

    /**
     * Récupère un menu spécifique par son identifiant.
     * Endpoint: GET /api/menus/{id}
     *
     * @param id l'identifiant du menu à récupérer
     * @return le menu au format JSON si trouvé, erreur 404 sinon
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMenuById(@PathParam("id") int id) {
        Menu menu = menuRepo.findById(id);
        return (menu != null) ? Response.ok(menu).build() : Response.status(Response.Status.NOT_FOUND).build();
    }

    /**
     * Crée un nouveau menu.
     * Endpoint: POST /api/menus
     *
     * @param menu l'objet menu à créer (au format JSON)
     * @return une réponse 201 CREATED avec le menu créé, ou 400 BAD_REQUEST en cas d'erreur
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createMenu(Menu menu) {
        if (menuRepo.save(menu)) {
            return Response.status(Response.Status.CREATED).entity(menu).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    /**
     * Met à jour un menu existant.
     * Endpoint: PUT /api/menus/{id}
     *
     * @param id l'identifiant du menu à mettre à jour
     * @param menu l'objet menu contenant les nouvelles données (au format JSON)
     * @return une réponse 200 OK en cas de succès, 404 NOT_FOUND si le menu n'existe pas
     */
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateMenu(@PathParam("id") int id, Menu menu) {
        menu.setId(id); // Force l'ID de l'URL dans l'objet
        if (menuRepo.save(menu)) {
            return Response.ok().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    /**
     * Supprime un menu par son identifiant.
     * Endpoint: DELETE /api/menus/{id}
     *
     * @param id l'identifiant du menu à supprimer
     * @return une réponse 204 NO_CONTENT en cas de succès, 404 NOT_FOUND si le menu n'existe pas
     */
    @DELETE
    @Path("/{id}")
    public Response deleteMenu(@PathParam("id") int id) {
        return menuRepo.delete(id) ? Response.noContent().build() : Response.status(Response.Status.NOT_FOUND).build();
    }
}

