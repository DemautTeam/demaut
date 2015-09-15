package ch.vd.demaut.microbiz.rest;

import org.apache.cxf.rs.security.cors.CrossOriginResourceSharing;
import org.springframework.stereotype.Service;

import javax.ws.rs.Path;

@CrossOriginResourceSharing(
        allowOrigins = {"*"},
        allowCredentials = true,
        maxAge = 3600,
        allowHeaders = {"Content-Type", "X-Requested-With"},
        exposeHeaders = {"Access-Control-Allow-Origin"}
)
@Service
@Path("/service")
public class MainCamelRest {

}
