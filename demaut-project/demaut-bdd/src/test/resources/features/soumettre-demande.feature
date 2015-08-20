#language: fr

@soumission-demande
Fonctionnalité: Soumettre une demande en cours de saisie

  Contexte:
  	Etant donné la date du jour: "15.07.2015 11:00"

    Etant donné le demandeur professionnel "DALTON, Joe"
    Etant donné qu'il a une demande d'autorisation de type "Medecin" en cours de saisie

  Scénario: Soumettre une demande valide
	Etant donné que le nom et prénom "DALTON, Joe" sont renseignés dans les données personnelles de la demande
    Lorsque que le demandeur professionnel soumet sa demande au système Demaut
    Alors le système Demaut accepte la soumission de la demande
    Et la demande passe dans l'état "Soumise"
    Et la date de soumission de la demande est "15.07.2015 11:00"

  Scénario: Refuser la soumission d'une demande non valide
	Etant donné que le nom et prénom n'ont pas été renseignés dans les données personnelles de la demande
    Lorsque que le demandeur professionnel soumet sa demande au système Demaut
    Alors le système Demaut refuse la soumission de la demande
    Et la demande reste dans l'état "Brouillon"
    Et il n'y a pas de date de soumission de la demande

