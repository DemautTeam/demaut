#language: fr

@annexes
Fonctionnalité: Gestion des annexes liées à la demande

  Contexte:
    Etant donné les formats de fichier acceptés:
      | pdf  |
      | jpg  |
      | jpeg |
      | png  |
    Etant donné la taille maximale de fichier acceptée "3"MB
    Etant donné la date du jour: "15.07.2015 11:00"
    Etant donné l´utilisateur identifié et connecté avec le login "joe.dalton@vd.ch"

  @attacher-annexe
  Scénario: Attacher une annexe
    Etant donné une demande de profession "Medecin" en cours de saisie ayant la référence "12345"
    Etant donné les annexes déja saisies:
      | Nom du fichier |
      | pieceID.pdf    |
      | diplome.jpg    |
      | pieceID2.pdf   |
    Lorsque l´utilisateur attache le fichier "equivalence.pdf" de taille 1M
    Alors le système Demaut "accepte" d´attacher cette annexe
    Alors les annexes attachées à la demande "12345" sont:
      | Nom du fichier |
      | pieceID.pdf    |
      | diplome.jpg    |
      | equivalence.pdf|
      | pieceID2.pdf   |

  @attacher-annexe
  Scénario: Refuser d´attacher une annexe de même nom de fichier
    Etant donné une demande de profession "Medecin" en cours de saisie ayant la référence "12345"
    Etant donné les annexes déja saisies:
      | Nom du fichier |
      | pieceID.pdf    |
      | diplome.jpg    |
      | pieceID2.pdf   |
    Lorsque l´utilisateur attache le fichier "pieceID.pdf" de taille 1M
    Alors le système Demaut "refuse" d´attacher cette annexe

  @supprimer-annexe
  Scénario: Supprimer une annexe en fonction du nom de fichier
    Etant donné une demande de profession "Medecin" en cours de saisie ayant la référence "12345"
    Etant donné les annexes déja saisies:
      | Nom du fichier |
      | pieceID.pdf    |
      | diplome.jpg    |
      | pieceID2.pdf   |
    Lorsque l´utilisateur supprime le fichier "pieceID.pdf"
    Alors le système Demaut "accepte" de supprimer cette annexe
    Alors les annexes attachées à la demande "12345" sont:
      | Nom du fichier |
      | diplome.jpg    |
      | pieceID2.pdf   |

  @format-fichier
  Plan du scénario: Accepter ou refuser les annexes en fonction du format de fichier
    Etant donné une demande de profession "Medecin" en cours de saisie ayant la référence "12345"
    Lorsque l´utilisateur attache le fichier <nom_fichier> de taille 2M
    Alors le système Demaut <action> d´attacher cette annexe
    Exemples:
      | nom_fichier       | action    |
      | "certificat.pdf"  | "accepte" |
      | "certificat.jpg"  | "accepte" |
      | "certificat.jpeg" | "accepte" |
      | "certificat.png"  | "accepte" |
      | "certificat.bmp"  | "refuse"  |
      | "certificat.gif"  | "refuse"  |
      | "certificat.docx" | "refuse"  |
      | "certificat.xls"  | "refuse"  |
      | "certificat.exe"  | "refuse"  |

  @taille-fichier
  Plan du scénario: Accepter ou refuser les annexes en fonction de la taille du fichier
    Etant donné une demande de profession "Medecin" en cours de saisie ayant la référence "12345"
    Lorsque l´utilisateur attache le fichier "certificat.pdf" de taille <taille_fichier>M
    Alors le système Demaut <action> d´attacher cette annexe
    Exemples:
      | taille_fichier | action    |
      | 0              | "refuse"  |
      | 2              | "accepte" |
      | 3              | "accepte" |
      | 4              | "refuse"  |
      | 200            | "refuse"  |

  @annexes-obligatoires
  Plan du scénario: Lister les annexes obligatoires
    Etant donnée les catégories universitaires suivantes:
      | Medecin        |
      | MedecinDentiste|
      | Chiropraticien |
      | Pharmacien     |
    Etant donné une demande de profession <profession> en cours de saisie ayant la référence "12345"
    Etant donné que le demandeur a <nb_act> activité(s) antérieure(s)
    Etant donné que le demandeur est de nationalité <nationalite>
    Etant donné que l´utilisateur a <nb_dipl> diplome(s) étranger(s)   
    Lorsque le système Demaut calcule les annexes obligatoires à fournir 
    Alors cette demande est dans le cas d´une procédure <type_proc>
    Alors cette profession est de catégorie <categorie_prof> 
    Alors les types d´annexes obligatoires à fournir sont: CV=<CV>;Diplome=<Diplome>;Titre=<Titre>;Equivalence=<Equivalence>;CertificatDeTravail=<CertificatDeTravail>;AttestationBonneConduite=<AttestationBonneConduite>;CertificatMedical=<CertificatMedical>;AutorisationPratiquer=<AutorisationPratiquer>;ResponsabiliteCivile=<ResponsabiliteCivile>;PieceIdentite=<PieceIdentite>       
    Exemples:
      | profession         | nb_act | nationalite | nb_dipl | type_proc     | categorie_prof    | CV  | Diplome | Titre | Equivalence | CertificatDeTravail | ExtraitCasierJudiciaire | AttestationBonneConduite | CertificatMedical | AutorisationPratiquer | ResponsabiliteCivile | PieceIdentite |
      | "Medecin"          | 0      |"Suisse"     | 0       | "Ordinaire"   | "Universitaire"   |"oui"|"oui"    |"oui"  |"non"        |"non"                |"oui"                    |"non"                     |"oui"              |"non"                  |"oui"                 |"oui"          |
      | "Medecin"          | 0      |"France"     | 1       | "Ordinaire"   | "Universitaire"   |"oui"|"oui"    |"oui"  |"oui"        |"non"                |"oui"                    |"oui"                     |"oui"              |"non"                  |"oui"                 |"oui"          |
      | "MedecinDentiste"  | 0      |"Suisse"     | 0       | "Ordinaire"   | "Universitaire"   |"oui"|"oui"    |"non"  |"non"        |"oui"                |"oui"                    |"non"                     |"oui"              |"non"                  |"oui"                 |"oui"          |
      | "Chiropraticien"   | 0      |"Suisse"     | 0       | "Ordinaire"   | "Universitaire"   |"oui"|"oui"    |"oui"  |"non"        |"oui"                |"oui"                    |"non"                     |"oui"              |"non"                  |"oui"                 |"oui"          |
      | "Pharmacien"       | 0      |"Suisse"     | 0       | "Ordinaire"   | "Universitaire"   |"oui"|"oui"    |"non"  |"non"        |"oui"                |"oui"                    |"non"                     |"oui"              |"non"                  |"oui"                 |"oui"          | 
      | "Dieteticien"      | 0      |"Suisse"     | 0       | "Ordinaire"   | "Autre"           |"oui"|"oui"    |"non"  |"non"        |"oui"                |"oui"                    |"non"                     |"oui"              |"non"                  |"oui"                 |"oui"          |
      | "Osteopathe"       | 0      |"France"     | 1       | "Ordinaire"   | "Autre"           |"oui"|"oui"    |"non"  |"oui"        |"oui"                |"oui"                    |"non"                     |"oui"              |"non"                  |"oui"                 |"oui"          |
      | "Medecin"          | 1      |"Suisse"     | 0       | "Simplifiee"  | "Universitaire"   |"non"|"oui"    |"oui"  |"non"        |"non"                |"non"                    |"oui"                     |"non"              |"oui"                  |"oui"                 |"oui"          |
      | "Medecin"          | 1      |"France"     | 1       | "Simplifiee"  | "Universitaire"   |"non"|"oui"    |"oui"  |"oui"        |"non"                |"non"                    |"oui"                     |"non"              |"oui"                  |"oui"                 |"oui"          |
      | "MedecinDentiste"  | 1      |"Suisse"     | 0       | "Simplifiee"  | "Universitaire"   |"non"|"oui"    |"non"  |"non"        |"non"                |"non"                    |"oui"                     |"non"              |"oui"                  |"oui"                 |"oui"          |
      | "Chiropraticien"   | 2      |"Suisse"     | 0       | "Simplifiee"  | "Universitaire"   |"non"|"oui"    |"oui"  |"non"        |"non"                |"non"                    |"oui"                     |"non"              |"oui"                  |"oui"                 |"oui"          |
      | "Pharmacien"       | 1      |"Suisse"     | 0       | "Simplifiee"  | "Universitaire"   |"non"|"oui"    |"non"  |"non"        |"non"                |"non"                    |"oui"                     |"non"              |"oui"                  |"oui"                 |"oui"          | 
      | "Dieteticien"      | 1      |"Suisse"     | 0       | "Simplifiee"  | "Autre"           |"non"|"oui"    |"non"  |"non"        |"non"                |"non"                    |"oui"                     |"non"              |"oui"                  |"oui"                 |"oui"          |
      | "Osteopathe"       | 1      |"France"     | 1       | "Simplifiee"  | "Autre"           |"non"|"oui"    |"non"  |"oui"        |"non"                |"non"                    |"oui"                     |"non"              |"oui"                  |"oui"                 |"oui"          |      

