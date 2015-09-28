package ch.vd.demaut.services.demandes.autorisation;

import ch.vd.demaut.domain.demandes.ReferenceDeDemande;
import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisation;
import ch.vd.demaut.domain.demandes.autorisation.Profession;
import ch.vd.demaut.domain.utilisateurs.Login;

public interface DemandeAutorisationService {

    DemandeAutorisation initialiserDemandeAutorisation(Login login, Profession profession);

    DemandeAutorisation recupererDemandeParReference(ReferenceDeDemande referenceDeDemande);

}

