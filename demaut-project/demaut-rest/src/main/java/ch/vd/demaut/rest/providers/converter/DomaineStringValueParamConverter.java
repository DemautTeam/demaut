package ch.vd.demaut.rest.providers.converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.ext.ParamConverter;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by mourad on 06.11.15.
 */
public class DomaineStringValueParamConverter<T> implements ParamConverter<T> {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final Class<?> objectClass;

    public DomaineStringValueParamConverter(Class<?> objectClass) {
        this.objectClass = objectClass;
    }

    /**
     * Parse the supplied value and create an instance of {@code T}.
     *
     * @param value
     *            the string value.
     * @return the newly created instance of {@code T}.
     * @throws IllegalArgumentException
     *             if the supplied string cannot be parsed or is {@code null}.
     */
    @SuppressWarnings("unchecked") //On a pas la choix ici
    @Override
    public T fromString(String value) {
        try {
            Constructor<?> constructor = objectClass.getConstructor(String.class);
            return (T) constructor.newInstance(value);
        } catch (InstantiationException | NoSuchMethodException | IllegalAccessException
                | InvocationTargetException e) {
            logger.error("Problème lors de l'instanciation de la classe {}", objectClass.getName());
            throw new IllegalArgumentException("Problème lors de l'instanciation de la classe", e);
        }
    }

    /**
     * Convert the supplied value to a String.
     * <p>
     * This method is reserved for future use. Proprietary JAX-RS extensions may leverage the method. Users should be
     * aware that any such support for the method comes at the expense of producing non-portable code.
     * </p>
     *
     * @param value
     *            the value of type {@code T}.
     * @return a String representation of the value.
     * @throws IllegalArgumentException
     *             if the supplied object cannot be serialized or is {@code null}.
     */
    @Override
    public String toString(T value) {
        try {
            Method valueMethode = objectClass.getMethod("getValue");
            return (String) valueMethode.invoke(value);
        } catch (ReflectiveOperationException e) {
            logger.error("Problème d'invocation de la methode getValue() de la classe {}", objectClass.getName());
            throw new IllegalArgumentException("Problème d'invocation de la methode getValue()", e);
        }
    }
}
