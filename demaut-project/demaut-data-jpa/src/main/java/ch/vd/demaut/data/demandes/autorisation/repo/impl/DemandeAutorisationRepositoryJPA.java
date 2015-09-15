package ch.vd.demaut.data.demandes.autorisation.repo.impl;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ch.vd.demaut.commons.exceptions.EntityNotFoundException;
import ch.vd.demaut.commons.exceptions.EntityNotUniqueException;
import ch.vd.demaut.data.GenericRepositoryImpl;
import ch.vd.demaut.domain.demandes.ReferenceDeDemande;
import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisation;
import ch.vd.demaut.domain.demandes.autorisation.repo.DemandeAutorisationRepository;

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
        
        List<DemandeAutorisation> resultList = typedQuery.getResultList();
        
        validateResultList(resultList);
        
        return resultList.get(0);
    }

    
	private void validateResultList(List<DemandeAutorisation> resultList) {
		if (resultList.size() > 1) {
        	throw new EntityNotUniqueException();
        }
        if (resultList.size() == 1) {
        	throw new EntityNotFoundException();
        }
	}

	private TypedQuery<DemandeAutorisation> createQuery(ReferenceDeDemande ref) {
		final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
        final CriteriaQuery<DemandeAutorisation> criteriaQuery = builder.createQuery(DemandeAutorisation.class);
        Root<DemandeAutorisation> autorisationRoot = criteriaQuery.from(DemandeAutorisation.class);
        criteriaQuery.where(builder.equal(autorisationRoot.get("referenceDeDemande"), ref));
        TypedQuery<DemandeAutorisation> typedQuery = this.getEntityManager().createQuery(criteriaQuery);
		return typedQuery;
	}
}
