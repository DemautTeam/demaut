package ch.vd.demaut.services.annexes;

import java.io.File;
import java.util.Collection;

import ch.vd.demaut.domain.annexes.Annexe;
import ch.vd.demaut.domain.annexes.AnnexeMetadata;
import ch.vd.demaut.domain.annexes.ContenuAnnexe;
import ch.vd.demaut.domain.annexes.NomFichier;
import ch.vd.demaut.domain.annexes.TypeAnnexe;
import ch.vd.demaut.domain.demandes.ReferenceDeDemande;

public interface AnnexesService {

    Collection<AnnexeMetadata> listerLesAnnexeMetadatas(ReferenceDeDemande referenceDeDemande);

    ContenuAnnexe recupererContenuAnnexe(ReferenceDeDemande ref, NomFichier nomFichier);

	void attacherUneAnnexe(ReferenceDeDemande ref, File file, NomFichier nomFichier, TypeAnnexe type);

	void attacherUneAnnexe(ReferenceDeDemande ref, Annexe annexeALier);

	void supprimerUneAnnexe(ReferenceDeDemande ref, NomFichier nomFichier);

}
