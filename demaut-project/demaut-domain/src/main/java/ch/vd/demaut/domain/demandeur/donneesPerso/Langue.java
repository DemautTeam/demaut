package ch.vd.demaut.domain.demandeur.donneesPerso;

import ch.vd.demaut.domain.config.RefProgresID;
import ch.vd.demaut.domain.config.TypeProgres;
import ch.vd.demaut.domain.exception.LangueNotFoundException;

public enum Langue implements TypeProgres {
    ListeIncomplete(0, "Incomplète", "Liste incomplète..."),
    SuisseFR(1, "CH_fr", "Français Suisse"),
    SuisseDE(2, "CH_de", "Allemand Suisse"),
    SuisseIT(3, "CH_it", "Italien Suisse"),
    Afghan(4, "AF", "Afghan"),
    Albanai(5, "AL", "Albanai"),
    Allemand(6, "DE", "Allemand"),
    Andorre(7, "AD", "Andorre"),
    Anglais(7, "EN", "Anglais"),
    Argentin(14, "AR", "Argentine"),
    Armenien(15, "AM", "Arménie"),
    Arabe(16, "AR", "Aruba"),
    Australie(17, "AU", "Australie"),
    Autrichen(18, "AT", "Autriche"),
    Azerbaidjan(19, "AZ", "Azerbaïdjan"),
    Neerlandaises(8, "NE", "Néerlandais");

    // TODO completer la liste

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
