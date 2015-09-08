package ch.vd.demaut.services.demandes.autorisation;

import ch.vd.demaut.domain.annexes.Annexe;
import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisation;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Collection;

@Service
public interface DemandeAutorisationService {

    DemandeAutorisation initialiserDemandeAutorisation();

    DemandeAutorisation afficherUneDemandeAutorisation(String demandeReference);

    DemandeAutorisation sauverLaDemandeAutorisation(DemandeAutorisation demandeAutorisation);

    Collection<Annexe> listerLesAnnexes(String demandeReference);

    Annexe afficherUneAnnexe(String demandeReference, String annexeFileName);

    boolean attacherUneAnnexe(String demandeReference, File file, String annexeFileName, String annexeFileSize, String annexeFileType, String annexeType);

    boolean supprimerUneAnnexe(String demandeReference, String annexeFileName, String annexeType);
}

