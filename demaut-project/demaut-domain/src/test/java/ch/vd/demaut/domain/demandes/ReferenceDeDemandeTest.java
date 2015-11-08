package ch.vd.demaut.domain.demandes;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

/**
 * Classe de tests pour la {@link ReferenceDeDemande}
 *
 */
public class ReferenceDeDemandeTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testGenerationReferenceDeDemande() {
        
        ReferenceDeDemande ref = new ReferenceDeDemande(new DateDeCreation(2015,  10, 1), 1L);
        assertThat(ref.getValue()).isEqualTo("201510-0001");
        
    }

}
