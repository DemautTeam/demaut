package ch.vd.demaut.cucumber.converteurs.commons;

import ch.vd.demaut.domain.demandeur.Telephone;
import cucumber.api.Transformer;

public class TelephoneConverter extends Transformer<Telephone> {

    @Override
    public Telephone transform(String str) {
        if (str.isEmpty()) {
            return null;
        }
        return new Telephone(str);
    }
}
