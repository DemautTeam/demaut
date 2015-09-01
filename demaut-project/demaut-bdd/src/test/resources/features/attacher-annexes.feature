#language: fr

@annexes
Fonctionnalité: Attacher des annexes à la demande par le professionnel

  Contexte:
  	Etant donné la date du jour: "15.07.2015 11:00"
    Etant donné un demandeur identifié "DALTON, Joe" 
    Etant donné une demande de type "Medecin" en cours de saisie
	Etant donné les formats de fichier acceptés:
    	| pdf  |
    	| jpg  |
    	| jpeg |
    	| png  |
    Etant donné la taille maximale de fichier acceptée "10M"

  Plan du scénario: Accepter ou refuser les annexes en fonction du format de fichier 
  	Lorsque le demandeur attache le fichier <nom_fichier> de taille <taille_fichier>M
  	Alors le système Demaut <action> d´attacher cette annexe
  	Alors les annexes attachées à la demande sont <annexes> 
  	Exemples:
    	| nom_fichier       | taille_fichier  | action    | annexes      |
    	| "certificat.pdf"  | 5               | "accepte" | "certificat.pdf" |
    	| "certificat.jpg"  | 5               | "accepte" | "certificat.jpg" |
    	| "certificat.jpeg" | 5               | "accepte" | "certificat.jpeg" |
    	| "certificat.png"  | 5               | "accepte" | "certificat.png" |
    	| "certificat.bmp"  | 5               | "refuse"  | "" |
    	| "certificat.gif"  | 5               | "refuse"  | "" |
    	| "certificat.docx" | 5               | "refuse"  | "" |
    	| "certificat.xls"  | 5               | "refuse"  | "" |
    	| "certificat.exe"  | 5               | "refuse"  | "" |

  Plan du scénario: Accepter ou refuser les annexes en fonction de la taille du fichier 
  	Lorsque le demandeur attache le fichier <nom_fichier> de taille <taille_fichier>M
  	Alors le système Demaut <action> d´attacher cette annexe
  	Alors les annexes attachées à la demande sont <annexes> 
  	Exemples:
    	| nom_fichier       | taille_fichier  | action    | annexes      |
    	| "certificat.pdf"  | 0               | "refuse"  | "" |
    	| "certificat.pdf"  | 5               | "accepte" | "certificat.pdf" |
    	| "certificat.pdf"  | 10              | "accepte" | "certificat.pdf" |
    	| "certificat.pdf"  | 11              | "refuse"  | "" |
    	| "certificat.pdf"  | 200             | "refuse"  | "" |
    	

    	