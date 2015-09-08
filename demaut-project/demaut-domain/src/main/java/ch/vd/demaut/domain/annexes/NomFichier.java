package ch.vd.demaut.domain.annexes;

import ch.vd.demaut.commons.annotations.ValueObject;
import ch.vd.demaut.commons.vo.BaseValueObject;

import javax.validation.constraints.NotNull;

/**
 * Nom du fichier d'une Annexe
 */
@ValueObject
public class NomFichier extends BaseValueObject {

    // ********************************************************* Fields
    @NotNull
    private String nomFichier;

    // ********************************************************* Constructor

    public NomFichier() {
    }

    public NomFichier(String nomFichier) {
        super();
        this.nomFichier = nomFichier;
    }

    // ********************************************************* Getter

    public String getNomFichier() {
        return nomFichier;
    }

    public String extraireExtension() {
        int lastIndexOfDot = nomFichier.lastIndexOf(".");
        String ext = nomFichier.substring(lastIndexOfDot + 1);
        return ext;
    }

}
