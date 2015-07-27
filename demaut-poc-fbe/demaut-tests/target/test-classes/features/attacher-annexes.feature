#language: fr

@annexes
Fonctionnalité: Attacher une ou plusieurs annexes à une demande

  Contexte:
  	Etant donné la date du jour: "15.07.2015 11:00"

    Etant donné le demandeur professionnel "DALTON, Joe"
    Etant donné qu'il a une demande d'autorisation de type "Medecin" en cours de saisie

  Scénario: Attacher une annexe valide à une demande
  	Etant donné le fichier "attestation-valide.pdf" est un PDF non vide
    Lorsque qu'il attache le fichier "attestation-valide.pdf" de type "Certificat" à cette demande
    Alors le système Demaut annexe le fichier à la demande avec succès

  Scénario: Refuser un fichier vide
  	Etant donné le fichier "attestation-non-valide.pdf" est un PDF vide
    Lorsque qu'il attache le fichier "attestation-non-valide.pdf" de type "Certificat" à cette demande
    Alors le système Demaut n'annexe pas le fichier à la demande car il est vide

