package fr.univamu.iut.servicemenus.infrastructure.api.controller;

import fr.univamu.iut.servicemenus.domain.model.Menu;
import fr.univamu.iut.servicemenus.domain.repository.MenuRepositoryInterface;
import fr.univamu.iut.servicemenus.domain.service.MenuService;
import fr.univamu.iut.servicemenus.infrastructure.persistence.mysql.MenuRepositoryMysql;
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

    /** Repository pour accéder et gérer les menus */
    private MenuRepositoryInterface menuRepo;

    /** Service métier pour la gestion des menus */
    private MenuService menuService;

    /**
     * Constructeur - initialise les dépendances
     */
    public MenuResource() {
        try {
            // Charger la configuration
            String url = "jdbc:mysql://mysql-mohamed-d.alwaysdata.net/mohamed-d_projet_api?useSSL=false&serverTimezone=UTC";
            String user = "mohamed-d_api";
            String pwd = "projet-api";

            this.menuRepo = new MenuRepositoryMysql(url, user, pwd);
            this.menuService = new MenuService();
        } catch (Exception e) {
            System.err.println("Erreur d'initialisation: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Récupère la liste complète des menus.
     * Endpoint: GET /api/menus
     *
     * @return une liste JSON de tous les menus disponibles
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Menu> getAllMenus() {
        List<Menu> menus = menuRepo.findAll();
        // Enrichir chaque menu avec les données des plats
        menus.forEach(menu -> menuService.enrichirMenu(menu));
        return menus;
    }

    /**
     * Récupère un menu spécifique par son identifiant.
     * Endpoint: GET /api/menus/{id}
     *
     * @param id l'identifiant du menu à récupérer
     * @return le menu au format JSON si trouvé (enrichi avec les données des plats), erreur 404 sinon
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMenuById(@PathParam("id") int id) {
        Menu menu = menuRepo.findById(id);
        if (menu != null) {
            menuService.enrichirMenu(menu);
            return Response.ok(menu).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    /**
     * Crée un nouveau menu.
     * Endpoint: POST /api/menus
     *
     * @param menu l'objet menu à créer (au format JSON)
     * @return une réponse 201 CREATED avec le menu créé (enrichi), ou 400 BAD_REQUEST en cas d'erreur
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createMenu(Menu menu) {
        // Valider le menu
        if (!menuService.validerMenu(menu)) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"erreur\":\"Menu invalide (nom, créateur requis, et au moins un plat)\"}").build();
        }

        // Préparer le menu pour la création (timestamps)
        menuService.preparerMenuPourCreation(menu);

        // Enrichir et sauvegarder
        menuService.enrichirMenu(menu);
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
     * @return une réponse 200 OK avec le menu mis à jour (enrichi), 404 NOT_FOUND si le menu n'existe pas
     */
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateMenu(@PathParam("id") int id, Menu menu) {
        Menu menuExistant = menuRepo.findById(id);
        if (menuExistant == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        menu.setId(id); // Force l'ID de l'URL dans l'objet

        // Valider le menu
        if (!menuService.validerMenu(menu)) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"erreur\":\"Menu invalide (nom, créateur requis, et au moins un plat)\"}").build();
        }

        // Préparer pour la mise à jour (mettre à jour dateMaj)
        menu.setDateCreation(menuExistant.getDateCreation()); // Conserver la date de création originale
        menuService.preparerMenuPourMaj(menu);

        // Enrichir et sauvegarder
        menuService.enrichirMenu(menu);
        if (menuRepo.save(menu)) {
            return Response.ok(menu).build();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
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

