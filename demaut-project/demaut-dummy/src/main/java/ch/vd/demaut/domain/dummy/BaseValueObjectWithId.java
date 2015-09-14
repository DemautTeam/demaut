package ch.vd.demaut.domain.dummy;

import ch.vd.demaut.commons.vo.BaseValueObject;

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
