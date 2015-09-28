package ch.vd.demaut.microbiz.rest.impl;

import java.io.IOException;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.cxf.rs.security.cors.CrossOriginResourceSharing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisation;
import ch.vd.demaut.domain.demandes.autorisation.Profession;
import ch.vd.demaut.microbiz.rest.RestUtils;
import ch.vd.demaut.services.demandes.autorisation.DemandeAutorisationService;

@CrossOriginResourceSharing(allowOrigins = { "*" }, allowCredentials = true, maxAge = 3600, allowHeaders = {
        "Content-Type", "X-Requested-With" }, exposeHeaders = { "Access-Control-Allow-Origin" })
@Service("demandeRestImpl")
@Path("/demande")
public class DemandeRestImpl {

    private static final Logger LOGGER = LoggerFactory.getLogger(DemandeRestImpl.class);

    @Inject
    private DemandeAutorisationService demandeAutorisationService;

    @GET
    @Path("/initialiser/{professionId}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER")
    public Response initialiser(@Context UriInfo uriInfo, @PathParam("professionId") String professionId)
            throws IOException {
        LOGGER.info("initialiser demande");

        DemandeAutorisation demande = demandeAutorisationService
                .initialiserDemandeAutorisation(Profession.Chiropraticien);

        return RestUtils.forgeResponseObject(demande.getReferenceDeDemande());
    }

}
