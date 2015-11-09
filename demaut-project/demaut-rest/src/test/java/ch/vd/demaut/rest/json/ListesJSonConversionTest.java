package ch.vd.demaut.rest.json;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ch.vd.demaut.domain.demandes.autorisation.Profession;
import ch.vd.demaut.domain.demandeur.Pays;

public class ListesJSonConversionTest extends AbstractJSonConversionTest {

    @Before
    public void setUp() throws Exception {
        super.setUp();
        ;
    }

    @Test
    public void testConversionProfession() {

        // Fixture
        List<Profession> professions = new ArrayList<Profession>();
        professions.add(Profession.Chiropraticien);
        professions.add(Profession.Dieteticien);

        // Process transform & Assert
        assertJsonStr(professions,
                "[{\"name\":\"Chiropraticien\",\"id\":53843599,\"libl\":\"Chiropraticien\"},{\"name\":\"Dieteticien\",\"id\":53843600,\"libl\":\"Diététicien\"}]");
    }


    @Test
    public void testPaysSuisseJson() {
        Pays suisse = Pays.Suisse;

        String jsonStrExpected = "{\"name\":\"Suisse\",\"id\":1,\"libl\":\"Suisse\"}";

        assertJsonStr(suisse, jsonStrExpected);

    }

}
