package ch.vd.demaut.data.demandes.autorisation.repo;

import ch.vd.demaut.data.GenericRepositoryImpl;
import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisation;
import ch.vd.demaut.domain.demandes.autorisation.repo.DemandeAutorisationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Service
public class DemandeAutorisationRepositoryImpl extends GenericRepositoryImpl<DemandeAutorisation, Long>
        implements DemandeAutorisationRepository {

    public DemandeAutorisationRepositoryImpl() {
        super(DemandeAutorisation.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    public DemandeAutorisation sauverLaDemandeAutorisation(DemandeAutorisation demandeAutorisation) {
        return demandeAutorisation != null ? this.store(demandeAutorisation) : null;
    }

    @Override
    @SuppressWarnings("all")
    @Transactional(readOnly = true)
    public DemandeAutorisation afficherUneDemandeAutorisation(String demandeReference) {
        final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
        final CriteriaQuery<DemandeAutorisation> criteriaQuery = builder.createQuery(DemandeAutorisation.class);

        Root<DemandeAutorisation> autorisationRoot = criteriaQuery.from(DemandeAutorisation.class);
        criteriaQuery.where(builder.equal(autorisationRoot.get("referenceDeDemande").get("reference"), demandeReference));
        criteriaQuery.orderBy(builder.desc(autorisationRoot.get("id")));
        TypedQuery<DemandeAutorisation> typedQuery = this.getEntityManager().createQuery(criteriaQuery);
        List<DemandeAutorisation> resultList = typedQuery.getResultList();
        return resultList != null && !resultList.isEmpty() ? resultList.get(0) : null;
    }
}
