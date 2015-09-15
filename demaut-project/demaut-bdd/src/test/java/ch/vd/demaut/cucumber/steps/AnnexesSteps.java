package ch.vd.demaut.cucumber.steps;

public class AnnexesSteps {

    // ********************************************************* Static fields

    private static AnnexesSteps INSTANCE = null;

    // ********************************************************* Instanciation

    private AnnexesSteps() {
    }

    /**
     * Récupère l'instance de la classe en cours. A utiliser dans les "BeforeScenario".
     *
     * @return
     */
    public static final AnnexesSteps getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AnnexesSteps();
        }
        return INSTANCE;
    }

    /**
     * Nettoye l'instance de la classe. A utiliser dans les "AfterScenario".
     */
    public static final void disposeInstance() {
        if (INSTANCE != null) {
            INSTANCE.clean();
            INSTANCE = null;
        }
    }

    public void clean() {
    }


}
