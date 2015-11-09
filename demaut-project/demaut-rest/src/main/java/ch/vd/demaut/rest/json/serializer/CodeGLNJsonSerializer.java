package ch.vd.demaut.rest.json.serializer;

import com.fasterxml.jackson.databind.JsonSerializer;

import ch.vd.demaut.domain.demandeur.donneesProf.CodeGLN;

/**
 * {@link JsonSerializer} pour le {@link CodeGLN}
 * TODO: Utiliser un serializer generic pour le StringVO (lorsque la persistence le permettra)
 * 
 */
public class CodeGLNJsonSerializer extends StringVOJsonSerializer<CodeGLN> {
}
