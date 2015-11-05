#language: fr
@activites-futures @ignoreme
Fonctionnalité: Vérification du code NPA

  Contexte: 
    Etant donné la date du jour: "15.10.2015 11:00"
    Etant donné l´utilisateur identifié et connecté avec le login "john.doe@noone.ch"
    Etant donné une demande de profession "Medecin" en cours de saisie ayant la référence "12345"

  @ajout-activite-future
  Plan du Scénario: Contrôler le format du code NPA Suisse
    Etant donné le code NPA <npa> renseigné par l´utilisateur
    Etant donné que tous les autres champs de l´activité future sont remplis et valides
    Lorsque le demandeur ajoute cette activité future
    Alors le système Demaut <action> ce code NPA en tant que NPA Suisse avec quatre caractères

    Exemples: 
      | npa     | action  |
      | "1234"  | accepte |
      | ""      | refuse  |
      | "1"     | refuse  |
      | "12"    | refuse  |
      | "123"   | refuse  |
      | "12345" | refuse  |
      | "123a"  | refuse  |

  Plan du Scénario: Contrôler le format du téléphone
    Etant donné le numéro de téléphone <tel> renseigné par l'utilisateur
    Etant donné que tous les autres champs de l'activité future sont remplis et valides
    Lorsque le demandeur valide ses activités futures
    Alors le système Demaut <action> ce code NPA en tant que NPA Suisse avec quatre caractères

    Exemples: 
      | npa    | action  |
      | "1234" | accepte |
      | ""     | refuse  |
