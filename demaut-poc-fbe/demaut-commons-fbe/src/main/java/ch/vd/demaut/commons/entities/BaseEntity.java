package ch.vd.demaut.commons.entities;


/**
 * 
 * {@link Entity} de base qui implémente getId() et setId()
 * 
 */
public abstract class BaseEntity implements Entity<Long> {

    protected Long id;

    @Override
    public Long getId() {
        return id;
    }
    
    @Override
    public void setId(Long id) {
        this.id = id;
    }
    
}
