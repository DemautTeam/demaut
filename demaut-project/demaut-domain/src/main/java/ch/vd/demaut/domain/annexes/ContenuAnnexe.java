package ch.vd.demaut.domain.annexes;

import ch.vd.demaut.commons.annotations.ValueObject;
import ch.vd.demaut.commons.vo.BaseValueObject;

import javax.validation.constraints.NotNull;

/**
 * Contenu d'une annexe {@link Annexe}
 */
@ValueObject
public class ContenuAnnexe extends BaseValueObject {

    // ********************************************************* Fields
    @NotNull
    private byte[] contenu;

    @NotNull
    private long taille; //en octets

    // ********************************************************* Constructor

    public ContenuAnnexe() {
    }

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

    // ********************************************************* Méthodes privées
    private long calculerTaille(byte[] contenu) {
        if (contenu == null) {
            return 0L;
        } else {
            return contenu.length;
        }
    }


}
