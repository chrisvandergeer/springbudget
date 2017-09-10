package nl.cge.sbb.aggregaties;

import nl.cge.sbb.aggregaties.control.MaandTotaalAggregator;
import nl.cge.sbb.transaktie.entity.Transaktie;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Created by chris on 05-09-17.
 */
public class MaandTotaalResultTest {

    private MaandTotaalAggregator maandTotaalResult;

    @Before
    public void setup() {
        maandTotaalResult = new MaandTotaalAggregator();
    }

    @Test
    public void test() {
        maandTotaalResult.add(createTransaktie(LocalDate.parse("2017-01-03"), BigDecimal.valueOf(99)));
        maandTotaalResult.add(createTransaktie(LocalDate.parse("2017-03-30"), BigDecimal.valueOf(2)));
        maandTotaalResult.add(createTransaktie(LocalDate.parse("2017-01-30"), BigDecimal.valueOf(3)));
        maandTotaalResult.finish();

        List<LocalDate> keys = new ArrayList<>(maandTotaalResult.getTotalen().keySet());
        assertThat(keys.size(), is(3));
        // Maand 1
        assertEquals(LocalDate.parse("2017-01-01"), keys.get(0));
        assertThat(maandTotaalResult.getTotalen().get(keys.get(0)), is(BigDecimal.valueOf(102)));
        // Maand 2
        assertEquals(LocalDate.parse("2017-02-01"), keys.get(1));
        assertThat(maandTotaalResult.getTotalen().get(keys.get(1)), is(BigDecimal.valueOf(0)));
        // Maand 3
        assertEquals(LocalDate.parse("2017-03-01"), keys.get(2));
        assertThat(maandTotaalResult.getTotalen().get(keys.get(2)), is(BigDecimal.valueOf(2)));
    }


    private Transaktie createTransaktie(LocalDate datum, BigDecimal bedrag) {
        return new Transaktie("P123", datum, bedrag);
    }

}