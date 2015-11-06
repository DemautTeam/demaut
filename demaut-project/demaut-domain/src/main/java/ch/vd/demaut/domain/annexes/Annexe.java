package ch.vd.demaut.domain.annexes;

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
    private ContenuAnnexe contenu;

    private NomFichier nomFichier;

    // ********************************************************* Constructor

    //Only here for OpenJPA
    public Annexe() {
    }

    public Annexe(NomFichier nomFichier, ContenuAnnexe contenu) {
        super();
        this.nomFichier = nomFichier;
        this.contenu = contenu;
    }

    // ********************************************************* Getters

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
        return new AnnexeMetadata(nomFichier.getNomFichier(), getTaille());
    }

    @Override
    public AnnexeFK getFunctionalKey() {
        return new AnnexeFK(this);
    }

    // ********************************************************* Technical methods

    // ********************************************************* Private Methods
}
