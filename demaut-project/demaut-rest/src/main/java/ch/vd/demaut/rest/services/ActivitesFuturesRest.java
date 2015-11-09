package ch.vd.demaut.rest.services;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ch.vd.demaut.domain.demandes.ReferenceDeDemande;
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
    @Path("/liste")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER")
    Response listerLesActivitesFutures(@QueryParam("referenceDeDemande") ReferenceDeDemande referenceDeDemande);
    
    @GET
    @Path("/ajouter")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER") //   
    //TODO: Ajouter les attributs de la ActiviteFuture
    Response ajouterActiviteFuture(@QueryParam("referenceDeDemande") ReferenceDeDemande referenceDeDemande);
    
    @GET
    @Path("/supprimer")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER") //
    Response supprimerActiviteFuture(@QueryParam("referenceDeDemande") ReferenceDeDemande referenceDeDemande, @QueryParam("ordre") String ordre);
}
