#language: fr
@activites-futures
Fonctionnalité: Vérification du scenario de l'activité future

  Contexte: 
    Etant donné la date du jour: "15.10.2015 11:00"
    Etant donné l´utilisateur identifié et connecté avec le login "john.doe@noone.ch"
    Etant donné une demande de profession "Medecin" en cours de saisie ayant la référence "12345"
    Etant donné une activité future valide en cours de saisie par l´utilisateur

  @controler-npa
  Plan du Scénario: Contrôler le format du code NPA Suisse
    Etant donné le code NPA <npa> renseigné par l´utilisateur
    Lorsque le demandeur ajoute cette activité future
    Alors le système Demaut <action> d´ajouter cette activité future à la demande en cours
    Exemples: 
      | npa     | action  |
      | "1234"  | accepte |
      | ""      | refuse  |
      | "1"     | refuse  |
      | "12"    | refuse  |
      | "123"   | refuse  |
      | "12345" | refuse  |
      | "123a"  | refuse  |

  @controler-telephones
  Plan du Scénario: Contrôler le format du téléphone
    Etant donné le numéro de téléphone <pro> professionnel, le téléphone <mobile> mobile et le numéro de fax <fax> renseignés par l´utilisateur
    Lorsque le demandeur ajoute cette activité future
    Alors le système Demaut <action> d´ajouter cette activité future à la demande en cours

    Exemples: 
      | pro            | mobile      | fax          | action  |
      | 0123456        |             |              | accepte |
      | +41123456      | 012345      | +3345678912  | accepte |
      |                |             |              | refuse  |
      | 1              |             |              | refuse  |
      | 012345678901234|             |              | refuse  |
      | 123456         |             |              | refuse  |
      | 0123456        | 45+54a5     |              | refuse  |

  @controler-email
  Plan du Scénario: Contrôler le formulaire completement saisi est valide
    Etant donné  l´email <email> renseignés par l´utilisateur
    Lorsque le demandeur ajoute cette activité future
    Alors le système Demaut <action> d´ajouter cette activité future à la demande en cours

    Exemples:
      | email          | action  |
      | toto@titi.com  | accepte |
      | +41123456      | refuse  |

  @controler-nom-etablissement @ignoreme
  Plan du Scénario: Contrôler le nom de l´établissement
    Etant donné  le nom de l´établissement <nom> renseignés par l´utilisateur
    Lorsque le demandeur ajoute cette activité future
    Alors le système Demaut <action> d´ajouter cette activité future à la demande en cours

    Exemples:
      | nom               | action  |
      | "Centre Medical"  | accepte |



  @controler-formulaire-valide @ignoreme
  Plan du Scénario: Contrôler le formulaire completement saisi
    Etant donné  les champs requis nom <nom>, adresse <adresse>, npa <npa>, localité <localite>, téléphone <telephone>, email <email>, renseignés par l´utilisateur
    Lorsque le demandeur ajoute cette activité future
    Alors le système Demaut <action> d´ajouter cette activité future à la demande en cours

    Exemples:
      | nom             | adresse         | npa   | localite   | email          | action  |
      | "Centre medical"| "rue de zurich" | 1200  | Lausanne   | toto@titi.com  | accepte |



