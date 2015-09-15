package ch.vd.demaut.domain.utilisateurs;

import ch.vd.demaut.commons.annotations.Repository;
import ch.vd.demaut.commons.repo.GenericRepository;

@Repository
public interface UtilisateurRepository extends GenericRepository<Utilisateur, Long> {

}

