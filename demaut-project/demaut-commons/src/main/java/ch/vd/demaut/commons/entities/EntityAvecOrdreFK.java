package ch.vd.demaut.commons.entities;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import ch.vd.demaut.commons.vo.OrdreVO;

/**
 * Entite dont la clé functionnel est une {@link OrdreVO} au sein d'une liste
 *
 */
abstract public class EntityAvecOrdreFK extends EntityFunctionalKeyAware {

    // *********************************************** Fields
    private OrdreVO ordre;
    
    // *********************************************** Constructeurs
    //Used only for JPA
    protected EntityAvecOrdreFK() {
        super();
    }
    
    protected EntityAvecOrdreFK(OrdreVO ordre) {
        this.ordre = ordre;
    }
    
    // *********************************************** Methodes metier
    /**
     * 
     * Assigne l'ordre de l' {@link ActiviteFuture} au sein d'une {@link ListeDesActivitesFutures} <br>
     * 
     */
    public void genererOrdre(ListeDesEntitesOrdonnees<? extends EntityAvecOrdreFK> listeEntites) {
        OrdreVO nouvelOrdre = listeEntites.genererNouvelOrdre();
        this.ordre = nouvelOrdre;
    }
    
    
    // *********************************************** Getters et contraintes
    @NotNull
    @Valid
    public OrdreVO getOrdre() {
        return ordre;
    }
}
