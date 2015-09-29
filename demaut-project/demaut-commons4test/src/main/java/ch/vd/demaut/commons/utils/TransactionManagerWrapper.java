package ch.vd.demaut.commons.utils;

/**
 Encpasule un TransactionManager.<br>
 Utilisé pour les tests
 **/
public interface TransactionManagerWrapper {

    void startTransaction();
    
    void commitTransaction();
    
    void rollbackTransaction();
}
