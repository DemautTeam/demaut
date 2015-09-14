package ch.vd.demaut.services.demandes.autorisation.impl;

import ch.vd.demaut.domain.annexes.Annexe;
import ch.vd.demaut.domain.annexes.AnnexeMetadata;
import ch.vd.demaut.domain.annexes.TypeAnnexe;
import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisation;
import ch.vd.demaut.domain.demandes.autorisation.ProfessionDeLaSante;
import ch.vd.demaut.domain.demandes.autorisation.factory.DemandeAutorisationFactory;
import ch.vd.demaut.domain.demandes.autorisation.repo.DemandeAutorisationRepository;
import ch.vd.demaut.services.demandes.autorisation.DemandeAutorisationService;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class DemandeAutorisationServiceImpl implements DemandeAutorisationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DemandeAutorisationServiceImpl.class);

    @Autowired
    private DemandeAutorisationRepository demandeAutorisationRepository;

    private DemandeAutorisationFactory demandeAutorisationFactory = DemandeAutorisationFactory.getInstance();

    @Override
    public DemandeAutorisation initialiserDemandeAutorisation() {
        return demandeAutorisationFactory.inititierDemandeAutorisation();
    }

    @Override
    public DemandeAutorisation afficherUneDemandeAutorisation(String demandeReference) {
        return demandeAutorisationRepository.afficherUneDemandeAutorisation(demandeReference);
    }

    @Override
    public DemandeAutorisation sauverLaDemandeAutorisation(DemandeAutorisation demandeAutorisation) {
        return demandeAutorisationRepository.sauverLaDemandeAutorisation(demandeAutorisation);
    }

    /**
     * L'annexe retournée de préférence ne devrait PAS contenir le contunu stream pour la consultation front
     *
     * @param demandeReference String
     * @return Collection AnnexeMetadata
     */
    @Override
    @SuppressWarnings("unchecked")
    public Collection<AnnexeMetadata> listerLesAnnexeMetadatas(String demandeReference) {
        DemandeAutorisation demandeAutorisation = this.afficherUneDemandeAutorisation(demandeReference);
        return demandeAutorisation != null ? demandeAutorisation.listerLesAnnexeMetadatas() : new ArrayList<AnnexeMetadata>();
    }

    /**
     * L'annexe retournée DOIT absolument contenir le contunu stream pour la consultation front
     *
     * @param demandeReference String
     * @param annexeFileName   String
     * @return Annexe
     */
    @Override
    public Annexe afficherUneAnnexe(String demandeReference, String annexeFileName) {
        DemandeAutorisation demandeAutorisation = this.afficherUneDemandeAutorisation(demandeReference);
        return demandeAutorisation != null ? demandeAutorisation.afficherUneAnnexe(annexeFileName) : null;
    }

    @Override
    public boolean attacherUneAnnexe(String demandeReference, File file, String annexeFileName, String annexeFileSize, String annexeFileType, String annexeType) {
        DemandeAutorisation demandeAutorisation = this.afficherUneDemandeAutorisation(demandeReference);
        if (demandeAutorisation != null) {
            try {
                Annexe annexe = new Annexe(TypeAnnexe.valueOf(annexeType), annexeFileName, IOUtils.toByteArray(new FileInputStream(file)));
                demandeAutorisation.attacherUneAnnexe(annexe);
                sauverLaDemandeAutorisation(demandeAutorisation);
                return true;
            } catch (IOException e) {
                LOGGER.error("Exception attacherUneAnnexe " + e.getMessage());
                return false;
            }
        }
        return false;
    }

    @Override
    public boolean supprimerUneAnnexe(String demandeReference, String annexeFileName, String annexeType) {
        DemandeAutorisation demandeAutorisation = this.afficherUneDemandeAutorisation(demandeReference);
        if (demandeAutorisation != null) {
            if (demandeAutorisation.supprimerUneAnnexe(annexeFileName)) {
                sauverLaDemandeAutorisation(demandeAutorisation);
                return true;
            }
        }
        return false;
    }

    @Override
    public ProfessionDeLaSante afficherDonneesProfession(String demandeReference) {
        DemandeAutorisation demandeAutorisation = this.afficherUneDemandeAutorisation(demandeReference);
        return demandeAutorisation != null ? demandeAutorisation.getProfessionDeLaSante() : null;
    }
}
