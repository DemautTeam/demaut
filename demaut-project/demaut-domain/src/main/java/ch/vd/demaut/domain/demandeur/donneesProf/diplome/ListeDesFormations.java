package ch.vd.demaut.domain.demandeur.donneesProf.diplome;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListeDesFormations {

    // ********************************************************* Fields
    private List<TitreFormation> titreFormations;

    // ********************************************************* Constructor

    public ListeDesFormations(List<TitreFormation> titreFormations) {
        this.titreFormations = titreFormations;
    }

    // ********************************************************* Getters

    /**
     * Renvoie la liste des annexes
     */
    public List<TitreFormation> listerTitreFormations() {
        if (titreFormations == null) {
            return Collections.unmodifiableList(new ArrayList<TitreFormation>());
        }
        return Collections.unmodifiableList(titreFormations);
    }

    public void ajouterTitreFormation(TitreFormation titreFormation) {
        this.titreFormations.add(titreFormation);
    }
}
