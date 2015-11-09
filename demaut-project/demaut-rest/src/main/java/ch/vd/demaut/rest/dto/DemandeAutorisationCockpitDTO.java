package ch.vd.demaut.rest.dto;

import ch.vd.demaut.domain.demandes.DateDeCreation;
import ch.vd.demaut.domain.demandes.ReferenceDeDemande;
import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisation;
import ch.vd.demaut.domain.demandes.autorisation.Profession;
import ch.vd.demaut.domain.demandes.autorisation.StatutDemandeAutorisation;
import ch.vd.demaut.domain.demandeur.donneesProf.CodeGLN;

/**
 * DTO pour une {@link DemandeAutorisation} dans l'affichage du Cockpit
 * 
 */
public class DemandeAutorisationCockpitDTO {

    public ReferenceDeDemande referenceDeDemande;
    public DateDeCreation dateDeCreation;
    public Profession profession;
    public CodeGLN codeGLN;
    public StatutDemandeAutorisation statut;

    public DemandeAutorisationCockpitDTO(DemandeAutorisation demandeAutorisation) {
        this.referenceDeDemande = demandeAutorisation.getReferenceDeDemande();
        this.dateDeCreation = demandeAutorisation.getDateDeCreation();
        this.profession = demandeAutorisation.getProfession();
        this.codeGLN = demandeAutorisation.getDonneesProfessionnelles().getCodeGLN();
        this.statut = demandeAutorisation.getStatutDemandeAutorisation();
    }

}
