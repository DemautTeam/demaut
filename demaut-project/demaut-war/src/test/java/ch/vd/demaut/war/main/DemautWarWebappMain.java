package ch.vd.demaut.war.main;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DemautWarWebappMain {

    private static ClassPathXmlApplicationContext xmlApplicationContext;

    public static void main(String[] args) throws Exception {
        xmlApplicationContext = new ClassPathXmlApplicationContext("classpath*:*warTest-context.xml");
        xmlApplicationContext.registerShutdownHook();
    }
}
