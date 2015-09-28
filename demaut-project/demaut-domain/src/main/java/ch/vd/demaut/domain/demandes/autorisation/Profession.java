package ch.vd.demaut.domain.demandes.autorisation;

import ch.vd.demaut.domain.config.RefProgresID;
import ch.vd.demaut.domain.config.TypeProgres;
import ch.vd.demaut.domain.demandeur.donneesProf.diplome.TypeDiplomeNotFoundException;

public enum Profession implements TypeProgres {
    Chiropraticien(53843599, "Chiropraticien"),//
    Dieteticien(53843600, "Diététicien"),//
    Droguiste(53843601, "Droguiste"),//
    Ergotherapeute(53843603, "Ergothérapeute"),//
    HygienisteDentaire(53843605, "Hygiéniste dentaire"),//
    Infirmier(-1, "Infirmier"),//
    LogopedisteOrthophoniste(53843612, "Logopédiste-orthophoniste"),//
    MasseurMedical(53843616, "Masseur médical"),//
    Medecin(53843613, "Médecin"),//
    MedecinDentiste(53843615, "Médecin-dentiste"),//
    Opticien(53843619, "Opticien"),//
    Orthoptiste(-2, "Orthoptiste"),//
    Osteopathe(53843621, "Ostéopathe"),//
    Pharmacien(53843622, "Pharmacien"),//
    Physiotherapeute(53843624, "Physiothérapeute"),//
    Podologue(53843625, "Podologue"),//
    PsychotherapeuteNonMedecin(53843626, "Psychothérapeute non médecin"),//
    SageFemme(53843627, "Sage-femme"),//
    TechnicienEnAnalysesBiomedicales(-3, "Technicien en analyses biomédicales"),//
    TechnicienEnRadiologieMedicale(53843628, "Technicien en radiologie médicale"),//
    TechnicienEnSalleDOperation(-4, "Technicien en salle d'opération"),//
    TherapeuteEnPsychomotricite(53843632, "Thérapeute de la psychomotricité");

    private RefProgresID refProgresID;

    private String libl;

    private Profession(Integer id, String libelle) {
        this.refProgresID = new RefProgresID(id);

        this.libl = libelle;
    }

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
}
