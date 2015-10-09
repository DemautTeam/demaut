package ch.vd.demaut.cucumber.converteurs.donneesperso;

import ch.vd.demaut.domain.demandeur.donneesPerso.Nom;
import ch.vd.demaut.domain.utilisateurs.Login;
import cucumber.api.Transformer;

public class NomConverter extends Transformer<Nom> {

    @Override
    public Nom transform(String str) {
        return new Nom(str);
    }

}
