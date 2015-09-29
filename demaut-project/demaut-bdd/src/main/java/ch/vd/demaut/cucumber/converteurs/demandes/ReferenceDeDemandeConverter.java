package ch.vd.demaut.cucumber.converteurs.demandes;

import ch.vd.demaut.domain.demandes.ReferenceDeDemande;
import cucumber.api.Transformer;

public class ReferenceDeDemandeConverter extends Transformer<ReferenceDeDemande> {

    @Override
    public ReferenceDeDemande transform(String str) {
        return new ReferenceDeDemande(str);
    }

}
