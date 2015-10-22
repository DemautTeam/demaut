#!/bin/sh

current_dir=$(pwd)
script_dir=$(dirname $0)

echo "Current dir $current_dir"
echo "Script dir $script_dir"

if [ $script_dir = '.' ]
then
script_dir="$current_dir"
fi

projectFolderName=$(basename $script_dir)

cd $script_dir
mvn clean install assembly:single

# deploy to smx4
component=$projectFolderName
localServer=/DEMAUT/microbiz-1.0.0
localBin=$localServer/bin
localDeploy=$localServer/deploy
localConfig=/$localServer/config

echo Rechercher bundle à déployer $component: `ls target/$component-*.jar`

if [ -f target/$component-*.jar ]
then
	 echo Nouveau bundle à déployer: `ls target/$component-*.jar`
else
     echo "Pas de bundle à déployer dans target. Veuillez compiler le projet"
    exit 0
fi

echo "Stop du container MicroBiz $localBin/..."
$localBin/microbiz stop
echo "Stop du container MicroBiz terminé"

echo "waiting 5s....."
sleep 5
echo "Mise à jour du fichier de configuration sur $localConfig..."
cp conf/$component.cfg $localConfig
echo "Mise à jour du fichier de configuration terminée"


echo "Suppression de l'ancien bundle sur le serveur $localDeploy"
echo "Emplacement du fichier sur le serveur:"
ls $localDeploy/$component-*.jar
echo "Suppression..."
rm $localDeploy/$component-*.jar
echo "waiting 5s....."
sleep 5
echo "deploying new bundle  in $localDeploy"
ls target/$component-*.jar
cp target/$component-*.jar $localDeploy
echo "waiting 5s....."
sleep 5
echo "Start du container MicroBiz..."
$localBin/microbiz start
echo "Start du container MicroBiz terminé"
echo "Status du container MicroBiz..."
$localBin/microbiz status