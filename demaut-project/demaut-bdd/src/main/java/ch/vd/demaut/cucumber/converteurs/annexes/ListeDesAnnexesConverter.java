package ch.vd.demaut.cucumber.converteurs.annexes;

import ch.vd.demaut.domain.annexes.Annexe;
import ch.vd.demaut.domain.annexes.ListeDesAnnexes;
import ch.vd.demaut.domain.annexes.TypeAnnexe;
import cucumber.api.Transformer;

public class ListeDesAnnexesConverter extends Transformer<ListeDesAnnexes> {

    @Override
    public ListeDesAnnexes transform(String nomFichiersStr) {
        String[] nomFichiers = nomFichiersStr.split(",");
        ListeDesAnnexes listeDesAnnexes = new ListeDesAnnexes();
        for (String nomFichierStr : nomFichiers) {
        	if (nomFichierStr.length() > 0) {
        		Annexe annexe = new Annexe(TypeAnnexe.Certificat, nomFichierStr, new byte[1]);
        		listeDesAnnexes.ajouterAnnexe(annexe);
        	}
        }
        return listeDesAnnexes;
    }
}
