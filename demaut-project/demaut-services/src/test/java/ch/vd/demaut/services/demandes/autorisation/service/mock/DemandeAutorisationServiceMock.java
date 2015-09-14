package ch.vd.demaut.services.demandes.autorisation.service.mock;

import ch.vd.demaut.domain.annexes.Annexe;
import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisation;
import ch.vd.demaut.services.demandes.autorisation.DemandeAutorisationService;

import java.io.File;
import java.util.Collection;

public class DemandeAutorisationServiceMock implements DemandeAutorisationService {

    @Override
    public DemandeAutorisation initialiserDemandeAutorisation() {
        return new DemandeAutorisation();
    }

    @Override
    public DemandeAutorisation afficherUneDemandeAutorisation(String demandeReference) {
        return null; //TODO
    }

    @Override
    public void sauverLaDemandeAutorisation(DemandeAutorisation demandeAutorisation) {
    }

    @Override
    public Collection<Annexe> listerLesAnnexes(String demandeReference) {
        return null; //TODO
    }

    @Override
    public Annexe afficherUneAnnexe(String demandeReference, String annexeFileName) {
        return null; //TODO
    }

    @Override
    public boolean attacherUneAnnexe(String demandeReference, File file, String annexeFileName, String annexeFileSize, String annexeFileType, String annexeType) {
        return false; //TODO
    }

    @Override
    public boolean supprimerUneAnnexe(String demandeReference, String annexeFileName, String annexeType) {
        return false; //TODO
    }
}
