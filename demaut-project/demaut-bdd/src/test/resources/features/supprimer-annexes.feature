#language: fr
@annexes
Fonctionnalité: Supprimer des annexes à la demande par le professionnel

  Contexte: 
    Etant donné l´utilisateur identifié et connecté avec le login "joe.dalton@vd.ch"
    Etant donné une demande de profession "Medecin" en cours de saisie par l´utilisateur

  @supprimer-annexe
  Plan du scénario: Supprimer une annexe en fonction du nom de fichier
    Etant donné les annexes déja saisies par l´utilisateur:
      | Type d´annexe       | Nom du fichier |
      | CertificatDeTravail | certificat.pdf |
      | Diplome             | diplome.jpg    |
    Lorsque l´utilisateur supprime le fichier <nom_fichier> de type "CertificatDeTravail"
    Alors le système Demaut <action> de supprimer cette annexe

    Exemples: 
      | nom_fichier      | action    |
      | "certificat.pdf" | "accepte" |
      | "diplome.jpg"    | "accepte" |
      | "cv.jpeg"        | "refuse"  |
