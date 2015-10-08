package ch.vd.demaut.cucumber.converteurs.donneesperso;

import ch.vd.demaut.domain.demandeur.donneesPerso.Nom;
import cucumber.api.Transformer;

public class PrenomConverter extends Transformer<Nom> {

    @Override
    public Nom transform(String str) {
        return new Nom(str);
    }

}
