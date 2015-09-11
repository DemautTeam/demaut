package ch.vd.demaut.domain.annexes;

import ch.vd.demaut.commons.utils.FileMockHelper;
import junit.framework.TestCase;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.assertj.core.api.Assertions.fail;
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;

@RunWith(JUnit4.class)
public class AnnexeValidateurTest {

    private AnnexeValidateur validateur;

    private String nomFichierValide = "test.pdf";
    private String nomFichierInvalideLongueur = StringUtils.leftPad("test.pdf", AnnexeMetadataValidateur.getLongueurMax(), 't');
    private String nomFichierInvalideNommage1 = "." + nomFichierValide;
    private String nomFichierInvalideNommage2 = nomFichierValide + ".";
    private String nomFichierInvalideNommage3 = "test*" + nomFichierValide;

    @Before
    public void setUp() throws Exception {
        validateur = AnnexeValidateur.getInstance();
    }

    @Test
    public void annexeVideEstInvalide() {
        Annexe annexe = buildAnnexe(0, nomFichierValide);
        validerAnnexeEtTesterInvalide(annexe);
    }

    @Test
    public void annexeAvecTailleSuperieurAMaxEstInvalide() {
        Annexe annexe = buildAnnexe(12, nomFichierValide);
        validerAnnexeEtTesterInvalide(annexe);
    }

    @Test
    public void annexeAvecTailleEntreMinEtMaxEstValide() {
        Annexe annexe = buildAnnexe(2, nomFichierValide);
        validerAnnexeEtTesterValide(annexe);
    }

    @Test
    public void annexeAvecNomFichierEstInvalide() {
        validerAnnexeEtTesterInvalide(buildAnnexe(2, nomFichierInvalideLongueur));
        validerAnnexeEtTesterInvalide(buildAnnexe(2, nomFichierInvalideNommage1));
        validerAnnexeEtTesterInvalide(buildAnnexe(2, nomFichierInvalideNommage2));
        validerAnnexeEtTesterInvalide(buildAnnexe(2, nomFichierInvalideNommage3));
    }

    private void validerAnnexeEtTesterValide(Annexe annexe) {
        try {
            validateur.valider(annexe);
        } catch (AnnexeNonValideException e) {
            fail("Failed because exception AnnexeNonValideException was thrown");
        }
    }

    private void validerAnnexeEtTesterInvalide(Annexe annexe) {
        try {
            validateur.valider(annexe);
            failBecauseExceptionWasNotThrown(AnnexeNonValideException.class);
        } catch (AnnexeNonValideException e) {
        }
    }

    @SuppressWarnings("all")
    private Annexe buildAnnexe(int tailleFichierEnMB, String nomFichier) {
        byte[] contenu = FileMockHelper.buildContenuFichier(tailleFichierEnMB);
        return new Annexe(TypeAnnexe.Certificat, nomFichier, contenu);
    }


}
