package ch.vd.demaut.domain.demandeur.donneesPerso.repo;

import ch.vd.demaut.commons.repo.GenericReadRepository;
import ch.vd.demaut.commons.repo.GenericRepository;
import ch.vd.demaut.domain.demandeur.donneesPerso.DonneesPersonnelles;

/**
 * Repository dédié à la gestion des données personnelles.
 */
public interface DonneesPersonnellesRepository extends GenericRepository<DonneesPersonnelles, Long>, GenericReadRepository<DonneesPersonnelles, Long> {

}
