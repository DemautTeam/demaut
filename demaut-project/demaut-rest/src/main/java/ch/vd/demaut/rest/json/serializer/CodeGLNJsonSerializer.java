package ch.vd.demaut.rest.json.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import ch.vd.demaut.domain.demandeur.donneesProf.CodeGLN;
import ch.vd.demaut.rest.json.commons.BaseJsonSerializer;

/**
 * {@link JsonSerializer} pour le {@link CodeGLN}
 * TODO: Utiliser un serializer generic pour le StringVO (lorsque la persistence le permettra)
 * 
 */
public class CodeGLNJsonSerializer extends StringVOJsonSerializer<CodeGLN> {
}
