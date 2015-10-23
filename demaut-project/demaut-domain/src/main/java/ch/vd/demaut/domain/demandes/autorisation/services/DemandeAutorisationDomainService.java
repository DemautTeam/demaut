package ch.vd.demaut.domain.demandes.autorisation.services;

import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisation;
import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisationFactory;
import ch.vd.demaut.domain.demandes.autorisation.Profession;
import ch.vd.demaut.domain.demandes.autorisation.repo.DemandeAutorisationRepository;
import ch.vd.demaut.domain.demandeur.donneesProf.CodeGLN;
import ch.vd.demaut.domain.utilisateurs.Utilisateur;
import ch.vd.demaut.domain.utilisateurs.UtilisateurService;

/**
 * Domain service pour les {@link DemandeAutorisationDomainService}
 * TODO: Utiliser cette classe !
 */
public class DemandeAutorisationDomainService {

    private DemandeAutorisationFactory factory;

    private DemandeAutorisationRepository demandeAutorisationRepository;

    private UtilisateurService utilisateurService;

    public DemandeAutorisationDomainService(DemandeAutorisationFactory factory, DemandeAutorisationRepository repo) {
        this.factory = factory;
        this.demandeAutorisationRepository = repo;
    }

    public DemandeAutorisation initialiserDemandeAutorisation(Profession profession, CodeGLN codeGLN) {
        Utilisateur utilisateurCourant = utilisateurService.recupererUtilisateurCourant();
        DemandeAutorisation nouvelleDemande = factory.initierDemandeAutorisation(utilisateurCourant.getLogin(), profession,codeGLN);
        demandeAutorisationRepository.store(nouvelleDemande);
        return nouvelleDemande;
    }

    public DemandeAutorisationRepository getDemandeAutorisationRepository() {
        return demandeAutorisationRepository;
    }

}
