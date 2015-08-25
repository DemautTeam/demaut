package ch.vd.ses.demaut.poc.appliquette.service;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/hello")
public class HelloService {

    @RequestMapping(method = RequestMethod.GET)
    public String sayHello() {
        return "Hello Spring boot !";
    }
}
