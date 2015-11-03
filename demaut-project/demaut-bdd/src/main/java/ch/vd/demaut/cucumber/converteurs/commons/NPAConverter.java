package ch.vd.demaut.cucumber.converteurs.commons;

import ch.vd.demaut.domain.demandeur.donneesPerso.NPA;
import cucumber.api.Transformer;
import org.apache.commons.lang.StringUtils;

public class NPAConverter extends Transformer<NPA> {

        @Override
        public NPA transform(String str ) {
            return StringUtils.isEmpty(str)? null: new NPA(str);
        }

}
