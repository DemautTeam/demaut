package ch.vd.demaut.commons.vo;

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

    public String getValue() {
        return value;
    }


}
