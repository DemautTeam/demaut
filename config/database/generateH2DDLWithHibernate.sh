#!/bin/bash

DEMAUT_PRJ=../../demaut-project
DEMAUT_DOMAIN=$DEMAUT_PRJ/demaut-domain
DEMAUT_COMMONS=$DEMAUT_PRJ/demaut-commons
DEMAUT_DATA_JPA=$DEMAUT_PRJ/demaut-data-jpa

CLASSPATH="./*:hibernate-5.0.0-Final/*:$DEMAUT_COMMONS/target/*:$DEMAUT_DOMAIN/target/*:$DEMAUT_DATA_JPA/src/main/resources"

TOOL=org.hibernate.tool.hbm2ddl.SchemaExport

java -cp $CLASSPATH $TOOL --properties=hibernate-h2.properties --output=demaut-h2-hibernate.ddl ../../demaut-project/demaut-data-jpa/src/main/resources/META-INF/orm.xml 
