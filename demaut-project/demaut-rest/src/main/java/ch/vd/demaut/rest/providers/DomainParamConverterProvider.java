package ch.vd.demaut.rest.providers;

import ch.vd.demaut.commons.fk.OrdreFK;
import ch.vd.demaut.domain.demandes.ReferenceDeDemande;
import ch.vd.demaut.domain.demandeur.donneesProf.CodeGLN;
import ch.vd.demaut.rest.providers.converter.DomaineIntegerValueParamConverter;
import ch.vd.demaut.rest.providers.converter.DomaineStringValueParamConverter;

import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;
import javax.ws.rs.ext.Provider;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * Provider permettqnt d'effectuer les conversions des parametres web dans leur réel typage.
 */
@Provider
public class DomainParamConverterProvider implements ParamConverterProvider {

    /**
     * Obtain a {@link ParamConverter} that can provide from/to string conversion
     * for an instance of a particular Java type.
     *
     * @param rawType     the raw type of the object to be converted.
     * @param genericType the type of object to be converted. E.g. if an String value
     *                    representing the injected request parameter
     *                    is to be converted into a method parameter, this will be the
     *                    formal type of the method parameter as returned by {@code Class.getGenericParameterTypes}.
     * @param annotations an array of the annotations associated with the convertible
     *                    parameter instance. E.g. if a string value is to be converted into a method parameter,
     *                    this would be the annotations on that parameter as returned by
     *                    {@link Method#getParameterAnnotations}.
     * @return the string converter, otherwise {@code null}.
     */
    @Override
    public <T> ParamConverter<T> getConverter(Class<T> rawType, Type genericType, Annotation[] annotations) {
        if(CodeGLN.class.isAssignableFrom(rawType) || ReferenceDeDemande.class.isAssignableFrom(rawType)) {
            return new DomaineStringValueParamConverter<T>(rawType);
        } else if(OrdreFK.class.isAssignableFrom(rawType)){
            return new DomaineIntegerValueParamConverter<T>(rawType);
        }
        return null;
    }
}
