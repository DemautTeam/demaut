package ch.vd.ses.demaut.portail.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * Ressource permettant d'accéder aux élements de configuration.
 * <p/>
 * Ces services sont destinés à Angular de cyber pour configurer ce URL d'interrogation de microbiz
 */
@Path("/config")
public interface ConfiguratorService {

    @GET
    @Path("urlprefix")
    @Produces("application/json")
    String getUrlPrefix();

}
