package ch.vd.demaut.cucumber.converteurs.annexes;

import ch.vd.demaut.domain.annexes.Annexe;
import ch.vd.demaut.domain.annexes.ListeDesAnnexes;
import ch.vd.demaut.domain.annexes.TypeAnnexe;
import cucumber.api.Transformer;
import org.springframework.util.StringUtils;

import java.util.ArrayList;

public class ListeDesAnnexesConverter extends Transformer<ListeDesAnnexes> {

    @Override
    public ListeDesAnnexes transform(String nomFichiersStr) {
        String[] nomFichiers = nomFichiersStr.split(",");
        ListeDesAnnexes listeDesAnnexes = new ListeDesAnnexes(new ArrayList<Annexe>());
        for (String nomFichierStr : nomFichiers) {
            if (!StringUtils.isEmpty(nomFichierStr)) {
                //TODO : Ne pas hardcoder le type d'annexe
                Annexe annexe = new Annexe(TypeAnnexe.CV, nomFichierStr, new byte[1]);
                listeDesAnnexes.ajouterAnnexe(annexe);
            }
        }
        return listeDesAnnexes;
    }
}
