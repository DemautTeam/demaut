package ch.vd.demaut.domain.demandeurs;

import ch.vd.demaut.commons.annotations.Repository;
import ch.vd.demaut.commons.repo.GenericRepository;

@Repository
public interface DemandeurRepository extends GenericRepository<Demandeur, Long> {

    Demandeur chercherUnDemandeur(String nom, String prenom);

}

