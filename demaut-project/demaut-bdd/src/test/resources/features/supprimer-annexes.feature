#language: fr

@annexes
Fonctionnalité: Supprimer des annexes à la demande par le professionnel

  Contexte:

	Etant donné l´utilisateur identifié et connecté avec le login "joe.dalton@vd.ch"

  @supprimer-annexe
  Plan du scénario: Réussir à supprimer une annexe en fonction du nom de fichier
    Etant donné une demande de profession "Medecin" en cours de saisie l´utilisateur
	Etant donné les annexes <nom_fichier> déja saisies par l'utilisateur:
	  | Type d'annexe 	    | Nom du fichier            |
	  | CertificatDeTravail 			| certificat.pdf 		    |
	  | Diplome 	        | diplome.jpg               |
  	Lorsque l´utilisateur supprime le fichier <nom_fichier> de type "Certificat"
  	Alors le système Demaut <action> de supprimer cette annexe
  	Exemples:
    	| nom_fichier       | action    |
    	| "certificat.pdf"  | "accepte" |
    	| "diplome.jpg"     | "accepte" |
    	| "cv.jpeg"         | "refuse"  |