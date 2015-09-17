package ch.vd.demaut.services.annexes;

import ch.vd.demaut.domain.annexes.*;
import ch.vd.demaut.domain.demandes.ReferenceDeDemande;

import java.io.File;
import java.util.Collection;

public interface AnnexesService {

    Collection<AnnexeMetadata> listerLesAnnexeMetadatas(ReferenceDeDemande referenceDeDemande);

    ContenuAnnexe recupererContenuAnnexe(ReferenceDeDemande referenceDeDemande, NomFichier nomFichier);

    void attacherUneAnnexe(ReferenceDeDemande referenceDeDemande, File file, NomFichier nomFichier, TypeAnnexe type);

    void attacherUneAnnexe(ReferenceDeDemande referenceDeDemande, Annexe annexeALier);

    void supprimerUneAnnexe(ReferenceDeDemande referenceDeDemande, NomFichier nomFichier);

}
