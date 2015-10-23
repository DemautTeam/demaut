package ch.vd.demaut.domain.demandes.autorisation;

import java.util.ArrayList;
import java.util.List;

import ch.vd.demaut.domain.config.RefProgresID;
import ch.vd.demaut.domain.config.TypeProgres;
import ch.vd.demaut.domain.exception.TypeDiplomeNotFoundException;

public enum Profession implements TypeProgres {
    Chiropraticien(53843599, "Chiropraticien", true, true, CategorieProfession.Universitaire),//
    Dieteticien(53843600, "Diététicien", true, true, CategorieProfession.Autre),//
    Droguiste(53843601, "Droguiste", false, true, CategorieProfession.Autre),//
    Ergotherapeute(53843603, "Ergothérapeute", true, true, CategorieProfession.Autre),//
    HygienisteDentaire(53843605, "Hygiéniste dentaire", false, true, CategorieProfession.Autre),//
    Infirmier(99984100, "Infirmier", true, true, CategorieProfession.Autre),//
    LogopedisteOrthophoniste(53843612, "Logopédiste-orthophoniste", false, true, CategorieProfession.Autre),//
    MasseurMedical(53843616, "Masseur médical", false, false, CategorieProfession.Autre),//
    Medecin(53843613, "Médecin", true, true, CategorieProfession.Universitaire),//
    MedecinDentiste(53843615, "Médecin-dentiste", true, true, CategorieProfession.Universitaire),//
    Opticien(53843619, "Opticien", false, true, CategorieProfession.Autre),//
    Orthoptiste(53843620, "Orthoptiste", false, false, CategorieProfession.Autre),//
    Osteopathe(53843621, "Ostéopathe", false, true, CategorieProfession.Autre),//
    Pharmacien(53843622, "Pharmacien", true, true, CategorieProfession.Universitaire),//
    Physiotherapeute(53843624, "Physiothérapeute", true, true, CategorieProfession.Autre),//
    Podologue(53843625, "Podologue", false, true, CategorieProfession.Autre),//
    PsychotherapeuteNonMedecin(53843626, "Psychothérapeute non médecin", true, true, CategorieProfession.Autre),//
    SageFemme(53843627, "Sage-femme", true, true, CategorieProfession.Autre),//
    TechnicienEnAnalysesBiomedicales(-3, "Technicien en analyses biomédicales", false, false, CategorieProfession.Autre),//
    TechnicienEnRadiologieMedicale(53843628, "Technicien en radiologie médicale", false, false, CategorieProfession.Autre),//
    TechnicienEnSalleDOperation(-4, "Technicien en salle d'opération", false, false, CategorieProfession.Autre),//
    TherapeuteEnPsychomotricite(53843632, "Thérapeute de la psychomotricité", false, true, CategorieProfession.Autre);

    
    private RefProgresID refProgresID;

    private String libl;
    
    private boolean codeGLNObligatoire;

    private boolean exigeantAnnexe;

    private CategorieProfession categorie;

    private Profession(Integer id, String libelle, boolean codeGLNObligatoire, boolean exigeantAnnexe, CategorieProfession categorie) {
        this.refProgresID = new RefProgresID(id);
        this.libl = libelle;
        this.codeGLNObligatoire = codeGLNObligatoire;
        this.exigeantAnnexe = exigeantAnnexe;
        this.categorie = categorie;
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
    
    public CategorieProfession getCategorie() {
        return categorie;
    }
    
    public boolean estUniversitaire() {
        return categorie.equals(CategorieProfession.Universitaire);
    }
    
    public boolean estNonUniversitaire() {
        return !estUniversitaire();
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
    
    public static List<Profession> listerProfessionsUniversitaires() {
        List<Profession> professionsUniversitaires = new ArrayList<>();
        for (Profession prof : values()) {
            if (prof.estUniversitaire()) {
                professionsUniversitaires.add(prof);
            }
        }
        return professionsUniversitaires;
    }
    
}
