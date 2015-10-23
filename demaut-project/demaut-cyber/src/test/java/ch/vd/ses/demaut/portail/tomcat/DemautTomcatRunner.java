package ch.vd.ses.demaut.portail.tomcat;

import ch.vd.cyber.common.boot.SpringBootSanitizer;
import ch.vd.ses.demaut.portail.appliquette.DemautCyberApplication;
import org.springframework.boot.SpringApplication;

/**
 * Ceci n'est pas un test unitaire, juste une classe utilitaire pour charger
 * demaut-cyber dans un environnement de developpement.
 */
public class DemautTomcatRunner {

    private static final String appDir = "appDir/CO";
    private static final String projectDir = appDir;

    public static void main(String[] args) {
        System.out.println("Working Directory = " + System.getProperty("user.dir"));

        final String deployDir = appDir + "/demaut";
        final String dataDir = projectDir + "/app/demaut";

        // Mis par le CEI dans Tomcat
        System.setProperty("ch.vd.appDir", appDir);
        System.setProperty("ch.vd.projectDir", projectDir);
        System.setProperty("ch.vd.deployDir", deployDir);
        System.setProperty("ch.vd.confDir", deployDir + "/config");
        System.setProperty("ch.vd.dataDir", dataDir);
        System.setProperty("server.port", "1234");
        System.setProperty("server.contextPath", "/prestations/demaut");
        // A ajouter à la cmd line
        System.setProperty("encrypt.key", "abcd7d6s7cq6f6svgdfhgf6h8fg68"); // clé partagée entre toutes les applications CO (dev local)

        System.setProperty("spring.cloud.config.uri", "http://user:DE_ConfigServer57620@slv1443v.etat-de-vaud.ch:57620/configserver");
        System.setProperty("spring.profiles.active", "CO");
        System.setProperty("spring.cloud.config.label", "master");

        SpringBootSanitizer.copyLogbackDevFile();

        args = SpringBootSanitizer.getSpringBootArgs();
        SpringApplication.run(DemautCyberApplication.class, args);
    }

}
