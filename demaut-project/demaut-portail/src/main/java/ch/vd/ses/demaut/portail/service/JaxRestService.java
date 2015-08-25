package ch.vd.ses.demaut.portail.service;

import org.springframework.stereotype.Service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

@Service
@Path("/jaxrs")
public class JaxRestService {

    @GET
    @Path("hello")
    public String sayHelloTo(@QueryParam("name") String name) {
        System.out.println("SayHello to ");
        return "Hello to " + name + " ! ";
    }
}
