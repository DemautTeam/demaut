#language: fr

@txn @creation-demande
Fonctionnalité: Créer une demande

  Contexte:
    Etant donné la date du jour: "15-07-2015 11:00"
    Etant donné l´utilisateur identifié et connecté avec le login "joe.dalton@vd.ch"

  Scénario: Créer une demande à l état Brouillon
    Lorsque l´utilisateur initialise une demande de profession "Medecin"
    Alors le système Demaut crée la demande avec les caractéristiques [état: "Brouillon", utilisateur: "joe.dalton@vd.ch", type: "Medecin"] 
