package ch.vd.demaut.cucumber.converters.demandeurs;

import ch.vd.demaut.domain.demandeurs.NomEtPrenomDemandeur;
import cucumber.api.Transformer;

public class NomEtPrenomDemandeurConverter extends Transformer<NomEtPrenomDemandeur> {

    @Override
    public NomEtPrenomDemandeur transform(String nomEtPrenom) {
        String[] nomPrenom = nomEtPrenom.split(",");
        String nom = nomPrenom[0].trim();
        String prenom = nomPrenom[1].trim();
        return new NomEtPrenomDemandeur(nom, prenom);
    }
}
