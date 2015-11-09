package ch.vd.demaut.rest.json.serializer;

import com.fasterxml.jackson.databind.JsonSerializer;

import ch.vd.demaut.domain.demandeur.donneesProf.activites.envisagee.DatePrevueDebut;

/**
 * {@link JsonSerializer} pour le {@link DatePrevueDebut}
 *
 */
public class DatePrevueDebutJsonSerializer extends LocalDateVOJsonSerializer<DatePrevueDebut> {

}
