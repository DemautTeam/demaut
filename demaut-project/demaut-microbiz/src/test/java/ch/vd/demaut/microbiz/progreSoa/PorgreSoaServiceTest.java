package ch.vd.demaut.microbiz.progreSoa;

import ch.vd.ses.referentiel.tiers_v01.Root;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
        Root root = porgreSoaService.rechercheSOATierById("323902038");
        assertThat(root).isNotNull();
    }

    @Test
    public void shouldRechercheSOATierByNom() throws Exception {
        Root root = porgreSoaService.rechercheSOATierByNom("Clinique");
        assertThat(root).isNotNull();
    }

    @Test
    public void shouldListerLesTypesAnnexes() throws Exception {
        AnnexetypesList annexetypesList = porgreSoaService.listerLesTypesAnnexes();
        assertThat(annexetypesList).isNotNull();
    }

}