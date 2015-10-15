#language: fr

@donnees-perso
Fonctionnalité: Saisir les données personnelles de la demande

  Contexte:
    Etant donné la date du jour: "15.07.2015 11:00"
    Etant donné l´utilisateur identifié et connecté avec le login "joe.dalton@vd.ch"
    Etant donné une demande de profession "Medecin" en cours de saisie ayant la référence "12345"

  Plan du scénario: Saisir le nom du demandeur
    Lorsque l´utilisateur saisit ses donnees personnelles: nom=<nom>, prenom=<prenom>
    Alors le système Demaut <action> les données personnelles
    Exemples:
      | nom           | prenom | action    |
      | "dos dantois" | "Jon"  | "accepte" |
      | ""            | "el"   | "refuse"  |
      | "Doe"         | ""     | "refuse"  |
      | ""            | ""     | "refuse"  |


  @ignoreme
  Scénario: Saisir le nom du demandeur trop long
    Lorsque l´utilisateur saisit son nom "ghhoihoijhohohuhuhiuhiuhiuhiuhiuhiuhiuhuigwghgwwghhoihoijhohohuhuhiuhiuhiuhiuhiuhiuhiuhuigwghgwwghhoihoijhohohuhuhiuhiuhiuhiuhiuhiuhiuhuigwghgwwghhoihoijhohohuhuhiuhiuhiuhiuhiuhiuhiuhuigwghgwwghhoihoijhohohuhuhiuhiuhiuhiuhiuhiuhiuhuigwghgwwghhoihoijhohohuhuhiuhiuhiuhiuhiuhiuhiuhuigwghgww"
    Alors le système Demaut "refuse" les données personnelles

  Plan du scénario: Saisie de la nationnalité
    Lorsque le demandeur saisit la nationalité <nationalite> et le permis <permis>
    Alors le système Demaut <action> les données personnelles
    Exemples:
      | nationalite   | permis   | action    |
      | "suisse"      | "Aucun"  | "accepte" |
      | "brésilienne" | "B"      | "accepte" |
      | "française"   | "Aucun"  | "refuse"  |
      | ""            | "Aucun"  | "refuse"  |
