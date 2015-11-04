#language: fr
@donnees-activite-professionnelle @ignoreme
Fonctionnalité: Saisir les données de la future activité professionnelle

  Contexte: 
    Etant donné la date du jour: "15.10.2015 11:00"
    Etant donné l´utilisateur identifié et connecté avec le login "john.doe@noone.ch"
    Etant donné une demande de profession "Medecin" en cours de saisie ayant la référence "12345"

  Scénario: Contrôle de format du NPA Professionel
    Etant donné un NPA prof. de format numérique et de 4 caractères
    Lorsque le demandeur valide le formulaire
    Alors la saisie du NPA est accepté

