package ch.vd.demaut.services.demandes.autorisation.service.mock;

import ch.vd.demaut.domain.annexes.Annexe;
import ch.vd.demaut.domain.annexes.AnnexeMetadata;
import ch.vd.demaut.domain.annexes.ListeDesAnnexes;
import ch.vd.demaut.domain.annexes.TypeAnnexe;
import ch.vd.demaut.domain.demandes.ReferenceDeDemande;
import ch.vd.demaut.domain.demandes.autorisation.DateSoumissionDemande;
import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisation;
import ch.vd.demaut.domain.demandes.autorisation.ProfessionDeLaSante;
import ch.vd.demaut.domain.demandes.autorisation.repo.DemandeAutorisationRepository;
import ch.vd.demaut.domain.demandeurs.Demandeur;
import ch.vd.demaut.domain.demandeurs.NomEtPrenomDemandeur;
import ch.vd.demaut.services.demandes.autorisation.DemandeAutorisationService;
import ch.vd.demaut.services.demandes.autorisation.repo.mock.DemandeAutorisationRepositoryMock;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DemandeAutorisationServiceMock implements DemandeAutorisationService {

    private static DemandeAutorisationServiceMock INSTANCE = null;

    public synchronized static DemandeAutorisationServiceMock getInstance() {
        if (INSTANCE == null) {
            INSTANCE = mock(DemandeAutorisationServiceMock.class);
        }
        return INSTANCE;
    }

    @Override
    public DemandeAutorisation initialiserDemandeAutorisation() {
        DemandeAutorisation demandeAutorisation = mockDemandeAutorisation();
        when(demandeAutorisation.getId()).thenReturn(0L);
        when(demandeAutorisation.getVersion()).thenReturn(0);
        return demandeAutorisation;
    }

    @Override
    public DemandeAutorisation afficherUneDemandeAutorisation(String demandeReference) {
        DemandeAutorisation demandeAutorisation = mockDemandeAutorisation();
        when(demandeAutorisation.getId()).thenReturn(100L);
        when(demandeAutorisation.getVersion()).thenReturn(100);
        return demandeAutorisation;
    }

    @Override
<<<<<<< HEAD
    public void sauverLaDemandeAutorisation(DemandeAutorisation demandeAutorisation) {
=======
    public DemandeAutorisation sauverLaDemandeAutorisation(DemandeAutorisation demandeAutorisation) {
        demandeAutorisation = mockDemandeAutorisation();
        when(demandeAutorisation.getVersion()).thenReturn(1);
        when(demandeAutorisation.getId()).thenReturn(1L);
        return demandeAutorisation;
>>>>>>> branch 'master' of git@git.etat-de-vaud.ch:/ses/demaut.git
    }

    @Override
    public Collection<AnnexeMetadata> listerLesAnnexeMetadatas(String demandeReference) {
        when(INSTANCE.listerLesAnnexeMetadatas(demandeReference)).thenReturn(mockDemandeAutorisation().listerLesAnnexeMetadatas());
        return new ArrayList<>();
    }

    @Override
    public Annexe afficherUneAnnexe(String demandeReference, String annexeFileName) {
        when(INSTANCE.afficherUneAnnexe(demandeReference, annexeFileName)).thenReturn(mockDemandeAutorisation().listerLesAnnexes().iterator().next());
        return new Annexe();
    }

    @Override
    public boolean attacherUneAnnexe(String demandeReference, File file, String annexeFileName, String annexeFileSize, String annexeFileType, String annexeType) {
        when(INSTANCE.attacherUneAnnexe(demandeReference, file, annexeFileName, annexeFileSize, annexeFileType, annexeType)).thenReturn(true);
        return true;
    }

    @Override
    public boolean supprimerUneAnnexe(String demandeReference, String annexeFileName, String annexeType) {
        when(INSTANCE.supprimerUneAnnexe(demandeReference, annexeFileName, annexeType)).thenReturn(true);
        return true;
    }

    private DemandeAutorisation mockDemandeAutorisation() {
        DemandeAutorisation demandeAutorisation = mock(DemandeAutorisation.class);
        when(demandeAutorisation.getProfessionDeLaSante()).thenReturn(ProfessionDeLaSante.Medecin);
        when(demandeAutorisation.getReferenceDeDemande()).thenReturn(new ReferenceDeDemande());
        when(demandeAutorisation.getDateSoumissionDemande()).thenReturn(new DateSoumissionDemande(2015, 11, 11, 12, 15, 25));
        when(demandeAutorisation.getListeDesAnnexes()).thenReturn(listeDesAnnexes());
        when(demandeAutorisation.getDemandeur()).thenReturn(new Demandeur(new NomEtPrenomDemandeur("Hello", "World")));
        return demandeAutorisation;
    }

    private ListeDesAnnexes listeDesAnnexes() {
        ListeDesAnnexes listeDesAnnexes = new ListeDesAnnexes();
        listeDesAnnexes.ajouterAnnexe(new Annexe(TypeAnnexe.Certificat, "certificat1.pdf", null));
        listeDesAnnexes.ajouterAnnexe(new Annexe(TypeAnnexe.Certificat, "certificat2.pdf", null));
        listeDesAnnexes.ajouterAnnexe(new Annexe(TypeAnnexe.Certificat, "certificat3.pdf", null));
        listeDesAnnexes.ajouterAnnexe(new Annexe(TypeAnnexe.CV, "cv.pdf", null));
        return listeDesAnnexes;
    }
}
