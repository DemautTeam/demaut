package ch.vd.demaut.rest.json.commons;

import javax.ws.rs.core.Response;

import ch.vd.demaut.domain.demandeur.donneesProf.activites.envisagee.DatePrevueDebut;
import ch.vd.demaut.rest.json.serializer.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.ObjectNode;

import ch.vd.demaut.commons.exceptions.TechnicalException;

public final class RestUtils {

    public static Response BuildStream(byte[] object) {
        return Response.ok().entity(object).build();
    }

    public static Response buildJSonResponse(Object object) {

        String jsonStr = buildJSonString(object);
        
        ObjectNode json = Json.newObject().put("response", jsonStr);

        return Response.ok().entity(json).build();
    }
    
    public static String buildJSonString(Object object) {
        ObjectWriter writer = buildJSonObjectWriter();
        String jsonStr;
        try {
            jsonStr = writer.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new TechnicalException("Cannot serialize JSon object", e);
        }
        return jsonStr;
    }
    
    private static ObjectWriter buildJSonObjectWriter() {
        ObjectMapper objMapper = buildJSonObjectMapper();
        return objMapper.writer();
    }

    private static ObjectMapper buildJSonObjectMapper() {
        ObjectMapper objMapper = new ObjectMapper();
        objMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        SimpleModule module = new SimpleModule("EnumModule");
        module.addSerializer(new TypeProgresJsonSerializer());
        module.addSerializer(new CodeGLNJsonSerializer());
        module.addSerializer(new NomJsonSerializer());
        module.addSerializer(new VoieJsonSerializer());
        module.addSerializer(new LocaliteJsonSerializer());
        module.addSerializer(new NPAProfessionnelJsonSerializer());
        module.addSerializer(new TelephoneJsonSerializer());
        module.addSerializer(new EmailJsonSerializer());
        module.addSerializer(new SiteInternetJsonSerializer());
        module.addSerializer(new NombreJourSemaineJsonSerializer());
        module.addSerializer(new DatePrevueDebutJsonSerializer());
        module.addSerializer(new SuperviseurJsonSerializer());

        module.addSerializer(new DateDeCreationJsonSerializer());
        module.addSerializer(new LocalDateJsonSerializer());
        module.addSerializer(new OrdreVOJsonSerializer());


        objMapper.registerModule(module);

        return objMapper;
    }
    
}
