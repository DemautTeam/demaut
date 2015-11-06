package ch.vd.demaut.commons.entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Predicate;

import ch.vd.demaut.commons.exceptions.EntityNotFoundException;
import ch.vd.demaut.commons.exceptions.EntityNotUniqueException;
import ch.vd.demaut.commons.fk.FunctionalKey;

/**
 * 
 * Conteneur d'une liste de {@link EntityFunctionalKeyAware} uniques
 *
 * @param <E> {@link EntityFunctionalKeyAware}
 */
public abstract class EntityFKAList<E extends EntityFunctionalKeyAware> {

    // ********************************************************* Fields
    private List<E> entities;
    
    // ********************************************************* Constructors
    public EntityFKAList() {
        this.entities = new ArrayList<E>();
    }
    
    public EntityFKAList(List<E> entities) {
        this.entities = entities;
    }
    
    // ********************************************************* Methodes metier
    public int taille() {
        return entities.size();
    }

    protected void ajouterEntity(E entity) {
        validateUnique(entity);
        entities.add(entity);
    }
    
    protected void supprimerEntity(FunctionalKey<E> eFK) {
        E entityTrouvee = trouverEntity(eFK);
        entities.remove(entityTrouvee);
    }
    
    protected List<E> listerEntities() {
        return Collections.unmodifiableList(entities);
    }
    
    @SuppressWarnings("unchecked")
    protected E trouverEntity(final FunctionalKey<E> eFK) {
        
        Object entity = CollectionUtils.find(entities, new Predicate<E>() {
            @Override
            public boolean evaluate(E object) {
                return object.getFunctionalKey().equals(eFK);
            }
        });
        
        if (entity == null) {
            throw new EntityNotFoundException(eFK);
        }
        return (E)entity;
    }
    
    protected void validateUnique(E e) {
        if (entities.contains(e)) {
            throw new EntityNotUniqueException();
        }
    }
}
