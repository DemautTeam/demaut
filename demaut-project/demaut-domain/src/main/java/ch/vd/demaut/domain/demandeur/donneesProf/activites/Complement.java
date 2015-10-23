package ch.vd.demaut.domain.demandeur.donneesProf.activites;

import ch.vd.demaut.commons.annotations.ValueObject;
import ch.vd.demaut.commons.vo.StringVO;

import javax.validation.constraints.NotNull;

/**
 * Représente le complement d'adresse
 */
@ValueObject
public class Complement extends StringVO {

    // ********************************************************* Constructor

    public Complement() {
    }

    public Complement(String value) {
        super(value);
    }

    @Override
    public String getValue() {
        return super.getValue();
    }
}
