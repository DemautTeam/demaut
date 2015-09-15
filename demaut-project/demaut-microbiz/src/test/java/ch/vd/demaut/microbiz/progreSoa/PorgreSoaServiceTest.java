package ch.vd.demaut.microbiz.progreSoa;

import ch.vd.ses.referentiel.demaut_1_0.RefRoot;
import ch.vd.ses.referentiel.tiers_v01.ReportedOrganisationType;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;

@ContextConfiguration({"classpath*:microbizTest-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PorgreSoaServiceTest {

    @Autowired
    private PorgreSoaService porgreSoaService;

    @Before
    public void setUp() throws Exception {
        assertNotNull(porgreSoaService);
    }

    @Test
    public void shouldRechercheSOATierById() throws Exception {
        List<ReportedOrganisationType> reportedOrganisationTypes = porgreSoaService.rechercheSOATierById("323902038");
        assertThat(reportedOrganisationTypes).isNotNull();
    }

    @Test
    public void shouldRechercheSOATierByNom() throws Exception {
        List<ReportedOrganisationType> reportedOrganisationTypes = porgreSoaService.rechercheSOATierByNom("Clinique");
        assertThat(reportedOrganisationTypes).isNotNull();
    }

    @Test
    public void shouldListeSOAFormationApprofondie() throws Exception {
        RefRoot refRoot = porgreSoaService.listeSOAFormationApprofondie();
        assertThat(refRoot).isNotNull();
        assertThat(refRoot.getRefList().getRefListType()).isNotEmpty();
        assertThat(refRoot.getRefList().getRefListType().size()).isEqualTo(28);
    }

    @Test
    public void shouldListeSOAFormationComplementaire() throws Exception {
        RefRoot refRoot = porgreSoaService.listeSOAFormationComplementaire();
        assertThat(refRoot).isNotNull();
        assertThat(refRoot.getRefList().getRefListType()).isNotEmpty();
        assertThat(refRoot.getRefList().getRefListType().size()).isEqualTo(27);
    }

    @Test
    public void shouldListeSOAFormationInitiale() throws Exception {
        RefRoot refRoot = porgreSoaService.listeSOAFormationInitiale();
        assertThat(refRoot).isNotNull();
        assertThat(refRoot.getRefList().getRefListType()).isNotEmpty();
        assertThat(refRoot.getRefList().getRefListType().size()).isEqualTo(102);
    }

    @Test
    public void shouldListeSOADiplomesPostGrade() throws Exception {
        RefRoot refRoot = porgreSoaService.listeSOADiplomesPostGrade();
        assertThat(refRoot).isNotNull();
        assertThat(refRoot.getRefList().getRefListType()).isNotEmpty();
        assertThat(refRoot.getRefList().getRefListType().size()).isEqualTo(63);
    }

    @Test
    public void shouldListeSOAProfession() throws Exception {
        RefRoot refRoot = porgreSoaService.listeSOAProfession();
        assertThat(refRoot).isNotNull();
        assertThat(refRoot.getRefList().getRefListType()).isNotEmpty();
        assertThat(refRoot.getRefList().getRefListType().size()).isEqualTo(41);
    }

    @Test
    public void shouldListeSOATypesAnnexes() throws Exception {
        RefRoot refRoot = porgreSoaService.listeSOATypesAnnexes();
        assertThat(refRoot).isNotNull();
        assertThat(refRoot.getRefList().getRefListType()).isNotEmpty();
        assertThat(refRoot.getRefList().getRefListType().size()).isEqualTo(26);
    }
}