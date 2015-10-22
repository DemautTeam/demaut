package ch.vd.demaut.domain.demandeur.donneesPerso;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by mourad on 16.10.15.
 */
public class AlternativePhoneValidator implements ConstraintValidator<AlternativePhone, Object> {

    private final Logger logger = LoggerFactory.getLogger(AlternativePhoneValidator.class);

    private String priveField;

    private String mobileField;

    /**
     * Initializes the validator in preparation for
     * {@link #isValid(Object, ConstraintValidatorContext)} calls.
     * The constraint annotation for a given constraint declaration
     * is passed.
     * <p/>
     * This method is guaranteed to be called before any use of this instance for
     * validation.
     *
     * @param constraintAnnotation annotation instance for a given constraint declaration
     */
    @Override
    public void initialize(AlternativePhone constraintAnnotation) {
        priveField = constraintAnnotation.prive();
        mobileField = constraintAnnotation.mobile();
    }

    /**
     * Implements the validation logic.
     * The state of {@code value} must not be altered.
     * <p/>
     * This method can be accessed concurrently, thread-safety must be ensured
     * by the implementation.
     *
     * @param value   object to validate
     * @param context context in which the constraint is evaluated
     * @return {@code false} if {@code value} does not pass the constraint
     */
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        try {
            TelephonePrive telephonePrive = (TelephonePrive) PropertyUtils.getProperty(value, priveField);
            TelephoneMobile telephoneMobile = (TelephoneMobile) PropertyUtils.getProperty(value, mobileField);
            if (telephonePrive == null || StringUtils.isEmpty(telephonePrive.getValue())) {
                if (telephoneMobile == null || StringUtils.isEmpty(telephoneMobile.getValue())) {
                    logger.info("les 2 numeros sont vides -> ko");
                    return false;
                }
            }
            logger.info("au moins 1 numéro est rempli -> ok");
            return true;

        } catch (Exception e) {

        }
        return false;
    }
}
