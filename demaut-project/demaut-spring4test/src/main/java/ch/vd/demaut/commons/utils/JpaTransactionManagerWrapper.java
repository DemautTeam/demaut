package ch.vd.demaut.commons.utils;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * Wrapper d'un {@link PlatformTransactionManager} utilisé dans le BDD et classes de test JPA 
 *
 */
public class JpaTransactionManagerWrapper implements TransactionManagerWrapper {

    //Injected via Spring XML
    private PlatformTransactionManager platformTransactionManager;

    private TransactionStatus transaction;

    @Override
    public void startTransaction() {
        DefaultTransactionDefinition definition = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        transaction = platformTransactionManager.getTransaction(definition);
    }

    @Override
    public void commitTransaction() {
        platformTransactionManager.commit(transaction);
    }
    
    @Override
    public void rollbackTransaction() {
        platformTransactionManager.rollback(transaction);
    }

    @Override
    public TransactionStatus getTransaction() {
        return transaction;
    }

    public void setPlatformTransactionManager(PlatformTransactionManager platformTransactionManager) {
        this.platformTransactionManager = platformTransactionManager;
    }
    
}
