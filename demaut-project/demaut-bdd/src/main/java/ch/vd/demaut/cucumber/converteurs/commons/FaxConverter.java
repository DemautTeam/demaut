package ch.vd.demaut.cucumber.converteurs.commons;

import ch.vd.demaut.domain.demandeur.Fax;
import cucumber.api.Transformer;

public class FaxConverter extends Transformer<Fax> {

    @Override
    public Fax transform(String str) {
        return new Fax(str);
    }
}
