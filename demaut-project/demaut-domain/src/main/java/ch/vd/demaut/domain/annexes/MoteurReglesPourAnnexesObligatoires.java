package ch.vd.demaut.domain.annexes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 
 * Moteur de règle implémenté en Java qui permet de déterminer la liste des
 * annexes obligatoires en fonction d'un contexte donné
 */
public class MoteurReglesPourAnnexesObligatoires {

    // ********************************************************* Methode metier

    static public List<TypeAnnexe> calculerTypesAnnexeObligatoires(CriteresAnnexeObligatoire contexte) {
        List<TypeAnnexe> typesAnnexe = new ArrayList<TypeAnnexe>();
        for (TypeAnnexe typeAnnexe : TypeAnnexe.values()) {
            if (contexte.estObligatoire(typeAnnexe)) {
                typesAnnexe.add(typeAnnexe);
            }
        }
        Collections.sort(typesAnnexe);
        return typesAnnexe;
    }

    // ********************************************************* Getters
}
