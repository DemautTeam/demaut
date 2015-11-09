package ch.vd.demaut.rest.json.serializer;

import com.fasterxml.jackson.databind.JsonSerializer;

import ch.vd.demaut.domain.demandeur.donneesPerso.DateDeNaissance;

/**
 * {@link JsonSerializer} pour le {@link DateDeNaissance}
 *
 */
public class DateDeNaissanceJsonSerializer extends LocalDateVOJsonSerializer<DateDeNaissance> {
}
