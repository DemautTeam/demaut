package ch.vd.demaut.services.test;

import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.beans.factory.annotation.Autowired;

import ch.vd.demaut.domain.demandes.ReferenceDeDemande;
import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisation;
import ch.vd.demaut.domain.demandes.autorisation.Profession;
import ch.vd.demaut.domain.demandeur.donneesProf.CodeGLN;
import ch.vd.demaut.domain.utilisateurs.Login;
import ch.vd.demaut.services.demandes.autorisation.DemandeAutorisationService;

/**
 * Classe utilitaire pour les classes de test des services
 *
 */
abstract public class AbstractServiceTest {

    protected static Login login = new Login("login1");
    
    protected DemandeAutorisation demandeEnCours;
    protected ReferenceDeDemande referenceDemandeEnCours;
    protected Profession profession;
    protected CodeGLN glnValide = new CodeGLN("4719512002889");


    @Autowired
    protected DemandeAutorisationService demandeAutorisationService;

    protected void creerDemandeEnCours() {
        demandeEnCours = demandeAutorisationService.initialiserDemandeAutorisation(profession, glnValide, login);
        referenceDemandeEnCours = demandeEnCours.getReferenceDeDemande();
    }
    
    public void setUp() throws Exception  {
        profession = Profession.Medecin;
        login = new Login(login.getValue() + "1");
        
        assertThat(demandeAutorisationService).isNotNull();
    }


}
