package ch.vd.demaut.domain.demandeur.donneesProf.diplome;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Predicate;

import ch.vd.demaut.domain.exception.DiplomeIntrouvableException;

/**
 * Liste des diplomes
 * TODO: Utiliser "extends EntityFKAList<Diplome>"
 *
 */
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

    public void ajouterUnDiplome(Diplome diplome) {
        this.diplomes.add(diplome);
    }

    public Collection<Diplome> extraireDiplomesDeType(final TypeDiplomeAccepte typeDiplomeAccepte) {
        Collection<Diplome>  diplomesTrouves = CollectionUtils.select(diplomes, new Predicate<Diplome>() {
            @Override
            public boolean evaluate(Diplome object) {
                return object.getTypeDiplomeAccepte().equals(typeDiplomeAccepte);
            }
        });
        return diplomesTrouves;
    }

    public void supprimerUnDiplomeParTypeEtTitre(TypeDiplomeAccepte typeDiplomeAccepte, TitreFormation titreFormation) {
        Diplome diplome = trouverDiplomeParTypeEtTitre(typeDiplomeAccepte, titreFormation);
        diplomes.remove(diplome);
    }

    private Diplome trouverDiplomeParTypeEtTitre(TypeDiplomeAccepte typeDiplomeAccepte, final TitreFormation titreFormation) {
        Collection<Diplome> diplomesAccepte = extraireDiplomesDeType(typeDiplomeAccepte);
        Diplome diplomeTrouvee = CollectionUtils.find(diplomesAccepte, new Predicate<Diplome>() {
            @Override
            public boolean evaluate(Diplome object) {
                return object.getTitreFormation().equals(titreFormation);
            }
        });
        if (diplomeTrouvee == null) {
            throw new DiplomeIntrouvableException();
        }
        return diplomeTrouvee;
    }

    public void supprimerUnDiplome(DiplomeFK diplomeFK) {
        Diplome diplome = trouverDiplome(diplomeFK);
        diplomes.remove(diplome);
    }

    private Diplome trouverDiplome(final DiplomeFK diplomeFK) {
        Diplome diplomeTrouve = (Diplome) CollectionUtils.find(diplomes, new Predicate<Diplome>() {
            @Override
            public boolean evaluate(Diplome object) {
                return object.getFunctionalKey().equals(diplomeFK);
            }
        });
        if (diplomeTrouve == null) {
            throw new DiplomeIntrouvableException();
        }
        return diplomeTrouve;
    }
}
