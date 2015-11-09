package ch.vd.demaut.rest.services.impl;

import ch.vd.demaut.domain.demandes.ReferenceDeDemande;
import ch.vd.demaut.domain.demandeur.donneesProf.activites.ActiviteFutureFK;
import ch.vd.demaut.domain.demandeur.donneesProf.activites.ListeDesActivitesFutures;
import ch.vd.demaut.rest.json.commons.RestUtils;
import ch.vd.demaut.rest.services.ActivitesFuturesRest;
import ch.vd.demaut.services.demandeurs.donneesProf.activites.ActivitesFuturesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.FormParam;
import javax.ws.rs.core.Response;

/**
 * Implementation du service
 */
public class ActivitesFuturesRestImpl implements ActivitesFuturesRest {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    ActivitesFuturesService activitesFuturesService;

    public void setActivitesFuturesService(ActivitesFuturesService activitesFuturesService) {
        this.activitesFuturesService = activitesFuturesService;
    }

    @Override
    public Response listerLesActivitesFutures(ReferenceDeDemande referenceDeDemande) {
        ListeDesActivitesFutures listeDesActivitesFutures = activitesFuturesService.listerLesActivitesFutures(referenceDeDemande);
        return RestUtils.buildJSonResponse(listeDesActivitesFutures.listerActivitesFutures());
    }

    @Override
    public Response ajouterActiviteFuture(ReferenceDeDemande referenceDeDemande) {
        logger.debug("/ajouter - Reference de la demande : {}", referenceDeDemande);
        return  RestUtils.buildJSonResponse("OK");
    }

    @Override
    public Response supprimerActiviteFuture(@FormParam("referenceDeDemande") ReferenceDeDemande referenceDeDemande, @FormParam("activiteFutureFK") ActiviteFutureFK activiteFutureFK) {
        logger.debug("/supprimer - Reference de la demande : {} ActiviteFK : {}", referenceDeDemande, activiteFutureFK);
        activitesFuturesService.supprimerActiviteFuture(referenceDeDemande,activiteFutureFK);
        return RestUtils.buildJSonResponse("OK");
    }
}
