package ch.vd.demaut.cucumber.converteurs.commons;


import ch.vd.demaut.domain.demandeur.donneesProf.activites.Voie;
import cucumber.api.Transformer;

public class VoieConverter extends Transformer<Voie> {

    @Override
    public Voie transform(String str) {
        return new Voie(str);
    }

}
