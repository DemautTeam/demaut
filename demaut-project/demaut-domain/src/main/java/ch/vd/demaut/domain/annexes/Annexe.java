package ch.vd.demaut.domain.annexes;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;

import ch.vd.demaut.commons.annotations.Entity;
import ch.vd.demaut.commons.entities.EntityFunctionalKeyAware;
import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisation;

/**
 * Représente une Annexe associée à une demande {@link DemandeAutorisation} <br>
 * Une annexe est une pièce jointe (un document) avec des données complémentaires
 */
@Entity
public class Annexe extends EntityFunctionalKeyAware {

    // ********************************************************* Static Fields

    // ********************************************************* Fields
    private TypeAnnexe typeAnnexe;

    private ContenuAnnexe contenu;

    private NomFichier nomFichier;

    private DateCreation dateCreation;

    // ********************************************************* Constructor

    //Only here for OpenJPA
    public Annexe() {
    }

    public Annexe(TypeAnnexe typeAnnexe, String nomFichier, byte[] contenu, String dateCreation) {
        this(typeAnnexe, new NomFichier(nomFichier), new ContenuAnnexe(contenu),
                new DateCreation(LocalDate.parse(dateCreation, DateTimeFormat.forPattern("dd.MM.yyyy hh:mm"))));
    }

    public Annexe(TypeAnnexe typeAnnexe, NomFichier nomFichier, ContenuAnnexe contenu, DateCreation dateCreation) {
        super();
        this.typeAnnexe = typeAnnexe;
        this.nomFichier = nomFichier;
        this.contenu = contenu;
        this.dateCreation = dateCreation;
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

    public DateCreation getDateCreation() {
        return dateCreation;
    }

    public AnnexeMetadata getAnnexeMetadata() {
        return new AnnexeMetadata(typeAnnexe, nomFichier.getNomFichier(), getTaille());
    }

    @Override
    public AnnexeFK getFunctionalKey() {
        return new AnnexeFK(this);
    }

    // ********************************************************* Technical methods

    // ********************************************************* Private Methods
}
