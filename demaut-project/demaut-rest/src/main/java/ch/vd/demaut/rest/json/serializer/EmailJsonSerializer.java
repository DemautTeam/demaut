package ch.vd.demaut.rest.json.serializer;

import ch.vd.demaut.domain.demandeur.Email;
import ch.vd.demaut.domain.demandeur.donneesPerso.Nom;
import com.fasterxml.jackson.databind.JsonSerializer;

/**
 * {@link JsonSerializer} pour le {@link Email}
 *
 */
public class EmailJsonSerializer extends StringVOJsonSerializer<Email> {

}
