package ch.vd.demaut.domain.demandes.autorisation;


import ch.vd.demaut.commons.annotations.Factory;
import ch.vd.demaut.domain.demandes.autorisation.repo.DemandeAutorisationRepository;
import ch.vd.demaut.domain.demandeur.donneesProf.CodeGLN;
import ch.vd.demaut.domain.exception.DemandeBrouillonExisteDejaException;
import ch.vd.demaut.domain.utilisateurs.Login;

/**
 * Factory d'une {@link DemandeAutorisation}. A utiliser pour créer une demande.
 */
@Factory
public class DemandeAutorisationFactory {

    // ********************************************************* Fields
    private DemandeAutorisationRepository demandeAutorisationRepository;

    // ********************************************************* Public methods 
    public DemandeAutorisation initierDemandeAutorisation(Login login, Profession profession, CodeGLN codeGLN) {
        DemandeAutorisation demandeAutorisation = new DemandeAutorisation(login, profession);

        throwExceptionSiDemandeBrouillonExisteDeja(login);

        demandeAutorisation.getDonneesProfessionnelles().validerEtRenseignerCodeGLN(codeGLN, profession);

        demandeAutorisation.generateReference();
        demandeAutorisation.generateDateDeCreation();

        return demandeAutorisation;
    }

    // ********************************************************* Private methods 
    private void throwExceptionSiDemandeBrouillonExisteDeja(Login login) {
        boolean brouillonExiste = demandeAutorisationRepository.brouillonExiste(login);
        if (brouillonExiste) {
            throw new DemandeBrouillonExisteDejaException();
        }
    }

    // ********************************************************* Technical methods 
    public void setDemandeAutorisationRepository(DemandeAutorisationRepository demandeAutorisationRepository) {
        this.demandeAutorisationRepository = demandeAutorisationRepository;
    }
}
