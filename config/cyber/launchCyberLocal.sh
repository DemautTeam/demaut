# Launch Demaut Cyber en local utilisant la classe DemautTomcatRunner
#!/bin/bash

DEMAUT_PRJ=../../demaut-project
DEMAUT_DOMAIN=$DEMAUT_PRJ/demaut-domain
DEMAUT_COMMONS=$DEMAUT_PRJ/demaut-commons
DEMAUT_DATA_JPA=$DEMAUT_PRJ/demaut-data-jpa
DEMAUT_CYBER=$DEMAUT_PRJ/demaut-cyber
DEMAUT_CYBER_TARGET=$DEMAUT_CYBER/target
DEMAUT_CYBER_WEBINF=$DEMAUT_CYBER_TARGET/demaut-cyber-0.1.0-SNAPSHOT/WEB-INF
DEMAUT_SERVICES=$DEMAUT_PRJ/demaut-services
LOCAL_REPO=$HOME/.m2/repository
VERSION=0.1.0.SNAPSHOT

CLASSPATH="$DEMAUT_CYBER_TARGET/*:$DEMAUT_CYBER_TARGET/lib/*:$DEMAUT_CYBER_WEBINF/classes"

MAIN=ch.vd.ses.demaut.portail.tomcat.DemautTomcatRunner

#-Duser.dir=$DEMAUT_CYBER ne marche pas
cd $DEMAUT_CYBER
if [ $# -eq 1 ]
	then
		mvn clean install
fi
java -cp $CLASSPATH $MAIN