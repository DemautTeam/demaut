package ch.vd.demaut.data.demandes.autorisation.repo.impl;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Fetch;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.vd.demaut.data.GenericRepositoryImpl;
import ch.vd.demaut.domain.demandes.ReferenceDeDemande;
import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisation;
import ch.vd.demaut.domain.demandes.autorisation.StatutDemandeAutorisation;
import ch.vd.demaut.domain.demandes.autorisation.repo.DemandeAutorisationRepository;
import ch.vd.demaut.domain.demandeur.donneesPerso.DonneesPersonnelles;
import ch.vd.demaut.domain.demandeur.donneesProf.DonneesProfessionnelles;
import ch.vd.demaut.domain.exception.DemandeBrouillonExisteDejaException;
import ch.vd.demaut.domain.exception.DemandeNotFoundException;
import ch.vd.demaut.domain.utilisateurs.Login;

public class DemandeAutorisationRepositoryJPA extends GenericRepositoryImpl<DemandeAutorisation, Long>
        implements DemandeAutorisationRepository {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    // ********************************************************* Constructor
    public DemandeAutorisationRepositoryJPA() {
        super(DemandeAutorisation.class);
    }

    // ********************************************************* Implementation des interfaces
    @Override
    public DemandeAutorisation recupererDemandeParReference(ReferenceDeDemande referenceDeDemande) {
        logger.debug("Chargement de la demande  {}", referenceDeDemande.getValue());
        TypedQuery<DemandeAutorisation> typedQuery = createQueryParReference(referenceDeDemande);
        return typedQuery.getSingleResult();
    }

    @Override
    public DemandeAutorisation recupererBrouillon(Login login) {
        List<DemandeAutorisation> brouillons = trouverBrouillonsParUtilisateur(login);
        if (brouillons.size() > 1) {
            throw new DemandeBrouillonExisteDejaException();
        }
        if (brouillons.isEmpty()) {
            throw new DemandeNotFoundException();
        }
        return brouillons.get(0);
    }

    @Override
    public List<DemandeAutorisation> recupererListeBrouillons(Login login) {
        return trouverBrouillonsParUtilisateur(login);
    }

    //TODO: Utiliser une query avec Count
    @Override
    public boolean brouillonExiste(Login login) {
        List<DemandeAutorisation> brouillons = trouverBrouillonsParUtilisateur(login);
        return !brouillons.isEmpty();
    }

    // ********************************************************* Methodes privées
    private List<DemandeAutorisation> trouverBrouillonsParUtilisateur(Login login) {
        TypedQuery<DemandeAutorisation> typedQuery = createQueryBrouillonParUtilisateur(login);
        return typedQuery.getResultList();
    }
    
    private TypedQuery<DemandeAutorisation> createQueryParReference(ReferenceDeDemande referenceDeDemande) {
        final CriteriaBuilder criteriaBuilder = this.getEntityManager().getCriteriaBuilder();
        final CriteriaQuery<DemandeAutorisation> criteriaQuery = criteriaBuilder.createQuery(DemandeAutorisation.class);
        final Root<DemandeAutorisation> autorisationRoot = criteriaQuery.from(DemandeAutorisation.class);
        //A completer le fetch si besoin de nouveaux elements en eager
        Fetch<DemandeAutorisation, DonneesProfessionnelles> fetchDonneesProfessionnelles = autorisationRoot.fetch("donneesProfessionnelles",JoinType.INNER);
        fetchDonneesProfessionnelles.fetch("diplomes", JoinType.LEFT);
        Fetch<DemandeAutorisation, DonneesPersonnelles> fetchDonneesPersonnelles = autorisationRoot.fetch("donneesPersonnelles",JoinType.INNER);
        fetchDonneesPersonnelles.fetch("adresse", JoinType.LEFT);

        criteriaQuery.where(criteriaBuilder.equal(autorisationRoot.get("referenceDeDemande"), referenceDeDemande));
        return this.getEntityManager().createQuery(criteriaQuery);
    }

    private TypedQuery<DemandeAutorisation> createQueryBrouillonParUtilisateur(Login login) {
        final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
        final CriteriaQuery<DemandeAutorisation> cq = builder.createQuery(DemandeAutorisation.class);
        Root<DemandeAutorisation> autorisationRoot = cq.from(DemandeAutorisation.class);
        cq.where(
                builder.equal(autorisationRoot.get("statutDemandeAutorisation"), StatutDemandeAutorisation.Brouillon),
                builder.equal(autorisationRoot.get("login").get("value"), login.getValue()));
        return this.getEntityManager().createQuery(cq);
    }

}
