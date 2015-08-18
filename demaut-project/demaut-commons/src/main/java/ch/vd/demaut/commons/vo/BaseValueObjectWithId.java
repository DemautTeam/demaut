package ch.vd.demaut.commons.vo;

import ch.vd.demaut.commons.entities.Entity;

public abstract class BaseValueObjectWithId extends BaseValueObject implements Entity<Long>{

	// ********************************************************* Fields
	protected Long id;

	// ********************************************************* Getters
    @Override
    public Long getId() {
        return id;
    }
    
	// ********************************************************* Setters
    @Override
    public void setId(Long id) {
        this.id = id;
    }
	
}
