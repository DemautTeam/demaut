package ch.vd.demaut.rest.services.impl;

import org.apache.cxf.rs.security.cors.CrossOriginResourceSharing;

import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.UriInfo;

@CrossOriginResourceSharing(allowAllOrigins = true)
@Path("/activite")
public class ActiviteRestImpl {

    @Context
    private UriInfo uriInfo;

    @Context
    private HttpHeaders httpHeaders;

}
