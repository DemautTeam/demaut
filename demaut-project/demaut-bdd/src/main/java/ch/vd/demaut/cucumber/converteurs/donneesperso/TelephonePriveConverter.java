package ch.vd.demaut.cucumber.converteurs.donneesperso;

import ch.vd.demaut.domain.demandeur.donneesPerso.TelephonePrive;
import cucumber.api.Transformer;
import org.apache.commons.lang.StringUtils;

public class TelephonePriveConverter extends Transformer<TelephonePrive> {

    @Override
    public TelephonePrive transform(String str) {
        if(StringUtils.isEmpty(str)){
            return null;
        }
        return new TelephonePrive(str);
    }

}
