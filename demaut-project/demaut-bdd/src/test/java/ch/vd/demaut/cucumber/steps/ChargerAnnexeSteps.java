package ch.vd.demaut.cucumber.steps;

import cucumber.api.java.fr.Alors;
import cucumber.api.java.fr.Etantdonné;
import cucumber.api.java.fr.Lorsque;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChargerAnnexeSteps {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChargerAnnexeSteps.class);

    @Etantdonné("^le demandeur fait une nouvelle demande$")
    public void le_demandeur_fait_une_nouvelle_demande() throws Throwable {
        LOGGER.info("le_demandeur_fait_une_nouvelle_demande");
        System.out.format("Cukes: %n\n");
    }

    @Lorsque("^ce dernier veut attacher une annexe à cette demande$")
    public void ce_dernier_veut_charger_une_annexe() throws Throwable {
        LOGGER.info("ce_dernier_veut_charger_une_annexe");
    }

    @Alors("^le systeme stocke l annexe et confirme la bonne reception de l annexe$")
    public void le_systeme_stocke_l_annexe_et_confirme_la_bonne_reception_de_l_annexe() throws Throwable {
        LOGGER.info("le_systeme_stocke_l_annexe_et_confirme_la_bonne_reception_de_l_annexe");
    }
}
