package ch.vd.demaut.domain.demandeur.donneesProf.diplome;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.commons.beanutils.BeanPropertyValueEqualsPredicate;
import org.apache.commons.collections.CollectionUtils;

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

    //TODO reimplementer en utilisant org.apache.collection4
    public Collection<Diplome> extraireDiplomesDeType(TypeDiplomeAccepte typeDiplomeAccepte) {
        return CollectionUtils.select(diplomes, new BeanPropertyValueEqualsPredicate("typeDiplomeAccepte", typeDiplomeAccepte));
    }

    public void supprimerUnDiplomeParTypeEtTitre(TypeDiplomeAccepte typeDiplomeAccepte, TitreFormation titreFormation) {
        Diplome diplome = trouverDiplomeParTypeEtTitre(typeDiplomeAccepte, titreFormation);
        diplomes.remove(diplome);
    }

    private Diplome trouverDiplomeParTypeEtTitre(TypeDiplomeAccepte typeDiplomeAccepte, TitreFormation titreFormation) {
        Collection<Diplome> diplomesAccepte = extraireDiplomesDeType(typeDiplomeAccepte);
        Object diplomeTrouvee = CollectionUtils.find(diplomesAccepte, new BeanPropertyValueEqualsPredicate("titreFormation", titreFormation));
        if (diplomeTrouvee == null) {
            throw new DiplomeIntrouvableException();
        }
        return (Diplome) diplomeTrouvee;
    }

    public void supprimerUnDiplome(DiplomeFK diplomeFK) {
        Diplome diplome = trouverDiplome(diplomeFK);
        diplomes.remove(diplome);
    }

    private Diplome trouverDiplome(DiplomeFK diplomeFK) {
        Diplome diplomeTrouve = (Diplome) CollectionUtils.find(diplomes, new BeanPropertyValueEqualsPredicate("functionalKey", diplomeFK));
        if (diplomeTrouve == null) {
            throw new DiplomeIntrouvableException();
        }
        return diplomeTrouve;
    }
    
    
}
