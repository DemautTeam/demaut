package ch.vd.demaut.domain.demandeur.donneesProf.diplome;

import ch.vd.demaut.domain.config.RefProgresID;
import ch.vd.demaut.domain.config.TypeProgres;
import ch.vd.demaut.domain.exception.TypeFormationNotFoundException;

public enum TypeDiplomeAccepte implements TypeProgres {
    D_FORMATION_APPROFONDIE(1, "Diplôme de formation approfondie"),
    D_FORMATION_COMPLEMENTAIRE(2, "Diplôme de formation complémentaire"),
    D_FORMATION_INITIALE(3, "Diplôme de formation initiale"),
    D_POSTGRADE(4, "Diplôme de formation postgrade");

    private RefProgresID refProgresID;

    private String libl;

    private TypeDiplomeAccepte(Integer id, String libl) {
        this.refProgresID = new RefProgresID(id);
        this.libl = libl;
    }

    static public TypeDiplomeAccepte getTypeById(Integer id2) {
        for (TypeDiplomeAccepte type : TypeDiplomeAccepte.values()) {
            if (type.getRefProgresID().equals(new RefProgresID(id2))) {
                return type;
            }
        }
        throw new TypeFormationNotFoundException();
    }

    public RefProgresID getRefProgresID() {
        return refProgresID;
    }

    public String getLibl() {
        return libl;
    }
}
