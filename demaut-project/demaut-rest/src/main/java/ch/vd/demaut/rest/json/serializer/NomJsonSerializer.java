package ch.vd.demaut.rest.json.serializer;

import com.fasterxml.jackson.databind.JsonSerializer;

import ch.vd.demaut.domain.demandeur.donneesPerso.Nom;

/**
 * {@link JsonSerializer} pour le {@link Nom}
 *
 */
public class NomJsonSerializer extends StringVOJsonSerializer<Nom> {

}
