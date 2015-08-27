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

projectFolderName=poc-demaut
projectBasedir=${WORKSPACE}/$projectFolderName

# deploy to smx4
component=$projectFolderName
pathServer=/ccv/data/dsi_cyber/microbiz-1.0.0
remoteBin=$pathServer/bin
remoteDeploy=$pathServer/deploy
remoteConfig=/ccv/data/dsi_cyber/microbiz-1.0.0/config
remoteServer=dsi_cyber@slv2395t.etat-de-vaud.ch
#contourne l'alerte de sécurité de SSH
chmod 600 ${WORKSPACE}/config/jenkins/id.rsa.jenkins
sshOptions="-v -o StrictHostKeyChecking=no -i ${WORKSPACE}/config/jenkins/id.rsa.jenkins"

echo Rechercher bundle à déployer $component : `ls $projectBasedir/target/$component-*.jar`

if [ -f $projectBasedir/target/$component-*.jar ]
then
	 echo Nouveau bundle à déployer: `ls ${WORKSPACE}/poc-demaut/target/$component-*.jar`
else
     echo "Pas de bundle à déployer dans target. Veuillez compiler le projet"
    exit 0
fi

echo "WARNING : You should copy jenkins public key to 'ssh-copy-id $remoteServer' or enter $remoteServer server password!"

echo "Stop du container MicroBiz..."
ssh $sshOptions $remoteServer $remoteBin/microbiz stop
echo "Stop du container MicroBiz terminé"


echo "waiting 5s....."
sleep 5
echo "Mise à jour du fichier de configuration sur $remoteServer:$remoteConfig..."
scp $sshOptions "${WORKSPACE}/poc-demaut/conf/$component.cfg" $remoteServer:$remoteConfig
echo "Mise à jour du fichier de configuration terminée"


echo "Suppression de l'ancien bundle sur le serveur $remoteServer:$remoteDeploy"
echo "Emplacement du fichier sur le serveur:"
ssh $sshOptions $remoteServer ls $remoteDeploy/$component-*.jar
echo "Suppression..."
ssh $sshOptions $remoteServer rm $remoteDeploy/$component-*.jar
echo "waiting 5s....."
sleep 5
echo "deploying new bundle  in $remoteServer:$remoteDeploy"
ls ${WORKSPACE}/poc-demaut/target/$component-*.jar
scp $sshOptions ${WORKSPACE}/poc-demaut/target/$component-*.jar $remoteServer:$remoteDeploy
echo "waiting 5s....."
sleep 5
echo "Start du container MicroBiz..."
ssh $sshOptions $remoteServer $remoteBin/microbiz start
echo "Start du container MicroBiz terminé"
echo "Status du container MicroBiz..."
ssh $sshOptions $remoteServer $remoteBin/microbiz status

