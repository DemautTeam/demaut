package ch.vd.demaut.commons.vo;

import javax.validation.constraints.NotNull;

/**
 * Represente un VO avec un seul attribut comme String <br>
 * On ne peut pas l'utiliser (pour le moment) car inheritance of embedded est problematique
 * La solution à appliquer : 
 * @see http://stackoverflow.com/a/1141575/144012 (Reponse B)
 *
 */
abstract public class StringVO extends BaseValueObject {

    // ********************************************************* Fields

    //Not final bec. of OpenJPA
    protected String value;

    // ********************************************************* Constructor

    //ONly for OpenJA
    protected StringVO() {

    }

    protected StringVO(String value) {
        super();
        this.value = value;
    }

    // ********************************************************* Getters

    @NotNull
    public String getValue() {
        return value;
    }

}
