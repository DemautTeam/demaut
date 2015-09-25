package ch.vd.demaut.domain.demandeur.donneesProf;

import ch.vd.demaut.commons.annotations.ValueObject;
import ch.vd.demaut.commons.vo.BaseValueObject;
import ch.vd.demaut.domain.demandeur.donneesProf.diplome.Diplome;
import ch.vd.demaut.domain.demandeur.donneesProf.diplome.ListeDesDiplomes;

@ValueObject
public class DonneesProfessionnelles extends BaseValueObject {

    final private CodeGLN codeGLN;

    final private ListeDesDiplomes listeDesDiplomes;

    public DonneesProfessionnelles(CodeGLN codeGLN, ListeDesDiplomes listeDesDiplomes) {
        this.codeGLN = codeGLN;
        this.listeDesDiplomes = listeDesDiplomes;
    }

    public void validerEtAjouterDiplome(Diplome diplomeAAjouter) {
        getListeDesDiplomes().ajouterDiplome(diplomeAAjouter);
    }

    public CodeGLN getCodeGLN() {
        return codeGLN;
    }

    public ListeDesDiplomes getListeDesDiplomes() {
        return listeDesDiplomes;
    }
}
