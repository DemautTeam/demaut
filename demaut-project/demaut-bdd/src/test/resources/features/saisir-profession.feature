#language: fr

@donnees-prof
Fonctionnalité: Saisir les données professionnelles de la demande

  Contexte:
    Etant donné la date du jour: "15.07.2015 11:00"
    Etant donné l´utilisateur identifié et connecté avec le login "joe.dalton@vd.ch"


  @saisir-profession
  Plan du scénario: Saisir la profession du demandeur
    Etant donné aucune demande de profession "Medecin" en cours de saisie à l´état "Brouillon"
    Etant donné un demandeur de profession "Medecin"
    Lorsque l´utilisateur initialise une nouvelle demande de profession "Medecin"
    Alors le système Demaut crée la demande avec la donnée de la profession
    Exemples:
      |  |

  @saisir-gln
  Plan du scénario: Saisir le code gln du demandeur
    Etant donné une demande de profession "Medecin" en cours de saisie à l´état "Brouillon"
    Lorsque l´utilisateur saisit son code <gln> de "13" catactères
    Alors le système Demaut <action> les données professionnelles avec un <message> en cas d´échec
    Exemples:
      | gln              | action    | message               |
      | "7601000000125"  | "accepte" | ""                    |
      | ""               | "refuse"  | "N° GLN non conforme" |
      | "g7601000000125" | "refuse"  | "N° GLN non conforme" |

