package ch.vd.demaut.domain.demandeur.donneesProf.activites.envisagee;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import ch.vd.demaut.commons.annotations.ValueObject;
import ch.vd.demaut.commons.vo.BaseValueObject;
import ch.vd.demaut.domain.demandeur.donneesProf.activites.TypeActivite;

/**
 * L'activité professionelle envisagée pour l'établissement saisi
 */
//TODO : Créer un validateur qui vérifie que superviseur valide si typeActivite = dépendant
@ValueObject
public class ActiviteEnvisagee extends BaseValueObject {

    // ********************************************************* Fields
    private TypeActivite typeActivite;
    
    private TauxActiviteEnDemiJournee nombreJourParSemaine;

    private DatePrevueDebut datePrevueDebut;

    private Superviseur superviseur;


    // ********************************************************* Constructor

    public ActiviteEnvisagee(TypeActivite typeActivite, TauxActiviteEnDemiJournee nombreJourParSemaine,
            DatePrevueDebut datePrevueDebut, Superviseur superviseur) {
        super();
        this.typeActivite = typeActivite;
        this.nombreJourParSemaine = nombreJourParSemaine;
        this.datePrevueDebut = datePrevueDebut;
        this.superviseur = superviseur;
    }

    // ********************************************************* Getters
    
    @NotNull
    @Valid
    public TypeActivite getTypeActivite() {
        return typeActivite;
    }
    
    @NotNull
    @Valid
    public TauxActiviteEnDemiJournee getNombreJourParSemaine() {
        return nombreJourParSemaine;
    }
    
    @NotNull
    @Valid
    public DatePrevueDebut getDatePrevueDebut() {
        return datePrevueDebut;
    }
    
    @Valid
    public Superviseur getSuperviseur() {
        return superviseur;
    }
}
