package ch.vd.demaut.domain.demandes.autorisation.repo;

import ch.vd.demaut.commons.annotations.Repository;
import ch.vd.demaut.commons.repo.GenericReadRepository;
import ch.vd.demaut.commons.repo.GenericRepository;
import ch.vd.demaut.domain.demandes.ReferenceDeDemande;
import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisation;
import ch.vd.demaut.domain.demandes.autorisation.ProfessionDeLaSante;
import ch.vd.demaut.domain.demandes.autorisation.StatutDemandeAutorisation;
import ch.vd.demaut.domain.utilisateurs.Login;

@Repository
public interface DemandeAutorisationRepository extends GenericRepository<DemandeAutorisation, Long>, GenericReadRepository<DemandeAutorisation, Long> {

    DemandeAutorisation recupererDemandeParReference(ReferenceDeDemande referenceDeDemande);

    DemandeAutorisation recupererDemandeParProfessionStatut(Login login, ProfessionDeLaSante profession, StatutDemandeAutorisation statut);
}

