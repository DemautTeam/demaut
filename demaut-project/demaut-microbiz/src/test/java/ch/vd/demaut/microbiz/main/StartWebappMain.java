package ch.vd.demaut.microbiz.main;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class StartWebappMain {

    public static void main(String[] args) throws Exception {
		ClassPathXmlApplicationContext xmlApplicationContext = new ClassPathXmlApplicationContext("/microbizTest-context.xml");
        xmlApplicationContext.registerShutdownHook();
    }
}
