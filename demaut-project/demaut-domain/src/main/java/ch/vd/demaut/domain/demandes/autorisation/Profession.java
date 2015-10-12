package ch.vd.demaut.domain.demandes.autorisation;

import java.util.ArrayList;
import java.util.List;

import ch.vd.demaut.domain.config.RefProgresID;
import ch.vd.demaut.domain.config.TypeProgres;
import ch.vd.demaut.domain.exception.TypeDiplomeNotFoundException;

public enum Profession implements TypeProgres {
    Chiropraticien(53843599, "Chiropraticien", true),//
    Dieteticien(53843600, "Diététicien", true),//
    Droguiste(53843601, "Droguiste", false),//
    Ergotherapeute(53843603, "Ergothérapeute", true),//
    HygienisteDentaire(53843605, "Hygiéniste dentaire", false),//
    Infirmier(-1, "Infirmier", true),//
    LogopedisteOrthophoniste(53843612, "Logopédiste-orthophoniste", false),//
    MasseurMedical(53843616, "Masseur médical", false),//
    Medecin(53843613, "Médecin", true),//
    MedecinDentiste(53843615, "Médecin-dentiste", true),//
    Opticien(53843619, "Opticien", false),//
    Orthoptiste(-2, "Orthoptiste", false),//
    Osteopathe(53843621, "Ostéopathe", false),//
    Pharmacien(53843622, "Pharmacien", true),//
    Physiotherapeute(53843624, "Physiothérapeute", true),//
    Podologue(53843625, "Podologue", false),//
    PsychotherapeuteNonMedecin(53843626, "Psychothérapeute non médecin", true),//
    SageFemme(53843627, "Sage-femme", true),//
    TechnicienEnAnalysesBiomedicales(-3, "Technicien en analyses biomédicales", false),//
    TechnicienEnRadiologieMedicale(53843628, "Technicien en radiologie médicale", false),//
    TechnicienEnSalleDOperation(-4, "Technicien en salle d'opération", false),//
    TherapeuteEnPsychomotricite(53843632, "Thérapeute de la psychomotricité", false);

    
    private RefProgresID refProgresID;

    private String libl;
    
    private boolean codeGLNObligatoire;

    private Profession(Integer id, String libelle, boolean codeGLNObligatoire) {
        this.refProgresID = new RefProgresID(id);
        this.libl = libelle;
        this.codeGLNObligatoire = codeGLNObligatoire;
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

    public static List<Profession> listerProfessionsAvecCodeGlnObligatoire() {
        List<Profession >profAvecGlnObligatoires = new ArrayList<Profession>();
        for (Profession prof : values()) {
            if (prof.isCodeGLNObligatoire()) {
                profAvecGlnObligatoires.add(prof);
            }
        }
        return profAvecGlnObligatoires;
    }
}
