package ch.vd.demaut.cucumber.converteurs.donneesperso;

import ch.vd.demaut.domain.utilisateurs.Login;
import cucumber.api.Transformer;

public class NomConverter extends Transformer<Login> {

    @Override
    public Login transform(String str) {
        return new Login(str);
    }

}
