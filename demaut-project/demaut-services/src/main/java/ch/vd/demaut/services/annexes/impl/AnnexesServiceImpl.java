package ch.vd.demaut.services.annexes.impl;

import ch.vd.demaut.domain.annexes.*;
import ch.vd.demaut.domain.demandes.DateDeCreation;
import ch.vd.demaut.domain.demandes.ReferenceDeDemande;
import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisation;
import ch.vd.demaut.domain.exception.AnnexeNonValideException;
import ch.vd.demaut.domain.utilisateurs.Login;
import ch.vd.demaut.services.annexes.AnnexesService;
import ch.vd.demaut.services.demandes.autorisation.DemandeAutorisationService;
import org.apache.commons.io.IOUtils;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collection;

@Service("annexesService")
public class AnnexesServiceImpl implements AnnexesService {

    // ********************************************************* Services
    // injectes
    @Autowired
    private DemandeAutorisationService demandeAutorisationService;

    // ********************************************************* Implémentation
    // Services

    /**
     * L'annexe retournée de préférence ne devrait PAS contenir le contunu
     * stream pour la consultation front
     *
     * @param login Login
     * @return Collection AnnexeMetadata
     */
    @Transactional(readOnly = true)
    @Override
    public Collection<AnnexeMetadata> listerLesAnnexeMetadatas(Login login) {
        DemandeAutorisation demandeAutorisation = demandeAutorisationService.trouverDemandeBrouillonParUtilisateur(login);
        return demandeAutorisation.listerLesAnnexeMetadatas();
    }

    @Override
    public Collection<TypeAnnexe> listerLesTypeAnnexesObligatoires(Login login) {
        DemandeAutorisation demandeAutorisation = demandeAutorisationService.trouverDemandeBrouillonParUtilisateur(login);
        return demandeAutorisation.listerLesTypeAnnexesObligatoires();
    }

    /**
     * L'annexe retournée DOIT absolument contenir le contunu stream pour la
     * consultation front
     *
     * @param login    Login
     * @param annexeFK AnnexeFK
     * @return Annexe
     */
    @Transactional(readOnly = true)
    @Override
    public ContenuAnnexe recupererContenuAnnexe(Login login, AnnexeFK annexeFK) {
        DemandeAutorisation demandeAutorisation = demandeAutorisationService.trouverDemandeBrouillonParUtilisateur(login);
        return demandeAutorisation.extraireContenuAnnexe(annexeFK);
    }

    @Transactional
    @Override
    public void attacherUneAnnexe(Login login, File file, NomFichier nomFichier, TypeAnnexe type) {
        DemandeAutorisation demandeAutorisation = demandeAutorisationService.trouverDemandeBrouillonParUtilisateur(login);
        ContenuAnnexe contenuAnnexe = buildContenuAnnexe(file);
        Annexe annexe = new Annexe(type, nomFichier, contenuAnnexe, new DateDeCreation(new LocalDate()));
        demandeAutorisation.validerEtAttacherAnnexe(annexe);
    }

    @Transactional
    @Override
    public void attacherUneAnnexe(Login login, Annexe annexe) {
        DemandeAutorisation demandeAutorisation = demandeAutorisationService.trouverDemandeBrouillonParUtilisateur(login);
        demandeAutorisation.validerEtAttacherAnnexe(annexe);
    }

    @Transactional
    @Override
    public void supprimerUneAnnexe(Login login, AnnexeFK annexeFK) {
        DemandeAutorisation demandeAutorisation = demandeAutorisationService.trouverDemandeBrouillonParUtilisateur(login);
        demandeAutorisation.supprimerUneAnnexe(annexeFK);
    }

    // ********************************************************* Methodes
    // privees

    private ContenuAnnexe buildContenuAnnexe(File file) {
        byte[] contenu;
        try {
            contenu = IOUtils.toByteArray(new FileInputStream(file));
        } catch (IOException e) {
            throw new AnnexeNonValideException();
        }
        return new ContenuAnnexe(contenu);
    }

    private DemandeAutorisation recupererDemandeParRef(ReferenceDeDemande referenceDeDemande) {
        return demandeAutorisationService.recupererDemandeParReference(referenceDeDemande);
    }
}
