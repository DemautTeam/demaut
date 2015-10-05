package ch.vd.demaut.microbiz.rest.impl;

import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisation;
import ch.vd.demaut.domain.demandes.autorisation.Profession;
import ch.vd.demaut.domain.demandeur.donneesProf.CodeGLN;
import ch.vd.demaut.microbiz.rest.RestUtils;
import ch.vd.demaut.services.demandes.autorisation.DemandeAutorisationService;
import org.apache.cxf.rs.security.cors.CrossOriginResourceSharing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.io.IOException;

@CrossOriginResourceSharing(allowAllOrigins = true)
@Service("demandeRestImpl")
@Path("/demande")
public class DemandeRestImpl {

    private static final Logger LOGGER = LoggerFactory.getLogger(DemandeRestImpl.class);

    @Autowired
    private DemandeAutorisationService demandeAutorisationService;

    @GET
    @Path("/initialiser")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER")
    public Response initialiser(@Context UriInfo uriInfo,
                                @QueryParam("professionId") String professionIdStr,
                                @QueryParam("codeGln") String codeGlnStr) throws IOException {
        LOGGER.info("initialiser demande professionId " + professionIdStr);

        Integer professionId = Integer.valueOf(professionIdStr);
        Profession profession = Profession.getTypeById(professionId);
        CodeGLN codeGLN = null;
        if (!StringUtils.isEmpty(codeGlnStr)) {
            codeGLN = new CodeGLN(codeGlnStr);
        }
        DemandeAutorisation demandeAutorisation = demandeAutorisationService.initialiserDemandeAutorisation(profession, codeGLN);

        return RestUtils.forgeResponseObject(demandeAutorisation.getReferenceDeDemande());
    }

}
