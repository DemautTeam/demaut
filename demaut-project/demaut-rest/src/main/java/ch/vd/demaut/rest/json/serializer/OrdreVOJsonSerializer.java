package ch.vd.demaut.rest.json.serializer;

import ch.vd.demaut.commons.vo.IntegerVOInterface;
import ch.vd.demaut.commons.vo.OrdreVO;
import ch.vd.demaut.commons.vo.StringVOInterface;
import ch.vd.demaut.domain.demandeur.donneesProf.CodeGLN;
import ch.vd.demaut.rest.json.commons.BaseJsonSerializer;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * {@link JsonSerializer} pour le {@link OrdreVO}
 *
 */
public class OrdreVOJsonSerializer extends IntegerVOJsonSerializer<OrdreVO> {
}
