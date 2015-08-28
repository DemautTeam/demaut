Jetty
    Lancer la classe StartWebappMain
    Accès au poc : http://localhost:8081/deamut-poc



Microbiz :
    Config

        Modifier le shellscript  /microbiz-1.0.0/bin/microbiz en ajoutant au début : export JAVA_HOME=/home/..../jdk1.7.0_79
        Il y a encore peut-être un soucis pour trouver le serveur nexus depuis la machine linux, il faut de modifier le fichier /microbiz-1.0.0/etc/org.ops4j.pax.url.mvn.cfg :
            org.ops4j.pax.url.mvn.repositories=http://spip.etat-de-vaud:8081/nexus/content/groups/esb-public@id=esb.public
        Il faut récupérer le context sous :
            /microbiz-1.0.0/config/pocDemaut.cfg                      poc.rs.base.endpoint=/pocDemaut
            /microbiz-1.0.0/etc/org.apache.cxf.osgi.cfg               org.apache.cxf.servlet.context=/outils

        Il faut ajouter les variables sous : /microbiz-1.0.0/config/pocDemaut.cfg
            service.endpoint=http://localhost:40009/outils/poc
            security.active=false
            dataSource.url=jdbc:oracle:thin:@sli2315t.etat-de-vaud.ch:1526:SESPOL1L

        poc-demaut.jar sous /microbiz-1.0.0/deploy

        pocDemaut.cfg sous /microbiz-1.0.0/config

    Stop/Start du container

         ./microbiz-1.0.0//bin/microbiz start
         ./microbiz-1.0.0//bin/microbiz stop

    Features à installer
        >ssh admin@localhost -p 40003
        features:install webconsole
        features:install -v openjpa
        features:install -v jpa
        features:install -v spring-jdbc/3.2.11.RELEASE_1
        features:install -v spring-orm/3.2.11.RELEASE_1

    WebConsole
        Accès à la console :http://localhost:40002/system/console/bundles

    Poc Endpoint
        Accès au poc : http://localhost:40002/outils/demaut-poc
        Endpoint Main processor : http://localhost:40009/outils/poc/services/main
        Endpoint all annexes : http://localhost:40009/outils/poc/services/annexes/all