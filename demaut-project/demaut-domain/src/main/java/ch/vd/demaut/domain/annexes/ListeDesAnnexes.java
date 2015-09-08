package ch.vd.demaut.domain.annexes;

import org.apache.commons.beanutils.BeanPropertyValueEqualsPredicate;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Représente une liste d'annexes
 */
public class ListeDesAnnexes {

    // ********************************************************* Fields
    private List<Annexe> annexes = new ArrayList<>();

    // ********************************************************* Constructor

    // ********************************************************* Business
    // methods
    public void ajouterAnnexe(Annexe annexe) {
        annexes.add(annexe);
    }

    // ********************************************************* Getters

    /**
     * Renvoie la liste des annexes
     */
    public Collection<Annexe> listerAnnexes() {
        return Collections.unmodifiableList(annexes);
    }

    /**
     * Extrait les annexes d'un type TypeAnnexe
     */
    @SuppressWarnings("unchecked")
    public Collection<Annexe> extraireAnnexesDeType(TypeAnnexe typeAnnexe) {
        return CollectionUtils.select(annexes, new BeanPropertyValueEqualsPredicate("typeAnnexe", typeAnnexe));
    }

    @SuppressWarnings("all")
    public Annexe afficherUneAnnexe(final String annexeFileName) {
        Object result = CollectionUtils.find(annexes, new Predicate() {
            @Override
            public boolean evaluate(Object input) {
                return ((Annexe) input).getNomFichier().getNomFichier().equals(annexeFileName);
            }
        });
        return result != null ? (Annexe) result : null;
    }

    @SuppressWarnings("all")
    public boolean supprimerUneAnnexe(final String annexeFileName) {
        Object result = CollectionUtils.find(annexes, new Predicate() {
            @Override
            public boolean evaluate(Object input) {
                return ((Annexe) input).getNomFichier().getNomFichier().equals(annexeFileName);
            }
        });
        return result != null && annexes.remove(result);
    }
}
