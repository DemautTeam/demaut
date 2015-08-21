package ch.vd.demaut.domain.demandes.autorisation;

import ch.vd.demaut.domain.annexes.Annexe;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ListeDesAnnexes {

    // ********************************************************* Fields
    private List<Annexe> annexes = new ArrayList<Annexe>();

    // ********************************************************* Constructor

    // ********************************************************* Business methods
    public void ajouterAnnexe(Annexe annexe) {
        annexes.add(annexe);
    }

    // ********************************************************* Getters
    public Collection<Annexe> listerAnnexes() {
        return Collections.unmodifiableList(annexes);
    }

}
