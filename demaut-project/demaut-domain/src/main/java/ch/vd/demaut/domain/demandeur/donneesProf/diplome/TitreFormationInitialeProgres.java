package ch.vd.demaut.domain.demandeur.donneesProf.diplome;

import ch.vd.demaut.domain.config.RefProgresID;
import ch.vd.demaut.domain.config.TypeProgres;
import ch.vd.demaut.domain.exception.TypeFormationNotFoundException;

public enum TitreFormationInitialeProgres implements TypeProgres {
    ListeIncomplete(0, "Liste incomplète..."),
    DiplFederalDeMedecin(344861006, "Dipl. fédéral de médecin /1"),
    DiplFederalDeMedecinDentiste(344861007, "Dipl. fédéral de médecin-dentiste /2"),
    DiplFederalDePharmacien(344861008, "Dipl. fédéral de pharmacien /3"),
    DiplFederalDOpticien(344861009, "Dipl. fédéral d'opticien /4"),
    DiplFederalDeDroguiste(344861010, "Dipl. fédéral de droguiste /5"),
    CFCDOpticien(344861011, "CFC d'opticien /6"),
    CFRDUnDiplomeEtrangerDeMedecin(344861013, "CFR d'un diplôme étranger de médecin");
    // TODO completer la liste

    private RefProgresID refProgresID;

    private String libl;

    private TitreFormationInitialeProgres(Integer id, String libl) {
        this.refProgresID = new RefProgresID(id);
        this.libl = libl;
    }

    static public TitreFormationInitialeProgres getTypeById(Integer id2) {
        for (TitreFormationInitialeProgres type : TitreFormationInitialeProgres.values()) {
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
