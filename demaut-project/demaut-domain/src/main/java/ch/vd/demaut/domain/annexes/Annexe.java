package ch.vd.demaut.domain.annexes;

import ch.vd.demaut.commons.annotations.ValueObject;
import ch.vd.demaut.commons.vo.BaseValueObjectWithId;
import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisation;

/**
 * Représente une Annexe associée à une demande {@link DemandeAutorisation}
 */
@ValueObject
public class Annexe extends BaseValueObjectWithId {

    // ********************************************************* Static Fields

    // ********************************************************* Fields
    private TypeAnnexe typeAnnexe;

    private ContenuAnnexe contenu;

    private NomFichier nomFichier;

    // ********************************************************* Constructor

    public Annexe() {
    }

    public Annexe(TypeAnnexe typeAnnexe, String nomFichier, byte[] contenu) {
        super();
        this.typeAnnexe = typeAnnexe;
        this.nomFichier = new NomFichier(nomFichier);
        this.contenu = new ContenuAnnexe(contenu);
    }

    // ********************************************************* Getters

    public TypeAnnexe getTypeAnnexe() {
        return typeAnnexe;
    }

    public ContenuAnnexe getContenu() {
        return contenu;
    }

    public long getTaille() {
        return contenu.getTaille();
    }

    public NomFichier getNomFichier() {
        return nomFichier;
    }
    
    public AnnexeMetadata getAnnexeMetadata() {
    	return new AnnexeMetadata(typeAnnexe, nomFichier.getNomFichier(), getTaille());
    }

    // ********************************************************* Technical methods

    // ********************************************************* Private Methods
}
