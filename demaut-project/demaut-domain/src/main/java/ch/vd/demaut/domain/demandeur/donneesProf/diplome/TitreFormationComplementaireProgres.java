package ch.vd.demaut.domain.demandeur.donneesProf.diplome;

import ch.vd.demaut.domain.config.RefProgresID;
import ch.vd.demaut.domain.config.TypeProgres;

public enum TitreFormationComplementaireProgres implements TypeProgres {
    Acupuncture(344861194, "Acupuncture - MTC /77"),
    Electroencephalographie(344861195, "Electroencéphalographie /86"),
    Electroneuromyographie(344861196, "Electroneuromyographie /87"),
    Homeopathie(344861197, "Homéopathie /78"),
    MaladiesCerebrovasculaires(344861198, "Maladies cérébrovasculaires /110"),
    MedecineDUrgence(344861199, "Médecine d'urgence /89"),
    MedecineDePlongee(344861200, "Médecine de plongée /109"),
    MedecineDOrientationAnthroposophique(344861201, "Médecine d'orientation anthroposophique /84"),
    MedecineDuSport(344861202, "Médecine du sport /80"),
    MedecineManuelle(344861203, "Médecine manuelle /79"),
    MedecinePsychosomatiqueEtPsychosociale(344861204, "Médecine psychosomatique et psychosociale /90"),
    HypnoseMedicale(344861205, "Hypnose médicale /88"),
    LasertherapiePeauEtMuqueusesOrificielles(344861206, "Laserthérapie peau et muqueuses orificielles /111"),
    MedecinConseil(344861207, "Médecin-conseil /210"),
    Phlebologie(344861208, "Phlébologie /128"),
    PratiqueDuLaboratoireAuCabinetMedical(344861209, "Pratique du laboratoire au cabinet médical /130"),
    ExTraitRadiolAFortesDosesEnAngio(344861210, "Ex., trait. radiol. à fortes doses en angio. /132"),
    ExRadiolRortesDosesEnGynecolEtObst(344861211, "Ex. radiol. fortes doses en gynécol. et obst. /133"),
    ExRadiolFortesDosesEnCardiologie(344861212, "Ex. radiol. fortes doses en cardiologie /134"),
    ExamensRadiologiquesAFortesDoses(344861213, "Examens radiologiques à fortes doses /131"),
    SonographieDeLaHanche(344861214, "Sonographie de la hanche /70"),
    ThérapieNeurale(344861215, "Thérapie neurale /97"),
    UltrasonographieDeLabdomen(344861216, "Ultrasonographie de l'abdomen /91"),
    UltrasonographiePrenatale(344861217, "Ultrasonographie prénatale /71"),
    PsychotherapieDeleguée(344861218, "Psychothérapie déléguée /151"),
    Gastroscopie(344861219, "Gastroscopie /149"),
    CholangioPancreatographieEndoscopiqueRetro(344861220, "Cholangio-pancréatographie endoscopique rétro /150");

    private RefProgresID refProgresID;

    private String libl;

    private TitreFormationComplementaireProgres(Integer id, String libl) {
        this.refProgresID = new RefProgresID(id);
        this.libl = libl;
    }

    static public TitreFormationComplementaireProgres getTypeById(Integer id2) {
        for (TitreFormationComplementaireProgres type : TitreFormationComplementaireProgres.values()) {
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
