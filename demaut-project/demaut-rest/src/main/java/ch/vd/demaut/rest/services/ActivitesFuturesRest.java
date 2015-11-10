package ch.vd.demaut.rest.services;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.cxf.rs.security.cors.CrossOriginResourceSharing;

import ch.vd.demaut.domain.demandes.ReferenceDeDemande;
import ch.vd.demaut.domain.demandeur.donneesProf.activites.ActiviteFuture;
import ch.vd.demaut.domain.demandeur.donneesProf.activites.ActiviteFutureFK;

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
    Response ajouterActiviteFuture(@FormParam("referenceDeDemande") ReferenceDeDemande referenceDeDemande,//
            @FormParam("nomEtablissement") String nomEtablissementStr, //
            @FormParam("voie") String voieStr, //
            @FormParam("complement") String complementStr, //
            @FormParam("localite") String localiteStr, //
            @FormParam("npaProfessionnel") String npaProfessionnelStr, //
            @FormParam("telephoneProf") String telephoneProfStr, //
            @FormParam("telephoneMobile") String telephoneMobileStr, //
            @FormParam("fax") String faxStr, //
            @FormParam("email") String emailStr, //
            @FormParam("siteInternet") String siteInternetStr, //
            @FormParam("typePratiqueLamal") String typePratiqueLamalStr, //
            @FormParam("typeActivite") String typeActiviteStr, //
            @FormParam("nombreJourParSemaine") Integer nombreJourParSemaine, //
            @FormParam("datePrevueDebut") Long datePrevueDebutStr, //
            @FormParam("superviseur") String superviseurStr);
    
    @POST
    @Path("supprimer")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER") //
    Response supprimerActiviteFuture(@FormParam("referenceDeDemande") ReferenceDeDemande referenceDeDemande, @FormParam("activiteFutureFK") ActiviteFutureFK activiteFutureFK);
}
