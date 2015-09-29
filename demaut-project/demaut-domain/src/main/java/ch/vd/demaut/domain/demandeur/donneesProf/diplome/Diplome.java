package ch.vd.demaut.domain.demandeur.donneesProf.diplome;

import ch.vd.demaut.commons.vo.BaseValueObjectWithId;

import javax.validation.Valid;

public class Diplome extends BaseValueObjectWithId {

    private ReferenceDeDiplome referenceDeDiplome;

    @Valid
    private TypeDiplomeAccepte typeDiplomeAccepte;

    @Valid
    private TitreFormation titreFormation;

    @Valid
    private DateObtention dateObtention;

    @Valid
    private PaysObtention paysObtention;

    @Valid
    private DateReconnaissance dateReconnaissance;

    //Only here for OpenJPA
    public Diplome() {
    }

    public Diplome(ReferenceDeDiplome referenceDeDiplome, TypeDiplomeAccepte typeDiplomeAccepte, TitreFormation titreFormation,
                   DateObtention dateObtention, PaysObtention paysObtention, DateReconnaissance dateReconnaissance) {
        this.referenceDeDiplome = referenceDeDiplome;
        this.typeDiplomeAccepte = typeDiplomeAccepte;
        this.titreFormation = titreFormation;
        this.dateObtention = dateObtention;
        this.paysObtention = paysObtention;
        this.dateReconnaissance = dateReconnaissance;
    }

    public ReferenceDeDiplome getReferenceDeDiplome() {
        return referenceDeDiplome;
    }

    public TypeDiplomeAccepte getTypeDiplomeAccepte() {
        return typeDiplomeAccepte;
    }

    public TitreFormation getTitreFormation() {
        return titreFormation;
    }

    public DateObtention getDateObtention() {
        return dateObtention;
    }

    public PaysObtention getPaysObtention() {
        return paysObtention;
    }

    public DateReconnaissance getDateReconnaissance() {
        return dateReconnaissance;
    }
}
