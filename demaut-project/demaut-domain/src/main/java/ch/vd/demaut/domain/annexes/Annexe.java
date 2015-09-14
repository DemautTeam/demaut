package ch.vd.demaut.domain.annexes;

import ch.vd.demaut.commons.annotations.ValueObject;
import ch.vd.demaut.commons.vo.BaseValueObjectWithId;
import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisation;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Représente une Annexe associée à une demande {@link DemandeAutorisation}
 */
@ValueObject
public class Annexe extends BaseValueObjectWithId {

    @NotNull
    private byte[] contenuAnnexe;

    @NotNull
    @Valid
    private AnnexeMetadata annexeMetadata;

    // ********************************************************* Constructor

    public Annexe() {
    }

    public Annexe(TypeAnnexe typeAnnexe, String nomFichier, byte[] contenu) {
        super();
        this.contenuAnnexe = contenu;
        this.annexeMetadata = new AnnexeMetadata(typeAnnexe, nomFichier, calculerTaille(contenu));
    }

    // ********************************************************* Getters

    public AnnexeMetadata getAnnexeMetadata() {
        return annexeMetadata;
    }

    public byte[] getContenuAnnexe() {
        return contenuAnnexe;
    }
    // ********************************************************* Technical methods

    private long calculerTaille(byte[] contenu) {
        if (contenu == null) {
            return 0L;
        } else {
            return contenu.length;
        }
    }

    // ********************************************************* Private Methods
}
