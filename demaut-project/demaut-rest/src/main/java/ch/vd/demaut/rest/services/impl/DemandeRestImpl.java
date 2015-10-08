package ch.vd.demaut.rest.services.impl;

import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisation;
import ch.vd.demaut.domain.demandes.autorisation.Profession;
import ch.vd.demaut.domain.demandeur.donneesProf.CodeGLN;
import ch.vd.demaut.domain.utilisateurs.Login;
import ch.vd.demaut.rest.commons.json.RestUtils;
import ch.vd.demaut.services.demandes.autorisation.DemandeAutorisationService;
import org.apache.cxf.rs.security.cors.CrossOriginResourceSharing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.*;
import java.io.IOException;
import java.util.Arrays;

@CrossOriginResourceSharing(allowAllOrigins = true)
@Service("demandeRestImpl")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Path("/demande")
public class DemandeRestImpl {

    private static final Logger LOGGER = LoggerFactory.getLogger(DemandeRestImpl.class);

    @Autowired
    private DemandeAutorisationService demandeAutorisationService;

    @Context
    private UriInfo uriInfo;

    @Context
    private HttpHeaders httpHeaders;

    @GET
    @Path("/initialiser")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER")
    public Response initialiserDemande(@QueryParam("professionId") String professionIdStr,
                                       @QueryParam("codeGln") String codeGlnStr) throws IOException {

        Login login = new Login(RestUtils.fetchCurrentUserToken(httpHeaders));

        LOGGER.info("initialiser demande pour : " + login.getValue() + ", profession=" + professionIdStr + ", codeGLN=" + codeGlnStr);

        Integer professionId = Integer.valueOf(professionIdStr);
        Profession profession = Profession.getTypeById(professionId);
        CodeGLN codeGLN = null;
        if (!StringUtils.isEmpty(codeGlnStr)) {
            codeGLN = new CodeGLN(codeGlnStr);
        }
        DemandeAutorisation demandeAutorisation = demandeAutorisationService.initialiserDemandeAutorisation(profession, codeGLN, login);
        return RestUtils.buildJSon(Arrays.asList(demandeAutorisation.getReferenceDeDemande()));
    }

    @GET
    @Path("/recuperer")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER")
    public Response trouverDemandeBrouillonParUtilisateur() throws IOException {

        Login login = new Login(RestUtils.fetchCurrentUserToken(httpHeaders));

        LOGGER.info("recuperer Brouillon pour : " + login.getValue());

        DemandeAutorisation demandeAutorisation = demandeAutorisationService.trouverDemandeBrouillonParUtilisateur(login);
        // TODO remonter les infos a afficher dans Cockpit
        return RestUtils.buildJSon(Arrays.asList(demandeAutorisation.getReferenceDeDemande()));
    }
}
