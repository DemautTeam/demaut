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

  @controler-telephones
  Plan du Scénario: Contrôler le format du téléphone
    Etant donné le numéro de téléphone <pro> professionnel, le téléphone <mobile> mobile et le numéro de fax <fax> renseignés par l'utilisateur
    Lorsque le demandeur ajoute cette activité future
    Alors le système Demaut <action> les numéros de télephone

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
    Etant donné  l'email <email> renseignés par l'utilisateur
    Lorsque le demandeur ajoute cette activité future
    Alors le système Demaut <action> les numéros de télephone

    Exemples:
      | email          | action  |
      | toto@titi.com  | accepte |
      | +41123456      | refuse  |
      | toto@titicom   | accepte |
      |                | refuse  |

  @controler-nom
  Plan du Scénario: Contrôler le nom de l'établissement
    Etant donné  le nom de l'établissement <nom> renseignés par l'utilisateur
    Lorsque le demandeur ajoute cette activité future
    Alors le système Demaut <action> le nom de l'établissement

    Exemples:
      | nom               | action  |
      | Centre Medical    | accepte |
      |                   | refuse  |
      | avec 120 cararcteres. Aasjklaskjkajkaj sjdklsaldk askdlklaskd as dksajdkajskdjasdjksa djs djkaskd jaksjdkajk121314244dfa             | accepte  |
      | plus de 120 cararcteres aasjklaskjkajkaj sjdklsaldk askdlklaskd as dksajdkajskdjasdjksa djs djkaskd jaksjdkajkjkjksajkkjkja          | refuse  |



  @controler-formulaire-rempli
  Plan du Scénario: Contrôler le formulaire rempli
    Etant donné  les champs requis nom <nom>, adresse <adresse>, npa <npa>, localité <localite>, téléphone <telephone>, email <email>, renseignés par l'utilisateur
    Lorsque le demandeur ajoute cette activité future
    Alors le système Demaut <action> le formulaires d'activité futures

    Exemples:
      | nom             | adresse         | npa   | localite   | telephone   | email          | action  |
      |  Centre medical | rue de zurich   | 1200  | Lausanne   | 0223334444  | toto@titi.com  | accepte |
      |                 | rue de zurich   |       |            |             |                | refuse  |
      |                 |                 | 1200  |            |             |                | refuse  |
      |                 |                 |       | Lausanne   |             |                | refuse  |
      |                 |                 |       |            | 0223334444  |                | refuse  |
      |                 |                 |       |            |             | toto@titi.com  | refuse  |



