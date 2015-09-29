package ch.vd.demaut.domain.demandeur.donneesProf;

import java.util.ArrayList;
import java.util.List;

import ch.vd.demaut.commons.annotations.Entity;
import ch.vd.demaut.commons.entities.AbstractEntity;
import ch.vd.demaut.domain.demandeur.donneesProf.diplome.Diplome;
import ch.vd.demaut.domain.demandeur.donneesProf.diplome.ListeDesDiplomes;

@Entity
public class DonneesProfessionnelles extends AbstractEntity {

    private CodeGLN codeGLN;

    private List<Diplome> diplomes;

    public DonneesProfessionnelles() {
        super();
        this.codeGLN = null;
        this.diplomes = new ArrayList<>();
    }

    //TODO: A virer
    public DonneesProfessionnelles(CodeGLN codeGLN, List<Diplome> diplomes) {
        this.codeGLN = codeGLN;
        this.diplomes = diplomes;
    }

    public void validerEtAjouterDiplome(Diplome diplomeAAjouter) {
        getListeDesDiplomes().ajouterDiplome(diplomeAAjouter);
    }

    public CodeGLN getCodeGLN() {
        return codeGLN;
    }

    public ListeDesDiplomes getListeDesDiplomes() {
        return new ListeDesDiplomes(diplomes);
    }
}
