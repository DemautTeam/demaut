package ch.vd.demaut.cucumber.converteurs.donneesperso;

import ch.vd.demaut.domain.demandeur.donneesPerso.Nationalite;
import cucumber.api.Transformer;

public class NationaliteConverter extends Transformer<Nationalite> {

    @Override
    public Nationalite transform(String str) {
        return new Nationalite(str);
    }

}
