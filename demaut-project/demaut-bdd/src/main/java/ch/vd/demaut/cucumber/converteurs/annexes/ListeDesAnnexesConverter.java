package ch.vd.demaut.cucumber.converteurs.annexes;

import org.springframework.util.StringUtils;

import ch.vd.demaut.domain.annexes.Annexe;
import ch.vd.demaut.domain.annexes.ContenuAnnexe;
import ch.vd.demaut.domain.annexes.ListeDesAnnexes;
import ch.vd.demaut.domain.annexes.NomFichier;
import cucumber.api.Transformer;

public class ListeDesAnnexesConverter extends Transformer<ListeDesAnnexes> {

    @Override
    public ListeDesAnnexes transform(String nomFichiersStr) {
        String[] nomFichiers = nomFichiersStr.split(",");
        ListeDesAnnexes listeDesAnnexes = new ListeDesAnnexes();
        for (String nomFichierStr : nomFichiers) {
            if (!StringUtils.isEmpty(nomFichierStr)) {
                Annexe annexe = new Annexe(new NomFichier(nomFichierStr), new ContenuAnnexe(new byte[1]));
                listeDesAnnexes.ajouterAnnexe(annexe);
            }
        }
        return listeDesAnnexes;
    }
}
