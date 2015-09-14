package ch.vd.demaut.microbiz.rest;

import ch.vd.pee.microbiz.core.utils.Json;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.apache.cxf.rs.security.cors.CorsHeaderConstants;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import java.util.Collection;

public final class RestUtils {

    private static final ObjectWriter viewWriter = new ObjectMapper().writer();

    private RestUtils() {
    }

    public static Response forgeResponseList(Response.Status status, Collection<?> objects) throws JsonProcessingException {
        ResponseBuilder responseBuilder = Response.status(status.getStatusCode());
        return buildHeaders(responseBuilder)
                .entity(Json.newObject().put("response", viewWriter.writeValueAsString(objects)))
                .status(status.getStatusCode())
                .build();
    }

    public static Response forgeResponseString(Response.Status status, String object) throws JsonProcessingException {
        ResponseBuilder responseBuilder = Response.status(status.getStatusCode());
        return buildHeaders(responseBuilder)
                .entity(Json.newObject().put("response", viewWriter.writeValueAsString(object)))
                .status(status.getStatusCode())
                .build();
    }

    public static Response forgeResponseStream(Response.Status status, byte[] object) throws JsonProcessingException {
        ResponseBuilder responseBuilder = Response.status(status.getStatusCode());
        return buildHeaders(responseBuilder)
                .entity(object)
                .status(status.getStatusCode())
                .build();
    }

    public static Response forgeResponseNoContent() throws JsonProcessingException {
        ResponseBuilder responseBuilder = Response.status(Response.Status.NO_CONTENT.getStatusCode());
        return buildHeaders(responseBuilder)
                .status(Response.Status.NO_CONTENT.getStatusCode())
                .build();
    }

    private static ResponseBuilder buildHeaders(ResponseBuilder responseBuilder) {
        // TODO
        return responseBuilder
                .header(CorsHeaderConstants.HEADER_AC_ALLOW_METHODS, "GET POST PUT")
                .header(CorsHeaderConstants.HEADER_AC_ALLOW_CREDENTIALS, "false")
                .header(CorsHeaderConstants.HEADER_AC_ALLOW_ORIGIN, "*");
    }
}
