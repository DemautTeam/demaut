package ch.vd.demaut.cucumber.steps;

import ch.vd.demaut.cucumber.steps.exception.RefenceDeDemandeNexistePas;
import ch.vd.demaut.domain.demandes.ReferenceDeDemande;

import java.util.HashMap;
import java.util.Map;

/**
 * Classe utilitaire de mapping entre les references des scénario et les références du Domaine (les vraies)
 */
public class MappingReferences {

    private Map<ReferenceDeDemande, ReferenceDeDemande> mapScenarioToDomaine = new HashMap<>();
    private Map<ReferenceDeDemande, ReferenceDeDemande> mapDomaineToScenario = new HashMap<>();

    public void ajouterMapping(ReferenceDeDemande refScenario, ReferenceDeDemande refDomaine) {
        mapScenarioToDomaine.put(refScenario, refDomaine);
        mapDomaineToScenario.put(refDomaine, refScenario);
    }

    public ReferenceDeDemande trouverDomaineRef(ReferenceDeDemande refScenario) {
        ReferenceDeDemande refDomaine = mapScenarioToDomaine.get(refScenario);
        if (refDomaine == null) {
            throw new RefenceDeDemandeNexistePas();
        }
        return refDomaine;
    }

    public ReferenceDeDemande trouverScenarioRef(ReferenceDeDemande refDomaine) {
        ReferenceDeDemande refScenario = mapDomaineToScenario.get(refDomaine);
        if (refScenario == null) {
            throw new RefenceDeDemandeNexistePas();
        }
        return refScenario;
    }

}
