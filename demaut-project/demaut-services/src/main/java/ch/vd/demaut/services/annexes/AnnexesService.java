package ch.vd.demaut.services.annexes;

import ch.vd.demaut.domain.annexes.*;
import ch.vd.demaut.domain.demandes.ReferenceDeDemande;
import ch.vd.demaut.domain.utilisateurs.Login;

import java.io.File;
import java.util.Collection;

public interface AnnexesService {

    Collection<AnnexeMetadata> listerLesAnnexeMetadatas(Login login, ReferenceDeDemande referenceDeDemande);

    Collection<TypeAnnexe> listerLesTypeAnnexesObligatoires(Login login, ReferenceDeDemande referenceDeDemande);

    ContenuAnnexe recupererContenuAnnexe(Login login, ReferenceDeDemande referenceDeDemande, AnnexeFK annexeFK);

    void attacherUneAnnexe(Login login, ReferenceDeDemande referenceDeDemande, File file, NomFichier nomFichier, TypeAnnexe type);

    void attacherUneAnnexe(Login login, ReferenceDeDemande referenceDeDemande, Annexe annexeALier);

    void supprimerUneAnnexe(Login login, ReferenceDeDemande referenceDeDemande, AnnexeFK annexeFK);

}
