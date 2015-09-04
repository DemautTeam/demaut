**********************
Database Config Tools
**********************

--- mappingTool.sh

-- Description --
- Permet de générer le schema DDL de Demaut pour un type de DB donné en se basant sur le fichier orm.xml.
- Actuellement seul H2 est configuré

-- Utilisation --
- "sh mappingTool.sh" => génère un fichier demaut-h2.ddl.sql sous le répertoire courant

-- TODO
- Rendre le chemin du fichier généré configurable
- Générer pour Oracle
- Intégrer le script généré dans la configuration des tests nécessitant JPA
- Générer le script automatiquement dans le cycle de vie Maven du projet data-jpa, phase "generate-sources" (notamment pour l'intégration continue)

