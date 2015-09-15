package ch.vd.demaut.services.annexes.impl;

import ch.vd.demaut.domain.annexes.*;
import ch.vd.demaut.domain.demandes.ReferenceDeDemande;
import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisation;
import ch.vd.demaut.services.annexes.AnnexesService;
import ch.vd.demaut.services.demandes.autorisation.DemandeAutorisationService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collection;

@Service("annexesService")
public class AnnexesServiceImpl implements AnnexesService {

    @Autowired
    private DemandeAutorisationService demandeAutorisationService;

    @Override
    @SuppressWarnings("unchecked")
    public Collection<AnnexeMetadata> listerLesAnnexeMetadatas(ReferenceDeDemande referenceDeDemande) {
        DemandeAutorisation demandeAutorisation = demandeAutorisationService.recupererDemandeParReference(referenceDeDemande);
        return demandeAutorisation.listerLesAnnexeMetadatas();
    }

    @Override
    public ContenuAnnexe afficherUneAnnexe(ReferenceDeDemande referenceDeDemande, NomFichier nomFichier) {
        DemandeAutorisation demandeAutorisation = demandeAutorisationService.recupererDemandeParReference(referenceDeDemande);
        return demandeAutorisation.extraireContenuAnnexe(nomFichier);
    }

    @Transactional
    @Override
    public boolean attacherUneAnnexe(ReferenceDeDemande referenceDeDemande, File file, NomFichier nomFichier, TypeAnnexe typeAnnexe) throws IOException {
        DemandeAutorisation demandeAutorisation = demandeAutorisationService.recupererDemandeParReference(referenceDeDemande);
        ContenuAnnexe contenuAnnexe = this.buildContenuAnnexe(file);
        Annexe annexe = new Annexe(typeAnnexe, nomFichier, contenuAnnexe);
        demandeAutorisation.validerEtAttacherAnnexe(annexe);
        return true;
    }

    @Transactional
    @Override
    public boolean supprimerUneAnnexe(ReferenceDeDemande referenceDeDemande, NomFichier nomFichier, TypeAnnexe typeAnnexe) {
        DemandeAutorisation demandeAutorisation = demandeAutorisationService.recupererDemandeParReference(referenceDeDemande);
        demandeAutorisation.supprimerUneAnnexeParNomFichier(nomFichier);
        return true;
    }

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
