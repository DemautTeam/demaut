package ch.vd.demaut.domain.demandeur.donneesPerso;

import ch.vd.demaut.domain.config.RefProgresID;
import ch.vd.demaut.domain.config.TypeProgres;
import ch.vd.demaut.domain.exception.LangueNotFoundException;

//TODO refactorer pour être en ligne avec ProgresSoa
public enum Langue implements TypeProgres {
    Francais(1, "fr", "Français"),
    Autre(0, "non_fr", "Autre");

    private RefProgresID progresId;

    private String libl;

    private String code;

    private Langue(Integer id, String code, String libl) {
        this.progresId = new RefProgresID(id);
        this.libl = libl;
        this.code = code;
    }

    static public Langue getTypeById(Integer id2) {
        for (Langue type : Langue.values()) {
            if (type.getRefProgresID().equals(new RefProgresID(id2))) {
                return type;
            }
        }
        throw new LangueNotFoundException();
    }

    public RefProgresID getRefProgresID() {
        return progresId;
    }

    public String getLibl() {
        return libl;
    }

    public String getCode() {
        return code;
    }
}
