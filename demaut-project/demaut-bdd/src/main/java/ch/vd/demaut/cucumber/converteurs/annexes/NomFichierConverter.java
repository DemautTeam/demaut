package ch.vd.demaut.cucumber.converteurs.annexes;

import ch.vd.demaut.domain.annexes.NomFichier;
import cucumber.api.Transformer;

public class NomFichierConverter extends Transformer<NomFichier> {

    @Override
    public NomFichier transform(String str) {
        return new NomFichier(str);
    }

}
