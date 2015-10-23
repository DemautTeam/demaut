package ch.vd.demaut.rest.services.impl;

import ch.vd.demaut.domain.demandeur.Pays;
import ch.vd.demaut.domain.demandeur.donneesPerso.Langue;
import ch.vd.demaut.rest.commons.json.RestUtils;
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
import javax.ws.rs.core.*;
import java.util.Arrays;
import java.util.List;

@CrossOriginResourceSharing(allowAllOrigins = true)
@Service("sharedRestImpl")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Path("/shared")
public class SharedRestImpl {

    private static final Logger LOGGER = LoggerFactory.getLogger(SharedRestImpl.class);

    @Context
    private UriInfo uriInfo;

    @Context
    private HttpHeaders httpHeaders;

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
        return RestUtils.buildRef(paysList);
    }

    private List<Pays> buildListePaysSansProgresSOA() {
        return Arrays.asList(Pays.values());
    }

    @GET
    @Path("/langues")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER")
    public Response listerLesLangues() throws Exception {

        LOGGER.info("listerLesLangues");

        return RestUtils.buildRef(Arrays.asList(Langue.values()));
    }

    @GET
    @Path("/paysList")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER")
    public Response listerLesPays() throws Exception {

        LOGGER.info("listerLesPays");

        // Altrenative:
        List<Pays> paysList = buildListePaysSansProgresSOA();
        // Autre altrenative:
        //List<VcType> paysList = buildListePaysAvecProgresSOA(uriInfo);
        return RestUtils.buildRef(paysList);
    }
}