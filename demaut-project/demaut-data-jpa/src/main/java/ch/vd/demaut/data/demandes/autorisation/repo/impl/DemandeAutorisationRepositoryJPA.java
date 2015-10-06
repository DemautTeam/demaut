package ch.vd.demaut.data.demandes.autorisation.repo.impl;

import ch.vd.demaut.data.GenericRepositoryImpl;
import ch.vd.demaut.domain.demandes.ReferenceDeDemande;
import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisation;
import ch.vd.demaut.domain.demandes.autorisation.StatutDemandeAutorisation;
import ch.vd.demaut.domain.demandes.autorisation.repo.DemandeAutorisationRepository;
import ch.vd.demaut.domain.utilisateurs.Login;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Repository
public class DemandeAutorisationRepositoryJPA extends GenericRepositoryImpl<DemandeAutorisation, Long>
        implements DemandeAutorisationRepository {

    // ********************************************************* Constructor
    public DemandeAutorisationRepositoryJPA() {
        super(DemandeAutorisation.class);
    }

    // ********************************************************* Implementation des interfaces
    @Override
    public DemandeAutorisation recupererDemandeParReference(ReferenceDeDemande referenceDeDemande) {
        TypedQuery<DemandeAutorisation> typedQuery = createQueryParReference(referenceDeDemande);
        return typedQuery.getSingleResult();
    }

    @Override
    public DemandeAutorisation trouverDemandeBrouillonParUtilisateur(Login login) {
        StatutDemandeAutorisation statutDemandeAutorisation = StatutDemandeAutorisation.Brouillon;
        TypedQuery<DemandeAutorisation> typedQuery = createQueryBrouillonParUtilisateur(statutDemandeAutorisation, login);
        return typedQuery.getSingleResult();
    }

    // ********************************************************* Methodes privées
    private TypedQuery<DemandeAutorisation> createQueryParReference(ReferenceDeDemande referenceDeDemande) {
        final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
        final CriteriaQuery<DemandeAutorisation> criteriaQuery = builder.createQuery(DemandeAutorisation.class);
        Root<DemandeAutorisation> autorisationRoot = criteriaQuery.from(DemandeAutorisation.class);
        criteriaQuery.where(builder.equal(autorisationRoot.get("referenceDeDemande").get("value"), referenceDeDemande.getValue()));
        return this.getEntityManager().createQuery(criteriaQuery);
    }

    private TypedQuery<DemandeAutorisation> createQueryBrouillonParUtilisateur(StatutDemandeAutorisation statutDemandeAutorisation, Login login) {
        final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
        final CriteriaQuery<DemandeAutorisation> criteriaQuery = builder.createQuery(DemandeAutorisation.class);
        Root<DemandeAutorisation> autorisationRoot = criteriaQuery.from(DemandeAutorisation.class);
        criteriaQuery.where(
                builder.equal(autorisationRoot.get("statutDemandeAutorisation"), statutDemandeAutorisation),
                builder.equal(autorisationRoot.get("login").get("value"), login.getValue()));
        return this.getEntityManager().createQuery(criteriaQuery);
    }
}
