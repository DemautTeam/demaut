package ch.vd.demaut.cucumber.converteurs.donneesperso;

import ch.vd.demaut.domain.demandeur.donneesPerso.Nom;
import ch.vd.demaut.domain.demandeur.donneesPerso.TelephonePrive;
import cucumber.api.Transformer;

public class TelephonePriveConverter extends Transformer<TelephonePrive> {

    @Override
    public TelephonePrive transform(String str) {
        return new TelephonePrive(str);
    }

}
