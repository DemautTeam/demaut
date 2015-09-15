package ch.vd.demaut.services.demandes.autorisation;

import java.io.File;
import java.util.Collection;

import org.springframework.stereotype.Service;

import ch.vd.demaut.domain.annexes.AnnexeMetadata;
import ch.vd.demaut.domain.annexes.ContenuAnnexe;
import ch.vd.demaut.domain.annexes.NomFichier;
import ch.vd.demaut.domain.annexes.TypeAnnexe;
import ch.vd.demaut.domain.demandes.ReferenceDeDemande;
import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisation;

@Service
public interface DemandeAutorisationService {

    DemandeAutorisation recupererDemandeParReference(ReferenceDeDemande ref);

    Collection<AnnexeMetadata> listerLesAnnexesMetadatas(ReferenceDeDemande ref);

    ContenuAnnexe recupererContenuAnnexe(ReferenceDeDemande ref, NomFichier nomFichier);

    void attacherUneAnnexe(ReferenceDeDemande ref, File file, NomFichier nomFichier, TypeAnnexe type);

    void supprimerUneAnnexe(ReferenceDeDemande ref, NomFichier nomFichier);
}

