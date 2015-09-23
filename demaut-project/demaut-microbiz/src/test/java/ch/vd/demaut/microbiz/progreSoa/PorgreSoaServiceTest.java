package ch.vd.demaut.microbiz.progreSoa;

import ch.vd.ses.referentiel.demaut_1_0.RefRoot;
import ch.vd.ses.referentiel.tiers_v01.ReportedOrganisationType;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;

@ContextConfiguration({"classpath*:microbizTest-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PorgreSoaServiceTest {

    @Inject
    private ProgreSoaService progreSoaService;

    @Before
    public void setUp() throws Exception {
        assertNotNull(progreSoaService);
    }

    @Test
    public void testRechercheSOATierById() throws Exception {
        List<ReportedOrganisationType> reportedOrganisationTypes = progreSoaService.rechercheSOATierById("323902038");
        assertThat(reportedOrganisationTypes).isNotNull();
    }

    @Test
    public void testRechercheSOATierByNom() throws Exception {
        List<ReportedOrganisationType> reportedOrganisationTypes = progreSoaService.rechercheSOATierByNom("Clinique");
        assertThat(reportedOrganisationTypes).isNotNull();
    }

    @Test
    public void testListeSOAAPTitre() throws Exception {
        RefRoot refRoot = progreSoaService.listeSOAAPTitre();
        assertThat(refRoot).isNotNull();
        assertThat(refRoot.getVcList().getVc()).isNotEmpty();
        assertThat(refRoot.getVcList().getVc().size()).isEqualTo(7);
    }

    @Test
    public void testListeSOAFormationApprofondie() throws Exception {
        RefRoot refRoot = progreSoaService.listeSOAFormationApprofondie();
        assertThat(refRoot).isNotNull();
        assertThat(refRoot.getVcList().getVc()).isNotEmpty();
        assertThat(refRoot.getVcList().getVc().size()).isEqualTo(28);
    }

    @Test
    public void testListeSOAFormationComplementaire() throws Exception {
        RefRoot refRoot = progreSoaService.listeSOAFormationComplementaire();
        assertThat(refRoot).isNotNull();
        assertThat(refRoot.getVcList().getVc()).isNotEmpty();
        assertThat(refRoot.getVcList().getVc().size()).isEqualTo(27);
    }

    @Test
    public void testListeSOAFormationInitiale() throws Exception {
        RefRoot refRoot = progreSoaService.listeSOAFormationInitiale();
        assertThat(refRoot).isNotNull();
        assertThat(refRoot.getVcList().getVc()).isNotEmpty();
        assertThat(refRoot.getVcList().getVc().size()).isEqualTo(102);
    }

    @Test
    public void testListeSOADiplomesPostGrade() throws Exception {
        RefRoot refRoot = progreSoaService.listeSOADiplomesPostGrade();
        assertThat(refRoot).isNotNull();
        assertThat(refRoot.getVcList().getVc()).isNotEmpty();
        assertThat(refRoot.getVcList().getVc().size()).isEqualTo(63);
    }

    @Test
    public void testListeSOAProfession() throws Exception {
        RefRoot refRoot = progreSoaService.listeSOAProfession();
        assertThat(refRoot).isNotNull();
        assertThat(refRoot.getVcList().getVc()).isNotEmpty();
        assertThat(refRoot.getVcList().getVc().size()).isEqualTo(41);
    }

    @Test
    public void testListeSOATypesAnnexes() throws Exception {
        RefRoot refRoot = progreSoaService.listeSOATypesAnnexes();
        assertThat(refRoot).isNotNull();
        assertThat(refRoot.getVcList().getVc()).isNotEmpty();
        assertThat(refRoot.getVcList().getVc().size()).isEqualTo(26);
    }
}