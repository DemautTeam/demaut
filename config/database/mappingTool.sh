#!/bin/bash

DEMAUT_PRJ=../../demaut-project
DEMAUT_DOMAIN=$DEMAUT_PRJ/demaut-domain
DEMAUT_COMMONS=$DEMAUT_PRJ/demaut-commons
DEMAUT_DATA_JPA=$DEMAUT_PRJ/demaut-data-jpa

CLASSPATH="./*:$DEMAUT_COMMONS/target/*:$DEMAUT_DOMAIN/target/*:$DEMAUT_DATA_JPA/src/main/resources"


TOOL=org.apache.openjpa.jdbc.meta.MappingTool

java -cp $CLASSPATH $TOOL -sa build -sql demaut-h2-openjpa.ddl -p persistence-h2.xml
