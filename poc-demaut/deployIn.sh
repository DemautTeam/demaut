#!/bin/sh

current_dir=$(pwd)
script_dir=$(dirname $0)

if [ $script_dir = '.' ]
then
script_dir="$current_dir"
fi

projectFolderName=$(basename $script_dir)

cd $script_dir
mvn clean package

# deploy to smx4
component=$projectFolderName
remoteDeploy=microbiz/deploy
remoteConfig=microbiz/config
remoteServer=microbiz@esb-ina

if [ -f target/$component-*.jar ]
then
	 echo Nouveau bundle à déployer: `ls target/$component-*.jar`
else
     echo "Pas de bundle à déployer dans target. Veuillez compiler le projet"
    exit 0
fi

echo "Mise à jour du fichier de configuration sur $remoteServer:$remoteConfig"
scp conf/$component.cfg $remoteServer:$remoteConfig
echo "Mise à jour du fichier de configuration terminée"


echo "Suppression de l'ancien bundle sur le serveur $remoteServer:$remoteDeploy"
echo "Emplacement du fichier sur le serveur:"
ssh $remoteServer ls $remoteDeploy/$component-*.jar
echo "Suppression..."
ssh $remoteServer rm $remoteDeploy/$component-*.jar
echo "waiting 5s....."
sleep 5
echo "deploying new bundle  in $remoteServer:$remoteDeploy"
ls target/$component-*.jar
scp target/$component-*.jar $remoteServer:$remoteDeploy