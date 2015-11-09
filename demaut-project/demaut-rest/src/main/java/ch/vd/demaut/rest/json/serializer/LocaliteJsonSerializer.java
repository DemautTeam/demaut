package ch.vd.demaut.rest.json.serializer;

import ch.vd.demaut.domain.demandeur.Localite;
import ch.vd.demaut.domain.demandeur.donneesPerso.Nom;
import com.fasterxml.jackson.databind.JsonSerializer;

/**
 * {@link JsonSerializer} pour le {@link Localite}
 *
 */
public class LocaliteJsonSerializer extends StringVOJsonSerializer<Localite> {

}
