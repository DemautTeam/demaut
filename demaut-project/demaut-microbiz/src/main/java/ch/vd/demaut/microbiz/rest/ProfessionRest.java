package ch.vd.demaut.microbiz.rest;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/profession")
public interface ProfessionRest {

    @GET
    @Path("/professionsList")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER")
    Response listerLesProfessionsDeLaSante() throws Exception;

    @GET
    @Path("/donnees/{demandeReference}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @RolesAllowed("USER")
    Response afficherDonneesProfession(String demandeReference) throws Exception;

    @GET
    @Path("/donnees/{demandeReference}/{idProfession}/{codeGln}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER")
    Response renseignerDonneesProfession(@PathParam("demandeReference") String demandeReference,
                                         @PathParam("idProfession") String idProfession,
                                         @PathParam("codeGln") String codeGln) throws Exception;

}
