package ch.vd.demaut.domain.demandes.autorisation.factory;


import ch.vd.demaut.commons.annotations.Factory;
import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisation;

@Factory
public class DemandeAutorisationFactory {

    private static DemandeAutorisationFactory INSTANCE = null;

    public synchronized static DemandeAutorisationFactory getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DemandeAutorisationFactory();
        }
        return INSTANCE;
    }

    public DemandeAutorisation inititierDemandeAutorisation() {
        DemandeAutorisation demande = new DemandeAutorisation();
        demande.generateReference();
		return demande;
    }
}
