package ch.vd.demaut.commons.entities;

import ch.vd.demaut.commons.fk.ObjectFunctionalKeyAware;

abstract public class EntityFunctionalKeyAware extends ObjectFunctionalKeyAware implements Entity<Long> {

    protected Long id; //Technical Id

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
