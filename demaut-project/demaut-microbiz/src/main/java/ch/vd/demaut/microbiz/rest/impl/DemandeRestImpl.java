package ch.vd.demaut.microbiz.rest.impl;

import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisation;
import ch.vd.demaut.domain.demandes.autorisation.Profession;
import ch.vd.demaut.microbiz.rest.RestUtils;
import ch.vd.demaut.services.demandes.autorisation.DemandeAutorisationService;
import org.apache.cxf.rs.security.cors.CrossOriginResourceSharing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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
import java.io.IOException;

@CrossOriginResourceSharing(allowOrigins = {"*"}, allowCredentials = true, maxAge = 3600, allowHeaders = {
        "Content-Type", "X-Requested-With"}, exposeHeaders = {"Access-Control-Allow-Origin"})
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
    public Response initialiser(@Context UriInfo uriInfo, @PathParam("professionId") String professionIdStr)
            throws IOException {
        LOGGER.info("initialiser demande professionId " + professionIdStr);

        Integer professionId = Integer.valueOf(professionIdStr);
        Profession profession = Profession.getTypeById(professionId);

        DemandeAutorisation demande = demandeAutorisationService.initialiserDemandeAutorisation(profession);

        return RestUtils.forgeResponseObject(demande.getReferenceDeDemande());
    }

}
