package ch.vd.demaut.rest.json.serializer;

import ch.vd.demaut.domain.demandeur.donneesProf.activites.envisagee.TauxActiviteEnDemiJournee;
import com.fasterxml.jackson.databind.JsonSerializer;

/**
 * {@link JsonSerializer} pour le {@link TauxActiviteEnDemiJournee}
 *
 */
public class NombreJourSemaineJsonSerializer extends IntegerVOJsonSerializer<TauxActiviteEnDemiJournee> {

}
