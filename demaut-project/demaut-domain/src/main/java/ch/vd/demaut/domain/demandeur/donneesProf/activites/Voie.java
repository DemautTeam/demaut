package ch.vd.demaut.domain.demandeur.donneesProf.activites;

import ch.vd.demaut.commons.annotations.ValueObject;
import ch.vd.demaut.commons.vo.StringVO;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Représente la voie de l'adresse
 *
 * Par exemple :
 *  - "rue Recordon, 1"
 *
 */
@ValueObject
public class Voie extends StringVO {

    // ********************************************************* Constructor

    public Voie() {
    }

    public Voie(String value) {
        super(value);
    }

    @NotNull
    @Override
    public String getValue() {
        return super.getValue();
    }
}
