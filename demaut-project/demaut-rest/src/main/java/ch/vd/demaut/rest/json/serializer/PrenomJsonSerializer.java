package ch.vd.demaut.rest.json.serializer;

import com.fasterxml.jackson.databind.JsonSerializer;

import ch.vd.demaut.domain.demandeur.donneesPerso.Prenom;

/**
 * {@link JsonSerializer} pour le {@link Prenom}
 *
 */
public class PrenomJsonSerializer extends StringVOJsonSerializer<Prenom> {

}
