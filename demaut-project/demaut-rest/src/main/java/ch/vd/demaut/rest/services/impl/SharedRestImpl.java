package ch.vd.demaut.rest.services.impl;

import ch.vd.demaut.domain.demandeur.Pays;
import ch.vd.demaut.domain.demandeur.donneesPerso.Langue;
import ch.vd.demaut.rest.json.commons.RestUtils;
import org.apache.cxf.rs.security.cors.CrossOriginResourceSharing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;
import java.util.Arrays;
import java.util.List;

/**
 * API Rest des services "partagés", l'ensemble des services disponibles pour toute l'application.
 *
 * Pas de métier dépendant d'un type de donnees
 *
 */
@CrossOriginResourceSharing(allowAllOrigins = true)
@Path("/shared")
public class SharedRestImpl {

    private static final Logger LOGGER = LoggerFactory.getLogger(SharedRestImpl.class);

    @GET
    @Path("/langues")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER")
    public Response listerLesLangues() throws Exception {

        LOGGER.info("listerLesLangues");

        return RestUtils.buildJSonResponse(Arrays.asList(Langue.values()));
    }

    @GET
    @Path("/paysList")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER")
    public Response listerLesPays() throws Exception {

        LOGGER.info("listerLesPays");

        // Alternative:
        List<Pays> paysList = buildListePaysSansProgresSOA();

        return RestUtils.buildJSonResponse(paysList);
    }

    private List<Pays> buildListePaysSansProgresSOA() {
        return Arrays.asList(Pays.values());
    }

}
