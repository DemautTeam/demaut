package ch.vd.demaut.domain.demandeur.donneesProf.diplome;

import javax.validation.Valid;

import ch.vd.demaut.commons.annotations.Entity;
import ch.vd.demaut.commons.entities.EntityFunctionalKeyAware;
import ch.vd.demaut.domain.demandeur.Pays;

/** 
 * Classe qui représente un diplome du demandeur
 *
 */
@Entity
public class Diplome extends EntityFunctionalKeyAware {

    // ********************************************************* Fields

    private ReferenceDeDiplome referenceDeDiplome;

    @Valid
    private TypeDiplomeAccepte typeDiplomeAccepte;

    @Valid
    private TitreFormation titreFormation;

    private String complement;

    @Valid
    private DateObtention dateObtention;

    @Valid
    private Pays paysObtention;

    @Valid
    private DateReconnaissance dateReconnaissance;

    // ********************************************************* Constructors
    //Only here for OpenJPA
    public Diplome() {
    }

    public Diplome(ReferenceDeDiplome referenceDeDiplome, TypeDiplomeAccepte typeDiplomeAccepte, TitreFormation titreFormation,
                   String complement, DateObtention dateObtention, Pays paysObtention, DateReconnaissance dateReconnaissance) {
        this.referenceDeDiplome = referenceDeDiplome;
        this.typeDiplomeAccepte = typeDiplomeAccepte;
        this.titreFormation = titreFormation;
        this.complement = complement;
        this.dateObtention = dateObtention;
        this.paysObtention = paysObtention;
        this.dateReconnaissance = dateReconnaissance;
    }

    // ********************************************************* Methodes metier
    
    boolean estEtranger() {
        return paysObtention.estEtranger();
    }

    // ********************************************************* Getters

    public ReferenceDeDiplome getReferenceDeDiplome() {
        return referenceDeDiplome;
    }

    public TypeDiplomeAccepte getTypeDiplomeAccepte() {
        return typeDiplomeAccepte;
    }

    public TitreFormation getTitreFormation() {
        return titreFormation;
    }

    public String getComplement() {
        return complement;
    }

    public DateObtention getDateObtention() {
        return dateObtention;
    }

    public Pays getPaysObtention() {
        return paysObtention;
    }

    public DateReconnaissance getDateReconnaissance() {
        return dateReconnaissance;
    }

    // ********************************************************* Methods techniques
    @Override
    public DiplomeFK getFunctionalKey() {
        return new DiplomeFK(this); 
    }
}
