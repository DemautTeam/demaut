--INSERT INTO DEMANDE_AUTORISATION (ID) VALUES (100)
INSERT INTO DEMANDE_AUTORISATION (id, reference, professionDeLaSante, statutDemandeAutorisation, version)
VALUES (100, '7dc53df5-703e-49b3-8670-b1c468f47f1f', 8, 0, 1);

INSERT INTO ANNEXE (id, contenu, nomfichier, taille, typeAnnexe, version, demande_id)
VALUES (10, '', 'Test_annexe_find.pdf', 208900, 1, 1, 100);
INSERT INTO ANNEXE (id, contenu, nomfichier, taille, typeAnnexe, version, demande_id)
VALUES (11, '', 'Test_annexe_image.png', 372700, 2, 1, 100);

