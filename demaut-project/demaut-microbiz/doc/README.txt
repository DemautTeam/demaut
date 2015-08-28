Jetty
    Lancer la classe StartWebappMain
    Accès au demaut-microbiz : http://localhost:8081/demaut-microbiz

Microbiz :
    Config

        Modifier le shellscript  /microbiz-1.0.0/bin/microbiz en ajoutant au début : export JAVA_HOME=/home/..../jdk1.7.0_79
        Il y a encore peut-être un soucis pour trouver le serveur nexus depuis la machine linux, il faut de modifier le fichier /microbiz-1.0.0/etc/org.ops4j.pax.url.mvn.cfg :
            org.ops4j.pax.url.mvn.repositories=http://spip.etat-de-vaud:8081/nexus/content/groups/esb-public@id=esb.public
        Il faut récupérer le context sous :
            /microbiz-1.0.0/config/demaut-microbiz.cfg                            demaut.rs.base.endpoint=/demaut-microbiz
            /microbiz-1.0.0/etc/org.apache.cxf.osgi.cfg               org.apache.cxf.servlet.context=/outils

        Il faut ajouter les variables sous : /microbiz-1.0.0/config/demaut-microbiz.cfg
            demaut.microbiz.service.endpoint=http://localhost:40009/outils/demaut-microbiz
            demaut.microbiz.security.active=false
            dataSource.url=jdbc:oracle:thin:@sli2315t.etat-de-vaud.ch:1526:SESPOL1L

        demaut-microbiz.jar sous /microbiz-1.0.0/deploy

        demaut-microbiz.cfg sous /microbiz-1.0.0/config

    Sous le projet demaut-microbiz, il y a un script deployLocal.sh (à configurer le path vers le container) qui permet de deployer le bundle et la config directement dans Microbiz.

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

    Vérifier l'installation/désinstallation du demaut-microbiz :

    > ssh admin@localhost -p 40003 (password = admin par défaut défini dans /etc/users.config)admin@localhost -p 40003
    > features:install webconsole
    > features:list | grep webconsole
    > osgi:list | grep demaut-microbiz

    WebConsole
        Accès à la console :http://localhost:40002/system/console/bundles

    Poc Endpoint
        Accès au poc : http://localhost:40002/outils/demaut-microbiz
        Endpoint Main processor : http://localhost:40009/outils/demaut-microbiz/services/main
        Endpoint all annexes : http://localhost:40009/outils/demaut-microbiz/services/annexes/all

    Microbiz Debug
        Faire le debugging de l'application Demo avec remote debugging :
            lancer le container Microbiz en mode "debug" (modifier les properties dans /etc/wrapper.conf) en activant les lignes :
                wrapper.java.additional.21=-Xdebug -Xnoagent -Djava.compiler=NONE
                wrapper.java.additional.22=-Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=%BASE_PORT%6
        s'y connecter avec la configuration Remote debug depuis IntelliJ sur le port 40006.