#language: fr

@donnees-perso
Fonctionnalité: Saisir les données personnelles de la demande

  Contexte:
    Etant donné la date du jour: "15.07.2015 11:00"
    Etant donné l´utilisateur identifié et connecté avec le login "joe.dalton@vd.ch"
    Etant donné une demande de profession "Medecin" en cours de saisie

  Plan du scénario: Saisir le nom du demandeur
    Etant donné une demande de profession "Medecin" en cours de saisie
    Lorsque l´utilisateur saisit son nom <nom>
    Alors le système Demaut <action> les données personnelles
    Exemples:
      | nom                                                                                                                                                        | action    |
      | "dos dantois"                                                                                                                                              | "accepte" |
      | ""                                                                                                                                                         | "refuse"  |
      | "ghhoihoijhohohuhuhiuhiuhiuhiuhiuhiuhiuhuigwghgwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww" | "refuse"  |


