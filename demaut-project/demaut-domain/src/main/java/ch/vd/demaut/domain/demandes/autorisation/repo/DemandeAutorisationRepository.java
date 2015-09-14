package ch.vd.demaut.domain.demandes.autorisation.repo;

import ch.vd.demaut.commons.annotations.Repository;
import ch.vd.demaut.commons.repo.GenericReadRepository;
import ch.vd.demaut.commons.repo.GenericRepository;
import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisation;

@Repository
public interface DemandeAutorisationRepository extends GenericRepository<DemandeAutorisation, Long>, GenericReadRepository<DemandeAutorisation, Long> {

    DemandeAutorisation afficherUneDemandeAutorisation(String demandeReference);

}

