package ch.vd.demaut.cucumber.steps;

import ch.vd.demaut.commons.bdd.AccepteOuRefuse;
import ch.vd.demaut.commons.exceptions.DomainException;
import ch.vd.demaut.domain.demandeur.Pays;
import ch.vd.demaut.domain.demandeur.donneesPerso.*;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static ch.vd.demaut.commons.bdd.AccepteOuRefuse.accepte;
import static ch.vd.demaut.commons.bdd.AccepteOuRefuse.refuse;
import static org.assertj.core.api.Assertions.assertThat;


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

    public void initDonneePersonnelles(Nom nom, Prenom prenom) {
        Localite localite = new Localite("Lausanne");
        NPA npa = new NPA("1000");
        Pays pays = Pays.Suisse;
        Adresse adresse = new Adresse("", "", localite, npa, pays);
        Email email = new Email("john.doe@nobody.com");
        Genre genre = Genre.Feminin;
        DateDeNaissance dateDeNaissance = new DateDeNaissance(LocalDate.now());
        TelephonePrive numeroTelephone = new TelephonePrive("022222222");
        TelephoneMobile numeroMobile = new TelephoneMobile("07625225123");
        Pays nationalite = Pays.Andorre;
        Langue langue = Langue.Anglais;
        Permis permis = new Permis(TypePermis.B);


        currentDonnees = new DonneesPersonnelles(nom, prenom, null, adresse, email,
                numeroTelephone, numeroMobile, null, genre,
                dateDeNaissance, nationalite, langue, permis);
    }

    public void initDonneePersonnelles(TelephonePrive telephonePrive, TelephoneMobile telephoneMobile) {
        Nom nom = new Nom("test nom");
        Prenom prenom = new Prenom("Test prenom");
        Localite localite = new Localite("Lausanne");
        NPA npa = new NPA("1000");
        Pays pays = Pays.Suisse;
        Adresse adresse = new Adresse("", "", localite, npa, pays);
        Email email = new Email("john.doe@nobody.com");
        Genre genre = Genre.Feminin;
        DateDeNaissance dateDeNaissance = new DateDeNaissance(LocalDate.now());
        Pays nationalite = Pays.Andorre;
        Langue langue = Langue.Anglais;
        Permis permis = new Permis(TypePermis.B);


        currentDonnees = new DonneesPersonnelles(nom, prenom, null, adresse, email,
                telephonePrive, telephoneMobile, null, genre,
                dateDeNaissance, nationalite, langue, permis);
    }

    public void initDonneePersonnelles(Pays nationalite, Permis permis) {
        Nom nom = new Nom("test nom");
        Prenom prenom = new Prenom("Test prenom");
        Localite localite = new Localite("Lausanne");
        NPA npa = new NPA("1000");
        Pays pays = Pays.Suisse;
        Adresse adresse = new Adresse("", "", localite, npa, pays);
        Email email = new Email("john.doe@nobody.com");
        Genre genre = Genre.Feminin;
        DateDeNaissance dateDeNaissance = new DateDeNaissance(LocalDate.now());
        TelephonePrive numeroTelephone = new TelephonePrive("022222222");
        TelephoneMobile numeroMobile = new TelephoneMobile("07625225123");
        Langue langue = Langue.Anglais;

        currentDonnees = new DonneesPersonnelles(nom, prenom, null, adresse, email, numeroTelephone, numeroMobile, null, genre,
                dateDeNaissance, nationalite, langue, permis);
    }

    public AccepteOuRefuse getActionActuelle() {
        assertThat(actionActuelle).isNotNull();
        return actionActuelle;
    }

    public void attacheDonneesPersonellesALaDemandeDAutorisation() {
        assertThat(currentDonnees).isNotNull();
        try {
            demandeAutorisationSteps.getDemandeEnCours().validerEtAttacherLesDonneesPersonnelles(currentDonnees);
            logger.info("donnees personnelles acceptées");
            actionActuelle = accepte;
        } catch (DomainException e) {
            logger.info("donnees personnelles refusées", e);
            actionActuelle = refuse;
        }
    }

}
