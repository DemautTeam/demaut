package ch.vd.demaut.data.demandeurs;

import org.springframework.stereotype.Repository;

import ch.vd.demaut.commons.exceptions.NotYetImplementedException;
import ch.vd.demaut.data.GenericRepositoryImpl;
import ch.vd.demaut.domain.demandeurs.Demandeur;
import ch.vd.demaut.domain.demandeurs.DemandeurRepository;

@Repository
public class DemandeurRepositoryImpl extends GenericRepositoryImpl<Demandeur, Long>
        implements DemandeurRepository {

    public DemandeurRepositoryImpl() {
        super(Demandeur.class);
    }

	@Override
	public Demandeur chercherUnDemandeur(String nom, String prenom) {
		throw new NotYetImplementedException();
	}
    
}
