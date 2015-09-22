package ch.vd.demaut.data.demandes.autorisation.repo.impl;

import ch.vd.demaut.commons.exceptions.EntityNotFoundException;
import ch.vd.demaut.commons.exceptions.EntityNotUniqueException;
import ch.vd.demaut.data.GenericRepositoryImpl;
import ch.vd.demaut.domain.demandes.ReferenceDeDemande;
import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisation;
import ch.vd.demaut.domain.demandes.autorisation.repo.DemandeAutorisationRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class DemandeAutorisationRepositoryJPA extends GenericRepositoryImpl<DemandeAutorisation, Long>
        implements DemandeAutorisationRepository {

    public DemandeAutorisationRepositoryJPA() {
        super(DemandeAutorisation.class);
    }

    @SuppressWarnings("all")
    @Transactional(readOnly = true)
    public DemandeAutorisation recupererDemandeParReference(ReferenceDeDemande ref) {

        TypedQuery<DemandeAutorisation> typedQuery = createQuery(ref);

        return typedQuery.getSingleResult();
    }


    private void validateUniqueResult(List<DemandeAutorisation> resultList) {
        if (resultList.size() > 1) {
            throw new EntityNotUniqueException();
        }
        if (resultList.size() == 0) {
            throw new EntityNotFoundException();
        }
    }

    private TypedQuery<DemandeAutorisation> createQuery(ReferenceDeDemande ref) {
        final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
        final CriteriaQuery<DemandeAutorisation> criteriaQuery = builder.createQuery(DemandeAutorisation.class);
        Root<DemandeAutorisation> autorisationRoot = criteriaQuery.from(DemandeAutorisation.class);
        criteriaQuery.where(builder.equal(autorisationRoot.get("referenceDeDemande"), ref.getValue()));
        TypedQuery<DemandeAutorisation> typedQuery = this.getEntityManager().createQuery(criteriaQuery);
        return typedQuery;
    }
}
