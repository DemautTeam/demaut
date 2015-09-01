package ch.vd.demaut.cucumber.steps.definitions;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import ch.vd.demaut.commons.utils.FileMockHelper;
import ch.vd.demaut.cucumber.converters.commons.AccepteOuRefuse;
import ch.vd.demaut.cucumber.converters.demandeurs.NomEtPrenomDemandeurConverter;
import ch.vd.demaut.cucumber.converteurs.annexes.ListeDesAnnexesConverter;
import ch.vd.demaut.cucumber.steps.DemandeAutorisationSteps;
import ch.vd.demaut.domain.annexes.Annexe;
import ch.vd.demaut.domain.annexes.AnnexeNonValideException;
import ch.vd.demaut.domain.annexes.AnnexeValidateur;
import ch.vd.demaut.domain.annexes.FormatFichierAccepte;
import ch.vd.demaut.domain.annexes.TypeAnnexe;
import ch.vd.demaut.domain.demandes.autorisation.ListeDesAnnexes;
import ch.vd.demaut.domain.demandes.autorisation.ProfessionDeLaSante;
import ch.vd.demaut.domain.demandeurs.NomEtPrenomDemandeur;
import cucumber.api.DataTable;
import cucumber.api.Transform;
import cucumber.api.java.fr.Alors;
import cucumber.api.java.fr.Etantdonné;
import cucumber.api.java.fr.Lorsque;

/**
 * Step definitions pour la fonctionnalité "Attacher des annexes"
 *
 */
public class AttacherAnnexeStepDefinitions extends StepDefinitions {

	// ********************************************************* Fields

	private static final Logger LOGGER = LoggerFactory.getLogger(AttacherAnnexeStepDefinitions.class);

	AccepteOuRefuse acceptationDemaut;

	protected List<String> nomsDeFichierNonVides = new ArrayList<String>();
	protected List<String> nomsDeFichierVides = new ArrayList<String>();

	private DemandeAutorisationSteps demandeAutorisationSteps;

	@Autowired
	Environment env;

	// ********************************************************* Technical
	// methods

	public DemandeAutorisationSteps getDemandeAutorisationSteps() {
		return demandeAutorisationSteps;
	}

	public void setDemandeAutorisationSteps(DemandeAutorisationSteps demandeAutorisationSteps) {
		this.demandeAutorisationSteps = demandeAutorisationSteps;
	}

	// ********************************************************* Before

	// ********************************************************* Given

	@Etantdonné("^un demandeur identifié \"([^\"]*)\"$")
	public void le_demandeur_professionnel(
			@Transform(NomEtPrenomDemandeurConverter.class) NomEtPrenomDemandeur nomEtPrenom) throws Throwable {
		demandeAutorisationSteps.initialiserDemandeur(nomEtPrenom);
	}

	@Etantdonné("^une demande de type \"([^\"]*)\" en cours de saisie$")
	public void qu_il_a_une_demande_d_autorisation_de_type_en_cours_de_saisie(ProfessionDeLaSante profession)
			throws Throwable {
		demandeAutorisationSteps.initialiserDemande(profession);
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

	@Etantdonné("^les formats de fichier acceptés:$")
	public void que_les_formats_de_fichier_acceptés(DataTable arg1) throws Throwable {
		List<FormatFichierAccepte> formatsAcceptes = arg1.asList(FormatFichierAccepte.class);
		assertThat(formatsAcceptes).containsExactly(FormatFichierAccepte.values());
	}

	@Etantdonné("^la taille maximale de fichier acceptée \"([^\"]*)\"$")
	public void la_taille_maximale_de_fichier_acceptée(Long tailleMaxEnMB) throws Throwable {
		long tailleMaxEnOctets = tailleMaxEnMB * 1024 * 1024;
		assertThat(tailleMaxEnOctets).isEqualTo(AnnexeValidateur.getTailleMax());
	}

	// ********************************************************* When

	@Lorsque("^le demandeur attache le fichier \"([^\"]*)\" de taille (\\d+)M$")
	public void le_demandeur_attache_le_fichier_certificat_exe(String nomFichier, Integer tailleFichierEnMB)
			throws Throwable {

		creerEtAttacherAnnexe(nomFichier, tailleFichierEnMB);
	}

	// ********************************************************* Méthodes
	// privées

	private void creerEtAttacherAnnexe(String nomFichier, Integer tailleFichier) {

		byte[] contenuFichier = FileMockHelper.buildContenuFichier(tailleFichier);

		Annexe annexe = new Annexe(TypeAnnexe.Certificat, nomFichier, contenuFichier);

		try {
			demandeAutorisationSteps.getDemandeAutorisation().attacherUneAnnexe(annexe);
			acceptationDemaut = AccepteOuRefuse.accepte;
		} catch (AnnexeNonValideException e) {
			acceptationDemaut = AccepteOuRefuse.refuse;
		}
	}

	// ********************************************************* Then

	@Alors("^le système Demaut \"([^\"]*)\" d´attacher cette annexe$")
	public void le_système_Demaut_accepte_ou_refuse_cette_annexe(AccepteOuRefuse action) throws Throwable {
		assertThat(action).isEqualTo(acceptationDemaut);
	}

	@Alors("^les annexes attachées à la demande sont \"([^\"]*)\"$")
	public void le_système_Demaut_annexe_le_fichier_à_la_demande(
			@Transform(ListeDesAnnexesConverter.class) ListeDesAnnexes listeDesAnnexesAttendues) throws Throwable {
		Collection<Annexe> annexesDeLaDemande = demandeAutorisationSteps.getDemandeAutorisation().listerLesAnnexes();
		Collection<Annexe> annexesAttendues = listeDesAnnexesAttendues.listerAnnexes();
		assertThat(annexesDeLaDemande).hasSameSizeAs(annexesAttendues);
		//TODO : vérifier les noms de fichier
	}

}
