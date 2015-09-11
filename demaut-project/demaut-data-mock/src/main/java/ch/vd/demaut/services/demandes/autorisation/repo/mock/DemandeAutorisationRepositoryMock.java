package ch.vd.demaut.services.demandes.autorisation.repo.mock;

import ch.vd.demaut.commons.repo.GenericRepository;
import ch.vd.demaut.commons.repo.mock.GenericFKARepositoryMock;
import ch.vd.demaut.domain.annexes.Annexe;
import ch.vd.demaut.domain.annexes.ListeDesAnnexes;
import ch.vd.demaut.domain.annexes.TypeAnnexe;
import ch.vd.demaut.domain.demandes.ReferenceDeDemande;
import ch.vd.demaut.domain.demandes.autorisation.DateSoumissionDemande;
import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisation;
import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisationFK;
import ch.vd.demaut.domain.demandes.autorisation.ProfessionDeLaSante;
import ch.vd.demaut.domain.demandes.autorisation.repo.DemandeAutorisationRepository;
import ch.vd.demaut.domain.demandeurs.Demandeur;
import ch.vd.demaut.domain.demandeurs.NomEtPrenomDemandeur;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Mock du {@link DemandeAutorisationRepository}
 */
public class DemandeAutorisationRepositoryMock extends GenericFKARepositoryMock<DemandeAutorisation, DemandeAutorisationFK>
        implements DemandeAutorisationRepository, GenericRepository<DemandeAutorisation, Long> {

    private static Long idSequence = 0L;

    private static DemandeAutorisationRepositoryMock INSTANCE = null;

    public synchronized static DemandeAutorisationRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = mock(DemandeAutorisationRepositoryMock.class);
        }
        return INSTANCE;
    }

    @Override
    protected Long getNextID() {
        return ++idSequence;
    }

    private DemandeAutorisation demandeAutorisation = mockDemandeAutorisation();

    @Override
    public DemandeAutorisation afficherUneDemandeAutorisation(String demandeReference) {
        DemandeAutorisation demandeAutorisation = mockDemandeAutorisation();
        when(demandeAutorisation.getId()).thenReturn(100L);
        when(demandeAutorisation.getVersion()).thenReturn(100);
        return demandeAutorisation;
    }

    @Override
    public DemandeAutorisation sauverLaDemandeAutorisation(DemandeAutorisation demandeAutorisation) {
        when(demandeAutorisation.getVersion()).thenReturn(1);
        when(demandeAutorisation.getId()).thenReturn(1L);
        return demandeAutorisation;
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
