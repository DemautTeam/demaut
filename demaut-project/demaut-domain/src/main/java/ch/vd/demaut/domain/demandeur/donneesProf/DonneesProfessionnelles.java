package ch.vd.demaut.domain.demandeur.donneesProf;

import ch.vd.demaut.commons.annotations.Entity;
import ch.vd.demaut.commons.entities.AbstractEntity;
import ch.vd.demaut.domain.demandes.autorisation.Profession;
import ch.vd.demaut.domain.demandeur.donneesProf.activites.ActiviteFuture;
import ch.vd.demaut.domain.demandeur.donneesProf.activites.ListeDesActivitesFutures;
import ch.vd.demaut.domain.demandeur.donneesProf.diplome.Diplome;
import ch.vd.demaut.domain.demandeur.donneesProf.diplome.ListeDesDiplomes;
import ch.vd.demaut.domain.demandeur.donneesProf.diplome.ReferenceDeDiplome;
import ch.vd.demaut.domain.exception.CodeGlnObligatoireException;

import java.util.ArrayList;
import java.util.List;

@Entity
public class DonneesProfessionnelles extends AbstractEntity {

    // ********************************************************* Fields
    private CodeGLN codeGLN;

    private List<Diplome> diplomes;

    private transient List<ActiviteFuture> activiteFutures;

    //TODO: Ajouter la reference a la DemandeAutorisation (ou si pas necessaire a la profession)
    //private Profession profession; 

    // ********************************************************* Constructor

    //Seulement pour JPA
    public DonneesProfessionnelles() {
        super();
        this.codeGLN = null;
        this.diplomes = new ArrayList<>();
        this.activiteFutures = new ArrayList<>();
    }

    // TODO: A virer
    public DonneesProfessionnelles(CodeGLN codeGLN, List<Diplome> diplomes) {
        this.codeGLN = codeGLN;
        this.diplomes = diplomes;
        this.activiteFutures = new ArrayList<>();
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
     *
     * @param codeGlnAAjouter CodeGLN
     */
    public void validerEtRenseignerCodeGLN(CodeGLN codeGlnAAjouter, Profession profession) {

        throwExceptionSiNullEtObligatoire(codeGlnAAjouter, profession);

        throwExceptionSiNonNullEtInvalide(codeGlnAAjouter);

        this.codeGLN = codeGlnAAjouter;
    }

    public void supprimerUnDiplome(ReferenceDeDiplome referenceDeDiplome) {
        getListeDesDiplomes().supprimerUnDiplome(referenceDeDiplome);
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

    public List<Diplome> getDiplomes() {
        return diplomes;
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
