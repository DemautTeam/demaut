package ch.vd.demaut.commons.vo;

abstract public class StringVO extends BaseValueObject {

    // ********************************************************* Fields
    final protected String value;

    // ********************************************************* Constructor
    protected StringVO(String value) {
        super();
        this.value = value;
    }

    // ********************************************************* Getters
    public String getValue() {
        return value;
    }


}
