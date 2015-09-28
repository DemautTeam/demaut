package ch.vd.demaut.data.demandes.autorisation.repo.impl;

import ch.vd.demaut.data.GenericRepositoryImpl;
import ch.vd.demaut.domain.demandes.ReferenceDeDemande;
import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisation;
import ch.vd.demaut.domain.demandes.autorisation.ProfessionDeLaSante;
import ch.vd.demaut.domain.demandes.autorisation.StatutDemandeAutorisation;
import ch.vd.demaut.domain.demandes.autorisation.repo.DemandeAutorisationRepository;
import ch.vd.demaut.domain.utilisateurs.Login;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Repository
public class DemandeAutorisationRepositoryJPA extends GenericRepositoryImpl<DemandeAutorisation, Long>
        implements DemandeAutorisationRepository {

    public DemandeAutorisationRepositoryJPA() {
        super(DemandeAutorisation.class);
    }

    @Override
    @Transactional(readOnly = true)
    public DemandeAutorisation recupererDemandeParReference(ReferenceDeDemande ref) {
        TypedQuery<DemandeAutorisation> typedQuery = createQueryParReference(ref);
        return typedQuery.getSingleResult();
    }

    @Override
    @Transactional(readOnly = true)
    public DemandeAutorisation recupererDemandeParProfessionStatut(Login login, ProfessionDeLaSante profession, StatutDemandeAutorisation statut) {
        return null;
    }

    private TypedQuery<DemandeAutorisation> createQueryParReference(ReferenceDeDemande ref) {
        final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
        final CriteriaQuery<DemandeAutorisation> criteriaQuery = builder.createQuery(DemandeAutorisation.class);
        Root<DemandeAutorisation> autorisationRoot = criteriaQuery.from(DemandeAutorisation.class);
        criteriaQuery.where(builder.equal(autorisationRoot.get("referenceDeDemande").get("value"), ref.getValue()));
        return this.getEntityManager().createQuery(criteriaQuery);
    }
}
