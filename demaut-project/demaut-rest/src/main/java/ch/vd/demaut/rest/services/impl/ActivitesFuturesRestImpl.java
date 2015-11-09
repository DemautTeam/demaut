package ch.vd.demaut.rest.services.impl;

import ch.vd.demaut.domain.demandes.ReferenceDeDemande;
import ch.vd.demaut.domain.demandeur.donneesProf.activites.ListeDesActivitesFutures;
import ch.vd.demaut.rest.json.commons.RestUtils;
import ch.vd.demaut.rest.services.ActivitesFuturesRest;
import ch.vd.demaut.services.demandeurs.donneesProf.activites.ActivitesFuturesService;

import javax.ws.rs.core.Response;

/**
 * Implementation du service
 */
public class ActivitesFuturesRestImpl implements ActivitesFuturesRest {

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

        return null;
    }

    @Override
    public Response supprimerActiviteFuture(ReferenceDeDemande referenceDeDemande, String ordre) {
        return null;
    }
}
