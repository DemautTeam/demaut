package ch.vd.demaut.rest.services;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ch.vd.demaut.domain.demandes.ReferenceDeDemande;
import ch.vd.demaut.domain.demandeur.donneesProf.activites.ActiviteFutureFK;
import org.apache.cxf.rs.security.cors.CrossOriginResourceSharing;

import ch.vd.demaut.domain.demandeur.donneesProf.activites.ActiviteFuture;

/**
 * Interface REST pour les {@link ActiviteFuture}
 *
 */
@CrossOriginResourceSharing(allowAllOrigins = true)
@Path("/activitesFutures")
public interface ActivitesFuturesRest {

    @GET
    @Path("liste")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER")
    Response listerLesActivitesFutures(@QueryParam("referenceDeDemande") ReferenceDeDemande referenceDeDemande);
    
    @POST
    @Path("ajouter")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER") //   
    //TODO: Ajouter les attributs de la ActiviteFuture
    Response ajouterActiviteFuture(@FormParam("referenceDeDemande") ReferenceDeDemande referenceDeDemande);
    
    @POST
    @Path("supprimer")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER") //
    Response supprimerActiviteFuture(@FormParam("referenceDeDemande") ReferenceDeDemande referenceDeDemande, @FormParam("activiteFutureFK") ActiviteFutureFK activiteFutureFK);
}
