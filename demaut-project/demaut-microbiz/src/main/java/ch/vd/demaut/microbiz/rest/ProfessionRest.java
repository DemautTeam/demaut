package ch.vd.demaut.microbiz.rest;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("/profession")
public interface ProfessionRest {

    @GET
    @Path("/professionsList")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER")
    Response listerLesProfessionsDeLaSante(UriInfo uriInfo) throws Exception;

    @GET
    @Path("/donnees/{demandeReference}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @RolesAllowed("USER")
    Response afficherDonneesProfession(UriInfo uriInfo, String demandeReference) throws Exception;

    @GET
    @Path("/donnees/{demandeReference}/{idProfession}/{codeGln}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER")
    Response renseignerDonneesProfession(UriInfo uriInfo, String demandeReference, String idProfession, String codeGln) throws Exception;

}
