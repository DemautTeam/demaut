package ch.vd.demaut.domain.demandes.donneesProf.activites;

import java.util.Set;

import javax.validation.ConstraintViolation;

import ch.vd.demaut.commons.exceptions.DomainException;
import ch.vd.demaut.commons.validation.AbstractDataValidateur;
import ch.vd.demaut.domain.demandeur.donneesProf.activites.ActiviteFuture;
import ch.vd.demaut.domain.exception.ActiviteFutureNonValideException;

/**
 * 
 * Validateur d'une {@link ActiviteFuture} <br>
 * Utilise une validateur JavaBeans Validation (Hibernate Validateur)
 *
 */
public class ActiviteFutureValidateur extends AbstractDataValidateur<ActiviteFuture> {

    @Override
    public void valider(ActiviteFuture data) throws DomainException {

        validerStructure(data);

    }

    public void validerStructure(ActiviteFuture activiteFuture) {
        Set<ConstraintViolation<ActiviteFuture>> constraintViolationsResult = validateData(activiteFuture);

        if (constraintViolationsResult.size() > 0) {
            throw new ActiviteFutureNonValideException();
        }
    }

}
