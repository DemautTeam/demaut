package ch.vd.demaut.domain.demandes.autorisation;

import java.util.ArrayList;
import java.util.List;

import ch.vd.demaut.domain.config.RefProgresID;
import ch.vd.demaut.domain.config.TypeProgres;
import ch.vd.demaut.domain.exception.ProfessionNotFoundException;

public enum Profession implements TypeProgres {
    Medecin(53843613, "Médecin", true, CategorieProfession.Universitaire), //
    MedecinDentiste(53843615, "Médecin-dentiste", true, CategorieProfession.Universitaire), //
    Chiropraticien(53843599, "Chiropraticien", true, CategorieProfession.Universitaire), //
    Pharmacien(53843622, "Pharmacien", true, CategorieProfession.Universitaire), //
    Dieteticien(53843600, "Diététicien", true, CategorieProfession.Autre), //
    Droguiste(53843601, "Droguiste", false, CategorieProfession.Autre), //
    Ergotherapeute(53843603, "Ergothérapeute", true, CategorieProfession.Autre), //
    HygienisteDentaire(53843605, "Hygiéniste dentaire", false, CategorieProfession.Autre), //
    Infirmiere(99984100, "Infirmière", true, CategorieProfession.Autre), //
    LogopedisteOrthophoniste(53843612, "Logopédiste-orthophoniste", false, CategorieProfession.Autre), //
    OpticienOptometriste(53843619, "Opticien-optométriste", false, CategorieProfession.Autre), //
    Osteopathe(53843621, "Ostéopathe", false, CategorieProfession.Autre), //
    Physiotherapeute(53843624, "Physiothérapeute", true, CategorieProfession.Autre), //
    Podologue(53843625, "Podologue", false, CategorieProfession.Autre), //
    Psychologue(344861060,"Psychologue", false, CategorieProfession.Autre),
    SageFemme(53843627, "Sage-femme", true, CategorieProfession.Autre), //
    TherapeuteDeLaMotricite(53843632, "Thérapeute de la motricité", false, CategorieProfession.Autre);

    private RefProgresID refProgresID;

    private String libl;

    private boolean codeGLNObligatoire;

    private CategorieProfession categorie;

    private Profession(Integer id, String libelle, boolean codeGLNObligatoire, CategorieProfession categorie) {
        this.refProgresID = new RefProgresID(id);
        this.libl = libelle;
        this.codeGLNObligatoire = codeGLNObligatoire;
        this.categorie = categorie;
    }

    // TODO: refactor avec TypeAnnexe pour mutualiser
    static public Profession getTypeById(Integer id2) {
        for (Profession type : Profession.values()) {
            if (type.getRefProgresID().equals(new RefProgresID(id2))) {
                return type;
            }
        }
        throw new ProfessionNotFoundException();
    }

    static public Profession getByLibele(String libele) {
        for (Profession profession : Profession.values()) {
            if (profession.getLibl().equals(libele)) {
                return profession;
            }
        }
        throw new ProfessionNotFoundException();
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
        List<Profession> profAvecGlnObligatoires = new ArrayList<>();
        for (Profession prof : values()) {
            if (prof.isCodeGLNObligatoire()) {
                profAvecGlnObligatoires.add(prof);
            }
        }
        return profAvecGlnObligatoires;
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
