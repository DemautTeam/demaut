package ch.vd.demaut.domain.demandeur.donneesProf;

import ch.vd.demaut.commons.annotations.ValueObject;
import ch.vd.demaut.commons.vo.StringVO;
import org.hibernate.validator.constraints.EAN;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Représente un Code GLN d'un professionnel de la santé
 * @see https://fr.wikipedia.org/wiki/EAN_13#Calcul_de_la_cl.C3.A9_de_contr.C3.B4le_EAN_13
 * @see https://en.wikipedia.org/wiki/Global_Location_Number
 */
@ValueObject
public class CodeGLN extends StringVO {

    // ********************************************************* Constructor

    public CodeGLN() {
    }

    public CodeGLN(String value) {
        super(value);
    }

    @NotNull
    @EAN(type = EAN.Type.EAN13)
    @Override
    public String getValue() {
        return super.getValue();
    }

}
