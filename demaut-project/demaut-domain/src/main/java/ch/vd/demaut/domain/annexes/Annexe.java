package ch.vd.demaut.domain.annexes;

import ch.vd.demaut.commons.annotations.Entity;
import ch.vd.demaut.commons.entities.EntityFunctionalKeyAware;
import ch.vd.demaut.domain.demandes.DateDeCreation;
import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisation;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;

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

    private DateDeCreation dateDeCreation;

    // ********************************************************* Constructor

    //Only here for OpenJPA
    public Annexe() {
    }

    public Annexe(TypeAnnexe typeAnnexe, String nomFichier, byte[] contenu, String dateCreation) {
        this(typeAnnexe, new NomFichier(nomFichier), new ContenuAnnexe(contenu),
                new DateDeCreation(LocalDate.parse(dateCreation, DateTimeFormat.forPattern("dd.MM.yyyy hh:mm"))));
    }

    public Annexe(TypeAnnexe typeAnnexe, NomFichier nomFichier, ContenuAnnexe contenu, DateDeCreation dateDeCreation) {
        super();
        this.typeAnnexe = typeAnnexe;
        this.nomFichier = nomFichier;
        this.contenu = contenu;
        this.dateDeCreation = dateDeCreation;
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

    public DateDeCreation getDateDeCreation() {
        return dateDeCreation;
    }

    public AnnexeMetadata getAnnexeMetadata() {
        return new AnnexeMetadata(typeAnnexe, nomFichier.getNomFichier(), getTaille(), dateDeCreation);
    }

    @Override
    public AnnexeFK getFunctionalKey() {
        return new AnnexeFK(this);
    }

    // ********************************************************* Technical methods

    // ********************************************************* Private Methods
}
