package ch.vd.demaut.domain.demandeur.donneesPerso;

import ch.vd.demaut.commons.vo.BaseValueObject;

public class Permis extends BaseValueObject {

    final private TypePermis typePermis;

    final private AutrePermis autrePermis;

    public Permis(TypePermis typePermis, AutrePermis autrePermis) {
        this.typePermis = typePermis;
        this.autrePermis = autrePermis;
    }

    public Permis(TypePermis typePermis) {
        this.typePermis = typePermis;
        this.autrePermis = new AutrePermis("");
    }

    public Permis(AutrePermis autrePermis) {
        this.typePermis = TypePermis.Autre;
        this.autrePermis = autrePermis;
    }

    public TypePermis getTypePermis() {
        return typePermis;
    }

    public AutrePermis getAutrePermis() {
        return autrePermis;
    }
}
