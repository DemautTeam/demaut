package ch.vd.demaut.domain.demandeur.donneesPerso;

import ch.vd.demaut.commons.vo.BaseValueObject;

/**
 * Created by mourad on 14.09.15.
 */
public class Permis extends BaseValueObject {
    final private TypePermis typePermis;

    final private AutrePermis autrePermis;

    public Permis(TypePermis typePermis, AutrePermis autrePermis) {
        this.typePermis = typePermis;
        this.autrePermis = autrePermis;
    }

    public Permis(TypePermis typePermis){
        this.typePermis = typePermis;
        this.autrePermis = new AutrePermis("");
    }

    public TypePermis getTypePermis() {
        return typePermis;
    }

    public AutrePermis getAutrePermis() {
        return autrePermis;
    }
}
