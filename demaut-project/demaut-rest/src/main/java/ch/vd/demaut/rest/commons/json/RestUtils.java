package ch.vd.demaut.rest.commons.json;

import java.util.Collection;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import org.apache.cxf.common.util.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.ObjectNode;

import ch.vd.demaut.domain.exception.UtilisateurNotFoundException;
import ch.vd.demaut.rest.json.converters.TypeProgresJsonSerializer;

public final class RestUtils {

    private static final String DEMAUT_USER_ID_HEADER = "demaut-user-id";
    private static final String IAM_USER_ID_HEADER = "iam-user-id";

    private static final ObjectWriter viewWriter = new ObjectMapper().writer();

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

    public static String fetchCurrentUserToken(HttpHeaders httpHeaders) {
        String userToken = httpHeaders.getHeaderString(DEMAUT_USER_ID_HEADER);
        if (StringUtils.isEmpty(userToken)) {
            userToken = httpHeaders.getHeaderString(IAM_USER_ID_HEADER);
            if (StringUtils.isEmpty(userToken)) {
                throw new UtilisateurNotFoundException();
            }
        }
        return userToken;
    }
}
