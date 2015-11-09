package ch.vd.demaut.rest.services.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.security.RolesAllowed;
import javax.validation.ConstraintViolation;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.cxf.rs.security.cors.CrossOriginResourceSharing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;

import ch.vd.demaut.commons.validation.ValidatorFactoryDefault;
import ch.vd.demaut.domain.demandes.ReferenceDeDemande;
import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisation;
import ch.vd.demaut.domain.demandes.autorisation.Profession;
import ch.vd.demaut.domain.demandeur.donneesProf.CodeGLN;
import ch.vd.demaut.domain.utilisateurs.Login;
import ch.vd.demaut.rest.dto.DemandeAutorisationCockpitDTO;
import ch.vd.demaut.rest.json.commons.RestUtils;
import ch.vd.demaut.rest.services.AbstractRestService;
import ch.vd.demaut.services.demandes.autorisation.DemandeAutorisationService;

@CrossOriginResourceSharing(allowAllOrigins = true)
@Path("/demande")
public class DemandeRestImpl extends AbstractRestService {

    private static final Logger LOG = LoggerFactory.getLogger(DemandeRestImpl.class);

    private DemandeAutorisationService demandeAutorisationService;

    public void setDemandeAutorisationService(DemandeAutorisationService demandeAutorisationService) {
        this.demandeAutorisationService = demandeAutorisationService;
    }

    /**
     * Exposition de la validation du GLN via REST
     *
     * @param codeGLN
     * @return
     * @throws JsonProcessingException
     */
    @GET
    @Path("/validerGln")
    @Produces(MediaType.APPLICATION_JSON)
    public Response validerCodeGln(@QueryParam("codegln") CodeGLN codeGLN) throws JsonProcessingException {
        LOG.debug("CodeGLN : {}", codeGLN.getValue());
        Set<ConstraintViolation<CodeGLN>> contraints = ValidatorFactoryDefault.getValidator().validate(codeGLN);
        if (contraints.isEmpty()) {
            return RestUtils.buildJSonResponse("OK");
        } else {
            return RestUtils.buildJSonResponse("Nok");
        }
    }

    @GET
    @Path("/initialiser")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER")
    public Response initialiserDemande(@QueryParam("professionId") Integer professionId,
                                       @QueryParam("codeGln") CodeGLN codeGLN) throws IOException {

        Login login = getLogin();

        LOG.debug("initialiser demande pour : {}, profession= {}", login.getValue(), professionId);

        Profession profession = Profession.getTypeById(professionId);

        DemandeAutorisation demandeAutorisation = demandeAutorisationService.initialiserDemandeAutorisation(profession, codeGLN, login);
        // TODO: Tester s'il existe une demande et si oui, lancer une exception
        return RestUtils.buildJSonResponse(demandeAutorisation.getReferenceDeDemande());
    }

    /**
     * Méthode qui renvoie un broullion via referenceDeDemande
     */
    @GET
    @Path("/recupererBrouillon")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER")
    public Response recupererBrouillon(@QueryParam("referenceDeDemande") ReferenceDeDemande referenceDeDemande) throws IOException {

        Login login = getLogin();

        LOG.debug("recuperer Brouillon pour : {}, referenceDeDemande= {}", login.getValue(), referenceDeDemande.getValue());

        DemandeAutorisation demandeAutorisation = demandeAutorisationService.recupererDemandeParReference(referenceDeDemande);
        return RestUtils.buildJSonResponse(demandeAutorisation);
    }

    /**
     * Méthode qui renvoie la liste des broullions utilisés dans Cockpit
     */
    @GET
    @Path("/recupererListBrouillons")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER")
    public Response recupererListeBrouillons() throws IOException {

        Login login = getLogin();

        LOG.debug("recuperer listes Brouillons pour : {}", login.getValue());

        List<DemandeAutorisation> demandeAutorisations = demandeAutorisationService.recupererListeBrouillons(login);
        
        List<DemandeAutorisationCockpitDTO> demandeAutorisationDTOs = convertToDemandeAutorisationDTOs(
                demandeAutorisations);
        
        return RestUtils.buildJSonResponse(demandeAutorisationDTOs);
    }

    @GET
    @Path("/supprimer")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER")
    public Response supprimerUnBrouillon(@QueryParam("referenceDeDemande") ReferenceDeDemande referenceDeDemande) throws IOException {

        Login login = getLogin();

        LOG.debug("supprimer un brouillons pour : {}, referenceDeDemande= {}", login.getValue(), referenceDeDemande);

        demandeAutorisationService.supprimerUnBrouillon(referenceDeDemande);
        
        return RestUtils.buildJSonResponse(true);
    }
    

    private List<DemandeAutorisationCockpitDTO> convertToDemandeAutorisationDTOs(
            List<DemandeAutorisation> demandeAutorisations) {
        List<DemandeAutorisationCockpitDTO> demandeAutorisationDTOs = new ArrayList<DemandeAutorisationCockpitDTO>();
        for (DemandeAutorisation demandeAutorisation : demandeAutorisations) {
            demandeAutorisationDTOs.add(new DemandeAutorisationCockpitDTO(demandeAutorisation));
        }
        return demandeAutorisationDTOs;
    }


}
