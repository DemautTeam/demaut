package ch.vd.demaut.domain.demandeur.donneesProf.activites.envisagee;

import javax.validation.Valid;

/**
 * L'activité professionelle envisagé pour l'établissement saisi
 */
public class ActiviteEnvisagee {

    // ********************************************************* Fields

    @Valid
    private TauxActiviteEnDemiJournee nombreJourParSemaine;

    @Valid
    private DatePrevueDebut datePrevueDebut;

    private Superviseur superviseur;


    // ********************************************************* Constructor


    // ********************************************************* getters/setters

}
