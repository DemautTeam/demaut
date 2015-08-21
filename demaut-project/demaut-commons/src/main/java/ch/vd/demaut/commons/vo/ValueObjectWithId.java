package ch.vd.demaut.commons.vo;

import ch.vd.demaut.commons.entities.Entity;

import java.io.Serializable;

/**
 * Cette interface désigne un Value Object qui doit être persisté dans une table DB
 * <p/>
 * Ce VO a besoin techniquement d'un Id (pour Hibernate notamment)
 * <p/>
 * FIXME: Générer l'ID en fonction des valeurs du VO. Utilisé pour cela une function de hashage parfaite (pour éviter
 * les collisions)
 */
public interface ValueObjectWithId<I extends Serializable> extends ValueObject, Entity<I> {

}
