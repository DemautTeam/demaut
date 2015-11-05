package ch.vd.demaut.cucumber.converteurs.activitesFutures;

import ch.vd.demaut.domain.demandeur.donneesPerso.TelephoneMobile;
import cucumber.api.Transformer;

public class TelephoneMobileConverter extends Transformer<TelephoneMobile> {

    @Override
    public TelephoneMobile transform(String str) {
        return new TelephoneMobile(str);
    }

}
