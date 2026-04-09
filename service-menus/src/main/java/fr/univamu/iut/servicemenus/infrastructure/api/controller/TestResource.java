package fr.univamu.iut.servicemenus.infrastructure.api.controller;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

/**
 * Endpoint simple de test pour vérifier que JAX-RS fonctionne.
 */
@Path("/test")
public class TestResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String test() {
        return "✅ Service Menus API - Test OK !";
    }
}

