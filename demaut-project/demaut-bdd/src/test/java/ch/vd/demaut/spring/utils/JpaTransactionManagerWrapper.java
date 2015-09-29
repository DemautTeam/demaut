package ch.vd.demaut.spring.utils;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import ch.vd.demaut.commons.utils.TransactionManagerWrapper;

//TODO: rendre cette classe diponible pour tous les tests mais ne pas la mettre dans commons4test car pas de Spring injecté
public class JpaTransactionManagerWrapper implements TransactionManagerWrapper {

    //Injected via Spring XML
    private PlatformTransactionManager platformTransactionManager;
    
    private TransactionStatus transaction;
    
    @Override
    public void startTransaction() {
        DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
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

    public void setPlatformTransactionManager(PlatformTransactionManager platformTransactionManager) {
        this.platformTransactionManager = platformTransactionManager;
    }
}
