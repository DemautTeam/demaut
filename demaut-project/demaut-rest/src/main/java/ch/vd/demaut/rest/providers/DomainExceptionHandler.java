package ch.vd.demaut.rest.providers;

import ch.vd.demaut.commons.exceptions.DomainException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Classe interceptant les Exceptions métier pour les convertir en message JSON
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
        logger.info("Interception de l'exception : {}", exception.getClass().getName() );
        Response.ResponseBuilder responseBuilder = Response.status(Response.Status.EXPECTATION_FAILED);
        responseBuilder.entity(exception.getMessage());
        return responseBuilder.build();
    }
}
