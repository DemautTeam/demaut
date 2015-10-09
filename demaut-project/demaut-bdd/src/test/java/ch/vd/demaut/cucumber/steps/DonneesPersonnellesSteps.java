package ch.vd.demaut.cucumber.steps;

import ch.vd.demaut.commons.bdd.AccepteOuRefuse;
import ch.vd.demaut.commons.exceptions.DomainException;
import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisation;
import ch.vd.demaut.domain.demandeur.Pays;
import ch.vd.demaut.domain.demandeur.donneesPerso.*;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;
import static ch.vd.demaut.commons.bdd.AccepteOuRefuse.*;


public class DonneesPersonnellesSteps {
    // ********************************************************* Static fields
    private final Logger logger = LoggerFactory.getLogger(getClass());

    // ********************************************************* Fields

    private DonneesPersonnelles currentDonnees;

    private AccepteOuRefuse actionActuelle;
    private DemandeAutorisationSteps demandeAutorisationSteps;

    // *********************************************** Technical Methods

    public void setDemandeAutorisationSteps(DemandeAutorisationSteps demandeAutorisationSteps) {
        this.demandeAutorisationSteps = demandeAutorisationSteps;
    }


    //************************************************************

    public void initDonneePersonnelles(Nom nom, Prenom prenom){
        Localite localite = new Localite("Lausanne");
        NPA npa = new NPA("1000");
        Pays pays = Pays.Suisse;
        Adresse adresse = new Adresse("", "", localite, npa, pays);
        Email email = new Email("john.doe@nobody.com");
        Genre genre = Genre.Feminin;
        DateDeNaissance dateDeNaissance = new DateDeNaissance(LocalDate.now());
        NumeroTelephone numeroTelephone = new NumeroTelephone("022222222");
        NumeroTelephone numeroMobile = new NumeroTelephone("07625225123");
        NumeroTelephone numeroFax = new NumeroTelephone("023456789");

        currentDonnees = new DonneesPersonnelles(nom, prenom, null, genre, dateDeNaissance, adresse, email, numeroTelephone, numeroMobile, numeroFax);
    }

    public AccepteOuRefuse getActionActuelle(){
        return actionActuelle;
    }

    public void attacheDonneesPersonellesALaDemandeDAutorisation(){
        assertThat(currentDonnees).isNotNull();
        try {
            demandeAutorisationSteps.getDemandeEnCours().validerEtAttacherLesDonneesPersonnelles(currentDonnees);
            actionActuelle = accepte;
        }
        catch (DomainException e){
            actionActuelle = refuse;
        }
    }


}
