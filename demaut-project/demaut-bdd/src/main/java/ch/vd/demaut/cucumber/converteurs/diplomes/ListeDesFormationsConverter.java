package ch.vd.demaut.cucumber.converteurs.diplomes;

import ch.vd.demaut.domain.demandeur.donneesProf.diplome.ListeDesFormations;
import ch.vd.demaut.domain.demandeur.donneesProf.diplome.TitreFormation;
import cucumber.api.Transformer;
import org.springframework.util.StringUtils;

import java.util.ArrayList;

public class ListeDesFormationsConverter extends Transformer<ListeDesFormations> {

    @Override
    public ListeDesFormations transform(String formationsStr) {
        String[] formations = formationsStr.split(",");
        ListeDesFormations listeDesFormations = new ListeDesFormations(new ArrayList<TitreFormation>());
        for (String formationStr : formations) {
            if (!StringUtils.isEmpty(formationStr)) {
                TitreFormation titreFormation = new TitreFormation(formationStr);
                listeDesFormations.ajouterTitreFormation(titreFormation);
            }
        }
        return listeDesFormations;
    }
}
