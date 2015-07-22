$(document).ready(function() {var formatter = new CucumberHTML.DOMFormatter($('.cucumber-report'));formatter.uri("attacher-annexes.feature");
formatter.feature({
  "id": "attacher-une-ou-plusieurs-annexes-à-une-demande",
  "tags": [
    {
      "name": "@annexes",
      "line": 3
    }
  ],
  "description": "",
  "name": "Attacher une ou plusieurs annexes à une demande",
  "keyword": "Fonctionnalité",
  "line": 4,
  "comments": [
    {
      "value": "#language: fr",
      "line": 1
    }
  ]
});
formatter.before({
  "duration": 140455706,
  "status": "passed"
});
formatter.background({
  "description": "",
  "name": "",
  "keyword": "Contexte",
  "line": 6,
  "type": "background"
});
formatter.step({
  "name": "la date du jour: \"15.07.2015 11:00\"",
  "keyword": "Etant donné ",
  "line": 7
});
formatter.step({
  "name": "le demandeur professionnel \"DALTON, Joe\"",
  "keyword": "Etant donné ",
  "line": 9
});
formatter.step({
  "name": "qu\u0027il a une demande d\u0027autorisation de type \"Medecin\" en cours de saisie",
  "keyword": "Etant donné ",
  "line": 10
});
formatter.match({
  "arguments": [
    {
      "val": "15.07.2015 11:00",
      "offset": 18
    }
  ],
  "location": "BackgroundStepDefinitions.date_du_jour(DateTime)"
});
formatter.result({
  "duration": 133330145,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "DALTON, Joe",
      "offset": 28
    }
  ],
  "location": "AttacherAnnexeStepDefinitions.le_demandeur_professionnel(NomEtPrenomDemandeur)"
});
formatter.result({
  "duration": 36787668,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "Medecin",
      "offset": 44
    }
  ],
  "location": "AttacherAnnexeStepDefinitions.qu_il_a_une_demande_d_autorisation_de_type_en_cours_de_saisie(ProfessionDeLaSante)"
});
formatter.result({
  "duration": 2434385,
  "status": "passed"
});
formatter.scenario({
  "id": "attacher-une-ou-plusieurs-annexes-à-une-demande;attacher-une-annexe-valide-à-une-demande",
  "description": "",
  "name": "Attacher une annexe valide à une demande",
  "keyword": "Scénario",
  "line": 12,
  "type": "scenario"
});
formatter.step({
  "name": "le fichier \"attestation-valide.pdf\" est un PDF non vide",
  "keyword": "Etant donné ",
  "line": 13
});
formatter.step({
  "name": "qu\u0027il attache le fichier \"attestation-valide.pdf\" de type \"Certificat\" à cette demande",
  "keyword": "Lorsque ",
  "line": 14
});
formatter.step({
  "name": "le système Demaut annexe le fichier à la demande avec succès",
  "keyword": "Alors ",
  "line": 15
});
formatter.match({
  "arguments": [
    {
      "val": "attestation-valide.pdf",
      "offset": 12
    }
  ],
  "location": "AttacherAnnexeStepDefinitions.etant_donne_un_fichier_valide(String)"
});
formatter.result({
  "duration": 729562,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "attestation-valide.pdf",
      "offset": 26
    },
    {
      "val": "Certificat",
      "offset": 59
    }
  ],
  "location": "AttacherAnnexeStepDefinitions.lorsque_il_attache_le_fichier_à_cette_demande(String,TypeAnnexe)"
});
formatter.result({
  "duration": 1140738,
  "status": "passed"
});
formatter.match({
  "location": "AttacherAnnexeStepDefinitions.le_système_Demaut_annexe_le_fichier_à_la_demande()"
});
formatter.result({
  "duration": 29995241,
  "status": "passed"
});
formatter.after({
  "duration": 159045,
  "status": "passed"
});
formatter.before({
  "duration": 194537,
  "status": "passed"
});
formatter.background({
  "description": "",
  "name": "",
  "keyword": "Contexte",
  "line": 6,
  "type": "background"
});
formatter.step({
  "name": "la date du jour: \"15.07.2015 11:00\"",
  "keyword": "Etant donné ",
  "line": 7
});
formatter.step({
  "name": "le demandeur professionnel \"DALTON, Joe\"",
  "keyword": "Etant donné ",
  "line": 9
});
formatter.step({
  "name": "qu\u0027il a une demande d\u0027autorisation de type \"Medecin\" en cours de saisie",
  "keyword": "Etant donné ",
  "line": 10
});
formatter.match({
  "arguments": [
    {
      "val": "15.07.2015 11:00",
      "offset": 18
    }
  ],
  "location": "BackgroundStepDefinitions.date_du_jour(DateTime)"
});
formatter.result({
  "duration": 311996,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "DALTON, Joe",
      "offset": 28
    }
  ],
  "location": "AttacherAnnexeStepDefinitions.le_demandeur_professionnel(NomEtPrenomDemandeur)"
});
formatter.result({
  "duration": 696992,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "Medecin",
      "offset": 44
    }
  ],
  "location": "AttacherAnnexeStepDefinitions.qu_il_a_une_demande_d_autorisation_de_type_en_cours_de_saisie(ProfessionDeLaSante)"
});
formatter.result({
  "duration": 353356,
  "status": "passed"
});
formatter.scenario({
  "id": "attacher-une-ou-plusieurs-annexes-à-une-demande;refuser-un-fichier-vide",
  "description": "",
  "name": "Refuser un fichier vide",
  "keyword": "Scénario",
  "line": 17,
  "type": "scenario"
});
formatter.step({
  "name": "le fichier \"attestation-non-valide.pdf\" est un PDF vide",
  "keyword": "Etant donné ",
  "line": 18
});
formatter.step({
  "name": "qu\u0027il attache le fichier \"attestation-non-valide.pdf\" de type \"Certificat\" à cette demande",
  "keyword": "Lorsque ",
  "line": 19
});
formatter.step({
  "name": "le système Demaut n\u0027annexe pas le fichier à la demande car il est vide",
  "keyword": "Alors ",
  "line": 20
});
formatter.match({
  "arguments": [
    {
      "val": "attestation-non-valide.pdf",
      "offset": 12
    }
  ],
  "location": "AttacherAnnexeStepDefinitions.etant_donne_un_fichier_invalide(String)"
});
formatter.result({
  "duration": 172455,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "attestation-non-valide.pdf",
      "offset": 26
    },
    {
      "val": "Certificat",
      "offset": 63
    }
  ],
  "location": "AttacherAnnexeStepDefinitions.lorsque_il_attache_le_fichier_à_cette_demande(String,TypeAnnexe)"
});
formatter.result({
  "duration": 148921,
  "status": "passed"
});
formatter.match({
  "location": "AttacherAnnexeStepDefinitions.le_système_Demaut_refuse_le_fichier()"
});
formatter.result({
  "duration": 562659,
  "status": "passed"
});
formatter.after({
  "duration": 109768,
  "status": "passed"
});
formatter.uri("charger-annexe.feature");
formatter.feature({
  "id": "attacher-une-ou-plusieurs-annexes-à-une-demande-nouvelle",
  "tags": [
    {
      "name": "@annexes",
      "line": 3
    }
  ],
  "description": "",
  "name": "Attacher une ou plusieurs annexes à une demande nouvelle",
  "keyword": "Fonctionnalité",
  "line": 4,
  "comments": [
    {
      "value": "#language: fr",
      "line": 1
    }
  ]
});
formatter.before({
  "duration": 183115,
  "status": "passed"
});
formatter.scenario({
  "id": "attacher-une-ou-plusieurs-annexes-à-une-demande-nouvelle;attacher-une-annexe-valide-à-une-demande-nouvelle",
  "description": "",
  "name": "Attacher une annexe valide à une demande nouvelle",
  "keyword": "Scénario",
  "line": 6,
  "type": "scenario"
});
formatter.step({
  "name": "le demandeur fait une nouvelle demande",
  "keyword": "Etant donné ",
  "line": 7
});
formatter.step({
  "name": "ce dernier veut attacher une annexe à cette demande",
  "keyword": "Lorsque ",
  "line": 8
});
formatter.step({
  "name": "le system Demaut valide av que l annexe est valide",
  "keyword": "Alors ",
  "line": 9
});
formatter.step({
  "name": "le systeme stocke l annexe et confirme la bonne reception de l annexe",
  "keyword": "Alors ",
  "line": 10
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.after({
  "duration": 149960,
  "status": "passed"
});
formatter.uri("soumettre-demande.feature");
formatter.feature({
  "id": "soumettre-une-demande-en-cours-de-saisie",
  "tags": [
    {
      "name": "@annexes",
      "line": 3
    }
  ],
  "description": "",
  "name": "Soumettre une demande en cours de saisie",
  "keyword": "Fonctionnalité",
  "line": 4,
  "comments": [
    {
      "value": "#language: fr",
      "line": 1
    }
  ]
});
formatter.before({
  "duration": 172119,
  "status": "passed"
});
formatter.background({
  "description": "",
  "name": "",
  "keyword": "Contexte",
  "line": 6,
  "type": "background"
});
formatter.step({
  "name": "la date du jour: \"15.07.2015 11:00\"",
  "keyword": "Etant donné ",
  "line": 7
});
formatter.step({
  "name": "le demandeur professionnel \"DALTON, Joe\"",
  "keyword": "Etant donné ",
  "line": 9
});
formatter.step({
  "name": "qu\u0027il a une demande d\u0027autorisation de type \"Medecin\" en cours de saisie",
  "keyword": "Etant donné ",
  "line": 10
});
formatter.match({
  "arguments": [
    {
      "val": "15.07.2015 11:00",
      "offset": 18
    }
  ],
  "location": "BackgroundStepDefinitions.date_du_jour(DateTime)"
});
formatter.result({
  "duration": 269748,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "DALTON, Joe",
      "offset": 28
    }
  ],
  "location": "AttacherAnnexeStepDefinitions.le_demandeur_professionnel(NomEtPrenomDemandeur)"
});
formatter.result({
  "duration": 622809,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "Medecin",
      "offset": 44
    }
  ],
  "location": "AttacherAnnexeStepDefinitions.qu_il_a_une_demande_d_autorisation_de_type_en_cours_de_saisie(ProfessionDeLaSante)"
});
formatter.result({
  "duration": 349395,
  "status": "passed"
});
formatter.scenario({
  "id": "soumettre-une-demande-en-cours-de-saisie;soumettre-une-demande-valide",
  "description": "",
  "name": "Soumettre une demande valide",
  "keyword": "Scénario",
  "line": 12,
  "type": "scenario"
});
formatter.step({
  "name": "que le nom et prénom \"DALTON, Joe\" sont renseignés dans les données personnelles de la demande",
  "keyword": "Etant donné ",
  "line": 13
});
formatter.step({
  "name": "que le demandeur professionnel soumet sa demande au système Demaut",
  "keyword": "Lorsque ",
  "line": 14
});
formatter.step({
  "name": "le système Demaut accepte la soumission de la demande",
  "keyword": "Alors ",
  "line": 15
});
formatter.step({
  "name": "la demande passe dans l\u0027état \"Soumise\"",
  "keyword": "Et ",
  "line": 16
});
formatter.step({
  "name": "la date de soumission de la demande est \"15.07.2015 11:00\"",
  "keyword": "Et ",
  "line": 17
});
formatter.match({
  "arguments": [
    {
      "val": "DALTON, Joe",
      "offset": 22
    }
  ],
  "location": "SoumettreDemandeStepDefinitions.que_le_nom_et_prénom_sont_renseignés_dans_les_données_personnelles_de_la_demande(String)"
});
formatter.result({
  "duration": 360101,
  "status": "passed"
});
formatter.match({
  "location": "SoumettreDemandeStepDefinitions.que_le_demandeur_professionnel_soumet_sa_demande_au_système_Demaut()"
});
formatter.result({
  "duration": 24168,
  "status": "passed"
});
formatter.match({
  "location": "SoumettreDemandeStepDefinitions.le_système_Demaut_accepte_la_soumission_de_la_demande()"
});
formatter.result({
  "duration": 21507,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "Soumise",
      "offset": 30
    }
  ],
  "location": "SoumettreDemandeStepDefinitions.la_demande_passe_dans_l_état(String)"
});
formatter.result({
  "duration": 52246,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "15.07.2015 11:00",
      "offset": 41
    }
  ],
  "location": "SoumettreDemandeStepDefinitions.la_date_de_soumission_de_la_demande_est(String)"
});
formatter.result({
  "duration": 52635,
  "status": "passed"
});
formatter.after({
  "duration": 100304,
  "status": "passed"
});
formatter.before({
  "duration": 196730,
  "status": "passed"
});
formatter.background({
  "description": "",
  "name": "",
  "keyword": "Contexte",
  "line": 6,
  "type": "background"
});
formatter.step({
  "name": "la date du jour: \"15.07.2015 11:00\"",
  "keyword": "Etant donné ",
  "line": 7
});
formatter.step({
  "name": "le demandeur professionnel \"DALTON, Joe\"",
  "keyword": "Etant donné ",
  "line": 9
});
formatter.step({
  "name": "qu\u0027il a une demande d\u0027autorisation de type \"Medecin\" en cours de saisie",
  "keyword": "Etant donné ",
  "line": 10
});
formatter.match({
  "arguments": [
    {
      "val": "15.07.2015 11:00",
      "offset": 18
    }
  ],
  "location": "BackgroundStepDefinitions.date_du_jour(DateTime)"
});
formatter.result({
  "duration": 367251,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "DALTON, Joe",
      "offset": 28
    }
  ],
  "location": "AttacherAnnexeStepDefinitions.le_demandeur_professionnel(NomEtPrenomDemandeur)"
});
formatter.result({
  "duration": 747830,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "Medecin",
      "offset": 44
    }
  ],
  "location": "AttacherAnnexeStepDefinitions.qu_il_a_une_demande_d_autorisation_de_type_en_cours_de_saisie(ProfessionDeLaSante)"
});
formatter.result({
  "duration": 444657,
  "status": "passed"
});
formatter.scenario({
  "id": "soumettre-une-demande-en-cours-de-saisie;refuser-la-soumission-d\u0027une-demande-non-valide",
  "description": "",
  "name": "Refuser la soumission d\u0027une demande non valide",
  "keyword": "Scénario",
  "line": 19,
  "type": "scenario"
});
formatter.step({
  "name": "que le nom et prénom n\u0027ont pas été renseignés dans les données personnelles de la demande",
  "keyword": "Etant donné ",
  "line": 20
});
formatter.step({
  "name": "que le demandeur professionnel soumet sa demande au système Demaut",
  "keyword": "Lorsque ",
  "line": 21
});
formatter.step({
  "name": "le système Demaut refuse la soumission de la demande",
  "keyword": "Alors ",
  "line": 22
});
formatter.step({
  "name": "la demande reste dans l\u0027état \"Brouillon\"",
  "keyword": "Et ",
  "line": 23
});
formatter.step({
  "name": "il n\u0027y a pas de date de soumission de la demande",
  "keyword": "Et ",
  "line": 24
});
formatter.match({
  "location": "SoumettreDemandeStepDefinitions.que_le_nom_et_prénom_n_ont_pas_été_renseignés_dans_les_données_personnelles_de_la_demande()"
});
formatter.result({
  "duration": 125766,
  "status": "passed"
});
formatter.match({
  "location": "SoumettreDemandeStepDefinitions.que_le_demandeur_professionnel_soumet_sa_demande_au_système_Demaut()"
});
formatter.result({
  "duration": 28780,
  "status": "passed"
});
formatter.match({
  "location": "SoumettreDemandeStepDefinitions.le_système_Demaut_refuse_la_soumission_de_la_demande()"
});
formatter.result({
  "duration": 26322,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "Brouillon",
      "offset": 30
    }
  ],
  "location": "SoumettreDemandeStepDefinitions.la_demande_reste_dans_l_état(String)"
});
formatter.result({
  "duration": 73808,
  "status": "passed"
});
formatter.match({
  "location": "SoumettreDemandeStepDefinitions.il_n_y_a_pas_de_date_de_soumission_de_la_demande()"
});
formatter.result({
  "duration": 24941,
  "status": "passed"
});
formatter.after({
  "duration": 149476,
  "status": "passed"
});
});