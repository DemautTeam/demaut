package ch.vd.demaut.cucumber.converteurs.donneesperso;

import ch.vd.demaut.domain.demandeur.donneesPerso.Nom;
import cucumber.api.Transformer;

public class NomConverter extends Transformer<Nom> {

    @Override
    public Nom transform(String str) {
        if (str.isEmpty()) {
            return null;
        }
        return new Nom(str);
    }

}
