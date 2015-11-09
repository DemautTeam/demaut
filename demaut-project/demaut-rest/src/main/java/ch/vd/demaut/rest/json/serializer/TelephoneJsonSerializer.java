package ch.vd.demaut.rest.json.serializer;

import ch.vd.demaut.domain.demandeur.Telephone;
import com.fasterxml.jackson.databind.JsonSerializer;

/**
 * {@link JsonSerializer} pour le {@link Telephone}
 *
 */
public class TelephoneJsonSerializer extends StringVOJsonSerializer<Telephone> {

}
