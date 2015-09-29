package ch.vd.demaut.domain.demandes.autorisation;


import ch.vd.demaut.commons.annotations.Factory;
import ch.vd.demaut.domain.utilisateurs.Login;

/**
 * Factory d'une {@link DemandeAutorisation}
 */
@Factory
public class DemandeAutorisationFactory {

    public DemandeAutorisation initierDemandeAutorisation(Login login, Profession profession) {
        DemandeAutorisation demande = new DemandeAutorisation(login, profession);
        demande.generateReference();
        return demande;
    }
}
