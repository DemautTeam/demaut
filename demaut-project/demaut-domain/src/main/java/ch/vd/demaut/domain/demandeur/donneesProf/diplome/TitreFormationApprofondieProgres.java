package ch.vd.demaut.domain.demandeur.donneesProf.diplome;

import ch.vd.demaut.domain.config.RefProgresID;
import ch.vd.demaut.domain.config.TypeProgres;

public enum TitreFormationApprofondieProgres implements TypeProgres {
    PneumologiePediatrique(344861166, "Pneumologie pédiatrique /118"),
    CardiologiePediatrique(344861167, "Cardiologie pédiatrique /114"),
    GastroenterologiePediatrique(344861168, "Gastroentérologie pédiatrique /113"),
    NeuroradiologieDiagnostique(344861169, "Neuroradiologie diagnostique /107"),
    NeuroradiologieInvasive(344861170, "Neuroradiologie invasive /108"),
    RadiologiePediatrique(344861171, "Radiologie pédiatrique /105"),
    UrologieOperatoire(344861172, "Urologie opératoire /127"),
    ChirurgieGeneraleEtDUrgence(344861173, "Chirurgie générale et d'urgence /135"),
    ChirurgieThoracique(344861174, "Chirurgie thoracique /138"),
    ChirurgieVasculaire(344861175, "Chirurgie vasculaire /137"),
    ChirurgieViscerale(344861176, "Chirurgie viscérale /136"),
    OncologieHematologiePediatrique(344861177, "Oncologie-hématologie pédiatrique /117"),
    PsychiatriePsychotherapiePersonneAgee(344861178, "Psychiatrie, psychothérapie personne âgée/140"),
    ChirurgieDeLaMain(344861179, "Chirurgie de la main /129"),
    Geriatrie(344861180, "Gériatrie /81"),
    ReproductionEtEndocrinologieGynecologique(344861181, "Reproduction et endocrinologie gynécologique /104"),
    ObstetriqueEtMedecineFoetoMaternelle(344861182, "Obstétrique et médecine foeto-maternelle /121"),
    OncologieGynecologique(344861183, "Oncologie gynécologique /120"),
    NephrologiePediatrique(344861184, "Néphrologie pédiatrique /115"),
    Ophtalmochirurgie(344861185, "Ophtalmochirurgie /72"),
    ChirurgieCervicoFaciale(344861186, "Chirurgie cervico-faciale /12"),
    Phoniatrie(344861187, "Phoniatrie /35"),
    Cytopathologie(344861188, "Cytopathologie /125"),
    Neuropathologie(344861189, "Neuropathologie /124"),
    PathologieMoleculaire(344861190, "Pathologie moléculaire /123"),
    EndocrinologieDiabetologiePediatrique(344861191, "Endocrinologie-diabétologie pédiatrique /112"),
    Neonatologie(344861192, "Néonatologie /73"),
    Neuropediatrie(344861193, "Neuropédiatrie /116");

    private RefProgresID refProgresID;

    private String libl;

    private TitreFormationApprofondieProgres(Integer id, String libl) {
        this.refProgresID = new RefProgresID(id);
        this.libl = libl;
    }

    static public TitreFormationApprofondieProgres getTypeById(Integer id2) {
        for (TitreFormationApprofondieProgres type : TitreFormationApprofondieProgres.values()) {
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
