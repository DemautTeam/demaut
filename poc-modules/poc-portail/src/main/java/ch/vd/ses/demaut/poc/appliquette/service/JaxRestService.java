package ch.vd.ses.demaut.poc.appliquette.service;

import org.springframework.stereotype.Service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

/**
 * Created by mourad on 10.08.15.
 */
@Service
@Path("/jaxrs")
public class JaxRestService {

    @GET
    public String sayHelloTo(@QueryParam("name") String name){
        System.out.println("SayHello to ");
        return "Hello to " + name + " ! ";
    }
}