package ch.vd.demaut.commons.entities;


/**
 * {@link Entity} de base qui implémente getId() et setId(),  getVersion() et setVersion()
 */
public abstract class BaseEntity implements Entity<Long> {

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
