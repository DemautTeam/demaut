package ch.vd.demaut.domain.demandes.donneesProf.activites;

import java.util.Set;

import javax.validation.ConstraintViolation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    // ********************************************************* Static Fields
    private static final Logger LOGGER = LoggerFactory.getLogger(ActiviteFutureValidateur.class);

    // ********************************************************* Business methodes
    @Override
    public void valider(ActiviteFuture data) throws DomainException {

        LOGGER.debug("Validation ActiviteFuture");
        
        validerStructure(data);

    }

    public void validerStructure(ActiviteFuture activiteFuture) {

        Set<ConstraintViolation<ActiviteFuture>> constraintViolationsResult = validateData(activiteFuture);

        throwExceptionSiNonValide(activiteFuture, constraintViolationsResult);
    }

    // ********************************************************* Private methodes
    private void throwExceptionSiNonValide(ActiviteFuture activiteFuture,
            Set<ConstraintViolation<ActiviteFuture>> constraintViolationsResult) {
        if (constraintViolationsResult.size() > 0) {
            LOGGER.info("ActiviteFuture non valide car:" + constraintViolationsResult);
            throw new ActiviteFutureNonValideException();
        }

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("ActiviteFuture valide");
        }
    }
}
