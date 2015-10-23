package ch.vd.demaut.commons.bdd;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Indique si le systeme accepte ou refuse
 */
public enum AccepteOuRefuse {
    accepte,
    refuse;

    public static void verifieAcceptation(AccepteOuRefuse expectedAcceptation, AccepteOuRefuse actualAcceptation) {
        assertThat(actualAcceptation).isEqualTo(expectedAcceptation);
    }

    public static AccepteOuRefuse accepteIfTrue(boolean value){
        if(value){
            return accepte;
        }
        return refuse;
    }

}
