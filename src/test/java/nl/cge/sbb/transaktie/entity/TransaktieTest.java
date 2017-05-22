package nl.cge.sbb.transaktie.entity;

import nl.cge.sbb.tag.entity.Tag;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by chris on 08-05-17.
 */
public class TransaktieTest {

    private Transaktie transaktie;

    @Before
    public void setup() {
        transaktie = createTransaktie();
    }

    private Transaktie createTransaktie() {
        return new Transaktie(
                "NL60RABO0123456789",
                LocalDate.parse("2017-03-31", DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                BigDecimal.valueOf(101),
                "omschrijving regel 1",
                "omschrijving regel 2");
    }

    @Test
    public void test() {
        List<String> regels = transaktie.getOmschrijving();
        assertEquals(2, regels.size());
        assertEquals("omschrijving regel 1", regels.get(0));
        assertEquals("omschrijving regel 2", regels.get(1));
    }
    @Test
    public void testGetOmschrijvingAsString() {
        assertEquals("omschrijving regel 1 omschrijving regel 2", transaktie.getOmschrijvingAsString());
    }

    @Test
    public void testHasTag() {
        assertFalse(transaktie.hasTag("foo"));
        transaktie.addTag(new Tag("foo"));
        assertTrue(transaktie.hasTag("foo"));
    }

    @Test
    public void testToString() {
        assertEquals(
                "Transaktie{rekeningnummer='NL60RABO0123456789', transaktiedatum=2017-03-31, bedrag=101, " +
                        "tegenrekeningnummer='null', tegenrekeningnaam='null', omschrijving=omschrijving regel 1 " +
                        "omschrijving regel 2}", transaktie.toString());
    }

    @Test
    public void testEquals() {
        Transaktie t1 = createTransaktie();
        Transaktie t2 = createTransaktie();
        assertTrue(t1.equals(t2));
        t2.addOmschrijvingsregel("nog een regel");
        assertFalse(t1.equals(t2));
    }

}