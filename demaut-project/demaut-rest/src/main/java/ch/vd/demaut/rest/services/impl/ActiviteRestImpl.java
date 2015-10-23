package ch.vd.demaut.rest.services.impl;

import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.UriInfo;

import org.apache.cxf.rs.security.cors.CrossOriginResourceSharing;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@CrossOriginResourceSharing(allowAllOrigins = true)
@Service("activiteRestImpl")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Path("/activite")
public class ActiviteRestImpl {

    @Context
    private UriInfo uriInfo;

    @Context
    private HttpHeaders httpHeaders;

}
