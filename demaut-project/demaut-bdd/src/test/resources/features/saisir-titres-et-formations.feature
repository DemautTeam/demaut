#language: fr

@donnees-diplomes
Fonctionnalité: Saisir les données professionnelles de la demande

  Contexte:
    Etant donné la date du jour: "15.07.2015 11:00"
    Etant donné l´utilisateur identifié et connecté avec le login "joe.dalton@vd.ch"
    Etant donné une demande de profession "Medecin" en cours de saisie à l´état "Brouillon"
    Etant donné les types de diplômes acceptés non vide:
      | D_FORMATION_APPROFONDIE     |
      | D_FORMATION_COMPLEMENTAIRE  |
      | D_FORMATION_INITIALE        |
      | D_POSTGRADE                 |

  @saisir-type-diplome
  Plan du scénario: Saisir un titre du demandeur
    Lorsque l´utilisateur selectionne un type de <diplome> dans la liste
    Alors le système Demaut retourne la liste des <formations> liéé au type de diplôme
    Exemples:
      | diplome                       |   formations                                                                                                                      |
      | "D_FORMATION_APPROFONDIE"     |   "Pneumologie pédiatrique /118,Cardiologie pédiatrique /114,Gastroentérologie pédiatrique /113"                                  |
      | "D_FORMATION_COMPLEMENTAIRE"  |   "Acupuncture - MTC /77,Electroencéphalographie /86,Electroneuromyographie /87,Homéopathie /78"                                  |
      | "D_FORMATION_INITIALE"        |   "Dipl. fédéral de médecin /1,Dipl. fédéral de médecin-dentiste /2,Dipl. fédéral de pharmacien /3,Dipl. fédéral d'opticien /4,CFR d'un diplôme étranger de médecin /8"   |
      | "D_POSTGRADE"                 |   "Allergologie et immunologie clinique /92,Anesthésiologie /2,Angiologie /119,Cardiologie /83"                                   |

  @saisir-date-obtention
  Plan du scénario: Saisir une date d´obtention du diplôme
    Lorsque l´utilisateur saisit la date d´obtention <date_obtention> du diplôme
    Alors le système Demaut <action> la date d´obtention <date_obtention> avec un <message> en cas d´échec
    Exemples:
      | date_obtention   | action    | message                       |
      | "12.12.2011"     | "accepte" | ""                            |
      | "32.12.2011"     | "refuse"  | "Date d'obtention non valide" |
      | "01.01.2025"     | "refuse"  | "Date d'obtention non valide" |

  @saisir-date-reconnaissance
  Plan du scénario: Saisir une date de reconnaissance du diplôme
    Lorsque l´utilisateur selectionne un type de formation obtenue à l´"étranger" dans la liste
    Alors le système Demaut <action> la date de reconnaissance <date_reconnaissance> avec un <message> en cas d´échec
    Exemples:
      | date_reconnaissance   | action    | message                       |
      | "12.12.2011"          | "accepte" | ""                            |
      | "32.12.2011"          | "refuse"  | "Date d'obtention non valide" |
      | "01.01.2025"          | "refuse"  | "Date d'obtention non valide" |
      | ""                    | "refuse"  | "Les renseignements concernant le diplôme étrangers ne sont pas complets" |

  @saisir-diplome
  Plan du scénario: Ajouter un diplôme à la liste des diplômes
    Etant donné l´utilisateur a déjà saisit un type de <diplome> dans la liste
    Etant donné l´utilisateur a déjà saisit le titre de formation <formation>
    Etant donné l´utilisateur a déjà saisit la date d´obtention <date_obtention> du diplôme
    Etant donné l´utilisateur a déjà saisit la date de reconnaissance <date_reconnaissance> du diplôme "étranger"
    Lorsque l´utilisateur initialise le diplôme en cours
    Alors le système Demaut <action> le diplome avec un <message> en cas d´échec
    Exemples:
      | diplome                       |   formation                                 | date_obtention   | date_reconnaissance | action    | message                                                                       |
      | "D_FORMATION_APPROFONDIE"     |   "Pneumologie pédiatrique /118"            | "12.12.2011"     | "12.12.2011"        | "accepte" | ""                                                                            |
      | "D_FORMATION_COMPLEMENTAIRE"  |   ""                                        | "12.12.2011"     | "12.12.2011"        | "refuse"  | "Les renseignements concernant le titre ou la formation ne sont pas complets" |
      | "D_FORMATION_INITIALE"        |   "CFR d'un diplôme étranger de médecin /8" | "12.12.2011"     | ""                  | "refuse"  | "Les renseignements concernant le diplôme étrangers ne sont pas complets"     |
