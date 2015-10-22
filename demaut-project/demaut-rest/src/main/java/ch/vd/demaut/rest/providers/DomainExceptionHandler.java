package ch.vd.demaut.rest.providers;

import ch.vd.demaut.commons.exceptions.DomainException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Classe interceptant les Exceptions métier pour les convertir en message JSON
 * Les messages JSON sont de la forme suivante :
 * <ul>
 *     <li>
 *         {"errorType":"DemandeBrouillonExisteDejaException","message":"Erreur du domaine métier, message non defini..."}
 *     </li>
 * </ul>
 */
@Provider
public class DomainExceptionHandler implements ExceptionMapper<DomainException> {
    private final Logger logger = LoggerFactory.getLogger(getClass());


    /**
     * Map an exception to a {@link Response}. Returning
     * {@code null} results in a {@link Response.Status#NO_CONTENT}
     * response. Throwing a runtime exception results in a
     * {@link Response.Status#INTERNAL_SERVER_ERROR} response.
     *
     * @param exception the exception to map to a response.
     * @return a response mapped from the supplied exception.
     */
    @Override
    public Response toResponse(DomainException exception) {
        logger.info("Interception de l'exception : {}", exception.getClass().getName());
        logger.warn("Trace de l'exception",exception);
        Response.ResponseBuilder responseBuilder = Response.status(Response.Status.EXPECTATION_FAILED);
        responseBuilder.encoding("UTF8")
                .type(MediaType.APPLICATION_JSON)
                .entity(String.format("{\"errorType\":\"%s\",\"message\":\"%s\"}",
                        exception.getClass().getSimpleName(), exception.getMessage()));
        return responseBuilder.build();
    }
}
