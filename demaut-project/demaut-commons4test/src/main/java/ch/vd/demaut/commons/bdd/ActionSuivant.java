package ch.vd.demaut.commons.bdd;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Symbolise l'êtat du clique du bouton suivant
 */
public enum ActionSuivant {

    valideAvancement,
    refuseAvancement;


    public static void verifieValidation(ActionSuivant expectedAcceptation, ActionSuivant actualAcceptation) {
        assertThat(actualAcceptation).isEqualTo(expectedAcceptation);
    }

    public static ActionSuivant accepteIfTrue(boolean value){
        if(value){
            return valideAvancement;
        }
        return refuseAvancement;
    }

}