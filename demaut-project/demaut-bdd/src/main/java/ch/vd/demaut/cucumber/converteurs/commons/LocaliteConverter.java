package ch.vd.demaut.cucumber.converteurs.commons;


import ch.vd.demaut.domain.demandeur.Localite;
import ch.vd.demaut.domain.demandeur.donneesProf.activites.Voie;
import cucumber.api.Transformer;

public class LocaliteConverter extends Transformer<Localite> {

    @Override
    public Localite transform(String str) {
        return new Localite(str);
    }

}
