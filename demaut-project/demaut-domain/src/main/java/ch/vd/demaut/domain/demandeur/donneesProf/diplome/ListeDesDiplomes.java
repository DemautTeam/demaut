package ch.vd.demaut.domain.demandeur.donneesProf.diplome;

import org.apache.commons.beanutils.BeanPropertyValueEqualsPredicate;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
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

    @SuppressWarnings("unchecked")
    public Collection<Diplome> extraireDiplomesDeType(TypeDiplomeAccepte typeDiplomeAccepte) {
        return CollectionUtils.select(diplomes, new BeanPropertyValueEqualsPredicate("typeDiplomeAccepte", typeDiplomeAccepte));
    }

    public void supprimerUnDiplomeParTypeEtTitre(TypeDiplomeAccepte dFormationApprofondie, TitreFormation titreFormation) {
        Diplome diplome = trouverDiplomeParTypeEtTitre(dFormationApprofondie, titreFormation);
        diplomes.remove(diplome);
    }

    private Diplome trouverDiplomeParTypeEtTitre(TypeDiplomeAccepte typeDiplomeAccepte, TitreFormation titreFormation) {
        Collection diplomesAccepte = extraireDiplomesDeType(typeDiplomeAccepte);
        Object diplomeTrouvee = CollectionUtils.find(diplomesAccepte, new BeanPropertyValueEqualsPredicate("titreFormation", titreFormation));
        if (diplomeTrouvee == null) {
            throw new DiplomeIntrouvableException();
        }
        return (Diplome) diplomeTrouvee;
    }
}
