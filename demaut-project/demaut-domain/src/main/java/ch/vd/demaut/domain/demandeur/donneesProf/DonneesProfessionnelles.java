package ch.vd.demaut.domain.demandeur.donneesProf;

import ch.vd.demaut.commons.annotations.ValueObject;
import ch.vd.demaut.commons.vo.BaseValueObjectWithId;
import ch.vd.demaut.domain.demandeur.donneesProf.diplome.Diplome;
import ch.vd.demaut.domain.demandeur.donneesProf.diplome.ListeDesDiplomes;

import java.util.ArrayList;
import java.util.List;

@ValueObject
public class DonneesProfessionnelles extends BaseValueObjectWithId {

    private CodeGLN codeGLN;

    private List<Diplome> diplomes;

    //Used for OpenJPA only
    protected DonneesProfessionnelles() {
        super();
        this.codeGLN = null;
        this.diplomes = new ArrayList<>();
    }

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
