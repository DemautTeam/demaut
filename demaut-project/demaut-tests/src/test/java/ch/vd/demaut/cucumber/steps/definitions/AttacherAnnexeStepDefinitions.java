package ch.vd.demaut.cucumber.steps.definitions;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.vd.demaut.commons.exceptions.TechnicalException;
import ch.vd.demaut.cucumber.converters.demandeurs.NomEtPrenomDemandeurConverter;
import ch.vd.demaut.domain.annexes.Annexe;
import ch.vd.demaut.domain.annexes.ContenuAnnexeNonValideException;
import ch.vd.demaut.domain.annexes.TypeAnnexe;
import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisation;
import ch.vd.demaut.domain.demandes.autorisation.ProfessionDeLaSante;
import ch.vd.demaut.domain.demandes.autorisation.repo.DemandeAutorisationRepository;
import ch.vd.demaut.domain.demandeurs.Demandeur;
import ch.vd.demaut.domain.demandeurs.DemandeurRepository;
import ch.vd.demaut.domain.demandeurs.NomEtPrenomDemandeur;
import cucumber.api.Transform;
import cucumber.api.java.fr.Alors;
import cucumber.api.java.fr.Etantdonné;
import cucumber.api.java.fr.Lorsque;

/**
 *  Step definitions pour la fonctionnalité "Attacher des annexes"
 *
 */
public class AttacherAnnexeStepDefinitions extends StepDefinitions {

	// ********************************************************* Fields
	
    private static final Logger LOGGER = LoggerFactory.getLogger(AttacherAnnexeStepDefinitions.class);

    private DemandeurRepository demandeurRepository;
    private DemandeAutorisationRepository demandeAutorisationRepository;

    private Demandeur demandeur;
    private DemandeAutorisation demande;
    private boolean contenuAnnexeNonValideException = false;
    
    private List<String> nomsDeFichierNonVides = new ArrayList<String>();
    private List<String> nomsDeFichierVides = new ArrayList<String>();

	// ********************************************************* Technical methods

    public void setDemandeurRepository(DemandeurRepository demandeurRepository) {
        this.demandeurRepository = demandeurRepository;
    }

    public void setDemandeAutorisationRepository(DemandeAutorisationRepository demandeAutorisationRepository) {
    	this.demandeAutorisationRepository = demandeAutorisationRepository;
    }
    
    
    // ********************************************************* Given
    
    @Etantdonné("^le demandeur professionnel \"([^\"]*)\"$")
    public void le_demandeur_professionnel(@Transform(NomEtPrenomDemandeurConverter.class) NomEtPrenomDemandeur nomEtPrenom) throws Throwable {
       
    	demandeur = new Demandeur(nomEtPrenom);
        demandeurRepository.store(demandeur);
        
        LOGGER.debug("Le demandeur " + nomEtPrenom + " a été ajouté au repository avec l'id technique:" + demandeur.getId());
    }

    @Etantdonné("^qu'il a une demande d'autorisation de type \"([^\"]*)\" en cours de saisie$")
    public void qu_il_a_une_demande_d_autorisation_de_type_en_cours_de_saisie(ProfessionDeLaSante typeAutorisation) throws Throwable {
    	
    	demande = new DemandeAutorisation();
    	demandeAutorisationRepository.store(demande);
        
    	LOGGER.debug("La demande " + demande + " a été ajoutée au repository avec l'id technique:" + demande.getId());
    }

    @Etantdonné("^le fichier \"([^\"]*)\" est un PDF non vide$")
    public void etant_donne_un_fichier_valide(String nomFichier) {
    	
    	nomsDeFichierNonVides.add(nomFichier);
    	
    	LOGGER.debug("Le fichier " + nomFichier + " a été ajouté à la liste des fichiers non vides");
    }

    @Etantdonné("^le fichier \"([^\"]*)\" est un PDF vide$")
    public void etant_donne_un_fichier_invalide(String nomFichier) {
    	
    	nomsDeFichierVides.add(nomFichier);

    	LOGGER.debug("Le fichier " + nomFichier + " a été ajouté à la liste des fichiers vides");
    }
    
    // ********************************************************* When
    
    @Lorsque("^qu'il attache le fichier \"([^\"]*)\" de type \"([^\"]*)\" à cette demande$")
    public void lorsque_il_attache_le_fichier_à_cette_demande(String nomFichier, TypeAnnexe typeAnnexe) throws Throwable {
    	
    	byte[] contenuFichier = extractContenuFichier(nomFichier);
    	
    	Annexe annexe = new Annexe(typeAnnexe, nomFichier, contenuFichier);
    	
    	try {
    		demande.attacherUneAnnexe(annexe);
    	} catch(ContenuAnnexeNonValideException e) {
    		contenuAnnexeNonValideException = true;
    	}
    }

	private byte[] extractContenuFichier(String nomFichier) {
		byte[] contenuFichier = null;
    	if (nomsDeFichierNonVides.contains(nomFichier)) {
    		contenuFichier = nomFichier.getBytes();
    	} else if (nomsDeFichierVides.contains(nomFichier)) {
    		contenuFichier = null;
    	} else {
    		throw new TechnicalException("nom de fichier non reconnu. Scénario à revoir");
    	}
		return contenuFichier;
	}

	// ********************************************************* Then

	@Alors("^le système Demaut annexe le fichier à la demande avec succès$")
    public void le_système_Demaut_annexe_le_fichier_à_la_demande() throws Throwable {
    	Collection<Annexe> annexes = demande.listerLesAnnexes();
    	assertThat(annexes).hasSize(1);
    }

    @Alors("^le système Demaut n'annexe pas le fichier à la demande car il est vide$")
    public void le_système_Demaut_refuse_le_fichier() throws Throwable {
    	assertThat(contenuAnnexeNonValideException).isTrue();
    }

}
