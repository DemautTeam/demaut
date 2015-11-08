package ch.vd.demaut.services.annexes.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collection;

import org.apache.commons.io.IOUtils;
import org.springframework.transaction.annotation.Transactional;

import ch.vd.demaut.domain.annexes.Annexe;
import ch.vd.demaut.domain.annexes.AnnexeFK;
import ch.vd.demaut.domain.annexes.AnnexeMetadata;
import ch.vd.demaut.domain.annexes.ContenuAnnexe;
import ch.vd.demaut.domain.annexes.NomFichier;
import ch.vd.demaut.domain.annexes.TypeAnnexe;
import ch.vd.demaut.domain.demandes.ReferenceDeDemande;
import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisation;
import ch.vd.demaut.domain.demandes.autorisation.repo.DemandeAutorisationRepository;
import ch.vd.demaut.domain.exception.AnnexeNonValideException;
import ch.vd.demaut.services.annexes.AnnexesService;

public class AnnexesServiceImpl implements AnnexesService {

    // ********************************************************* Services
    private DemandeAutorisationRepository demandeAutorisationRepository;

    // ********************************************************* Implémentation Services

    /**
     * L'annexe retournée de préférence ne devrait PAS contenir le contunu
     * stream pour la consultation front
     *
     * @param login Login
     * @return Collection AnnexeMetadata
     */
    @Transactional(readOnly = true)
    @Override
    public Collection<AnnexeMetadata> listerLesAnnexeMetadatas(ReferenceDeDemande referenceDeDemande) {
        DemandeAutorisation demandeAutorisation = demandeAutorisationRepository.recupererDemandeParReference(referenceDeDemande);
        return demandeAutorisation.listerLesAnnexeMetadatas();
    }

    @Transactional(readOnly = true)
    @Override
    public Collection<TypeAnnexe> listerLesTypeAnnexesObligatoires(ReferenceDeDemande referenceDeDemande) {
        DemandeAutorisation demandeAutorisation = demandeAutorisationRepository.recupererDemandeParReference(referenceDeDemande);
        return demandeAutorisation.calculerTypesAnnexeObligatoires();
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
    public ContenuAnnexe recupererContenuAnnexe(ReferenceDeDemande referenceDeDemande, AnnexeFK annexeFK) {
        DemandeAutorisation demandeAutorisation = demandeAutorisationRepository.recupererDemandeParReference(referenceDeDemande);
        return demandeAutorisation.extraireContenuAnnexe(annexeFK);
    }

    @Transactional
    @Override
    public void attacherUneAnnexe(ReferenceDeDemande referenceDeDemande, File file, NomFichier nomFichier) {
        DemandeAutorisation demandeAutorisation = demandeAutorisationRepository.recupererDemandeParReference(referenceDeDemande);
        ContenuAnnexe contenuAnnexe = buildContenuAnnexe(file);
        Annexe annexe = new Annexe(nomFichier, contenuAnnexe);
        demandeAutorisation.validerEtAttacherAnnexe(annexe);
    }

    @Transactional
    @Override
    public void attacherUneAnnexe(ReferenceDeDemande referenceDeDemande, Annexe annexe) {
        DemandeAutorisation demandeAutorisation = demandeAutorisationRepository.recupererDemandeParReference(referenceDeDemande);
        demandeAutorisation.validerEtAttacherAnnexe(annexe);
    }

    @Transactional
    @Override
    public void supprimerUneAnnexe(ReferenceDeDemande referenceDeDemande, AnnexeFK annexeFK) {
        DemandeAutorisation demandeAutorisation = demandeAutorisationRepository.recupererDemandeParReference(referenceDeDemande);
        demandeAutorisation.supprimerUneAnnexe(annexeFK);
    }

    // ********************************************************* Setters for injection

    public void setDemandeAutorisationRepository(DemandeAutorisationRepository demandeAutorisationRepository) {
        this.demandeAutorisationRepository = demandeAutorisationRepository;
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
}
