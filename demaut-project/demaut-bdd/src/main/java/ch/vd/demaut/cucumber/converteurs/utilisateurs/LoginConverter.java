package ch.vd.demaut.cucumber.converteurs.utilisateurs;

import ch.vd.demaut.domain.utilisateurs.Login;
import cucumber.api.Transformer;

public class LoginConverter extends Transformer<Login> {

    @Override
    public Login transform(String str) {
        return new Login(str);
    }

}
