--Demande 1
INSERT INTO SES_DEMAUT_DEV.DEMANDE
(ID, DTYPE, VERSION, DATE_CREATION, REFERENCE_DEMANDE, PROFESSION, STATUT_DEMANDE, DONNEESPERSONNELLES_ID, DONNEESPROFESSIONNELLES_ID, LOGIN)
VALUES(1, 'DemandeAutorisation', 3, TO_DATE('2015-01-01 01:00:00','YYYY-MM-DD HH24:MI:SS'), '9e88c31c-9cdf-4b8d-964a-b0af8fd06c1c', 'Medecin', 'Brouillon', 51, 101, 'demaut-user-id');


INSERT INTO SES_DEMAUT_DEV.DONNEES_PERSONNELLES
(ID, GENRE, LANGUE, NATIONALITE, VERSION, ADRESSE_ID, DATE_DE_NAISSANCE, EMAIL, FAX, NOM, NOM_DE_CELIBATAIRE, TYPE_PERMIS, AUTRE_PERMIS, PRENOM, TELEPHONE_MOBILE, TELEPHONE_PRIVE)
VALUES(51, 'Feminin', 12, 2, 3, 151, TO_DATE('2015-10-03 00:00:00','YYYY-MM-DD HH24:MI:SS'), '6666@emaiul.com', '233333333', 'eeeeeeeeeeeeee', NULL, 'B', NULL, 'eeeeeeeeeeeeee', '66666666', '1234566666');


INSERT INTO SES_DEMAUT_DEV.ADRESSE_PERSONNELLE
(ID, COMPLEMENT, PAYS, VOIE, VERSION, LOCALITE, NPA)
VALUES(151, NULL, 1, 'Adresse priveee', 2, 'Lausanne', '45678');


INSERT INTO SES_DEMAUT_DEV.DONNEES_PROFESSIONNELLES
(ID, VERSION, CODE_GLN)
VALUES(101, 3, '4719512002889');


INSERT INTO SES_DEMAUT_DEV.DIPLOME
(ID, COMPLEMENT, TYPE_DIPLOME_ACCEPTE, DIPLOMES_ID, DATE_OBTENTION, DATE_RECONNAISSANCE, PAYS_OBTENTION, REFERENCE_DIPLOME, TITRE_FORMATION)
VALUES(201, NULL, 'D_FORMATION_INITIALE', 101, TO_DATE('2015-10-03 00:00:00','YYYY-MM-DD HH24:MI:SS'), TO_DATE('2015-10-03 00:00:00','YYYY-MM-DD HH24:MI:SS'), 'Afghanistan', '3#344861006#SunOct04201500:00:00GMT+0200(CEST)', 'DiplFederalDeMedecin');


--Demande 2
INSERT INTO SES_DEMAUT_DEV.DEMANDE
(ID, DTYPE, VERSION, DATE_CREATION, REFERENCE_DEMANDE, PROFESSION, STATUT_DEMANDE, DONNEESPERSONNELLES_ID, DONNEESPROFESSIONNELLES_ID, LOGIN)
VALUES(2, 'DemandeAutorisation', 2, TO_DATE('2015-01-01 01:00:00','YYYY-MM-DD HH24:MI:SS'), '9e88c31c-9cdf-4b8d-964a-b0af8fd06c1b', 'Medecin', 'Brouillon', 52, 102, 'demaut-user-id');


INSERT INTO SES_DEMAUT_DEV.DONNEES_PERSONNELLES
(ID, GENRE, LANGUE, NATIONALITE, VERSION, ADRESSE_ID, DATE_DE_NAISSANCE, EMAIL, FAX, NOM, NOM_DE_CELIBATAIRE, TYPE_PERMIS, AUTRE_PERMIS, PRENOM, TELEPHONE_MOBILE, TELEPHONE_PRIVE)
VALUES(52, 'Feminin', 12, 2, 3, 153, TO_DATE('2015-10-03 00:00:00','YYYY-MM-DD HH24:MI:SS'), '6666@emaiul.com', '233333333', 'eeeeeeeeeeeeee', NULL, 'B', NULL, 'eeeeeeeeeeeeee', '66666666', '1234566666');


INSERT INTO SES_DEMAUT_DEV.ADRESSE_PERSONNELLE
(ID, COMPLEMENT, PAYS, VOIE, VERSION, LOCALITE, NPA)
VALUES(153, NULL, 1, 'Adresse priveee', 2, 'Lausanne', '45678');


INSERT INTO SES_DEMAUT_DEV.DONNEES_PROFESSIONNELLES
(ID, VERSION, CODE_GLN)
VALUES(102, 3, '4719512002889');


INSERT INTO SES_DEMAUT_DEV.DIPLOME
(ID, COMPLEMENT, TYPE_DIPLOME_ACCEPTE, DIPLOMES_ID, DATE_OBTENTION, DATE_RECONNAISSANCE, PAYS_OBTENTION, REFERENCE_DIPLOME, TITRE_FORMATION)
VALUES(202, NULL, 'D_FORMATION_INITIALE', 102, TO_DATE('2015-10-03 00:00:00','YYYY-MM-DD HH24:MI:SS'), TO_DATE('2015-10-03 00:00:00','YYYY-MM-DD HH24:MI:SS'), 'Afghanistan', '3#344861006#SunOct04201500:00:00GMT+0200(CEST)', 'DiplFederalDeMedecin');
