package ch.vd.pee.microbiz.poc.microbiz;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class StartWebappMain {

    public static void main(String[] args) throws Exception {
		ClassPathXmlApplicationContext xmlApplicationContext = new ClassPathXmlApplicationContext("/microbizTest-context.xml");
        xmlApplicationContext.registerShutdownHook();
        xmlApplicationContext.close();
    }
}
