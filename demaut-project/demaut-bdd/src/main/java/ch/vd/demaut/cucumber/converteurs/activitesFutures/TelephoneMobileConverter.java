package ch.vd.demaut.cucumber.converteurs.activitesFutures;

import ch.vd.demaut.domain.demandeur.donneesPerso.TelephoneMobile;
import cucumber.api.Transformer;
import org.apache.commons.lang.StringUtils;

public class TelephoneMobileConverter extends Transformer<TelephoneMobile> {

    @Override
    public TelephoneMobile transform(String str) {
        if(StringUtils.isEmpty(str)){
            return null;
        }
        return new TelephoneMobile(str);
    }

}
