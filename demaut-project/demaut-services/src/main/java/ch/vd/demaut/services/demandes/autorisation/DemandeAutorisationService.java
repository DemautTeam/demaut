package ch.vd.demaut.services.demandes.autorisation;

import ch.vd.demaut.domain.annexes.Annexe;
import ch.vd.demaut.domain.annexes.AnnexeMetadata;
import ch.vd.demaut.domain.config.ConfigDemaut;
import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisation;
import ch.vd.demaut.domain.demandes.autorisation.ProfessionDeLaSante;
import ch.vd.demaut.domain.demandeurs.Demandeur;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

@Service
public interface DemandeAutorisationService {

    DemandeAutorisation initialiserDemandeAutorisation(Demandeur demandeur, ProfessionDeLaSante profession, ConfigDemaut config);

    DemandeAutorisation afficherUneDemandeAutorisation(String demandeReference);

    DemandeAutorisation sauverLaDemandeAutorisation(DemandeAutorisation demandeAutorisation);

    Collection<AnnexeMetadata> listerLesAnnexeMetadatas(String demandeReference);

    Annexe afficherUneAnnexe(String demandeReference, String annexeFileName);

    boolean attacherUneAnnexe(String demandeReference, File file, String annexeFileName, String annexeFileSize, String annexeFileType, String annexeType) throws IOException;

    boolean supprimerUneAnnexe(String demandeReference, String annexeFileName, String annexeType);

    ProfessionDeLaSante afficherDonneesProfession(String demandeReference);

    String renseignerDonneesProfession(String demandeReference, String idProfession, String codeGln);
}

