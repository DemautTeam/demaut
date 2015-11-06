package ch.vd.demaut.cucumber.steps;

import org.joda.time.DateTime;

import ch.vd.demaut.commons.utils.TransactionManagerWrapper;
import ch.vd.demaut.cucumber.DateEtHeureCourant;

/**
 * Steps pour les contextes
 *
 */
public class BackgroundSteps {

    // ********************************************************* Injected Fields
    private DateEtHeureCourant dateHeureCourant;

    private TransactionManagerWrapper transactionManagerWrapper;

    // ********************************************************* Methods business
    public void startTransaction() {
        transactionManagerWrapper.startTransaction();
    }
    
    public void rollbackTransaction() {
        transactionManagerWrapper.rollbackTransaction();
    }
    
    public void initialiserDateDuJour(DateTime dateHeureCourant) {
        this.dateHeureCourant = new DateEtHeureCourant(dateHeureCourant);
    }
    
    public DateEtHeureCourant getDateHeureCourant() {
        return dateHeureCourant;
    } 
    
    // ********************************************************* Setters for Spring
    public void setTransactionManagerWrapper(TransactionManagerWrapper transactionManagerWrapper) {
        this.transactionManagerWrapper = transactionManagerWrapper;
    }

}
