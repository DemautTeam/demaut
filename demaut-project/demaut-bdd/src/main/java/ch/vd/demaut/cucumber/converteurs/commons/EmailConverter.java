package ch.vd.demaut.cucumber.converteurs.commons;

import ch.vd.demaut.domain.demandeur.Email;
import cucumber.api.Transformer;

public class EmailConverter extends Transformer<Email> {

    @Override
    public Email transform(String str) {
        if (str.isEmpty()) {
            return null;
        }
        return new Email(str);
    }

}
