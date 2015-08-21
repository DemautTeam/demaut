package ch.vd.demaut.data.demandes.autorisation.repo;

import java.util.List;

import ch.vd.demaut.domain.demandes.autorisation.repo.DemandeAutorisationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ch.vd.demaut.data.GenericRepositoryImpl;
import ch.vd.demaut.domain.annexes.Annexe;
import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisation;

@Service
public class DemandeAutorisationRepositoryImpl extends GenericRepositoryImpl<DemandeAutorisation, Long>
		implements DemandeAutorisationRepository {

	public DemandeAutorisationRepositoryImpl() {
		super(DemandeAutorisation.class);
	}

	@Transactional(readOnly = true)
	public Annexe fetchByName(String annexeName) {
		List<?> resultList = this.getEntityManager()
				.createQuery("select o from " + entityClass.getSimpleName() + " as o where o.name = :name ")
				.setParameter("name", annexeName).getResultList();
		return resultList != null && !resultList.isEmpty() ? (Annexe) resultList.get(0) : null;
	}

}
