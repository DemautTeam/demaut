package ch.vd.demaut.domain.demandeur.donneesProf;

import java.util.ArrayList;
import java.util.List;

import ch.vd.demaut.commons.annotations.Entity;
import ch.vd.demaut.commons.entities.AbstractEntity;
import ch.vd.demaut.domain.annexes.ProcedureAnnexe;
import ch.vd.demaut.domain.demandes.autorisation.Profession;
import ch.vd.demaut.domain.demandeur.donneesProf.activites.ActiviteAnterieure;
import ch.vd.demaut.domain.demandeur.donneesProf.activites.ActiviteFuture;
import ch.vd.demaut.domain.demandeur.donneesProf.activites.ListeDesActivitesAnterieures;
import ch.vd.demaut.domain.demandeur.donneesProf.activites.ListeDesActivitesFutures;
import ch.vd.demaut.domain.demandeur.donneesProf.activites.ReferenceActiviteAnterieure;
import ch.vd.demaut.domain.demandeur.donneesProf.diplome.Diplome;
import ch.vd.demaut.domain.demandeur.donneesProf.diplome.DiplomeFK;
import ch.vd.demaut.domain.demandeur.donneesProf.diplome.ListeDesDiplomes;
import ch.vd.demaut.domain.exception.CodeGlnObligatoireException;

@Entity
public class DonneesProfessionnelles extends AbstractEntity {

    // ********************************************************* Fields
    private CodeGLN codeGLN;

    private List<Diplome> diplomes;

    private transient List<ActiviteFuture> activiteFutures;
    
    //TODO: Ajouter la reference a la DemandeAutorisation (ou si pas necessaire a la profession)
    //private Profession profession; 
    
    private List<ActiviteAnterieure> activitesAnterieures;

    // ********************************************************* Constructor
    
    //Seulement pour JPA
    public DonneesProfessionnelles() {
        super();
        this.codeGLN = null;
        this.diplomes = new ArrayList<Diplome>();
        this.activiteFutures = new ArrayList<ActiviteFuture>();
        this.activitesAnterieures = new ArrayList<ActiviteAnterieure>();
    }

    // TODO: A virer
    public DonneesProfessionnelles(CodeGLN codeGLN, List<Diplome> diplomes) {
        this.codeGLN = codeGLN;
        this.diplomes = diplomes;
        this.activiteFutures = new ArrayList<ActiviteFuture>();
        this.activitesAnterieures = new ArrayList<ActiviteAnterieure>();
    }

    // ********************************************************* Methodes metiers
    public void validerEtAjouterDiplome(Diplome diplomeAAjouter) {
        getListeDesDiplomes().ajouterUnDiplome(diplomeAAjouter);
    }

    public void validerEtAjouterActiviteFuture(ActiviteFuture activiteFutureAAjouter) {
        getActiviteFutures().ajouterUneActiviteFuture(activiteFutureAAjouter);
    }

    /**
     * Valide si le Code GLN est obligatoire et correct.
     * Si pas obligatoire, le code peut être nul
     * @param codeGlnAAjouter CodeGLN
     */
    public void validerEtRenseignerCodeGLN(CodeGLN codeGlnAAjouter, Profession profession) {

        throwExceptionSiNullEtObligatoire(codeGlnAAjouter, profession);

        throwExceptionSiNonNullEtInvalide(codeGlnAAjouter);

        this.codeGLN = codeGlnAAjouter;
    }

    public void supprimerUnDiplome(DiplomeFK diplomeFK) {
        getListeDesDiplomes().supprimerUnDiplome(diplomeFK);
    }
    
    public void creerEtAjouterActiviteAnterieure() {
        ReferenceActiviteAnterieure nouvelleReference = getActivitesAnterieures().genererNouvelleReference();
        ActiviteAnterieure nouvelleActivite = new ActiviteAnterieure(nouvelleReference);
        
        ajouterActiviteAnterieure(nouvelleActivite);
    }
    
    public void ajouterActiviteAnterieure(ActiviteAnterieure activiteAnterieure) {
        getActivitesAnterieures().ajouterActivite(activiteAnterieure);
    }

    public ProcedureAnnexe calculerProcedureAnnexe() {
        int tailleActivitesAnterieures = getActivitesAnterieures().taille();
        if (tailleActivitesAnterieures > 0) {
            return ProcedureAnnexe.Simplifiee;
        } else {
            return ProcedureAnnexe.Ordinaire;
        }
    }

    public boolean contientDiplomesEtrangers() {
        for (Diplome diplome : getListeDesDiplomes().listerDiplomes()) {
            if (diplome.getPaysObtention().estEtranger()) {
                return true;
            }
        }
        return false;
    }

    // ********************************************************* Getters
    public CodeGLN getCodeGLN() {
        return codeGLN;
    }

    public ListeDesDiplomes getListeDesDiplomes() {
        return new ListeDesDiplomes(diplomes);
    }

    public ListeDesActivitesFutures getActiviteFutures() {
        return new ListeDesActivitesFutures(activiteFutures);
    }
    
    public ListeDesActivitesAnterieures getActivitesAnterieures() {
        return new ListeDesActivitesAnterieures(activitesAnterieures);
    }
    
    // ********************************************************* Methodes privées
    private void throwExceptionSiNullEtObligatoire(CodeGLN codeGlnAAjouter, Profession profession) {
        if (codeGlnAAjouter == null) {
            if (profession.isCodeGLNObligatoire()) {
                throw new CodeGlnObligatoireException();
            }
        }
    }
    
    private void throwExceptionSiNonNullEtInvalide(CodeGLN codeGlnAAjouter) {
        if (codeGlnAAjouter != null) {
            new CodeGLNValidator().valider(codeGlnAAjouter);
        }
    }

}
