package ch.vd.demaut.rest.json.serializer;

import com.fasterxml.jackson.databind.JsonSerializer;

import ch.vd.demaut.domain.demandeur.Email;

/**
 * {@link JsonSerializer} pour le {@link Email}
 *
 */
public class EmailJsonSerializer extends StringVOJsonSerializer<Email> {

}
