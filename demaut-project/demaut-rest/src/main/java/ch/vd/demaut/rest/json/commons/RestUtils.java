package ch.vd.demaut.rest.json.commons;

import ch.vd.demaut.rest.json.serializer.TypeProgresJsonSerializer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.ObjectNode;

import javax.ws.rs.core.Response;
import java.util.Collection;

public final class RestUtils {

    private static final ObjectWriter viewWriter = new ObjectMapper().configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false).writer();

    private RestUtils() {
    }

    public static Response BuildStream(byte[] object) throws JsonProcessingException {
        return Response.ok()
                .entity(object)
                .build();
    }

    public static Response buildJSon(Collection<?> objects) throws JsonProcessingException {
        return Response.ok()
                .entity(Json.newObject().put("response", viewWriter.writeValueAsString(objects)))
                .build();
    }

    public static Response buildJSon(Object object) throws JsonProcessingException {
        return Response.ok()
                .entity(Json.newObject().put("response", viewWriter.writeValueAsString(object)))
                .build();
    }

    public static Response buildRef(Collection<?> objects) throws JsonProcessingException {
        ObjectMapper objMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule("EnumModule");
        module.addSerializer(new TypeProgresJsonSerializer());
        objMapper.registerModule(module);
        ObjectWriter writer = objMapper.writer();
        String jsonStr = writer.writeValueAsString(objects);
        ObjectNode json = Json.newObject().put("response", jsonStr);
        return Response.ok().entity(json).build();
    }

}
