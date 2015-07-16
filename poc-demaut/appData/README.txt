Config

    Modifier le shellscript  /microbiz-1.0.0/bin/microbiz en ajoutant au début : export JAVA_HOME=/home/..../jdk1.7.0_79
    Il y a encore peut-être un soucis pour trouver le serveur nexus depuis la machine linux, il faut de modifier le fichier /microbiz-1.0.0/etc/org.ops4j.pax.url.mvn.cfg :
        org.ops4j.pax.url.mvn.repositories=http://spip.etat-de-vaud:8081/nexus/content/groups/esb-public@id=esb.public
    Il faut récupérer le context sous :
        /microbiz-1.0.0/config/demo1.cfg                            poc.rs.base.endpoint=/demaut-poc
        /microbiz-1.0.0/etc/org.apache.cxf.osgi.cfg                 org.apache.cxf.servlet.context=/outils

    poc.jar sous /microbiz-1.0.0/deploy

    poc.cfg sous /microbiz-1.0.0/config

Stop/Start du container

     ./microbiz-1.0.0//bin/microbiz start
     ./microbiz-1.0.0//bin/microbiz stop

WebConsole

    ssh admin@localhost -p 40003
    > features:install webconsole
    > osgi:list | grep demo1
    Accès à la console :http://localhost:40002/system/console/bundles

Demo Endpoint
    Accès à la demo :http://localhost:40002/outils/demaut-poc