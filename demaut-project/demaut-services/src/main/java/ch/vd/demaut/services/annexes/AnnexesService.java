package ch.vd.demaut.services.annexes;

import ch.vd.demaut.domain.annexes.*;
import ch.vd.demaut.domain.utilisateurs.Login;

import java.io.File;
import java.util.Collection;

public interface AnnexesService {

    Collection<AnnexeMetadata> listerLesAnnexeMetadatas(Login login);

    Collection<TypeAnnexe> listerLesTypeAnnexesObligatoires(Login login);

    ContenuAnnexe recupererContenuAnnexe(Login login, AnnexeFK annexeFK);

    void attacherUneAnnexe(Login login, File file, NomFichier nomFichier, TypeAnnexe type);

    void attacherUneAnnexe(Login login, Annexe annexeALier);

    void supprimerUneAnnexe(Login login, AnnexeFK annexeFK);

}
