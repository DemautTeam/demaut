package ch.vd.demaut.microbiz.rest;

import ch.vd.demaut.microbiz.json.converters.TypeProgresJsonSerializer;
import ch.vd.pee.microbiz.core.utils.Json;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.camel.Message;
import org.apache.cxf.rs.security.cors.CorsHeaderConstants;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import java.util.Collection;

public final class RestUtils {

    private RestUtils() {
    }

    // TODO à supprimer dès que le DEV peut passer sur Microbiz
    public static void forgeExchangeHeaders(Message exchangeOut) {
        exchangeOut.setHeader(CorsHeaderConstants.HEADER_AC_ALLOW_METHODS, "GET POST PUT");
        exchangeOut.setHeader(CorsHeaderConstants.HEADER_AC_ALLOW_CREDENTIALS, "false");
        exchangeOut.setHeader(CorsHeaderConstants.HEADER_AC_ALLOW_ORIGIN, "*");
    }

    public static Response forgeResponseList(Collection<?> objects) throws JsonProcessingException {
        return forgeResponseAsString(objects);
    }

    public static Response forgeResponseString(String object) throws JsonProcessingException {
        return forgeResponseAsString(object);
    }

    public static Response forgeResponseObject(Object object) throws JsonProcessingException {
        return forgeResponseAsString(object);
    }

    public static Response forgeResponseTrue() throws JsonProcessingException {
        return forgeResponseAsString(Boolean.TRUE);
    }

    public static Response forgeResponseStream(byte[] object) throws JsonProcessingException {
        ResponseBuilder responseBuilder = getResponseBuildeOK();
        return buildHeaders(responseBuilder).entity(object).build();
    }

    public static Response forgeResponseNoContent() throws JsonProcessingException {
        ResponseBuilder responseBuilder = Response.status(Response.Status.NO_CONTENT.getStatusCode());
        return buildHeaders(responseBuilder).build();
    }

    //////////////////////// Private Methods

    private static ResponseBuilder getResponseBuildeOK() {
        return Response.status(Response.Status.OK);
    }

    private static Response forgeResponseAsString(Object value) throws JsonProcessingException {

        ObjectNode json = buildJSon(value);

        ResponseBuilder responseBuilder = getResponseBuildeOK();

        Response response = buildHeaders(responseBuilder).entity(json).build();

        return response;
    }

    private static ObjectNode buildJSon(Object value) throws JsonProcessingException {
        ObjectMapper objMapper = buildJSonObjectMapper();
        ObjectWriter writer = objMapper.writer();
        String jsonStr = writer.writeValueAsString(value);
        return Json.newObject().put("response", jsonStr);
    }

    public static ObjectMapper buildJSonObjectMapper() {
        ObjectMapper objMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule("EnumModule");
        module.addSerializer(new TypeProgresJsonSerializer());
        objMapper.registerModule(module);

        return objMapper;
    }

    // TODO à supprimer dès que le DEV peut passer sur Microbiz
    private static ResponseBuilder buildHeaders(ResponseBuilder responseBuilder) {
        return responseBuilder.header(CorsHeaderConstants.HEADER_AC_ALLOW_METHODS, "GET POST PUT")
                .header(CorsHeaderConstants.HEADER_AC_ALLOW_CREDENTIALS, "false")
                .header(CorsHeaderConstants.HEADER_AC_ALLOW_ORIGIN, "*");
    }
}
