package ch.vd.demaut.domain.demandes.autorisation;


import ch.vd.demaut.commons.annotations.Factory;
import ch.vd.demaut.domain.demandeur.donneesProf.CodeGLN;
import ch.vd.demaut.domain.utilisateurs.Login;

/**
 * Factory d'une {@link DemandeAutorisation}
 */
@Factory
public class DemandeAutorisationFactory {

    public DemandeAutorisation initierDemandeAutorisation(Login login, Profession profession, CodeGLN codeGLN) {
        DemandeAutorisation demande = new DemandeAutorisation(login, profession);
        demande.getDonneesProfessionnelles().validerEtRenseignerCodeGLN(codeGLN, profession);
        demande.generateReference();
        return demande;
    }
}
