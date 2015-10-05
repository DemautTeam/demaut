package ch.vd.demaut.microbiz.rest.impl;

import ch.vd.demaut.domain.demandeur.Pays;
import ch.vd.demaut.microbiz.rest.RestUtils;
import org.apache.cxf.rs.security.cors.CrossOriginResourceSharing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.Arrays;
import java.util.List;

@Service("activiteRestImpl")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@CrossOriginResourceSharing(allowAllOrigins = true)
@Path("/activite")
public class ActiviteRestImpl {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActiviteRestImpl.class);

    @Context
    private UriInfo uriInfo;

    @GET
    @Path("/nationalites")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER")
    public Response listerLesNationalites() throws Exception {

        LOGGER.info("listerLesNationalites");

        // Altrenative:
        List<Pays> paysList = buildListePaysSansProgresSOA();
        // Autre altrenative:
        //List<VcType> paysList = buildListePaysAvecProgresSOA(uriInfo);
        return RestUtils.forgeResponseList(paysList);
    }

    private List<Pays> buildListePaysSansProgresSOA() {
        return Arrays.asList(Pays.values());
    }
}
