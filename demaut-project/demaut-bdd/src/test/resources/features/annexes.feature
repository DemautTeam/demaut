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
    Etant donné une demande de profession "Medecin" en cours de saisie ayant la référence "12345"
    Etant donné les annexes déja saisies:
      | Nom du fichier |
      | pieceID.pdf    |
      | diplome.jpg    |
      | pieceID2.pdf   |

  @attacher-annexe
  Scénario: Attacher une annexe
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
    Lorsque l´utilisateur attache le fichier "pieceID.pdf" de taille 1M
    Alors le système Demaut "refuse" d´attacher cette annexe

  @supprimer-annexe
  Scénario: Supprimer une annexe en fonction du nom de fichier
    Lorsque l´utilisateur supprime le fichier "pieceID.pdf"
    Alors le système Demaut "accepte" de supprimer cette annexe
    Alors les annexes attachées à la demande "12345" sont:
      | Nom du fichier |
      | diplome.jpg    |
      | pieceID2.pdf   |

  @format-fichier
  Plan du scénario: Accepter ou refuser les annexes en fonction du format de fichier
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
    Lorsque l´utilisateur attache le fichier "certificat.pdf" de taille <taille_fichier>M
    Alors le système Demaut <action> d´attacher cette annexe
    Exemples:
      | taille_fichier | action    |
      | 0              | "refuse"  |
      | 2              | "accepte" |
      | 3              | "accepte" |
      | 4              | "refuse"  |
      | 200            | "refuse"  |

