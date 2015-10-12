#language: fr

@txn @creation-demande
Fonctionnalité: Créer une demande

  Contexte: 
    Etant donné la date du jour: "15.07.2015 11:00"
    Etant donné l´utilisateur identifié et connecté avec le login "joe.dalton@vd.ch"
    Etant donné les professions nécessitant un code GLN obligatoire:
      | Medecin                    |
      | MedecinDentiste            |
      | Pharmacien                 |
      | Chiropraticien             |
      | Infirmier                  |
      | Physiotherapeute           |
      | Ergotherapeute             |
      | SageFemme                  |
      | Dieteticien                |
      | PsychotherapeuteNonMedecin |

  Scénario: Créer une demande
    Lorsque l´utilisateur initialise une demande de profession "Medecin" avec un code GLN valide
    Alors le système Demaut crée la demande avec les caractéristiques [état: "Brouillon", utilisateur: "joe.dalton@vd.ch", type: "Medecin"]
  
  Scénario: Refuser de créer une demande sans code GLN
    Lorsque l´utilisateur initialise une demande de profession "Medecin" sans code GLN
    Alors le système Demaut refuse de créer la demande

  Scénario: Créer une demande même sans code GLN
    Lorsque l´utilisateur initialise une demande de profession "Osteopathe" sans code GLN
    Alors le système Demaut crée la demande avec les caractéristiques [état: "Brouillon", utilisateur: "joe.dalton@vd.ch", type: "Osteopathe"]
