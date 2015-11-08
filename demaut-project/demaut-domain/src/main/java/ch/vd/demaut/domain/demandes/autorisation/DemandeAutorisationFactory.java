package ch.vd.demaut.domain.demandes.autorisation;

import ch.vd.demaut.commons.annotations.Factory;
import ch.vd.demaut.domain.demandes.DateDeCreation;
import ch.vd.demaut.domain.demandes.Sequenceur;
import ch.vd.demaut.domain.demandes.SequenceurImplStatic;
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
    
    //TODO: Injecter le sequenceur DB ou Static en fonction du contexte
    private Sequenceur sequenceur = new SequenceurImplStatic();

    // ********************************************************* Public methods
    
    public DemandeAutorisation initierDemandeAutorisation(Login login, Profession profession, CodeGLN codeGLN) {
        //Genère une date de creation à l'instant présent
        DateDeCreation dateDeCreation = new DateDeCreation();
        
        return initierDemandeAutorisation(login, profession, codeGLN, dateDeCreation);
    }
    
    /**
     * Crée une demande valide avec au minima un {@link Login}, une {@link Profession}, une {@link DateDeCreation} <br>
     * 
     * Génère une référence unique de demande.
     * 
     * Vérifie s'il existe déjà un brouillon pour ce même utilisateur et si oui, renvoie l'exception
     * DemandeBrouillonExisteDejaException
     * 
     * @param login
     * @param profession
     * @param codeGLN
     * @param dateDeCreation
     * @return
     */
    public DemandeAutorisation initierDemandeAutorisation(Login login, Profession profession, CodeGLN codeGLN, DateDeCreation dateDeCreation) {
        
        DemandeAutorisation demandeAutorisation = new DemandeAutorisation(login, profession, dateDeCreation);

        throwExceptionSiDemandeBrouillonExisteDeja(login);

        demandeAutorisation.getDonneesProfessionnelles().validerEtRenseignerCodeGLN(codeGLN, profession);

        demandeAutorisation.generateReference(sequenceur.nextSequence());

        return demandeAutorisation;
    }

    // ********************************************************* Private methods
    private void throwExceptionSiDemandeBrouillonExisteDeja(Login login) {
        boolean brouillonExiste = demandeAutorisationRepository.brouillonExiste(login);
        if (brouillonExiste) {
            throw new DemandeBrouillonExisteDejaException("Il existe déjà un brouillon pour le login " + login);
        }
    }

    // ********************************************************* Technical methods
    public void setDemandeAutorisationRepository(DemandeAutorisationRepository demandeAutorisationRepository) {
        this.demandeAutorisationRepository = demandeAutorisationRepository;
    }
}
