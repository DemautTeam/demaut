package ch.vd.demaut.domain.demandeur.donneesProf.diplome;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListeDesDiplomes {

    // ********************************************************* Fields
    private List<Diplome> diplomes;

    // ********************************************************* Constructor

    public ListeDesDiplomes(List<Diplome> diplomes) {
        this.diplomes = diplomes;
    }

    // ********************************************************* Getters

    /**
     * Renvoie la liste des annexes
     */
    public List<Diplome> listerDiplomes() {
        if (diplomes == null) {
            return Collections.unmodifiableList(new ArrayList<Diplome>());
        }
        return Collections.unmodifiableList(diplomes);
    }

    public void ajouterDiplome(Diplome diplome) {
        this.diplomes.add(diplome);
    }
}
