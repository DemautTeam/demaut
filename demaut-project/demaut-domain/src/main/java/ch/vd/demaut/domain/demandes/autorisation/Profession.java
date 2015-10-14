package ch.vd.demaut.domain.demandes.autorisation;

import java.util.ArrayList;
import java.util.List;

import ch.vd.demaut.domain.config.RefProgresID;
import ch.vd.demaut.domain.config.TypeProgres;
import ch.vd.demaut.domain.exception.TypeDiplomeNotFoundException;

public enum Profession implements TypeProgres {
    Chiropraticien(53843599, "Chiropraticien", true, true),//
    Dieteticien(53843600, "Diététicien", true, true),//
    Droguiste(53843601, "Droguiste", false, true),//
    Ergotherapeute(53843603, "Ergothérapeute", true, true),//
    HygienisteDentaire(53843605, "Hygiéniste dentaire", false, true),//
    Infirmier(99984100, "Infirmier", true, true),//
    LogopedisteOrthophoniste(53843612, "Logopédiste-orthophoniste", false, true),//
    MasseurMedical(53843616, "Masseur médical", false, false),//
    Medecin(53843613, "Médecin", true, true),//
    MedecinDentiste(53843615, "Médecin-dentiste", true, true),//
    Opticien(53843619, "Opticien", false, true),//
    Orthoptiste(53843620, "Orthoptiste", false, false),//
    Osteopathe(53843621, "Ostéopathe", false, true),//
    Pharmacien(53843622, "Pharmacien", true, true),//
    Physiotherapeute(53843624, "Physiothérapeute", true, true),//
    Podologue(53843625, "Podologue", false, true),//
    PsychotherapeuteNonMedecin(53843626, "Psychothérapeute non médecin", true, true),//
    SageFemme(53843627, "Sage-femme", true, true),//
    TechnicienEnAnalysesBiomedicales(-3, "Technicien en analyses biomédicales", false, false),//
    TechnicienEnRadiologieMedicale(53843628, "Technicien en radiologie médicale", false, false),//
    TechnicienEnSalleDOperation(-4, "Technicien en salle d'opération", false, false),//
    TherapeuteEnPsychomotricite(53843632, "Thérapeute de la psychomotricité", false, true);

    
    private RefProgresID refProgresID;

    private String libl;
    
    private boolean codeGLNObligatoire;

    private boolean exigeantAnnexe;

    private Profession(Integer id, String libelle, boolean codeGLNObligatoire, boolean exigeantAnnexe) {
        this.refProgresID = new RefProgresID(id);
        this.libl = libelle;
        this.codeGLNObligatoire = codeGLNObligatoire;
        this.exigeantAnnexe = exigeantAnnexe;
    }

    //TODO: refactor avec TypeAnnexe
    static public Profession getTypeById(Integer id2) {
        for (Profession type : Profession.values()) {
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
    
    public boolean isCodeGLNObligatoire() {
        return codeGLNObligatoire;
    }

    public boolean isExigeantAnnexes() {
        return exigeantAnnexe;
    }

    public static List<Profession> listerProfessionsAvecCodeGlnObligatoire() {
        List<Profession>profAvecGlnObligatoires = new ArrayList<>();
        for (Profession prof : values()) {
            if (prof.isCodeGLNObligatoire()) {
                profAvecGlnObligatoires.add(prof);
            }
        }
        return profAvecGlnObligatoires;
    }

    public static List<Profession> listerProfessionsExigeantAnnexes() {
        List<Profession> profExigeantAnnexes = new ArrayList<>();
        for (Profession prof : values()) {
            if (prof.isExigeantAnnexes()) {
                profExigeantAnnexes.add(prof);
            }
        }
        return profExigeantAnnexes;
    }
}
