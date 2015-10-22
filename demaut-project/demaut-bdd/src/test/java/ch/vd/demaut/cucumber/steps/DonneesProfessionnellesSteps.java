package ch.vd.demaut.cucumber.steps;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.joda.time.LocalDate;
import org.springframework.util.StringUtils;

import ch.vd.demaut.commons.bdd.AccepteOuRefuse;
import ch.vd.demaut.cucumber.converteurs.commons.LocalDateConverter;
import ch.vd.demaut.domain.demandeur.Pays;
import ch.vd.demaut.domain.demandeur.donneesProf.diplome.DateObtention;
import ch.vd.demaut.domain.demandeur.donneesProf.diplome.DateReconnaissance;
import ch.vd.demaut.domain.demandeur.donneesProf.diplome.Diplome;
import ch.vd.demaut.domain.demandeur.donneesProf.diplome.ListeDesFormations;
import ch.vd.demaut.domain.demandeur.donneesProf.diplome.ReferenceDeDiplome;
import ch.vd.demaut.domain.demandeur.donneesProf.diplome.TitreFormation;
import ch.vd.demaut.domain.demandeur.donneesProf.diplome.TypeDiplomeAccepte;

public class DonneesProfessionnellesSteps {

    // ********************************************************* Static fields

    // ********************************************************* Fields
    private DemandeAutorisationSteps demandeAutorisationSteps;

    // ********************************************************* Code GLN

    // ********************************************************* Diplome
    private Diplome diplomeEnCours;
    private List<TypeDiplomeAccepte> typeDiplomeAcceptes;
    private TypeDiplomeAccepte typeDiplomeSelectionne;
    private ListeDesFormations listeDesFormations;
    private TitreFormation titreFormation;
    private DateObtention dateObtention;
    private DateReconnaissance dateReconnaissance;
    private AccepteOuRefuse acceptationDateObtention;
    private AccepteOuRefuse acceptationDateReconnaissance;
    private String complement;
    private String critereDiplomeEtranger;

    // ********************************************************* Methods metier

    public void initialiserDiplomeEnCours() {
        this.diplomeEnCours = new Diplome(new ReferenceDeDiplome("refDiplome1"),
                TypeDiplomeAccepte.D_FORMATION_APPROFONDIE, titreFormation, complement, dateObtention, Pays.Allemagne,
                dateReconnaissance);
    }

    public void validerEtAjouterDiplome() {
        demandeAutorisationSteps.getDemandeEnCours().getDonneesProfessionnelles()
                .validerEtAjouterDiplome(this.diplomeEnCours);
    }

    // ********************************************************* Getters &
    // Setters

    public DemandeAutorisationSteps getDemandeAutorisationSteps() {
        return demandeAutorisationSteps;
    }

    public void setDemandeAutorisationSteps(DemandeAutorisationSteps demandeAutorisationSteps) {
        this.demandeAutorisationSteps = demandeAutorisationSteps;
    }

    public Diplome getDiplomeEnCours() {
        return diplomeEnCours;
    }

    public List<TypeDiplomeAccepte> getTypeDiplomeAcceptes() {
        return typeDiplomeAcceptes;
    }

    public void setTypeDiplomeAcceptes(List<TypeDiplomeAccepte> typeDiplomeAcceptes) {
        this.typeDiplomeAcceptes = typeDiplomeAcceptes;
    }

    public TypeDiplomeAccepte getTypeDiplomeSelectionne() {
        return typeDiplomeSelectionne;
    }

    public void setTypeDiplomeSelectionne(TypeDiplomeAccepte typeDiplomeSelectionne) {
        this.typeDiplomeSelectionne = typeDiplomeSelectionne;
    }

    public ListeDesFormations getListeDesFormations() {
        return listeDesFormations;
    }

    public void setListeDesFormations(ListeDesFormations listeDesFormations) {
        this.listeDesFormations = listeDesFormations;
    }

    public TitreFormation getTitreFormation() {
        return titreFormation;
    }

    public void setTitreFormation(TitreFormation titreFormation) {
        this.titreFormation = titreFormation;
    }

    public DateObtention getDateObtention() {
        return dateObtention;
    }

    public void setDateObtention(DateObtention dateObtention) {
        this.dateObtention = dateObtention;
    }

    public DateReconnaissance getDateReconnaissance() {
        return dateReconnaissance;
    }

    public void setDateReconnaissance(DateReconnaissance dateReconnaissance) {
        this.dateReconnaissance = dateReconnaissance;
    }

    public AccepteOuRefuse getAcceptationDateObtention() {
        return acceptationDateObtention;
    }

    public void setAcceptationDateObtention(AccepteOuRefuse acceptationDateObtention) {
        this.acceptationDateObtention = acceptationDateObtention;
    }

    public AccepteOuRefuse getAcceptationDateReconnaissance() {
        return acceptationDateReconnaissance;
    }

    public void setAcceptationDateReconnaissance(AccepteOuRefuse acceptationDateReconnaissance) {
        this.acceptationDateReconnaissance = acceptationDateReconnaissance;
    }

    public String getCritereDiplomeEtranger() {
        return critereDiplomeEtranger;
    }

    public void setCritereDiplomeEtranger(String critereDiplomeEtranger) {
        this.critereDiplomeEtranger = critereDiplomeEtranger;
    }

    public void renseignerComplement(String complement) {
        this.complement = complement;
    }

    public void verifierEtRenseignerDateObtention(String dateObtentionStr) {
        try {
            LocalDateConverter localDateConverter = new LocalDateConverter();
            LocalDate dateObtentionLocal = (LocalDate) localDateConverter.fromString(dateObtentionStr);
            this.dateObtention = new DateObtention(dateObtentionLocal);
        } catch (Exception e) {
            // Format de date invalide
        }
    }

    public void verifierEtRenseignerDateReconnaissance(String dateReconnaissanceStr) {
        assertThat(dateReconnaissanceStr).isNotEmpty();
        try {
            if (this.titreFormation != null && !StringUtils.isEmpty(this.titreFormation.getValue())
                    && !StringUtils.isEmpty(this.critereDiplomeEtranger)
                    && this.titreFormation.getValue().contains(this.critereDiplomeEtranger)) {
                LocalDateConverter localDateConverter = new LocalDateConverter();
                LocalDate dateReconnaissance = (LocalDate) localDateConverter.fromString(dateReconnaissanceStr);
                this.dateReconnaissance = new DateReconnaissance(dateReconnaissance);
            }
        } catch (Exception e) {
            // Format de date invalide
        }
    }

}
