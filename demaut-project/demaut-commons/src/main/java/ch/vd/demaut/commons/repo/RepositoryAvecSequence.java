package ch.vd.demaut.commons.repo;

import ch.vd.demaut.commons.entities.Entity;


/**
 * Gestion générique des méthodes basiques sur le repository d'une {@link Entity} qui gère également une séquence.
 */
public interface RepositoryAvecSequence {

    /**
     * Incrémente et Renvoie le prochain numero de la séquence gérée par le repo
     *
     * @return Le prochain numéro de la Sequence
     */
    Long incrementerProchainNumeroSequence();
}