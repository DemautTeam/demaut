package ch.vd.demaut.domain.annexes;

import ch.vd.demaut.commons.annotations.ValueObject;
import ch.vd.demaut.commons.vo.BaseValueObject;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Nom du fichier d'une Annexe
 */
@ValueObject
public class NomFichier extends BaseValueObject {

    // ********************************************************* Fields
    private String nomFichier;

    // ********************************************************* Constructor

    public NomFichier() {
    }

    public NomFichier(String nomFichier) {
        super();
        this.nomFichier = nomFichier;
    }

    // ********************************************************* Getter

    @NotNull
    @Size(max = 255)
    public String getNomFichier() {
        return nomFichier;
    }

    //FIXME : la gestion des extensions est une notion Ms Dos, il faut checker le mime type.
    public String extraireExtension() {
        int lastIndexOfDot = nomFichier.lastIndexOf(".");
        return nomFichier.substring(lastIndexOfDot + 1);
    }

    //FIXME : la gestion des extensions est une notion Ms Dos, il faut checker le mime type.
    public String extraireNomSansExtension() {
        int lastIndexOfDot = nomFichier.lastIndexOf(".");
        return nomFichier.substring(0, lastIndexOfDot);
    }
}
