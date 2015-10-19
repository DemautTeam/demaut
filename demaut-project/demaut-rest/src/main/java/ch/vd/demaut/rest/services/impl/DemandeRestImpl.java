package ch.vd.demaut.rest.services.impl;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import ch.vd.demaut.domain.demandes.ReferenceDeDemande;
import ch.vd.demaut.domain.exception.DemandeNotFoundException;
import org.apache.cxf.rs.security.cors.CrossOriginResourceSharing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisation;
import ch.vd.demaut.domain.demandes.autorisation.Profession;
import ch.vd.demaut.domain.demandeur.donneesProf.CodeGLN;
import ch.vd.demaut.domain.utilisateurs.Login;
import ch.vd.demaut.rest.commons.json.RestUtils;
import ch.vd.demaut.services.demandes.autorisation.DemandeAutorisationService;

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

        LOGGER.info("initialiser demande pour : " + login.getValue() + ", profession=" + professionIdStr + ", codeGLN="
                + codeGlnStr);

        Integer professionId = Integer.valueOf(professionIdStr);
        Profession profession = Profession.getTypeById(professionId);
        CodeGLN codeGLN = null;
        if (!StringUtils.isEmpty(codeGlnStr)) {
            codeGLN = new CodeGLN(codeGlnStr);
        }
        
        DemandeAutorisation demandeAutorisation = demandeAutorisationService.initialiserDemandeAutorisation(profession, codeGLN, login);
        // TODO: Tester s'il existe une demande et si oui, lancer une exception
        return RestUtils.buildJSon(demandeAutorisation.getReferenceDeDemande());
    }

    /**
     * Méthode qui renvoie un broullion via referenceDeDemande
     */
    @GET
    @Path("/recupererBrouillon")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER")
    public Response recupererBrouillon(@QueryParam("referenceDeDemande") String referenceDeDemandeStr) throws IOException {

        Login login = new Login(RestUtils.fetchCurrentUserToken(httpHeaders));

        LOGGER.info("recuperer Brouillon pour : " + login.getValue() + ", referenceDeDemande=" + referenceDeDemandeStr);

        ReferenceDeDemande referenceDeDemande = new ReferenceDeDemande(referenceDeDemandeStr);

        DemandeAutorisation demandeAutorisation = demandeAutorisationService.recupererDemandeParReference(referenceDeDemande);
        return RestUtils.buildJSon(demandeAutorisation);
    }

    /**
     * Méthode qui renvoie la liste des broullions utilisés dans Cockpit
     */
    @GET
    @Path("/recupererListBrouillons")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER")
    public Response recupererListeBrouillons() throws IOException {

        Login login = new Login(RestUtils.fetchCurrentUserToken(httpHeaders));

        LOGGER.info("recuperer listes Brouillons pour : " + login.getValue());

        List<DemandeAutorisation> demandeAutorisations = demandeAutorisationService.recupererListeBrouillons(login);
        return RestUtils.buildJSon(demandeAutorisations);
    }

    @GET
    @Path("/supprimer")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER")
    public Response supprimerUnBrouillon(@QueryParam("referenceDeDemande") String referenceDeDemandeStr) throws IOException {

        Login login = new Login(RestUtils.fetchCurrentUserToken(httpHeaders));

        LOGGER.info("supprimer un brouillons pour : " + login.getValue() + ", referenceDeDemande=" + referenceDeDemandeStr);

        ReferenceDeDemande referenceDeDemande = new ReferenceDeDemande(referenceDeDemandeStr);

        demandeAutorisationService.supprimerUnBrouillon(login, referenceDeDemande);
        return RestUtils.buildJSon(true);
    }
}
