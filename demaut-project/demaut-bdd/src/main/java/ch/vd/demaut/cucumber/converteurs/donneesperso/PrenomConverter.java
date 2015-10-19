package ch.vd.demaut.cucumber.converteurs.donneesperso;

import ch.vd.demaut.domain.demandeur.donneesPerso.Prenom;
import cucumber.api.Transformer;

public class PrenomConverter extends Transformer<Prenom> {

    @Override
    public Prenom transform(String str) {
        return new Prenom(str);
    }

}
