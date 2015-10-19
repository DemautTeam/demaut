package ch.vd.demaut.cucumber.converteurs.donneesperso;

import ch.vd.demaut.domain.demandeur.donneesPerso.TelephoneMobile;
import ch.vd.demaut.domain.demandeur.donneesPerso.TelephonePrive;
import cucumber.api.Transformer;

public class TelephoneMobileConverter extends Transformer<TelephoneMobile> {

    @Override
    public TelephoneMobile transform(String str) {
        return new TelephoneMobile(str);
    }

}
