package ch.vd.demaut.domain.annexes;

import ch.vd.demaut.commons.annotations.ValueObject;
import ch.vd.demaut.commons.vo.BaseValueObject;

import javax.validation.constraints.NotNull;

/**
 * Nom du fichier d'une Annexe
 *
 */
@ValueObject
public class NomFichier extends BaseValueObject {

    // ********************************************************* Fields
    @NotNull
    final private String value;

    // ********************************************************* Constructor

    public NomFichier(String value) {
        super();
        this.value = value;
    }

    // ********************************************************* Getter

    public String getValue() {
        return value;
    }
    
    public String extraireExtension() {
    	int lastIndexOfDot = value.lastIndexOf(".");
    	String ext = value.substring(lastIndexOfDot + 1);
    	return ext;
    }

}
