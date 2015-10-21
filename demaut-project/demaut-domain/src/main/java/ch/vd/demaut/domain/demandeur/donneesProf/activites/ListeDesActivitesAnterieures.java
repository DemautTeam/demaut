package ch.vd.demaut.domain.demandeur.donneesProf.activites;

import java.util.List;

import ch.vd.demaut.commons.entities.EntityFKAList;

/** 
 * Liste des activites anterieures {@link ActiviteAnterieure}
 *
 */
public class ListeDesActivitesAnterieures  extends EntityFKAList<ActiviteAnterieure> {


    // ********************************************************* Constructor

    public ListeDesActivitesAnterieures() {
        super();
    }

    public ListeDesActivitesAnterieures(List<ActiviteAnterieure> activites) {
        super(activites);
    }

    // ********************************************************* Business
    // methods

    public ReferenceActiviteAnterieure genererNouvelleReference() {
        ReferenceActiviteAnterieure refMax = new ReferenceActiviteAnterieure(0);
        for (ActiviteAnterieure activite : listerActivitesAnterieures()) {
            if (refMax.compareTo(activite.getReferenceActiviteAnterieure()) < 0) {
                refMax = activite.getReferenceActiviteAnterieure();
            }
        }
        return refMax;
    }
    
    
    /**
     * Ajoute une activite anterieure à la liste des activites<br>
     * Renvoie une exception si l'activite n'est pas unique
     *
     * @param annexe Annexe
     */
    public void ajouterActivite(ActiviteAnterieure activite) {
        ajouterEntity(activite);
    }

    /**
     * Supprimer une activite anterieure. <br>
     * Renvoie exception si activite pas trouvée
     *
     * @param annexeFK
     */
    public void supprimerUneActivite(ActiviteAnterieureFK activiteFK) {
        supprimerEntity(activiteFK);
    }

    /**
     * Renvoie la liste des activites anterieures
     */
    public List<ActiviteAnterieure> listerActivitesAnterieures() {
        return listerEntities();
    }

}
