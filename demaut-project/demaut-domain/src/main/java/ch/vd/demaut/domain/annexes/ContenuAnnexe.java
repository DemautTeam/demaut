package ch.vd.demaut.domain.annexes;

import ch.vd.demaut.commons.annotations.ValueObject;
import ch.vd.demaut.commons.vo.BaseValueObject;

/**
 * Contenu d'une annexe {@link Annexe}
 *
 */
@ValueObject
public class ContenuAnnexe extends BaseValueObject {

    // ********************************************************* Fields
    final byte[] contenu;

    final long taille; //en octets

    // ********************************************************* Constructor
    public ContenuAnnexe(byte[] contenu) {
        this.contenu = contenu;
        this.taille = calculerTaille(contenu);
    }

    // ********************************************************* Business Methods


    // ********************************************************* Getters
    public byte[] getContenu() {
        return contenu;
    }

    public long getTaille() {
        return taille;
    }

    public double getTailleEnMB() {
        return ((double)taille) / ((double)(1024 * 1024));
    }

    // ********************************************************* Méthodes privées
    private long calculerTaille(byte[] contenu) {
        if (contenu == null) {
            return 0L;
        } else {
            return contenu.length;
        }
    }


}
