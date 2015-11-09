package ch.vd.demaut.domain.demandeur.donneesPerso;

import ch.vd.demaut.commons.annotations.ValueObject;
import ch.vd.demaut.commons.vo.BaseValueObject;
import ch.vd.demaut.commons.vo.StringVOInterface;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

//TODO refactorer le packaging de cette classe. Elle est reutilisé par Etablissement
@ValueObject
public class Nom extends BaseValueObject implements StringVOInterface {

    private String value;

    // ********************************************************* Constructor

    //Used only for JPA
    protected Nom() {
    }

    public Nom(String value) {
        this.value = value;
    }

    // ********************************************************* Getters
    @NotNull
    @Size(min = 1, max = 120)
    public String getValue() {
        return value;
    }
}
