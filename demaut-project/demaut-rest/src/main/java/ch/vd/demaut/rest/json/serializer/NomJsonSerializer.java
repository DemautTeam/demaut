package ch.vd.demaut.rest.json.serializer;

import ch.vd.demaut.domain.demandeur.donneesPerso.Nom;
import ch.vd.demaut.domain.demandeur.donneesProf.CodeGLN;
import com.fasterxml.jackson.databind.JsonSerializer;

/**
 * {@link JsonSerializer} pour le {@link Nom}
 *
 */
public class NomJsonSerializer extends StringVOJsonSerializer<Nom> {

}
