package ch.vd.demaut.domain.demandes;

/**
 * Sequenceur permettant de générer une séquence <br>
 * Généralement implémenté par une séquence DB mais pas forcément
 * 
 */
public interface Sequenceur {

    /**
     * Renvoie la prochaine séquence
     * @return
     */
    Long nextSequence();
    
    /**
     * Reinitialise le séquenceur
     */
    void reset();
    
}
