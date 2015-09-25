package ch.vd.demaut.domain.demandeur.donneesProf.diplome;

import ch.vd.demaut.commons.vo.BaseValueObjectWithId;

import javax.validation.Valid;

public class Diplome extends BaseValueObjectWithId {

    @Valid
    final private TypeDiplomeAccepte typeDiplomeAccepte;

    @Valid
    final private TitreFormation titreFormation;

    @Valid
    final private DateObtention dateObtention;

    @Valid
    final private PaysObtention paysObtention;

    @Valid
    final private DateReconnaissance dateReconnaissance;

    public Diplome(TypeDiplomeAccepte typeDiplomeAccepte, TitreFormation titreFormation, DateObtention dateObtention, PaysObtention paysObtention, DateReconnaissance dateReconnaissance) {
        this.typeDiplomeAccepte = typeDiplomeAccepte;
        this.titreFormation = titreFormation;
        this.dateObtention = dateObtention;
        this.paysObtention = paysObtention;
        this.dateReconnaissance = dateReconnaissance;
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
