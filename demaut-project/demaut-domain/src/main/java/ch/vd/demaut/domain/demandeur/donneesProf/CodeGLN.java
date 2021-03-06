package ch.vd.demaut.domain.demandeur.donneesProf;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import ch.vd.demaut.commons.vo.StringVOInterface;
import org.hibernate.validator.constraints.EAN;

import ch.vd.demaut.commons.annotations.ValueObject;
import ch.vd.demaut.commons.vo.BaseValueObject;

/**
 * Représente un Code GLN d'un professionnel de la santé
 *
 * @see https://fr.wikipedia.org/wiki/EAN_13#Calcul_de_la_cl.C3.A9_de_contr.C3.B4le_EAN_13
 * @see https://en.wikipedia.org/wiki/Global_Location_Number
 */
@ValueObject
public class CodeGLN extends BaseValueObject implements StringVOInterface {

    private String value;

    // ********************************************************* Constructor

    //Used for JPA Only
    CodeGLN() {
    }

    public CodeGLN(String value) {
        this.value = value;
    }

    @NotNull
    @EAN(type = EAN.Type.EAN13)
    @Size(min = 13, max = 13)
    public String getValue() {
        return value;
    }

}
