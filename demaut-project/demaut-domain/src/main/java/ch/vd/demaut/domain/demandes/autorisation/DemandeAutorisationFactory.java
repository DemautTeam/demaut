package ch.vd.demaut.domain.demandes.autorisation;


import ch.vd.demaut.commons.annotations.Factory;
import ch.vd.demaut.domain.config.ConfigDemaut;
import ch.vd.demaut.domain.utilisateurs.Login;

@Factory
public class DemandeAutorisationFactory {

    private static DemandeAutorisationFactory INSTANCE = null;

    public synchronized static DemandeAutorisationFactory getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DemandeAutorisationFactory();
        }
        return INSTANCE;
    }

    public DemandeAutorisation initierDemandeAutorisation(Login login, ProfessionDeLaSante profession, ConfigDemaut config) {
        DemandeAutorisation demande = new DemandeAutorisation(login, profession, config);
        demande.generateReference();
        return demande;
    }
}
