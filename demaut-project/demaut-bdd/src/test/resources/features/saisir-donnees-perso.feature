#language: fr
@donnees-perso
Fonctionnalité: Saisir les données personnelles de la demande

  Contexte: 
    Etant donné la date du jour: "15.10.2015 11:00"
    Etant donné l´utilisateur identifié et connecté avec le login "john.doe@noone.ch"
    Etant donné une demande de profession "Medecin" en cours de saisie ayant la référence "12345"

  @nom-demandeur
  Plan du scénario: Saisir le nom du demandeur
    Lorsque l´utilisateur saisit ses donnees personnelles: nom=<nom>, prenom=<prenom>
    Alors le système Demaut <action> les données personnelles
    Exemples: 
      | nom           | prenom | action    |
      | "dos dantois" | "Jon"  | "accepte" |
      | ""            | "el"   | "refuse"  |
      | "Doe"         | ""     | "refuse"  |
      | ""            | ""     | "refuse"  |

  @nom-demandeur
  Scénario: Saisir le nom du demandeur trop long
    Etant donné la taille maximale du nom configurée à 120 caractères
    Lorsque l´utilisateur saisit un nom d´une longueur de 121 caractères
    Alors le système Demaut "refuse" les données personnelles
  
  @nom-demandeur
  Scénario: Saisir le nom du demandeur avec le nombre maximum de caractères
    Etant donné la taille maximale du nom configurée à 120 caractères
    Lorsque l´utilisateur saisit un nom d´une longueur de 120 caractères
    Alors le système Demaut "accepte" les données personnelles

  @nationalite-demandeur
  Plan du scénario: Saisie de la nationnalité
    Lorsque le demandeur saisit la nationalité <nationalite> et le permis <permis>
    Alors le système Demaut <action> les données personnelles

    Exemples: 
      | nationalite | permis  | action    |
      | "Suisse"    | "Aucun" | "accepte" |
      | "Bresil"    | "B"     | "accepte" |
      | "France"    | "Aucun" | "refuse"  |
      | ""          | "Aucun" | "refuse"  |

  @tel-demandeur
  Plan du scénario: Saisir les numéros de téléphone fixe ou mobile
    Lorsque l´utilisateur saisit ses donnees personnelles: téléphone privé=<numeroPrive>, Téléphone Mobile=<numeroMobile>
    Alors le système Demaut <action> les données personnelles

    Exemples: 
      | numeroPrive    | numeroMobile | action    |
      | "0226543210"   | ""           | "accepte" |
      | "+41226543210" | ""           | "accepte" |
      | "2265"         | ""           | "refuse"  |
      | ""             | "0767894545" | "accepte" |
      | "0222222222"   | "0789745621" | "accepte" |
      | ""             | ""           | "refuse"  |
