package ch.vd.demaut.domain.annexes;

import ch.vd.demaut.commons.annotations.ValueObject;
import ch.vd.demaut.commons.vo.BaseValueObject;
import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Liste de types d'annexe ({@link Annexe}) obligatoire pour la complétude d'une {@link DemandeAutorisation}
 */
@ValueObject
public class ListeTypeAnnexesObligatoires extends BaseValueObject {

    // ********************************************************* Fields

    private final List<TypeAnnexe> typesAnnexe;

    // ********************************************************* Constructor

    public ListeTypeAnnexesObligatoires() {
        typesAnnexe = new ArrayList<>();
    }

    public ListeTypeAnnexesObligatoires(List<TypeAnnexe> typeAnnexes) {
        this.typesAnnexe = typeAnnexes;
    }

    public ListeTypeAnnexesObligatoires(TypeAnnexe... typeAnnexes) {
        this(Arrays.asList(typeAnnexes));
    }

    // ********************************************************* Methode metier

    public List<TypeAnnexe> listerTypesAnnexe() {
        return Collections.unmodifiableList(typesAnnexe);
    }

    // ********************************************************* Getters

    // ********************************************************* Builder
    public static class Builder {
        private List<TypeAnnexe> typesAnnexe = new ArrayList<>();

        public Builder ajouterAnnexeObligatoire(TypeAnnexe typeAnnexe) {
            typesAnnexe.add(typeAnnexe);
            return this;
        }

        public ListeTypeAnnexesObligatoires build() {
            return new ListeTypeAnnexesObligatoires(typesAnnexe);
        }
    }

}
