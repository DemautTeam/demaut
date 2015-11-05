package ch.vd.demaut.cucumber.converteurs.commons;

import ch.vd.demaut.domain.demandeur.donneesProf.activites.NPAProfessionnel;
import cucumber.api.Transformer;

public class NPAProfessionnelConverter extends Transformer<NPAProfessionnel> {

    @Override
    public NPAProfessionnel transform(String str) {
        return new NPAProfessionnel(str);
    }
}
