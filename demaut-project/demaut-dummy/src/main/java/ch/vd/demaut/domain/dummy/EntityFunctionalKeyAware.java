package ch.vd.demaut.domain.dummy;

import ch.vd.demaut.commons.entities.Entity;
import ch.vd.demaut.commons.fk.ObjectFunctionalKeyAware;

abstract public class EntityFunctionalKeyAware extends ObjectFunctionalKeyAware implements Entity<Long> {

    protected Long id; //Technical Id

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
