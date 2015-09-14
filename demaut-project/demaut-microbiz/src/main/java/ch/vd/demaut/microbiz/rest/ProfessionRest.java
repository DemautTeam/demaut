package ch.vd.demaut.microbiz.rest;

import ch.vd.ses.referentiel.demaut_1_0.RefListType;
import com.fasterxml.jackson.core.JsonProcessingException;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

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

}
