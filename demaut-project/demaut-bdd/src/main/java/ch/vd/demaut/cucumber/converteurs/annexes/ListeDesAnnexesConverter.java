package ch.vd.demaut.cucumber.converteurs.annexes;

import org.springframework.util.StringUtils;

import ch.vd.demaut.domain.annexes.Annexe;
import ch.vd.demaut.domain.annexes.ListeDesAnnexes;
import cucumber.api.Transformer;

public class ListeDesAnnexesConverter extends Transformer<ListeDesAnnexes> {

    @Override
    public ListeDesAnnexes transform(String nomFichiersStr) {
        String[] nomFichiers = nomFichiersStr.split(",");
        ListeDesAnnexes listeDesAnnexes = new ListeDesAnnexes();
        for (String nomFichierStr : nomFichiers) {
            if (!StringUtils.isEmpty(nomFichierStr)) {
                Annexe annexe = new Annexe(nomFichierStr, new byte[1], "01.01.2015 11:00");
                listeDesAnnexes.ajouterAnnexe(annexe);
            }
        }
        return listeDesAnnexes;
    }
}
