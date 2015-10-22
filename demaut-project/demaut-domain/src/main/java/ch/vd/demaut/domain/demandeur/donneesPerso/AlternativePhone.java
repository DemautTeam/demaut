package ch.vd.demaut.domain.demandeur.donneesPerso;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Valide que au moins un des numéro de téléphone est valide.
 */
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AlternativePhoneValidator.class)
@Documented
public @interface AlternativePhone {
    // Elements obligatoires dans les annotations de validation
    String message() default "Au moins un numéro de téléphone doit être renseigné";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    // Elements propre à l'annotation
    String prive();

    String mobile();
}
