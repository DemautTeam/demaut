#language: fr

@creation-demande
Fonctionnalité: Créer une demande

  Contexte:
  	Etant donné la date du jour: "15.07.2015 11:00"
    Etant donné le demandeur professionnel "DALTON, Joe"

  Scénario: Créer une demande à l état Brouillon
    Lorsque que le demandeur initialise une demande de type "Medecin"
    Alors le système Demaut crée la demande avec les caractéristiques [état: "Brouillon", demandeur: "DALTON, Joe", type: "Medecin"] 
