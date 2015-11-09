package ch.vd.demaut.domain.demandes;

/**
 * Implémentation mémoire d'un {@link Sequenceur} <br>
 * Ce séquenceur se réinitialise à zéro à chaque démarrage de l'application <br>
 * NE PAS UTILISER CE SEQUENCEUR EN PRODUCTION
 */
public class SequenceurImplStatic implements Sequenceur {

    static Long sequence = 0L;

    /**
     * {@inheritDoc}
     */
    @Override
    public Long nextSequence() {
        sequence++;
        return sequence;
    }

    @Override
    public void reset() {
        sequence = 0L;
    }

}
