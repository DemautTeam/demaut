package ch.vd.demaut.services.annexes;

import ch.vd.demaut.domain.annexes.AnnexeMetadata;
import ch.vd.demaut.domain.annexes.ContenuAnnexe;
import ch.vd.demaut.domain.annexes.NomFichier;
import ch.vd.demaut.domain.annexes.TypeAnnexe;
import ch.vd.demaut.domain.demandes.ReferenceDeDemande;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

public interface AnnexesService {

    Collection<AnnexeMetadata> listerLesAnnexeMetadatas(ReferenceDeDemande referenceDeDemande);

    ContenuAnnexe afficherUneAnnexe(ReferenceDeDemande referenceDeDemande, NomFichier nomFichier);

    boolean attacherUneAnnexe(ReferenceDeDemande referenceDeDemande, File file, NomFichier nomFichier, TypeAnnexe typeAnnexe) throws IOException;

    boolean supprimerUneAnnexe(ReferenceDeDemande referenceDeDemande, NomFichier nomFichier, TypeAnnexe typeAnnexe);
}
