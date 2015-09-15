package ch.vd.demaut.commons.vo;

public abstract class BaseValueObjectWithId extends BaseValueObject {

    // ********************************************************* Fields
    protected Long id;

    // ********************************************************* Getters
    public Long getId() {
        return id;
    }

    // ********************************************************* Setters
    public void setId(Long id) {
        this.id = id;
    }
}
