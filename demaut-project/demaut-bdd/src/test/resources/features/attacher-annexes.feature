#language: fr

@annexes
Fonctionnalité: Attacher des annexes à la demande par le professionnel

  Contexte:
    Etant donné les formats de fichier acceptés:
      | pdf  |
      | jpg  |
      | jpeg |
      | png  |
    Etant donné la taille maximale de fichier acceptée "3"MB
    Etant donné les annexes obligatoires par type de demande:
      | Type de demande | Types d´annexe obligatoires |
      | Medecin         | Certificat,Diplome          |
      | Dieteticien     | Certificat                  |

    Etant donné la date du jour: "15.07.2015 11:00"
    Etant donné l´utilisateur identifié et connecté avec le login "joe.dalton@vd.ch"

  @format-fichier
  Plan du scénario: Accepter ou refuser les annexes en fonction du format de fichier
    Etant donné une demande de profession "Medecin" en cours de saisie
    Lorsque l´utilisateur attache le fichier <nom_fichier> de taille 2M de type "Certificat"
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
    Etant donné une demande de profession "Medecin" en cours de saisie
    Lorsque l´utilisateur attache le fichier "certificat.pdf" de taille <taille_fichier>M de type "Certificat"
    Alors le système Demaut <action> d´attacher cette annexe
    Exemples:
      | taille_fichier | action    |
      | 0              | "refuse"  |
      | 2              | "accepte" |
      | 3              | "accepte" |
      | 4              | "refuse"  |
      | 200            | "refuse"  |

  @attacher-annexe
  Plan du scénario: Attacher une annexe à une liste existante
    Etant donné une demande de profession "Medecin" en cours de saisie
    Etant donné la liste des annexes initiale <annexes_initiales> attachées à la demande en cours
    Lorsque l´utilisateur attache le fichier "certificat.pdf" de taille 2M de type "Certificat"
    Alors le système Demaut "accepte" d´attacher cette annexe
    Alors les annexes attachées à la demande sont <annexes>
    Exemples:
      | annexes_initiales    | annexes                             |
      | ""                   | "certificat.pdf"                    |
      | "cv.pdf"             | "certificat.pdf,cv.pdf"             |
      | "cv.pdf,diplome.pdf" | "certificat.pdf,cv.pdf,diplome.pdf" |

  @type-demande @ignoreme
  Plan du scénario: Accepter ou refuser les annexes en fonction de leur type et du type de la demande en cours
    Etant donné une demande de profession <type_demande> en cours de saisie
    Lorsque l´utilisateur attache les annexes de type <types_annexe>
    Alors toutes les annexes obligatoires sont validés: <annexes_complet>
    Exemples:
      | type_demande | types_annexe         | annexes_complet |
      | "Medecin"    | "Diplome"            | "non"           |
      | "Medecin"    | "Certificat,Diplome" | "oui"           |
