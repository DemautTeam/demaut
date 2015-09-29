package ch.vd.demaut.domain.demandeur.donneesProf.diplome;

import ch.vd.demaut.domain.config.RefProgresID;
import ch.vd.demaut.domain.config.TypeProgres;

public enum TitreFormationPostgradeProgres implements TypeProgres {
    ListeIncomplete(0, "Liste incomplète..."),
    AllergologieEtImmunologieClinique(344861105, "Allergologie et immunologie clinique /92"),
    Anesthesiologie(344861106, "Anesthésiologie /2"),
    Angiologie(344861107, "Angiologie /119"),
    Cardiologie(344861108, "Cardiologie /83");
    // TODO completer la liste

    private RefProgresID refProgresID;

    private String libl;

    private TitreFormationPostgradeProgres(Integer id, String libl) {
        this.refProgresID = new RefProgresID(id);
        this.libl = libl;
    }

    static public TitreFormationPostgradeProgres getTypeById(Integer id2) {
        for (TitreFormationPostgradeProgres type : TitreFormationPostgradeProgres.values()) {
            if (type.getRefProgresID().equals(new RefProgresID(id2))) {
                return type;
            }
        }
        throw new TypeDiplomeNotFoundException();
    }

    public RefProgresID getRefProgresID() {
        return refProgresID;
    }

    public String getLibl() {
        return libl;
    }
}
