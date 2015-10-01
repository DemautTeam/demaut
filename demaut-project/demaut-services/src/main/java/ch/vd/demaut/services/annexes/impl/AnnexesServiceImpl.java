package ch.vd.demaut.services.annexes.impl;

import ch.vd.demaut.domain.annexes.*;
import ch.vd.demaut.domain.demandes.ReferenceDeDemande;
import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisation;
import ch.vd.demaut.domain.exception.AnnexeNonValideException;
import ch.vd.demaut.services.annexes.AnnexesService;
import ch.vd.demaut.services.demandes.autorisation.DemandeAutorisationService;
import org.apache.commons.io.IOUtils;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
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
     * @param referenceDeDemande ReferenceDeDemande
     * @return Collection AnnexeMetadata
     */
    @Transactional(readOnly = true)
    @Override
    public Collection<AnnexeMetadata> listerLesAnnexeMetadatas(ReferenceDeDemande referenceDeDemande) {
        DemandeAutorisation demandeAutorisation = recupererDemandeParRef(referenceDeDemande);
        return demandeAutorisation.listerLesAnnexeMetadatas();
    }

    /**
     * L'annexe retournée DOIT absolument contenir le contunu stream pour la
     * consultation front
     *
     * @param referenceDeDemande ReferenceDeDemande
     * @param annexeFK           AnnexeFK
     * @return Annexe
     */
    @Transactional(readOnly = true)
    @Override
    public ContenuAnnexe recupererContenuAnnexe(ReferenceDeDemande referenceDeDemande, AnnexeFK annexeFK) {
        DemandeAutorisation demandeAutorisation = recupererDemandeParRef(referenceDeDemande);
        return demandeAutorisation.extraireContenuAnnexe(annexeFK);
    }

    @Transactional
    @Override
    public void attacherUneAnnexe(ReferenceDeDemande referenceDeDemande, File file, NomFichier nomFichier, TypeAnnexe type) {
        ContenuAnnexe contenuAnnexe = buildContenuAnnexe(file);
        Annexe annexe = new Annexe(type, nomFichier, contenuAnnexe, new DateCreation(new LocalDate()));
        attacherAnnexe(referenceDeDemande, annexe);
    }

    @Transactional
    @Override
    public void attacherUneAnnexe(ReferenceDeDemande referenceDeDemande, Annexe annexe) {
        attacherAnnexe(referenceDeDemande, annexe);
    }

    @Transactional
    @Override
    public void supprimerUneAnnexe(ReferenceDeDemande referenceDeDemande, AnnexeFK annexeFK) {
        DemandeAutorisation demandeAutorisation = recupererDemandeParRef(referenceDeDemande);
        demandeAutorisation.supprimerUneAnnexe(annexeFK);
    }

    private void attacherAnnexe(ReferenceDeDemande ref, Annexe annexe) {
        DemandeAutorisation demandeAutorisation = recupererDemandeParRef(ref);
        demandeAutorisation.validerEtAttacherAnnexe(annexe);
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
