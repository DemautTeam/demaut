package ch.vd.demaut.commons.vo;

import ch.vd.demaut.commons.entities.Entity;

public abstract class BaseValueObjectWithId extends BaseValueObject implements Entity<Long> {

    protected Long id;

    protected int version;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int getVersion() {
        return version;
    }

    @Override
    public void setVersion(int version) {
        this.version = version;
    }

}
