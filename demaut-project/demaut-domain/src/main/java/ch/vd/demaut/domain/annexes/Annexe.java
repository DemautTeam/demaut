package ch.vd.demaut.domain.annexes;

import ch.vd.demaut.commons.annotations.ValueObject;
import ch.vd.demaut.commons.vo.BaseValueObjectWithId;

import javax.validation.constraints.NotNull;

/**
 * Représente une Annexe associée à une demande
 */
@ValueObject
public class Annexe extends BaseValueObjectWithId {

    // ********************************************************* Fields
    @NotNull
    final private TypeAnnexe typeAnnexe;

    @NotNull
    final private ContenuAnnexe contenu;

    @NotNull
    final private FormatFichier formatFichier;

    @NotNull
    final private NomFichier nomFichier;

    // ********************************************************* Constructor
    public Annexe(TypeAnnexe typeAnnexe, String nomFichier, byte[] contenu) {
        super();
        this.typeAnnexe = typeAnnexe;
        this.nomFichier = new NomFichier(nomFichier);
        this.contenu = new ContenuAnnexe(contenu);
        this.formatFichier = FormatFichier.PDF;
    }
    // ********************************************************* Business Methods

    /**
     * Valide le contenu de l'annexe.
     * Renvoie l'exception {@link ContenuAnnexeNonValideException} si non valide
     */
    public void validerContenu() {
        contenu.validerContenu();
    }


    // ********************************************************* Getters

    public TypeAnnexe getTypeAnnexe() {
        return typeAnnexe;
    }

    public ContenuAnnexe getContenu() {
        return contenu;
    }

    public FormatFichier getFormatFichier() {
        return formatFichier;
    }

    public long getTaille() {
        return contenu.getTaille();
    }

    public NomFichier getNomFichier() {
        return nomFichier;
    }

    // ********************************************************* Technical methods

    // ********************************************************* Private Methods
}
