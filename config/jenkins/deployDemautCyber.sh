#!/bin/sh

current_dir="$(pwd)"
script_dir="$(dirname $0)"

echo "Current dir $current_dir"
echo "Script dir $script_dir"
whoami

workspaceContent=`ls ${WORKSPACE}`
configContent=`ls -al "${WORKSPACE}/config/jenkins/"`
echo Lister workspace : $workspaceContent
echo Lister config : $configContent

#if [ $script_dir = '.' ]
#then
#script_dir=$current_dir
#fi

projectFolderName=demaut-cyber
projectConfigName=application
tomcatConfigName=demaut-cyber.xml
projectBasedir=${WORKSPACE}/demaut-project/$projectFolderName

# deploy to demo
component=$projectFolderName
pathServer=/ccv/data/dsi_cyber/demautIN
remoteBin=$pathServer
remoteBaseApp=$pathServer/app/demaut
#remoteDeploy=$pathServer/app/demaut/deployment
#remoteConfig=$pathServer/app/demaut/config
remoteTomcatConfig=$pathServer/conf/Catalina/localhost
remoteServer=dsi_cyber@slv2395t.etat-de-vaud.ch
#contourne l'alerte de sécurité de SSH
chmod 600 ${WORKSPACE}/config/jenkins/id.rsa.jenkins
sshOptions="-o StrictHostKeyChecking=no -i ${WORKSPACE}/config/jenkins/id.rsa.jenkins"

echo Rechercher bundle à déployer $component : `ls $projectBasedir/target/$component-*.tar.gz`

if [ -f $projectBasedir/target/$component-*.tar.gz ]
then
	 echo Nouveau bundle à déployer: `ls $projectBasedir/target/$component-*.tar.gz`
else
     echo "Pas de bundle à déployer dans target. Veuillez compiler le projet"
    exit 0
fi

echo "WARNING : You should copy jenkins public key to 'ssh-copy-id $remoteServer' or enter $remoteServer server password!"

echo "Stop du container Tomcat..."
ssh $sshOptions $remoteServer $remoteBin/tomcatctl.sh stop
echo "Stop du container Tomcat terminé"

echo "waiting 5s....."
sleep 5

echo "Cleaning Tomcat's container..."
ssh $sshOptions $remoteServer $remoteBin/tomcatctl.sh clean
echo "Cleaning Tomcat's container, done..."

echo "Suppression de l'ancien bundle..."
ssh $sshOptions $remoteServer rm -rf $remoteBaseApp/$component-*
echo "Copie de la nouvelle version..."
scp $sshOptions $projectBasedir/target/$component-*.tar.gz $remoteServer:$remoteBaseApp
echo "Décompression de la nouvelle version..."
ssh $sshOptions $remoteServer tar -xzvf $remoteBaseApp/$component-*.tar.gz
echo "Mise à jour du bundle terminée"

echo "Mise à jour du fichier de configuration tomcat sur $remoteServer:$remoteTomcatConfig..."
scp $sshOptions "$projectBasedir/tomcat/$tomcatConfigName.xml" $remoteServer:$remoteTomcatConfig
echo "Mise à jour du fichier de configuration tomcat terminée"

echo "Start du tomcat cyber..."
ssh $sshOptions $remoteServer $remoteBin/tomcatctl.sh start
echo "Start du tomcat cyber terminé"

echo "waiting 5s....."
sleep 5
echo "Status du tomcat cyber..."
ssh $sshOptions $remoteServer $remoteBin/tomcatctl.sh check

