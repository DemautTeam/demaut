package ch.vd.demaut.domain.demandeur.donneesProf;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import ch.vd.demaut.commons.annotations.Entity;
import ch.vd.demaut.commons.entities.AbstractEntity;
import ch.vd.demaut.domain.demandes.autorisation.Profession;
import ch.vd.demaut.domain.demandeur.donneesProf.diplome.Diplome;
import ch.vd.demaut.domain.demandeur.donneesProf.diplome.ListeDesDiplomes;
import ch.vd.demaut.domain.demandeur.donneesProf.diplome.ReferenceDeDiplome;

@Entity
public class DonneesProfessionnelles extends AbstractEntity {

    private CodeGLN codeGLN;

    private List<Diplome> diplomes;

    public DonneesProfessionnelles() {
        super();
        this.codeGLN = null;
        this.diplomes = new ArrayList<>();
    }

    // TODO: A virer
    public DonneesProfessionnelles(CodeGLN codeGLN, List<Diplome> diplomes) {
        this.codeGLN = codeGLN;
        this.diplomes = diplomes;
    }

    public void validerEtAjouterDiplome(Diplome diplomeAAjouter) {
        getListeDesDiplomes().ajouterUnDiplome(diplomeAAjouter);
    }

    public void validerEtRenseignerCodeGLN(CodeGLN codeGLNAAjouter) {
        new CodeGLNValidator().valider(codeGLNAAjouter);
        this.codeGLN = codeGLNAAjouter;
    }

    public void supprimerUnDiplome(ReferenceDeDiplome referenceDeDiplome) {
        getListeDesDiplomes().supprimerUnDiplome(referenceDeDiplome);
    }

    public CodeGLN getCodeGLN() {
        return codeGLN;
    }

    public ListeDesDiplomes getListeDesDiplomes() {
        return new ListeDesDiplomes(diplomes);
    }

    /**
     * Liste des professions exigeant un code GLN
     * 
     * @return
     */
    public List<Profession> listerProfessionsExigeantCodeGLN() {
        return new ArrayList<Profession>() {
            {
                add(Profession.Medecin);
                add(Profession.MedecinDentiste);
                add(Profession.Pharmacien);
                add(Profession.Chiropraticien);
                add(Profession.Infirmier);
                add(Profession.Physiotherapeute);
                add(Profession.Ergotherapeute);
                add(Profession.SageFemme);
                add(Profession.Dieteticien);
                add(Profession.PsychotherapeuteNonMedecin);
            }
        };
    }

}
