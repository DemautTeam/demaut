package ch.vd.demaut.microbiz.main;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class StartWebappMain {

    private static ClassPathXmlApplicationContext xmlApplicationContext;

    public static void main(String[] args) throws Exception {
        xmlApplicationContext = new ClassPathXmlApplicationContext("/osgiTest-context.xml");
        xmlApplicationContext.registerShutdownHook();
    }
}
