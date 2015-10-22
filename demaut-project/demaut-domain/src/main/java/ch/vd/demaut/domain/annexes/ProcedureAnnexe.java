package ch.vd.demaut.domain.annexes;

/**
 * Représente le cas d'une procédure pour les annexes obligatoires
 */
public enum ProcedureAnnexe {
    Ordinaire, //
    Simplifiee;
    
    boolean isOrdinaire() {
        return this.equals(Ordinaire);
    }
    
    boolean iSimplifiee() {
        return this.equals(Simplifiee);
    }
}
